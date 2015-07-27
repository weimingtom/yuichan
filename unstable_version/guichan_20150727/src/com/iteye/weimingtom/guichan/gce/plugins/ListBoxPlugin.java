package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gce.model.GceExampleDummyListModel;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.widget.ListBox;

public class ListBoxPlugin extends GcePlugin {

	public ListBoxPlugin() {
		super("ListBoxPlugin");
	}

	public Widget newInstance() throws GuichanException {
		ListBox c = new ListBox(new GceExampleDummyListModel());
	    return c;
	}

	public boolean canParent() {
	    return false;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {
		
	}

	public String componentName() {
	    return "gcn::ListBox";
	}
}
