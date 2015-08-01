package com.iteye.weimingtom.guichan.demo.fps;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.MouseEvent;
import com.iteye.weimingtom.guichan.platform.Font;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.widget.Button;

public class FPSButton extends Button {
	private Font mHighLightFont;
	private boolean mHasMouse;
	private static int mInstances = 0;
	
	public FPSButton(String caption) throws GuichanException {
		super(caption);
		this.mHasMouse = false;
		
//		setBorderSize(0);
		this.setFrameSize(0);
		
		if (mInstances == 0) {		
//			mHoverSound = Mix_LoadWAV("sound/sound5.wav");
//			Mix_VolumeChunk(mHoverSound, 60);
		}
		++mInstances;
	}

	@Override
	public void destroy() throws GuichanException {
		super.destroy();
		--mInstances;

		if (mInstances == 0) {
//			Mix_FreeChunk(mHoverSound);
		}
	}
	
	public void setHighLightFont(Font font) {
		mHighLightFont = font;
	}

	@Override
	public void draw(Graphics graphics) throws GuichanException {
//		super.draw(graphics);
	    if (mHasMouse) {
			graphics.setFont(mHighLightFont);
			graphics.drawText(getCaption(), 0, 0);
		} else {
			graphics.setFont(getFont());
			graphics.drawText(getCaption(), 0, 0);
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent mouseEvent) {
	    super.mouseEntered(mouseEvent);
//		Mix_PlayChannel(-1, mHoverSound, 0);
		mHasMouse = true;
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {
	    super.mouseExited(mouseEvent);
		mHasMouse = false;
	}
}
