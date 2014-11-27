package com.iteye.weimingtom.guichan.platform.awt;

import java.io.IOException;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.platform.Image;
import com.iteye.weimingtom.guichan.platform.ImageLoader;

public class AWTImageLoader extends ImageLoader {
	@Override
	public Image load(String filename, boolean convertToDisplayFormat) throws GuichanException {
		java.awt.image.BufferedImage loadedSurface = 
				loadAWTSurface(filename);
        if (loadedSurface == null) {
            throw new GuichanException(
                    "Unable to load image file: " + filename);
        }
        java.awt.image.BufferedImage surface = 
        		convertToStandardFormat(loadedSurface);
        if (surface == null) {
            throw new GuichanException(
                    "Not enough memory to load: " + filename);
        }
        Image image = new AWTImage(surface, true);
        if (convertToDisplayFormat) {
            image.convertToDisplayFormat();
        }
        return image;
	}
	
    protected java.awt.image.BufferedImage loadAWTSurface(String filename) {
    	try {
			return javax.imageio.ImageIO.read(new java.io.File(filename));
		} catch (IOException e) {
			return null;
		}
    }
    
    protected java.awt.image.BufferedImage convertToStandardFormat(java.awt.image.BufferedImage surface) {
    	java.awt.image.BufferedImage tmp = null;
    	if (surface != null) {
    		tmp = new java.awt.image.BufferedImage(
    					surface.getWidth(), surface.getHeight(), 
    					java.awt.image.BufferedImage.TYPE_INT_ARGB);
    	} else {
    		return null;
    	}
    	java.awt.Graphics g = tmp.getGraphics();
		g.drawImage(surface, 0, 0, null);
		g.dispose();
		return tmp;
    }
}
