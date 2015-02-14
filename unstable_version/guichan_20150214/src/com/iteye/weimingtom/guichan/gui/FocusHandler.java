package com.iteye.weimingtom.guichan.gui;

import java.util.List;
import java.util.Vector;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.Event;
import com.iteye.weimingtom.guichan.listener.FocusListener;

public class FocusHandler {
    protected Vector<Widget> mWidgets = new Vector<Widget>();
    protected Widget mFocusedWidget;
    protected Widget mModalFocusedWidget;
    protected Widget mModalMouseInputFocusedWidget;
    protected Widget mDraggedWidget;
    protected Widget mLastWidgetWithMouse;
    protected Widget mLastWidgetWithModalFocus;
    protected Widget mLastWidgetWithModalMouseInputFocus;
    protected Widget mLastWidgetPressed;
	
	public FocusHandler() {
        mFocusedWidget = null;
        mModalFocusedWidget = null;
        mModalMouseInputFocusedWidget = null;
        mDraggedWidget = null;
        mLastWidgetWithMouse = null;
        mLastWidgetWithModalFocus = null;
        mLastWidgetWithModalMouseInputFocus = null;
        mLastWidgetPressed = null;
	}

    public void requestFocus(Widget widget) throws GuichanException {
        if (widget == null || widget == mFocusedWidget) {
            return;
        }
        int i = 0;
        int toBeFocusedIndex = -1;
        for (i = 0; i < mWidgets.size(); ++i) {
            if (mWidgets.get(i) == widget) {
                toBeFocusedIndex = i;
                break;
            }
        }
        if (toBeFocusedIndex < 0) {
            throw new GuichanException("Trying to focus a none existing widget.");
        }
        Widget oldFocused = mFocusedWidget;
        if (oldFocused != widget) {
            mFocusedWidget = mWidgets.get(toBeFocusedIndex);
            if (oldFocused != null) {
                Event focusEvent = new Event(oldFocused);
                distributeFocusLostEvent(focusEvent);
            }
            Event focusEvent = new Event(mWidgets.get(toBeFocusedIndex));
            distributeFocusGainedEvent(focusEvent);
        }        
    }
	
    public void requestModalFocus(Widget widget) throws GuichanException {
        if (mModalFocusedWidget != null && mModalFocusedWidget != widget) {
            throw new GuichanException("Another widget already has modal focus.");
        }
        mModalFocusedWidget = widget;
        if (mFocusedWidget != null && 
        	!mFocusedWidget.isModalFocused()) {
            focusNone();
        }
    }
    
    public void requestModalMouseInputFocus(Widget widget) throws GuichanException {
        if (mModalMouseInputFocusedWidget != null && 
        	mModalMouseInputFocusedWidget != widget) {
            throw new GuichanException("Another widget already has modal input focus.");
        }
        mModalMouseInputFocusedWidget = widget;
    }
    
    public void releaseModalFocus(Widget widget) {
        if (mModalFocusedWidget == widget) {
            mModalFocusedWidget = null;
        }
    }
    
    public void releaseModalMouseInputFocus(Widget widget) {
        if (mModalMouseInputFocusedWidget == widget) {
            mModalMouseInputFocusedWidget = null;
        }
    }
    
    public Widget getFocused() {
        return mFocusedWidget;
    }
    
    public Widget getModalFocused() {
        return mModalFocusedWidget;
    }
    
    public Widget getModalMouseInputFocused() {
        return mModalMouseInputFocusedWidget;
    }
    
    public void focusNext() throws GuichanException {
        int i;
        int focusedWidget = -1;
        for (i = 0; i < (int)mWidgets.size(); ++i) {
            if (mWidgets.get(i) == mFocusedWidget) {
                focusedWidget = i;
            }
        }
        int focused = focusedWidget;
        i = (int)mWidgets.size();
        do
        {
            ++focusedWidget;
            if (i==0) {
                focusedWidget = -1;
                break;
            }
            --i;
            if (focusedWidget >= (int)mWidgets.size()) {
                focusedWidget = 0;
            }
            if (focusedWidget == focused) {
                return;
            }
        } while (!mWidgets.get(focusedWidget).isFocusable());
        if (focusedWidget >= 0) {
            mFocusedWidget = mWidgets.get(focusedWidget);
            Event focusEvent = new Event(mFocusedWidget);
            distributeFocusGainedEvent(focusEvent);
        }
        if (focused >= 0) {
            Event focusEvent = new Event(mWidgets.get(focused));
            distributeFocusLostEvent(focusEvent);
        }
    }
    
    public void focusPrevious() throws GuichanException {
        if (mWidgets.size() == 0) {
            mFocusedWidget = null;
            return;
        }
        int i;
        int focusedWidget = -1;
        for (i = 0; i < (int)mWidgets.size(); ++i) {
            if (mWidgets.get(i) == mFocusedWidget) {
                focusedWidget = i;
            }
        }
        int focused = focusedWidget;
        i = (int)mWidgets.size();
        do
        {
            --focusedWidget;
            if (i==0) {
                focusedWidget = -1;
                break;
            }
            --i;
            if (focusedWidget <= 0) {
                focusedWidget = mWidgets.size() - 1;
            }
            if (focusedWidget == focused) {
                return;
            }
        } while (!mWidgets.get(focusedWidget).isFocusable());
        if (focusedWidget >= 0) {
            mFocusedWidget = mWidgets.get(focusedWidget);
            Event focusEvent = new Event(mFocusedWidget);
            distributeFocusGainedEvent(focusEvent);
        }
        if (focused >= 0) {
            Event focusEvent = new Event(mWidgets.get(focused));
            distributeFocusLostEvent(focusEvent);
        }
    }
    
    public boolean isFocused(Widget widget) {
        return mFocusedWidget == widget;
    }

    public void add(Widget widget) {
        mWidgets.add(widget);
    }

    public void remove(Widget widget) {
        if (isFocused(widget)) {
            mFocusedWidget = null;
        }
        mWidgets.remove(widget);//FIXME:
        if (mDraggedWidget == widget) {
            mDraggedWidget = null;
            return;
        }
        if (mLastWidgetWithMouse == widget) {
            mLastWidgetWithMouse = null;
            return;
        }
        if (mLastWidgetWithModalFocus == widget) {
            mLastWidgetWithModalFocus = null;
            return;
        }
        if (mLastWidgetWithModalMouseInputFocus == widget) {
            mLastWidgetWithModalMouseInputFocus = null;
            return;
        }
        if (mLastWidgetPressed == widget) {
            mLastWidgetPressed = null;
            return;
        }
    }
    
    public void focusNone() throws GuichanException {
        if (mFocusedWidget != null) {
            Widget focused = mFocusedWidget;
            mFocusedWidget = null;
            Event focusEvent = new Event(focused);
            distributeFocusLostEvent(focusEvent);
        }
    }

    public void tabNext() throws GuichanException {
        if (mFocusedWidget != null) {
            if (!mFocusedWidget.isTabOutEnabled()) {
                return;
            }
        }
        if (mWidgets.size() == 0) {
            mFocusedWidget = null;
            return;
        }
        int i;
        int focusedWidget = -1;
        for (i = 0; i < (int)mWidgets.size(); ++i) {
            if (mWidgets.get(i) == mFocusedWidget) {
                focusedWidget = i;
            }
        }
        int focused = focusedWidget;
        boolean done = false;
        i = (int)mWidgets.size();
        do {
            ++focusedWidget;
            if (i == 0) {
                focusedWidget = -1;
                break;
            }
            --i;
            if (focusedWidget >= (int)mWidgets.size()) {
                focusedWidget = 0;
            }
            if (focusedWidget == focused) {
                return;
            }
            if (mWidgets.get(focusedWidget).isFocusable() &&
                mWidgets.get(focusedWidget).isTabInEnabled() &&
                (mModalFocusedWidget == null ||
                 mWidgets.get(focusedWidget).isModalFocused())) {
                done = true;
            }
        } while (!done);
        if (focusedWidget >= 0) {
            mFocusedWidget = mWidgets.get(focusedWidget);
            Event focusEvent = new Event(mFocusedWidget);
            distributeFocusGainedEvent(focusEvent);
        }
        if (focused >= 0) {
            Event focusEvent = new Event(mWidgets.get(focused));
            distributeFocusLostEvent(focusEvent);
        }
    }
    
    public void tabPrevious() throws GuichanException {
        if (mFocusedWidget != null) {
            if (!mFocusedWidget.isTabOutEnabled()) {
                return;
            }
        }
        if (mWidgets.size() == 0) {
            mFocusedWidget = null;
            return;
        }
        int i;
        int focusedWidget = -1;
        for (i = 0; i < (int)mWidgets.size(); ++i) {
            if (mWidgets.get(i) == mFocusedWidget) {
                focusedWidget = i;
            }
        }
        int focused = focusedWidget;
        boolean done = false;
        i = (int)mWidgets.size();
        do {
            --focusedWidget;
            if (i == 0) {
                focusedWidget = -1;
                break;
            }
            --i;
            if (focusedWidget <= 0) {
                focusedWidget = mWidgets.size() - 1;
            }
            if (focusedWidget == focused) {
                return;
            }
            if (mWidgets.get(focusedWidget).isFocusable() &&
                mWidgets.get(focusedWidget).isTabInEnabled() &&
                (mModalFocusedWidget == null ||
                 mWidgets.get(focusedWidget).isModalFocused())) {
                done = true;
            }
        } while (!done);
        if (focusedWidget >= 0) {
            mFocusedWidget = mWidgets.get(focusedWidget);
            Event focusEvent = new Event(mFocusedWidget);
            distributeFocusGainedEvent(focusEvent);
        }
        if (focused >= 0) {
            Event focusEvent = new Event(mWidgets.get(focused));
            distributeFocusLostEvent(focusEvent);
        }
    }
    
    public Widget getDraggedWidget() {
        return mDraggedWidget;
    }

    public void setDraggedWidget(Widget draggedWidget) {
        mDraggedWidget = draggedWidget;
    }

    public Widget getLastWidgetWithMouse() {
        return mLastWidgetWithMouse;
    }

    public void setLastWidgetWithMouse(Widget lastWidgetWithMouse) {
        mLastWidgetWithMouse = lastWidgetWithMouse;
    }

    public Widget getLastWidgetWithModalFocus() {
        return mLastWidgetWithModalFocus;
    }

    public void setLastWidgetWithModalFocus(Widget lastWidgetWithModalFocus) {
        mLastWidgetWithModalFocus = lastWidgetWithModalFocus;
    }

    public Widget getLastWidgetWithModalMouseInputFocus() {
        return mLastWidgetWithModalMouseInputFocus;
    }

    public void setLastWidgetWithModalMouseInputFocus(Widget lastWidgetWithModalMouseInputFocus) {
        mLastWidgetWithModalMouseInputFocus = lastWidgetWithModalMouseInputFocus;
    }

    public Widget getLastWidgetPressed() {
        return mLastWidgetPressed;
    }

    public void setLastWidgetPressed(Widget lastWidgetPressed) {
        mLastWidgetPressed = lastWidgetPressed;
    }
    
	protected void distributeFocusLostEvent(Event focusEvent) throws GuichanException {
        Widget sourceWidget = focusEvent.getSource();
        List<FocusListener> focusListeners = sourceWidget._getFocusListeners();
        for (FocusListener focusListener : focusListeners) {
        	focusListener.focusLost(focusEvent);
        }
    }

	protected void distributeFocusGainedEvent(Event focusEvent) {
        Widget sourceWidget = focusEvent.getSource();
        List<FocusListener> focusListeners = sourceWidget._getFocusListeners();
        for (FocusListener focusListener : focusListeners) {
        	focusListener.focusGained(focusEvent);
        }
    }
}
