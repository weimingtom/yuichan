package com.iteye.weimingtom.guichan.demo.ff;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Image;
import com.iteye.weimingtom.guichan.widget.Container;

public class FFContainer extends Container {
	private static int mInstances;
	private static Image mCornerUL;
	private static Image mCornerUR;
	private static Image mCornerDL;
	private static Image mCornerDR;
	private static Image mHorizontal;
	private static Image mVertical;	
	
	private int mRealWidth;
	private int mRealHeight;
	private int mSlideTarget;
	private int mCurrentSlide;
	private long mTime;
	private boolean mShow;
	
	public FFContainer() throws GuichanException {
		if (mInstances == 0) {
			mCornerUL = Image.load("images/cornerul.png");
			mCornerUR = Image.load("images/cornerur.png");
			mCornerDL = Image.load("images/cornerdl.png");
			mCornerDR = Image.load("images/cornerdr.png");
			mHorizontal = Image.load("images/horizontal.png");
			mVertical = Image.load("images/vertical.png");
		}

		mInstances++;

	 	mRealWidth = 0;
		mRealHeight = 0;
		mTime = -1;
		mShow = true;
		super.setWidth(0);
		super.setHeight(0);
		mSlideTarget = 0;
		mCurrentSlide = 0;
		//setBorderSize(0);
		setFrameSize(0);
	}

	public void destroy() {
		mInstances--;

		if (mInstances == 0) {
			mCornerUL.destroy();
			mCornerUL = null;
			mCornerUR.destroy();
			mCornerUR = null;
			mCornerDL.destroy();
			mCornerDL = null;
			mCornerDR.destroy();
			mCornerDR = null;
			mHorizontal.destroy();
			mHorizontal = null;
			mVertical.destroy(); 
			mVertical = null;
		}		
	}
		
	public void logic() throws GuichanException {
		if (mTime < 0) {
			mTime = System.currentTimeMillis();
		}

		int deltaTime = (int)(System.currentTimeMillis() - mTime);
		mTime = System.currentTimeMillis();

		if (!mShow) {
			super.setWidth(getWidth() - deltaTime);

			if (getWidth() < 0) {
				//Container::
				super.setWidth(0);
			}

			//Container::
			super.setHeight(getHeight() - deltaTime);

			if (getHeight() < 0) {
				//Container::setHeight(0);
				super.setHeight(0);
			}

			if (getHeight() == 0 && getWidth() == 0) {
				//Container::
				super.setVisible(false);
			}
		} else {
			if (getWidth() < mRealWidth) {
				//Container::
				super.setWidth(getWidth() + deltaTime);

				if (getWidth() > mRealWidth) {
					//Container::
					super.setWidth(mRealWidth);
				}
			} else if (getWidth() > mRealWidth) {
				//Container::
				super.setWidth(getWidth() - deltaTime);

				if (getWidth() < mRealWidth) {
					//Container::
					super.setWidth(mRealWidth);
				}
			}

			if (getHeight() < mRealHeight) {
				//Container::
				super.setHeight(getHeight() + deltaTime);

				if (getHeight() > mRealHeight) {
					//Container::
					super.setHeight(mRealHeight);
				}
			} else if (getHeight() > mRealHeight) {
				//Container::
				super.setHeight(getHeight() - deltaTime);

				if (getHeight() < mRealHeight) {
					//Container::
					super.setHeight(mRealHeight);
				}
			}
		}

		if (mCurrentSlide < mSlideTarget) {
			mCurrentSlide += deltaTime;
			if (mCurrentSlide > mSlideTarget) {
				mCurrentSlide = mSlideTarget;
			}
		}

		if (mCurrentSlide > mSlideTarget) {
			mCurrentSlide -= deltaTime;
			if (mCurrentSlide < mSlideTarget) {
				mCurrentSlide = mSlideTarget;
			}
		}

		//Container::
		super.logic();		
	}
	
	public void draw(Graphics graphics) throws GuichanException {
		int i;

		if (isOpaque()) {
			double height = (mRealHeight - 8) / 16.0;
			Color c = new Color(0x7070FF);

			for (i = 0; i < 16; ++i) {
				graphics.setColor(c.multi((float)(1.0 - i / 18.0)));
				graphics.fillRectangle(
					new Rectangle(4, 
						(int)(i * height + 4), 
						getWidth() - 8, 
						(int)((i * height) + height)));
			}
		}

		graphics.pushClipArea(new Rectangle(0, mCurrentSlide, getWidth(), getHeight()));
		drawChildren(graphics);
		graphics.popClipArea();

		for (i = 5; i < getHeight() - 10; i += 5) {
			graphics.drawImage(mVertical, 0, i);
			graphics.drawImage(mVertical, getWidth()-4, i);
		}
		graphics.drawImage(mVertical, 0, 0, 0, i, 4, getHeight()-5-i);
		graphics.drawImage(mVertical, 0, 0, getWidth()-4, i, 4, getHeight()-5-i);

		for (i = 5; i < getWidth() - 10; i += 5) {
			graphics.drawImage(mHorizontal, i, 0);
			graphics.drawImage(mHorizontal, i, getHeight()-4);
		}
		graphics.drawImage(mHorizontal, 0, 0, i, 0, getWidth()-5-i, 4);
		graphics.drawImage(mHorizontal, 0, 0, i, getHeight()-4, getWidth()-5-i, 4);

		graphics.drawImage(mCornerUL, 0, 0);
		graphics.drawImage(mCornerUR, getWidth()-5, 0);
		graphics.drawImage(mCornerDL, 0, getHeight()-5);
		graphics.drawImage(mCornerDR, getWidth()-5, getHeight()-5);		
	}
	
	public void setVisible(boolean visible) throws GuichanException {
		mShow = visible;
		if (visible) {
			//Container::
			super.setVisible(true);
		}
	}
	
	public void setWidth(int width) {
		mRealWidth = width;
	}
	
	public void setHeight(int height) {
		mRealHeight = height;
	}
	
	public void setDimension2(Rectangle dimension) throws GuichanException {
		setPosition(dimension.x, dimension.y);
		setWidth(dimension.width);
		setHeight(dimension.height);		
	}
	
	/*
    public void _setPosition(int x, int y) throws GuichanException {
        Rectangle newDimension = new Rectangle(mDimension);
        newDimension.x = x;
        newDimension.y = y;
        super.setDimension(newDimension);
    }
    */
	
	public void slideContentTo(int y) {
		mSlideTarget = y;
	}
	
	public Rectangle getChildrenArea() {
		return new Rectangle(0, 0, mRealWidth, mRealHeight);
	}
}
