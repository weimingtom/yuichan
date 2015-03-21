package com.iteye.weimingtom.guichan.example;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.font.ImageFont;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.platform.awt.AWT;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.xml.XmlGui;

public class XmlGuiExample {
    private static Container top;
    private static ImageFont font;
    
	public static void main(String[] args) throws GuichanException {
        AWT.init();
    	init();
    	AWT.run();
    	halt();
    	AWT.halt();
	}

	public static void init() throws GuichanException {
        top = new Container();
        top.setDimension(new Rectangle(0, 0, 640, 480));
        Globals.gui.setTop(top);

        font = new ImageFont("fixedfont.bmp", " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        Widget.setGlobalFont(font);

		XmlGui guiParser = new XmlGui();
		guiParser.parse("gui.xml");
		Widget w = guiParser.getWidget("top");
		
		top.add(w);
	}
	
	public static void halt() {
		
	}
}
