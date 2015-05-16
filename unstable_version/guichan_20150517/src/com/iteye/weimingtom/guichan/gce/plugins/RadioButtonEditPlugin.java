package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.widget.GceRadioButtonEdit;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.CheckBox;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.Label;
import com.iteye.weimingtom.guichan.widget.RadioButton;
import com.iteye.weimingtom.guichan.widget.TextField;

public class RadioButtonEditPlugin extends GceEditPlugin {
	private static Container extendedProps;
	private static TextField radioCaptionEdit;
	private static TextField radioGroupEdit;
	private static int radioButtonIndex;
	private static CheckBox isChecked;

	private static void doExtended() {
	    radioCaptionEdit.setText(
	    	((RadioButton)EditorGui.currentWidget).getCaption());
	    radioGroupEdit.setText(
	    	((RadioButton)EditorGui.currentWidget).getGroup());
	    isChecked.setSelected(
	    	((RadioButton)EditorGui.currentWidget).isSelected());
	}
	
	private final static class RadioButtonActionListener implements ActionListener {
		@Override
		public void action(ActionEvent actionEvent) throws GuichanException {
		    String id = actionEvent.getId();
			if ("newRadioButton".equals(id)) {
		        radioButtonIndex++;
		        String s = String.format("RadioButton%d", radioButtonIndex);
		        GceRadioButtonEdit b = new GceRadioButtonEdit();       
		        
		        GceBase.emptyMouseListeners(b);
		        b.setCaption(s);
		        b.adjustSize();
		        b.setPosition(16, 16);
		        b.addMouseListener(EditorGui.MListener);   
		        
		        EditorGui.top.add(b);
		        EditorGui.currentWidget = b;
		        
		        doExtended();
		        
		        EditorGui.updateBaseProperties();
		    } else if ("radioButtonEdit".equals(id)) {
		    	((GceRadioButtonEdit)EditorGui.currentWidget)
		    		.setCaption(radioCaptionEdit.getText());
		    } else if ("radioGroupEdit".equals(id)) {  
		    	((GceRadioButtonEdit)EditorGui.currentWidget)
		    		.setGroup(radioGroupEdit.getText());
		    } else if ("isChecked".equals(id)) {
		    	((GceRadioButtonEdit)EditorGui.currentWidget)
		    		.setSelected(isChecked.isSelected());
		    }
		}
	}

	private static RadioButtonActionListener rbListener;
	
	public RadioButtonEditPlugin() {
		super("RadioButtonEditPlugin");
	}

	@Override
	public Widget initComponent() throws GuichanException {
	    extendedProps = new Container();
	    extendedProps.setSize(EditorGui.SHEET_WIDTH, EditorGui.SHEET_HEIGHT);
	    
	    rbListener = new RadioButtonActionListener();
	    Button  b = new Button("RadioButton"); 
	    b.adjustSize();
	    b.setHeight(b.getHeight() - 6);

	    b.setActionEventId("newRadioButton");
	    b.addActionListener(rbListener);
	    
	    radioCaptionEdit = new TextField();
	    radioCaptionEdit.setSize(84,18);
	    radioCaptionEdit.setActionEventId("radioButtonEdit");
	    radioCaptionEdit.addActionListener(rbListener);
	    
	    radioGroupEdit = new TextField();
	    radioGroupEdit.setSize(84,18);
	    radioGroupEdit.setActionEventId("radioGroupEdit");
	    radioGroupEdit.addActionListener(rbListener);
	    
	    Label captionLabel = new Label("Caption");
	    captionLabel.setFont(EditorGui.labelFont);
	    
	    Label groupLabel = new Label("Group");
	    groupLabel.setFont(EditorGui.labelFont);
	    
	    isChecked = new CheckBox("Checked");
	    isChecked.setActionEventId("isChecked");
	    isChecked.addActionListener(rbListener);
	    
	    extendedProps.add(isChecked,8,48);
	    
	    extendedProps.add(radioCaptionEdit, 8, 14);
	    extendedProps.add(captionLabel, 8, 0);
	    extendedProps.add(radioGroupEdit, 8, 92);
	    extendedProps.add(groupLabel, 8, 78);
	    
	    extendedProps.setBaseColor(new Color(255, 128, 128, 0));

	    return b; 
	}

	@Override
	public boolean canParent() {
		return false;
	}

	@Override
	public boolean currentIsThisType() {
	    if (EditorGui.currentWidget instanceof GceRadioButtonEdit) {
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
	    GceRadioButtonEdit c = new GceRadioButtonEdit();
	    GceBase.emptyMouseListeners(c);
	    c.addMouseListener(EditorGui.MListener);
	    return c;
	}

	@Override
	public void exportNodeExtended(Element node) {
	    node.addAttribute("caption",
	    	((GceRadioButtonEdit)EditorGui.currentWidget).getCaption());
	    node.addAttribute("group",
	    	((GceRadioButtonEdit)EditorGui.currentWidget).getGroup());
	    if (((GceRadioButtonEdit)EditorGui.currentWidget).isSelected()) {
	        node.addAttribute("checked", "true");
	    } else {
	        node.addAttribute("checked", "false");
	    }
	}

	@Override
	public void setExtendedFromNode(Element node) {
	    ((RadioButton)EditorGui.currentWidget).setCaption(
	    	node.attributeValue("caption"));
	    ((RadioButton)EditorGui.currentWidget).setGroup(
	    	node.attributeValue("group"));
	    if ("true".equals(node.attributeValue("checked"))) {
	        ((GceRadioButtonEdit)EditorGui.currentWidget)
	        	.setSelected(true);
	    } else {
	        ((GceRadioButtonEdit)EditorGui.currentWidget)
	        	.setSelected(false);
	    }
	}

	@Override
	public String componentName() {
		return "gcn::RadioButton";
	}
}
