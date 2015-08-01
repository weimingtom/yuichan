package com.iteye.weimingtom.guichan.gce;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.font.ImageFont;
import com.iteye.weimingtom.guichan.gce.model.GceStringList;
import com.iteye.weimingtom.guichan.gce.plugins.GceEditPlugin;
import com.iteye.weimingtom.guichan.gce.widget.GceColourSelector;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ApplicationListener;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.DropDown;
import com.iteye.weimingtom.guichan.widget.Label;
import com.iteye.weimingtom.guichan.widget.Slider;
import com.iteye.weimingtom.guichan.widget.TextField;
import com.iteye.weimingtom.guichan.widget.Window;

public class GceEditor implements ApplicationListener {
	public void initWidgets() throws GuichanException {
	    EditorGui.oldExtendedProps = null;
	    
	    EditorGui.mainWindow = new Window("GCe");
	    EditorGui.mainWindow.setBaseColor(new Color(0, 64, 128, 128));
	    EditorGui.mainWindow.setSize(160, 480);
	    EditorGui.top.add(EditorGui.mainWindow, 800 - 176, 16);
	    
	    EditorGui.standard = new Container();
	    EditorGui.standard.setBaseColor(new Color(0, 64, 128, 0));
	    EditorGui.standard.setSize(160,480-32);

	    EditorGui.extended = new Container();
	    EditorGui.extended.setBaseColor(new Color(0, 64, 128, 0));
	    EditorGui.extended.setSize(160,480 - 32);    
	    
	    EditorGui.global = new Container();
	    EditorGui.global.setBaseColor(new Color(0, 64, 128, 0));
	    EditorGui.global.setSize(160,480-32);    

	    EditorGui.components = new Container();
	    EditorGui.components.setBaseColor(new Color(0, 64, 128, 0));
	    EditorGui.components.setSize(160,480-32);
	    
	    EditorGui.sl = new GceStringList();
	    EditorGui.sl.addString("Global");
	    EditorGui.sl.addString("Components");
	    EditorGui.sl.addString("Properties");
	    EditorGui.sl.addString("Extra Props");

	    EditorGui.sheetSelector = new DropDown(EditorGui.sl);
	    EditorGui.sheetSelector.setWidth(160 - 16);
	    EditorGui.sheetSelector.setActionEventId("sheetSelector");
	    EditorGui.sheetSelector.addActionListener(EditorGui.guiListener);
	    EditorGui.mainWindow.add(EditorGui.sheetSelector, 8, 8);

	    EditorGui.mainWindow.add(EditorGui.global, 0, 32);
	    EditorGui.lastSelected = EditorGui.global;

	    Button save = new Button("Save");
	    EditorGui.global.add(save, 8, 0);
	    save.addActionListener(EditorGui.guiListener);
	    save.setActionEventId("save");
	    
	    Button load = new Button("Load");
	    EditorGui.global.add(load, 8, 30);
	    load.addActionListener(EditorGui.guiListener);
	    load.setActionEventId("load");
	    
	    EditorGui.del = new Button("Delete Widget");
	    EditorGui.global.add(EditorGui.del, 8, 60);
	    EditorGui.del.addActionListener(EditorGui.guiListener);
	    EditorGui.del.setActionEventId("delete");

	    EditorGui.parent = new Button("Parent");
	    EditorGui.parent.addActionListener(EditorGui.guiListener);
	    EditorGui.parent.setActionEventId("parent");
	    EditorGui.parentTop = new Button("Parent top");
	    EditorGui.parentTop.addActionListener(EditorGui.guiListener);
	    EditorGui.parentTop.setActionEventId("parentTop");

	    EditorGui.toBack = new Button("To back ");
	    EditorGui.toFront = new Button("To front");
	    EditorGui.toBack.addActionListener(EditorGui.guiListener);
	    EditorGui.toBack.setActionEventId("toBack");
	    EditorGui.toFront.addActionListener(EditorGui.guiListener);
	    EditorGui.toFront.setActionEventId("toFront");

	    EditorGui.global.add(EditorGui.toBack, 8, 90);
	    EditorGui.global.add(EditorGui.toFront, 8, 120);
	    EditorGui.global.add(EditorGui.parent, 8, 150);
	    EditorGui.global.add(EditorGui.parentTop, 8, 180);

	    EditorGui.borderSize = new Slider(32);
	    EditorGui.borderSize.setSize(100, 10);
	    EditorGui.borderSize.setActionEventId("borderSize");
	    EditorGui.borderSize.addActionListener(EditorGui.guiListener);
	    EditorGui.borderSizeLabel = new Label("Border Size");
	    EditorGui.borderSizeLabel.setFont(EditorGui.labelFont);
	    
	    EditorGui.baseColour = new GceColourSelector();
	    EditorGui.baseColour.addActionListener(EditorGui.guiListener);
	    EditorGui.baseColour.mActionId = "baseColour";
	    EditorGui.baseColourLabel = new Label("Base Colour");
	    EditorGui.baseColourLabel.setFont(EditorGui.labelFont);
	    
	    EditorGui.backgroundColour = new GceColourSelector();
	    EditorGui.backgroundColour.addActionListener(EditorGui.guiListener);
	    EditorGui.backgroundColour.mActionId = "backgroundColour";
	    EditorGui.backgroundColourLabel = new Label("Background Colour");
	    EditorGui.backgroundColourLabel.setFont(EditorGui.labelFont);
	    
	    EditorGui.foregroundColour = new GceColourSelector();
	    EditorGui.foregroundColour.addActionListener(EditorGui.guiListener);
	    EditorGui.foregroundColour.mActionId = "foregroundColour";
	    EditorGui.foregroundColourLabel = new Label("Foreground Colour");
	    EditorGui.foregroundColourLabel.setFont(EditorGui.labelFont);
	    
	    EditorGui.selectionColour = new GceColourSelector();
	    EditorGui.selectionColour.addActionListener(EditorGui.guiListener);
	    EditorGui.selectionColour.mActionId = "selectionColour";
	    EditorGui.selectionColourLabel = new Label("Selection Colour");
	    EditorGui.selectionColourLabel.setFont(EditorGui.labelFont);

	    EditorGui.actionEventId = new TextField("############");
	    EditorGui.actionEventId.adjustSize();
	    EditorGui.actionEventId.setText("");
	    EditorGui.actionEventId.addActionListener(EditorGui.guiListener);
	    EditorGui.actionEventId.setActionEventId("actionEventId");
	    EditorGui.actionEventIdLabel = new Label("Action Event Id");
	    EditorGui.actionEventIdLabel.setFont(EditorGui.labelFont);
	    
	    EditorGui.widgetId = new TextField("############");
	    EditorGui.widgetId.adjustSize();
	    EditorGui.widgetId.setText("");
	    EditorGui.widgetId.addActionListener(EditorGui.guiListener);
	    EditorGui.widgetId.setActionEventId("widgetId");
	    EditorGui.widgetIdLabel = new Label("Widget id");
	    EditorGui.widgetIdLabel.setFont(EditorGui.labelFont);

	    EditorGui.standard.add(EditorGui.borderSizeLabel, 8, 0);
	    EditorGui.standard.add(EditorGui.borderSize, 8, 15);
	    EditorGui.standard.add(EditorGui.baseColourLabel, 38, 32);
	    EditorGui.standard.add(EditorGui.baseColour, 8, 32);    
	    EditorGui.standard.add(EditorGui.backgroundColourLabel, 38, 62);
	    EditorGui.standard.add(EditorGui.backgroundColour, 8, 62);
	    EditorGui.standard.add(EditorGui.foregroundColourLabel, 38, 92);
	    EditorGui.standard.add(EditorGui.foregroundColour, 8, 92);
	    EditorGui.standard.add(EditorGui.selectionColourLabel, 38, 122);
	    EditorGui.standard.add(EditorGui.selectionColour, 8, 122);
	    EditorGui.standard.add(EditorGui.actionEventIdLabel, 8, 152);
	    EditorGui.standard.add(EditorGui.actionEventId, 8, 166);
	    EditorGui.standard.add(EditorGui.widgetIdLabel, 8, 182);
	    EditorGui.standard.add(EditorGui.widgetId, 8, 196);

	    int py = 8;
		for (String file : GceEditPlugin.PLUG_PATHS) {
		    GceEditPlugin plug = GceEditPlugin.newPlugin(file);
		    if (plug == null) {
		    	System.out.println(file + " aborted");
		    } else {
		    	EditorGui.plugins.add(plug);
		        System.out.println(plug.componentName() + " component loaded. ");
		    
                Widget b = plug.initComponent();
                EditorGui.components.add(b, 8, py); 
                py = py + 4 + b.getHeight();
		    }
		}
	}
	
	public void init() throws GuichanException {
		EditorGui.top = new Container();  
		EditorGui.top.setDimension(new Rectangle(0, 0, 800, 600));
	    Globals.gui.setTop(EditorGui.top);
	    EditorGui.font = new ImageFont(ImageFont.FONT_PNG, 
	    		ImageFont.SPACE_ALPHABET_NUMBER_MARK);
	    Widget.setGlobalFont(EditorGui.font);
	    EditorGui.labelFont = new ImageFont(ImageFont.TINY_PNG, 
	    		ImageFont.SPACE_ALPHABET_NUMBER_MARK);
	    initWidgets();
    }
	
	public void halt() {
	    free_all_plugins();
	}
	
	public void free_all_plugins() {
		EditorGui.plugins.clear();
	}


	@Override
	public void onInit() throws GuichanException {
		init();
	}


	@Override
	public void onTimer() throws GuichanException {
        if (!EditorGui.widgetsToDelete.isEmpty()) {
            System.out.println("processing widgets to delete with " + EditorGui.widgetsToDelete.size() + " item(s) ");
            for (int i=0; i < EditorGui.widgetsToDelete.size(); i++) {
          	  	EditorGui.widgetsToDelete.get(i).destroy();
			}
            EditorGui.widgetsToDelete.clear();
        }
	}


	@Override
	public void onHalt() throws GuichanException {
		halt();
	}

	@Override
	public void preDraw(Graphics graphics) throws GuichanException {

	}

	@Override
	public void onKeyPressed(KeyEvent keyEvent) throws GuichanException {
		// TODO Auto-generated method stub
		
	}
}
