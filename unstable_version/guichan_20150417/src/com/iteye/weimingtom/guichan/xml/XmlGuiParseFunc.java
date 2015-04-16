package com.iteye.weimingtom.guichan.xml;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.gui.Widget;

public interface XmlGuiParseFunc {
	void xmlGuiParse(Element val, Widget parent, XmlGui caller);
}
