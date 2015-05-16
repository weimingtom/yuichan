package com.iteye.weimingtom.guichan.widget;

import java.util.List;
import java.util.Vector;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gui.Widget;

public class AdjustingContainer extends Container {
    private static final int LEFT = 0;
    private static final int CENTER = 1;
    private static final int RIGHT = 2;
	
    protected List<Widget> mContainedWidgets = new Vector<Widget>();
    protected List<Integer> mColumnWidths = new Vector<Integer>();
    protected List<Integer> mColumnAlignment = new Vector<Integer>();
    protected List<Integer> mRowHeights = new Vector<Integer>();
    protected int mWidth;
    protected int mHeight;
    protected int mNumberOfColumns;
    protected int mNumberOfRows;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mVerticalSpacing;
    protected int mHorizontalSpacing;
    
    public AdjustingContainer() {
    	mWidth = 0;
    	mHeight = 0;
    	mNumberOfColumns = 1;
    	mNumberOfRows = 1;
    	mPaddingLeft = 0;
    	mPaddingRight = 0;
    	mPaddingTop = 0;
    	mPaddingBottom = 0;
    	mVerticalSpacing = 0;
    	mHorizontalSpacing = 0;
    	mColumnWidths.add(Integer.valueOf(0));
    	mRowHeights.add(Integer.valueOf(0));
    }

    protected void adjustSize() throws GuichanException {
		mNumberOfRows = mContainedWidgets.size()
				/ mNumberOfColumns + mContainedWidgets.size() % mNumberOfColumns;
		
		mColumnWidths.clear();
		
		int i;
		for (i = 0; i < mNumberOfColumns; i++) {
		    mColumnWidths.add(Integer.valueOf(0));
		}
		
		mRowHeights.clear();
		
		for (i = 0; i < mNumberOfRows; i++) {
		    mRowHeights.add(Integer.valueOf(0));
		}
		
		for (i = 0; i < mNumberOfColumns; i++) {
		    int j;
		    for (j = 0; j < mNumberOfRows && mNumberOfColumns * j + i < mContainedWidgets.size(); j++) {
		        if ((int)mContainedWidgets.get(mNumberOfColumns * j + i).getWidth() > mColumnWidths.get(i)) {
		            mColumnWidths.set(i, mContainedWidgets.get(mNumberOfColumns * j + i).getWidth());
		        }
		        if ((int)mContainedWidgets.get(mNumberOfColumns * j + i).getHeight() > mRowHeights.get(j)) {
		            mRowHeights.set(j, mContainedWidgets.get(mNumberOfColumns * j + i).getHeight());
		        }
		    }
		}
		
		mWidth = mPaddingLeft;
		
		for (i = 0; i < mColumnWidths.size(); i++) {
		    mWidth += mColumnWidths.get(i) + mHorizontalSpacing;
		}
		
		mWidth -= mHorizontalSpacing;
		mWidth += mPaddingRight;
		
		mHeight = mPaddingTop;
		
		for (i = 0; i < mRowHeights.size(); i++) {
		    mHeight += mRowHeights.get(i) + mVerticalSpacing;
		}
		
		mHeight -= mVerticalSpacing;
		mHeight += mPaddingBottom;
		
		setHeight(mHeight);
		setWidth(mWidth);
    }
    
    public void setNumberOfColumns(int numberOfColumns) {
		mNumberOfColumns = numberOfColumns;
		
		if (mColumnAlignment.size() < numberOfColumns) {
		    while (mColumnAlignment.size() < numberOfColumns) {
		        mColumnAlignment.add(Integer.valueOf(LEFT));
		    }
		} else {
		    while (mColumnAlignment.size() > numberOfColumns) {
		        mColumnAlignment.remove(mColumnAlignment.size() - 1);
		    }
		}
	}

    public void setColumnAlignment(int column, int alignment) {
		if (column < mColumnAlignment.size()) {
		    mColumnAlignment.set(column, alignment);
		}
	}

    public void setPadding(int paddingLeft,
                           int paddingRight,
                           int paddingTop,
                           int paddingBottom) {
		mPaddingLeft = paddingLeft;
		mPaddingRight = paddingRight;
		mPaddingTop = paddingTop;
		mPaddingBottom = paddingBottom;
	}

    public void setVerticalSpacing(int verticalSpacing) {
    	mVerticalSpacing = verticalSpacing;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
    	mHorizontalSpacing = horizontalSpacing;
    }

    @Override
    public void logic() throws GuichanException {
    	super.logic();
    	adjustContent();
    }

    @Override
    public void add(Widget widget) {
    	super.add(widget);
    	mContainedWidgets.add(widget);
    }

    @Override
    public void add(Widget widget, int x, int y) {
    	add(widget);
    }

    @Override
    public void clear() {
    	super.clear();
    	mContainedWidgets.clear();
    }

    @Override
    public void remove(Widget widget) throws GuichanException {
    	super.remove(widget);
    	mContainedWidgets.remove(widget);
    }

    public void adjustContent() throws GuichanException {
		adjustSize();
		
		int columnCount = 0;
		int rowCount = 0;
		int y = mPaddingTop;
		
		for (int i = 0; i < mContainedWidgets.size(); i++) {
		    int basex;
		    if (columnCount % mNumberOfColumns != 0) {
		        basex = mPaddingLeft;
		        int j;
		        
		        for (j = 0; j < columnCount; j++) {
		            basex += mColumnWidths.get(j) + mHorizontalSpacing;
		        }
		    } else {
		        basex = mPaddingLeft;
		    }
		
		    switch (mColumnAlignment.get(columnCount)) {
	        case LEFT:
	            mContainedWidgets.get(i).setX(basex);
	            break;
	            
	        case CENTER:
	            mContainedWidgets.get(i).setX(basex + (mColumnWidths.get(columnCount) - mContainedWidgets.get(i).getWidth()) / 2);
	            break;
	        
	        case RIGHT:
	            mContainedWidgets.get(i).setX(basex + mColumnWidths.get(columnCount) - mContainedWidgets.get(i).getWidth());
	            break;
	        
	        default:
	            throw new GuichanException("Unknown alignment.");
		    }
		
		    mContainedWidgets.get(i).setY(y);
		    columnCount++;
		    
		    if (columnCount == mNumberOfColumns) {
		        columnCount = 0;
		        y += mRowHeights.get(rowCount) + mVerticalSpacing;
		        rowCount++;
		    }
		}
	}
}
