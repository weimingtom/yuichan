package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.widget.GceDropDownEdit;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;

public class DropDownEditPlugin extends GceEditPlugin {
	private static Container extendedProps;

	private static void doExtended() {

	}
	
	private static final class DropDownActionListener implements ActionListener {
		@Override
		public void action(ActionEvent actionEvent) throws GuichanException {
		    String id = actionEvent.getId();
			if ("newDropDown".equals(id)) {
		        GceDropDownEdit c = new GceDropDownEdit(EditorGui.dummyLM);
		        c.setPosition(16,16);
		        c.setSize(100,16);
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
	private DropDownActionListener ddListener;
	
	public DropDownEditPlugin() {
		super("DropDownEditPlugin");
	}

	@Override
	public Widget initComponent() throws GuichanException {
	    extendedProps = new Container();
	    extendedProps.setSize(EditorGui.SHEET_WIDTH, EditorGui.SHEET_HEIGHT);
	        
	    ddListener = new DropDownActionListener();
	    Button b = new Button("DropDown"); 
	    b.adjustSize();
	    b.setHeight(b.getHeight()-6);

	    b.setActionEventId("newDropDown");
	    b.addActionListener(ddListener);
	    
	    extendedProps.setBaseColor(new Color(255, 128, 128, 0));
	    
	    return b;
	}

	@Override
	public boolean canParent() {
		return false;
	}

	@Override
	public boolean currentIsThisType() {
	    if (EditorGui.currentWidget instanceof GceDropDownEdit) {
	        return true;
	    } else {
	        return false;
	    }
	}

	@Override
	public void updateExtendedProperties() throws GuichanException {
	    if (EditorGui.oldExtendedProps!=extendedProps) {
	        if (EditorGui.oldExtendedProps != null) { 
	        	EditorGui.extended.remove(EditorGui.oldExtendedProps);
	        }
	        EditorGui.extended.add(extendedProps,0,0);
	    }
	    
	    //doExtended();
	    
	    EditorGui.oldExtendedProps = extendedProps;  
	}

	@Override
	public Widget newInstance() throws GuichanException {
		GceDropDownEdit c = new GceDropDownEdit(EditorGui.dummyLM);
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
		return "gcn::DropDown";
	}

}
