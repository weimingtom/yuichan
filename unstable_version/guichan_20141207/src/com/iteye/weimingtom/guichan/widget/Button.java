package com.iteye.weimingtom.guichan.widget;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Key;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.Event;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.event.MouseEvent;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.FocusListener;
import com.iteye.weimingtom.guichan.listener.KeyListener;
import com.iteye.weimingtom.guichan.listener.MouseListener;
import com.iteye.weimingtom.guichan.platform.Graphics;

public class Button extends Widget implements MouseListener, KeyListener, FocusListener {
    protected String mCaption;
    protected boolean mHasMouse;
    protected boolean mKeyPressed;
    protected boolean mMousePressed;
    protected Graphics.Alignment mAlignment;
    protected int mSpacing;
    
    public Button() throws GuichanException {
        mHasMouse = false;
        mKeyPressed = false;
        mMousePressed = false;
        mAlignment = Graphics.Alignment.CENTER;
        mSpacing = 4;
        
        setFocusable(true);
        adjustSize();
        setFrameSize(1);

        addMouseListener(this);
        addKeyListener(this);
        addFocusListener(this);
    }
    
	public Button(String caption) throws GuichanException {
		mCaption = caption;
        mHasMouse = false;
        mKeyPressed = false;
        mMousePressed = false;
        mAlignment = Graphics.Alignment.CENTER;
        mSpacing = 4;
        
        setFocusable(true);
        adjustSize();
        setFrameSize(1);

        addMouseListener(this);
        addKeyListener(this);
        addFocusListener(this);
	}
	
	public void setCaption(String caption) {
		mCaption = caption;
	}
	
	public String getCaption() {
		return mCaption;
	}
	
	public void setAlignment(Graphics.Alignment alignment) {
		mAlignment = alignment;
	}
	
	public Graphics.Alignment getAlignment() {
		return mAlignment;
	}
	
	public void setSpacing(int spacing) {
		mSpacing = spacing;
	}
	
    public int getSpacing() {
    	return mSpacing;
    }
    
    public void adjustSize() throws GuichanException {
        setWidth(getFont().getWidth(mCaption) + 2 * mSpacing);
        setHeight(getFont().getHeight() + 2 * mSpacing);	
    }
    
    protected boolean isPressed() {
        if (mMousePressed) {
            return mHasMouse;
        } else {
            return mKeyPressed;
        }
    }
    
	@Override
	public void focusGained(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(Event event) {
        mMousePressed = false;
        mKeyPressed = false;
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
        Key key = keyEvent.getKey();
        if (key.getValue() == Key.ENTER || key.getValue() == Key.SPACE) {
            mKeyPressed = true;
            keyEvent.consume();
        }
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) throws GuichanException {
        Key key = keyEvent.getKey();
        if ((key.getValue() == Key.ENTER || key.getValue() == Key.SPACE) && mKeyPressed) {
            mKeyPressed = false;
            distributeActionEvent();
            keyEvent.consume();
        }
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
	public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.LEFT) {
            mMousePressed = true;
            mouseEvent.consume();
        }
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) throws GuichanException {
        if (mouseEvent.getButton() == MouseEvent.LEFT && mMousePressed && mHasMouse) {
            mMousePressed = false;
            distributeActionEvent();
            mouseEvent.consume();
        } else if (mouseEvent.getButton() == MouseEvent.LEFT) {
            mMousePressed = false;
            mouseEvent.consume();
        }
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
		mouseEvent.consume();
	}

	@Override
	public void draw(Graphics graphics) throws GuichanException {
        Color faceColor = getBaseColor();
        Color highlightColor, shadowColor;
        int alpha = getBaseColor().a;
        if (isPressed()) {
            faceColor = faceColor.minus(new Color(0x303030));
            faceColor.a = alpha;
            highlightColor = faceColor.minus(new Color(0x303030));
            highlightColor.a = alpha;
            shadowColor = faceColor.plus(new Color(0x303030));
            shadowColor.a = alpha;
        } else {
            highlightColor = faceColor.plus(new Color(0x303030));
            highlightColor.a = alpha;
            shadowColor = faceColor.minus(new Color(0x303030));
            shadowColor.a = alpha;
        }
        graphics.setColor(faceColor);
        graphics.fillRectangle(new Rectangle(1, 1, getDimension().width-1, getHeight() - 1));

        graphics.setColor(highlightColor);
        graphics.drawLine(0, 0, getWidth() - 1, 0);
        graphics.drawLine(0, 1, 0, getHeight() - 1);

        graphics.setColor(shadowColor);
        graphics.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 1);
        graphics.drawLine(1, getHeight() - 1, getWidth() - 1, getHeight() - 1);

        graphics.setColor(getForegroundColor());
        
        int textX;
        int textY = getHeight() / 2 - getFont().getHeight() / 2;

        if (getAlignment() == Graphics.Alignment.LEFT) {
        	textX = mSpacing;
        } else if (getAlignment() == Graphics.Alignment.CENTER) {
            textX = getWidth() / 2;
        } else if (getAlignment() == Graphics.Alignment.RIGHT) {
            textX = getWidth() - mSpacing;
        } else {
            throw new GuichanException("Unknown alignment.");
        }
        graphics.setFont(getFont());
        if (isPressed()) {
            graphics.drawText(getCaption(), textX + 1, textY + 1, getAlignment());
        } else {
            graphics.drawText(getCaption(), textX, textY, getAlignment());
            if (isFocused()) {
                graphics.drawRectangle(new Rectangle(2, 2, getWidth() - 4, getHeight() - 4));
            }
        }
	}

}
