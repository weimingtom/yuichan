package com.iteye.weimingtom.guichan.gce.awt;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gce.GceExample;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.platform.awt.AWT;

public class GceTestExample {
	public static void main(String[] args) throws GuichanException {
		Globals.app = new AWT(800, 600);
		Globals.app.run(new GceExample());
	}
}
