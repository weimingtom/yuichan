package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.widget.Label;

public class LabelPlugin extends GcePlugin {

	public LabelPlugin() {
		super("LabelPlugin");
	}

	public boolean canParent() {
	    return false;
	}

	public Widget newInstance() {
	    Label c = new Label();
	    return c;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {
		Label label = (Label)currentWidget;
		label.setCaption(queryStringAttribute(node, "caption"));
		String alignment = queryStringAttribute(node, "alignment");
	    if ("LEFT".equals(alignment)) {
	    	label.setAlignment(Graphics.Alignment.LEFT);
	    } else if ("CENTER".equals(alignment)) {
	    	label.setAlignment(Graphics.Alignment.CENTER);
	    } else if ("RIGHT".equals(alignment)) {
	    	label.setAlignment(Graphics.Alignment.RIGHT);
	    }
	}

	public String componentName() {
	    return "gcn::Label";
	}
}
