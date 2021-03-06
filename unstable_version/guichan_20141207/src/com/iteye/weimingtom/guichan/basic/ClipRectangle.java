package com.iteye.weimingtom.guichan.basic;

public class ClipRectangle extends Rectangle {
	public int xOffset;
	public int yOffset;

	public ClipRectangle() {
		x = y = width = height = xOffset = yOffset = 0;
	}
    
	public ClipRectangle(int x, 
                  int y, 
                  int width, 
                  int height,
                  int xOffset, 
                  int yOffset) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
	}
	
	public Rectangle copy(Rectangle other) {
        x = other.x;
        y = other.y;
        width = other.width;
        height = other.height;
        return this;
	}
}
