package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.widget.CheckBox;

public class CheckBoxPlugin extends GcePlugin {
	public CheckBoxPlugin() {
		super("CheckBoxPlugin");
	}

	public boolean canParent() {
	    return false;
	}

	public Widget newInstance() throws GuichanException {
	    CheckBox c = new CheckBox();
	    return c;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {
		CheckBox checkBox = (CheckBox)currentWidget;
		checkBox.setCaption(queryStringAttribute(node, "caption"));
		String checked = queryStringAttribute(node, "checked");
	    if ("true".equals(checked)) {
	    	checkBox.setSelected(true);
	    } else {
	    	checkBox.setSelected(false);
	    }
	}

	public String componentName() {
	    return "gcn::CheckBox";
	}
}
