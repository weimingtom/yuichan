package com.iteye.weimingtom.guichan.example;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.font.ImageFont;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ApplicationListener;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.Label;

public class Helloworld implements ApplicationListener {
    private Container top;
    private ImageFont font;
    private Label label;

	public void init() throws GuichanException {
        top = new Container();
        top.setDimension(new Rectangle(0, 0, 640, 480));
        Globals.gui.setTop(top);

        font = new ImageFont("fixedfont.bmp", " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        Widget.setGlobalFont(font);

        label = new Label("Hello World");
        label.setPosition(280, 220);
        top.add(label);
	}
	
	public void halt() {
		
	}

	@Override
	public void onInit() throws GuichanException {
		init();
	}

	@Override
	public void onTimer() throws GuichanException {
		
	}

	@Override
	public void onHalt() throws GuichanException {
		halt();
	}
}
