package com.iteye.weimingtom.guichan.listener;

import com.iteye.weimingtom.guichan.event.Event;

public interface WidgetListener {
    public void widgetResized(Event event);
    public void widgetMoved(Event event);
    public void widgetHidden(Event event);
    public void widgetShown(Event event);
}
