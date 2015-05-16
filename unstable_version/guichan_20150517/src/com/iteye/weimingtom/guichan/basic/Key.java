package com.iteye.weimingtom.guichan.basic;

public class Key {
    public final static int SPACE = ' ';
    public final static int TAB = '\t';
    public final static int ENTER = '\n';
    public final static int LEFT_ALT = 1000;
    public final static int RIGHT_ALT = 1001;
    public final static int LEFT_SHIFT = 1002;
    public final static int RIGHT_SHIFT = 1003;
    public final static int LEFT_CONTROL = 1004;
    public final static int RIGHT_CONTROL = 1005;
    public final static int LEFT_META = 1006;
    public final static int RIGHT_META = 1007;
    public final static int LEFT_SUPER = 1008;
    public final static int RIGHT_SUPER = 1009;
    public final static int INSERT = 1010;
    public final static int HOME = 1011;
    public final static int PAGE_UP = 1012;
    public final static int DELETE = 1013;
    public final static int END = 1014;
    public final static int PAGE_DOWN = 1015;
    public final static int ESCAPE = 1016;
    public final static int CAPS_LOCK = 1017;
    public final static int BACKSPACE = 1018;
    public final static int F1 = 1019;
    public final static int F2 = 1020;
    public final static int F3 = 1021;
    public final static int F4 = 1022;
    public final static int F5 = 1023;
    public final static int F6 = 1024;
    public final static int F7 = 1025;
    public final static int F8 = 1026;
    public final static int F9 = 1027;
    public final static int F10 = 1028;
    public final static int F11 = 1029;
    public final static int F12 = 1030;
    public final static int F13 = 1031;
    public final static int F14 = 1032;
    public final static int F15 = 1033;
    public final static int PRINT_SCREEN = 1034;
    public final static int SCROLL_LOCK = 1035;
    public final static int PAUSE = 1036;
    public final static int NUM_LOCK = 1037;
    public final static int ALT_GR = 1038;
    public final static int LEFT = 1039;
    public final static int RIGHT = 1040;
    public final static int UP = 1041;
    public final static int DOWN = 1042;
    
    protected int mValue;
	
    public Key() {
    	this(0);
    }
    
    public Key(int value) {
    	mValue = value;
    }
	
    public boolean isCharacter() {
        return (mValue >= 32 && mValue <= 126)
                || (mValue >= 162 && mValue <= 255)
                || (mValue == 9);
    }
    
    public boolean isNumber() {
    	return mValue >= 48 && mValue <= 57;
    }
    
    public boolean isLetter() {
        return (((mValue >= 65 && mValue <= 90)
                || (mValue >= 97 && mValue <= 122)
                || (mValue >= 192 && mValue <= 255))
               && (mValue != 215) && (mValue != 247));
    }
    
    public int getValue() {
    	return mValue;
    }
    
    public boolean isEqual(Key key) {
    	return mValue == key.mValue;
    }
    
    public boolean isNotEqual(Key key) {
    	return mValue != key.mValue;
    }
}
