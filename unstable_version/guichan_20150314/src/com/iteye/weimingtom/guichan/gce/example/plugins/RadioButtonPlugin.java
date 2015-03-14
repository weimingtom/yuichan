package com.iteye.weimingtom.guichan.gce.example.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.widget.RadioButton;

public class RadioButtonPlugin extends Plugin {

	public RadioButtonPlugin() {
		super("RadioButtonPlugin");
	}

	public Widget newInstance() throws GuichanException {
		RadioButton c = new RadioButton();
	    return c;
	}

	public boolean canParent() {
	    return false;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {
		RadioButton btn = (RadioButton)currentWidget;
		btn.setCaption(queryStringAttribute(node, "caption"));
	    btn.setGroup(queryStringAttribute(node, "group"));
	    System.out.println("===>group:" + btn.getGroup());
	    String checked = queryStringAttribute(node, "checked");
	    if ("true".equals(checked)) {
	        btn.setSelected(true);
	    } else {
	    	btn.setSelected(false);
	    }
	}

	public String componentName() {
	    return "gcn::RadioButton";
	}
}
