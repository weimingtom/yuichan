package com.iteye.weimingtom.guichan.widget;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Image;

public class Icon extends Widget {
    protected Image mImage;
    protected boolean mInternalImage;

    public Icon() throws GuichanException {
    	mImage = null;
    	mInternalImage = false;
    	
    	setSize(0, 0);
    }

	public Icon(String filename) throws GuichanException {
		mImage = null;
		mInternalImage = false;
		mImage = Image.load(filename);
		mInternalImage = true;
		setSize(mImage.getWidth(),
            mImage.getHeight());
	}

	public Icon(Image image) throws GuichanException {
    	mImage = image;
      	mInternalImage = false;
      	setSize(mImage.getWidth(),
            mImage.getHeight());
	}
    
	@Override
	public void draw(Graphics graphics) throws GuichanException {
        if (mImage != null) {
            final int x = (getWidth() - mImage.getWidth()) / 2;
            final int y = (getHeight() - mImage.getHeight()) / 2;
            graphics.drawImage(mImage, x, y);
        }
	}

    public void setImage(Image image) throws GuichanException {
//        if (mInternalImage) {
//            delete mImage;
//        }

        mImage = image;
        mInternalImage = false;
        setSize(mImage.getWidth(),
                mImage.getHeight());
    }
    
    public Image getImage() {
        return mImage;
    }
}
