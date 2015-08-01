package com.iteye.weimingtom.guichan.gce.plugins;

import org.dom4j.Element;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.widget.GceTabbedAreaEdit;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.Label;
import com.iteye.weimingtom.guichan.widget.Tab;

public class TabbedAreaEditPlugin extends GceEditPlugin {
	private static Container tabExtendedProps;
//	private static TextField tabCaptionEdit;
	
	private static void doExtended() {
//	    tabCaptionEdit.setText(((GceTabbedAreaEdit)
//	    	EditorGui.currentWidget).getCaption());
	}
	
	private final static class TabbedAreaActionListener implements ActionListener {
		@Override
		public void action(ActionEvent actionEvent) throws GuichanException {
		    String id = actionEvent.getId();
			if ("newTabber".equals(id)) {
		        GceTabbedAreaEdit tb = new GceTabbedAreaEdit();
		        //tb->setCaption("New Tabber");
		        tb.setPosition(16,16);
		        tb.setSize(110,110);
		        //((gceBase*)tb)->emptyMouseListeners();
		        //tb->addMouseListener(&MListener);   // all components must add editors global mouse listener
		        
		        Container c1 = new Container();
		        Container c2 = new Container();
		        c1.setSize(60,60);
		        c2.setSize(60,60);
		    
		        c1.addMouseListener(EditorGui.MListener);    

		        Tab t1 = new Tab();
		        t1.setCaption("Tab 1");
		        t1.setSize(40,20);
		        tb.addTab(t1,c1);
		        //t1->addMouseListener(&MListener);

		        Tab t2 = new Tab();

		        t2.setCaption("Tab 2");
		        t2.setSize(40, 20);
		        tb.addTab(t2, c2);
		        //t2->addMouseListener(&MListener);
		        
		        EditorGui.top.add(tb);
		        EditorGui.currentWidget = tb;
		        doExtended();
		        EditorGui.updateBaseProperties();
		    }
		    
//		    if (actionEvent.getId() == "captionEdit") {
//				((GceTabbedAreaEdit)EditorGui.currentWidget)
//					.setCaption(tabCaptionEdit.getText());
//			}
		}
	}
	private TabbedAreaActionListener tbListener;
	
	public TabbedAreaEditPlugin() {
		super("TabbedAreaEditPlugin");
	}

	@Override
	public Widget initComponent() throws GuichanException {
	    tabExtendedProps = new Container();
	    tabExtendedProps.setSize(EditorGui.SHEET_WIDTH, EditorGui.SHEET_HEIGHT);
	    
	    tbListener = new TabbedAreaActionListener();
	    Button b = new Button("TabbedArea"); 
	    b.adjustSize();
	    b.setHeight(b.getHeight() - 6);

	    b.setActionEventId("newTabber");
	    b.addActionListener(tbListener);
	    
//	    tabCaptionEdit=new gcn::TextField();
//	    tabCaptionEdit->setSize(84,18);    
//	    tabCaptionEdit->setActionEventId("captionEdit");
//	    tabCaptionEdit->addActionListener(tbListener);
	    
	    Label tl = new Label("Caption");
	    tl.setFont(EditorGui.labelFont);
	    tabExtendedProps.add(tl,8,0);

	    //tabExtendedProps->add(captionEdit,8,14); 
	    tabExtendedProps.setBaseColor(new Color(255, 128, 128, 0));
	    
	    return b;
	}

	@Override
	public boolean canParent() {
		return true;
	}

	@Override
	public boolean currentIsThisType() {
	    if (EditorGui.currentWidget instanceof GceTabbedAreaEdit) {
	        return true;
	    } else {
	        return false;
	    }
	}

	@Override
	public void updateExtendedProperties() throws GuichanException {
	    if (EditorGui.oldExtendedProps != tabExtendedProps) {
	        if (EditorGui.oldExtendedProps != null) {
	        	EditorGui.extended.remove(EditorGui.oldExtendedProps);
	        }
	        EditorGui.extended.add(tabExtendedProps, 0, 0);
	    }
	    doExtended();
	    
	    EditorGui.oldExtendedProps = tabExtendedProps;  
	}

	@Override
	public Widget newInstance() throws GuichanException {
	    GceTabbedAreaEdit c = new GceTabbedAreaEdit();
	    GceBase.emptyMouseListeners(c);
	    c.addMouseListener(EditorGui.MListener);
	    return c;
	}

	@Override
	public void exportNodeExtended(Element node) {
		//node->SetAttribute("caption",((gcn::Tabber*)currentWidget)->getCaption().c_str());
	}

	@Override
	public void setExtendedFromNode(Element node) {
	    //((gcn::Tabber*)currentWidget)->setCaption(node->Attribute("caption"));
	}

	@Override
	public String componentName() {
		return "gcn::Tabber";
	}
}
