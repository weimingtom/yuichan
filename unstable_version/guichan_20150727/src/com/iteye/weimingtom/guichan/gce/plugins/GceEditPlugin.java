package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gui.Widget;

public abstract class GceEditPlugin {
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
		"plugins/gcetabbedarea.plug",
	};
	
	public GceEditPlugin(String pluginName) {
		
	}
	
	public static GceEditPlugin newPlugin(String pluginName) {
		GceEditPlugin plug = null;
		if ("plugins/gcebutton.plug".equals(pluginName)) {
			plug = new ButtonEditPlugin();
		} else if ("plugins/gcecontainer.plug".equals(pluginName)) {
			plug = new ContainerEditPlugin();
		} else if ("plugins/gcecheckbox.plug".equals(pluginName)) {
			plug = new CheckBoxEditPlugin();
		} else if ("plugins/gceicon.plug".equals(pluginName)) {
			plug = new IconEditPlugin();
		} else if ("plugins/gcelabel.plug".equals(pluginName)) {
			plug = new LabelEditPlugin();
		} else if ("plugins/gcelistbox.plug".equals(pluginName)) {
			plug = new ListBoxEditPlugin();
		} else if ("plugins/gceradiobutton.plug".equals(pluginName)) {
			plug = new RadioButtonEditPlugin();
		} else if ("plugins/gcescrollarea.plug".equals(pluginName)) {
			plug = new ScrollAreaEditPlugin();
		} else if ("plugins/gceslider.plug".equals(pluginName)) {
			plug = new SliderEditPlugin();
		} else if ("plugins/gcetextbox.plug".equals(pluginName)) {
			plug = new TextBoxEditPlugin();
		} else if ("plugins/gcetextfield.plug".equals(pluginName)) {
			plug = new TextFieldEditPlugin();
		} else if ("plugins/gcewindow.plug".equals(pluginName)) {
			plug = new WindowEditPlugin();
		} else if ("plugins/gcedropdown.plug".equals(pluginName)) {
			plug = new DropDownEditPlugin();
		} else if ("plugins/examplebutton.plug".equals(pluginName)) {
			plug = new ExampleButtonEditPlugin();
		} else if ("plugins/gcetabbedarea.plug".equals(pluginName)) {
			plug = new TabbedAreaEditPlugin();
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
    
    public abstract Widget initComponent() throws GuichanException;
	public abstract boolean canParent();
	public abstract boolean currentIsThisType();
	public abstract void updateExtendedProperties() throws GuichanException;
	public abstract Widget newInstance() throws GuichanException;
	public abstract void exportNodeExtended(Element node);
	public abstract void setExtendedFromNode(Element node);
	public abstract String componentName();
}
