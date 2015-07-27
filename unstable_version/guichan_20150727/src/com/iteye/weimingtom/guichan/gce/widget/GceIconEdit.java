package com.iteye.weimingtom.guichan.gce.widget;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Image;
import com.iteye.weimingtom.guichan.widget.Icon;

public class GceIconEdit extends Icon {
	private String mFileName;
	
	public GceIconEdit() throws GuichanException {
		super();
	}
	
	public GceIconEdit(String filename) throws GuichanException {
		super(filename);
	}
	
	public GceIconEdit(Image image) throws GuichanException {
		super(image);
		this.mFileName = "";
	}

	@Override
	public void draw(Graphics g) throws GuichanException {
		super.draw(g);
	    if (EditorGui.currentWidget == this) {
	        g.setColor(new Color(255, 0, 0, 128));        
	        g.fillRectangle(new Rectangle(0, 0, 12, 12));
	        int w = getWidth();
	        int h = getHeight();
	        g.fillRectangle(new Rectangle(w - 12, h - 12, w, h));
	    }
	}
	
	public Image getIconImage() {
	    return mImage;
	}
	
	public void setIconImage(String filename) {
	    //FIXME:
		//delete mImage;
	    mImage = null;
	    try {
	        mImage = Image.load(filename);
	        this.mFileName = filename;
	    } catch (GuichanException e) {
	        mFileName = "";
	        try {
				mImage = Image.load("images/broken.bmp");
			} catch (GuichanException e1) {
				e1.printStackTrace();
			}
	    }
	}
	
	public String getFileName() {
	    return mFileName;
	}
}
