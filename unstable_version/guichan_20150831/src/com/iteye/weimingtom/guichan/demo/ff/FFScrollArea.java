package com.iteye.weimingtom.guichan.demo.ff;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Key;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.listener.KeyListener;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.widget.ScrollArea;

public class FFScrollArea extends ScrollArea implements KeyListener {
	//FIXME: it's only necessary for mAboutScrollArea, by weimingtom
	public boolean _useCustomKeyboard = false;
	
	public FFScrollArea() throws GuichanException {
		setScrollPolicy(ScrollPolicy.SHOW_NEVER, ScrollPolicy.SHOW_ALWAYS);
		addKeyListener(this);
		setFocusable(false);
		//setBorderSize(0);
		this.setFrameSize(0);
	}
	
	@Override
	public void keyPressed(KeyEvent keyEvent) throws GuichanException {
		if (_useCustomKeyboard) {
			if (keyEvent.getKey().getValue() == Key.DOWN) {
				setVerticalScrollAmount(getVerticalScrollAmount() + 16);
			} else if (keyEvent.getKey().getValue() == Key.UP) {
				setVerticalScrollAmount(getVerticalScrollAmount() - 16);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) throws GuichanException {

	}
	
	public void draw(Graphics graphics) throws GuichanException {
		//super.draw(graphics);
		graphics.pushClipArea(getContent().getDimension());
		getContent().draw(graphics);
		graphics.popClipArea();

		if (getVerticalMaxScroll() != 0) {
			int y = ((getHeight() - 32) * getVerticalScrollAmount()) /
				getVerticalMaxScroll();

			graphics.setColor(new Color(0x000000));
			graphics.drawRectangle(new Rectangle(getWidth() - 11, y, 8, 32));
			graphics.drawRectangle(new Rectangle(getWidth() - 10, y + 1, 8, 32));

			graphics.setColor(new Color(0xffffff));

			graphics.fillRectangle(new Rectangle(getWidth() - 10, y + 1, 6, 30));
		}
	}
}
