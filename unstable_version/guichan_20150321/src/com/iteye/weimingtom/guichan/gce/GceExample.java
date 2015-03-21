package com.iteye.weimingtom.guichan.gce;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.font.ImageFont;
import com.iteye.weimingtom.guichan.gce.listener.GceExampleActionListener;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.platform.awt.AWT;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;

public class GceExample {
    private static Container top;
    private static ImageFont font;
    private static GceExampleActionListener guiListener;

	public static void main(String[] args) throws GuichanException {
		AWT.window_width = 800;
		AWT.window_height = 600;
		AWT.init();
    	init();
    	AWT.run();
    	halt();
    	AWT.halt();
	}

	public static void init() throws GuichanException {
        font = new ImageFont("fonts/font.png", 
        		" abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,!?-+/():;%&`'*#=[]\"");
        Widget.setGlobalFont(font);

        GcePluginManager.initialisePlugs();
        
        top = GceLoader.loadGui("gce_output.xml");
//        top = loadGui("gce_test1.xml");
        Globals.gui.setTop(top);

        guiListener = new GceExampleActionListener();
        Button b1 = (Button) top.findWidgetById("exitButton");
        if (b1 != null) {
            b1.addActionListener(guiListener);
        }
    }
	
	public static void halt() {
		GcePluginManager.free_all_plugins();
	}
}
