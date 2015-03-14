package com.iteye.weimingtom.guichan.gce.example.widget;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.platform.Image;
import com.iteye.weimingtom.guichan.widget.Icon;

public class GceIcon extends Icon {

	public GceIcon() throws GuichanException {
		super();
	}
	
	public GceIcon(String filename) throws GuichanException {
		super(filename);
	}
	
	public GceIcon(Image image) throws GuichanException {
		super(image);
	}
	
    public void setIconImage(String filename) {
    	this.mImage.free();
        mImage = null;
        try {
            mImage = Image.load(filename);
        } catch (GuichanException e) {
            try {
				mImage = Image.load("broken.bmp");
			} catch (GuichanException e1) {
				e1.printStackTrace();
			}
        }
    }
}
