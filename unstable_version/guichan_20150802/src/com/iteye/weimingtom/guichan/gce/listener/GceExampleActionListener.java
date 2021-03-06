package com.iteye.weimingtom.guichan.gce.listener;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.platform.awt.AWT;

public class GceExampleActionListener implements ActionListener {
	@Override
	public void action(ActionEvent actionEvent) throws GuichanException {
        if ("quit".equals(actionEvent.getId())) {
        	System.out.println("ExampleActionListener quit");
        	if (Globals.app != null) {
        		Globals.app.dispose();
        	}
        }
	}
}
