package com.iteye.weimingtom.guichan.platform.awt;

import com.iteye.weimingtom.guichan.basic.ClipRectangle;
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
    public boolean pushClipArea(Rectangle area) {
        boolean result = super.pushClipArea(area);
        
        ClipRectangle carea = mClipStack.peek();
        if (mTarget != null) {
        	mTarget.setClip(carea.x, carea.y, carea.width, carea.height);
        }
        
        return result;
    }

    @Override
    public void popClipArea() throws GuichanException {
        super.popClipArea();

        if (mClipStack.empty()) {
            return;
        }
        
        ClipRectangle carea = mClipStack.peek();
        if (mTarget != null) {
        	mTarget.setClip(carea.x, carea.y, carea.width, carea.height);
        }
    }
    
	@Override
	public void drawImage(Image image, int srcX, int srcY, int dstX, int dstY,
			int width, int height) throws GuichanException {
        if (image == null) {
            throw new GuichanException("Trying to draw an image of unknown format, must be an SDLImage.");
        }
        ClipRectangle top = mClipStack.peek();
        if (image instanceof AWTImage) {
        	AWTImage srcImage = (AWTImage)image;
        	if (mTarget != null && 
        		srcImage != null && 
        		srcImage.getSurface() != null) {
        		mTarget.drawImage(srcImage.getSurface(), 
        			top.x + dstX, top.y + dstY,
        			top.x + dstX + width, top.y + dstY + height,
        			srcX, srcY,
        			srcX + width, srcY + height,
        			null);
        	}
        }
	}

	@Override
	public void drawPoint(int x, int y) {
		ClipRectangle top = mClipStack.peek();
		if (mTarget != null) {
			mTarget.drawLine(top.x + x, top.y + y, 
				top.x + x, top.y + y);
		}
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		ClipRectangle top = mClipStack.peek();
		if (mTarget != null) {
			mTarget.drawLine(top.x + x1, top.y + y1, 
					top.x + x2, top.y + y2);
		}
	}

	@Override
	public void drawRectangle(Rectangle rectangle) {
		ClipRectangle top = mClipStack.peek();
		if (mTarget != null) {
			mTarget.drawRect(top.x + rectangle.x, top.y + rectangle.y, 
					rectangle.width, rectangle.height);
		}		
	}

	@Override
	public void fillRectangle(Rectangle rectangle) {
        ClipRectangle top = mClipStack.peek();
		if (mTarget != null) {
			mTarget.fillRect(top.x + rectangle.x, top.x + rectangle.y, 
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
	
	public void test() {
        java.awt.Graphics g = mTarget;
		g.setColor(java.awt.Color.YELLOW);
		g.fillRect(0, 0, 640, 480);
	}
}
