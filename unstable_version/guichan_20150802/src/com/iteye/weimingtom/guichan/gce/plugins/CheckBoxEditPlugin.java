package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.widget.GceCheckBoxEdit;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.CheckBox;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.Label;
import com.iteye.weimingtom.guichan.widget.TextField;

public class CheckBoxEditPlugin extends GceEditPlugin {
	private static Container extendedProps;
	private static TextField checkCaptionEdit;
	private static int checkBoxIndex;
	private static CheckBox isChecked;

	static void doExtended() {
	    checkCaptionEdit.setText(((CheckBox)EditorGui.currentWidget)
	    	.getCaption());
	    isChecked.setSelected(((CheckBox)EditorGui.currentWidget)
	    	.isSelected());
	}

	private final static class CheckBoxActionListener implements ActionListener {
		@Override
		public void action(ActionEvent actionEvent) throws GuichanException {
		    String id = actionEvent.getId();
			if ("newCheckBox".equals(id)) {
		        checkBoxIndex++;
		        String s;
		        s = String.format("CheckBox%d", checkBoxIndex);
		        GceCheckBoxEdit b = new GceCheckBoxEdit(); 
		        
		        GceBase.emptyMouseListeners(b);
		        b.setCaption(s);
		        b.adjustSize();
		        b.setPosition(16, 16);
		        b.addMouseListener(EditorGui.MListener);   
		        
		        EditorGui.top.add(b);
		        EditorGui.currentWidget = b;
		        doExtended();
		        EditorGui.updateBaseProperties();
		    }
		    if ("checkBoxEdit".equals(id)) {
		    	((GceCheckBoxEdit)EditorGui.currentWidget)
		    		.setCaption(checkCaptionEdit.getText());
		    }
		    if ("isChecked".equals(id)) {
		    	((GceCheckBoxEdit)EditorGui.currentWidget)
		    		.setSelected(isChecked.isSelected());
		    }
		}
	}
	private CheckBoxActionListener ckListener;
	
	public CheckBoxEditPlugin() {
		super("CheckBoxEditPlugin");
	}

	@Override
	public Widget initComponent() throws GuichanException {
	    extendedProps = new Container();
	    extendedProps.setSize(EditorGui.SHEET_WIDTH, EditorGui.SHEET_HEIGHT);
	    
	    ckListener = new CheckBoxActionListener();
	    Button b = new Button("CheckBox"); 
	    b.adjustSize();
	    b.setHeight(b.getHeight() - 6);

	    b.setActionEventId("newCheckBox");
	    b.addActionListener(ckListener);
	    
	    checkCaptionEdit = new TextField();
	    checkCaptionEdit.setSize(84,18);
	    checkCaptionEdit.setActionEventId("checkBoxEdit");
	    checkCaptionEdit.addActionListener(ckListener);
	    
	    Label captionLabel = new Label("Caption");
	    captionLabel.setFont(EditorGui.labelFont);
	    
	    isChecked = new CheckBox("Checked");
	    isChecked.setActionEventId("isChecked");
	    isChecked.addActionListener(ckListener);
	        
	    extendedProps.add(isChecked,8,48);
	    
	    extendedProps.add(checkCaptionEdit,8,14);
	    extendedProps.add(captionLabel,8,0);
	     
	    extendedProps.setBaseColor(new Color(255, 128, 128, 0));

	    return b; 
	}

	@Override
	public boolean canParent() {
		return false;
	}

	@Override
	public boolean currentIsThisType() {
	    if (EditorGui.currentWidget instanceof GceCheckBoxEdit) {
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
	    GceCheckBoxEdit c = new GceCheckBoxEdit();
	    GceBase.emptyMouseListeners(c);
	    c.addMouseListener(EditorGui.MListener);
	    return c;
	}

	@Override
	public void exportNodeExtended(Element node) {
	    // currentWidget is set by iteration
	    node.addAttribute("caption", 
	    	((GceCheckBoxEdit)EditorGui.currentWidget)
	    		.getCaption());
	    if (((GceCheckBoxEdit)EditorGui.currentWidget).isSelected()) {
	        node.addAttribute("checked", "true");
	    } else {
	        node.addAttribute("checked", "false");
	    }
	}

	@Override
	public void setExtendedFromNode(Element node) {
	    ((GceCheckBoxEdit)EditorGui.currentWidget).setCaption(
	    		node.attributeValue("caption"));
	    if ("true".equals(node.attributeValue("checked"))) {
	        ((GceCheckBoxEdit)EditorGui.currentWidget)
	        	.setSelected(true);
	    } else {
	        ((GceCheckBoxEdit)EditorGui.currentWidget)
	        	.setSelected(false);
	    }
	}

	@Override
	public String componentName() {
		return "gcn::CheckBox";
	}

}
