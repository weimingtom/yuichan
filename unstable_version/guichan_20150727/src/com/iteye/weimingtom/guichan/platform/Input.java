package com.iteye.weimingtom.guichan.platform;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.KeyInput;
import com.iteye.weimingtom.guichan.basic.MouseInput;

public abstract class Input {

	public Input() {
		
	}

    public abstract boolean isKeyQueueEmpty();
    public abstract KeyInput dequeueKeyInput() throws GuichanException;
    public abstract boolean isMouseQueueEmpty();
    public abstract MouseInput dequeueMouseInput() throws GuichanException;
    public abstract void _pollInput();
}
