package com.iteye.weimingtom.guichan.demo.fps.awt;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.demo.fps.FPSDemo;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.platform.awt.AWT;

public class FPSDemoExample {
	public static void main(String[] args) throws GuichanException {
		Globals.app = new AWT(800, 600, "Guichan FPS demo", false);
		Globals.app.run(new FPSDemo());
	}
}
