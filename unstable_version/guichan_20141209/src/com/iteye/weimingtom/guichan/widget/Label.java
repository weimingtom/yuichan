package com.iteye.weimingtom.guichan.widget;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.platform.Graphics;

public class Label extends Widget {
    protected String mCaption;
    protected Graphics.Alignment mAlignment;
	
    public Label() {
        mAlignment = Graphics.Alignment.LEFT;
    }

    public Label(String caption) throws GuichanException {
        mCaption = caption;
        mAlignment = Graphics.Alignment.LEFT;

        setWidth(getFont().getWidth(caption));
        setHeight(getFont().getHeight());
    }
    
    @Override
	public void draw(Graphics graphics) throws GuichanException {
        int textX;
        int textY = getHeight() / 2 - getFont().getHeight() / 2;

        if (getAlignment() == Graphics.Alignment.LEFT) {
        	textX = 0;
        } else if (getAlignment() == Graphics.Alignment.CENTER) {
            textX = getWidth() / 2;
        } else if (getAlignment() == Graphics.Alignment.RIGHT) {
            textX = getWidth();
        } else {
            throw new GuichanException("Unknown alignment.");
        }

        graphics.setFont(getFont());
        graphics.setColor(getForegroundColor());
        graphics.drawText(getCaption(), textX, textY, getAlignment());
	}
    
    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public void setAlignment(Graphics.Alignment alignment) {
        mAlignment = alignment;
    }

    public Graphics.Alignment getAlignment() {
        return mAlignment;
    }
    
    public void adjustSize() throws GuichanException {
        setWidth(getFont().getWidth(getCaption()));
        setHeight(getFont().getHeight());
    }
}
