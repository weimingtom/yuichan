package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gce.widget.GceExampleIcon;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.platform.Image;
import com.iteye.weimingtom.guichan.widget.Icon;

public class IconPlugin extends GcePlugin {

	public IconPlugin() {
		super("IconPlugin");
	}

	public boolean canParent() {
	    return false;
	}

	public Widget newInstance() throws GuichanException {
	    Image i = Image.load("broken.bmp");
		Icon c = new GceExampleIcon(i);
	    return c;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {
		GceExampleIcon icon = (GceExampleIcon)currentWidget;
		String str = queryStringAttribute(node, "imageFileName");
		icon.setIconImage(str);
	}

	public String componentName() {
	    return "gcn::Icon";
	}
}
