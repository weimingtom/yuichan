package com.iteye.weimingtom.guichan.gce.awt;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gce.GceEditor;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.platform.awt.AWT;

public class GceEditorExample {
	public static void main(String[] args) throws GuichanException {
		Globals.app = new AWT(800, 600);
		Globals.app.run(new GceEditor());
	}
}
