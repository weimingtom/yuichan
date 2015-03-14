package com.iteye.weimingtom.guichan.widget;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.MouseEvent;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.MouseListener;
import com.iteye.weimingtom.guichan.platform.Graphics;

public class Window extends Container implements MouseListener {
    protected String mCaption;
    protected Graphics.Alignment mAlignment;
    protected int mPadding;
    protected int mTitleBarHeight;
    protected boolean mMovable;
    protected boolean mOpaque;
    protected int mDragOffsetX;
    protected int mDragOffsetY;
    protected boolean mMoved;
    
    public Window() {
    	mMoved = false;
    	
    	setFrameSize(1);
		setPadding(2);
		setTitleBarHeight(16);
		setAlignment(Graphics.Alignment.CENTER);
		addMouseListener(this);
		setMovable(true);
		setOpaque(true);
	}

    public Window(String caption) {
    	mMoved = false;
    	
		setCaption(caption);
		setFrameSize(1);
		setPadding(2);
		setTitleBarHeight(16);
		setAlignment(Graphics.Alignment.CENTER);
		addMouseListener(this);
		setMovable(true);
		setOpaque(true);
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
        if (mouseEvent.getSource() != this) {
            return;
        }
        
        if (getParent() != null) {
            getParent().moveToTop(this);
        }

        mDragOffsetX = mouseEvent.getX();
        mDragOffsetY = mouseEvent.getY();

        mMoved = mouseEvent.getY() <= (int)mTitleBarHeight;
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		mMoved = false;
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMovedUp(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMovedDown(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) throws GuichanException {
        if (mouseEvent.isConsumed() || mouseEvent.getSource() != this) {
            return;
        }

        if (isMovable() && mMoved) {
            setPosition(mouseEvent.getX() - mDragOffsetX + getX(),
                        mouseEvent.getY() - mDragOffsetY + getY());
        }

        mouseEvent.consume();
	}
	
    public void setPadding(int padding) {
        mPadding = padding;
    }

    public int getPadding() {
        return mPadding;
    }

    public void setTitleBarHeight(int height) {
        mTitleBarHeight = height;
    }

    public int getTitleBarHeight() {
        return mTitleBarHeight;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setAlignment(Graphics.Alignment alignment) {
        mAlignment = alignment;
    }

    public Graphics.Alignment getAlignment() {
        return mAlignment;
    }

    @Override
    public void draw(Graphics graphics) throws GuichanException {
//    	System.out.println("draw peek: " + graphics.mClipStack.peek());
        
        Color faceColor = getBaseColor();
        Color highlightColor, shadowColor;
        int alpha = getBaseColor().a;
        highlightColor = faceColor.plus(new Color(0x303030));
        highlightColor.a = alpha;
        shadowColor = faceColor.minus(new Color(0x303030));
        shadowColor.a = alpha;

        Rectangle d = getChildrenArea();

        // Fill the background around the content
        graphics.setColor(faceColor);
        // Fill top
        graphics.fillRectangle(new Rectangle(0,0,getWidth(),d.y - 1));
        // Fill left
        graphics.fillRectangle(new Rectangle(0,d.y - 1, d.x - 1, getHeight() - d.y + 1));
        // Fill right
        graphics.fillRectangle(new Rectangle(d.x + d.width + 1,
                                          d.y - 1,
                                          getWidth() - d.x - d.width - 1,
                                          getHeight() - d.y + 1));
        // Fill bottom
        graphics.fillRectangle(new Rectangle(d.x - 1,
                                          d.y + d.height + 1,
                                          d.width + 2,
                                          getHeight() - d.height - d.y - 1));

        if (isOpaque()) {
            graphics.fillRectangle(d);
        }

        // Construct a rectangle one pixel bigger than the content
        d.x -= 1;
        d.y -= 1;
        d.width += 2;
        d.height += 2;

        // Draw a border around the content
        graphics.setColor(shadowColor);
        // Top line
        graphics.drawLine(d.x,
                           d.y,
                           d.x + d.width - 2,
                           d.y);

        // Left line
        graphics.drawLine(d.x,
                           d.y + 1,
                           d.x,
                           d.y + d.height - 1);

        graphics.setColor(highlightColor);
        // Right line
        graphics.drawLine(d.x + d.width - 1,
                           d.y,
                           d.x + d.width - 1,
                           d.y + d.height - 2);
        // Bottom line
        graphics.drawLine(d.x + 1,
                           d.y + d.height - 1,
                           d.x + d.width - 1,
                           d.y + d.height - 1);

        drawChildren(graphics);

        int textX;
        int textY;

        textY = ((int)getTitleBarHeight() - getFont().getHeight()) / 2;

        if (getAlignment() == Graphics.Alignment.LEFT) {
        	textX = 4;
        } else if (getAlignment() == Graphics.Alignment.CENTER) {
            textX = getWidth() / 2;
        } else if (getAlignment() == Graphics.Alignment.RIGHT) {
            textX = getWidth() - 4;
        } else {
        	throw new GuichanException("Unknown alignment.");
        }

        graphics.setColor(getForegroundColor());
        graphics.setFont(getFont());
        graphics.pushClipArea(new Rectangle(0, 0, getWidth(), getTitleBarHeight() - 1));
//    	System.out.println("drawText peek: " + graphics.mClipStack.peek());
        graphics.drawText(getCaption(), textX, textY, getAlignment());
        graphics.popClipArea();
    }
    
    @Override
    public Rectangle getChildrenArea() {
        return new Rectangle(getPadding(),
                         getTitleBarHeight(),
                         getWidth() - getPadding() * 2,
                         getHeight() - getPadding() - getTitleBarHeight());
    }

    public void setMovable(boolean movable) {
        mMovable = movable;
    }

    public boolean isMovable() {
        return mMovable;
    }

    @Override
    public void setOpaque(boolean opaque) {
        mOpaque = opaque;
    }

    @Override
    public boolean isOpaque() {
        return mOpaque;
    }
    
    public void resizeToContent() throws GuichanException {
        int w = 0, h = 0;
        for (Widget it : mWidgets) {
            if (it.getX() + it.getWidth() > w) {
                w = it.getX() + it.getWidth();
            }

            if (it.getY() + it.getHeight() > h) {
                h = it.getY() + it.getHeight();
            }
        }

        setSize(w + 2* getPadding(), h + getPadding() + getTitleBarHeight());
    }

//    //FIXME: for test
//	@Override
//	public void drawFrame(Graphics graphics) {
//		super.drawFrame(graphics);
//    	System.out.println("drawFrame peek: " + graphics.mClipStack.peek());
//    }
}
