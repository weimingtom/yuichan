package com.iteye.weimingtom.guichan.demo.fps;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.widget.CheckBox;

public class FPSCheckBox extends CheckBox {

	public FPSCheckBox(String caption) throws GuichanException {
		super(caption);
	}

	@Override
	public void draw(Graphics graphics) throws GuichanException {
//		super.draw(graphics);
		graphics.setFont(getFont());
		graphics.setColor(getForegroundColor());

		graphics.drawText(mCaption, 0, 0);

		int x = getFont().getWidth(mCaption) + getHeight() / 2;

		graphics.pushClipArea(new Rectangle(x, 0, getWidth() - x, getHeight())); 
		drawBox(graphics);
		graphics.popClipArea();
	}
	
}
