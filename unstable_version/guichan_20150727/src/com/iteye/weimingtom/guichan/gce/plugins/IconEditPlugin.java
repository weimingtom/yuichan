package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.widget.GceIconEdit;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.platform.Image;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.TextField;

public class IconEditPlugin extends GceEditPlugin {
	private static Container extendedProps;
	private static TextField fileNameEdit;

	static void doExtended() {
	    fileNameEdit.setText(((GceIconEdit)EditorGui.currentWidget)
	    	.getFileName());
	}
	
	private final static class IconActionListener implements ActionListener {
		@Override
		public void action(ActionEvent actionEvent) throws GuichanException {
		    String id = actionEvent.getId();
			if ("newIcon".equals(id)) {
		        Image i = Image.load("images/broken.bmp");
		        GceIconEdit c = new GceIconEdit(i); 

		        c.setPosition(16, 16);
		        c.setSize(32, 32);
		        GceBase.emptyMouseListeners(c);
		        c.addMouseListener(EditorGui.MListener);   
		        EditorGui.top.add(c);
		        EditorGui.currentWidget = c;
		        doExtended();
		        EditorGui.updateBaseProperties();
		    }
		    if ("fileNameEdit".equals(id)) {
		    	((GceIconEdit)EditorGui.currentWidget)
		    		.setIconImage(fileNameEdit.getText());
		    }
		}
	}
	private IconActionListener iListener;
	
	public IconEditPlugin() {
		super("IconEditPlugin");
	}

	@Override
	public Widget initComponent() throws GuichanException {
	    extendedProps = new Container();
	    extendedProps.setSize(EditorGui.SHEET_WIDTH, EditorGui.SHEET_HEIGHT);
	        
	    iListener = new IconActionListener();
	    Button b = new Button("Icon"); 
	    b.adjustSize();
	    b.setHeight(b.getHeight() - 6);

	    b.setActionEventId("newIcon");
	    b.addActionListener(iListener);
	    
	    extendedProps.setBaseColor(new Color(255, 128, 128, 0));
	    
	    fileNameEdit = new TextField();
	    fileNameEdit.setSize(84,18);
	    fileNameEdit.setActionEventId("fileNameEdit");
	    fileNameEdit.addActionListener(iListener);

	    extendedProps.add(fileNameEdit, 8, 8);
	    
	    return b;
	}

	@Override
	public boolean canParent() {
		return false;
	}

	@Override
	public boolean currentIsThisType() {
	    if (EditorGui.currentWidget instanceof GceIconEdit) {
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
	    Image i = Image.load("images/broken.bmp");
	    GceIconEdit c = new GceIconEdit(i); 
	    
	    GceBase.emptyMouseListeners(c);
	    c.addMouseListener(EditorGui.MListener);

	    return c;
	}

	@Override
	public void exportNodeExtended(Element node) {
	    node.addAttribute("imageFileName",
	    	((GceIconEdit)EditorGui.currentWidget).getFileName());
	}

	@Override
	public void setExtendedFromNode(Element node) {
	    String str = node.attributeValue("imageFileName");
	    ((GceIconEdit)EditorGui.currentWidget).setIconImage(str);
	}

	@Override
	public String componentName() {
		return "gcn::Icon";
	}

}
