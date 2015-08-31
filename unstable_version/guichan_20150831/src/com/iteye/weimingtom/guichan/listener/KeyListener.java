package com.iteye.weimingtom.guichan.listener;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.KeyEvent;

public interface KeyListener {
    public void keyPressed(KeyEvent keyEvent) throws GuichanException;
    public void keyReleased(KeyEvent keyEvent) throws GuichanException;
}
