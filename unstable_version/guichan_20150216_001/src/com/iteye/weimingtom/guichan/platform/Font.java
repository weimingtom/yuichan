package com.iteye.weimingtom.guichan.platform;

import com.iteye.weimingtom.guichan.basic.GuichanException;

public abstract class Font {
	public Font() {
		
	}
	
	public abstract int getWidth(String text);
	public abstract int getHeight();
	
	public int getStringIndexAt(String text, int x) {
        int i;
        int size = 0;
        for (i = 0; i < text.length(); ++i) {
            size = getWidth(text.substring(0, 0 + i));
            if (size > x) {
                return i;
            }
        }
        return text.length();
	}
	
	public abstract void drawString(Graphics graphics, String text, 
			int x, int y) throws GuichanException;
}
