package com.iteye.weimingtom.guichan.gce.example.plugins;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gui.Widget;

public abstract class Plugin {
	public static final String[] PLUG_PATHS = new String[] {
		"plugins/gcebutton.plug",
		"plugins/gcecontainer.plug",
		"plugins/gcecheckbox.plug",
		"plugins/gceicon.plug",
		"plugins/gcelabel.plug",
		"plugins/gcelistbox.plug",
		"plugins/gceradiobutton.plug",
		"plugins/gcescrollarea.plug",
		"plugins/gceslider.plug",
		"plugins/gcetextbox.plug",
		"plugins/gcetextfield.plug",
		"plugins/gcewindow.plug",
		"plugins/gcedropdown.plug",
		"plugins/examplebutton.plug", 
	};
	
	public Plugin(String pluginName) {
		
	}
	
	public static Plugin newPlugin(String pluginName) {
		Plugin plug = null;
		if ("plugins/gcebutton.plug".equals(pluginName)) {
			plug = new ButtonPlugin();
		} else if ("plugins/gcecontainer.plug".equals(pluginName)) {
			plug = new ContainerPlugin();
		} else if ("plugins/gcecheckbox.plug".equals(pluginName)) {
			plug = new CheckBoxPlugin();
		} else if ("plugins/gceicon.plug".equals(pluginName)) {
			plug = new IconPlugin();
		} else if ("plugins/gcelabel.plug".equals(pluginName)) {
			plug = new LabelPlugin();
		} else if ("plugins/gcelistbox.plug".equals(pluginName)) {
			plug = new ListBoxPlugin();
		} else if ("plugins/gceradiobutton.plug".equals(pluginName)) {
			plug = new RadioButtonPlugin();
		} else if ("plugins/gcescrollarea.plug".equals(pluginName)) {
			plug = new ScrollAreaPlugin();
		} else if ("plugins/gceslider.plug".equals(pluginName)) {
			plug = new SliderPlugin();
		} else if ("plugins/gcetextbox.plug".equals(pluginName)) {
			plug = new TextBoxPlugin();
		} else if ("plugins/gcetextfield.plug".equals(pluginName)) {
			plug = new TextFieldPlugin();
		} else if ("plugins/gcewindow.plug".equals(pluginName)) {
			plug = new WindowPlugin();
		} else if ("plugins/gcedropdown.plug".equals(pluginName)) {
			plug = new DropDownPlugin();
		} else if ("plugins/examplebutton.plug".equals(pluginName)) {
			plug = new ExampleButtonPlugin();
		}
		return plug;
	}
	
	protected int queryIntAttribute(Element val, String name) {
    	if (val == null) {
    		return 0;
    	}
    	Attribute attr = val.attribute(name);
    	if (attr == null) {
    		return 0;
    	}
    	String value = attr.getValue();
    	int ret = 0;
    	if (value != null) {
    		try {
    			ret = Integer.parseInt(value);
    		} catch (NumberFormatException e) {
    		}
    	}
    	return ret;
    }
    
    protected String queryStringAttribute(Element val, String name) {
    	if (val == null) {
    		return null;
    	}
    	Attribute attr = val.attribute(name);
    	if (attr == null) {
    		return null;
    	}
    	return attr.getValue();
    }
    
    protected double queryDoubleAttribute(Element val, String name) {
    	if (val == null) {
    		return 0;
    	}
    	Attribute attr = val.attribute(name);
    	if (attr == null) {
    		return 0;
    	}
    	String value = attr.getValue();
    	double ret = 0;
    	if (value != null) {
    		try {
    			ret = Double.parseDouble(value);
    		} catch (NumberFormatException e) {
    		}
    	}
    	return ret;
    }
    
	public abstract boolean canParent();
	public abstract Widget newInstance() throws GuichanException;
	public abstract void setExtendedFromNode(Element node, Widget currentWidget);
	public abstract String componentName();
}
