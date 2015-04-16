package com.iteye.weimingtom.guichan.widget;

import java.util.ArrayList;
import java.util.List;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Key;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.event.Event;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.event.MouseEvent;
import com.iteye.weimingtom.guichan.event.SelectionEvent;
import com.iteye.weimingtom.guichan.gui.BasicContainer;
import com.iteye.weimingtom.guichan.gui.FocusHandler;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.listener.FocusListener;
import com.iteye.weimingtom.guichan.listener.KeyListener;
import com.iteye.weimingtom.guichan.listener.MouseListener;
import com.iteye.weimingtom.guichan.listener.SelectionListener;
import com.iteye.weimingtom.guichan.platform.Font;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.ListModel;

public class DropDown extends BasicContainer implements ActionListener, KeyListener, MouseListener, FocusListener, SelectionListener {
	protected boolean mDroppedDown;
	protected boolean mPushed;
	protected int mFoldedUpHeight;
	protected ScrollArea mScrollArea;
	protected ListBox mListBox;
	protected FocusHandler mInternalFocusHandler = new FocusHandler();
	protected boolean mInternalScrollArea;
	protected boolean mInternalListBox;
	protected boolean mIsDragged;
    protected List<SelectionListener> mSelectionListeners = new ArrayList<SelectionListener>();

    public DropDown() throws GuichanException {
    	this(null, null, null);
    }
    
    public DropDown(ListModel listModel) throws GuichanException {
    	this(listModel, null, null);
    }
    
    public DropDown(ListModel listModel,
            ScrollArea scrollArea) throws GuichanException {
    	this(listModel, scrollArea, null);
    }
    
    public DropDown(ListModel listModel,
            ScrollArea scrollArea,
            ListBox listBox) throws GuichanException {
		setWidth(100);
		setFocusable(true);
		mDroppedDown = false;
		mPushed = false;
		mIsDragged = false;
		
		setInternalFocusHandler(mInternalFocusHandler);
		
		mInternalScrollArea = (scrollArea == null);
		mInternalListBox = (listBox == null);
		
		if (mInternalScrollArea) {
			mScrollArea = new ScrollArea();
		} else {
			mScrollArea = scrollArea;
		}
		
		if (mInternalListBox) {
			mListBox = new ListBox();
		} else {
			mListBox = listBox;
		}
		
		mScrollArea.setContent(mListBox);
		add(mScrollArea);
		
		mListBox.addActionListener(this);
		mListBox.addSelectionListener(this);
		
		setListModel(listModel);
		
		if (mListBox.getSelected() < 0) {
			mListBox.setSelected(0);
		}
		
		addMouseListener(this);
		addKeyListener(this);
		addFocusListener(this);
		
		adjustHeight();
	}
	    
    protected void drawButton(Graphics graphics) throws GuichanException {
        Color faceColor, highlightColor, shadowColor;
        int offset;
        int alpha = getBaseColor().a;

        if (mPushed) {
            faceColor = getBaseColor().minus(new Color(0x303030));
            faceColor.a = alpha;
            highlightColor = faceColor.minus(new Color(0x303030));
            highlightColor.a = alpha;
            shadowColor = faceColor.plus(new Color(0x303030));
            shadowColor.a = alpha;
            offset = 1;
        } else {
            faceColor = getBaseColor();
            faceColor.a = alpha;
            highlightColor = faceColor.plus(new Color(0x303030));
            highlightColor.a = alpha;
            shadowColor = faceColor.minus(new Color(0x303030));
            shadowColor.a = alpha;
            offset = 0;
        }

        Rectangle currentClipArea = graphics.getCurrentClipArea();
        graphics.setColor(highlightColor);
        graphics.drawLine(0, 
                           0, 
                           currentClipArea.width - 1, 
                           0);
        graphics.drawLine(0, 
                           1, 
                           0, 
                           currentClipArea.height - 1);
        graphics.setColor(shadowColor);
        graphics.drawLine(currentClipArea.width - 1, 
                           1, 
                           currentClipArea.width - 1, 
                           currentClipArea.height - 1);
        graphics.drawLine(1, 
                           currentClipArea.height - 1, 
                           currentClipArea.width - 2, 
                           currentClipArea.height - 1);

        graphics.setColor(faceColor);
        graphics.fillRectangle(new Rectangle(1, 
                                          1, 
                                          currentClipArea.width - 2, 
                                          currentClipArea.height - 2));

        graphics.setColor(getForegroundColor());

        int i;
        int n = currentClipArea.height / 3;
        int dx = currentClipArea.height / 2;
        int dy = (currentClipArea.height * 2) / 3;
        for (i = 0; i < n; i++) {
            graphics.drawLine(dx - i + offset,
                               dy - i + offset,
                               dx + i + offset,
                               dy - i + offset);
        }
    }

    protected void dropDown() throws GuichanException {
        if (!mDroppedDown) {
            mDroppedDown = true;
            mFoldedUpHeight = getHeight();
            adjustHeight();

            if (getParent() != null) {
                getParent().moveToTop(this);
            }
        }
        mListBox.requestFocus();
    }
    
    protected void foldUp() throws GuichanException {
        if (mDroppedDown) {
            mDroppedDown = false;
            adjustHeight();
            mInternalFocusHandler.focusNone();
        }
    }
    
    protected void distributeValueChangedEvent() {
        for (SelectionListener iter : mSelectionListeners) {
            SelectionEvent event = new SelectionEvent(this);
            iter.valueChanged(event);
        }
    }
    
	@Override
	public void valueChanged(SelectionEvent event) {
		distributeValueChangedEvent();
	}

	@Override
	public void focusGained(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(Event event) throws GuichanException {
        foldUp();
        mInternalFocusHandler.focusNone();
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) throws GuichanException {
        // If we have a mouse press on the widget.
        if (0 <= mouseEvent.getY()
            && mouseEvent.getY() < getHeight()
            && mouseEvent.getX() >= 0
            && mouseEvent.getX() < getWidth()
            && mouseEvent.getButton() == MouseEvent.LEFT
            && !mDroppedDown
            && mouseEvent.getSource() == this) {
            mPushed = true;
            dropDown();
            requestModalMouseInputFocus();
        } else if (0 <= mouseEvent.getY()
                 && mouseEvent.getY() < mFoldedUpHeight
                 && mouseEvent.getX() >= 0
                 && mouseEvent.getX() < getWidth()
                 && mouseEvent.getButton() == MouseEvent.LEFT
                 && mDroppedDown
                 && mouseEvent.getSource() == this) {
            mPushed = false;
            foldUp();
            releaseModalMouseInputFocus();
        } else if (0 > mouseEvent.getY()
                 || mouseEvent.getY() >= getHeight()
                 || mouseEvent.getX() < 0
                 || mouseEvent.getX() >= getWidth()) {
            mPushed = false;
            foldUp();
        }
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) throws GuichanException {
        if (mIsDragged) {
            mPushed = false;
        }
        
        if ((0 > mouseEvent.getY()
            || mouseEvent.getY() >= getHeight()
            || mouseEvent.getX() < 0
            || mouseEvent.getX() >= getWidth())
            && mouseEvent.getButton() == MouseEvent.LEFT
            && isModalMouseInputFocused()) {
            releaseModalMouseInputFocus();

            if (mIsDragged) {
                foldUp();
            }
        } else if (mouseEvent.getButton() == MouseEvent.LEFT) {
            mPushed = false;
        }

        mIsDragged = false;
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) throws GuichanException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMovedUp(MouseEvent mouseEvent)
			throws GuichanException {
        if (isFocused() && mouseEvent.getSource() == this) {                   
            mouseEvent.consume();
            if (mListBox.getSelected() > 0) {
                mListBox.setSelected(mListBox.getSelected() - 1);
            }
        }
	}

	@Override
	public void mouseWheelMovedDown(MouseEvent mouseEvent)
			throws GuichanException {
        if (isFocused() && mouseEvent.getSource() == this) {            
            mouseEvent.consume();
            mListBox.setSelected(mListBox.getSelected() + 1);
        }
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) throws GuichanException {
        mIsDragged = true;

        mouseEvent.consume();
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) throws GuichanException {
        if (keyEvent.isConsumed()) {
            return;
        }
        Key key = keyEvent.getKey();

        if ((key.getValue() == Key.ENTER || 
        	key.getValue() == Key.SPACE)
            && !mDroppedDown) {
            dropDown();
            keyEvent.consume();
        } else if (key.getValue() == Key.UP) {
            setSelected(getSelected() - 1);
            keyEvent.consume();
        } else if (key.getValue() == Key.DOWN) {
            setSelected(getSelected() + 1);
            keyEvent.consume();
        }
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) throws GuichanException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void action(ActionEvent actionEvent) throws GuichanException {
        foldUp();
        releaseModalMouseInputFocus();
        distributeActionEvent();
	}

	@Override
	public void draw(Graphics graphics) throws GuichanException {
        int h;

        if (mDroppedDown) {
            h = mFoldedUpHeight;
        } else {
            h = getHeight();
        }

        Color faceColor = getBaseColor();
        Color highlightColor, shadowColor;
        int alpha = getBaseColor().a;
        highlightColor = faceColor.plus(new Color(0x303030));
        highlightColor.a = alpha;
        shadowColor = faceColor.minus(new Color(0x303030));
        shadowColor.a = alpha;

        // Draw a border.
        graphics.setColor(shadowColor);
        graphics.drawLine(0, 0, getWidth() - 1, 0);
        graphics.drawLine(0, 1, 0, h - 2);
        graphics.setColor(highlightColor);
        graphics.drawLine(getWidth() - 1, 1, getWidth() - 1, h - 1);
        graphics.drawLine(0, h - 1, getWidth() - 1, h - 1);

         // Push a clip area so the other drawings don't need to worry
        // about the border.
        graphics.pushClipArea(new Rectangle(1, 1, getWidth() - 2, h - 2));
        Rectangle currentClipArea = graphics.getCurrentClipArea();

        graphics.setColor(getBackgroundColor());
        graphics.fillRectangle(new Rectangle(0, 0, currentClipArea.width, currentClipArea.height));
       
        if (isFocused()) {
            graphics.setColor(getSelectionColor());
            graphics.fillRectangle(new Rectangle(0, 
                                              0, 
                                              currentClipArea.width - currentClipArea.height, 
                                              currentClipArea.height));
            graphics.setColor(getForegroundColor());
        }

        if (mListBox.getListModel() != null 
            && mListBox.getSelected() >= 0) {
            graphics.setColor(getForegroundColor());
            graphics.setFont(getFont());

            graphics.drawText(mListBox.getListModel().getElementAt(mListBox.getSelected()), 1, 0);
        }
        
        // Push a clip area before drawing the button.
        graphics.pushClipArea(new Rectangle(currentClipArea.width - currentClipArea.height,
                                         0,
                                         currentClipArea.height,
                                         currentClipArea.height));
        drawButton(graphics);
        graphics.popClipArea();
        graphics.popClipArea();

         if (mDroppedDown) {
             // Draw a border around the children.
             graphics.setColor(shadowColor);
             graphics.drawRectangle(new Rectangle(0,
                                               mFoldedUpHeight,
                                               getWidth(),
                                               getHeight() - mFoldedUpHeight));
             drawChildren(graphics);
         }
	}
    
    public int getSelected() {
        return mListBox.getSelected();
    }

    public void setSelected(int selected) throws GuichanException {
        if (selected >= 0) {
            mListBox.setSelected(selected);
        }
    }
    
    public void setListModel(ListModel listModel) throws GuichanException {
        mListBox.setListModel(listModel);

        if (mListBox.getSelected() < 0) {
            mListBox.setSelected(0);
        }

        adjustHeight();
    }

    public ListModel getListModel() {
        return mListBox.getListModel();
    }
    
    public void adjustHeight() throws GuichanException {
        if (mScrollArea == null) {
            throw new GuichanException("Scroll area has been deleted.");
        }

        if (mListBox == null) {
            throw new GuichanException("List box has been deleted.");
        }

        int listBoxHeight = mListBox.getHeight();
        
        // We add 2 for the border
        int h2 = getFont().getHeight() + 2;

        setHeight(h2);

        // The addition/subtraction of 2 compensates for the seperation lines
        // seperating the selected element view and the scroll area.

        if (mDroppedDown && getParent() != null) {
            int h = getParent().getChildrenArea().height - getY();

            if (listBoxHeight > h - h2 - 2) {
                mScrollArea.setHeight(h - h2 - 2);
                setHeight(h);
            } else {
                setHeight(listBoxHeight + h2 + 2);
                mScrollArea.setHeight(listBoxHeight);
            }
        }

        mScrollArea.setWidth(getWidth());
        // Resize the ListBox to exactly fit the ScrollArea.
        mListBox.setWidth(mScrollArea.getChildrenArea().width);
        mScrollArea.setPosition(0, 0);
    }
    
    @Override
    public void death(Event event) throws GuichanException {        
        if (event.getSource() == mScrollArea) {
            mScrollArea = null;
        }
        super.death(event);
    }
    
    @Override
    public Rectangle getChildrenArea() {
        if (mDroppedDown) {
            // Calculate the children area (with the one pixel border in mind)
            return new Rectangle(1, 
                             mFoldedUpHeight + 1, 
                             getWidth() - 2, 
                             getHeight() - mFoldedUpHeight - 2);
        }

        return new Rectangle();
    }
    
    @Override
    public void setBaseColor(Color color) {
        if (mInternalScrollArea) {
            mScrollArea.setBaseColor(color);
        }
        if (mInternalListBox) {
            mListBox.setBaseColor(color);
        }
        super.setBaseColor(color);
    }

    @Override
    public void setBackgroundColor(Color color) {
        if (mInternalScrollArea) {
            mScrollArea.setBackgroundColor(color);
        }
        if (mInternalListBox) {
            mListBox.setBackgroundColor(color);
        }
        super.setBackgroundColor(color);
    }

    @Override
    public void setForegroundColor(Color color) {
        if (mInternalScrollArea) {
            mScrollArea.setForegroundColor(color);
        }

        if (mInternalListBox) {
            mListBox.setForegroundColor(color);
        }

        super.setForegroundColor(color);
    }

    @Override
	public void setFont(Font font) throws GuichanException {
		if (mInternalScrollArea) {
            mScrollArea.setFont(font);
        }

        if (mInternalListBox) {
            mListBox.setFont(font);
        }

        super.setFont(font);
	}
    
    @Override
    public void setSelectionColor(Color color) {
        super.setSelectionColor(color);
        if (mInternalListBox) {
            mListBox.setSelectionColor(color);
        }       
    }

    public void addSelectionListener(SelectionListener selectionListener) {
        mSelectionListeners.add(selectionListener);
    }
   
    public void removeSelectionListener(SelectionListener selectionListener) {
        mSelectionListeners.remove(selectionListener);
    }
}
