package com.iteye.weimingtom.guichan.event;

import com.iteye.weimingtom.guichan.gui.Widget;

public class InputEvent extends Event {

    protected boolean mShiftPressed;
    protected boolean mControlPressed;
    protected boolean mAltPressed;
    protected boolean mMetaPressed;
    protected boolean mIsConsumed;
    
	public InputEvent(Widget source,
			boolean isShiftPressed,
			boolean isControlPressed,
			boolean isAltPressed,
			boolean isMetaPressed) {
		super(source);
        mShiftPressed = isShiftPressed;
        mControlPressed = isControlPressed;
        mAltPressed = isAltPressed;
        mMetaPressed = isMetaPressed;
        mIsConsumed = false;
	}

    public boolean isShiftPressed() {
    	return mShiftPressed;
    }
    
    public boolean isControlPressed() {
    	return mControlPressed;
    }
    
    public boolean isAltPressed() {
    	return mAltPressed;
    }
    
    public boolean isMetaPressed() {
    	return mMetaPressed;
    }
    
    public void consume() {
    	mIsConsumed = true;
    }
    
    public boolean isConsumed() {
    	return mIsConsumed;
    }
}
