package com.iteye.weimingtom.guichan.listener;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.Event;

public interface FocusListener {
    public void focusGained(Event event);
    public void focusLost(Event event) throws GuichanException;
}
