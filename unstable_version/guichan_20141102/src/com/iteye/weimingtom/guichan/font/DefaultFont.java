package com.iteye.weimingtom.guichan.font;

import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.platform.Font;
import com.iteye.weimingtom.guichan.platform.Graphics;

public class DefaultFont extends Font {
	public DefaultFont() {
		
	}

	@Override
	public int getWidth(String text) {
		return 8 * text.length();
	}

	@Override
	public int getHeight() {
		return 8;
	}

    public int drawGlyph(Graphics graphics, char glyph, int x, int y) {
        graphics.drawRectangle(new Rectangle(x, y, 8, 8));
        return 8;
    }
	
	@Override
	public void drawString(Graphics graphics, String text, int x, int y) {
		for (int i = 0; i < text.length(); ++i) {
            drawGlyph(graphics, text.charAt(i), x, y);
            x += getWidth(text);
        }
	}
	
	@Override
    public int getStringIndexAt(String text, int x) {
        if (x > (int)text.length() * 8) {
            return text.length();
        }
        return x / 8;
    }
}
