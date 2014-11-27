package com.iteye.weimingtom.guichan.event;

import com.iteye.weimingtom.guichan.basic.Key;
import com.iteye.weimingtom.guichan.gui.Widget;

public class KeyEvent extends InputEvent {
    public final static int PRESSED = 0;
    public final static int RELEASED = 1;
    
    protected int mType;
    protected boolean mIsNumericPad;
    protected Key mKey;
    
	public KeyEvent(Widget source,
			boolean isShiftPressed,
			boolean isControlPressed,
			boolean isAltPressed,
			boolean isMetaPressed,
            int type,
            boolean isNumericPad,
            Key key) {
		super(source,
                isShiftPressed,
                isControlPressed,
                isAltPressed,
                isMetaPressed);
		mType = type;
		mIsNumericPad = isNumericPad;
		mKey = key;
	}

    public int getType() {
    	return mType;
    }
    
    public boolean isNumericPad() {
    	return mIsNumericPad;
    }
    
    public Key getKey() {
    	return mKey;
    }    
}
