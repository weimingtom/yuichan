package com.iteye.weimingtom.guichan.example.awt;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.example.Helloworld;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.platform.awt.AWT;

public class HelloworldExample {
	public static void main(String[] args) throws GuichanException {
		Globals.app = new AWT();
		Globals.app.run(new Helloworld());
	}
}
