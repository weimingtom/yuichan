package com.iteye.weimingtom.guichan.widget;

import java.util.ArrayList;
import java.util.List;

import com.iteye.weimingtom.guichan.basic.ClipRectangle;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Key;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.event.MouseEvent;
import com.iteye.weimingtom.guichan.event.SelectionEvent;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.KeyListener;
import com.iteye.weimingtom.guichan.listener.MouseListener;
import com.iteye.weimingtom.guichan.listener.SelectionListener;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.ListModel;

public class ListBox extends Widget implements MouseListener, KeyListener{
    protected int mSelected;
    protected ListModel mListModel;
    protected boolean mWrappingEnabled;
    protected List<SelectionListener> mSelectionListeners = new ArrayList<SelectionListener>();
    
    public ListBox() throws GuichanException {
    	mSelected = -1;
    	mListModel = null;
    	mWrappingEnabled = false;
    
    	setWidth(100);
    	setFocusable(true);

    	addMouseListener(this);
    	addKeyListener(this);
    }

    public ListBox(ListModel listModel) throws GuichanException {
    	mSelected = -1;
      	mWrappingEnabled = false;
    
      	setWidth(100);
      	setListModel(listModel);
    	setFocusable(true);

    	addMouseListener(this);
    	addKeyListener(this);
    }
    
    
    protected void distributeValueChangedEvent() {
        for (SelectionListener iter : mSelectionListeners) {
            SelectionEvent event = new SelectionEvent(this);
            iter.valueChanged(event);
        }
    }

	@Override
	public void keyPressed(KeyEvent keyEvent) throws GuichanException {
        Key key = keyEvent.getKey();

        if (key.getValue() == Key.ENTER || key.getValue() == Key.SPACE)  {
            distributeActionEvent();
            keyEvent.consume();
        } else if (key.getValue() == Key.UP) {
            setSelected(mSelected - 1);

            if (mSelected == -1) {
                if (mWrappingEnabled) {
                    setSelected(getListModel().getNumberOfElements() - 1);
                } else {
                    setSelected(0);
                }
            }
            
            keyEvent.consume();
        } else if (key.getValue() == Key.DOWN) {
            if (mWrappingEnabled
                && getSelected() == getListModel().getNumberOfElements() - 1) {
                setSelected(0);
            } else {
                setSelected(getSelected() + 1);
            }
            
            keyEvent.consume();
        } else if (key.getValue() == Key.HOME) {
            setSelected(0);
            keyEvent.consume();
        } else if (key.getValue() == Key.END) {
            setSelected(getListModel().getNumberOfElements() - 1);
            keyEvent.consume();
        }
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
		
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
        if (mouseEvent.getButton() == MouseEvent.LEFT) {
            setSelected(mouseEvent.getY() / getRowHeight());
            distributeActionEvent();
        }
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMovedUp(MouseEvent mouseEvent) throws GuichanException {
        if (isFocused()) {
            if (getSelected() > 0 ) {
                setSelected(getSelected() - 1);
            }

            mouseEvent.consume();
        }
	}

	@Override
	public void mouseWheelMovedDown(MouseEvent mouseEvent) throws GuichanException {
        if (isFocused()) {
            setSelected(getSelected() + 1);

            mouseEvent.consume();
        }
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
		mouseEvent.consume();
	}

	@Override
	public void draw(Graphics graphics) throws GuichanException {
        graphics.setColor(getBackgroundColor());
        graphics.fillRectangle(new Rectangle(0, 0, getWidth(), getHeight()));

        if (mListModel == null) {
            return;
        }

        graphics.setColor(getForegroundColor());
        graphics.setFont(getFont());
         
        // Check the current clip area so we don't draw unnecessary items
        // that are not visible.
        final ClipRectangle currentClipArea = graphics.getCurrentClipArea();
        int rowHeight = getRowHeight();
        
		// Calculate the number of rows to draw by checking the clip area.
		// The addition of two makes covers a partial visible row at the top
		// and a partial visible row at the bottom.
		int numberOfRows = currentClipArea.height / rowHeight + 2;

        if (numberOfRows > mListModel.getNumberOfElements()) {
            numberOfRows = mListModel.getNumberOfElements();
        }

		// Calculate which row to start drawing. If the list box 
		// has a negative y coordinate value we should check if
		// we should drop rows in the begining of the list as
		// they might not be visible. A negative y value is very
		// common if the list box for instance resides in a scroll
		// area and the user has scrolled the list box downwards.
		int startRow;    	
		if (getY() < 0) {
			startRow = -1 * (getY() / rowHeight);
		} else {
			startRow = 0;
		}

		int i;
		// The y coordinate where we start to draw the text is
		// simply the y coordinate multiplied with the font height.
		int y = rowHeight * startRow;
        for (i = startRow; i < startRow + numberOfRows; ++i) {
            if (i == mSelected) {
                graphics.setColor(getSelectionColor());
                graphics.fillRectangle(new Rectangle(0, y, getWidth(), rowHeight));
                graphics.setColor(getForegroundColor());
            }
			
            if (i >= 0 && i < mListModel.getNumberOfElements()) {//FIXME:
				// If the row height is greater than the font height we
				// draw the text with a center vertical alignment.
				if (rowHeight > getFont().getHeight()) {
					graphics.drawText(mListModel.getElementAt(i), 1, y + rowHeight / 2 - getFont().getHeight() / 2);
				} else {
					graphics.drawText(mListModel.getElementAt(i), 1, y);
				}
            }

            y += rowHeight;
        }
	}
	
    public void logic() throws GuichanException {
        adjustSize();
    }

    public int getSelected() {
        return mSelected;
    }

    public void setSelected(int selected) throws GuichanException {
        if (mListModel == null) {
            mSelected = -1;
        } else {
            if (selected < 0) {
                mSelected = -1;
            } else if (selected >= mListModel.getNumberOfElements()) {
                mSelected = mListModel.getNumberOfElements() - 1;
            } else {
                mSelected = selected;
            }
        }
        
        Rectangle scroll = new Rectangle();

        if (mSelected < 0) {
            scroll.y = 0;
        } else {
            scroll.y = getRowHeight() * mSelected;
        }

        scroll.height = getRowHeight();
        showPart(scroll);

        distributeValueChangedEvent();
    }
    
    public void setListModel(ListModel listModel) throws GuichanException {
        mSelected = -1;
        mListModel = listModel;
        adjustSize();
    }

    public ListModel getListModel() {
        return mListModel;
    }

    public void adjustSize() throws GuichanException {
        if (mListModel != null) {
            setHeight(getRowHeight() * mListModel.getNumberOfElements());
        }
    }

    public boolean isWrappingEnabled() {
        return mWrappingEnabled;
    }

    public void setWrappingEnabled(boolean wrappingEnabled) {
        mWrappingEnabled = wrappingEnabled;
    }
        
    public void addSelectionListener(SelectionListener selectionListener) {
        mSelectionListeners.add(selectionListener);
    }
   
    public void removeSelectionListener(SelectionListener selectionListener) {
        mSelectionListeners.remove(selectionListener);
    }

	public int getRowHeight() {
		return getFont().getHeight();
	}
}
