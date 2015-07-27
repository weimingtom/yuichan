package com.iteye.weimingtom.guichan.event;

import com.iteye.weimingtom.guichan.gui.Widget;

public class MouseEvent extends InputEvent {
    public final static int MOVED = 0;
    public final static int PRESSED = 1;
    public final static int RELEASED = 2;
    public final static int WHEEL_MOVED_DOWN = 3;
    public final static int WHEEL_MOVED_UP = 4;
    public final static int CLICKED = 5;
    public final static int ENTERED = 6;
    public final static int EXITED = 7;
    public final static int DRAGGED = 8;

    public final static int EMPTY = 0;
    public final static int LEFT = 1;
    public final static int RIGHT = 2;
    public final static int MIDDLE = 3;
    
    protected int mType;
    protected int mButton;
    public int mX;
    public int mY;
    protected int mClickCount;
    
	public MouseEvent(Widget source, 
			boolean isShiftPressed,
            boolean isControlPressed,
            boolean isAltPressed,
            boolean isMetaPressed,
            int type,
            int button,
            int x,
            int y,
            int clickCount) {
		super(source, 
				isShiftPressed, 
				isControlPressed, 
				isAltPressed, 
				isMetaPressed);
        mType = type;
        mButton = button;
        mX = x;
        mY = y;
        mClickCount = clickCount;
	}

    public int getButton() {
    	return mButton;
    }
    
    public int getX() {
    	return mX;
    }
    
    public int getY() {
    	return mY;
    }
    
    public int getClickCount() {
    	return mClickCount;
    }
    
    public int getType() {
    	return mType;
    }
}
