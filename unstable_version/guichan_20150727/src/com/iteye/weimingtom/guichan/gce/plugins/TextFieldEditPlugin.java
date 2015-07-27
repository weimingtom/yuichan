package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.widget.GceTextFieldEdit;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.Label;
import com.iteye.weimingtom.guichan.widget.TextField;

public class TextFieldEditPlugin extends GceEditPlugin {
	private static Container extendedProps;
	private static TextField textEdit;
	
	private static TextFieldActionListener tfListener;
	private final static class TextFieldActionListener implements ActionListener {
		@Override
		public void action(ActionEvent actionEvent) throws GuichanException {
			String id = actionEvent.getId();
			if ("newTfield".equals(id)) {
		        GceTextFieldEdit t = new GceTextFieldEdit();
		        t.setText("********");
		        t.adjustSize();
		        t.setText("");
		        t.setPosition(16,16);
		        GceBase.emptyMouseListeners(t);
		        t.addMouseListener(EditorGui.MListener);   // all components must add editors global mouse listener
		        EditorGui.top.add(t);
		        EditorGui.currentWidget = t;
		        doExtended();
		        EditorGui.updateBaseProperties();
		    }
		    if ("textEdit".equals(id)) {
		    	((GceTextFieldEdit)EditorGui.currentWidget)
		    		.setText(textEdit.getText());
		    }

		}
	}
	
	private static void doExtended() {
		textEdit.setText(((TextField)EditorGui.currentWidget)
			.getText());
	}
	
	public TextFieldEditPlugin() {
		super("TextFieldEditPlugin");
	}

	@Override
	public Widget initComponent() throws GuichanException {
	    extendedProps = new Container();
	    extendedProps.setSize(EditorGui.SHEET_WIDTH, EditorGui.SHEET_HEIGHT);
	        
	    tfListener = new TextFieldActionListener();
	    Button t = new Button("TextField");
	    t.adjustSize();
	    t.setHeight(t.getHeight() - 6);
	    t.setActionEventId("newTfield");
	    t.addActionListener(tfListener);
	    
	    textEdit = new TextField();
	    textEdit.setSize(84,18);    
	    textEdit.setActionEventId("textEdit");
	    textEdit.addActionListener(tfListener);
	    
	    Label tl = new Label("Text");
	    tl.setFont(EditorGui.labelFont);
	    extendedProps.add(tl, 8, 0);

	    extendedProps.add(textEdit, 8, 14); 
	    extendedProps.setBaseColor(
	    	new Color(255, 128, 128, 0));
	    
		return t;
	}

	@Override
	public boolean canParent() {
		return false;
	}

	@Override
	public boolean currentIsThisType() {
	    if (EditorGui.currentWidget instanceof GceTextFieldEdit) {
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
	    GceTextFieldEdit c = new GceTextFieldEdit();
	    GceBase.emptyMouseListeners(c);
	    c.addMouseListener(EditorGui.MListener);
		return c;
	}

	@Override
	public void exportNodeExtended(Element node) {
		node.addAttribute("text",
			((TextField)EditorGui.currentWidget).getText());
	}

	@Override
	public void setExtendedFromNode(Element node) {
		((TextField)EditorGui.currentWidget).setText(
			node.attributeValue("text"));  
	}

	@Override
	public String componentName() {
		return "gcn::TextField";
	}

}
