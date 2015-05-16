package com.iteye.weimingtom.guichan.gce;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.gce.plugins.ContainerEditPlugin;
import com.iteye.weimingtom.guichan.gce.plugins.GceEditPlugin;
import com.iteye.weimingtom.guichan.gui.BasicContainer;
import com.iteye.weimingtom.guichan.gui.Widget;

public abstract class GceBase extends Widget {
	public static String colourStr(Color c) {
	    return String.format("%02x%02x%02x%02x", c.r, c.g, c.b, c.a);
	}
	
    public static void iterate(Widget widget, Element parent/*, int deep*/) {
    	EditorGui.currentWidget = widget;
        Element node = DocumentFactory.getInstance().createElement("widget");        
        GceEditPlugin plug = new ContainerEditPlugin();
        for (GceEditPlugin plug_ : EditorGui.plugins) {
            if (plug_.currentIsThisType()) {
            	plug = plug_;
                break;
            }
        }
        node.addAttribute("type", plug.componentName());
        parent.add(node);
        /*
        for (int i = 0; i < deep; i++) {
        	System.out.print("===");
        }
        System.out.println("====> " + deep + " parent : " + parent.attributeValue("type") + " node : " + node.attributeValue("type"));
        */
        // set base property widgets
        node.addAttribute("x", Integer.toString(EditorGui.currentWidget.getX()));
        node.addAttribute("y", Integer.toString(EditorGui.currentWidget.getY()));
        node.addAttribute("width", Integer.toString(EditorGui.currentWidget.getWidth()));
        node.addAttribute("height", Integer.toString(EditorGui.currentWidget.getHeight()));
        
        node.addAttribute("bordersize", Integer.toString(EditorGui.currentWidget.getFrameSize()));
        node.addAttribute("basecolour", colourStr(EditorGui.currentWidget.getBaseColor()));
        node.addAttribute("backgroundcolour", colourStr(EditorGui.currentWidget.getBackgroundColor()));
        node.addAttribute("foregroundcolour", colourStr(EditorGui.currentWidget.getForegroundColor()));
        node.addAttribute("selectioncolour",colourStr(EditorGui.currentWidget.getSelectionColor()));
        node.addAttribute("actioneventid", EditorGui.currentWidget.getActionEventId());
        node.addAttribute("widgetid", EditorGui.currentWidget.getId());
        
        plug.exportNodeExtended(node);

        if (plug.canParent()) {
            for (Widget w : ((BasicContainer)widget).getWidgets()) {
            	EditorGui.currentWidget = w;
                if (EditorGui.currentWidget != widget) {
                    for (GceEditPlugin plug_ : EditorGui.plugins) {
                    	
                    	//FIXME:
                    	EditorGui.currentWidget = w;
                    	
                    	if (plug_.currentIsThisType()) {
                        	iterate(EditorGui.currentWidget, node/*, deep + 1*/);
                        }
                    }
                }
            }
        }
        
        //FIXME:
        //EditorGui.currentWidget = widget;
    }
    
    public static void emptyMouseListeners(Widget widget) {
        widget._getMouseListeners().clear();
    }
    
    public static void exportNodeExtended(Widget container, Element node) {
    	
    }
    
    public static void deleteChildren(Widget widget) {
        EditorGui.currentWidget = widget;
        GceEditPlugin plug = null;
        for (GceEditPlugin plug_ : EditorGui.plugins) {
            if (plug_.currentIsThisType()) {
            	plug = plug_;
                break;
            }
        }
        if (plug != null && plug.canParent()) {
            for (Widget w : Widget.mWidgets) {
            	EditorGui.currentWidget = w;
                for (GceEditPlugin plug_ : EditorGui.plugins) {
                    if (plug_.currentIsThisType()) {
                    	//FIXME: java.lang.StackOverflowError
                        deleteChildren(EditorGui.currentWidget);
                    }
                }
            }
        }
        if (widget != EditorGui.top) {
        	EditorGui.widgetsToDelete.add(widget);
        }
    }
}
