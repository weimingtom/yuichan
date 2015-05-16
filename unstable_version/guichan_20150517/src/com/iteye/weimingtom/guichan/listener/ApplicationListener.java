package com.iteye.weimingtom.guichan.listener;

import com.iteye.weimingtom.guichan.basic.GuichanException;

public interface ApplicationListener {
	public void onInit() throws GuichanException;
	
	public void onTimer() throws GuichanException;
	
	public void onHalt() throws GuichanException;
}
