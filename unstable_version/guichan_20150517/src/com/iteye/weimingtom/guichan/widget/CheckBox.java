package com.iteye.weimingtom.guichan.widget;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Key;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.event.MouseEvent;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.KeyListener;
import com.iteye.weimingtom.guichan.listener.MouseListener;
import com.iteye.weimingtom.guichan.platform.Graphics;

public class CheckBox extends Widget implements MouseListener, KeyListener {
	protected boolean mSelected;
	protected String mCaption;
	
	public CheckBox() throws GuichanException {
        setSelected(false);
        
        setFocusable(true);
        addMouseListener(this);
        addKeyListener(this);
    }
	
	public CheckBox(String caption) throws GuichanException {
		this(caption, false);
	}
	
	public CheckBox(String caption, boolean selected) throws GuichanException {
        setCaption(caption);
        setSelected(selected);

        setFocusable(true);
        addMouseListener(this);
        addKeyListener(this);

        adjustSize();
    }
	
	@Override
	public void keyPressed(KeyEvent keyEvent) throws GuichanException {
        Key key = keyEvent.getKey();

        if (key.getValue() == Key.ENTER ||
            key.getValue() == Key.SPACE) {
            toggleSelected();
            keyEvent.consume();
        }
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) throws GuichanException {
		if (mouseEvent.getButton() == MouseEvent.LEFT) {
            toggleSelected();
        }
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
		mouseEvent.consume();
	}

	@Override
	public void draw(Graphics graphics) throws GuichanException {
        drawBox(graphics);

        graphics.setFont(getFont());
        graphics.setColor(getForegroundColor());

        final int h = getHeight() + getHeight() / 2;

        graphics.drawText(getCaption(), h - 2, 0);
	}
	
	protected void drawBox(Graphics graphics) {
        final int h = getHeight() - 2;
        final int alpha = getBaseColor().a;
        Color faceColor = getBaseColor();
        faceColor.a = alpha;
        Color highlightColor = faceColor.plus(new Color(0x303030));
        highlightColor.a = alpha;
        Color shadowColor = faceColor.minus(new Color(0x303030));
        shadowColor.a = alpha;

        graphics.setColor(shadowColor);
        graphics.drawLine(1, 1, h, 1);
        graphics.drawLine(1, 1, 1, h);

        graphics.setColor(highlightColor);
        graphics.drawLine(h, 1, h, h);
        graphics.drawLine(1, h, h - 1, h);

        graphics.setColor(getBackgroundColor());
        graphics.fillRectangle(new Rectangle(2, 2, h - 2, h - 2));

        graphics.setColor(getForegroundColor());

        if (isFocused()) {
            graphics.drawRectangle(new Rectangle(0, 0, h + 2, h + 2));
        }

        if (mSelected) {
            graphics.drawLine(3, 5, 3, h - 2);
            graphics.drawLine(4, 5, 4, h - 2);

            graphics.drawLine(5, h - 3, h - 2, 4);
            graphics.drawLine(5, h - 4, h - 4, 5);
        }
    }
	
	public boolean isSelected() {
        return mSelected;
    }

	public void setSelected(boolean selected) {
        mSelected = selected;
    }
	
    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }
    
    public void adjustSize() throws GuichanException {
        int height = getFont().getHeight();

        setHeight(height);
        setWidth(getFont().getWidth(mCaption) + height + height / 2);
    }

    protected void toggleSelected() throws GuichanException {
        mSelected = !mSelected;
        distributeActionEvent();
    }
}
