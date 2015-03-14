package com.iteye.weimingtom.guichan.gce.example.listener;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.platform.awt.AWT;

public class ExampleActionListener implements ActionListener {
	@Override
	public void action(ActionEvent actionEvent) throws GuichanException {
        if ("quit".equals(actionEvent.getId())) {
        	System.out.println("ExampleActionListener quit");
        	AWT.dispose();
        }
	}
}
