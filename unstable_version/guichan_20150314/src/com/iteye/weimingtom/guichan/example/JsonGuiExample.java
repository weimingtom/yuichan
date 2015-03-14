package com.iteye.weimingtom.guichan.example;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.font.ImageFont;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.json.JsonGui;
import com.iteye.weimingtom.guichan.platform.awt.AWT;
import com.iteye.weimingtom.guichan.widget.Container;

public class JsonGuiExample {
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

		JsonGui guiParser = new JsonGui();
//		guiParser.parse("example.json");
//		Widget w = guiParser.getWidget("myWindow");
		
		guiParser.parse("gui.json");
		Widget w = guiParser.getWidget("top");
		
		top.add(w);
	}
	
	public static void halt() {
		
	}
}
