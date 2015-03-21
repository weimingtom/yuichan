package com.iteye.weimingtom.guichan.gce;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gce.plugins.GcePlugin;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.ScrollArea;

public class GceLoader {
	public static Container loadGui(String filename) throws GuichanException {
	    Container top = null;
		InputStream istr = null;
		Reader reader = null;
		try {
			istr = new FileInputStream(filename);
			reader = new InputStreamReader(istr, "utf-8");
	        SAXReader saxReader = new SAXReader();
	        Document doc = saxReader.read(reader);
	        if (doc != null) {
		        Element root = doc.getRootElement();
		        Element topNode = (Element)root.elements().get(0); 
		        top = (Container)makeFromNode(topNode);
		        iterateXml((Widget)top, topNode.elementIterator());
		    }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (istr != null) {
				try {
					istr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	    return top;
	}
	
	private static Widget makeFromNode(Element node) throws GuichanException {
	    Widget w = null;
	    GcePlugin plugin = null;
	    for (GcePlugin plug : GcePluginManager.plugins) {
	    	String type = queryStringAttribute(node, "type");
	        if (plug.componentName().equals(type)) {
	            w = plug.newInstance();
	            plugin = plug;
	            break;
	        }
	    } 
	    if (w != null) {
		    w.setBaseColor(strToColour(queryStringAttribute(node, "basecolour")));
		    w.setBackgroundColor(strToColour(queryStringAttribute(node, "backgroundcolour")));
		    w.setForegroundColor(strToColour(queryStringAttribute(node, "foregroundcolour")));
		    w.setSelectionColor(strToColour(queryStringAttribute(node, "selectioncolour")));
		    w.setX(queryIntAttribute(node, "x"));
		    w.setY(queryIntAttribute(node, "y"));
		    w.setWidth(queryIntAttribute(node, "width"));
		    w.setHeight(queryIntAttribute(node, "height"));
		    w.setFrameSize(queryIntAttribute(node, "bordersize"));
		    w.setActionEventId(queryStringAttribute(node, "actioneventid"));
		    w.setId(queryStringAttribute(node, "widgetid"));
		    System.out.println("w.id:" + w.getId());
		    System.out.println("w.aid:" + w.getActionEventId());
		    plugin.setExtendedFromNode(node, w);
	    }
	    return w;
	}
	
	private static void iterateXml(Widget p, Iterator<?> iterator) throws GuichanException {
	    if (!iterator.hasNext()) {
	    	return;
	    }
	    while (iterator.hasNext()) {
	    	Element node = (Element)iterator.next();
	    	System.out.println(node.attribute("type").getValue());
		    Widget w = makeFromNode(node);
	        if (p instanceof ScrollArea) {
	            ((ScrollArea)p).setContent(w);
	        } else {
	            ((Container)p).add(w);
	        }
	        for (GcePlugin plug: GcePluginManager.plugins) {
	        	if (plug.componentName().equals(node.attribute("type").getValue())) {
	        		if (plug.canParent()) {
	        			iterateXml((Widget)w, node.elementIterator());
	        		} else {
	        			break;
	                }
	            }
	        }
	    }
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
    
	private static String queryStringAttribute(Element val, String name) {
    	if (val == null) {
    		return null;
    	}
    	Attribute attr = val.attribute(name);
    	if (attr == null) {
    		return null;
    	}
    	return attr.getValue();
    }
    
	protected static double queryDoubleAttribute(Element val, String name) {
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
}
