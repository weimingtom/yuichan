package com.iteye.weimingtom.guichan.platform.awt;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Image;

/**
 * @see http://www.cjsdn.net/Doc/JDK50/java/awt/Graphics.html
 * @see http://www.cjsdn.net/Doc/JDK50/java/awt/Color.html
 * @author Administrator
 *
 */
public class AWTGraphics extends Graphics {
    private java.awt.Graphics mTarget;

	@Override
	public void drawImage(Image image, int srcX, int srcY, int dstX, int dstY,
			int width, int height) throws GuichanException {
        if (image == null) {
            throw new GuichanException("Trying to draw an image of unknown format, must be an SDLImage.");
        }
        if (image instanceof AWTImage) {
        	AWTImage srcImage = (AWTImage)image;
        	if (mTarget != null && 
        		srcImage != null && 
        		srcImage.getSurface() != null) {
        		mTarget.drawImage(srcImage.getSurface(), 
        			dstX, dstY,
        			dstX + width, dstY + height,
        			srcX, srcY,
        			srcX + width, srcY + height,
        			null);
        	}
        }
	}

	@Override
	public void drawPoint(int x, int y) {
		if (mTarget != null) {
			mTarget.drawLine(x, y, x, y);
		}
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		if (mTarget != null) {
			mTarget.drawLine(x1, y1, x2, y2);
		}
	}

	@Override
	public void drawRectangle(Rectangle rectangle) {
		if (mTarget != null) {
			mTarget.drawRect(rectangle.x, rectangle.y, 
					rectangle.width, rectangle.height);
		}		
	}

	@Override
	public void fillRectangle(Rectangle rectangle) {
		if (mTarget != null) {
			mTarget.fillRect(rectangle.x, rectangle.y, 
					rectangle.width, rectangle.height);
		}
	}

	@Override
	public void setColor(Color color) {
		if (mTarget != null) {
			mTarget.setColor(new java.awt.Color(
				color.r, color.g, color.b, color.a));
		}
	}

	@Override
	public Color getColor() {
		if (mTarget != null) {
			java.awt.Color color = mTarget.getColor();
			return new Color(color.getRed(), color.getGreen(), 
					color.getBlue(), color.getAlpha());
		}
		return new Color();
	}

	
	//private method
	public void setTarget(java.awt.Graphics target) {
		this.mTarget = target;
	}
}
