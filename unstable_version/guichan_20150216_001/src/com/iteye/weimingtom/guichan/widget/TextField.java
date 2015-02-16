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

public class TextField extends Widget implements MouseListener, KeyListener {
    protected String mText;
    protected int mCaretPosition;
    protected int mXScroll;
    
    public TextField() throws GuichanException {
        mCaretPosition = 0;
        mXScroll = 0;

        setFocusable(true);

        addMouseListener(this);
        addKeyListener(this);
    }

    public TextField(String text) throws GuichanException {
        mCaretPosition = 0;
        mXScroll = 0;

        mText = text;
        adjustSize();

        setFocusable(true);

        addMouseListener(this);
        addKeyListener(this);
    }
    
	@Override
	public void keyPressed(KeyEvent keyEvent) throws GuichanException {
        Key key = keyEvent.getKey();

        if (key.getValue() == Key.LEFT && mCaretPosition > 0) {
            --mCaretPosition;
        } else if (key.getValue() == Key.RIGHT && mCaretPosition < mText.length()) {
            ++mCaretPosition;
        } else if (key.getValue() == Key.DELETE && mCaretPosition < mText.length()) {
            mText = mText.substring(0, mCaretPosition) + mText.substring(mCaretPosition + 1);
        } else if (key.getValue() == Key.BACKSPACE && mCaretPosition > 0) {
            mText = mText.substring(0, mCaretPosition - 1) + mText.substring(mCaretPosition);
            --mCaretPosition;
        } else if (key.getValue() == Key.ENTER) {
            distributeActionEvent();
        } else if (key.getValue() == Key.HOME) {
            mCaretPosition = 0;
        } else if (key.getValue() == Key.END) {
            mCaretPosition = mText.length();
        } else if (key.isCharacter() && key.getValue() != Key.TAB) {
            mText = mText.substring(0, mCaretPosition) + (char)key.getValue() + mText.substring(mCaretPosition);
            ++mCaretPosition;
        } 
        
        if (key.getValue() != Key.TAB) {
            keyEvent.consume();
        }
        
        fixScroll();
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
        if (mouseEvent.getButton() == MouseEvent.LEFT) {
            mCaretPosition = getFont().getStringIndexAt(mText, mouseEvent.getX() + mXScroll);
            fixScroll();
        }
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
		mouseEvent.consume();
	}

	@Override
	public void draw(Graphics graphics) throws GuichanException {
        Color faceColor = getBaseColor();
        Color highlightColor, shadowColor;
        int alpha = getBaseColor().a;
        highlightColor = faceColor.plus(new Color(0x303030));
        highlightColor.a = alpha;
        shadowColor = faceColor.minus(new Color(0x303030));
        shadowColor.a = alpha;

        // Draw a border.
        graphics.setColor(shadowColor);
        graphics.drawLine(0, 0, getWidth() - 1, 0);
        graphics.drawLine(0, 1, 0, getHeight() - 2);
        graphics.fillRectangle(new Rectangle(0, 0, getWidth(), getHeight()));
        graphics.setColor(highlightColor);
        graphics.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 1);
        graphics.drawLine(0, getHeight() - 1, getWidth() - 1, getHeight() - 1);

        // Push a clip area so the other drawings don't need to worry
        // about the border.
        graphics.pushClipArea(new Rectangle(1, 1, getWidth() - 2, getHeight() - 2));

        graphics.setColor(getBackgroundColor());
        graphics.fillRectangle(new Rectangle(0, 0, getWidth(), getHeight()));

//        System.out.println("getBackgroundColor():" + getBackgroundColor());
//        System.out.println("getValidate : " + ((AWTGraphics)graphics).getValidate());
        
        if (isFocused()) {
            graphics.setColor(getSelectionColor());
            graphics.drawRectangle(new Rectangle(0, 0, getWidth() - 2, getHeight() - 2));
            graphics.drawRectangle(new Rectangle(1, 1, getWidth() - 4, getHeight() - 4));
        }

        if (isFocused()) {
            drawCaret(graphics, getFont().getWidth(mText.substring(0, 0 + mCaretPosition)) - mXScroll);
        }

        graphics.setColor(getForegroundColor());
        graphics.setFont(getFont());
        graphics.drawText(mText, 3 - mXScroll, 1);

        graphics.popClipArea();
	}

	protected void drawCaret(Graphics graphics, int x) throws GuichanException {
        // Check the current clip area as a clip area with a different
        // size than the widget might have been pushed (which is the
        // case in the draw method when we push a clip area after we have
        // drawn a border).
        final Rectangle clipArea = graphics.getCurrentClipArea();

        graphics.setColor(getForegroundColor());
        graphics.drawLine(x, clipArea.height - 2, x, 1);
    }
    
    protected void fixScroll() {
        if (isFocused()) {
            int caretX = getFont().getWidth(mText.substring(0, 0 + mCaretPosition));

            if (caretX - mXScroll >= getWidth() - 4) {
                mXScroll = caretX - getWidth() + 4;
            } else if (caretX - mXScroll <= 0) {
                mXScroll = caretX - getWidth() / 2;

                if (mXScroll < 0) {
                    mXScroll = 0;
                }
            }
        }
    }
    
    public void setText(String text)  {
        if(text.length() < mCaretPosition ) {
            mCaretPosition = text.length();
        }

        mText = text;
    }
    
    public void adjustSize() throws GuichanException {
        setWidth(getFont().getWidth(mText) + 7);
        adjustHeight();

        fixScroll();
    }

    public void adjustHeight() throws GuichanException {
        setHeight(getFont().getHeight() + 4);
    }

    public void setCaretPosition(int position) {
        if (position > mText.length()) {
            mCaretPosition = mText.length();
        } else {
            mCaretPosition = position;
        }

        fixScroll();
    }

    public int getCaretPosition() {
        return mCaretPosition;
    }

    public String getText() {
        return mText;
    }

    @Override
    public void fontChanged() {
        fixScroll();
    }
}
