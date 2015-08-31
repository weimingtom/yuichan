package com.iteye.weimingtom.guichan.demo.ff;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Image;
import com.iteye.weimingtom.guichan.widget.ListBox;

public class FFListBox extends ListBox {
	private static Image mHand;
	private static int mInstances;
		
	public FFListBox() throws GuichanException {
		if (mInstances == 0) {
			mHand = Image.load("images/hand.png");
		}

		mInstances++;
		//setBorderSize(0);
		this.setFrameSize(0);
		//setWrappingKeyboardSelection(true);
		this.setWrappingEnabled(true);
	}

	public void destroy() {
		mInstances--;

		if (mInstances == 0) {
			mHand.destroy();
			mHand = null;
		}
	}

	public void draw(Graphics graphics) throws GuichanException {
		//super.draw(graphics);
		if (mListModel == null) {
			return;
		}

		graphics.setColor(getForegroundColor());
		graphics.setFont(getFont());

		int i, fontHeight;
		int y = 0;

		fontHeight = getFont().getHeight();

		for (i = 0; i < mListModel.getNumberOfElements(); ++i) {
			graphics.drawText(mListModel.getElementAt(i), 16, y);

			if (i == mSelected) {
				if (isFocused()) {
					graphics.drawImage(mHand, 0, y);
				} else if (((System.currentTimeMillis() / 100) & 1) != 0) {
					graphics.drawImage(mHand, 0, y);
				}
			}
			
			y += fontHeight;
		}
	}

	public void setSelected(int i) throws GuichanException {
		//skip empty item
		if (i >= 0 &&
			i < getListModel().getNumberOfElements() &&
			"".equals(getListModel().getElementAt(i))) {
			if (i < getSelected()) {
				i--;
			} else {
				i++;
			}
		}
		//ListBox::
		super.setSelected(i);
	}
}
