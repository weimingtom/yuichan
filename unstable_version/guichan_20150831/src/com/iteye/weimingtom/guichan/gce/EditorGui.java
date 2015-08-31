package com.iteye.weimingtom.guichan.gce;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.font.ImageFont;
import com.iteye.weimingtom.guichan.gce.listener.GceActionListener;
import com.iteye.weimingtom.guichan.gce.listener.GlobalMouseListener;
import com.iteye.weimingtom.guichan.gce.model.GceExampleDummyListModel;
import com.iteye.weimingtom.guichan.gce.model.GceStringList;
import com.iteye.weimingtom.guichan.gce.plugins.GceEditPlugin;
import com.iteye.weimingtom.guichan.gce.widget.GceColourSelector;
import com.iteye.weimingtom.guichan.gui.Gui;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.DropDown;
import com.iteye.weimingtom.guichan.widget.Label;
import com.iteye.weimingtom.guichan.widget.ScrollArea;
import com.iteye.weimingtom.guichan.widget.Slider;
import com.iteye.weimingtom.guichan.widget.TextField;
import com.iteye.weimingtom.guichan.widget.Window;

public class EditorGui {
	public static final int SHEET_WIDTH = 160;
	public static final int SHEET_HEIGHT = 320;
	
	public static ImageFont labelFont;
	public static Container top;
	public static Container oldExtendedProps;
	public static Container extended;
	public static List<Widget> widgetsToDelete = new ArrayList<Widget>();
	
	public static GlobalMouseListener MListener = new GlobalMouseListener();
	
	public static DropDown sheetSelector;
	public static Container global;
	
	public static Widget currentWidget;
	//public static Container  top;
	public static Gui gui;
	public static Window mainWindow;

	//public static Container oldExtendedProps;
	//public static Container extended;
	//public static Container global;
	public static Container standard;
	public static Container components;
	public static Container lastSelected;

	//public static ImageFont labelFont;  

	//public static DropDown sheetSelector;

	//public static SDLImageLoader imageLoader;
	//std property widgets
	public static Slider borderSize;
	public static Label borderSizeLabel;
	public static GceColourSelector baseColour;
	public static Label baseColourLabel;
	public static GceColourSelector backgroundColour;
	public static Label backgroundColourLabel;
	public static GceColourSelector foregroundColour;
	public static Label foregroundColourLabel;
	public static GceColourSelector selectionColour;
	public static Label selectionColourLabel;
	public static TextField actionEventId;
	public static Label actionEventIdLabel;
	public static TextField widgetId;
	public static Label widgetIdLabel;

	public static Button parent;
	public static Button parentTop;
	public static Button del;

	public static int timesClicked;

	//public static List<Widget> widgetsToDelete = new ArrayList<Widget>();

	public static List<GceEditPlugin> plugins = new ArrayList<GceEditPlugin>();
	
	public static void updateBaseProperties() {
	    String s;
	    int i = currentWidget.getFrameSize();
	    s = String.format("Border Size : %d", i);
	    borderSizeLabel.setCaption(s);
	    borderSize.setValue(i);
	    
	    baseColour.setSelectedColour(currentWidget.getBaseColor());
	    backgroundColour.setSelectedColour(currentWidget.getBackgroundColor());
	    foregroundColour.setSelectedColour(currentWidget.getForegroundColor());
	    selectionColour.setSelectedColour(currentWidget.getSelectionColor());
	    actionEventId.setText(currentWidget.getActionEventId());
	    widgetId.setText(currentWidget.getId());
	}
	
	public static GceExampleDummyListModel dummyLM = new GceExampleDummyListModel();




	//-----------------------


	public static boolean running = true;

	//public static Gui gui;
	public static ImageFont font;
	//public static ImageFont labelFont;
	
	//public static Container top;
	//public static Window mainWindow;
	//public static Container global;
	//public static Container components;
	//public static Container extended;
	//public static Container standard;
	//public static Container oldExtendedProps;
	//public static Container lastSelected;
	//public static Widget currentWidget;
	
	//public static DropDown sheetSelector;          
	public static GceStringList sl = new GceStringList();

	//public static Slider borderSize;
	//public static Label borderSizeLabel;
	//public static GceColourSelector baseColour;
	//public static Label baseColourLabel;
	//public static GceColourSelector backgroundColour;
//	public static Label backgroundColourLabel;
//	public static GceColourSelector foregroundColour;
//	public static Label foregroundColourLabel;
//	public static GceColourSelector selectionColour;
//	public static Label selectionColourLabel;
//	public static TextField actionEventId;
//	public static Label actionEventIdLabel;
//	public static TextField widgetId;
//	public static Label widgetIdLabel;

	public static Button toFront;
	public static Button toBack;
//	public static Button parent;
//	public static Button parentTop;
//	public static Button del;

//	public static List<Widget> widgetsToDelete = new ArrayList<Widget>();
	
	public static GceActionListener guiListener = new GceActionListener();


	//============NOTE=============
	//format:rgba
	//============NOTE=============
	private static Color strToColour(String s) {
		int color = 0;
		int alpha = 0xff;
		String rgbStr = s;
		String alphaStr = "";
		if (rgbStr.length() > 6) {
			rgbStr = rgbStr.substring(0, 6);
			alphaStr = rgbStr.substring(6);
		}
		try {
        	color = Integer.parseInt(rgbStr, 16);
        } catch (NumberFormatException e) {
        	
        }
		try {
			alpha = Integer.parseInt(alphaStr, 16);
		} catch (NumberFormatException e) {
			alpha = 0xff;
		}
		Color result = new Color(color);
		result.a = alpha;
		System.out.println("s=" + s + ", result=" + result);
		return result;
	}
	
	private static Widget makeFromNode(Element node) throws GuichanException {
		Widget w = null;
	    
		GceEditPlugin plug = null;
		String typeAttr = node.attributeValue("type");
		System.out.println("typeAttr : " + typeAttr);
        for (GceEditPlugin plug_ : EditorGui.plugins) {
        	String componentName = plug_.componentName();
        	System.out.println("componentName : " + componentName);
        	if (componentName.equals(typeAttr)) {
            	w = plug_.newInstance();
                plug = plug_;
            	break;
            }
        }
		
	    int i;
	    System.out.println("w : " + w);
	    System.out.println("widgetid : " + node.attributeValue("widgetid"));    
	    w.setBaseColor(strToColour(node.attributeValue("basecolour")));
	    w.setBackgroundColor(strToColour(node.attributeValue("backgroundcolour")));
	    w.setForegroundColor(strToColour(node.attributeValue("foregroundcolour")));
	    w.setSelectionColor(strToColour(node.attributeValue("selectioncolour")));
	    
	    i = queryIntAttribute(node, "x");
	    w.setX(i);
	    i = queryIntAttribute(node, "y");
	    w.setY(i);
	    i = queryIntAttribute(node, "width");
	    w.setWidth(i);
	    i = queryIntAttribute(node, "height");
	    w.setHeight(i);
	    i = queryIntAttribute(node, "bordersize");
	    //w->setBorderSize(i);
	    w.setFrameSize(i);

	    w.setActionEventId(node.attributeValue("actioneventid"));
	    w.setId(node.attributeValue("widgetid"));
	    
	    currentWidget = w;
	    plug.setExtendedFromNode(node);
	    return w;
	}
	
	private static int queryIntAttribute(Element val, String name) {
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
	
	public static void iterateXml(Widget p, Iterator<Element> node) throws GuichanException {
	    if (node == null) {
	    	return;
	    }
	    
	    while (node.hasNext()) {
	    	Element pnode = node.next();
	    	Widget w = makeFromNode(pnode);

	        if (p instanceof ScrollArea) {
	        	System.out.println("setting as contents of scroll area");
	            ((ScrollArea)p).setContent(w);
	        } else {
	            ((Container)p).add(w);
	        }

	        // check if perentable if it is do iterateXml(w,pnode)
	        if (pnode != null) {
	            for (GceEditPlugin plug_ : EditorGui.plugins) {
	                if (plug_.componentName().equals(pnode.attributeValue("type"))) {
	                	if (plug_.canParent()) {
	                		 iterateXml(w, pnode.elementIterator());
	                	}
	                }
	            }
	        }
	    }
	}
	
	//FIXME:
	public static boolean parentMode = false;
	public static boolean deleteMode = false;
	public static Widget oldCurrent;
}
