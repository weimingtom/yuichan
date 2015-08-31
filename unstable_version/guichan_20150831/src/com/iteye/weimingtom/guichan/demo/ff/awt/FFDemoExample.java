package com.iteye.weimingtom.guichan.demo.ff.awt;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.demo.ff.FFDemo;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.platform.awt.AWT;

public class FFDemoExample {

	public static void main(String[] args) throws GuichanException {
		Globals.app = new AWT(320, 240, "Gui-chan FF demo", false);
		Globals.app.run(new FFDemo());
	}
	
}
