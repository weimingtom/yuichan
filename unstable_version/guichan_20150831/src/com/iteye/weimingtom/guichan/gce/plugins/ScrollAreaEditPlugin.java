package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.widget.GceScrollAreaEdit;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;

public class ScrollAreaEditPlugin extends GceEditPlugin {
	private static Container extendedProps;

	private static void doExtended() {

	}
	
	private static class ScrollAreaActionListener implements ActionListener {
		@Override
		public void action(ActionEvent actionEvent) throws GuichanException {
		    String id = actionEvent.getId();
			if ("newScrollArea".equals(id)) {
		        GceScrollAreaEdit c = new GceScrollAreaEdit();
		        c.setPosition(16,16);
		        c.setSize(100,100);
		        //((gceBase*)c)->emptyMouseListeners();
		        c.addMouseListener(EditorGui.MListener);   
		        EditorGui.top.add(c);
		        EditorGui.currentWidget = c;
		        doExtended();
		        EditorGui.updateBaseProperties();
		    }
		}
	}

	private static ScrollAreaActionListener saListener;
	
	public ScrollAreaEditPlugin() {
		super("ScrollAreaEditPlugin");
	}

	@Override
	public Widget initComponent() throws GuichanException {
	    extendedProps = new Container();
	    extendedProps.setSize(EditorGui.SHEET_WIDTH, EditorGui.SHEET_HEIGHT);
	        
	    saListener = new ScrollAreaActionListener();
	    Button b = new Button("ScrollArea"); 
	    b.adjustSize();
	    b.setHeight(b.getHeight() - 6);

	    b.setActionEventId("newScrollArea");
	    b.addActionListener(saListener);
	    
	    extendedProps.setBaseColor(new Color(255, 128, 128, 0));
	    
	    return b;
	}

	@Override
	public boolean canParent() {
		return true;
	}

	@Override
	public boolean currentIsThisType() {
	    if (EditorGui.currentWidget instanceof GceScrollAreaEdit) {
	        return true;
	    } else {
	        return false;
	    }
	}

	@Override
	public void updateExtendedProperties() throws GuichanException {
	    if (EditorGui.oldExtendedProps != extendedProps) {
	        if (EditorGui.oldExtendedProps != null) {
	        	EditorGui.extended.remove(EditorGui.oldExtendedProps);
	        }
	        EditorGui.extended.add(extendedProps, 0, 0);
	    }
	    
	    doExtended();
	    
	    EditorGui.oldExtendedProps = extendedProps;  
	}

	@Override
	public Widget newInstance() throws GuichanException {
	    GceScrollAreaEdit c = new GceScrollAreaEdit();
	    GceBase.emptyMouseListeners(c);
	    c.addMouseListener(EditorGui.MListener);
	    return c;
	}

	@Override
	public void exportNodeExtended(Element node) {
		
	}

	@Override
	public void setExtendedFromNode(Element node) {
		
	}

	@Override
	public String componentName() {
		return "gcn::ScrollArea";
	}

}
