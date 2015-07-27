package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.widget.GceWindowEdit;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.Label;
import com.iteye.weimingtom.guichan.widget.TextField;
import com.iteye.weimingtom.guichan.widget.Window;

public class WindowEditPlugin extends GceEditPlugin {
	private static Container extendedProps;
	private static TextField captionEdit;
	
	private static WindowActionListener wListener = new WindowActionListener();
	
	private final static class WindowActionListener implements ActionListener {
	    public void action(ActionEvent actionEvent) throws GuichanException {
	        String id = actionEvent.getId();
	    	if ("newWindow".equals(id)) {
	            GceWindowEdit w = new GceWindowEdit();
	            w.setCaption("New Window");
	            w.setPosition(16, 16);
	            w.setSize(100, 100);
	            GceBase.emptyMouseListeners(w);
	            w.addMouseListener(EditorGui.MListener);  
	            EditorGui.top.add(w);
	            EditorGui.currentWidget = w;
	            doExtended();
	            EditorGui.updateBaseProperties();
	        }
	        if ("captionEdit".equals(id)) {
	        	((GceWindowEdit)EditorGui.currentWidget)
	        		.setCaption(captionEdit.getText());
	        }
	    }
	};
	
	private static void doExtended() {
	    captionEdit.setText(((GceWindowEdit)EditorGui.currentWidget)
	    	.getCaption());
	}
	
	public WindowEditPlugin() {
		super("WindowEditPlugin");
	}

	@Override
	public Widget initComponent() throws GuichanException {
	    extendedProps = new Container();
	    extendedProps.setSize(EditorGui.SHEET_WIDTH, EditorGui.SHEET_HEIGHT);
	    
	    wListener = new WindowActionListener();
	    Button w = new Button("Window"); 
	    w.adjustSize();
	    w.setHeight(w.getHeight()-6);

	    w.setActionEventId("newWindow");
	    w.addActionListener(wListener);
	    
	    captionEdit = new TextField();
	    captionEdit.setSize(84,18);    
	    captionEdit.setActionEventId("captionEdit");
	    captionEdit.addActionListener(wListener);
	    
	    Label tl = new Label("Caption");
	    tl.setFont(EditorGui.labelFont);
	    extendedProps.add(tl, 8, 0);

	    extendedProps.add(captionEdit, 8, 14); 
	    extendedProps.setBaseColor(new Color(255, 128, 128, 0));
	    
	    return w;
	}

	@Override
	public boolean canParent() {
		return true;
	}

	@Override
	public boolean currentIsThisType() {
	    if (EditorGui.currentWidget instanceof GceWindowEdit) {
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
	        EditorGui.extended.add(extendedProps,0,0);
	    }  
	    
	    doExtended();
	    
	    EditorGui.oldExtendedProps = extendedProps;  
	}

	@Override
	public Widget newInstance() throws GuichanException {
	    GceWindowEdit c = new GceWindowEdit();
	    GceBase.emptyMouseListeners(c);
	    c.addMouseListener(EditorGui.MListener);
	    return c;
	}

	@Override
	public void exportNodeExtended(Element node) {
		node.addAttribute("caption",
			((Window)EditorGui.currentWidget).getCaption());
	}

	@Override
	public void setExtendedFromNode(Element node) {
		((Window)EditorGui.currentWidget)
			.setCaption(node.attributeValue("caption"));
	}

	@Override
	public String componentName() {
		return "gcn::Window";
	}

}
