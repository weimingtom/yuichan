package com.iteye.weimingtom.guichan.widget;

import java.util.List;
import java.util.Vector;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Key;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.event.Event;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.event.MouseEvent;
import com.iteye.weimingtom.guichan.gui.BasicContainer;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.listener.KeyListener;
import com.iteye.weimingtom.guichan.listener.MouseListener;
import com.iteye.weimingtom.guichan.platform.Graphics;

public class TabbedArea extends BasicContainer implements ActionListener, KeyListener, MouseListener{
    protected Tab mSelectedTab;
    protected Container mTabContainer;
    protected Container mWidgetContainer;
    protected List<Tab> mTabsToDelete = new Vector<Tab>();
    protected List<TabWidgetPair> mTabs = new Vector<TabWidgetPair>();
    protected boolean mOpaque;
    
    private final class TabWidgetPair {
    	public Tab first;
    	public Widget second;
    	
    	public TabWidgetPair(Tab first, Widget second) {
    		this.first = first;
    		this.second = second;
    	}
    }
    
    public TabbedArea() throws GuichanException {
    	mSelectedTab = null;
     	mOpaque = false;
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		
		mTabContainer = new Container();
		mTabContainer.setOpaque(false);
		mWidgetContainer = new Container();
		
		add(mTabContainer);
		add(mWidgetContainer);
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
        if (mouseEvent.isConsumed()) {
            return;
        }

        if (mouseEvent.getButton() == MouseEvent.LEFT) {
            Widget widget = mTabContainer.getWidgetAt(mouseEvent.getX(), mouseEvent.getY());
            
            if (widget != null && widget instanceof Tab) {
                Tab tab = (Tab)widget;
                setSelectedTab(tab);
            }
        }

        // Request focus only if the source of the event
        // is not focusble. If the source of the event
        // is focused we don't want to steal the focus.
        if (!mouseEvent.getSource().isFocusable()) {
            requestFocus();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) throws GuichanException {
        if (keyEvent.isConsumed() || !isFocused()) {
            return;
        }

        if (keyEvent.getKey().getValue() == Key.LEFT) {
            int index = getSelectedTabIndex();
            index--;

            if (index < 0) {
                return;
            } else {
                setSelectedTab(mTabs.get(index).first);
            }

            keyEvent.consume();
        } else if (keyEvent.getKey().getValue() == Key.RIGHT) {
            int index = getSelectedTabIndex();
            index++;

            if (index >= (int)mTabs.size()) {
                return;
            } else {
                setSelectedTab(mTabs.get(index).first);
            }

            keyEvent.consume();
        }
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void action(ActionEvent actionEvent) throws GuichanException {
        if (actionEvent.getSource() == null || !(actionEvent.getSource() instanceof Tab)) {
            throw new GuichanException("Received an action from a widget that's not a tab!");
        }
        Tab tab = (Tab)actionEvent.getSource();
        setSelectedTab(tab);
	}

	@Override
	public void draw(Graphics graphics) throws GuichanException {
		Color faceColor = getBaseColor();
        final int alpha = getBaseColor().a;
        Color highlightColor = faceColor.plus(new Color(0x303030));
        highlightColor.a = alpha;
        Color shadowColor = faceColor.minus(new Color(0x303030));
        shadowColor.a = alpha;

        // Draw a border.
        graphics.setColor(highlightColor);
        graphics.drawLine(0,
                           mTabContainer.getHeight(),
                           0,
                           getHeight() - 2);
        graphics.setColor(shadowColor);
        graphics.drawLine(getWidth() - 1,
                           mTabContainer.getHeight() + 1,
                           getWidth() - 1,
                           getHeight() - 1);
        graphics.drawLine(1,
                           getHeight() - 1,
                           getWidth() - 1,
                           getHeight() - 1);

        if (isOpaque()) {
            graphics.setColor(getBaseColor());
            graphics.fillRectangle(new Rectangle(1, 1,
                                              getWidth() - 2,
                                              getHeight() - 2));
        }

        // Draw a line underneath the tabs.
        graphics.setColor(highlightColor);
        graphics.drawLine(1,
                           mTabContainer.getHeight(),
                           getWidth() - 1,
                           mTabContainer.getHeight());

        // If a tab is selected, remove the line right underneath
        // the selected tab.
        if (mSelectedTab != null) {
            graphics.setColor(getBaseColor());
            graphics.drawLine(mSelectedTab.getX() + 1,
                               mTabContainer.getHeight(),
                               mSelectedTab.getX() + mSelectedTab.getWidth() - 2,
                               mTabContainer.getHeight());

        }

        drawChildren(graphics);
	}

	
    public void addTab(String caption, Widget widget) throws GuichanException {
        Tab tab = new Tab();
        tab.setCaption(caption);
        mTabsToDelete.add(tab);

        addTab(tab, widget);
    }

    public void addTab(Tab tab, Widget widget) throws GuichanException {
        tab.setTabbedArea(this);
        tab.addActionListener(this);

        mTabContainer.add(tab);
        mTabs.add(new TabWidgetPair(tab, widget));

        if (mSelectedTab == null) {
            setSelectedTab(tab);
        }

        adjustTabPositions();
        adjustSize();
    }

    public void removeTabWithIndex(int index) throws GuichanException {
        if (index >= mTabs.size()) {
            throw new GuichanException("No such tab index.");
        }

        removeTab(mTabs.get(index).first);
    }

    public void removeTab(Tab tab) throws GuichanException {
        int tabIndexToBeSelected = - 1;

        if (tab == mSelectedTab) {
            int index = getSelectedTabIndex();

            if (index == (int)mTabs.size() - 1
                && mTabs.size() >= 2) {
                tabIndexToBeSelected = index--;
            } else if (index == (int)mTabs.size() - 1
                     && mTabs.size() == 1) {
                tabIndexToBeSelected = -1;
            } else {
                tabIndexToBeSelected = index;
            }
        }

        
        for (TabWidgetPair iter : mTabs) {
            if (iter.first == tab) {
            	mTabContainer.remove(tab);
                mTabs.remove(iter);
                break;
            }
        }

        mTabsToDelete.remove(tab);

        if (tabIndexToBeSelected == -1) {
            mSelectedTab = null;
            mWidgetContainer.clear();
        } else {
            setSelectedTab(tabIndexToBeSelected);
        }

        adjustSize();
        adjustTabPositions();
    }

    public boolean isTabSelected(int index) throws GuichanException {
        if (index >= mTabs.size()) {
            throw new GuichanException("No such tab index.");
        }

        return mSelectedTab == mTabs.get(index).first;
    }

    public boolean isTabSelected(Tab tab) {
        return mSelectedTab == tab;
    }

    public void setSelectedTab(int index) throws GuichanException {
        if (index >= mTabs.size()) {
            throw new GuichanException("No such tab index.");
        }

        setSelectedTab(mTabs.get(index).first);
    }

    public void setSelectedTab(Tab tab) throws GuichanException {
        int i;
        for (i = 0; i < mTabs.size(); i++) {
            if (mTabs.get(i).first == mSelectedTab) {
                mWidgetContainer.remove(mTabs.get(i).second);
            }
        }
        
        for (i = 0; i < mTabs.size(); i++) {
            if (mTabs.get(i).first == tab) {
                mSelectedTab = tab;
                mWidgetContainer.add(mTabs.get(i).second);
            }
        }
    }

    public int getSelectedTabIndex() {
        int i;
        for (i = 0; i < mTabs.size(); i++) {
            if (mTabs.get(i).first == mSelectedTab) {
                return i;
            }
        }
        
        return -1;
    }

    public Tab getSelectedTab() {
        return mSelectedTab;
    }

    public void setOpaque(boolean opaque) {
        mOpaque = opaque;
    }

    public boolean isOpaque() {
        return mOpaque;
    }
    
    @Override
    public void logic() throws GuichanException {
    	//FIXME:
        for (int i = 0; i < mTabs.size(); i++) {
             mTabs.get(i).second.logic();
        }
    }
    
    public void adjustSize() throws GuichanException {
        int maxTabHeight = 0;

        for (int i = 0; i < mTabs.size(); i++) {
            if (mTabs.get(i).first.getHeight() > maxTabHeight) {
                maxTabHeight = mTabs.get(i).first.getHeight();
            }
        }

        mTabContainer.setSize(getWidth() - 2,
                               maxTabHeight);

        mWidgetContainer.setPosition(1, maxTabHeight + 1);
        mWidgetContainer.setSize(getWidth() - 2,
                                  getHeight() - maxTabHeight - 2);
    }

    public void adjustTabPositions() throws GuichanException {
        int maxTabHeight = 0;
        int i;
        for (i = 0; i < mTabs.size(); i++) {
            if (mTabs.get(i).first.getHeight() > maxTabHeight) {
                maxTabHeight = mTabs.get(i).first.getHeight();
            }
        }

        int x = 0;
        for (i = 0; i < mTabs.size(); i++) {
            Tab tab = mTabs.get(i).first;
            tab.setPosition(x, maxTabHeight - tab.getHeight());
            x += tab.getWidth();
        }
    }

    @Override
    public void setWidth(int width) throws GuichanException {
        super.setWidth(width);
        adjustSize();
    }

    @Override
    public void setHeight(int height) throws GuichanException {
        super.setHeight(height);
        adjustSize();
    }

    @Override
    public void setSize(int width, int height) throws GuichanException {
        super.setSize(width, height);
        adjustSize();
    }

    @Override
    public void setDimension(Rectangle dimension) throws GuichanException {
        super.setDimension(dimension);
        adjustSize();
    }
    
    @Override
    public void death(Event event) throws GuichanException {
        if (event.getSource() != null && event.getSource() instanceof Tab) {
            Tab tab = (Tab)event.getSource();
        	removeTab(tab);
        } else {
            super.death(event);
        }
    }
}
