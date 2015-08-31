package com.iteye.weimingtom.guichan.event;

import com.iteye.weimingtom.guichan.gui.Widget;

public class ActionEvent extends Event {
    protected String mId;
    
	public ActionEvent(Widget source, String id) {
		super(source);
        mId = id;
    }
    
    public String getId() {
    	return mId;
    }
}
