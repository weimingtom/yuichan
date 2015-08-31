package com.iteye.weimingtom.guichan.font;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.platform.Font;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Image;

public class ImageFont extends Font {
	public static final String FIXMEDFONT_BMP = "fonts/fixedfont.bmp"; 
	
	public static final String FONT_PNG = "fonts/font.png";
	public static final String TINY_PNG = "fonts/tiny.png";
	
	public static final String FIXEDFONT_BIG_BMP = "fonts/fixedfont_big.bmp";
	public static final String FIXEDFONT_BMP = "fonts/fixedfont.bmp";
	public static final String PAPYRUS_32_PNG = "fonts/papyrus_32.png";
	public static final String RPGFONT_THINNER_NOSHADOW_PNG = "fonts/rpgfont_thinner_noshadow.png";
	public static final String RPGFONT_PNG = "fonts/rpgfont.png";
	public static final String RPGFONT2_PNG = "fonts/rpgfont2.png";
	public static final String TECHYFONTBIG_PNG = "fonts/techyfontbig.png";
	public static final String TECHYFONTBIG2_PNG = "fonts/techyfontbig2.png";
	public static final String TECHYFONTBLACK_PNG = "fonts/techyfontblack.png";
	public static final String TECHYFONTWHITE_PNG = "fonts/techyfontwhite.png";
	public static final String THINSANS_BMP = "fonts/thinsans.bmp";
	
	public static final String SPACE_ALPHABET_NUMBER = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static final String SPACE_ALPHABET_NUMBER_MARK = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,!?-+/():;%&`'*#=[]\"";
	
    protected Rectangle[] mGlyph = new Rectangle[256];
    protected int mHeight;
    protected int mGlyphSpacing;
    protected int mRowSpacing;
    protected Image mImage;
    protected String mFilename;

	private ImageFont() {
		super();
		for (int i = 0; i < mGlyph.length; ++i) {
			mGlyph[i] = new Rectangle();
		}
	}
	
    public ImageFont(String filename, String glyphs) throws GuichanException {
    	this();
        mFilename = filename;
        mImage = Image.load(filename, false);
        Color separator = mImage.getPixel(0, 0);
        int i = 0;
        for (i = 0;
             i < mImage.getWidth() && separator.isEqual(mImage.getPixel(i, 0));
             ++i) {
        }
        if (i >= mImage.getWidth()) {
            throw new GuichanException("Corrupt image.");
        }
        int j = 0;
        for (j = 0; j < mImage.getHeight(); ++j) {
            if (separator.isEqual(mImage.getPixel(i, j))) {
                break;
            }
        }
        mHeight = j;
        int x = 0, y = 0;
        char k;
        for (i = 0; i < (int)glyphs.length(); ++i) {
            k = glyphs.charAt(i);
            mGlyph[k] = scanForGlyph(k, x, y, separator);
            x = mGlyph[k].x + mGlyph[k].width;
            y =  mGlyph[k].y;
        }
        mImage.convertToDisplayFormat();
        mRowSpacing = 0;
        mGlyphSpacing = 0;
    }
    
    public ImageFont(Image image, String glyphs) throws GuichanException {
    	this();
        mFilename = "Image*";
        if (image == null) {
            throw new GuichanException("Font image is NULL");
        }
        mImage = image;
        Color separator = mImage.getPixel(0, 0);
        int i = 0;
        for (i = 0;
             i < mImage.getWidth() && separator.isEqual(mImage.getPixel(i, 0));
             ++i) {
        }
        if (i >= mImage.getWidth()) {
            throw new GuichanException("Corrupt image.");
        }
        int j = 0;
        for (j = 0; j < mImage.getHeight(); ++j) {
            if (separator.isEqual(mImage.getPixel(i, j))) {
                break;
            }
        }
        mHeight = j;
        int x = 0, y = 0;
        char k;
        for (i = 0; i < (int)glyphs.length(); ++i) {
            k = glyphs.charAt(i);
            mGlyph[k] = scanForGlyph(k, x, y, separator);
            x = mGlyph[k].x + mGlyph[k].width;
            y =  mGlyph[k].y;
        }
        mImage.convertToDisplayFormat();
        mRowSpacing = 0;
        mGlyphSpacing = 0;
    }
    
    public ImageFont(String filename) throws GuichanException {
    	this(filename, (char)32, (char)126);
    }
    
    public ImageFont(String filename, 
              char glyphsFrom,
              char glyphsTo) throws GuichanException {
    	this();
        mFilename = filename;
        mImage = Image.load(filename, false);
        Color separator = mImage.getPixel(0, 0);
        int i = 0;
        for (i=0; 
             separator.isEqual(mImage.getPixel(i, 0)) && i < mImage.getWidth(); 
             ++i) {
        }
        if (i >= mImage.getWidth()) {
            throw new GuichanException("Corrupt image.");
        }
        int j = 0;
        for (j = 0; j < mImage.getHeight(); ++j) {
            if (separator.isEqual(mImage.getPixel(i, j))) {
                break;
            }
        }
        mHeight = j;
        int x = 0, y = 0;
        for (i = glyphsFrom; i < glyphsTo + 1; i++) {
            mGlyph[i] = scanForGlyph((char)i, x, y, separator);
            x = mGlyph[i].x + mGlyph[i].width;
            y =  mGlyph[i].y;
        }
        mImage.convertToDisplayFormat();
        mRowSpacing = 0;
        mGlyphSpacing = 0;
    }
    
    public void destroy() {
    	this.mImage.destroy();
    	this.mImage = null;
    }
    
    public int getWidth(char glyph) {
        if (mGlyph[glyph].width == 0) {
            return mGlyph[(int)(' ')].width + mGlyphSpacing;
        }
        return mGlyph[glyph].width + mGlyphSpacing;
    }
    
    
	@Override
	public int getWidth(String text) {
        int size = 0;
        if (text != null) {
	        for (int i = 0; i < text.length(); ++i) {
	            size += getWidth(text.charAt(i));
	        }
        }
        return size - mGlyphSpacing;
	}

	@Override
	public int getHeight() {
		return mHeight + mRowSpacing;
	}

	@Override
    public int getStringIndexAt(String text, int x) {
		if (text == null) {
			return 0;
		}
        int size = 0;
        for (int i = 0; i < text.length(); ++i) {
            size += getWidth(text.charAt(i));
            if (size > x) {
                return i;
            }
        }
        return text.length();
    }
	
    public int drawGlyph(Graphics graphics,
            char glyph,
            int x, int y) throws GuichanException {
    	int yoffset = getRowSpacing() / 2;
		if (mGlyph[(int)glyph].width == 0) {
			graphics.drawRectangle(new Rectangle(x,
			      y + 1 + yoffset,
			      mGlyph[(int)(' ')].width - 1,
			      mGlyph[(int)(' ')].height - 2));
			return mGlyph[(int)(' ')].width + mGlyphSpacing;
		}
		graphics.drawImage(mImage,
		           mGlyph[glyph].x,
		           mGlyph[glyph].y,
		           x,
		           y + yoffset,
		           mGlyph[glyph].width,
		           mGlyph[glyph].height);
		return mGlyph[glyph].width + mGlyphSpacing;
	}
	
	@Override
	public void drawString(Graphics graphics, String text, int x, int y) throws GuichanException {
        if (text != null) {
			for (int i = 0; i < text.length(); ++i) {
	            drawGlyph(graphics, text.charAt(i), x, y);
	            x += getWidth(text.charAt(i));
	        }
        }
	}
	
    public void setRowSpacing(int spacing) {
        mRowSpacing = spacing;
    }

    public int getRowSpacing() {
        return mRowSpacing;
    }

    public void setGlyphSpacing(int spacing) {
        mGlyphSpacing = spacing;
    }

    public int getGlyphSpacing() {
        return mGlyphSpacing;
    }
	
    protected Rectangle scanForGlyph(char glyph, 
        int x, int y, Color separator) throws GuichanException {
        Color color = new Color();
        do {
            ++x;
            if (x >= mImage.getWidth()) {
                y += mHeight + 1;
                x = 0;
                if (y >= mImage.getHeight()) {
                    String str = "Image " + 
                    	mFilename +
                    	" with font is corrupt near character '" +
                    	glyph +
                    	"'";
                    throw new GuichanException(str);
                }
            }
            color = mImage.getPixel(x, y);
        } while (color.isEqual(separator));
        int width = 0;
        do {
            ++width;
            if (x + width >= mImage.getWidth()) {
                String str = "Image " +
                	mFilename + 
                	" with font is corrupt near character '" + 
                	glyph + 
                	"'";
                throw new GuichanException(str);
            }
            color = mImage.getPixel(x + width, y);
        } while (color.isNotEqual(separator));
        return new Rectangle(x, y, width, mHeight);
    }
}
