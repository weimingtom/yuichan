package com.iteye.weimingtom.guichan.gce;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.font.ImageFont;
import com.iteye.weimingtom.guichan.gce.listener.GceExampleActionListener;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ApplicationListener;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;

public class GceExample implements ApplicationListener {
    private Container top;
    private ImageFont font;
    private GceExampleActionListener guiListener;

	public void init() throws GuichanException {
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
	
	public void halt() {
		GcePluginManager.free_all_plugins();
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
