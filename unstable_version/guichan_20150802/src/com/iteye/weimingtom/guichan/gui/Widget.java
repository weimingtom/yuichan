package com.iteye.weimingtom.guichan.gui;

import java.util.ArrayList;
import java.util.List;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.event.Event;
import com.iteye.weimingtom.guichan.font.DefaultFont;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.listener.DeathListener;
import com.iteye.weimingtom.guichan.listener.FocusListener;
import com.iteye.weimingtom.guichan.listener.KeyListener;
import com.iteye.weimingtom.guichan.listener.MouseListener;
import com.iteye.weimingtom.guichan.listener.WidgetListener;
import com.iteye.weimingtom.guichan.platform.Font;
import com.iteye.weimingtom.guichan.platform.Graphics;

/**
 * @see D:\android_rtmp\ffmpeg\msys\home\Administrator\guichan\guichan-0.8.2
 * @author Administrator
 *
 */
public abstract class Widget {
    protected List<MouseListener> mMouseListeners = new ArrayList<MouseListener>();
    protected List<KeyListener> mKeyListeners = new ArrayList<KeyListener>();
    protected List<ActionListener> mActionListeners = new ArrayList<ActionListener>();
    protected List<DeathListener> mDeathListeners = new ArrayList<DeathListener>();
    protected List<FocusListener> mFocusListeners = new ArrayList<FocusListener>();
    protected List<WidgetListener> mWidgetListeners = new ArrayList<WidgetListener>();
	
    protected Color mForegroundColor;
    protected Color mBackgroundColor;
    protected Color mBaseColor;
    protected Color mSelectionColor;
    protected FocusHandler mFocusHandler;
    protected FocusHandler mInternalFocusHandler;
    protected Widget mParent;
    protected Rectangle mDimension = new Rectangle();
    protected int mFrameSize;
    protected String mActionEventId;
    protected boolean mFocusable;
    protected boolean mVisible;
    protected boolean mTabIn;
    protected boolean mTabOut;
    protected boolean mEnabled;
    protected String mId;
    protected Font mCurrentFont;
    
    protected static DefaultFont mDefaultFont;
    protected static Font mGlobalFont;
    protected static List<Widget> mWidgets = new ArrayList<Widget>();
    
    /**
     * NOTE:see BasicContainer::drawChildren, not include drawFrame
     * @param graphics
     * @throws GuichanException
     */
    public abstract void draw(Graphics graphics) throws GuichanException;
    
    public void logic() throws GuichanException { 
    	
    }
    
    public void fontChanged() throws GuichanException { 
    	
    }
    
    public void moveToTop(Widget widget) throws GuichanException  { 
    	
    }
    
    public void moveToBottom(Widget widget) throws GuichanException { 
    	
    }
    
    public void focusNext() throws GuichanException { 
    	
    }
    
    public void focusPrevious() throws GuichanException { 
    	
    }
    
    public void showWidgetPart(Widget widget, Rectangle area) throws GuichanException { 
    	
    }
    
    public Widget() {
    	mForegroundColor = new Color(0x000000);
    	mBackgroundColor = new Color(0xffffff);
        mBaseColor = new Color(0x808090);
        mSelectionColor = new Color(0xc3d9ff);
        mFocusHandler = null;
        mInternalFocusHandler = null;
        mParent = null;
        mFrameSize = 0;
        mFocusable = false;
        mVisible = true;
        mTabIn = true;
        mTabOut = true;
        mEnabled = true;
        mCurrentFont = null;
        mWidgets.add(this);
	}
    
    //FIXME: destroy
    public void destroy() throws GuichanException {
        for (DeathListener listener : mDeathListeners) {
            Event event = new Event(this);
            listener.death(event);
        }
        _setFocusHandler(null);
        mWidgets.remove(this); //FIXME:
    }

    public void drawFrame(Graphics graphics) {
        Color faceColor = getBaseColor();
        Color highlightColor, shadowColor;
        int alpha = getBaseColor().a;
        int width = getWidth() + getFrameSize() * 2 - 1;
        int height = getHeight() + getFrameSize() * 2 - 1;
        highlightColor = faceColor.plus(new Color(0x303030));
        highlightColor.a = alpha;
        shadowColor = faceColor.minus(new Color(0x303030));
        shadowColor.a = alpha;
        for (int i = 0; i < getFrameSize(); ++i) {
            graphics.setColor(shadowColor);
            graphics.drawLine(i,i, width - i, i);
            graphics.drawLine(i,i + 1, i, height - i - 1);
            graphics.setColor(highlightColor);
            graphics.drawLine(width - i,i + 1, width - i, height - i);
            graphics.drawLine(i,height - i, width - i - 1, height - i);
        }
    }
    
    public void _setParent(Widget parent) {
        mParent = parent;
    }

    public Widget getParent() {
        return mParent;
    }
    
    public void setWidth(int width) throws GuichanException {
        Rectangle newDimension = mDimension;
        newDimension.width = width;
        setDimension(newDimension);
    }

    public int getWidth() {
        return mDimension.width;
    }
    
    public void setHeight(int height) throws GuichanException {
        Rectangle newDimension = mDimension;
        newDimension.height = height;
        setDimension(newDimension);
    }

    public int getHeight() {
        return mDimension.height;
    }
    
    public void setX(int x) throws GuichanException {
        Rectangle newDimension = mDimension;
        newDimension.x = x;
        setDimension(newDimension);
    }

    public int getX() {
        return mDimension.x;
    }

    public void setY(int y) throws GuichanException {
        Rectangle newDimension = mDimension;
        newDimension.y = y;
        setDimension(newDimension);
    }

    public int getY() {
        return mDimension.y;
    }
    
    public void setPosition(int x, int y) throws GuichanException {
        Rectangle newDimension = mDimension;
        newDimension.x = x;
        newDimension.y = y;
        setDimension(newDimension);
    }

    public void setDimension(Rectangle dimension) throws GuichanException { 
        Rectangle oldDimension = mDimension; //FIXME:
        mDimension = dimension;
        if (mDimension.width != oldDimension.width || 
        	mDimension.height != oldDimension.height) {
            distributeResizedEvent();
        }
        if (mDimension.x != oldDimension.x || 
        	mDimension.y != oldDimension.y) {
            distributeMovedEvent();
        }
    }
    
    /**
     * FIXME: cannot be written
     * @return
     */
    public Rectangle getDimension() {
        return mDimension;
    }
    
    public void setFrameSize(int frameSize) {
        mFrameSize = frameSize;
    }

    public int getFrameSize() {
        return mFrameSize;
    }
    
    public String getActionEventId() {
        return mActionEventId;
    }

    public void setActionEventId(String actionEventId) {
        mActionEventId = actionEventId;
    }
    
    public boolean isFocused() {
        if (mFocusHandler == null) {
            return false;
        }
        return mFocusHandler.isFocused(this);
    }

    public void setFocusable(boolean focusable) throws GuichanException {
        if (!focusable && isFocused()) {
            mFocusHandler.focusNone();
        }
        mFocusable = focusable;
    }

    public boolean isFocusable() {
        return mFocusable && isVisible() && isEnabled();
    }

    public void requestFocus() throws GuichanException {
        if (mFocusHandler == null) {
            throw new GuichanException("No focushandler set (did you add the widget to the gui?).");
        }
        if (isFocusable()) {
            mFocusHandler.requestFocus(this);
        }
    }
    
    public void requestMoveToTop() throws GuichanException {
        if (mParent != null) {
            mParent.moveToTop(this);
        }
    }

    public void requestMoveToBottom() throws GuichanException {
        if (mParent != null) {
            mParent.moveToBottom(this);
        }
    }
    
    public void setVisible(boolean visible) throws GuichanException {
        if (!visible && isFocused()) {
            mFocusHandler.focusNone();
        }
        if (visible) {
            distributeShownEvent();
        } else if(!visible) {
            distributeHiddenEvent();
        }
        mVisible = visible;
    }

    public boolean isVisible() {
        if (getParent() == null) {
            return mVisible;
        } else {
            return mVisible && getParent().isVisible();
        }
    }
    
    public void setBaseColor(Color color) {
        mBaseColor = color;
    }

    public Color getBaseColor() {
        return mBaseColor;
    }

    public void setForegroundColor(Color color) {
        mForegroundColor = color;
    }

    public Color getForegroundColor() {
        return mForegroundColor;
    }

    public void setBackgroundColor(Color color) {
        mBackgroundColor = color;
    }

    public Color getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setSelectionColor(Color color){
        mSelectionColor = color;
    }

    public Color getSelectionColor() {
        return mSelectionColor;
    } 
    
    public void _setFocusHandler(FocusHandler focusHandler) throws GuichanException {
        if (mFocusHandler != null) {
            releaseModalFocus();
            mFocusHandler.remove(this);
        }
        if (focusHandler != null) {
            focusHandler.add(this);
        }
        mFocusHandler = focusHandler;
    }

    public FocusHandler _getFocusHandler() {
        return mFocusHandler;
    }
    
    public void addActionListener(ActionListener actionListener) {
        mActionListeners.add(actionListener);
    }

    public void removeActionListener(ActionListener actionListener) {
        mActionListeners.remove(actionListener); //FIXME:
    }

    public void addDeathListener(DeathListener deathListener) {
        mDeathListeners.add(deathListener);
    }

    public void removeDeathListener(DeathListener deathListener) {
        mDeathListeners.remove(deathListener); //FIXME:
    }

    public void addKeyListener(KeyListener keyListener) {
        mKeyListeners.add(keyListener);
    }

    public void removeKeyListener(KeyListener keyListener) {
        mKeyListeners.remove(keyListener); //FIXME:
    }

    public void addFocusListener(FocusListener focusListener) {
        mFocusListeners.add(focusListener);
    }
    
    public void removeFocusListener(FocusListener focusListener) {
        mFocusListeners.remove(focusListener); //FIXME:
    }

    public void addMouseListener(MouseListener mouseListener) {
        mMouseListeners.add(mouseListener);
    }

    public void removeMouseListener(MouseListener mouseListener) {
        mMouseListeners.remove(mouseListener); //FIXME:
    }

    public void addWidgetListener(WidgetListener widgetListener) {
        mWidgetListeners.add(widgetListener);
    }

    public void removeWidgetListener(WidgetListener widgetListener) {
        mWidgetListeners.remove(widgetListener); //FIXME:
    }
    
    public void getAbsolutePosition(int[] x, int[] y) {
        if (getParent() == null) {
            x[0] = mDimension.x;
            y[0] = mDimension.y;
            return;
        }
        int[] parentX = new int[1];
        int[] parentY = new int[1];
        getParent().getAbsolutePosition(parentX, parentY);
        x[0] = parentX[0] + mDimension.x + getParent().getChildrenArea().x;
        y[0] = parentY[0] + mDimension.y + getParent().getChildrenArea().y;
    }

    public Font getFont() {
        if (mCurrentFont == null) {
            if (mGlobalFont == null) {
                return mDefaultFont;
            }
            return mGlobalFont;
        }
        return mCurrentFont;
    }

    public static void setGlobalFont(Font font) throws GuichanException {
        mGlobalFont = font;
        for (Widget widget : mWidgets) {
            if (widget.mCurrentFont == null) {
            	widget.fontChanged();
            }
        }
    }

    public void setFont(Font font) throws GuichanException {
        mCurrentFont = font;
        fontChanged();
    }
    
    public static boolean widgetExists(Widget widget) {
        boolean result = false;
        for (Widget w : mWidgets) {
            if (w == widget) { //FIXME:
                return true;
            }
        }
        return result;
    }

    public boolean isTabInEnabled() {
        return mTabIn;
    }

    public void setTabInEnabled(boolean enabled) {
        mTabIn = enabled;
    }

    public boolean isTabOutEnabled() {
        return mTabOut;
    }

    public void setTabOutEnabled(boolean enabled) {
        mTabOut = enabled;
    }
    
    public void setSize(int width, int height) throws GuichanException {
        Rectangle newDimension = mDimension; //FIXME:
        newDimension.width = width;
        newDimension.height = height;
        setDimension(newDimension);
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    public boolean isEnabled() {
        return mEnabled && isVisible();
    }
    
    public void requestModalFocus() throws GuichanException {
        if (mFocusHandler == null) {
            throw new GuichanException("No focushandler set (did you add the widget to the gui?).");
        }
        mFocusHandler.requestModalFocus(this);
    }

    public void requestModalMouseInputFocus() throws GuichanException {
        if (mFocusHandler == null) {
            throw new GuichanException("No focushandler set (did you add the widget to the gui?).");
        }
        mFocusHandler.requestModalMouseInputFocus(this);
    }

    public void releaseModalFocus() {
        if (mFocusHandler == null) {
            return;
        }
        mFocusHandler.releaseModalFocus(this);
    }

    public void releaseModalMouseInputFocus() {
        if (mFocusHandler == null) {
            return;
        }
        mFocusHandler.releaseModalMouseInputFocus(this);
    }
    
    public boolean isModalFocused() throws GuichanException {
        if (mFocusHandler == null) {
        	//FIXME:
            //throw new GuichanException("No focushandler set (did you add the widget to the gui?).");
        	return false;
        }
        if (getParent() != null) {
            return (mFocusHandler.getModalFocused() == this) || 
            	getParent().isModalFocused();
        }
        return mFocusHandler.getModalFocused() == this;
    }

    public boolean isModalMouseInputFocused() throws GuichanException {
        if (mFocusHandler == null) {
            //FIXME:
        	//throw new GuichanException("No focushandler set (did you add the widget to the gui?).");
        	return false;
        }
        if (getParent() != null) {
            return (mFocusHandler.getModalMouseInputFocused() == this) || 
            		getParent().isModalMouseInputFocused();
        }
        return mFocusHandler.getModalMouseInputFocused() == this;
    }
    
    public Widget getWidgetAt(int x, int y) {
        return null;
    }

    public List<MouseListener> _getMouseListeners() {
        return mMouseListeners;
    }

    public List<KeyListener> _getKeyListeners() {
        return mKeyListeners;
    }

    public List<FocusListener> _getFocusListeners() {
        return mFocusListeners;
    }
    
    public Rectangle getChildrenArea() {
        return new Rectangle(0, 0, 0, 0);
    }

    public FocusHandler _getInternalFocusHandler() {
        return mInternalFocusHandler;
    }

    public void setInternalFocusHandler(FocusHandler focusHandler) throws GuichanException {
        mInternalFocusHandler = focusHandler;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }
    
    public void showPart(Rectangle rectangle) throws GuichanException {
        if (mParent != null) {
            mParent.showWidgetPart(this, rectangle);
        }                
    }
        
    protected void distributeResizedEvent() {
        for (WidgetListener widgetListener : mWidgetListeners) {
            Event event = new Event(this);
            widgetListener.widgetResized(event);
        }
    }

    protected void distributeMovedEvent() {
        for (WidgetListener widgetListener : mWidgetListeners) {
            Event event = new Event(this);
            widgetListener.widgetMoved(event);
        }
    }

    protected void distributeHiddenEvent() {
        for (WidgetListener widgetListener : mWidgetListeners) {
            Event event = new Event(this);
            widgetListener.widgetHidden(event);
        }
    }

    protected void distributeActionEvent() throws GuichanException {
        for (ActionListener actionListener : mActionListeners) {
            ActionEvent actionEvent = new ActionEvent(this, mActionEventId);
            actionListener.action(actionEvent);
        }
    }

    protected void distributeShownEvent() {
        for (WidgetListener widgetListener : mWidgetListeners) {
            Event event = new Event(this);
            widgetListener.widgetShown(event);
        }
    }
    
//    //FIXME: for test
//    public void drawFramePrint(Graphics graphics) {
//    	int width = getWidth() + getFrameSize() * 2 - 1;
//    	System.out.println("line: " + 
//    		(graphics.mClipStack.peek().x) + ", " + graphics.mClipStack.peek().y + ", " +  
//    		(graphics.mClipStack.peek().x + width) + ", " + graphics.mClipStack.peek().y);
//    }
}
