package com.iteye.weimingtom.guichan.platform;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;

public abstract class Image {
	protected static ImageLoader mImageLoader = null;
	
	public Image() {
		
	}

	public static Image load(String filename) throws GuichanException {
		return load(filename, true);
	}
	
    public static Image load(String filename, boolean convertToDisplayFormat) throws GuichanException {
        if (mImageLoader == null) {
            throw new GuichanException("Trying to load an image but no image loader is set.");
        }
        return mImageLoader.load(filename, convertToDisplayFormat);
    }
    
    public static ImageLoader getImageLoader() {
    	return mImageLoader;
    }
    
    public static void setImageLoader(ImageLoader imageLoader) {
    	mImageLoader = imageLoader;
    }
    
    public abstract void free();
    public abstract int getWidth() throws GuichanException;
    public abstract int getHeight() throws GuichanException;
    public abstract Color getPixel(int x, int y) throws GuichanException;
    public abstract void putPixel(int x, int y, Color color) throws GuichanException;
    public abstract void convertToDisplayFormat() throws GuichanException;
}
