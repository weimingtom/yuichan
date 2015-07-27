package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.widget.GceLabelEdit;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Graphics.Alignment;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.RadioButton;
import com.iteye.weimingtom.guichan.widget.TextField;
import com.iteye.weimingtom.guichan.widget.Label;

public class LabelEditPlugin extends GceEditPlugin {
	private static Container extendedProps;
	private static TextField lcaptionEdit;
	private static int labelIndex;
	private static RadioButton alignL;
	private static RadioButton alignC;
	private static RadioButton alignR;
	
	private static void doExtended() {
	    lcaptionEdit.setText(((Label)EditorGui.currentWidget)
	    	.getCaption());
	    
	    Alignment alignment = ((Label)EditorGui.currentWidget).getAlignment();
	    if (alignment == Graphics.Alignment.LEFT)  {
	    	alignL.setSelected(true);
	    }
	    if (alignment == Graphics.Alignment.CENTER) {
	    	alignC.setSelected(true);
	    }
	    if (alignment == Graphics.Alignment.RIGHT) {
	    	alignR.setSelected(true);   
	    }
	}
	
	private final static class LabelActionListener implements ActionListener {
		@Override
		public void action(ActionEvent actionEvent) throws GuichanException {
		    String id = actionEvent.getId();
			if ("newLabel".equals(id)) {
		        labelIndex++;
		        String s;
		        s = String.format("Label%d", labelIndex);
		        GceLabelEdit b = new GceLabelEdit();
		        
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
		    if (actionEvent.getId() == "captionEdit") {
		    	((GceLabelEdit)EditorGui.currentWidget)
		    		.setCaption(lcaptionEdit.getText());
		    }
		    if (actionEvent.getId() == "aL") {
		    	((GceLabelEdit)EditorGui.currentWidget)
		    		.setAlignment(Graphics.Alignment.LEFT);
		    }
		    if (actionEvent.getId() == "aC") {
		    	((GceLabelEdit)EditorGui.currentWidget)
		    		.setAlignment(Graphics.Alignment.CENTER);
		    }
		    if (actionEvent.getId() == "aR") {
		    	((GceLabelEdit)EditorGui.currentWidget)
		    		.setAlignment(Graphics.Alignment.RIGHT);
		    }
		}
	}
	
	private static LabelActionListener lListener;
	
	public LabelEditPlugin() {
		super("LabelEditPlugin");
	}

	@Override
	public Widget initComponent() throws GuichanException {
	    extendedProps = new Container();
	    extendedProps.setSize(EditorGui.SHEET_WIDTH, EditorGui.SHEET_HEIGHT);
	    
	    lListener = new LabelActionListener();
	    Button b = new Button("Label"); 
	    b.adjustSize();
	    b.setHeight(b.getHeight() - 6);
	    b.setActionEventId("newLabel");
	    b.addActionListener(lListener);
	    
	    lcaptionEdit = new TextField();
	    lcaptionEdit.setSize(84, 18);
	    lcaptionEdit.setActionEventId("captionEdit");
	    lcaptionEdit.addActionListener(lListener);
	    
	    Label captionLabel = new Label("Caption");
	    captionLabel.setFont(EditorGui.labelFont);
	    
	    alignL = new RadioButton("LEFT", "buttonA", true);
	    alignC = new RadioButton("CENTER", "buttonA");
	    alignR = new RadioButton("RIGHT", "buttonA");
	    alignL.setActionEventId("aL");
	    alignC.setActionEventId("aC");
	    alignR.setActionEventId("aR");
	    alignL.addActionListener(lListener);
	    alignC.addActionListener(lListener);
	    alignR.addActionListener(lListener);
	    
	    Label al = new Label("Alignment");
	    al.setFont(EditorGui.labelFont);
	    extendedProps.add(alignL, 8, 48);
	    extendedProps.add(alignC, 8, 66);
	    extendedProps.add(alignR, 8, 84);
	    extendedProps.add(al, 8, 33);
	    
	    extendedProps.add(lcaptionEdit, 8, 14);
	    extendedProps.add(captionLabel, 8, 0);
	     
	    extendedProps.setBaseColor(new Color(255, 128, 128, 0));

	    return b; 
	}

	@Override
	public boolean canParent() {
		return false;
	}

	@Override
	public boolean currentIsThisType() {
	    if (EditorGui.currentWidget instanceof GceLabelEdit) {
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
	    GceLabelEdit c = new GceLabelEdit();
	    GceBase.emptyMouseListeners(c);
	    c.addMouseListener(EditorGui.MListener);
	    return c;
	}

	@Override
	public void exportNodeExtended(Element node) {
	    node.addAttribute("caption", ((GceLabelEdit)EditorGui.currentWidget)
	    	.getCaption());
	    Alignment aligment = ((GceLabelEdit)EditorGui.currentWidget)
	    	.getAlignment();
	    if (aligment == Graphics.Alignment.LEFT) {
	    	node.addAttribute("alignment","LEFT");
	    } else if (aligment == Graphics.Alignment.CENTER) {
	        node.addAttribute("alignment","CENTRE");
	    } else if (aligment == Graphics.Alignment.RIGHT) {
	        node.addAttribute("alignment","RIGHT");
	    }
	}

	@Override
	public void setExtendedFromNode(Element node) {
	    ((Label)EditorGui.currentWidget)
	    	.setCaption(node.attributeValue("caption"));
	    String alignment = node.attributeValue("alignment");
	    if ("LEFT".equals(alignment)) {
	    	((GceLabelEdit)EditorGui.currentWidget)
	    		.setAlignment(Graphics.Alignment.LEFT);
	    } else if ("CENTRE".equals(alignment)) {
	    	((GceLabelEdit)EditorGui.currentWidget)
	    		.setAlignment(Graphics.Alignment.CENTER);
	    } else if ("RIGHT".equals(alignment)) {
	    	((GceLabelEdit)EditorGui.currentWidget)
	    		.setAlignment(Graphics.Alignment.RIGHT);
	    }
	}

	@Override
	public String componentName() {
		return "gcn::Label";
	}

}
