package com.iteye.weimingtom.guichan.platform;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.listener.ApplicationListener;

public interface AppLauncher {
    public abstract int getWindowWidth();
    public abstract int getWindowHeight();
    public abstract ApplicationListener getApplicationListener();
	
	public abstract void init();
    public abstract void halt();
    public abstract void dispose();
    public abstract void run(ApplicationListener appListener_) throws GuichanException;
}
