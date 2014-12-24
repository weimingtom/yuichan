package com.iteye.weimingtom.guichan.example;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.font.ImageFont;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.platform.awt.AWT;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.Label;

public class Helloworld {
    private static Container top;
    private static ImageFont font;
    private static Label label;
    
	public static void main(String[] args) throws GuichanException {
        AWT.init();
    	Helloworld.init();
    	AWT.run();
    	Helloworld.halt();
    	AWT.halt();
	}

	public static void init() throws GuichanException {
        top = new Container();
        top.setDimension(new Rectangle(0, 0, 640, 480));
        Globals.gui.setTop(top);

        font = new ImageFont("fixedfont.bmp", " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        Widget.setGlobalFont(font);

        label = new Label("Hello World");
        label.setPosition(280, 220);
        top.add(label);
	}
	
	public static void halt() {
		
	}
}
