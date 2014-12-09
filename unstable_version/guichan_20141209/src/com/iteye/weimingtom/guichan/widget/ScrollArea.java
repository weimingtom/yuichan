package com.iteye.weimingtom.guichan.widget;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.MouseEvent;
import com.iteye.weimingtom.guichan.gui.BasicContainer;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.MouseListener;
import com.iteye.weimingtom.guichan.platform.Graphics;

public class ScrollArea extends BasicContainer implements MouseListener {
    public enum ScrollPolicy {
        SHOW_ALWAYS,
        SHOW_NEVER,
        SHOW_AUTO
    };
    
    protected int mVScroll;
    protected int mHScroll;
    protected int mScrollbarWidth;
    protected ScrollPolicy mHPolicy;
    protected ScrollPolicy mVPolicy;
    protected boolean mVBarVisible;
    protected boolean mHBarVisible;
    protected boolean mUpButtonPressed;
    protected boolean mDownButtonPressed;
    protected boolean mLeftButtonPressed;
    protected boolean mRightButtonPressed;
    protected int mUpButtonScrollAmount;
    protected int mDownButtonScrollAmount;
    protected int mLeftButtonScrollAmount;
    protected int mRightButtonScrollAmount;
    protected boolean mIsVerticalMarkerDragged;
    protected boolean mIsHorizontalMarkerDragged;
    protected int mHorizontalMarkerDragOffset;
    protected int mVerticalMarkerDragOffset;
    protected boolean mOpaque;
    
    
    public ScrollArea() {
        mVScroll = 0;
        mHScroll = 0;
        mHPolicy = ScrollPolicy.SHOW_AUTO;
        mVPolicy = ScrollPolicy.SHOW_AUTO;
        mScrollbarWidth = 12;
        mUpButtonPressed = false;
        mDownButtonPressed = false;
        mLeftButtonPressed = false;
        mRightButtonPressed = false;
        mUpButtonScrollAmount = 10;
        mDownButtonScrollAmount = 10;
        mLeftButtonScrollAmount = 10;
        mRightButtonScrollAmount = 10;
        mIsVerticalMarkerDragged = false;
        mIsHorizontalMarkerDragged =false;
        mOpaque = true;

        addMouseListener(this);
    }

    public ScrollArea(Widget content) throws GuichanException {
        mVScroll = 0;
        mHScroll = 0;
        mHPolicy = ScrollPolicy.SHOW_AUTO;
        mVPolicy = ScrollPolicy.SHOW_AUTO;
        mScrollbarWidth = 12;
        mUpButtonPressed = false;
        mDownButtonPressed = false;
        mLeftButtonPressed = false;
        mRightButtonPressed = false;
        mUpButtonScrollAmount = 10;
        mDownButtonScrollAmount = 10;
        mLeftButtonScrollAmount = 10;
        mRightButtonScrollAmount = 10;
        mIsVerticalMarkerDragged = false;
        mIsHorizontalMarkerDragged =false;
        mOpaque = true;

        setContent(content);
        addMouseListener(this);
    }

    public ScrollArea(Widget content,
                           ScrollPolicy hPolicy,
                           ScrollPolicy vPolicy) throws GuichanException {
        mVScroll = 0;
        mHScroll = 0;
        mHPolicy = hPolicy;
        mVPolicy = vPolicy;
        mScrollbarWidth = 12;
        mUpButtonPressed = false;
        mDownButtonPressed = false;
        mLeftButtonPressed = false;
        mRightButtonPressed = false;
        mUpButtonScrollAmount = 10;
        mDownButtonScrollAmount = 10;
        mLeftButtonScrollAmount = 10;
        mRightButtonScrollAmount = 10;
        mIsVerticalMarkerDragged = false;
        mIsHorizontalMarkerDragged =false;
        mOpaque = true;

        setContent(content);
        addMouseListener(this);
    }
    
    protected void drawBackground(Graphics graphics) {
        if (isOpaque()) {
            graphics.setColor(getBackgroundColor());
            graphics.fillRectangle(getChildrenArea());
        }
    }
    
    protected void drawUpButton(Graphics graphics) throws GuichanException {
        Rectangle dim = getUpButtonDimension();
        graphics.pushClipArea(dim);

        Color highlightColor;
        Color shadowColor;
        Color faceColor;
        int offset;
        int alpha = getBaseColor().a;

        if (mUpButtonPressed) {
            faceColor = getBaseColor().minus(new Color(0x303030));
            faceColor.a = alpha;
            highlightColor = faceColor.minus(new Color(0x303030));
            highlightColor.a = alpha;
            shadowColor = getBaseColor();
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

        graphics.setColor(faceColor);
        graphics.fillRectangle(new Rectangle(0, 0, dim.width, dim.height));

        graphics.setColor(highlightColor);
        graphics.drawLine(0, 0, dim.width - 1, 0);
        graphics.drawLine(0, 1, 0, dim.height - 1);

        graphics.setColor(shadowColor);
        graphics.drawLine(dim.width - 1, 0, dim.width - 1, dim.height - 1);
        graphics.drawLine(1, dim.height - 1, dim.width - 1, dim.height - 1);

        graphics.setColor(getForegroundColor());

        int i;
        int w = dim.height / 2;
        int h = w / 2 + 2;
        for (i = 0; i < w / 2; ++i) {
            graphics.drawLine(w - i + offset,
                               i + h + offset,
                               w + i + offset,
                               i + h + offset);
        }

        graphics.popClipArea();
    }
    
    protected void drawDownButton(Graphics graphics) throws GuichanException {
        Rectangle dim = getDownButtonDimension();
        graphics.pushClipArea(dim);

        Color highlightColor;
        Color shadowColor;
        Color faceColor;
        int offset;
        int alpha = getBaseColor().a;

        if (mDownButtonPressed) {
            faceColor = getBaseColor().minus(new Color(0x303030));
            faceColor.a = alpha;
            highlightColor = faceColor.minus(new Color(0x303030));
            highlightColor.a = alpha;
            shadowColor = getBaseColor();
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

        graphics.setColor(faceColor);
        graphics.fillRectangle(new Rectangle(0, 0, dim.width, dim.height));

        graphics.setColor(highlightColor);
        graphics.drawLine(0, 0, dim.width - 1, 0);
        graphics.drawLine(0, 1, 0, dim.height - 1);

        graphics.setColor(shadowColor);
        graphics.drawLine(dim.width - 1, 0, dim.width - 1, dim.height - 1);
        graphics.drawLine(1, dim.height - 1, dim.width - 1, dim.height - 1);

        graphics.setColor(getForegroundColor());

        int i;
        int w = dim.height / 2;
        int h = w + 1;
        for (i = 0; i < w / 2; ++i) {
            graphics.drawLine(w - i + offset,
                               -i + h + offset,
                               w + i + offset,
                               -i + h + offset);
        }

        graphics.popClipArea();
    }
    
    protected void drawLeftButton(Graphics graphics) throws GuichanException {
        Rectangle dim = getLeftButtonDimension();
        graphics.pushClipArea(dim);

        Color highlightColor;
        Color shadowColor;
        Color faceColor;
        int offset;
        int alpha = getBaseColor().a;

        if (mLeftButtonPressed) {
            faceColor = getBaseColor().minus(new Color(0x303030));
            faceColor.a = alpha;
            highlightColor = faceColor.minus(new Color(0x303030));
            highlightColor.a = alpha;
            shadowColor = getBaseColor();
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

        graphics.setColor(faceColor);
        graphics.fillRectangle(new Rectangle(0, 0, dim.width, dim.height));

        graphics.setColor(highlightColor);
        graphics.drawLine(0, 0, dim.width - 1, 0);
        graphics.drawLine(0, 1, 0, dim.height - 1);

        graphics.setColor(shadowColor);
        graphics.drawLine(dim.width - 1, 0, dim.width - 1, dim.height - 1);
        graphics.drawLine(1, dim.height - 1, dim.width - 1, dim.height - 1);

        graphics.setColor(getForegroundColor());

        int i;
        int w = dim.width / 2;
        int h = w - 2;
        for (i = 0; i < w / 2; ++i) {
            graphics.drawLine(i + h + offset,
                               w - i + offset,
                               i + h + offset,
                               w + i + offset);
        }

        graphics.popClipArea();
    }
    
    protected void drawRightButton(Graphics graphics) throws GuichanException {
        Rectangle dim = getRightButtonDimension();
        graphics.pushClipArea(dim);

        Color highlightColor;
        Color shadowColor;
        Color faceColor;
        int offset;
        int alpha = getBaseColor().a;

        if (mRightButtonPressed) {
            faceColor = getBaseColor().minus(new Color(0x303030));
            faceColor.a = alpha;
            highlightColor = faceColor.minus(new Color(0x303030));
            highlightColor.a = alpha;
            shadowColor = getBaseColor();
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

        graphics.setColor(faceColor);
        graphics.fillRectangle(new Rectangle(0, 0, dim.width, dim.height));

        graphics.setColor(highlightColor);
        graphics.drawLine(0, 0, dim.width - 1, 0);
        graphics.drawLine(0, 1, 0, dim.height - 1);

        graphics.setColor(shadowColor);
        graphics.drawLine(dim.width - 1, 0, dim.width - 1, dim.height - 1);
        graphics.drawLine(1, dim.height - 1, dim.width - 1, dim.height - 1);

        graphics.setColor(getForegroundColor());

        int i;
        int w = dim.width / 2;
        int h = w + 1;
        for (i = 0; i < w / 2; ++i) {
            graphics.drawLine(-i + h + offset,
                               w - i + offset,
                               -i + h + offset,
                               w + i + offset);
        }

        graphics.popClipArea();
    }
    
    protected void drawVBar(Graphics graphics) throws GuichanException {
        Rectangle dim = getVerticalBarDimension();

        graphics.pushClipArea(dim);

        int alpha = getBaseColor().a;
        Color trackColor = getBaseColor().minus(new Color(0x101010));
        trackColor.a = alpha;
        Color shadowColor = getBaseColor().minus(new Color(0x303030));
        shadowColor.a = alpha;

        graphics.setColor(trackColor);
        graphics.fillRectangle(new Rectangle(0, 0, dim.width, dim.height));

        graphics.setColor(shadowColor);
        graphics.drawLine(0, 0, 0, dim.height);

        graphics.popClipArea();
    }
    
    protected void drawHBar(Graphics graphics) throws GuichanException {
        Rectangle dim = getHorizontalBarDimension();

        graphics.pushClipArea(dim);

        int alpha = getBaseColor().a;
        Color trackColor = getBaseColor().minus(new Color(0x101010));
        trackColor.a = alpha;
        Color shadowColor = getBaseColor().minus(new Color(0x303030));
        shadowColor.a = alpha;

        graphics.setColor(trackColor);
        graphics.fillRectangle(new Rectangle(0, 0, dim.width, dim.height));

        graphics.setColor(shadowColor);
        graphics.drawLine(0, 0, dim.width, 0);

        graphics.popClipArea();
    }
    
    protected void drawVMarker(Graphics graphics) throws GuichanException {
        Rectangle dim = getVerticalMarkerDimension();
        graphics.pushClipArea(dim);

        int alpha = getBaseColor().a;
        Color faceColor = getBaseColor();
        faceColor.a = alpha;
        Color highlightColor = faceColor.plus(new Color(0x303030));
        highlightColor.a = alpha;
        Color shadowColor = faceColor.minus(new Color(0x303030));
        shadowColor.a = alpha;

        graphics.setColor(faceColor);
        graphics.fillRectangle(new Rectangle(1, 1, dim.width - 1, dim.height - 1));

        graphics.setColor(highlightColor);
        graphics.drawLine(0, 0, dim.width - 1, 0);
        graphics.drawLine(0, 1, 0, dim.height - 1);

        graphics.setColor(shadowColor);
        graphics.drawLine(1, dim.height - 1, dim.width - 1, dim.height - 1);
        graphics.drawLine(dim.width - 1, 0, dim.width - 1, dim.height - 1);

        graphics.popClipArea();
    }
    
    protected void drawHMarker(Graphics graphics) throws GuichanException {
        Rectangle dim = getHorizontalMarkerDimension();
        graphics.pushClipArea(dim);

        int alpha = getBaseColor().a;
        Color faceColor = getBaseColor();
        faceColor.a = alpha;
        Color highlightColor = faceColor.plus(new Color(0x303030));
        highlightColor.a = alpha;
        Color shadowColor = faceColor.minus(new Color(0x303030));
        shadowColor.a = alpha;

        graphics.setColor(faceColor);
        graphics.fillRectangle(new Rectangle(1, 1, dim.width - 1, dim.height - 1));

        graphics.setColor(highlightColor);
        graphics.drawLine(0, 0, dim.width - 1, 0);
        graphics.drawLine(0, 1, 0, dim.height - 1);

        graphics.setColor(shadowColor);
        graphics.drawLine(1, dim.height - 1, dim.width - 1, dim.height - 1);
        graphics.drawLine(dim.width - 1, 0, dim.width - 1, dim.height - 1);

        graphics.popClipArea();
    }
    
    protected void checkPolicies() throws GuichanException {
        int w = getWidth();
        int h = getHeight();

        mHBarVisible = false;
        mVBarVisible = false;
        
        if (getContent() == null) {
            mHBarVisible = (mHPolicy == ScrollPolicy.SHOW_ALWAYS);
            mVBarVisible = (mVPolicy == ScrollPolicy.SHOW_ALWAYS);
            return;
        }

        if (mHPolicy == ScrollPolicy.SHOW_AUTO &&
            mVPolicy == ScrollPolicy.SHOW_AUTO) {
            if (getContent().getWidth() <= w
                && getContent().getHeight() <= h) {
                mHBarVisible = false;
                mVBarVisible = false;
            }

            if (getContent().getWidth() > w) {
                mHBarVisible = true;
            }

            if ((getContent().getHeight() > h)
                || (mHBarVisible && getContent().getHeight() > h - mScrollbarWidth)) {
                mVBarVisible = true;
            }

            if (mVBarVisible && getContent().getWidth() > w - mScrollbarWidth) {
                mHBarVisible = true;
            }

            return;
        }

        if (mHPolicy == ScrollPolicy.SHOW_NEVER) {
              mHBarVisible = false;
    	} else if (mHPolicy == ScrollPolicy.SHOW_ALWAYS) {
            mHBarVisible = true;
    	} else if (mHPolicy == ScrollPolicy.SHOW_AUTO) {
            if (mVPolicy == ScrollPolicy.SHOW_NEVER) {
            	mHBarVisible = getContent().getWidth() > w;
            } else {
            	mHBarVisible = getContent().getWidth() > w - mScrollbarWidth;
            }
    	} else {
        	throw new GuichanException("Horizontal scroll policy invalid.");
        }

        if (mVPolicy == ScrollPolicy.SHOW_NEVER) {
            mVBarVisible = false;
        } else if (mVPolicy == ScrollPolicy.SHOW_ALWAYS) {
        	mVBarVisible = true;
        } else if (mVPolicy == ScrollPolicy.SHOW_AUTO) {
            if (mHPolicy == ScrollPolicy.SHOW_NEVER) {
            	mVBarVisible = getContent().getHeight() > h;
            } else {
            	mVBarVisible = getContent().getHeight() > h - mScrollbarWidth;
            }
        } else {
        	throw new GuichanException("Vertical scroll policy invalid.");
        }
    }
    
    protected Rectangle getUpButtonDimension() {
        if (!mVBarVisible) {
            return new Rectangle(0, 0, 0, 0);
        }
        return new Rectangle(getWidth() - mScrollbarWidth,
                         0,
                         mScrollbarWidth,
                         mScrollbarWidth);
    }
    
    protected Rectangle getDownButtonDimension() {
        if (!mVBarVisible) {
            return new Rectangle(0, 0, 0, 0);
        }

        if (mVBarVisible && mHBarVisible) {
            return new Rectangle(getWidth() - mScrollbarWidth,
                             getHeight() - mScrollbarWidth*2,
                             mScrollbarWidth,
                             mScrollbarWidth);
        }

        return new Rectangle(getWidth() - mScrollbarWidth,
                         getHeight() - mScrollbarWidth,
                         mScrollbarWidth,
                         mScrollbarWidth);
    }
    
    protected Rectangle getLeftButtonDimension() {
        if (!mHBarVisible) {
            return new Rectangle(0, 0, 0, 0);
        }
        return new Rectangle(0,
                         getHeight() - mScrollbarWidth,
                         mScrollbarWidth,
                         mScrollbarWidth);
    }
    
    protected Rectangle getRightButtonDimension() {
        if (!mHBarVisible) {
            return new Rectangle(0, 0, 0, 0);
        }

        if (mVBarVisible && mHBarVisible) {
            return new Rectangle(getWidth() - mScrollbarWidth*2,
                             getHeight() - mScrollbarWidth,
                             mScrollbarWidth,
                             mScrollbarWidth);
        }
        return new Rectangle(getWidth() - mScrollbarWidth,
                         getHeight() - mScrollbarWidth,
                         mScrollbarWidth,
                         mScrollbarWidth);
    }
    
    protected Rectangle getVerticalBarDimension() {
        if (!mVBarVisible) {
            return new Rectangle(0, 0, 0, 0);
        }

        if (mHBarVisible) {
            return new Rectangle(getWidth() - mScrollbarWidth,
                             getUpButtonDimension().height,
                             mScrollbarWidth,
                             getHeight()
                             - getUpButtonDimension().height
                             - getDownButtonDimension().height
                             - mScrollbarWidth);
        }
        return new Rectangle(getWidth() - mScrollbarWidth,
                         getUpButtonDimension().height,
                         mScrollbarWidth,
                         getHeight()
                         - getUpButtonDimension().height
                         - getDownButtonDimension().height);
    }
    
    protected Rectangle getHorizontalBarDimension() {
        if (!mHBarVisible) {
            return new Rectangle(0, 0, 0, 0);
        }

        if (mVBarVisible) {
            return new Rectangle(getLeftButtonDimension().width,
                             getHeight() - mScrollbarWidth,
                             getWidth()
                             - getLeftButtonDimension().width
                             - getRightButtonDimension().width
                             - mScrollbarWidth,
                             mScrollbarWidth);
        }
        return new Rectangle(getLeftButtonDimension().width,
                         getHeight() - mScrollbarWidth,
                         getWidth()
                         - getLeftButtonDimension().width
                         - getRightButtonDimension().width,
                         mScrollbarWidth);
    }
    
    protected Rectangle getVerticalMarkerDimension() throws GuichanException {
        if (!mVBarVisible) {
            return new Rectangle(0, 0, 0, 0);
        }

        int length, pos;
        Rectangle barDim = getVerticalBarDimension();

        if (getContent() != null && getContent().getHeight() != 0) {
            length = (barDim.height * getChildrenArea().height)
                / getContent().getHeight();
        } else {
            length = barDim.height;
        }

        if (length < mScrollbarWidth)
        {
            length = mScrollbarWidth;
        }

        if (length > barDim.height) {
            length = barDim.height;
        }

        if (getVerticalMaxScroll() != 0) {
            pos = ((barDim.height - length) * getVerticalScrollAmount())
                / getVerticalMaxScroll();
        } else {
            pos = 0;
        }

        return new Rectangle(barDim.x, barDim.y + pos, mScrollbarWidth, length);
    }
    
    protected Rectangle getHorizontalMarkerDimension() throws GuichanException {
        if (!mHBarVisible) {
            return new Rectangle(0, 0, 0, 0);
        }

        int length, pos;
        Rectangle barDim = getHorizontalBarDimension();

        if (getContent() != null && getContent().getWidth() != 0) {
            length = (barDim.width * getChildrenArea().width)
                / getContent().getWidth();
        } else {
            length = barDim.width;
        }

        if (length < mScrollbarWidth)
        {
            length = mScrollbarWidth;
        }

        if (length > barDim.width)
        {
            length = barDim.width;
        }

        if (getHorizontalMaxScroll() != 0) {
            pos = ((barDim.width - length) * getHorizontalScrollAmount())
                / getHorizontalMaxScroll();
        } else {
            pos = 0;
        }

        return new Rectangle(barDim.x + pos, barDim.y, length, mScrollbarWidth);
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
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        if (getUpButtonDimension().isPointInRect(x, y)) {
            setVerticalScrollAmount(getVerticalScrollAmount()
                                    - mUpButtonScrollAmount);
            mUpButtonPressed = true;
        } else if (getDownButtonDimension().isPointInRect(x, y)) {
            setVerticalScrollAmount(getVerticalScrollAmount()
                                    + mDownButtonScrollAmount);
            mDownButtonPressed = true;
        } else if (getLeftButtonDimension().isPointInRect(x, y)) {
            setHorizontalScrollAmount(getHorizontalScrollAmount()
                                      - mLeftButtonScrollAmount);
            mLeftButtonPressed = true;
        } else if (getRightButtonDimension().isPointInRect(x, y)) {
            setHorizontalScrollAmount(getHorizontalScrollAmount()
                                      + mRightButtonScrollAmount);
            mRightButtonPressed = true;
        } else if (getVerticalMarkerDimension().isPointInRect(x, y)) {
            mIsHorizontalMarkerDragged = false;
            mIsVerticalMarkerDragged = true;

            mVerticalMarkerDragOffset = y - getVerticalMarkerDimension().y;
        } else if (getVerticalBarDimension().isPointInRect(x,y)) {
            if (y < getVerticalMarkerDimension().y) {
                setVerticalScrollAmount(getVerticalScrollAmount()
                                        - (int)(getChildrenArea().height * 0.95));
            } else {
                setVerticalScrollAmount(getVerticalScrollAmount()
                                        + (int)(getChildrenArea().height * 0.95));
            }
        } else if (getHorizontalMarkerDimension().isPointInRect(x, y)) {
            mIsHorizontalMarkerDragged = true;
            mIsVerticalMarkerDragged = false;

            mHorizontalMarkerDragOffset = x - getHorizontalMarkerDimension().x;
        } else if (getHorizontalBarDimension().isPointInRect(x,y)) {
            if (x < getHorizontalMarkerDimension().x) {
                setHorizontalScrollAmount(getHorizontalScrollAmount()
                                          - (int)(getChildrenArea().width * 0.95));
            } else {
                setHorizontalScrollAmount(getHorizontalScrollAmount()
                                          + (int)(getChildrenArea().width * 0.95));
            }
        }
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) throws GuichanException {
        mUpButtonPressed = false;
        mDownButtonPressed = false;
        mLeftButtonPressed = false;
        mRightButtonPressed = false;
        mIsHorizontalMarkerDragged = false;
        mIsVerticalMarkerDragged = false;

        mouseEvent.consume();
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) throws GuichanException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMovedUp(MouseEvent mouseEvent)
			throws GuichanException {
        if (mouseEvent.isConsumed()) {
            return;
        }
        setVerticalScrollAmount(getVerticalScrollAmount() - getChildrenArea().height / 8);
        mouseEvent.consume();
	}

	@Override
	public void mouseWheelMovedDown(MouseEvent mouseEvent)
			throws GuichanException {
        if (mouseEvent.isConsumed()) {
            return;
        }
        setVerticalScrollAmount(getVerticalScrollAmount() + getChildrenArea().height / 8);
        mouseEvent.consume();
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) throws GuichanException {
        if (mIsVerticalMarkerDragged) {
            int pos = mouseEvent.getY() - getVerticalBarDimension().y  - mVerticalMarkerDragOffset;
            int length = getVerticalMarkerDimension().height;

            Rectangle barDim = getVerticalBarDimension();

            if ((barDim.height - length) > 0) {
                setVerticalScrollAmount((getVerticalMaxScroll() * pos)
                                         / (barDim.height - length));
            } else {
                setVerticalScrollAmount(0);
            }
        }

        if (mIsHorizontalMarkerDragged) {
            int pos = mouseEvent.getX() - getHorizontalBarDimension().x  - mHorizontalMarkerDragOffset;
            int length = getHorizontalMarkerDimension().width;

            Rectangle barDim = getHorizontalBarDimension();

            if ((barDim.width - length) > 0) {
                setHorizontalScrollAmount((getHorizontalMaxScroll() * pos)
                                          / (barDim.width - length));
            } else {
                setHorizontalScrollAmount(0);
            }
        }

        mouseEvent.consume();
	}

	@Override
	public void draw(Graphics graphics) throws GuichanException {
        drawBackground(graphics);

        if (mVBarVisible) {
            drawUpButton(graphics);
            drawDownButton(graphics);
            drawVBar(graphics);
            drawVMarker(graphics);
        }

        if (mHBarVisible) {
            drawLeftButton(graphics);
            drawRightButton(graphics);
            drawHBar(graphics);
            drawHMarker(graphics);
        }

        if (mHBarVisible && mVBarVisible) {
            graphics.setColor(getBaseColor());
            graphics.fillRectangle(new Rectangle(getWidth() - mScrollbarWidth,
                                              getHeight() - mScrollbarWidth,
                                              mScrollbarWidth,
                                              mScrollbarWidth));
        }

        drawChildren(graphics);
	}

    public void setContent(Widget widget) throws GuichanException {
        if (widget != null) {
            clear();
            add(widget);
            widget.setPosition(0,0);
        } else {
            clear();
        }

        checkPolicies();
    }

    public Widget getContent() {
        if (mWidgets.size() > 0) {
            return mWidgets.getFirst();
        }
        return null;
    }
    
    public void setHorizontalScrollPolicy(ScrollPolicy hPolicy) throws GuichanException {
        mHPolicy = hPolicy;
        checkPolicies();
    }

    public ScrollPolicy getHorizontalScrollPolicy() {
        return mHPolicy;
    }

    public void setVerticalScrollPolicy(ScrollPolicy vPolicy) throws GuichanException {
        mVPolicy = vPolicy;
        checkPolicies();
    }

    public ScrollPolicy getVerticalScrollPolicy() {
        return mVPolicy;
    }

    public void setScrollPolicy(ScrollPolicy hPolicy, ScrollPolicy vPolicy) throws GuichanException {
        mHPolicy = hPolicy;
        mVPolicy = vPolicy;
        checkPolicies();
    }

    public void setVerticalScrollAmount(int vScroll) throws GuichanException {
        int max = getVerticalMaxScroll();

        mVScroll = vScroll;

        if (vScroll > max)
        {
            mVScroll = max;
        }

        if (vScroll < 0)
        {
            mVScroll = 0;
        }
    }

    public int getVerticalScrollAmount() {
        return mVScroll;
    }

    public void setHorizontalScrollAmount(int hScroll) throws GuichanException {
        int max = getHorizontalMaxScroll();

        mHScroll = hScroll;

        if (hScroll > max)
        {
            mHScroll = max;
        }
        else if (hScroll < 0)
        {
            mHScroll = 0;
        }
    }

    public int getHorizontalScrollAmount() {
        return mHScroll;
    }

    public void setScrollAmount(int hScroll, int vScroll) throws GuichanException {
        setHorizontalScrollAmount(hScroll);
        setVerticalScrollAmount(vScroll);
    }

    public int getHorizontalMaxScroll() throws GuichanException {
        checkPolicies();

        if (getContent() == null) {
            return 0;
        }

        int value = getContent().getWidth() - getChildrenArea().width +
            2 * getContent().getFrameSize();

        if (value < 0) {
            return 0;
        }

        return value;
    }

    public int getVerticalMaxScroll() throws GuichanException {
        checkPolicies();

        if (getContent() == null) {
            return 0;
        }

        int value;

        value = getContent().getHeight() - getChildrenArea().height +
            2 * getContent().getFrameSize();

        if (value < 0) {
            return 0;
        }

        return value;
    }

    public void setScrollbarWidth(int width) throws GuichanException {
        if (width > 0) {
            mScrollbarWidth = width;
        } else {
            throw new GuichanException("Width should be greater then 0.");
        }
    }

    public int getScrollbarWidth() {
        return mScrollbarWidth;
    }
    
    @Override
    public void logic() throws GuichanException {
        checkPolicies();

        setVerticalScrollAmount(getVerticalScrollAmount());
        setHorizontalScrollAmount(getHorizontalScrollAmount());

        if (getContent() != null) {
            getContent().setPosition(-mHScroll + getContent().getFrameSize(),
                                      -mVScroll + getContent().getFrameSize());
            getContent().logic();
        }
    }
    
    @Override
    public Rectangle getChildrenArea() {
        Rectangle area = new Rectangle(0, 
                                   0,
                                   mVBarVisible ? getWidth() - mScrollbarWidth : getWidth(),
                                   mHBarVisible ? getHeight() - mScrollbarWidth : getHeight()); 
        if (area.width < 0 || area.height < 0) {
            return new Rectangle();
        }
        return area;
    }
    
    @Override
    public void showWidgetPart(Widget widget, Rectangle area) throws GuichanException {
        if (widget != getContent()) {
            throw new GuichanException("Widget not content widget");
        }

        super.showWidgetPart(widget, area);

        setHorizontalScrollAmount(getContent().getFrameSize() - getContent().getX());
        setVerticalScrollAmount(getContent().getFrameSize() - getContent().getY());
    }
    
    @Override
    public Widget getWidgetAt(int x, int y) {
        if (getChildrenArea().isPointInRect(x, y)) {
            return getContent();
        }
        return null;
    }
    
    @Override
    public void setWidth(int width) throws GuichanException {
        super.setWidth(width);
        checkPolicies();
    }

    @Override
    public void setHeight(int height) throws GuichanException {
        super.setHeight(height);
        checkPolicies();
    }
    
    @Override
    public void setDimension(Rectangle dimension) throws GuichanException {
        super.setDimension(dimension);
        checkPolicies();
    }

    public void setLeftButtonScrollAmount(int amount) {
        mLeftButtonScrollAmount = amount;
    }

    public void setRightButtonScrollAmount(int amount) {
        mRightButtonScrollAmount = amount;
    }

    public void setUpButtonScrollAmount(int amount) {
        mUpButtonScrollAmount = amount;
    }

    public void setDownButtonScrollAmount(int amount) {
        mDownButtonScrollAmount = amount;
    }

    public int getLeftButtonScrollAmount() {
        return mLeftButtonScrollAmount;
    }

    public int getRightButtonScrollAmount() {
        return mRightButtonScrollAmount;
    }

    public int getUpButtonScrollAmount() {
        return mUpButtonScrollAmount;
    }

    public int getDownButtonScrollAmount() {
        return mDownButtonScrollAmount;
    }

    public void setOpaque(boolean opaque) {
        mOpaque = opaque;
    }
    
    public boolean isOpaque() {
        return mOpaque;
    }
}
