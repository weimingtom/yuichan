package com.iteye.weimingtom.guichan.platform.awt;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Key;
import com.iteye.weimingtom.guichan.basic.KeyInput;
import com.iteye.weimingtom.guichan.basic.MouseInput;
import com.iteye.weimingtom.guichan.platform.Input;

public class AWTInput extends Input {
    private Queue<KeyInput> mKeyInputQueue = new LinkedList<KeyInput>();
    private Queue<MouseInput> mMouseInputQueue = new LinkedList<MouseInput>();
    private boolean mMouseDown;
    private boolean mMouseInWindow;
    
    public AWTInput() {
        mMouseInWindow = true;
        mMouseDown = false;
    }
    
	@Override
	public boolean isKeyQueueEmpty() {
		return mKeyInputQueue.isEmpty();
	}

	@Override
	public KeyInput dequeueKeyInput() throws GuichanException {
        KeyInput keyInput = new KeyInput();
        if (mKeyInputQueue.isEmpty()) {
            throw new GuichanException("The queue is empty.");
        }
        keyInput = mKeyInputQueue.poll();
        return keyInput;
	}

	@Override
	public boolean isMouseQueueEmpty() {
		return mMouseInputQueue.isEmpty();
	}

	@Override
	public MouseInput dequeueMouseInput() throws GuichanException {
		return null;
	}

	@Override
	public void _pollInput() {
		
	}
	
	//private method
	public void pushInput(java.awt.event.KeyEvent event) {
        KeyInput keyInput = new KeyInput();
        MouseInput mouseInput = new MouseInput();
        
        int value = convertAWTEventToGuichanKeyValue(event);

        if (value == -1) {
            value = (int)event.getKeyCode();
        }
        
        //@see http://www.java2s.com/Tutorial/Java/0280__SWT/Printkeystatecodeandcharacter.htm
        keyInput.setKey(new Key(value));
        keyInput.setType(KeyInput.PRESSED);
        int mod = event.getModifiersEx();
        keyInput.setShiftPressed((mod & InputEvent.SHIFT_DOWN_MASK) != 0);
        keyInput.setControlPressed((mod & InputEvent.CTRL_DOWN_MASK) != 0);
//        keyInput.setAltPressed((mod & InputEvent.ALT_DOWN_MASK) != 0);
//        keyInput.setMetaPressed(event.key.keysym.mod & KMOD_META);
//        keyInput.setNumericPad(event.key.keysym.sym >= SDLK_KP0
//                             && event.key.keysym.sym <= SDLK_KP_EQUALS);

        mKeyInputQueue.offer(keyInput);
	}

	protected int convertAWTEventToGuichanKeyValue(java.awt.event.KeyEvent event) {
	    int value = -1;

	    switch (event.getKeyCode()) {
	    case KeyEvent.VK_TAB:
	    	value = Key.TAB;
	        break;
	         
	    case KeyEvent.VK_ALT:
	        value = Key.LEFT_ALT;
	        break;
	          
//	    case KeyEvent.VK_ALT:
//	        value = Key.RIGHT_ALT;
//	        break;
	        
	    case KeyEvent.VK_SHIFT:
	        value = Key.LEFT_SHIFT;
	        break;
	          
//	    case KeyEvent.VK_SHIFT:
//	        value = Key.RIGHT_SHIFT;
//	        break;
	        
	    case KeyEvent.VK_CONTROL:
	        value = Key.LEFT_CONTROL;
	        break;
	          
//	    case KeyEvent.VK_CONTROL:
//	        value = Key.RIGHT_CONTROL;
//	        break;
	          
	    case KeyEvent.VK_BACK_SPACE:
	        value = Key.BACKSPACE;
	        break;
	          
	    case KeyEvent.VK_PAUSE:
	        value = Key.PAUSE;
	        break;
	              
	    case KeyEvent.VK_SPACE:
	        value = Key.SPACE;
	        break;
	          
	    case KeyEvent.VK_ESCAPE:
	        value = Key.ESCAPE;
	        break;
	              
	    case KeyEvent.VK_DELETE:
	        value = Key.DELETE;
	        break;
	          
	    case KeyEvent.VK_INSERT:
	        value = Key.INSERT;
	        break;
	          
	    case KeyEvent.VK_HOME:
	        value = Key.HOME;
	        break;
	          
	    case KeyEvent.VK_END:
	        value = Key.END;
	        break;
	        
	    case KeyEvent.VK_PAGE_UP:
	        value = Key.PAGE_UP;
	        break;
	        
	    case KeyEvent.VK_PRINTSCREEN:
	        value = Key.PRINT_SCREEN;
	        break;
	          
	    case KeyEvent.VK_PAGE_DOWN:
	        value = Key.PAGE_DOWN;
	        break;
	        
	    case KeyEvent.VK_F1:
	        value = Key.F1;
	        break;
	        
	    case KeyEvent.VK_F2:
	        value = Key.F2;
	        break;
	        
	    case KeyEvent.VK_F3:
	        value = Key.F3;
	        break;
	        
	    case KeyEvent.VK_F4:
	        value = Key.F4;
	        break;
	        
	    case KeyEvent.VK_F5:
	        value = Key.F5;
	        break;
	        
	    case KeyEvent.VK_F6:
	        value = Key.F6;
	        break;
	        
	    case KeyEvent.VK_F7:
	        value = Key.F7;
	        break;
	        
	    case KeyEvent.VK_F8:
	        value = Key.F8;
	        break;
	        
	    case KeyEvent.VK_F9:
	        value = Key.F9;
	        break;
	        
	    case KeyEvent.VK_F10:
	        value = Key.F10;
	        break;
	        
	    case KeyEvent.VK_F11:
	        value = Key.F11;
	        break;
	        
	    case KeyEvent.VK_F12:
	        value = Key.F12;
	        break;
	        
	    case KeyEvent.VK_F13:
	        value = Key.F13;
	        break;
	        
	    case KeyEvent.VK_F14:
	        value = Key.F14;
	        break;
	        
	    case KeyEvent.VK_F15:
	        value = Key.F15;
	        break;
	        
	    case KeyEvent.VK_NUM_LOCK:
	        value = Key.NUM_LOCK;
	        break;
	        
	    case KeyEvent.VK_CAPS_LOCK:
	        value = Key.CAPS_LOCK;
	        break;
	        
	    case KeyEvent.VK_SCROLL_LOCK:
	        value = Key.SCROLL_LOCK;
	        break;

//	    case SDLK_RMETA:
//	        value = Key.RIGHT_META;
//	        break;
//	          
//	    case SDLK_LMETA:
//	        value = Key.LEFT_META;
//	        break;
//	          
//	    case SDLK_LSUPER:
//	        value = Key.LEFT_SUPER;
//	        break;
//	          
//	    case SDLK_RSUPER:
//	        value = Key.RIGHT_SUPER;
//	        break;
//	          
//	    case SDLK_MODE:
//	        value = Key::ALT_GR;
//	        break;
	          
	    case KeyEvent.VK_UP:
	        value = Key.UP;
	        break;
	          
	    case KeyEvent.VK_DOWN:
	        value = Key.DOWN;
	        break;
	        
	    case KeyEvent.VK_LEFT:
	        value = Key.LEFT;
	        break;
	        
	    case KeyEvent.VK_RIGHT:
	        value = Key.RIGHT;
	        break;
	        
	    case KeyEvent.VK_ENTER:
	        value = Key.ENTER;
	        break;
	         
	    default:
	        break;
	    }
	    return value;
	}
	
}
