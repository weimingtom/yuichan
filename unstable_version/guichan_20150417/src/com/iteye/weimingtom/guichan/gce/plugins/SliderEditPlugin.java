package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.widget.GceSliderEdit;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.CheckBox;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.Label;
import com.iteye.weimingtom.guichan.widget.Slider;
import com.iteye.weimingtom.guichan.widget.TextField;

public class SliderEditPlugin extends GceEditPlugin {
	private static Container extendedProps;

	private static TextField markerLength;
	private static CheckBox vertical;
	private static TextField scaleEnd;
	private static TextField scaleStart;
	private static TextField stepLength;
	
	private static void doExtended() {
	    int l = ((Slider)EditorGui.currentWidget).getMarkerLength();
	    String tmp = String.format("%d", l);
	    markerLength.setText(tmp);

	    if (((Slider)EditorGui.currentWidget).getOrientation() == 
	    	Slider.Orientation.VERTICAL) {
	        vertical.setSelected(true);
	    } else {
	        vertical.setSelected(false);
	    }

	    double f = ((Slider)EditorGui.currentWidget).getScaleEnd();
	    tmp = String.format("%f", f);
	    scaleEnd.setText(tmp);

	    f = ((Slider)EditorGui.currentWidget).getScaleStart();
	    tmp = String.format("%f", f);
	    scaleStart.setText(tmp);

	    f = ((Slider)EditorGui.currentWidget).getStepLength();
	    tmp = String.format("%f", f);
	    stepLength.setText(tmp);
	}
	
	private final static class SliderActionListener implements ActionListener {
		@Override
		public void action(ActionEvent actionEvent) throws GuichanException {
		    String id = actionEvent.getId();
			if ("newSlider".equals(id)) {
		        GceSliderEdit c = new GceSliderEdit();
		        c.setPosition(16,16);
		        c.setSize(140,14);
		        //((gceBase*)c)->emptyMouseListeners();
		        c.addMouseListener(EditorGui.MListener);   
		        // all components must add editors global mouse listener
		        EditorGui.top.add(c);
		        EditorGui.currentWidget = c;

		        // never do updateExtendedProperties(); as you will get the routine from the first plugin!
		        doExtended();
		        EditorGui.updateBaseProperties();
		    }
			
		    if ("markerLength".equals(id)) {
		    	int value = 0;
		    	try {
		    		value = Integer.parseInt(markerLength.getText());
		    	} catch (NumberFormatException e) {
		    		
		    	}
		    	((Slider)EditorGui.currentWidget)
		        	.setMarkerLength(value);
		    }

		    if ("vertical".equals(id)) {
		        if (vertical.isSelected()) {
		            ((Slider)EditorGui.currentWidget)
		            	.setOrientation(Slider.Orientation.VERTICAL); 
		        } else {
		            ((Slider)EditorGui.currentWidget)
		            	.setOrientation(Slider.Orientation.HORIZONTAL); 
		        }
		        int w = EditorGui.currentWidget.getWidth();
		        int h = EditorGui.currentWidget.getHeight();
		        EditorGui.currentWidget.setSize(h, w); 
		    }

		    if ("scaleStart".equals(id)) {
		    	float value = 0;
		    	try {
		    		value = Float.parseFloat(scaleStart.getText());
		    	} catch (NumberFormatException e) {
		    		
		    	}
		    	((Slider)EditorGui.currentWidget)
		        	.setScaleStart(value);
		    }

		    if ("scaleEnd".equals(id)) {
		    	float value = 0;
		    	try {
		    		value = Float.parseFloat(scaleEnd.getText());
		    	} catch (NumberFormatException e) {
		    		
		    	}
		    	((Slider)EditorGui.currentWidget)
		        	.setScaleEnd(value);
		    }

		    if (actionEvent.getId() == "stepLength") {
		    	float value = 0;
		    	try {
		    		value = Float.parseFloat(stepLength.getText());
		    	} catch (NumberFormatException e) {
		    		
		    	}
		    	((Slider)EditorGui.currentWidget)
		        	.setStepLength(value);
		    }
		}
	}
	private SliderActionListener slListener;
	
	public SliderEditPlugin() {
		super("SliderEditPlugin");
	}

	@Override
	public Widget initComponent() throws GuichanException {
	    extendedProps = new Container();
	    extendedProps.setSize(EditorGui.SHEET_WIDTH, EditorGui.SHEET_HEIGHT);
	    
	    slListener = new SliderActionListener();

	    Button b = new Button("Slider"); 
	    b.adjustSize();
	    b.setHeight(b.getHeight() - 6);

	    b.setActionEventId("newSlider");
	    b.addActionListener(slListener);

	    markerLength = new TextField("");
	    markerLength.setWidth(100);
	    markerLength.setActionEventId("markerLength");
	    markerLength.addActionListener(slListener);
	    extendedProps.add(markerLength, 8, 18);
	    
	    vertical = new CheckBox("Vertical");
	    vertical.setWidth(100);
	    vertical.setActionEventId("vertical");
	    vertical.addActionListener(slListener);
	    extendedProps.add(vertical,8,48);

	    scaleStart = new TextField("");
	    scaleStart.setWidth(100);
	    scaleStart.setActionEventId("scaleStart");
	    scaleStart.addActionListener(slListener);
	    extendedProps.add(scaleStart,8,88);
	     
	    scaleEnd = new TextField("");
	    scaleEnd.setWidth(100);
	    scaleEnd.setActionEventId("scaleEnd");
	    scaleEnd.addActionListener(slListener);
	    extendedProps.add(scaleEnd,8,128);
	     
	    stepLength = new TextField("");
	    stepLength.setWidth(100);
	    stepLength.setActionEventId("stepLength");
	    stepLength.addActionListener(slListener);
	    extendedProps.add(stepLength,8,168);

	    Label bw = new Label("Bar width");
	    bw.setFont(EditorGui.labelFont);
	    extendedProps.add(bw,8,2);

	    Label ss = new Label("Start");
	    ss.setFont(EditorGui.labelFont);
	    extendedProps.add(ss,8,74);

	    Label se = new Label("End");
	    se.setFont(EditorGui.labelFont);
	    extendedProps.add(se, 8, 114);

	    Label st = new Label("Step");
	    st.setFont(EditorGui.labelFont);
	    extendedProps.add(st,8,154);
	     
	    extendedProps.setBaseColor(new Color(255, 128, 128, 0));

	    return b;
	}

	@Override
	public boolean canParent() {
		return false;
	}

	@Override
	public boolean currentIsThisType() {
	    if (EditorGui.currentWidget instanceof GceSliderEdit) {
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
	    GceSliderEdit c = new GceSliderEdit();
	    GceBase.emptyMouseListeners(c);
	    c.addMouseListener(EditorGui.MListener);
		return c;
	}

	@Override
	public void exportNodeExtended(Element node) {
	    String tmp;
	    int l = ((Slider)EditorGui.currentWidget).getMarkerLength();
	    tmp = String.format("%d", l);
	    node.addAttribute("markerLength", tmp);

	    if (((Slider)EditorGui.currentWidget)
	    	.getOrientation() == Slider.Orientation.VERTICAL) {
	        node.addAttribute("vertical", "true");
	    } else {
	        node.addAttribute("vertical", "false");
	    }
	    
	    double f = ((Slider)EditorGui.currentWidget).getScaleEnd();
	    tmp = String.format("%f", f);
	    node.addAttribute("scaleEnd", tmp);

	    f = ((Slider)EditorGui.currentWidget).getScaleStart();
	    tmp = String.format("%f", f);
	    node.addAttribute("scaleStart", tmp);

	    f = ((Slider)EditorGui.currentWidget).getStepLength();
	    tmp = String.format("%f", f);
	    node.addAttribute("stepLength", tmp);
	}

	@Override
	public void setExtendedFromNode(Element node) {
	    int valueInt = 0;
    	String valueStr = node.attributeValue("markerLength");
    	if (valueStr != null) {
		    try {
		    	valueInt = Integer.parseInt(valueStr);
		    } catch (NumberFormatException e) {
		    	valueInt = 0;
		    }
    	} else {
    		valueInt = 0;
    	}
	    ((Slider)EditorGui.currentWidget).setMarkerLength(valueInt);
	    
	    if ("true".equals(node.attributeValue("vertical"))) {
	        ((Slider)EditorGui.currentWidget).setOrientation(
	        	Slider.Orientation.VERTICAL);
	    } else {
	        ((Slider)EditorGui.currentWidget).setOrientation(
	        	Slider.Orientation.HORIZONTAL);
	    }
	    float value = 0;
	    valueStr = node.attributeValue("scaleStart");
	    if (valueStr != null) {
		    try {
		    	value = Float.parseFloat(valueStr);
		    } catch (NumberFormatException e) {
		    	value = 0;
		    }
	    } else {
	    	value = 0;
	    }
	    ((Slider)EditorGui.currentWidget).setScaleStart(value);
	    valueStr = node.attributeValue("scaleEnd");
	    if (valueStr != null) {
		    try {
		    	value = Float.parseFloat(valueStr);
		    } catch (NumberFormatException e) {
		    	value = 0;
		    }
	    } else {
	    	value = 0;
	    }
	    ((Slider)EditorGui.currentWidget).setScaleEnd(value);
	    valueStr = node.attributeValue("stepLength");
	    if (valueStr != null) {
		    try {
		    	value = Float.parseFloat(valueStr);
		    } catch (NumberFormatException e) {
		    	value = 0;
		    }
	    } else {
	    	value = 0;
	    }
	    ((Slider)EditorGui.currentWidget).setStepLength(value);
	}

	@Override
	public String componentName() {
		return "gcn::Slider";
	}

}
