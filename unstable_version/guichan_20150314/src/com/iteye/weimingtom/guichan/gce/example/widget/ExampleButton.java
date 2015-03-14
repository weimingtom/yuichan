package com.iteye.weimingtom.guichan.gce.example.widget;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Image;
import com.iteye.weimingtom.guichan.widget.Button;

public class ExampleButton extends Button {
	private Image mImage;
	private Color mCC;
	private Color mCCD;
	
	public ExampleButton() throws GuichanException {
		super();
	}
	
	public void loadThemeImage(String filename) throws GuichanException {
	    this.mImage = Image.load(filename);
	    mCC = mImage.getPixel(8, 8);
	    mCCD = mImage.getPixel(25, 8);
	}

	public void draw(Graphics g) throws GuichanException {
	    Color centreColour;
	    int srcX=0;
	    if (isPressed()) {
	        srcX = 17;
	        centreColour = mCCD;
	    } else {
	        centreColour=mCC;
	    }
	    g.setColor(centreColour);
	    g.fillRectangle(new Rectangle(8, 8, 
	    	getWidth() - 16, getHeight() - 16));
	    g.drawImage(mImage, 0 + srcX, 0, 
	    	0, 0, 8, 8);
	    g.drawImage(mImage, 9 + srcX, 0, 
	    	getWidth() - 8, 0, 8, 8);
	    g.drawImage(mImage, 0 + srcX, 9, 
	    	0, getHeight() - 8, 8, 8);
	    g.drawImage(mImage, 9 + srcX, 9, 
	    	getWidth() - 8, getHeight() - 8, 8, 8);
	    for (int i = 0; i < (getWidth() - 16); i++) {
	        g.drawImage(mImage, 8 + srcX, 0, 
	        	8 + i, 0, 1, 8);
	        g.drawImage(mImage, 8 + srcX, 9, 
	        	8 + i, getHeight() - 8, 1, 8);
	    }
	    for (int i = 0; i < (getHeight() - 16); i++) {
	        g.drawImage(mImage, 0 + srcX, 8, 
	        	0, 8 + i, 8, 1);
	        g.drawImage(mImage, 9 + srcX, 8, 
	        	getWidth() - 8, 8 + i, 8, 1);
	    }
	    g.setColor(getForegroundColor());
	    int textX;
	    int textY = getHeight() / 2 - getFont().getHeight() / 2;
	    Graphics.Alignment alignment = getAlignment();
	    if (alignment == Graphics.Alignment.LEFT) {
            textX = mSpacing;
	    } else if (alignment == Graphics.Alignment.CENTER) {
            textX = getWidth() / 2;
		} else if (alignment == Graphics.Alignment.RIGHT) {
            textX = getWidth() - mSpacing;
		} else {
            throw new GuichanException("Unknown alignment.");
	    }
	    g.setFont(getFont());
	    if (isPressed()) {
	        g.drawText(getCaption(), textX + 1, textY + 1, getAlignment());
	    } else {
	        g.drawText(getCaption(), textX, textY, getAlignment());
	        if (isFocused()){
	            g.drawRectangle(new Rectangle(6, 6, 
	            	getWidth() - 12,
	            	getHeight() - 12));
	        }
	    }
	}
}
