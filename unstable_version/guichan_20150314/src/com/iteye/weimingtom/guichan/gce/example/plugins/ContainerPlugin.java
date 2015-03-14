package com.iteye.weimingtom.guichan.gce.example.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.widget.Container;

public class ContainerPlugin extends Plugin {

	public ContainerPlugin() {
		super("ContainerPlugin");
	}

	public Widget newInstance() {
	    Container c = new Container();
	    return c;
	}

	public boolean canParent() {
	    return true;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {

	}

	public String componentName() {
	    return "gcn::Container";
	}
}
