package com.iteye.weimingtom.guichan.event;

import com.iteye.weimingtom.guichan.gui.Widget;

public class Event {
	protected Widget mSource;
	
	public Event(Widget source) {
		mSource = source;
    }
    
	public Widget getSource() {
		return mSource;
    }
}
