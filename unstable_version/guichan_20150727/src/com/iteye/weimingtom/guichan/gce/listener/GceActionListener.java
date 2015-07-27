package com.iteye.weimingtom.guichan.gce.listener;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceFileDialog;
import com.iteye.weimingtom.guichan.gce.plugins.GceEditPlugin;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.widget.Container;

public class GceActionListener implements ActionListener {
	@Override
	public void action(ActionEvent actionEvent) throws GuichanException {
		String id = actionEvent.getId();
		if ("sheetSelector".equals(id)){
			int i = EditorGui.sheetSelector.getSelected();
		    Container sel = null;
		    switch(i) {
		    case 0:
		    	sel = EditorGui.global;
		        break;
		            
		    case 1:
		        sel = EditorGui.components;
		        break;
		            
		    case 2:
		        sel = EditorGui.standard;
		        break;
		            
		    case 3:
		        sel = EditorGui.extended;
		        if (EditorGui.currentWidget != null) {
                    boolean isP = false;
                    for (GceEditPlugin plug_ : EditorGui.plugins) {
                        if (plug_.currentIsThisType()) {
                        	plug_.updateExtendedProperties();
                            break;
                        }
                    }
                }
                break;
		    }
		    EditorGui.mainWindow.remove(EditorGui.lastSelected);
		    EditorGui.mainWindow.add(sel, 0, 32);
		    EditorGui.lastSelected = sel;
		}
	    if ("borderSize".equals(id) && 
	    	EditorGui.currentWidget != null) {
	        int i = (int)EditorGui.borderSize.getValue();
	        String s = String.format("Border Size : %d", i);
	        EditorGui.borderSizeLabel.setCaption(s);
	        EditorGui.currentWidget.setFrameSize(i);
	    }
	    if ("baseColour".equals(id) && 
	    	EditorGui.currentWidget != null) {
	    	EditorGui.currentWidget.setBaseColor(
	    	EditorGui.baseColour.getSelectedColour());
	    }
	    if ("backgroundColour".equals(id) && 
	    	EditorGui.currentWidget != null) {
	    	EditorGui.currentWidget.setBackgroundColor(
	    		EditorGui.backgroundColour.getSelectedColour());
	    }
	    if ("foregroundColour".equals(id) && 
	    	EditorGui.currentWidget != null) {
	    	EditorGui.currentWidget.setForegroundColor(
	    		EditorGui.foregroundColour.getSelectedColour());
	    }
	    if ("selectionColour".equals(id) && 
	    	EditorGui.currentWidget != null) {
	    	EditorGui.currentWidget.setSelectionColor(
	    		EditorGui.selectionColour.getSelectedColour());
	    }
	    if ("actionEventId".equals(id) && 
	    	EditorGui.currentWidget != null) {
	    	EditorGui.currentWidget.setActionEventId(
	    		EditorGui.actionEventId.getText());
	    }
	    if ("widgetId".equals(id) && 
	    	EditorGui.currentWidget != null) {
	    	EditorGui.currentWidget.setId(
	    		EditorGui.widgetId.getText());
	    }
	    if ("toBack".equals(id) && 
	    	EditorGui.currentWidget != null) {
	    	EditorGui.currentWidget.requestMoveToBottom();
	    }
	    if ("toFront".equals(id) && 
	    	EditorGui.currentWidget != null) {
	        EditorGui.currentWidget.requestMoveToTop();
	    }
	    if ("parent".equals(id)) {
	        if (EditorGui.parentMode == true) {
	        	EditorGui.parentMode = false;
	            EditorGui.parent.setCaption("Parent");
	            return;
	        }
	        EditorGui.parentMode = true;
	        EditorGui.parent.setCaption("CLICK!");
	    }   
		if ("delete".equals(id)) {
			if (EditorGui.deleteMode == true) {
				EditorGui.deleteMode = false;
		        EditorGui.del.setCaption("Delete Widget");
		        return;
		    }
			EditorGui.deleteMode = true;
			EditorGui.del.setCaption("CLICK!");
		}
		if ("parentTop".equals(id)) {
			EditorGui.parentMode = false;
			if (EditorGui.currentWidget != null && EditorGui.currentWidget.getParent() != null) {
			    ((Container)EditorGui.currentWidget.getParent())
			    	.remove(EditorGui.currentWidget);
			    EditorGui.top.add(EditorGui.currentWidget, 16, 16);
			}
		}
		if ("save".equals(id)) {
			GceFileDialog fd = new GceFileDialog(new GceFileDialogSaveGui());
		}
	    if ("load".equals(id)) {
	    	GceFileDialog fd = new GceFileDialog(new GceFileDialogLoadGui());
	    } 
	}
}
