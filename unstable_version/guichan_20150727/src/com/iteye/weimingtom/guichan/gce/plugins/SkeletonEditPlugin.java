package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.widget.GceSkeletonEdit;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;

public class SkeletonEditPlugin extends GceEditPlugin {
	private static Container extendedProps;
	private static SkeletonActionListener skelListener = new SkeletonActionListener();
	
	private static void doExtended() {

	}
	
	private final static class SkeletonActionListener implements ActionListener {
		@Override
		public void action(ActionEvent actionEvent) throws GuichanException {
		    if (actionEvent.getId().equals("newSkeleton")) {
		    	doExtended();
		        EditorGui.updateBaseProperties();
		    }
		}
	};
	
	
	public SkeletonEditPlugin() {
		super("SkeletonEditPlugin");
	}

	@Override
	public Widget initComponent() throws GuichanException {
	    extendedProps = new Container();
	    extendedProps.setSize(EditorGui.SHEET_WIDTH, EditorGui.SHEET_HEIGHT);
	    
	    skelListener = new SkeletonActionListener();

	    Button b = new Button("Skeleton"); 
	    b.adjustSize();
	    b.setHeight(b.getHeight() - 6);

	    b.setActionEventId("newSkeleton");
	    b.addActionListener(skelListener);

	    extendedProps.setBaseColor(new Color(255, 128, 128, 0));

	    return b;
	}

	@Override
	public boolean canParent() {
		return false;
	}

	@Override
	public boolean currentIsThisType() {
	    if (EditorGui.currentWidget instanceof GceSkeletonEdit) {
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
	    GceSkeletonEdit c = new GceSkeletonEdit();
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
		return "gceSkeleton";
	}
}
