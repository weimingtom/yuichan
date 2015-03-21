package com.iteye.weimingtom.guichan.basic;

public class MouseInput {
    public final static int MOVED = 0;
    public final static int PRESSED = 1;
    public final static int RELEASED = 2;
    public final static int WHEEL_MOVED_DOWN = 3;
    public final static int WHEEL_MOVED_UP = 4;
    
    public final static int EMPTY = 0;
    public final static int LEFT = 1;
    public final static int RIGHT = 2;
    public final static int MIDDLE = 3;
    
    protected int mType;
    protected int mButton;
    protected int mTimeStamp;
    protected int mX;
    protected int mY;
    
	public MouseInput() { 
		
	}
	
	public MouseInput(int button, 
			       int type,
                   int x,
                   int y,
                   int timeStamp) {
        mType = type;
        mButton = button;
        mTimeStamp = timeStamp;
        mX = x;
        mY = y;
	}
	
	public void setType(int type) {
		mType = type;
	}
	
	public int getType() {
		return mType;
	}
	
	public void setButton(int button) {
		mButton = button;
	}
    
	public int getButton() {
		return mButton;
	}
	
    public void setTimeStamp(int timeStamp) {
    	mTimeStamp = timeStamp;
    }
    
    public int getTimeStamp() {
    	return mTimeStamp;
    }
    
    public void setX(int x) {
    	mX = x;
    }
    
    public int getX() {
    	return mX;
    }
    
    public void setY(int y) {
    	mY = y;
    }

    public int getY() {
    	return mY;
    }
}
