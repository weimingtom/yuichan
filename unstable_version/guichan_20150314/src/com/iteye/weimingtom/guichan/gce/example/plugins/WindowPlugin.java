package com.iteye.weimingtom.guichan.gce.example.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.widget.Window;

public class WindowPlugin extends Plugin {

	public WindowPlugin() {
		super("WindowPlugin");
	}

	public Widget newInstance() {
	    Window c = new Window();
	    return c;
	}

	public boolean canParent() {
	    return true;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {
		Window win = (Window)currentWidget;
		win.setCaption(queryStringAttribute(node, "caption"));
	}

	public String componentName() {
	    return "gcn::Window";
	}
}
