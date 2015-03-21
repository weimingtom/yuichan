package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.widget.ScrollArea;

public class ScrollAreaPlugin extends GcePlugin {

	public ScrollAreaPlugin() {
		super("ScrollAreaPlugin");
	}

	public Widget newInstance() {
		ScrollArea c = new ScrollArea();
	    return c;
	}

	public boolean canParent() {
	    return true;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {
	}

	public String componentName() {
	    return "gcn::ScrollArea";
	}
}
