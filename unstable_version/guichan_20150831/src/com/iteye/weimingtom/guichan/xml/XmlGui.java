package com.iteye.weimingtom.guichan.xml;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.SAXReader;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.platform.Font;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Image;
import com.iteye.weimingtom.guichan.platform.ListModel;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.CheckBox;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.DropDown;
import com.iteye.weimingtom.guichan.widget.Icon;
import com.iteye.weimingtom.guichan.widget.ImageButton;
import com.iteye.weimingtom.guichan.widget.Label;
import com.iteye.weimingtom.guichan.widget.ListBox;
import com.iteye.weimingtom.guichan.widget.RadioButton;
import com.iteye.weimingtom.guichan.widget.ScrollArea;
import com.iteye.weimingtom.guichan.widget.Slider;
import com.iteye.weimingtom.guichan.widget.TextBox;
import com.iteye.weimingtom.guichan.widget.TextField;
import com.iteye.weimingtom.guichan.widget.Window;

public class XmlGui {
	private Element rootValue = new DOMElement("");
	
	private Map<String, Widget> widgets = new HashMap<String, Widget>();
	private Map<String, XmlGuiParseFunc> extensionFuncs = new HashMap<String, XmlGuiParseFunc>(); 
	private Map<String, ActionListener> actions = new HashMap<String, ActionListener>();
	private Map<String, Font> fonts = new HashMap<String, Font>();
	
	public XmlGui() {
		
	}
	
	public boolean parse(String filePath) throws GuichanException {
		InputStream istr = null;
		Reader reader = null;
		try {
			istr = new FileInputStream(filePath);
			reader = new InputStreamReader(istr, "utf-8");
	        SAXReader saxReader = new SAXReader();
	        Document doc = saxReader.read(reader);
	        rootValue = doc.getRootElement(); 
	        parseWidget(rootValue, null);
			return true;
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
		return false;
	}
	
	public boolean parseByteArray(byte[] strData) throws GuichanException {
		InputStream istr = null;
		Reader reader = null;
		try {
			istr = new ByteArrayInputStream(strData);
			reader = new InputStreamReader(istr, "utf-8");
	        SAXReader saxReader = new SAXReader();
	        Document doc = saxReader.read(reader);
	        rootValue = doc.getRootElement(); 
			parseWidget(rootValue, null);
			return true;
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
		return false;
	}
	
	public boolean parseString(String str) throws GuichanException {
		if (str != null) {
			try {
				Document doc = DocumentHelper.parseText(str);
		        rootValue = doc.getRootElement(); 
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			parseWidget(rootValue, null);
			return true;
		}
		return false;
	}
	
    public void addToParent(Widget widget, Widget parent) throws GuichanException {
		if (parent == null) {
			return;
		}
		
        if (parent instanceof Container) {
        	Container cont = (Container)parent;
            cont.add(widget);
        } else {
            if (parent instanceof Window) {
            	Window wnd = (Window)parent;
                wnd.add(widget);
            } else {
                if (parent instanceof ScrollArea) {
                	ScrollArea scroll = (ScrollArea)parent;
                    scroll.setContent(widget);
                }
            }
        }
    }
	
	public Widget getWidget(String name) {
		return widgets.get(name);
	}
	
	public void registerParseFunc(String name, XmlGuiParseFunc func) {
		extensionFuncs.put(name, func);
	}
	
	public void addFont(String name, Font font) {
		if (font != null) {
			fonts.put(name, font);
		}
	}
	
	public void addActionListener(String name, ActionListener al) {
		if (al != null) {
			actions.put(name, al);
		}
	}
	
	
	
	
	
	
	
	
	
	
	private void _parseWidget(String name, Element val, Widget parent) throws GuichanException {
		if ("container".equals(name)) {
			parseContainer(val, parent);
		} else if ("window".equals(name)) {
			parseWindow(val, parent);
		} else if("scrollarea".equals(name)) {
			parseScrollArea(val, parent);
		} else if ("button".equals(name)) {
			parseButton(val, parent);
		} else if ("slider".equals(name)) {
			parseSlider(val, parent);
		} else if ("textbox".equals(name)) {
			parseTextBox(val, parent);
		} else if ("textfield".equals(name)) {
			parseTextField(val, parent);
		} else if ("label".equals(name)) {
			parseLabel(val, parent);
		} else if ("checkbox".equals(name)) {
			parseCheckbox(val, parent);
		} else if ("radiobutton".equals(name)) {
			parseRadioButton(val, parent);
		} else if ("imagebutton".equals(name)) {
			parseImageButton(val, parent);
		} else if ("icon".equals(name)) {
			parseIcon(val, parent);
		} else if ("listbox".equals(name)) {
			parseListBox(val, parent);
		} else if ("dropdown".equals(name)) {
			parseDropDown(val, parent);
		} else {
			XmlGuiParseFunc func = extensionFuncs.get(name);
			if (func != null) {
				func.xmlGuiParse(val, parent, this);
			}
		}
	}
	
    public void parseWidget(Element val, Widget parent) throws GuichanException {
        String type = val.getName();
        _parseWidget(type, val, parent);
    }
    
    private boolean hasAttr(Element val, String name) {
    	if (val == null) {
    		return false;
    	}
    	if (val.attribute(name) != null) {
    		return true;
    	}
    	return false;
    }
    
    private int getAttrInt(Element val, String name) {
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

    private String getAttrString(Element val, String name) {
    	if (val == null) {
    		return null;
    	}
    	Attribute attr = val.attribute(name);
    	if (attr == null) {
    		return null;
    	}
    	return attr.getValue();
    }

    private boolean getAttrBoolean(Element val, String name) {
    	if (val == null) {
    		return false;
    	}
    	Attribute attr = val.attribute(name);
    	if (attr == null) {
    		return false;
    	}
    	String value = attr.getValue();
    	if (value.equals("true")) {
    		return true;
    	}
    	return false;
    }

    private double getAttrDouble(Element val, String name) {
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
    
    public void parseDefaults(Element val, Widget widget) throws GuichanException {
        if (hasAttr(val, "x")) {
            widget.setX(getAttrInt(val, "x"));
        }
        if (hasAttr(val, "y")) {
            widget.setY(getAttrInt(val, "y"));
        }
        if (hasAttr(val, "width")) {
            widget.setWidth(getAttrInt(val, "width"));
        }
        if (hasAttr(val, "height")) {
            widget.setHeight(getAttrInt(val, "height"));
        }
        if (hasAttr(val, "basecolor")) {
            int color = 0;
            String basecolor = getAttrString(val, "basecolor");
            try {
            	color = Integer.parseInt(basecolor, 16);
            } catch (NumberFormatException e) {
            	
            }
            widget.setBaseColor(new Color(color));
        }
        if (hasAttr(val, "foregroundcolor")) {
            int color = 0;
            String foregroundcolor = getAttrString(val, "foregroundcolor");
            try {
            	color = Integer.parseInt(foregroundcolor, 16);
            } catch (NumberFormatException e) {
            	
            }
            widget.setForegroundColor(new Color(color));
        }
        if (hasAttr(val, "backgroundcolor")) {
            int color = 0;
            String backgroundcolor = getAttrString(val, "backgroundcolor");
            try {
            	color = Integer.parseInt(backgroundcolor, 16);
            } catch (NumberFormatException e) {
            	
            }
            widget.setBackgroundColor(new Color(color));
        }
        if (hasAttr(val, "framesize")) {
            widget.setFrameSize(getAttrInt(val, "framesize"));
        }
        if (hasAttr(val, "font")) {
    		String fontName = getAttrString(val, "font");
    		Font font = fonts.get(fontName);
    		if (font != null) {
    			widget.setFont(font);
    		}
        }
        if (hasAttr(val, "visible")) {
            widget.setVisible(getAttrBoolean(val, "visible"));
        }
        if (hasAttr(val, "focusable")) {
            widget.setFocusable(getAttrBoolean(val, "focusable"));
        }
        if (hasAttr(val, "enabled")) {
            widget.setEnabled(getAttrBoolean(val, "enabled"));
        }
        if (hasAttr(val, "tabin")) {
            widget.setTabInEnabled(getAttrBoolean(val, "tabin"));
        }
        if (hasAttr(val, "tabout")) {
            widget.setTabOutEnabled(getAttrBoolean(val, "tabout"));
        }
        if (hasAttr(val, "eventId")) {
            widget.setActionEventId(getAttrString(val, "eventId"));
        }
        if (hasAttr(val, "id")) {
            widget.setId(getAttrString(val, "id"));
        }
    	if (hasAttr(val, "actionListener")) {
    		widget.addActionListener(actions.get(getAttrString(val, "actionListener")));
    	}
    }
    
    public void parseContainer(Element val, Widget parent) throws GuichanException {
        String name = null;
        if (hasAttr(val, "name")) {
            name = getAttrString(val, "name");
        } else {
            throw new GuichanException("Container Widget must have a unique name");
        }
        
        Container c = new Container();
        
        if (hasAttr(val, "opaque")) {
            c.setOpaque(getAttrBoolean(val, "opaque"));
        }
        parseDefaults(val, c);
        
        //parsing child elements
    	List<?> members = val.elements();
    	Iterator<?> itMember = members.iterator();
        while (itMember.hasNext()) {
            Element value = (Element)itMember.next();
            parseWidget(value, c);
        }
    	
        addToParent(c, parent);
        widgets.put(name, c);
    }
    
    public void parseWindow(Element val, Widget parent) throws GuichanException {
        String name = null;
        if (hasAttr(val, "name")) {
            name = getAttrString(val, "name");
        } else {
            throw new GuichanException("Window Widget must have a unique name");
        }
        
        Window window = new Window();
        if (hasAttr(val, "caption")) {
            window.setCaption(getAttrString(val, "caption"));
        }
        if (hasAttr(val, "padding")) {
            window.setPadding(getAttrInt(val, "padding"));
        }
        if (hasAttr(val, "movable")) {
            window.setMovable(getAttrBoolean(val, "movable"));
        }
        if (hasAttr(val, "titleBarHeight")) {
            window.setTitleBarHeight(getAttrInt(val, "titleBarHeight"));
        }
        if (hasAttr(val, "opaque")) {
            window.setOpaque(getAttrBoolean(val, "opaque"));
        }
        
        //parsing child elements
    	List<?> members = val.elements();
    	Iterator<?> itMember = members.iterator();
        while (itMember.hasNext()) {
            Element value = (Element)itMember.next();
            parseWidget(value, window);
        }
		
		//FIXME:
		window.resizeToContent();
        
        parseDefaults(val, window);
        
        addToParent(window, parent);
        widgets.put(name, window);
    }
    
    public void parseScrollArea(Element val, Widget parent) throws GuichanException {
        String name = null;
        if (hasAttr(val, "name")) {
            name = getAttrString(val, "name");
        } else {
    		throw new GuichanException("ScrollArea Widget must have a unique name");
    	}

    	ScrollArea scroll = new ScrollArea();
    	
    	if (hasAttr(val, "hPolicy")) {
    		String hPolicy = getAttrString(val, "hPolicy");
    		if ("always".equals(hPolicy)) {
    			scroll.setHorizontalScrollPolicy(ScrollArea.ScrollPolicy.SHOW_ALWAYS);
    	    } else if ("never".equals(hPolicy)) {
    			scroll.setHorizontalScrollPolicy(ScrollArea.ScrollPolicy.SHOW_NEVER);
    	    }
    	}
    	if (hasAttr(val, "vPolicy")) {
    		String vPolicy = getAttrString(val, "vPolicy");
    		if ("always".equals(vPolicy)) {
    			scroll.setVerticalScrollPolicy(ScrollArea.ScrollPolicy.SHOW_ALWAYS);
    	    } else if ("never".equals(vPolicy)) {
    			scroll.setVerticalScrollPolicy(ScrollArea.ScrollPolicy.SHOW_NEVER);
    	    }
    	}
    	if (hasAttr(val, "vScrollAmount")) {
    		scroll.setVerticalScrollAmount(getAttrInt(val, "vScrollAmount"));
    	}
    	if (hasAttr(val, "hScrollAmount")) {
    		scroll.setHorizontalScrollAmount(getAttrInt(val, "hScrollAmount"));
    	}
    	if (hasAttr(val, "scrollBarWidth")) {
    		scroll.setScrollbarWidth(getAttrInt(val, "scrollbarWidth"));
    	}
    	if (hasAttr(val, "content")) {
    		Widget content = getWidget(getAttrString(val, "content"));
    		if (content != null) {
    			scroll.setContent(content);
    		}
    	}

        //parsing child elements
    	List<?> members = val.elements();
    	Iterator<?> itMember = members.iterator();
        while (itMember.hasNext()) {
            Element value = (Element)itMember.next();
            parseWidget(value, scroll);
        }
		
        parseDefaults(val, scroll);

    	addToParent(scroll, parent);

    	widgets.put(name, scroll);
    }
    
    public void parseButton(Element val, Widget parent) throws GuichanException {
        String name = null;
        if (hasAttr(val, "name")) {
            name = getAttrString(val, "name");
        } else {
            throw new GuichanException("Button Widget must have a unique name");
        }
        
        Button button = new Button();
        if (hasAttr(val, "caption")) {
            button.setCaption(getAttrString(val, "caption"));
        }
        if (hasAttr(val, "align")) {
            String align = getAttrString(val, "align");
            if ("center".equals(align)) {
                button.setAlignment(Graphics.Alignment.CENTER);
            } else if ("left".equals(align)) {
                button.setAlignment(Graphics.Alignment.LEFT);
            } else {
                button.setAlignment(Graphics.Alignment.RIGHT);
            }
        }
        
        button.adjustSize();
        parseDefaults(val, button);
		
        addToParent(button, parent);
        widgets.put(name, button);
    }
    
    public void parseSlider(Element val, Widget parent) throws GuichanException {
        String name = null;
        if (hasAttr(val, "name")) {
            name = getAttrString(val, "name");
        } else {
            throw new GuichanException("Slider Widget must have a unique name");
        }
        
        Slider slider = new Slider();
        if (hasAttr(val, "start")) {            
            slider.setScaleStart(getAttrDouble(val, "start"));
        }
        if (hasAttr(val, "end")) {
            slider.setScaleEnd(getAttrDouble(val, "end"));
        }
        if (hasAttr(val, "value")) {
            slider.setValue(getAttrDouble(val, "value"));
        }
        if (hasAttr(val, "markerLength")) {
            slider.setMarkerLength(getAttrInt(val, "markerLength"));
        }
        if (hasAttr(val, "stepLength")) {
            slider.setStepLength(getAttrDouble(val, "stepLength"));
        }
        if (hasAttr(val, "orientation")) {
        	String orientation = getAttrString(val, "orientation");
            if ("horizontal".equals(orientation)) {
                slider.setOrientation(Slider.Orientation.HORIZONTAL);
            } else {
            	slider.setOrientation(Slider.Orientation.VERTICAL);
            }
        }
        
        parseDefaults(val, slider);
        
        addToParent(slider, parent);
        widgets.put(name, slider);
    }
    
    public void parseLabel(Element val, Widget parent) throws GuichanException {
        String name = null;
        if (hasAttr(val, "name")) {
            name = getAttrString(val, "name");
        } else {
            throw new GuichanException("Icon Widget must have a unique name");
        }
        
        Label label = new Label();
        if (hasAttr(val, "caption")) {
            label.setCaption(getAttrString(val, "caption"));
        }
        
        label.adjustSize();
        if (hasAttr(val, "align")) {
            String align = getAttrString(val, "align");
            if ("center".equals(align)) {
                label.setAlignment(Graphics.Alignment.CENTER);
            } else if ("left".equals(align)) {
                label.setAlignment(Graphics.Alignment.LEFT);
            } else {
                label.setAlignment(Graphics.Alignment.RIGHT);
            }
        }
        
        parseDefaults(val, label);
        
        addToParent(label, parent);
        widgets.put(name, label);
    }
    
    public void parseImageButton(Element val, Widget parent) throws GuichanException {
        String name = null;
        if (hasAttr(val, "name")) {
            name = getAttrString(val, "name");
        } else {
            throw new GuichanException("Icon Widget must have a unique name");
        }
        
        ImageButton pButton = null;
        Image image = null;
        
        if (hasAttr(val, "image")) {
            image = Image.load(getAttrString(val, "image"));
        }
        if (image != null) {
            pButton = new ImageButton(image);
            parseDefaults(val, pButton);
            
            addToParent(pButton, parent);
            widgets.put(name, pButton);
        }    	
    }
    
    public void parseIcon(Element val, Widget parent) throws GuichanException {
        String name = null;
        if (hasAttr(val, "name")) {
            name = getAttrString(val, "name");
        } else {
            throw new GuichanException("Icon Widget must have a unique name");
        }
        
        Icon icon = null;
        Image image = null;
        
        if (hasAttr(val, "image")) {
            image = Image.load(getAttrString(val, "image"));
        }
        
        if (image != null) {
            icon = new Icon(image);
            
            parseDefaults(val, icon);
            
            addToParent(icon, parent);
            widgets.put(name, icon);
        }
    }
    
    
    
    public void parseCheckbox(Element val, Widget parent) throws GuichanException {
        String name;
        if (hasAttr(val, "name")) {
            name = getAttrString(val, "name");
        } else {
            throw new GuichanException("Slider Widget must have a unique name");
        }
        
        CheckBox checkbox = new CheckBox();
        
        if (hasAttr(val, "caption")) {
            checkbox.setCaption(getAttrString(val, "caption"));
        }
        
        checkbox.adjustSize();
    
        parseDefaults(val, checkbox);

        addToParent(checkbox, parent);
        widgets.put(name, checkbox);
    }
    
    public void parseTextBox(Element val, Widget parent) throws GuichanException {
        String name;
        if (hasAttr(val, "name")) {
            name = getAttrString(val, "name");
        } else {
            throw new GuichanException("Slider Widget must have a unique name");
        }
        
        TextBox textbox = new TextBox();
        
        if (hasAttr(val, "editable")) {
            textbox.setEditable(getAttrBoolean(val, "editable"));
        }
        if (hasAttr(val, "text")) {
            textbox.setText(getAttrString(val, "text"));
        }
        if (hasAttr(val, "opaque")) {
            textbox.setOpaque(getAttrBoolean(val, "opaque"));
        }
        
        parseDefaults(val, textbox);
        
        addToParent(textbox, parent);
        widgets.put(name, textbox);
    }
    
    public void parseTextField(Element val, Widget parent) throws GuichanException {
        String name = null;
        if (hasAttr(val, "name")) {
            name = getAttrString(val, "name");
        } else {
            throw new GuichanException("Slider Widget must have a unique name");
        }
        
        TextField textField = new TextField();
        
        if (hasAttr(val, "text")) {
            textField.setText(getAttrString(val, "text"));
        }
        
        textField.adjustSize();
        
        parseDefaults(val, textField);
        
        addToParent(textField, parent);
        widgets.put(name, textField);
    }
    
    public void parseRadioButton(Element val, Widget parent) throws GuichanException {
        String name;
        if (hasAttr(val, "name")) {
            name = getAttrString(val, "name");
        } else {
            throw new GuichanException("Slider Widget must have a unique name");
        }
        
        RadioButton radio = new RadioButton();
        
        if (hasAttr(val, "caption")) {
            radio.setCaption(getAttrString(val, "caption"));
        }
        radio.adjustSize();
        
        if (hasAttr(val, "group")) {
            radio.setGroup(getAttrString(val, "group"));		
        }
        parseDefaults(val, radio);
        
        addToParent(radio, parent);
        widgets.put(name, radio);
    }
    
    
    public void parseListBox(Element val, Widget parent) throws GuichanException {
		String name = null;
        if (hasAttr(val, "name")) {
            name = getAttrString(val, "name");
        } else {
            throw new GuichanException("Slider Widget must have a unique name");
        }
		
		ListBox listbox = new ListBox();
		if (hasAttr(val, "selectioncolor")) {
			int color = 0;
			try {
				color = Integer.parseInt(getAttrString(val, "selectioncolor"), 16);
			} catch (NumberFormatException e) {
				
			}
            listbox.setSelectionColor(new Color(color));
		}
		
    	List<?> items = val.elements();
    	if (items != null) {
    		XmlListModel listModel = new XmlListModel();
			int len = items.size();
			for (int i = 0; i < len; ++i) {
				Element elem = (Element)items.get(i);
				listModel.pushElement(elem.getText());
			}
			listbox.setListModel(listModel);    		
    	}
		
		parseDefaults(val, listbox);
		addToParent(listbox, parent);
		widgets.put(name, listbox);
    }
	
    public void parseDropDown(Element val, Widget parent) throws GuichanException {
		String name = null;
        if (hasAttr(val, "name")) {
            name = getAttrString(val, "name");
        } else {
            throw new GuichanException("Slider Widget must have a unique name");
        }
		
		DropDown dropdown = new DropDown();
		if (hasAttr(val, "selectioncolor")) {
			int color = 0;
			try {
				color = Integer.parseInt(getAttrString(val, "selectioncolor"), 16);
			} catch (NumberFormatException e) {
				
			}
            dropdown.setSelectionColor(new Color(color));
		}
		
    	List<?> items = val.elements();
    	if (items != null) {
    		XmlListModel listModel = new XmlListModel();
			int len = items.size();
			for (int i = 0; i < len; ++i) {
				Element elem = (Element)items.get(i);
				listModel.pushElement(elem.getText());
			}
			dropdown.setListModel(listModel);    		
    	}
		
		parseDefaults(val, dropdown);
		addToParent(dropdown, parent);
		widgets.put(name, dropdown);
	}
	
	private final static class XmlListModel extends ListModel {
		private List<String> items = new ArrayList<String>();
		
		@Override
		public int getNumberOfElements() {
			return items.size(); 
		}

		@Override
		public String getElementAt(int i) {
			if (i < items.size()) {
				return items.get(i);
			}
			return "";
		}
		
//		public void removeElementAt(int i) {
//            if (i < items.size()) {
//                items.remove(i);
//            }
//        }
		
		public void pushElement(String str) { 
			items.add(str);
		}
	};
}
