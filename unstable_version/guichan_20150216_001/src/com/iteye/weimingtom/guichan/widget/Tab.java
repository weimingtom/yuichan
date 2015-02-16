package com.iteye.weimingtom.guichan.widget;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.MouseEvent;
import com.iteye.weimingtom.guichan.gui.BasicContainer;
import com.iteye.weimingtom.guichan.listener.MouseListener;
import com.iteye.weimingtom.guichan.platform.Graphics;

public class Tab extends BasicContainer implements MouseListener {
    protected Label mLabel;
    protected boolean mHasMouse;
    protected TabbedArea mTabbedArea;
        
    public Tab() throws GuichanException {
	    mHasMouse = false;
	    mTabbedArea = null;
	    
	    mLabel = new Label();
	    mLabel.setPosition(4, 4);
	    add(mLabel);
	    
	    addMouseListener(this);
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {
		mHasMouse = true;
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {
		mHasMouse = false;
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) throws GuichanException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMovedUp(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMovedDown(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics graphics) throws GuichanException {
		Color faceColor = getBaseColor();
	    final int alpha = getBaseColor().a;
	    Color highlightColor = faceColor.plus(new Color(0x303030));
	    highlightColor.a = alpha;
        Color shadowColor = faceColor.minus(new Color(0x303030));
        shadowColor.a = alpha;

        Color borderColor; //FIXME:
        Color baseColor;

        if ((mTabbedArea != null && mTabbedArea.isTabSelected(this))
            || mHasMouse) {
            // Draw a border.
            graphics.setColor(highlightColor);
            graphics.drawLine(0, 0, getWidth() - 1, 0);
            graphics.drawLine(0, 1, 0, getHeight() - 1);
            graphics.setColor(shadowColor);
            graphics.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 1);
            borderColor = highlightColor;
            baseColor = getBaseColor();
        } else {
            // Draw a border.
            graphics.setColor(shadowColor);
            graphics.drawLine(0, 0, getWidth() - 1, 0);
            graphics.drawLine(0, 1, 0, getHeight() - 1);
            graphics.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 1);

            baseColor = getBaseColor().minus(new Color(0x151515));
            baseColor.a = alpha;
        }

        // Push a clip area so the other drawings don't need to worry
        // about the border.
        graphics.pushClipArea(new Rectangle(1, 1, getWidth() - 2, getHeight() - 1));
        Rectangle currentClipArea = graphics.getCurrentClipArea();

        graphics.setColor(baseColor);
        graphics.fillRectangle(new Rectangle(0, 
                                          0, 
                                          currentClipArea.width, 
                                          currentClipArea.height));

        drawChildren(graphics);

        if (mTabbedArea != null
            && mTabbedArea.isFocused()
            && mTabbedArea.isTabSelected(this)) {
            graphics.setColor(new Color(0x000000));
            graphics.drawRectangle(new Rectangle(2,
                                              2,
                                              currentClipArea.width - 4,
                                              currentClipArea.height - 4));
        }

        graphics.popClipArea();
	}

    public void adjustSize() throws GuichanException {
        setSize(mLabel.getWidth() + 8,
                mLabel.getHeight() + 8);
        
        if (mTabbedArea != null) {
            mTabbedArea.adjustTabPositions();
        }
    }

    public void setTabbedArea(TabbedArea tabbedArea) {
        mTabbedArea = tabbedArea;
    }

    public TabbedArea getTabbedArea() {
        return mTabbedArea;
    }

    public void setCaption(String caption) throws GuichanException {
        mLabel.setCaption(caption);
        mLabel.adjustSize();
        adjustSize();
    }

    public String getCaption() {
        return mLabel.getCaption();
    }

}
