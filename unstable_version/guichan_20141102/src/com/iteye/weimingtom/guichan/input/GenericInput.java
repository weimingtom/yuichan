package com.iteye.weimingtom.guichan.input;

import java.util.LinkedList;
import java.util.Queue;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.KeyInput;
import com.iteye.weimingtom.guichan.basic.MouseInput;
import com.iteye.weimingtom.guichan.platform.Input;

public class GenericInput extends Input {
    protected Queue<KeyInput> mKeyInputQueue = new LinkedList<KeyInput>();
    protected Queue<MouseInput> mMouseInputQueue = new LinkedList<MouseInput>();
    
	public GenericInput() {
		
	}

    public void pushKeyPressed(int unicode) {
        // TODO
    }
	
    public void pushKeyReleased(int unicode) {
        // TODO
    }
    
    public void pushMouseButtonPressed(int x, int y, int button) {
        MouseInput mouseInput = new MouseInput();
        mouseInput.setX(x);
        mouseInput.setY(y);
        mouseInput.setButton(button);
        mouseInput.setType(MouseInput.PRESSED);
        mMouseInputQueue.offer(mouseInput); // FIXME: push()
    }
    
    public void pushMouseButtonReleased(int x, int y, int button) {
        MouseInput mouseInput = new MouseInput();
        mouseInput.setX(x);
        mouseInput.setY(y);
        mouseInput.setButton(button);
        mouseInput.setType(MouseInput.RELEASED);
        mMouseInputQueue.offer(mouseInput); // FIXME: push()
    }
    
    public void pushMouseWheelMovedUp(int x, int y) {
        MouseInput mouseInput = new MouseInput();
        mouseInput.setX(x);
        mouseInput.setY(y);
        mouseInput.setType(MouseInput.WHEEL_MOVED_UP);
        mMouseInputQueue.offer(mouseInput); // FIXME: push
    }
    
    public void pushMouseWheelMovedDown(int x, int y) {
        MouseInput mouseInput = new MouseInput();
        mouseInput.setX(x);
        mouseInput.setY(y);
        mouseInput.setType(MouseInput.WHEEL_MOVED_DOWN);
        mMouseInputQueue.offer(mouseInput); // FIXME: push
    }
    
    public void pushMouseMoved(int x, int y) {
        MouseInput mouseInput = new MouseInput();
        mouseInput.setX(x);
        mouseInput.setY(y);
        mouseInput.setType(MouseInput.MOVED);
        mMouseInputQueue.offer(mouseInput); // FIXME: push
    }
    
 	@Override
	public boolean isKeyQueueEmpty() {
		return mKeyInputQueue.isEmpty();
	}

	@Override
	public KeyInput dequeueKeyInput() throws GuichanException {
        KeyInput keyInput;
        if (mKeyInputQueue.isEmpty()) {
            throw new GuichanException("The queue is empty.");
        }
        keyInput = mKeyInputQueue.poll(); //FIXME: front & pop 
        return keyInput;
	}

	@Override
	public boolean isMouseQueueEmpty() {
		return mMouseInputQueue.isEmpty();
	}

	@Override
	public MouseInput dequeueMouseInput() throws GuichanException {
        MouseInput mouseInput;
        if (mMouseInputQueue.isEmpty()) {
            throw new GuichanException("The queue is empty.");
        }
        mouseInput = mMouseInputQueue.poll(); //FIXME: front & pop
        return mouseInput;
	}

	@Override
	public void _pollInput() {
		// Does nothing.
	}
}
