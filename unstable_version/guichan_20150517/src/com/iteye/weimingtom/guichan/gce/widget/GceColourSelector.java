package com.iteye.weimingtom.guichan.gce.widget;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.Slider;

public class GceColourSelector extends Button implements ActionListener {
	public String mActionId;
	
    private Color mCol;
    private Color mColTemp;
    private Slider mRed;
    private Slider mGreen;
    private Slider mBlue;
    private Slider mAlpha;
    private Button mOkButt;
    private Container mCsPane;
    
	public GceColourSelector() throws GuichanException {
		super();
	    addActionListener(this);
	    setActionEventId("openColourSelector");
	    setCaption("  ");
	    adjustSize();
	    mColTemp = mCol;
	}
	
	public GceColourSelector(String caption) throws GuichanException {
		super(caption);
	    addActionListener(this);
	    setActionEventId("openColourSelector");
	    setCaption("  ");
	    adjustSize();
	    mColTemp = mCol;
	}

	@Override
	public void action(ActionEvent actionEvent) throws GuichanException {
	    String id = actionEvent.getId();
		if ("openColourSelector".equals(id)) {
	        if (EditorGui.currentWidget != null) {
	            mCsPane = new Container();
	            mCsPane.setSize(148, 96);
	            mCsPane.setFrameSize(2);
	            int[] x = new int[1];
	            int[] y = new int[1];
	            getAbsolutePosition(x, y);
	            EditorGui.top.add(mCsPane, 0, 0);
	            mCsPane.setPosition(x[0] + (getWidth() / 2) - 76,
	            	y[0] + (getHeight() / 2) - 50);
	            mOkButt = new Button("OK");
	            mOkButt.adjustSize();
	            mOkButt.addActionListener(this);
	            mOkButt.setActionEventId("ColourSelected");
	            mCsPane.add(mOkButt,8,8);
	            
	            mRed = new Slider(255);
	            mRed.setSize(130, 10);
	            mRed.setActionEventId("Red");
	            mRed.addActionListener(this);
	            mRed.setValue(mCol.r);
	            mCsPane.add(mRed, 8, 36);
	            
	            mGreen = new Slider(255);
	            mGreen.setSize(130, 10);
	            mGreen.setActionEventId("Green");
	            mGreen.addActionListener(this);
	            mGreen.setValue(mCol.g);
	            mCsPane.add(mGreen,8,48);
	            
	            mBlue = new Slider(255);
	            mBlue.setSize(130, 10);
	            mBlue.setActionEventId("Blue");
	            mBlue.addActionListener(this);
	            mBlue.setValue(mCol.b);
	            mCsPane.add(mBlue, 8, 60);            
	            
	            mAlpha = new Slider(255);
	            mAlpha.setSize(130, 10);
	            mAlpha.setActionEventId("Alpha");
	            mAlpha.addActionListener(this);
	            mAlpha.setValue(mCol.a);
	            mCsPane.add(mAlpha, 8, 72);
	            
	            
	            //FIXME:
		        mColTemp.r = (int)mRed.getValue();
		        mColTemp.g = (int)mGreen.getValue();
		        mColTemp.b = (int)mBlue.getValue();
		        mColTemp.a = (int)mAlpha.getValue();
		        mCsPane.setBaseColor(mColTemp);
	        }
	    }
	    if ("Red".equals(id)) {
	        mColTemp.r = (int)mRed.getValue();
	        mCsPane.setBaseColor(mColTemp);
	    }
	    if ("Green".equals(id)) {
	        mColTemp.g = (int)mGreen.getValue();
	        mCsPane.setBaseColor(mColTemp);
	    }
	    if ("Blue".equals(id)) {
	        mColTemp.b = (int)mBlue.getValue();
	        mCsPane.setBaseColor(mColTemp);
	    }
	    if ("Alpha".equals(id)) {
	        mColTemp.a = (int)mAlpha.getValue();
	        mCsPane.setBaseColor(mColTemp);
	    }
	    if ("ColourSelected".equals(id)) {
	        mCol=mColTemp;
	        setBaseColor(mCol);
	        setActionEventId(mActionId);  // this is the users close event id... (kludge!)
	        distributeActionEvent();
	        setActionEventId("openColourSelector");
	                
	        EditorGui.widgetsToDelete.add(mCsPane);
	        EditorGui.widgetsToDelete.add(mOkButt);
	        EditorGui.widgetsToDelete.add(mAlpha);
	        EditorGui.widgetsToDelete.add(mRed);
	        EditorGui.widgetsToDelete.add(mGreen);
	        EditorGui.widgetsToDelete.add(mBlue);
	    }
	}
	
	public Color getSelectedColour() {
	    return mCol;
	}
	
	public void setSelectedColour(Color c) {
	    mCol = c;
	    mColTemp = c;
	    setBaseColor(mCol);
	}
}
