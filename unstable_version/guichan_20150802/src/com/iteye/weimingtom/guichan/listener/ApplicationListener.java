package com.iteye.weimingtom.guichan.listener;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.platform.Graphics;

public interface ApplicationListener {
	
	public void onInit() throws GuichanException;
	
	public void preDraw(Graphics graphics) throws GuichanException;
	
	public void onKeyPressed(KeyEvent keyEvent) throws GuichanException;
	
	public void onTimer() throws GuichanException;
	
	public void onHalt() throws GuichanException;
}
