package com.iteye.weimingtom.guichan.example;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.font.ImageFont;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ApplicationListener;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.awt.AWT;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.xml.XmlGui;

public class XmlGuiExample implements ApplicationListener {
    private static Container top;
    private static ImageFont font;

	public static void init() throws GuichanException {
        top = new Container();
        top.setDimension(new Rectangle(0, 0, 640, 480));
        Globals.gui.setTop(top);

        font = new ImageFont(ImageFont.FIXMEDFONT_BMP, ImageFont.SPACE_ALPHABET_NUMBER);
        Widget.setGlobalFont(font);

		XmlGui guiParser = new XmlGui();
		guiParser.parse("gui.xml");
		Widget w = guiParser.getWidget("top");
		
		top.add(w);
	}
	
	public static void halt() {
		
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

	@Override
	public void preDraw(Graphics graphics) throws GuichanException {

	}
	
	@Override
	public void onKeyPressed(KeyEvent keyEvent) throws GuichanException {
		
	}
}
