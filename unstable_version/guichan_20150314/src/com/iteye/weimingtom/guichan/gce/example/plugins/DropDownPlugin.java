package com.iteye.weimingtom.guichan.gce.example.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gce.example.model.DummyListModel;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.widget.DropDown;

public class DropDownPlugin extends Plugin {

	public DropDownPlugin() {
		super("DropDownPlugin");
	}

	public boolean canParent() {
	    return false;
	}

	public Widget newInstance() throws GuichanException {
		DropDown c = new DropDown(new DummyListModel());
	    return c;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {
		
	}

	public String componentName() {
	    return "gcn::DropDown";
	}
}
