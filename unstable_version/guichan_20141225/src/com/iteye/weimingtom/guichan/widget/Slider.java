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

public class Slider extends Widget implements MouseListener, KeyListener {
    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    };
    
    protected boolean mDragged;
    protected double mValue;
    protected double mStepLength;
    protected int mMarkerLength;
    protected double mScaleStart;
    protected double mScaleEnd;
    protected Orientation mOrientation;
    
    protected void drawMarker(Graphics graphics) {
        Color faceColor = getBaseColor();
        Color highlightColor, shadowColor;
        int alpha = getBaseColor().a;
        highlightColor = faceColor.plus(new Color(0x303030));
        highlightColor.a = alpha;
        shadowColor = faceColor.minus(new Color(0x303030));
        shadowColor.a = alpha;

        graphics.setColor(faceColor);

        if (getOrientation() == Orientation.HORIZONTAL) {
            int v = getMarkerPosition();
            graphics.fillRectangle(new Rectangle(v + 1, 1, getMarkerLength() - 2, getHeight() - 2));
            graphics.setColor(highlightColor);
            graphics.drawLine(v, 0, v + getMarkerLength() - 1,0);
            graphics.drawLine(v, 0, v, getHeight() - 1);
            graphics.setColor(shadowColor);
            graphics.drawLine(v + getMarkerLength() - 1, 1, v + getMarkerLength() - 1, getHeight() - 1);
            graphics.drawLine(v + 1, getHeight() - 1, v + getMarkerLength() - 1, getHeight() - 1);

            if (isFocused()) {
                graphics.setColor(getForegroundColor());
                graphics.drawRectangle(new Rectangle(v + 2, 2, getMarkerLength() - 4, getHeight() - 4));
            }
        } else {
            int v = (getHeight() - getMarkerLength()) - getMarkerPosition();
            graphics.fillRectangle(new Rectangle(1, v + 1, getWidth() - 2, getMarkerLength() - 2));
            graphics.setColor(highlightColor);
            graphics.drawLine(0, v, 0, v + getMarkerLength() - 1);
            graphics.drawLine(0, v, getWidth() - 1, v);
            graphics.setColor(shadowColor);
            graphics.drawLine(1, v + getMarkerLength() - 1, getWidth() - 1, v + getMarkerLength() - 1);
            graphics.drawLine(getWidth() - 1, v + 1, getWidth() - 1, v + getMarkerLength() - 1);

            if (isFocused()) {
                graphics.setColor(getForegroundColor());
                graphics.drawRectangle(new Rectangle(2, v + 2, getWidth() - 4, getMarkerLength() - 4));
            }
        }
    }
    
    protected double markerPositionToValue(int position) {
        int w;
        if (getOrientation() == Orientation.HORIZONTAL) {
            w = getWidth();
        } else {
            w = getHeight();
        }

        double pos = position / ((double)w - getMarkerLength());
        return (1.0 - pos) * getScaleStart() + pos * getScaleEnd();
    }
    
    protected int valueToMarkerPosition(double value) {
        int v;
        if (getOrientation() == Orientation.HORIZONTAL) {
            v = getWidth();
        } else {
            v = getHeight();
        }

        int w =  (int)((v - getMarkerLength())
                       * (value  - getScaleStart())
                       / (getScaleEnd() - getScaleStart()));

        if (w < 0) {
            return 0;
        }

        if (w > v - getMarkerLength()) {
            return v - getMarkerLength();
        }

        return w;
    }
    
    protected int getMarkerPosition() {
    	return valueToMarkerPosition(getValue());
    }
    
    public Slider() throws GuichanException {
    	this(1.0);
    }
    
    public Slider(double scaleEnd) throws GuichanException {
        mDragged = false;

        mScaleStart = 0;
        mScaleEnd = scaleEnd;

        setFocusable(true);
        setFrameSize(1);
        setOrientation(Orientation.HORIZONTAL);
        setValue(0);
        setStepLength(scaleEnd / 10);
        setMarkerLength(10);

        addMouseListener(this);
        addKeyListener(this);
    }

    public Slider(double scaleStart, double scaleEnd) throws GuichanException {
        mDragged = false;

        mScaleStart = scaleStart;
        mScaleEnd = scaleEnd;

        setFocusable(true);
        setFrameSize(1);
        setOrientation(Orientation.HORIZONTAL);
        setValue(scaleStart);
        setStepLength((scaleEnd  - scaleStart)/ 10);
        setMarkerLength(10);

        addMouseListener(this);
        addKeyListener(this);
    }
    
    
	@Override
	public void keyPressed(KeyEvent keyEvent) throws GuichanException {
        Key key = keyEvent.getKey();

        if (getOrientation() == Orientation.HORIZONTAL) {
            if (key.getValue() == Key.RIGHT) {
                setValue(getValue() + getStepLength());
                distributeActionEvent();
                keyEvent.consume();
            } else if (key.getValue() == Key.LEFT) {
                setValue(getValue() - getStepLength());
                distributeActionEvent();
                keyEvent.consume();
            }            
        } else {
            if (key.getValue() == Key.UP) {
                setValue(getValue() + getStepLength());
                distributeActionEvent();
                keyEvent.consume();
            } else if (key.getValue() == Key.DOWN) {
                setValue(getValue() - getStepLength());
                distributeActionEvent();
                keyEvent.consume();
            }
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
	public void mousePressed(MouseEvent mouseEvent) throws GuichanException {
        if (mouseEvent.getButton() == MouseEvent.LEFT
                && mouseEvent.getX() >= 0
                && mouseEvent.getX() <= getWidth()
                && mouseEvent.getY() >= 0
                && mouseEvent.getY() <= getHeight()) {
            if (getOrientation() == Orientation.HORIZONTAL) {
                setValue(markerPositionToValue(mouseEvent.getX() - getMarkerLength() / 2));
            } else {
                setValue(markerPositionToValue(getHeight() - mouseEvent.getY() - getMarkerLength() / 2));
            }

            distributeActionEvent();
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
	public void mouseWheelMovedUp(MouseEvent mouseEvent) throws GuichanException {
        setValue(getValue() + getStepLength());
        distributeActionEvent();

        mouseEvent.consume();
	}

	@Override
	public void mouseWheelMovedDown(MouseEvent mouseEvent) throws GuichanException {
        setValue(getValue() - getStepLength());
        distributeActionEvent();

        mouseEvent.consume();
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) throws GuichanException {
        if (getOrientation() == Orientation.HORIZONTAL) {
            setValue(markerPositionToValue(mouseEvent.getX() - getMarkerLength() / 2));
        } else {
            setValue(markerPositionToValue(getHeight() - mouseEvent.getY() - getMarkerLength() / 2));
        }

        distributeActionEvent();

        mouseEvent.consume();
	}

	@Override
	public void draw(Graphics graphics) throws GuichanException {
        Color shadowColor = getBaseColor().minus(new Color(0x101010));
        int alpha = getBaseColor().a;
        shadowColor.a = alpha;

        graphics.setColor(shadowColor);
        graphics.fillRectangle(new Rectangle(0, 0, getWidth(), getHeight()));

        drawMarker(graphics);
	}

    public void setScale(double scaleStart, double scaleEnd) {
        mScaleStart = scaleStart;
        mScaleEnd = scaleEnd;
    }

    public double getScaleStart() {
        return mScaleStart;
    }

    public void setScaleStart(double scaleStart) {
        mScaleStart = scaleStart;
    }

    public double getScaleEnd() {
        return mScaleEnd;
    }

    public void setScaleEnd(double scaleEnd) {
        mScaleEnd = scaleEnd;
    }
    
    public void setValue(double value) {
        if (value > getScaleEnd()) {
            mValue = getScaleEnd();
            return;
        }

        if (value < getScaleStart()) {
            mValue = getScaleStart();
            return;
        }

        mValue = value;
    }

    public double getValue() {
        return mValue;
    }

    public int getMarkerLength() {
        return mMarkerLength;
    }

    public void setMarkerLength(int length) {
        mMarkerLength = length;
    }
    
    public void setOrientation(Orientation orientation) {
        mOrientation = orientation;
    }

    public Orientation getOrientation() {
        return mOrientation;
    }
    
    public void setStepLength(double length) {
        mStepLength = length;
    }

    public double getStepLength() {
        return mStepLength;
    }
}
