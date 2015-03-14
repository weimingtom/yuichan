package com.iteye.weimingtom.guichan.gce.example.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.widget.Slider;

public class SliderPlugin extends Plugin {

	public SliderPlugin() {
		super("SliderPlugin");
	}

	public Widget newInstance() throws GuichanException {
		Slider c = new Slider();
	    return c;
	}

	public boolean canParent() {
	    return false;
	}

	public void setExtendedFromNode(Element node, Widget currentWidget) {
		Slider slider = (Slider)currentWidget;
		slider.setMarkerLength(queryIntAttribute(node, "markerLength"));
	    String vertical = queryStringAttribute(node, "vertical");
	    if ("true".equals(vertical)) {
	    	slider.setOrientation(Slider.Orientation.VERTICAL);
	    } else {
	    	slider.setOrientation(Slider.Orientation.HORIZONTAL);
		}
	    slider.setScaleStart(queryDoubleAttribute(node, "scaleStart"));
	    slider.setScaleEnd(queryDoubleAttribute(node, "scaleEnd"));
	    slider.setStepLength(queryDoubleAttribute(node, "stepLength"));
	}

	public String componentName() {
	    return "gcn::Slider";
	}
}
