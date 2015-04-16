package com.iteye.weimingtom.guichan.gui;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.Event;
import com.iteye.weimingtom.guichan.listener.DeathListener;
import com.iteye.weimingtom.guichan.platform.Graphics;

public abstract class BasicContainer extends Widget implements DeathListener {
	protected Deque<Widget> mWidgets = new LinkedList<Widget>();
	
	public Deque<Widget> getWidgets() {
		return mWidgets;
	}
	
	public BasicContainer() {
		
	}
	
	public void destory() {
		//FIXME:
		clear();
	}

	@Override
	public void death(Event event) throws GuichanException {
	  	//FIXME:
		Widget widget = event.getSource();
    	if (mWidgets.contains(widget)) {
	    	mWidgets.remove(widget);
	    	return;
	    }
        throw new GuichanException("There is no such widget in this container.");
	}
	
	@Override
    public void moveToTop(Widget widget) throws GuichanException {
    	//FIXME:
    	if (mWidgets.contains(widget)) {
	    	mWidgets.remove(widget);
	    	mWidgets.addLast(widget);
	    	return;
	    }
        throw new GuichanException("There is no such widget in this container.");
    }
	
	@Override
    public void moveToBottom(Widget widget) throws GuichanException {
       	//FIXME:
    	if (mWidgets.contains(widget)) {
	    	mWidgets.remove(widget);
	    	mWidgets.addFirst(widget);
	    	return;
	    }
        throw new GuichanException("There is no such widget in this container.");
    }

	@Override
    public Rectangle getChildrenArea() {
        return new Rectangle(0, 0, getWidth(), getHeight());
    }

	@Override
    public void focusNext() throws GuichanException {
    	//FIXME:
        Iterator<Widget> it = mWidgets.iterator();
        int pos = 0;
        while (it.hasNext()) {
        	if (it.next().isFocused()) {
                break;
            }
        	pos++;
        }
        int end = pos;
        if (!it.hasNext()) {
            it = mWidgets.iterator();
        }
        it.next();
        while (pos == end) { //FIXME:it != end
            if (!it.hasNext()) {
                it = mWidgets.iterator();
                pos = 0;
            }
            Widget widget = it.next();
            if (widget.isFocusable()) {
            	widget.requestFocus();
                return;
            }
            pos++;
        }
    }
	
	@Override
    public void focusPrevious() throws GuichanException {
		//FIXME:
		Iterator<Widget> it = mWidgets.descendingIterator();
        int pos = 0;
		while (it.hasNext()) {
        	if (it.next().isFocused()) {
                break;
            }
        	pos++;
        }
        int end = pos;
        it.next();
        if (!it.hasNext()) {
            it = mWidgets.descendingIterator();
        }
        while (pos == end) { //FIXME:it != end
            if (!it.hasNext()) {
                it = mWidgets.descendingIterator();
                pos = 0;
            }
            Widget widget = it.next();
            if (widget.isFocusable()) {
            	widget.requestFocus();
                return;
            }
            pos++;
        }
    }
	
	@Override
    public Widget getWidgetAt(int x, int y) {
        Rectangle r = getChildrenArea();
        if (!r.isPointInRect(x, y)) {
            return null;
        }
        x -= r.x;
        y -= r.y;
        Iterator<Widget> it = mWidgets.descendingIterator();
        while (it.hasNext()) {
        	Widget widget = it.next();
            if (widget.isVisible() && 
            	widget.getDimension().isPointInRect(x, y)) {
                return widget;
            }
        }
        return null;
    }
	
	@Override
    public void logic() throws GuichanException {
        logicChildren();
    }
	
	@Override
    public void _setFocusHandler(FocusHandler focusHandler) {
        super._setFocusHandler(focusHandler);
        if (mInternalFocusHandler != null) {
            return;
        }
        Iterator<Widget> iter = mWidgets.iterator();
        while (iter.hasNext()) {
        	Widget w = iter.next();
        	System.out.println("_setFocusHandler w : " + w);
            w._setFocusHandler(focusHandler);
        }
    }
	
	protected void add(Widget widget) {
        mWidgets.addLast(widget);
        if (mInternalFocusHandler == null) {
            widget._setFocusHandler(_getFocusHandler());
        } else {
            widget._setFocusHandler(mInternalFocusHandler);
        }
        widget._setParent(this);
        widget.addDeathListener(this);
    }
	
    protected void remove(Widget widget) throws GuichanException {
        Iterator<Widget> iter = mWidgets.iterator();
        while (iter.hasNext()) {
        	Widget w = iter.next();
        	if (w == widget) {
                iter.remove();//FIXME:
                widget._setFocusHandler(null);
                widget._setParent(null);
                widget.removeDeathListener(this);
                return;
            }
        }
        throw new GuichanException("There is no such widget in this container.");
    }
    
    protected void clear() {
    	Iterator<Widget> iter = mWidgets.iterator();
        while (iter.hasNext()) {
        	Widget w = iter.next();
        	w._setFocusHandler(null);
            w._setParent(null);
            w.removeDeathListener(this);
        }
        mWidgets.clear();
    }
    
    protected void drawChildren(Graphics graphics) throws GuichanException {
        graphics.pushClipArea(getChildrenArea());
    	Iterator<Widget> iter = mWidgets.iterator();
        while (iter.hasNext()) {
        	Widget w = iter.next();
            if (w.isVisible()) {
                if (w.getFrameSize() > 0) {
                    Rectangle rec = new Rectangle(w.getDimension());
                    rec.x -= w.getFrameSize();
                    rec.y -= w.getFrameSize();
                    rec.width += 2 * w.getFrameSize();
                    rec.height += 2 * w.getFrameSize();
                    
                    graphics.pushClipArea(rec);
                    w.drawFrame(graphics);
                    graphics.popClipArea();
                }
                graphics.pushClipArea(w.getDimension());
                w.draw(graphics);
                graphics.popClipArea();
            }
        }
        graphics.popClipArea();
    }
	
	protected void logicChildren() throws GuichanException {
    	Iterator<Widget> iter = mWidgets.iterator();
        while (iter.hasNext()) {
            iter.next().logic();
        }
	}
	
	@Override
	public void showWidgetPart(Widget widget, Rectangle area) throws GuichanException {
	    Rectangle widgetArea = getChildrenArea();
        area.x += widget.getX();
        area.y += widget.getY();
        if (area.x + area.width > widgetArea.width) {
            widget.setX(widget.getX() - area.x - area.width + widgetArea.width);
        }
        if (area.y + area.height > widgetArea.height) {
            widget.setY(widget.getY() - area.y - area.height + widgetArea.height);
        }
        if (area.x < 0) {
            widget.setX(widget.getX() - area.x);
        }
        if (area.y < 0) {
            widget.setY(widget.getY() - area.y);
        }
    }
	
	@Override
    public void setInternalFocusHandler(FocusHandler focusHandler) {
        super.setInternalFocusHandler(focusHandler);
    	Iterator<Widget> iter = mWidgets.iterator();
        while (iter.hasNext()) {
        	if (mInternalFocusHandler == null) {
                iter.next()._setFocusHandler(_getFocusHandler());
            } else {
            	iter.next()._setFocusHandler(mInternalFocusHandler);
            }
        }
    }
	
    protected Widget findWidgetById(String id) {
    	Iterator<Widget> iter = mWidgets.iterator();
        while (iter.hasNext()) {
        	Widget w = iter.next();
        	if (w.getId() != null && w.getId().equals(id)) {
                return w;
            }
            if (w != null && w instanceof BasicContainer) { //FIXME:
            	BasicContainer basicContainer = (BasicContainer)w;
            	Widget widget = basicContainer.findWidgetById(id);
                if (widget != null) {
                    return widget;
                }
            }
        }
        return null;
    }
}
