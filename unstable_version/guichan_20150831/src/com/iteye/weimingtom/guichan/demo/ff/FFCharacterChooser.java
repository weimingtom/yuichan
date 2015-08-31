package com.iteye.weimingtom.guichan.demo.ff;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Key;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.KeyListener;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Image;

public class FFCharacterChooser extends Widget implements KeyListener {
	private int mSelected;
	private int mDistance;
	private Image mHand;
	
	public FFCharacterChooser() throws GuichanException {
		setWidth(20);
		setHeight(240);
		mSelected = 0;
		mDistance = 76;
		mHand = Image.load("images/hand.png");
		setFocusable(true);
		addKeyListener(this);
		//setBorderSize(0);
		setFrameSize(0);
	}
	
	@Override
	public void destroy() {
		mHand.destroy();
		mHand = null;
	}
	
	public int getSelected() {
		return mSelected;
	}

	public void setSelected(int selected) {
		mSelected = selected;
	}

	public void setDistance(int distance) {
		mDistance = distance;
	}
	
	@Override
	public void keyPressed(KeyEvent keyEvent) throws GuichanException {
		if (keyEvent.getKey().getValue() == Key.UP) {
			mSelected--;
			if (mSelected < 0) {
				mSelected = 0;
			}
		} else if (keyEvent.getKey().getValue() == Key.DOWN) {
			mSelected++;
			if (mSelected > 2) {
				mSelected = 2;
			}
		} else if (keyEvent.getKey().getValue() == Key.ENTER) {
			//generateAction();
			distributeActionEvent();
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) throws GuichanException {
		
	}

	@Override
	public void draw(Graphics graphics) throws GuichanException {
		if (isFocused()) {
			graphics.drawImage(mHand, 0, mDistance * mSelected);
		}		
	}

}
