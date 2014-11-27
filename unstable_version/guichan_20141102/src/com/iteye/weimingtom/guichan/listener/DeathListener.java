package com.iteye.weimingtom.guichan.listener;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.Event;

public interface DeathListener {
	public void death(Event event) throws GuichanException;
}
