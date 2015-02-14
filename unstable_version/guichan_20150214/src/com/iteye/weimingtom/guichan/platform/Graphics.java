package com.iteye.weimingtom.guichan.platform;

import java.util.Stack;

import com.iteye.weimingtom.guichan.basic.ClipRectangle;
import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;

public abstract class Graphics {
    public enum Alignment {
        LEFT,
        CENTER,
        RIGHT,
    };
    
    public Stack<ClipRectangle> mClipStack = new Stack<ClipRectangle>();
    public Font mFont;
    
	public Graphics() {
		mFont = null;
	}

    public void _beginDraw() { 
    	
    }
    
    public void _endDraw() { 
    	
    }
    
    public boolean pushClipArea(Rectangle area) {
        if (area.width < 0 || area.height < 0) {
            ClipRectangle carea = new ClipRectangle();
            mClipStack.push(carea);
            return true;
        }
        if (mClipStack.empty()) {
            ClipRectangle carea = new ClipRectangle();
            carea.x = area.x;
            carea.y = area.y;
            carea.width = area.width;
            carea.height = area.height;
            carea.xOffset = area.x;
            carea.yOffset = area.y;
            mClipStack.push(carea);
            return true; 
        }
        ClipRectangle top = mClipStack.peek(); // FIXME: top()
        ClipRectangle carea = new ClipRectangle(area);
        carea.xOffset = top.xOffset + carea.x;
        carea.yOffset = top.yOffset + carea.y;
        carea.x += top.xOffset;
        carea.y += top.yOffset;
        if (carea.x < top.x) {
            carea.x = top.x;
        }
        if (carea.y < top.y) {
            carea.y = top.y;            
        }
        if (carea.x + carea.width > top.x + top.width) {
            carea.width = top.x + top.width - carea.x;
            if (carea.width < 0) {
                carea.width = 0;
            }
        }
        if (carea.y + carea.height > top.y + top.height) {
            carea.height = top.y + top.height - carea.y;
            if (carea.height < 0) {
                carea.height = 0;
            }
        }
        boolean result = carea.isIntersecting(top);
        mClipStack.push(carea);
        return result;
    }
    
    public void popClipArea() throws GuichanException {
        if (mClipStack.empty()) {
            throw new GuichanException("Tried to pop clip area from empty stack.");
        }
        mClipStack.pop();
    }
    
    public ClipRectangle getCurrentClipArea() throws GuichanException {
        if (mClipStack.empty())
        {
            throw new GuichanException("The clip area stack is empty.");
        }
        return mClipStack.peek(); // FIXME: top();
    }
    
    public abstract void drawImage(Image image, int srcX, int srcY,
       int dstX, int dstY, int width, int height) throws GuichanException;
    
    public void drawImage(Image image, int dstX, int dstY) throws GuichanException {
    	drawImage(image, 0, 0, dstX, dstY, image.getWidth(), image.getHeight());
    }
    
    public abstract void drawPoint(int x, int y);
    
    public abstract void drawLine(int x1, int y1, int x2, int y2);
    
    public abstract void drawRectangle(Rectangle rectangle);
    
    public abstract void fillRectangle(Rectangle rectangle);
    
    public abstract void setColor(Color color);
    
    public abstract Color getColor();
    
    public void setFont(Font font) {
    	mFont = font;
    }
    
    public void drawText(String text, int x, int y) throws GuichanException {
    	drawText(text, x, y, Alignment.LEFT);
    }
    
    public void drawText(String text, int x, int y,
        Alignment alignment) throws GuichanException {
        if (mFont == null) {
            throw new GuichanException("No font set.");
        }
        
        switch (alignment) {
        case LEFT:
        	mFont.drawString(this, text, x, y);
            break;
              
        case CENTER:
            mFont.drawString(this, text, x - mFont.getWidth(text) / 2, y);
            break;
              
        case RIGHT:
            mFont.drawString(this, text, x - mFont.getWidth(text), y);
            break;
              
        default:
            throw new GuichanException("Unknown alignment.");
        }
    }
}
