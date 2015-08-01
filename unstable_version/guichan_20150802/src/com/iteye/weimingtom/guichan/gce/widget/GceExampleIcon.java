package com.iteye.weimingtom.guichan.gce.widget;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.platform.Image;
import com.iteye.weimingtom.guichan.widget.Icon;

public class GceExampleIcon extends Icon {

	public GceExampleIcon() throws GuichanException {
		super();
	}
	
	public GceExampleIcon(String filename) throws GuichanException {
		super(filename);
	}
	
	public GceExampleIcon(Image image) throws GuichanException {
		super(image);
	}
	
    public void setIconImage(String filename) {
    	this.mImage.free();
        mImage = null;
        try {
            mImage = Image.load(filename);
        } catch (GuichanException e) {
            try {
				mImage = Image.load("images/broken.bmp");
			} catch (GuichanException e1) {
				e1.printStackTrace();
			}
        }
    }
}
