package com.iteye.weimingtom.guichan.widget;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.gui.BasicContainer;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.platform.Graphics;

public class Container extends BasicContainer {
	protected boolean mOpaque;
	
	public Container() {
        mOpaque = true;
    }
	
	@Override
	public void draw(Graphics graphics) throws GuichanException {
        if (isOpaque()) {
            graphics.setColor(getBaseColor());
            graphics.fillRectangle(new Rectangle(0, 0, getWidth(), getHeight()));
        }        
        drawChildren(graphics);
	}

    public void setOpaque(boolean opaque) {
        mOpaque = opaque;
    }

    public boolean isOpaque() {
        return mOpaque;
    }

    @Override
    public void add(Widget widget) throws GuichanException {
        super.add(widget);
    }

    public void add(Widget widget, int x, int y) throws GuichanException {
        widget.setPosition(x, y);
        super.add(widget);
    }

    @Override
    public void remove(Widget widget) throws GuichanException {
        super.remove(widget);
    }

    @Override
    public void clear() throws GuichanException {
        super.clear();
    }

    @Override
    public Widget findWidgetById(String id) {
        return super.findWidgetById(id);
    }
}
