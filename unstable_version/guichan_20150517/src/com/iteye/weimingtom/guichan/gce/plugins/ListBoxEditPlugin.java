package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.widget.GceListBoxEdit;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;

public class ListBoxEditPlugin extends GceEditPlugin {
	private static Container extendedProps;
	
	private static void doExtended() {

	}
	
	private static final class ListBoxActionListener implements ActionListener{
		@Override
		public void action(ActionEvent actionEvent) throws GuichanException {
		    String id = actionEvent.getId();
			if ("newListBox".equals(id)) {
		        GceListBoxEdit c = new GceListBoxEdit(EditorGui.dummyLM);
		        c.setPosition(16, 16);
		        c.setSize(100, 24);
		        //((gceBase*)c)->emptyMouseListeners();  
		        c.removeMouseListener(c);
		        c.addMouseListener(EditorGui.MListener);   
		        EditorGui.top.add(c);
		        EditorGui.currentWidget = c;
		        doExtended();
		        EditorGui.updateBaseProperties();
		    }
		}
	}
	private ListBoxActionListener lbListener;
	
	public ListBoxEditPlugin() {
		super("ListBoxEditPlugin");
	}

	@Override
	public Widget initComponent() throws GuichanException {
	    extendedProps = new Container();
	    extendedProps.setSize(EditorGui.SHEET_WIDTH, EditorGui.SHEET_HEIGHT);
	        
	    lbListener = new ListBoxActionListener();
	    Button b = new Button("ListBox"); 
	    b.adjustSize();
	    b.setHeight(b.getHeight() - 6);
	    
	    b.setActionEventId("newListBox");
	    b.addActionListener(lbListener);
	    
	    extendedProps.setBaseColor(new Color(255, 128, 128, 0));
	    
	    return b;
	}

	@Override
	public boolean canParent() {
		return false;
	}

	@Override
	public boolean currentIsThisType() {
	    if (EditorGui.currentWidget instanceof GceListBoxEdit) {
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
	    GceListBoxEdit c = new GceListBoxEdit(EditorGui.dummyLM);
	    GceBase.emptyMouseListeners(c);
	    c.removeMouseListener(c);
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
		return "gcn::ListBox";
	}

}
