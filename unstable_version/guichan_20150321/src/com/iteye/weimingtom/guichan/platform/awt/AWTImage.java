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
		return mSurface.getHeight();
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
        
        int x = 0;
        int y = 0;
        int width = mSurface.getWidth();
        int height = mSurface.getHeight();
		int[] pixels = new int[width * height];
		mSurface.getRGB(x, y, width, height, pixels, 0, width);
		int pixels_position = 0; 
		while (pixels_position < pixels.length) {
			int p = pixels[pixels_position];
			int r = (p >>> 16) & 0xFF;
			int g = (p >>>  8) & 0xFF;
			int b = (p >>>  0) & 0xFF;
			if (r == 255 && g == 0 && b == 255) {
				//NOTE:Map #ff00ff to alpha
				pixels[pixels_position] = p & 0x00ffffff; 
			}
			pixels_position++;
		}
		mSurface.setRGB(x, y, width, height, pixels, 0, width);
		
		
	}

}
