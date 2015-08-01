package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.widget.GceContainerEdit;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;

public class ContainerEditPlugin extends GceEditPlugin {
	private static Container extendedProps;

	private static void doExtended() {

	}

	private static final class ContainerActionListener implements ActionListener {
		@Override
		public void action(ActionEvent actionEvent) throws GuichanException {
		    String id = actionEvent.getId();
			if ("newContainer".equals(id)) {
		        GceContainerEdit c = new GceContainerEdit();
		        c.setPosition(16, 16);
		        c.setSize(100, 100);
		        GceBase.emptyMouseListeners(c);
		        c.addMouseListener(EditorGui.MListener);   
		        EditorGui.top.add(c);
		        EditorGui.currentWidget = c;
		        doExtended();
		        EditorGui.updateBaseProperties();
		    }
		}
	}

	private ContainerActionListener cListener;

	public ContainerEditPlugin() {
		super("ContainerEditPlugin");
	}

	@Override
	public Widget initComponent() throws GuichanException {
	    extendedProps = new Container();
	    extendedProps.setSize(EditorGui.SHEET_WIDTH, EditorGui.SHEET_HEIGHT);
	        
	    cListener = new ContainerActionListener();
	    Button b = new Button("Container"); 
	    b.adjustSize();
	    b.setHeight(b.getHeight() - 6);

	    b.setActionEventId("newContainer");
	    b.addActionListener(cListener);
	    
	    extendedProps.setBaseColor(new Color(255, 128, 128, 0));
	    
	    return b;
	}

	@Override
	public boolean canParent() {
		return true;
	}

	@Override
	public boolean currentIsThisType() {
	    //FIXME:
		if (EditorGui.currentWidget instanceof GceContainerEdit /*||
	    	EditorGui.currentWidget instanceof Container*/) {
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
	    GceContainerEdit c = new GceContainerEdit();
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
		return "gcn::Container";
	}

}
