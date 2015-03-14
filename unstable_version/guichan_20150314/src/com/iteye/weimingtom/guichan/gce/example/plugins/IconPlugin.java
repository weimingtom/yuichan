package com.iteye.weimingtom.guichan.gce.example.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gce.example.widget.GceIcon;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.platform.Image;
import com.iteye.weimingtom.guichan.widget.Icon;

public class IconPlugin extends Plugin {

	public IconPlugin() {
		super("IconPlugin");
	}

	public boolean canParent() {
	    return false;
	}

	public Widget newInstance() throws GuichanException {
	    Image i = Image.load("broken.bmp");
		Icon c = new GceIcon(i);
	    return c;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {
		GceIcon icon = (GceIcon)currentWidget;
		String str = queryStringAttribute(node, "imageFileName");
		icon.setIconImage(str);
	}

	public String componentName() {
	    return "gcn::Icon";
	}
}
