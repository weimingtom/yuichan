package com.iteye.weimingtom.guichan.gce.example.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gce.example.model.DummyListModel;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.widget.ListBox;

public class ListBoxPlugin extends Plugin {

	public ListBoxPlugin() {
		super("ListBoxPlugin");
	}

	public Widget newInstance() throws GuichanException {
		ListBox c = new ListBox(new DummyListModel());
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
