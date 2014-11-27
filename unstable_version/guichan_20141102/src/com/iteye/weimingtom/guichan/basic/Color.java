package com.iteye.weimingtom.guichan.basic;

public class Color {
	public int r;
	public int g;
	public int b;
	public int a;
	
	public Color() {
        r = 0; 
        g = 0; 
        b = 0; 
        a = 255;
	}
	
	public Color(int color) {
	    r = (color >>> 16) & 0xFF;
	    g = (color >>>  8) & 0xFF;
	    b =  color         & 0xFF;
	    a =  255;
	}
	
	public Color(int r, int g, int b) {
		this(r, g, b, 255);
	}
	
	public Color(int ar, int ag, int ab, int aa) {
        r = ar; 
        g = ag; 
        b = ab; 
        a = aa;
	}
	
    public Color plus(Color color) {
        Color result = new Color(r + color.r, 
                     g + color.g, 
                     b + color.b, 
                     255);
        result.r = (result.r > 255 ? 255 : (result.r < 0 ? 0 : result.r));
        result.g = (result.g > 255 ? 255 : (result.g < 0 ? 0 : result.g));
        result.b = (result.b > 255 ? 255 : (result.b < 0 ? 0 : result.b));
        return result;
    }

    public Color minus(Color color) {
        Color result = new Color(r - color.r, 
                     g - color.g, 
                     b - color.b, 
                     255);
        result.r = (result.r > 255 ? 255 : (result.r < 0 ? 0 : result.r));
        result.g = (result.g > 255 ? 255 : (result.g < 0 ? 0 : result.g));
        result.b = (result.b > 255 ? 255 : (result.b < 0 ? 0 : result.b));
        return result;
    }

    public Color multi(float value) {
        Color result = new Color((int)(r * value), 
                     (int)(g * value), 
                     (int)(b * value), 
                     a);
        result.r = (result.r > 255 ? 255 : (result.r < 0 ? 0 : result.r));
        result.g = (result.g > 255 ? 255 : (result.g < 0 ? 0 : result.g));
        result.b = (result.b > 255 ? 255 : (result.b < 0 ? 0 : result.b));
        return result;
    }

    boolean isEqual(Color color) {
        return r == color.r && g == color.g && b == color.b && a == color.a;
    }

    boolean isNotEqual(Color color) {
        return !(r == color.r && g == color.g && b == color.b && a == color.a);
    }
	
	@Override
	public String toString() {
		return "Color [r = "+
				r +
				", g = " +
				g +
				", b = " +
				b +
				", a = " + 
				a + 
				"]";
	}
}
