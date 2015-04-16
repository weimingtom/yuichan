package com.iteye.weimingtom.guichan.widget;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Image;

public class ImageButton extends Button {
    protected Image mImage;
    protected boolean mInternalImage;
    
    public ImageButton() throws GuichanException {
    	mImage = null; 
      	mInternalImage = false;
    
      	setWidth(0);
      	setHeight(0);
    }

    public ImageButton(String filename) throws GuichanException {
    	mImage = null;
    	mInternalImage = false;
    	
    	mImage = Image.load(filename);
    	mInternalImage = true;
	    setWidth(mImage.getWidth() + mImage.getWidth() / 2);
	    setHeight(mImage.getHeight() + mImage.getHeight() / 2);
	}
	
	public ImageButton(Image image) throws GuichanException {
	    mImage = image; 
	    mInternalImage = false;
	    
	    setWidth(mImage.getWidth() + mImage.getWidth() / 2);
	    setHeight(mImage.getHeight() + mImage.getHeight() / 2);
	}

	public void setImage(Image image) {
//		if (mInternalImage) {
//			delete mImage;
//		}

		mImage = image;
		mInternalImage = false;
	}

	public Image getImage() {
		return mImage;
	}

	@Override
	public void draw(Graphics graphics) throws GuichanException {
	    Color faceColor = getBaseColor();
	    Color highlightColor, shadowColor;
	    int alpha = getBaseColor().a;
	
	    if (isPressed()) {
	        faceColor = faceColor.minus(new Color(0x303030));
	        faceColor.a = alpha;
	        highlightColor = faceColor.minus(new Color(0x303030));
	        highlightColor.a = alpha;
	        shadowColor = faceColor.plus(new Color(0x303030));
	        shadowColor.a = alpha;
	    } else {
	        highlightColor = faceColor.plus(new Color(0x303030));
	        highlightColor.a = alpha;
	        shadowColor = faceColor.minus(new Color(0x303030));
	        shadowColor.a = alpha;
	    }
	
	    graphics.setColor(faceColor);
	    graphics.fillRectangle(new Rectangle(1, 
	                                      1, 
	                                      getDimension().width - 1, 
	                                      getHeight() - 1));
	
	    graphics.setColor(highlightColor);
	    graphics.drawLine(0, 0, getWidth() - 1, 0);
	    graphics.drawLine(0, 1, 0, getHeight() - 1);
	
	    graphics.setColor(shadowColor);
	    graphics.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 1);
	    graphics.drawLine(1, getHeight() - 1, getWidth() - 1, getHeight() - 1);
	
	    graphics.setColor(getForegroundColor());
	
	    final int textX = (getWidth() - (mImage != null ? mImage.getWidth() : 0) ) / 2;
	    final int textY = (getHeight() - (mImage != null ? mImage.getHeight() : 0) ) / 2;
	
	    if (isPressed()) {
	        if (mImage != null) {
	            graphics.drawImage(mImage, textX + 1, textY + 1);
	        }
	    } else {
	        if (mImage != null) {
	            graphics.drawImage(mImage, textX, textY);
	        }
	        if (isFocused()) {
	            graphics.drawRectangle(new Rectangle(2, 
	                                              2, 
	                                              getWidth() - 4,
	                                              getHeight() - 4));
	        }
	    }
	}
}
