package com.iteye.weimingtom.guichan.gui;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Key;
import com.iteye.weimingtom.guichan.basic.KeyInput;
import com.iteye.weimingtom.guichan.basic.MouseInput;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.event.MouseEvent;
import com.iteye.weimingtom.guichan.listener.KeyListener;
import com.iteye.weimingtom.guichan.listener.MouseListener;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Input;
import com.iteye.weimingtom.guichan.platform.awt.AWTGraphics;

public class Gui {
	protected Widget mTop;
	protected Graphics mGraphics;
	protected Input mInput;
	protected FocusHandler mFocusHandler;
	protected boolean mTabbing;

    protected List<KeyListener> mKeyListeners = new ArrayList<KeyListener>();
    protected boolean mShiftPressed;
    protected boolean mMetaPressed;
    protected boolean mControlPressed;
    protected boolean mAltPressed;
    protected int mLastMousePressButton;
    protected int mLastMousePressTimeStamp;
    protected int mLastMouseX;
    protected int mLastMouseY;
    protected int mClickCount;
    protected int mLastMouseDragButton;
    protected Deque<Widget> mWidgetWithMouseQueue = new LinkedList<Widget>();

    public Gui() {
    	mTop = null;
    	mGraphics = null;
    	mInput = null;
    	mTabbing = true;
    	mShiftPressed = false;
    	mMetaPressed = false;
    	mControlPressed = false;
    	mAltPressed = false;
    	mLastMousePressButton = 0;
    	mLastMousePressTimeStamp = 0;
    	mLastMouseX = 0;
    	mLastMouseY = 0;
    	mClickCount = 1;
    	mLastMouseDragButton = 0;
    	mFocusHandler = new FocusHandler();
    }
    
    public void dispose() {
//        if (Widget.widgetExists(mTop)) {
//            setTop(null);
//        }
//        delete mFocusHandler;
    }
    
    protected void handleMouseInput() throws GuichanException {
        while (!mInput.isMouseQueueEmpty()) {
            MouseInput mouseInput = mInput.dequeueMouseInput();
            mLastMouseX = mouseInput.getX();
            mLastMouseY = mouseInput.getY();
            switch (mouseInput.getType()) {
            case MouseInput.PRESSED:
            	handleMousePressed(mouseInput);
                break;
                  
            case MouseInput.RELEASED:
                handleMouseReleased(mouseInput);
                break;
                  
            case MouseInput.MOVED:
                handleMouseMoved(mouseInput);
                break;
            
            case MouseInput.WHEEL_MOVED_DOWN:
                handleMouseWheelMovedDown(mouseInput);
                break;
                  
            case MouseInput.WHEEL_MOVED_UP:
                handleMouseWheelMovedUp(mouseInput);
                break;
            
            default:
                throw new GuichanException("Unknown mouse input type.");
            }
        }
    }
    
    protected void handleKeyInput() throws GuichanException {
        while (!mInput.isKeyQueueEmpty()) {
            KeyInput keyInput = mInput.dequeueKeyInput();
            mShiftPressed = keyInput.isShiftPressed();
            mMetaPressed = keyInput.isMetaPressed();
            mControlPressed = keyInput.isControlPressed();
            mAltPressed = keyInput.isAltPressed();
            KeyEvent keyEventToGlobalKeyListeners = new KeyEvent(null, 
            		mShiftPressed,
            		mControlPressed,
            		mAltPressed,
            		mMetaPressed,
            		keyInput.getType(),
            		keyInput.isNumericPad(),
            		keyInput.getKey());
            distributeKeyEventToGlobalKeyListeners(keyEventToGlobalKeyListeners);
            if (keyEventToGlobalKeyListeners.isConsumed()) {
                continue;
            }
            boolean keyEventConsumed = false;
            if (mFocusHandler.getFocused() != null) {
                KeyEvent keyEvent = new KeyEvent(getKeyEventSource(),
                                  mShiftPressed,
                                  mControlPressed,
                                  mAltPressed,
                                  mMetaPressed,
                                  keyInput.getType(),
                                  keyInput.isNumericPad(),
                                  keyInput.getKey());
                if (!mFocusHandler.getFocused().isFocusable()) {
                    mFocusHandler.focusNone();
                } else {                    
                    distributeKeyEvent(keyEvent);                    
                }
                keyEventConsumed = keyEvent.isConsumed();
            }
            if (!keyEventConsumed
                && mTabbing
                && keyInput.getKey().getValue() == Key.TAB
                && keyInput.getType() == KeyInput.PRESSED) {
                if (keyInput.isShiftPressed()) {
                    mFocusHandler.tabPrevious();
                } else {
                    mFocusHandler.tabNext();
                }
            }
        }
    }
    
    protected void handleMouseMoved(MouseInput mouseInput) throws GuichanException {
        // Check if the mouse leaves the application window.
        if (!mWidgetWithMouseQueue.isEmpty()
            && (mouseInput.getX() < 0
                || mouseInput.getY() < 0
                || !mTop.getDimension().isPointInRect(mouseInput.getX(), mouseInput.getY()))
            ) {
            while (!mWidgetWithMouseQueue.isEmpty()) {
                Widget widget = mWidgetWithMouseQueue.getFirst();
                if (Widget.widgetExists(widget)) {
                    distributeMouseEvent(widget,
                                         MouseEvent.EXITED,
                                         mouseInput.getButton(),
                                         mouseInput.getX(),
                                         mouseInput.getY(),
                                         true,
                                         true);
                }

                mWidgetWithMouseQueue.pollFirst();
            }
            return;
        }
        boolean widgetWithMouseQueueCheckDone = mWidgetWithMouseQueue.isEmpty();
        while (!widgetWithMouseQueueCheckDone) {
            int iterations = 0;
            Iterator<Widget> iter = mWidgetWithMouseQueue.iterator();
            while (iter.hasNext()) {
            	Widget widget = iter.next();
            	if (!Widget.widgetExists(widget)) {
                    iter.remove();
                    break;
                } else {
                    int[] x = new int[1];
                    int[] y = new int[1];
                    widget.getAbsolutePosition(x, y);
                    if (x[0] > mouseInput.getX()
                        || y[0] > mouseInput.getY()
                        || x[0] + widget.getWidth() <= mouseInput.getX()
                        || y[0] + widget.getHeight() <= mouseInput.getY()
                        || !widget.isVisible()) {
                        distributeMouseEvent(widget,
                                             MouseEvent.EXITED,
                                             mouseInput.getButton(),
                                             mouseInput.getX(),
                                             mouseInput.getY(),
                                             true,
                                             true);                                       
                        mClickCount = 1;
                        mLastMousePressTimeStamp = 0;
                        iter.remove();
                        break;
                    }
                }
                iterations++;
            }
            widgetWithMouseQueueCheckDone = iterations == mWidgetWithMouseQueue.size();
        }
        Widget parent = getMouseEventSource(mouseInput.getX(), mouseInput.getY());
        Widget widget = parent;
        if (mFocusHandler.getModalMouseInputFocused() != null
            && widget == mFocusHandler.getModalMouseInputFocused()
            && Widget.widgetExists(widget)) {
            int[] x = new int[1];
            int[] y = new int[1];
            widget.getAbsolutePosition(x, y);
            if (x[0] > mouseInput.getX()
                || y[0] > mouseInput.getY()
                || x[0] + widget.getWidth() <= mouseInput.getX() 
                || y[0] + widget.getHeight() <= mouseInput.getY()) {
                parent = null;
            }
        }
        while (parent != null) {
            parent = (Widget)widget.getParent();
            boolean widgetIsPresentInQueue = false;
            Iterator<Widget> iter = mWidgetWithMouseQueue.iterator();
            while (iter.hasNext()) {
                if (iter.next() == widget) {
                    widgetIsPresentInQueue = true;
                    break;
                }
            }
            if (!widgetIsPresentInQueue
                && Widget.widgetExists(widget)) {
                distributeMouseEvent(widget,
                                     MouseEvent.ENTERED,
                                     mouseInput.getButton(),
                                     mouseInput.getX(),
                                     mouseInput.getY(),
                                     true,
                                     true);
                mWidgetWithMouseQueue.offerFirst(widget);
            }
            Widget swap = widget;
            widget = parent;
            parent = (Widget)swap.getParent();
        }
        if (mFocusHandler.getDraggedWidget() != null) {
            distributeMouseEvent(mFocusHandler.getDraggedWidget(),
                                 MouseEvent.DRAGGED,
                                 mLastMouseDragButton,
                                 mouseInput.getX(),
                                 mouseInput.getY());
        } else {
            Widget sourceWidget = getMouseEventSource(mouseInput.getX(), mouseInput.getY());
            distributeMouseEvent(sourceWidget,
                                 MouseEvent.MOVED,
                                 mouseInput.getButton(),
                                 mouseInput.getX(),
                                 mouseInput.getY());
        }
    }
    
    protected void handleMousePressed(MouseInput mouseInput) throws GuichanException {
        Widget sourceWidget = getMouseEventSource(mouseInput.getX(), mouseInput.getY());
        if (mFocusHandler.getDraggedWidget() != null) {
            sourceWidget = mFocusHandler.getDraggedWidget();
        }
        int[] sourceWidgetX = new int[1];
        int[] sourceWidgetY = new int[1];
        sourceWidget.getAbsolutePosition(sourceWidgetX, sourceWidgetY);
        if ((mFocusHandler.getModalFocused() != null
            && sourceWidget.isModalFocused())
            || mFocusHandler.getModalFocused() == null) {
            sourceWidget.requestFocus();
        }
        if (mouseInput.getTimeStamp() - mLastMousePressTimeStamp < 250
            && mLastMousePressButton == mouseInput.getButton()) {
            mClickCount++;
        } else {
            mClickCount = 1;
        }

        distributeMouseEvent(sourceWidget,
                             MouseEvent.PRESSED,
                             mouseInput.getButton(),
                             mouseInput.getX(),
                             mouseInput.getY());
        mFocusHandler.setLastWidgetPressed(sourceWidget);
        mFocusHandler.setDraggedWidget(sourceWidget);
        mLastMouseDragButton = mouseInput.getButton();
        mLastMousePressButton = mouseInput.getButton();
        mLastMousePressTimeStamp = mouseInput.getTimeStamp();
    }
    
    protected void handleMouseWheelMovedDown(MouseInput mouseInput) throws GuichanException {
        Widget sourceWidget = getMouseEventSource(mouseInput.getX(), mouseInput.getY());
        if (mFocusHandler.getDraggedWidget() != null) {
            sourceWidget = mFocusHandler.getDraggedWidget();
        }
        int[] sourceWidgetX = new int[1];
        int[] sourceWidgetY = new int[1];
        sourceWidget.getAbsolutePosition(sourceWidgetX, sourceWidgetY);
        distributeMouseEvent(sourceWidget,
                             MouseEvent.WHEEL_MOVED_DOWN,
                             mouseInput.getButton(),
                             mouseInput.getX(),
                             mouseInput.getY());
    }
    
    protected void handleMouseWheelMovedUp(MouseInput mouseInput) throws GuichanException {
        Widget sourceWidget = getMouseEventSource(mouseInput.getX(), mouseInput.getY());
        if (mFocusHandler.getDraggedWidget() != null) {
            sourceWidget = mFocusHandler.getDraggedWidget();
        }
        int[] sourceWidgetX = new int[1];
        int[] sourceWidgetY = new int[1];
        sourceWidget.getAbsolutePosition(sourceWidgetX, sourceWidgetY);
        distributeMouseEvent(sourceWidget,
                             MouseEvent.WHEEL_MOVED_UP,
                             mouseInput.getButton(),
                             mouseInput.getX(),
                             mouseInput.getY());
    }
    
    protected void handleMouseReleased(MouseInput mouseInput) throws GuichanException {
        Widget sourceWidget = getMouseEventSource(mouseInput.getX(), mouseInput.getY());
        if (mFocusHandler.getDraggedWidget() != null) {
            if (sourceWidget != mFocusHandler.getLastWidgetPressed()) {
                mFocusHandler.setLastWidgetPressed(null);
            }
            sourceWidget = mFocusHandler.getDraggedWidget();
        }
        int[] sourceWidgetX = new int[1];
        int[] sourceWidgetY = new int[1];
        sourceWidget.getAbsolutePosition(sourceWidgetX, sourceWidgetY);
        distributeMouseEvent(sourceWidget,
                             MouseEvent.RELEASED,
                             mouseInput.getButton(),
                             mouseInput.getX(),
                             mouseInput.getY());
        if (mouseInput.getButton() == mLastMousePressButton            
            && mFocusHandler.getLastWidgetPressed() == sourceWidget) {
            distributeMouseEvent(sourceWidget,
                                 MouseEvent.CLICKED,
                                 mouseInput.getButton(),
                                 mouseInput.getX(),
                                 mouseInput.getY());
            mFocusHandler.setLastWidgetPressed(null);
        } else {
            mLastMousePressButton = 0;
            mClickCount = 0;
        }
        if (mFocusHandler.getDraggedWidget() != null) {
            mFocusHandler.setDraggedWidget(null);
        }
    }
    
    protected void handleModalFocus() throws GuichanException {
        if ((mFocusHandler.getLastWidgetWithModalFocus() 
                != mFocusHandler.getModalFocused())
             && (mFocusHandler.getLastWidgetWithModalFocus() == null)) {
            handleModalFocusGained();
            mFocusHandler.setLastWidgetWithModalFocus(mFocusHandler.getModalFocused());
        } else if ((mFocusHandler.getLastWidgetWithModalFocus()
                    != mFocusHandler.getModalFocused())
                    && (mFocusHandler.getLastWidgetWithModalFocus() != null)) {
            handleModalFocusReleased();
            mFocusHandler.setLastWidgetWithModalFocus(null);
        }
    }
    
    protected void handleModalMouseInputFocus() throws GuichanException {
        if ((mFocusHandler.getLastWidgetWithModalMouseInputFocus() 
                != mFocusHandler.getModalMouseInputFocused())
             && (mFocusHandler.getLastWidgetWithModalMouseInputFocus() == null)) {
            handleModalFocusGained();
            mFocusHandler.setLastWidgetWithModalMouseInputFocus(mFocusHandler.getModalMouseInputFocused());
        } else if ((mFocusHandler.getLastWidgetWithModalMouseInputFocus()
                    != mFocusHandler.getModalMouseInputFocused())
                    && (mFocusHandler.getLastWidgetWithModalMouseInputFocus() != null)) {
            handleModalFocusReleased();
            mFocusHandler.setLastWidgetWithModalMouseInputFocus(null);
        }
    }
    
    protected void handleModalFocusGained() throws GuichanException {
        while (!mWidgetWithMouseQueue.isEmpty()) {
            Widget widget = mWidgetWithMouseQueue.getFirst();
            if (Widget.widgetExists(widget)) {
                distributeMouseEvent(widget,
                                     MouseEvent.EXITED,
                                     mLastMousePressButton,
                                     mLastMouseX,
                                     mLastMouseY,
                                     true,
                                     true);
            }
            mWidgetWithMouseQueue.pollFirst();
        }
        mFocusHandler.setLastWidgetWithModalMouseInputFocus(mFocusHandler.getModalMouseInputFocused());
    }
    
    protected void handleModalFocusReleased() throws GuichanException {
        Widget widget = getMouseEventSource(mLastMouseX, mLastMouseY);
        Widget parent = widget;

        while (parent != null) {
            parent = (Widget)widget.getParent();
            boolean widgetIsPresentInQueue = false;
            Iterator<Widget> iter = mWidgetWithMouseQueue.iterator();
            while (iter.hasNext()) {
                if (iter.next() == widget) {
                    widgetIsPresentInQueue = true;
                    break;
                }
            }
            if (!widgetIsPresentInQueue
                && Widget.widgetExists(widget)) {
                distributeMouseEvent(widget,
                                     MouseEvent.ENTERED,
                                     mLastMousePressButton,
                                     mLastMouseX,
                                     mLastMouseY,
                                     false,
                                     true);
                mWidgetWithMouseQueue.offerFirst(widget);
            }
            Widget swap = widget;
            widget = parent;
            parent = (Widget)swap.getParent();
        }
    }
    
    protected void distributeMouseEvent(Widget source, int type,
    		int button, int x, int y) throws GuichanException {
    	distributeMouseEvent(source, type, button, x, y, false, false);
    }

    protected void distributeMouseEvent(Widget source, int type,
    		int button, int x, int y, boolean force) throws GuichanException {
    	distributeMouseEvent(source, type, button, x, y, force, false);
    }
    
    protected void distributeMouseEvent(Widget source, int type,
    		int button, int x, int y, boolean force, boolean toSourceOnly) throws GuichanException {
        Widget parent = source;
        Widget widget = source;
        if (mFocusHandler.getModalFocused() != null
            && !widget.isModalFocused()
            && !force) {
            return;
        }
        if (mFocusHandler.getModalMouseInputFocused() != null
            && !widget.isModalMouseInputFocused()
            && !force) {
            return;
        }
        MouseEvent mouseEvent = new MouseEvent(source,
                              mShiftPressed,
                              mControlPressed,
                              mAltPressed,
                              mMetaPressed,
                              type,
                              button,
                              x,
                              y,
                              mClickCount);
        while (parent != null) {
            // If the widget has been removed due to input
            // cancel the distribution.
            if (!Widget.widgetExists(widget)) {
                break;
            }
            parent = (Widget)widget.getParent();
            if (widget.isEnabled() || force) {
                int[] widgetX = new int[1];
                int[] widgetY = new int[1];
                widget.getAbsolutePosition(widgetX, widgetY);
                mouseEvent.mX = x - widgetX[0];
                mouseEvent.mY = y - widgetY[0];
                List<MouseListener> mouseListeners = widget._getMouseListeners();

                // Send the event to all mouse listeners of the widget.
                Iterator<MouseListener> it = mouseListeners.iterator();
                while (it.hasNext()) {
                    switch (mouseEvent.getType()) {
                    case MouseEvent.ENTERED:
                    	it.next().mouseEntered(mouseEvent);
                        break;
                          
                    case MouseEvent.EXITED:
                    	it.next().mouseExited(mouseEvent);
                        break;
                          
                    case MouseEvent.MOVED:
                    	it.next().mouseMoved(mouseEvent);
                        break;
                          
                    case MouseEvent.PRESSED:
                    	it.next().mousePressed(mouseEvent);
                        break;
                          
                    case MouseEvent.RELEASED:
                    	it.next().mouseReleased(mouseEvent);
                        break;
                      
                    case MouseEvent.WHEEL_MOVED_UP:
                    	it.next().mouseWheelMovedUp(mouseEvent);
                        break;
                          
                    case MouseEvent.WHEEL_MOVED_DOWN:
                    	it.next().mouseWheelMovedDown(mouseEvent);
                        break;
                      
                    case MouseEvent.DRAGGED:
                    	it.next().mouseDragged(mouseEvent);
                        break;
                      
                    case MouseEvent.CLICKED:
                    	it.next().mouseClicked(mouseEvent);
                        break;
                      
                    default:
                        throw new GuichanException("Unknown mouse event type.");
                    }
                }
                if (toSourceOnly) {
                    break;
                }
            }
            Widget swap = widget;
            widget = parent;
            parent = (Widget)swap.getParent();
            if (mFocusHandler.getModalFocused() != null
                && !widget.isModalFocused()) {
                break;
            }
            if (mFocusHandler.getModalMouseInputFocused() != null
                && !widget.isModalMouseInputFocused()) {
                break;
            }
        }
    }
    
    protected void distributeKeyEvent(KeyEvent keyEvent) throws GuichanException {
        Widget parent = keyEvent.getSource();
        Widget widget = keyEvent.getSource();
        if (mFocusHandler.getModalFocused() != null
            && !widget.isModalFocused()) {
            return;
        }
        if (mFocusHandler.getModalMouseInputFocused() != null
            && !widget.isModalMouseInputFocused()) {
            return;
        }
        while (parent != null) {
            if (!Widget.widgetExists(widget)) {
                break;
            }
            parent = (Widget)widget.getParent();
            if (widget.isEnabled()) {
                List<KeyListener> keyListeners = widget._getKeyListeners();
                Iterator<KeyListener> it = keyListeners.iterator();
                while (it.hasNext()) {
                    switch (keyEvent.getType()) {
                    case KeyEvent.PRESSED:
                    	it.next().keyPressed(keyEvent);
                        break;
                          
                    case KeyEvent.RELEASED:
                        it.next().keyReleased(keyEvent);
                        break;
                          
                    default:
                        throw new GuichanException("Unknown key event type.");
                    }   
                }
            }
            Widget swap = widget;
            widget = parent;
            parent = (Widget)swap.getParent();
            if (mFocusHandler.getModalFocused() != null
                && !widget.isModalFocused()) {
            	break;
            }
        }
    }
    
    protected void distributeKeyEventToGlobalKeyListeners(KeyEvent keyEvent) throws GuichanException {
        Iterator<KeyListener> it = mKeyListeners.iterator();
        while (it.hasNext()) {
        	switch (keyEvent.getType()) {
            case KeyEvent.PRESSED:
            	it.next().keyPressed(keyEvent);
                break;
                  
            case KeyEvent.RELEASED:
                it.next().keyReleased(keyEvent);
                break;
                
            default:
                throw new GuichanException("Unknown key event type.");
            }
            if (keyEvent.isConsumed()) {
                break;
            }
        }
    }
    
    protected Widget getWidgetAt(int x, int y) {
        Widget parent = mTop;
        Widget child = mTop;
        while (child != null) {
            Widget swap = child;
            int[] parentX = new int[1];
            int[] parentY = new int[1];
            parent.getAbsolutePosition(parentX, parentY);
            child = parent.getWidgetAt(x - parentX[0], y - parentY[0]);
            parent = swap;
        }
        return parent;
    }
    
    protected Widget getMouseEventSource(int x, int y) throws GuichanException {
        Widget widget = getWidgetAt(x, y);
        if (mFocusHandler.getModalMouseInputFocused() != null
            && !widget.isModalMouseInputFocused()) {
            return mFocusHandler.getModalMouseInputFocused();
        }
        return widget;
    }
    
    protected Widget getKeyEventSource() {
        Widget widget = mFocusHandler.getFocused();
        while (widget._getInternalFocusHandler() != null
               && widget._getInternalFocusHandler().getFocused() != null) {
            widget = widget._getInternalFocusHandler().getFocused();
        }
        return widget;
    }
    
    
    public void setTop(Widget top) {
        if (mTop != null) {
            mTop._setFocusHandler(null);
        }
        if (top != null) {
            top._setFocusHandler(mFocusHandler);
        }
        mTop = top;
    }
    
    public Widget getTop() {
    	return mTop;
    }
    
    public void setGraphics(Graphics graphics) {
    	mGraphics = graphics;
    }
    
    public Graphics getGraphics() {
    	return mGraphics;
    }
    
    public void setInput(Input input) {
    	mInput = input;
    }
    
    public Input getInput() {
    	return mInput;
    }
    
    public void logic() throws GuichanException {
        if (mTop == null) {
            throw new GuichanException("No top widget set");
        }

        handleModalFocus();
        handleModalMouseInputFocus();

        if (mInput != null) {
            mInput._pollInput();
            handleKeyInput();
            handleMouseInput();
        }
        mTop.logic();
    }
    
    public void draw() throws GuichanException {
        if (mTop == null) {
            throw new GuichanException("No top widget set");
        }
        if (mGraphics == null) {
            throw new GuichanException("No graphics set");
        }
        if (!mTop.isVisible()) {
            return;
        }
        mGraphics._beginDraw();
        if (mTop.getFrameSize() > 0) {
            Rectangle rec = new Rectangle(mTop.getDimension());
            rec.x -= mTop.getFrameSize();
            rec.y -= mTop.getFrameSize();
            rec.width += 2 * mTop.getFrameSize();
            rec.height += 2 * mTop.getFrameSize();
            mGraphics.pushClipArea(rec);
            mTop.drawFrame(mGraphics);
            mGraphics.popClipArea();
        }
        mGraphics.pushClipArea(mTop.getDimension());
        mTop.draw(mGraphics);
        mGraphics.popClipArea();
        mGraphics._endDraw();
        
//        ((AWTGraphics)mGraphics).test();
    }
    
    public void focusNone() throws GuichanException {
    	mFocusHandler.focusNone();
    }
    
    public void setTabbingEnabled(boolean tabbing) {
    	mTabbing = tabbing;
    }
    
    public boolean isTabbingEnabled() {
    	return mTabbing;
    }
    
    public void addGlobalKeyListener(KeyListener keyListener) {
    	mKeyListeners.add(keyListener);
    }
    
    public void removeGlobalKeyListener(KeyListener keyListener) {
    	mKeyListeners.remove(keyListener);
    }   
}
