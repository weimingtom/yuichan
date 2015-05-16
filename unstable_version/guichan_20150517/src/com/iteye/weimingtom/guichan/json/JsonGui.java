package com.iteye.weimingtom.guichan.json;

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
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

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

public class JsonGui {
	private JSONObject rootValue = new JSONObject();
	
	private Map<String, Widget> widgets = new HashMap<String, Widget>();
	private Map<String, JsonGuiParseFunc> extensionFuncs = new HashMap<String, JsonGuiParseFunc>(); 
	private Map<String, ActionListener> actions = new HashMap<String, ActionListener>();
	private Map<String, Font> fonts = new HashMap<String, Font>();
	
	public JsonGui() {
		
	}
	
	public boolean parse(String filePath) throws GuichanException {
		InputStream istr = null;
		Reader reader = null;
		try {
			istr = new FileInputStream(filePath);
			reader = new InputStreamReader(istr, "utf-8");
			JSONTokener tokener = new JSONTokener(reader);
			rootValue = new JSONObject(tokener);
			parseWidget(rootValue, null);
			return true;
		} catch (IOException e) {
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
			JSONTokener tokener = new JSONTokener(reader);
			rootValue = new JSONObject(tokener);
			parseWidget(rootValue, null);
			return true;
		} catch (IOException e) {
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
			rootValue = new JSONObject(str);
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
	
	public void registerParseFunc(String name, JsonGuiParseFunc func) {
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
	
	
	
	
	
	
	
	private void _parseWidget(String name, JSONObject val, Widget parent) throws GuichanException {
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
			JsonGuiParseFunc func = extensionFuncs.get(name);
			if (func != null) {
				func.jsonGuiParse(val, parent, this);
			}
		}
	}
	
    public void parseWidget(JSONObject val, Widget parent) throws GuichanException {
    	Set<String> members = val.keySet();
    	Iterator<String> itMember = members.iterator();
        while (itMember.hasNext()) {
            String type = itMember.next();
            Object value = val.opt(type);
            if (value != null) {
	            if (value instanceof JSONObject) {
					_parseWidget(type, (JSONObject)value, parent);
				} else if (value instanceof JSONArray) {
					if (type != null && "widgets".equals(type)) {
						JSONArray arrValue = (JSONArray)value;
						int len = arrValue.length();
						for (int i = 0; i < len; ++i) {
							Object value2 = arrValue.get(i);
							if (value2 instanceof JSONObject) {
								parseWidget((JSONObject)value2, parent);
							}
						}
					}
				}
            }
        }
    }
    
    public void parseDefaults(JSONObject val, Widget widget) throws GuichanException {
        if (val.has("x")) {
            widget.setX(val.optInt("x"));
        }
        if (val.has("y")) {
            widget.setY(val.optInt("y"));
        }
        if (val.has("width")) {
            widget.setWidth(val.optInt("width"));
        }
        if (val.has("height")) {
            widget.setHeight(val.optInt("height"));
        }
        if (val.has("basecolor")) {
            int color = 0;
            String basecolor = val.optString("basecolor");
            try {
            	color = Integer.parseInt(basecolor, 16);
            } catch (NumberFormatException e) {
            	
            }
            widget.setBaseColor(new Color(color));
        }
        if (val.has("foregroundcolor")) {
            int color = 0;
            String foregroundcolor = val.optString("foregroundcolor");
            try {
            	color = Integer.parseInt(foregroundcolor, 16);
            } catch (NumberFormatException e) {
            	
            }
            widget.setForegroundColor(new Color(color));
        }
        if (val.has("backgroundcolor")) {
            int color = 0;
            String backgroundcolor = val.optString("backgroundcolor");
            try {
            	color = Integer.parseInt(backgroundcolor, 16);
            } catch (NumberFormatException e) {
            	
            }
            widget.setBackgroundColor(new Color(color));
        }
        if (val.has("framesize")) {
            widget.setFrameSize(val.optInt("framesize"));
        }
        if (val.has("font")) {
    		String fontName = val.optString("font");
    		Font font = fonts.get(fontName);
    		if (font != null) {
    			widget.setFont(font);
    		}
        }
        if (val.has("visible")) {
            widget.setVisible(val.optBoolean("visible"));
        }
        if (val.has("focusable")) {
            widget.setFocusable(val.optBoolean("focusable"));
        }
        if (val.has("enabled")) {
            widget.setEnabled(val.optBoolean("enabled"));
        }
        if (val.has("tabin")) {
            widget.setTabInEnabled(val.optBoolean("tabin"));
        }
        if (val.has("tabout")) {
            widget.setTabOutEnabled(val.optBoolean("tabout"));
        }
        if (val.has("eventId")) {
            widget.setActionEventId(val.optString("eventId"));
        }
        if (val.has("id")) {
            widget.setId(val.optString("id"));
        }
        if (val.has("actionListener")) {
    		widget.addActionListener(actions.get(val.optString("actionListener")));
    	}
    }
    
    public void parseContainer(JSONObject val, Widget parent) throws GuichanException {
        String name = null;
        if (val.has("name")) {
            name = val.optString("name");
        } else {
            throw new GuichanException("Container Widget must have a unique name");
        }
        
        Container c = new Container();
        
        if (val.has("opaque")) {
            c.setOpaque(val.optBoolean("opaque"));
        }
        parseDefaults(val, c);
        
        //parsing child elements
		if (val.has("widgets")) {
			Object widgets = val.opt("widgets");
			if (widgets instanceof JSONArray) {
				JSONArray arrWidgets = (JSONArray) widgets;
				int len = arrWidgets.length();
				for (int i = 0; i < len; ++i) {
					Object widget = arrWidgets.opt(i);
					if (widget != null && widget instanceof JSONObject) {
						parseWidget((JSONObject)widget, c);
					}
				}
			}
        }
		
        addToParent(c, parent);
        widgets.put(name, c);
    }
    
    public void parseWindow(JSONObject val, Widget parent) throws GuichanException {
        String name = null;
        if (val.has("name")) {
            name = val.optString("name");
        } else {
            throw new GuichanException("Window Widget must have a unique name");
        }
        
        Window window = new Window();
        if (val.has("caption")) {
            window.setCaption(val.optString("caption"));
        }
        if (val.has("padding")) {
            window.setPadding(val.optInt("padding"));
        }
        if (val.has("movable")) {
            window.setMovable(val.optBoolean("movable"));
        }
        if (val.has("titleBarHeight")) {
            window.setTitleBarHeight(val.optInt("titleBarHeight"));
        }
        if (val.has("opaque")) {
            window.setOpaque(val.optBoolean("opaque"));
        }
        
        //parsing child elements
		if (val.has("widgets")) {
			Object widgets = val.opt("widgets");
			if (widgets instanceof JSONArray) {
				JSONArray arrWidgets = (JSONArray) widgets;
				int len = arrWidgets.length();
				for (int i = 0; i < len; ++i) {
					Object widget2 = arrWidgets.get(i);
					if (widget2 != null && widget2 instanceof JSONObject) {
						parseWidget((JSONObject)widget2, window);
					}
				}
			}
        }
		
		//FIXME:
		window.resizeToContent();
        
        parseDefaults(val, window);
        
        addToParent(window, parent);
        widgets.put(name, window);
    }
    
    public void parseScrollArea(JSONObject val, Widget parent) throws GuichanException {
        String name = null;
        if (val.has("name")) {
            name = val.optString("name");
        } else {
    		throw new GuichanException("ScrollArea Widget must have a unique name");
    	}

    	ScrollArea scroll = new ScrollArea();
    	
    	if (val.has("hPolicy")) {
    		String hPolicy = val.optString("hPolicy");
    		if ("always".equals(hPolicy)) {
    			scroll.setHorizontalScrollPolicy(ScrollArea.ScrollPolicy.SHOW_ALWAYS);
    	    } else if ("never".equals(hPolicy)) {
    			scroll.setHorizontalScrollPolicy(ScrollArea.ScrollPolicy.SHOW_NEVER);
    	    }
    	}
    	if (val.has("vPolicy")) {
    		String vPolicy = val.optString("vPolicy");
    		if ("always".equals(vPolicy)) {
    			scroll.setVerticalScrollPolicy(ScrollArea.ScrollPolicy.SHOW_ALWAYS);
    	    } else if ("never".equals(vPolicy)) {
    			scroll.setVerticalScrollPolicy(ScrollArea.ScrollPolicy.SHOW_NEVER);
    	    }
    	}
    	if (val.has("vScrollAmount")) {
    		scroll.setVerticalScrollAmount(val.optInt("vScrollAmount"));
    	}
    	if (val.has("hScrollAmount")) {
    		scroll.setHorizontalScrollAmount(val.optInt("hScrollAmount"));
    	}
    	if (val.has("scrollBarWidth")) {
    		scroll.setScrollbarWidth(val.optInt("scrollbarWidth"));
    	}
    	if (val.has("content")) {
    		Widget content = getWidget(val.optString("content"));
    		if (content != null) {
    			scroll.setContent(content);
    		}
    	}

        //parsing child elements
		if (val.has("widgets")) {
			Object widgets = val.opt("widgets");
			if (widgets instanceof JSONArray) {
				JSONArray arrWidgets = (JSONArray) widgets;
				int len = arrWidgets.length();
				for (int i = 0; i < len; ++i) {
					Object widget2 = arrWidgets.get(i);
					if (widget2 != null && widget2 instanceof JSONObject) {
						parseWidget((JSONObject)widget2, scroll);
					}
				}
			}
        }
		
        parseDefaults(val, scroll);

    	addToParent(scroll, parent);

    	widgets.put(name, scroll);
    }
    
    public void parseButton(JSONObject val, Widget parent) throws GuichanException {
        String name = null;
        if (val.has("name")) {
            name = val.optString("name");
        } else {
            throw new GuichanException("Button Widget must have a unique name");
        }
        
        Button button = new Button();
        if (val.has("caption")) {
            button.setCaption(val.optString("caption"));
        }
        if (val.has("align")) {
            String align = val.optString("align");
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
    
    public void parseSlider(JSONObject val, Widget parent) throws GuichanException {
        String name = null;
        if (val.has("name")) {
            name = val.optString("name");
        } else {
            throw new GuichanException("Slider Widget must have a unique name");
        }
        
        Slider slider = new Slider();
        if (val.has("start")) {            
            slider.setScaleStart(val.optDouble("start"));
        }
        if (val.has("end")) {
            slider.setScaleEnd(val.optDouble("end"));
        }
        if (val.has("value")) {
            slider.setValue(val.optDouble("value"));
        }
        if (val.has("markerLength")) {
            slider.setMarkerLength(val.optInt("markerLength"));
        }
        if (val.has("stepLength")) {
            slider.setStepLength(val.optDouble("stepLength"));
        }
        if (val.has("orientation")) {
        	String orientation = val.optString("orientation");
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
    
    public void parseLabel(JSONObject val, Widget parent) throws GuichanException {
        String name = null;
        if (val.has("name")) {
            name = val.optString("name");
        } else {
            throw new GuichanException("Icon Widget must have a unique name");
        }
        
        Label label = new Label();
        if (val.has("caption")) {
            label.setCaption(val.optString("caption"));
        }
        
        label.adjustSize();
        if (val.has("align")) {
            String align = val.optString("align");
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
    
    public void parseImageButton(JSONObject val, Widget parent) throws GuichanException {
        String name = null;
        if (val.has("name")) {
            name = val.optString("name");
        } else {
            throw new GuichanException("Icon Widget must have a unique name");
        }
        
        ImageButton pButton = null;
        Image image = null;
        
        if (val.has("image")) {
            image = Image.load(val.optString("image"));
        }
        if (image != null) {
            pButton = new ImageButton(image);
            parseDefaults(val, pButton);
            
            addToParent(pButton, parent);
            widgets.put(name, pButton);
        }    	
    }
    
    public void parseIcon(JSONObject val, Widget parent) throws GuichanException {
        String name = null;
        if (val.has("name")) {
            name = val.optString("name");
        } else {
            throw new GuichanException("Icon Widget must have a unique name");
        }
        
        Icon icon = null;
        Image image = null;
        
        if (val.has("image")) {
            image = Image.load(val.optString("image"));
        }
        
        if (image != null) {
            icon = new Icon(image);
            
            parseDefaults(val, icon);
            
            addToParent(icon, parent);
            widgets.put(name, icon);
        }
    }
    
    
    
    public void parseCheckbox(JSONObject val, Widget parent) throws GuichanException {
        String name;
        if (val.has("name")) {
            name = val.optString("name");
        } else {
            throw new GuichanException("Slider Widget must have a unique name");
        }
        
        CheckBox checkbox = new CheckBox();
        
        if (val.has("caption")) {
            checkbox.setCaption(val.optString("caption"));
        }
        
        checkbox.adjustSize();
    
        parseDefaults(val, checkbox);

        addToParent(checkbox, parent);
        widgets.put(name, checkbox);
    }
    
    public void parseTextBox(JSONObject val, Widget parent) throws GuichanException {
        String name;
        if (val.has("name")) {
            name = val.optString("name");
        } else {
            throw new GuichanException("Slider Widget must have a unique name");
        }
        
        TextBox textbox = new TextBox();
        
        if (val.has("editable")) {
            textbox.setEditable(val.optBoolean("editable"));
        }
        if (val.has("text")) {
            textbox.setText(val.optString("text"));
        }
        if (val.has("opaque")) {
            textbox.setOpaque(val.optBoolean("opaque"));
        }
        
        parseDefaults(val, textbox);
        
        addToParent(textbox, parent);
        widgets.put(name, textbox);
    }
    
    public void parseTextField(JSONObject val, Widget parent) throws GuichanException {
        String name = null;
        if (val.has("name")) {
            name = val.optString("name");
        } else {
            throw new GuichanException("Slider Widget must have a unique name");
        }
        
        TextField textField = new TextField();
        
        if (val.has("text")) {
            textField.setText(val.optString("text"));
        }
        
        textField.adjustSize();
        
        parseDefaults(val, textField);
        
        addToParent(textField, parent);
        widgets.put(name, textField);
    }
    
    public void parseRadioButton(JSONObject val, Widget parent) throws GuichanException {
        String name;
        if (val.has("name")) {
            name = val.optString("name");
        } else {
            throw new GuichanException("Slider Widget must have a unique name");
        }
        
        RadioButton radio = new RadioButton();
        
        if (val.has("caption")) {
            radio.setCaption(val.optString("caption"));
        }
        radio.adjustSize();
        
        if (val.has("group")) {
            radio.setGroup(val.optString("group"));		
        }
        parseDefaults(val, radio);
        
        addToParent(radio, parent);
        widgets.put(name, radio);
    }
    
    
    public void parseListBox(JSONObject val, Widget parent) throws GuichanException {
		String name = null;
        if (val.has("name")) {
            name = val.optString("name");
        } else {
            throw new GuichanException("Slider Widget must have a unique name");
        }
		
		ListBox listbox = new ListBox();
		if (val.has("selectioncolor")) {
			int color = 0;
			try {
				color = Integer.parseInt(val.optString("selectioncolor"), 16);
			} catch (NumberFormatException e) {
				
			}
            listbox.setSelectionColor(new Color(color));
		}
		
		if (val.has("items")) {
			Object items = val.opt("items");
			if(items instanceof JSONArray) {
				JSONArray arrItems = (JSONArray)items;
				JsonListModel listModel = new JsonListModel();
				int len = arrItems.length();
				for (int i = 0; i < len; ++i) {
					listModel.pushElement(arrItems.optString(i));
				}
				listbox.setListModel(listModel);
			} 
		}
		
		parseDefaults(val, listbox);
		addToParent(listbox, parent);
		widgets.put(name, listbox);
    }
	
    public void parseDropDown(JSONObject val, Widget parent) throws GuichanException {
		String name = null;
        if (val.has("name")) {
            name = val.optString("name");
        } else {
            throw new GuichanException("Slider Widget must have a unique name");
        }
		
		DropDown dropdown = new DropDown();
		if (val.has("selectioncolor")) {
			int color = 0;
			try {
				color = Integer.parseInt(val.optString("selectioncolor"), 16);
			} catch (NumberFormatException e) {
				
			}
            dropdown.setSelectionColor(new Color(color));
		}
		
		if (val.has("items")) {
			Object items = val.opt("items");
			if(items instanceof JSONArray) {
				JSONArray arrItems = (JSONArray)items;
				JsonListModel listModel = new JsonListModel();
				int len = arrItems.length();
				for (int i = 0; i < len; ++i) {
					listModel.pushElement(arrItems.optString(i));
				}
				dropdown.setListModel(listModel);
			} 
		}
		
		parseDefaults(val, dropdown);
		addToParent(dropdown, parent);
		widgets.put(name, dropdown);
	}
	
	private final static class JsonListModel extends ListModel {
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
