package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gce.widget.GceExampleButton;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.widget.Button;

public class ExampleButtonPlugin extends GcePlugin {
	public ExampleButtonPlugin() {
		super("ExampleButtonPlugin");
	}

	public boolean canParent() {
	    return false;
	}

	public Widget newInstance() throws GuichanException {
	    GceExampleButton c = new GceExampleButton();
	    c.loadThemeImage("buttonskin.png");
	    return c;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {
		Button btn = (Button)currentWidget;
		btn.setCaption(queryStringAttribute(node, "caption"));
		btn.setSpacing(queryIntAttribute(node, "spacing"));
	    String alignment = queryStringAttribute(node, "alignment");
	    if ("LEFT".equals(alignment)) {
	    	btn.setAlignment(Graphics.Alignment.LEFT);
	    } else if ("CENTER".equals(alignment)) {
	    	btn.setAlignment(Graphics.Alignment.CENTER);
	    } else if ("RIGHT".equals(alignment)) {
	    	btn.setAlignment(Graphics.Alignment.RIGHT);
	    }
	}

	public String componentName() {
	    return "exampleButton";
	}
}
