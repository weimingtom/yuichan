package com.iteye.weimingtom.guichan.gce.widget;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.widget.Container;

public class GceContainerEdit extends Container {
	public GceContainerEdit() {
		super();
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
}
