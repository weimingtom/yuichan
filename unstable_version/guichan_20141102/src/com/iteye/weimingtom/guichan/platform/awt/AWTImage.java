package com.iteye.weimingtom.guichan.platform.awt;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.platform.Image;

/**
 * @see http://man.ddvip.com/program/java_api_zh/java/awt/image/BufferedImage.html
 * @author Administrator
 *
 */
public class AWTImage extends Image {
    protected java.awt.image.BufferedImage mSurface;
    protected boolean mAutoFree;
        
    public java.awt.image.BufferedImage getSurface() {
    	return this.mSurface;
    }
    
    public AWTImage(java.awt.image.BufferedImage surface, boolean autoFree) {
        mAutoFree = autoFree;
        mSurface = surface;
    }
    
    public void destroy() {
        if (mAutoFree) {
            free();
        }
    }

	@Override
	public void free() {
		mSurface = null;
	}

	@Override
	public int getWidth() throws GuichanException {
		if (mSurface == null) {
			throw new GuichanException("Trying to get the width of a non loaded image.");
		}
		return mSurface.getWidth();
	}

	@Override
	public int getHeight() throws GuichanException {
		if (mSurface == null) {
			throw new GuichanException("Trying to get the height of a non loaded image.");
		}
		return mSurface.getWidth();
	}

	@Override
	public Color getPixel(int x, int y) throws GuichanException {
		if (mSurface == null) {
			throw new GuichanException("Trying to get a pixel from a non loaded image.");
		}
		int[] pixels = new int[1];
		mSurface.getRGB(x, y, 1, 1, pixels, 0, 1);
		int color = pixels[0];
		int a = (color >>> 24) & 0xff;
		int r = (color >>> 16) & 0xff;
		int g = (color >>>  8) & 0xff;
		int b = (color >>>  0) & 0xff;
		return new Color(r, g, b, a);
	}

	@Override
	public void putPixel(int x, int y, Color color) throws GuichanException {
		if (mSurface == null) {
			throw new GuichanException("Trying to put a pixel in a non loaded image.");
		}
		int[] pixels = new int[1];
		pixels[0] = 0;
		pixels[0] |= (color.a << 24);
		pixels[0] |= (color.r << 16);
		pixels[0] |= (color.g <<  8);
		pixels[0] |= (color.b <<  0);
		mSurface.setRGB(x, y, 1, 1, pixels, 0, 1);
	}

	@Override
	public void convertToDisplayFormat() throws GuichanException {
        if (mSurface == null) {
			throw new GuichanException("Trying to convert a non loaded image to display format.");
		}
	}

}
