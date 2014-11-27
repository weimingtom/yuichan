package com.iteye.weimingtom.guichan.basic;

public class Rectangle {
    public int x;
    public int y;
    public int width;
    public int height;
	
	public Rectangle() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
	}
	
    public Rectangle(int x_, int y_, int width_, int height_) {
        x = x_;
        y = y_;
        width = width_;
        height = height_;
    }
    
    public void setAll(int x_, int y_, int width_, int height_) {
	    x = x_;
	    y = y_;
	    width = width_;
	    height = height_;
	}
    
    public boolean isIntersecting(Rectangle rectangle) {
        int x_ = x;
        int y_ = y;
        int width_ = width;
        int height_ = height;
        x_ -= rectangle.x;
        y_ -= rectangle.y;
        if (x_ < 0) {
            width_ += x_;
            x_ = 0;
        } else if (x_ + width_ > rectangle.width) {
            width_ = rectangle.width - x_;
        }
        if (y_ < 0) {
            height_ += y_;
            y_ = 0;
        } else if (y_ + height_ > rectangle.height) {
            height_ = rectangle.height - y_;
        }
        if (width_ <= 0 || height_ <= 0) {
            return false;
        }
        return true;
    }
    
    public boolean isPointInRect(int x_, int y_) {
        return x_ >= x && 
        	   y_ >= y && 
        	   x_ < x + width && 
        	   y_ < y + height;
    }
    
    public String toString() {
        return "Rectangle [x = " + x +
        	", y = " + y +
            ", width = " + width + 
            ", height = " + height +
            "]";
    }
}
