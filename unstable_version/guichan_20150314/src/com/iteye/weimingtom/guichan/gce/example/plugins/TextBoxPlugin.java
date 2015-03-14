package com.iteye.weimingtom.guichan.gce.example.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.widget.TextBox;

public class TextBoxPlugin extends Plugin {

	public TextBoxPlugin() {
		super("TextBoxPlugin");
	}

	public Widget newInstance() throws GuichanException {
		TextBox c = new TextBox();
	    return c;
	}

	public boolean canParent() {
	    return false;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {
		
	}

	public String componentName() {
	    return "gcn::TextBox";
	}
}
