package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.widget.GceExampleButtonEdit;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Graphics.Alignment;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.Label;
import com.iteye.weimingtom.guichan.widget.RadioButton;
import com.iteye.weimingtom.guichan.widget.Slider;
import com.iteye.weimingtom.guichan.widget.TextField;

public class ExampleButtonEditPlugin extends GceEditPlugin {
	private static Container extendedProps;
	private static TextField captionEdit;
	private static int buttonIndex;
	private static RadioButton alignL;
	private static RadioButton alignC;
	private static RadioButton alignR;
	private static Slider spacing;
	
	private static ExampleButtonActionListener bListener;
	
	private final static class ExampleButtonActionListener implements ActionListener {
		@Override
		public void action(ActionEvent actionEvent) throws GuichanException {
		    String id = actionEvent.getId();
			if ("newButton".equals(id)) {
		        buttonIndex++;
		        String s = "Button" + buttonIndex;
		        GceExampleButtonEdit b = new GceExampleButtonEdit();
		        GceBase.emptyMouseListeners(b);
		        b.setCaption(s);
		        b.adjustSize();
		        b.setPosition(16, 16);
		        b.addMouseListener(EditorGui.MListener);
		        
		        //FIXME:
		        b.loadThemeImage("images/buttonskin.png");
		        b.setFrameSize(0);
				
		        EditorGui.top.add(b);
		        EditorGui.currentWidget = b;
		        doExtended();
		        EditorGui.updateBaseProperties();
		    } else if ("captionEdit".equals(id)) {
		    	((GceExampleButtonEdit)EditorGui.currentWidget)
		    		.setCaption(captionEdit.getText());    
		    } else if ("aL".equals(id)) {
		    	((GceExampleButtonEdit)EditorGui.currentWidget)
		    		.setAlignment(Graphics.Alignment.LEFT);
		    } else if ("aC".equals(id)) {
		    	((GceExampleButtonEdit)EditorGui.currentWidget)
		    		.setAlignment(Graphics.Alignment.CENTER);
		    } else if ("aR".equals(id)) {
		    	((GceExampleButtonEdit)EditorGui.currentWidget)
	    			.setAlignment(Graphics.Alignment.RIGHT);
		    } else if ("spacing".equals(id)) {       
		    	((GceExampleButtonEdit)EditorGui.currentWidget)
	    			.setSpacing((int)spacing.getValue());
		    }
		}
	};
	
	public ExampleButtonEditPlugin() {
		super("ExampleButtonEditPlugin");
	}

	@Override
	public Widget initComponent() throws GuichanException {
		extendedProps = new Container();
	    extendedProps.setSize(EditorGui.SHEET_WIDTH, EditorGui.SHEET_HEIGHT);
	    
	    bListener = new ExampleButtonActionListener();
	    Button b = new Button("exampleButton");
	    b.adjustSize();
	    b.setHeight(b.getHeight() - 6);
	    b.setActionEventId("newButton");
	    b.addActionListener(bListener);
	    
	    captionEdit = new TextField();
	    captionEdit.setSize(84,18);
	    captionEdit.setActionEventId("captionEdit");
	    captionEdit.addActionListener(bListener);
	    
	    Label captionLabel = new Label("Caption");
	    captionLabel.setFont(EditorGui.labelFont);
	    
	    alignL = new RadioButton("LEFT", "buttonA", true);
	    alignC = new RadioButton("CENTER", "buttonA");
	    alignR = new RadioButton("RIGHT", "buttonA");
	    alignL.setActionEventId("aL");
	    alignC.setActionEventId("aC");
	    alignR.setActionEventId("aR");
	    alignL.addActionListener(bListener);
	    alignC.addActionListener(bListener);
	    alignR.addActionListener(bListener);
	    
	    spacing = new Slider(128);
	    spacing.setSize(130,10);
	    spacing.setActionEventId("spacing");
	    spacing.addActionListener(bListener);
	    Label sl = new Label("Spacing");
	    sl.setFont(EditorGui.labelFont);
	    
	    Label al = new Label("Alignment");
	    al.setFont(EditorGui.labelFont);
	    extendedProps.add(alignL,8,48);
	    extendedProps.add(alignC,8,66);
	    extendedProps.add(alignR,8,84);
	    extendedProps.add(al,8,33);
	    
	    extendedProps.add(captionEdit,8,14);
	    extendedProps.add(captionLabel,8,0);
	     
	    extendedProps.add(sl,8,120);
	    extendedProps.add(spacing,8,134);
	    
	    extendedProps.setBaseColor(new Color(255, 128, 128, 0));

	    return b;
	}
	
	private static void doExtended() {
	    captionEdit.setText(((Button)EditorGui.currentWidget).getCaption());
	    
	    Alignment align = ((Button)EditorGui.currentWidget).getAlignment();
	    if (align == Graphics.Alignment.LEFT) {
	    	alignL.setSelected(true);
	    } else if (align == Graphics.Alignment.CENTER)  {
	    	alignC.setSelected(true);
	    } else if (align == Graphics.Alignment.RIGHT) {
	    	alignR.setSelected(true);
	    }
	    spacing.setValue(((Button)EditorGui.currentWidget)
	    	.getSpacing());
	}
	
	public boolean canParent() {
	    return false;
	}

	public boolean currentIsThisType() {
	    if (EditorGui.currentWidget instanceof GceExampleButtonEdit) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
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
	
	public Widget newInstance() throws GuichanException {
		GceExampleButtonEdit c = new GceExampleButtonEdit();
	    GceBase.emptyMouseListeners(c);
	    c.addMouseListener(EditorGui.MListener);
	    
	    //FIXME:
	    c.loadThemeImage("images/buttonskin.png");
        c.setFrameSize(0);
		
	    return c;
	}

	public void exportNodeExtended(Element node) {
	    // currentWidget is set by iteration
		node.addAttribute("caption", 
	    	((GceExampleButtonEdit)EditorGui.currentWidget).getCaption());
	    Alignment align = ((GceExampleButtonEdit)EditorGui.currentWidget).getAlignment();
	    if (align == Graphics.Alignment.LEFT) {
	        node.addAttribute("alignment", "LEFT");
	    } else if (align == Graphics.Alignment.CENTER) {
	    	node.addAttribute("alignment", "CENTRE");
	    } else if (align == Graphics.Alignment.RIGHT) {
	        node.addAttribute("alignment", "RIGHT");
	    }
	    node.addAttribute("spacing",
	    	Integer.toString(((GceExampleButtonEdit)EditorGui.currentWidget).getSpacing()));
	}
	
	public void setExtendedFromNode(Element node) {
		GceExampleButtonEdit btn = (GceExampleButtonEdit)EditorGui.currentWidget;
		btn.setCaption(queryStringAttribute(node, "caption"));
		btn.setSpacing(queryIntAttribute(node, "spacing"));
	    String alignment = queryStringAttribute(node, "alignment");
	    if ("LEFT".equals(alignment)) {
	    	btn.setAlignment(Graphics.Alignment.LEFT);
	    } else if ("CENTER".equals(alignment)) {
	    	btn.setAlignment(Graphics.Alignment.CENTER);
	    } else if ("RIGHT".equals(alignment)) {
	    	btn.setAlignment(Graphics.Alignment.RIGHT);
	    }
	}

	public String componentName() {
	    return "exampleButton";
	}
}
