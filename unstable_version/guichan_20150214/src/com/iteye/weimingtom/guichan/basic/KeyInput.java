package com.iteye.weimingtom.guichan.basic;

public class KeyInput {
    public final static int PRESSED = 0;
    public final static int RELEASED = 1;

    protected Key mKey;
    protected int mType; //unsigned
    protected boolean mShiftPressed;
    protected boolean mControlPressed;
    protected boolean mAltPressed;
    protected boolean mMetaPressed;
    protected boolean mNumericPad;
    
	public KeyInput() { 
		
	}
    
	public KeyInput(Key key, int type) {
        mKey = key;
        mType = type;
        mShiftPressed = false;
        mControlPressed = false;
        mAltPressed = false;
        mMetaPressed = false;
        mNumericPad = false;
	}
    
	public void setType(int type) {
		mType = type;
	}
	
    public int getType() {
    	return mType;
    }
    
    public void setKey(Key key) {
    	mKey = key;
    }
    
    public Key getKey() {
    	return mKey;
    }
    
    public boolean isShiftPressed() {
    	return mShiftPressed;
    }
    
    public void setShiftPressed(boolean pressed) {
    	mShiftPressed = pressed;
    }
    
    public boolean isControlPressed() {
    	return mControlPressed;
    }
    
    public void setControlPressed(boolean pressed) {
    	mControlPressed = pressed;
    }
    
    public boolean isAltPressed() {
    	return mAltPressed;
    }
    
    public void setAltPressed(boolean pressed) {
    	mAltPressed = pressed;
    }
    
    public boolean isMetaPressed() {
    	return mMetaPressed;
    }
    
    public void setMetaPressed(boolean pressed) {
    	mMetaPressed = pressed;
    }
    
    public boolean isNumericPad() {
    	return mNumericPad;
    }
    
    public void setNumericPad(boolean numpad) {
    	mNumericPad = numpad;
    }
}
