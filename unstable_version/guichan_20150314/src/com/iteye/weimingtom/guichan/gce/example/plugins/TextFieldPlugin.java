package com.iteye.weimingtom.guichan.gce.example.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.widget.TextField;

public class TextFieldPlugin extends Plugin {

	public TextFieldPlugin() {
		super("TextFiledPlugin");
	}

	public Widget newInstance() throws GuichanException {
		TextField c = new TextField();
	    return c;
	}

	public boolean canParent() {
	    return false;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {
		TextField textField = (TextField)currentWidget;
		textField.setText(queryStringAttribute(node, "text"));
	}

	public String componentName() {
	    return "gcn::TextField";
	}
}
