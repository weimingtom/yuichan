package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gce.model.GceExampleDummyListModel;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.widget.DropDown;

public class DropDownPlugin extends GcePlugin {

	public DropDownPlugin() {
		super("DropDownPlugin");
	}

	public boolean canParent() {
	    return false;
	}

	public Widget newInstance() throws GuichanException {
		DropDown c = new DropDown(new GceExampleDummyListModel());
	    return c;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {
		
	}

	public String componentName() {
	    return "gcn::DropDown";
	}
}
