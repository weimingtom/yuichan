package com.iteye.weimingtom.guichan.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class RadioButton extends Widget implements MouseListener, KeyListener {
	protected boolean mSelected;
	protected String mCaption;
    protected String mGroup;
    protected static Map<String, List<RadioButton>> mGroupMap = new HashMap<String, List<RadioButton>>();
    
    public RadioButton() throws GuichanException {
        setSelected(false);

        setFocusable(true);
        addMouseListener(this);
        addKeyListener(this);
    }

    public RadioButton(String caption,
            String group) throws GuichanException {
    	this(caption, group, false);
    }
    
    public RadioButton(String caption,
        String group,
        boolean selected) throws GuichanException {
        setCaption(caption);
        setGroup(group);
        setSelected(selected);

        setFocusable(true);
        addMouseListener(this);
        addKeyListener(this);

        adjustSize();
    }
    
    protected void drawBox(Graphics graphics) {
        int h;

        if (getHeight() % 2 == 0) {
            h = getHeight() - 4;
        } else {
            h = getHeight() - 3;
        }

        int alpha = getBaseColor().a;
        Color faceColor = getBaseColor();
        faceColor.a = alpha;
        Color highlightColor = faceColor.plus(new Color(0x303030));
        highlightColor.a = alpha;
        Color shadowColor = faceColor.minus(new Color(0x303030));
        shadowColor.a = alpha;

        graphics.setColor(getBackgroundColor());

        int i;
        int hh = (h + 1) / 2;

        for (i = 1; i <= hh; ++i) {
            graphics.drawLine(hh - i + 1,
                               i,
                               hh + i - 1,
                               i);
        }
        
        for (i = 1; i < hh; ++i) {
            graphics.drawLine(hh - i + 1,
                               h - i,
                               hh + i - 1,
                               h - i);
        }

        graphics.setColor(shadowColor);
        graphics.drawLine(hh, 0, 0, hh);
        graphics.drawLine(hh + 1, 1, h - 1, hh - 1);

        graphics.setColor(highlightColor);
        graphics.drawLine(1, hh + 1, hh, h);
        graphics.drawLine(hh + 1, h - 1, h, hh);

        graphics.setColor(getForegroundColor());

        int hhh = hh - 3;
        if (mSelected) {
            for (i = 0; i < hhh; ++i) {
                graphics.drawLine(hh - i, 4 + i, hh + i, 4 + i);
            }
            for (i = 0; i < hhh; ++i) {
                graphics.drawLine(hh - i, h - 4 - i, hh + i, h - 4 -  i);
            }
        }
    }
       
	@Override
	public void keyPressed(KeyEvent keyEvent) throws GuichanException {
        Key key = keyEvent.getKey();

        if (key.getValue() == Key.ENTER ||
            key.getValue() == Key.SPACE) {
            setSelected(true);
            distributeActionEvent();
            keyEvent.consume();
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
	public void mousePressed(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) throws GuichanException {
        if (mouseEvent.getButton() == MouseEvent.LEFT) {
            setSelected(true);
            distributeActionEvent();
        }
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
        graphics.pushClipArea(new Rectangle(1,
        		1,
                getWidth() - 1,
                getHeight() - 1));
        drawBox(graphics);
        graphics.popClipArea();
        
        graphics.setFont(getFont());
        graphics.setColor(getForegroundColor());

        if (isFocused()) {
            int fh;
            
            if (getHeight() % 2 == 0) {
                fh = getHeight() - 4;
            } else {
                fh = getHeight() - 3;
            }

            int hh = (fh + 1) / 2;
        
            graphics.drawLine(0, hh + 1, hh + 1, 0);
            graphics.drawLine(hh + 2, 1, fh + 2, hh + 1);
            graphics.drawLine(fh + 1, hh + 2, hh + 1, fh + 2);
            graphics.drawLine(hh + 1, fh + 2, 1, hh + 2);            
        }
        
        int h = getHeight() + getHeight() / 2;

        graphics.drawText(getCaption(), h - 2, 0);
    }

	public boolean isSelected() {
		return mSelected;
	}

	public void setSelected(boolean selected) {
        if (selected && mGroup != null && !mGroup.equals("")) {
        	List<RadioButton> list = mGroupMap.get(mGroup);
            if (list != null) {
	        	for (RadioButton second : list) {
	                if (second.isSelected()) {
	                	second.setSelected(false);
	                }
	            }
            }
        }
        mSelected = selected;
    }

	public String getCaption() {
        return mCaption;
    }

	public void setCaption(String caption) {
		mCaption = caption;
    }
	
    public void setGroup(String group) {
    	//FIXME:multimap implement for Java
        if (mGroup != null && !mGroup.equals("")) {
        	List<RadioButton> list = mGroupMap.get(mGroup);
            if (list != null) {
            	list.remove(this);
            }
        }

        if (group != null && !group.equals("")) {
        	List<RadioButton> list = mGroupMap.get(group);
        	if (list == null) {
        		list = new ArrayList<RadioButton>();
        		mGroupMap.put(group, list);
        	}
        	list.add(this);
        }
        mGroup = group;
    }

    public String getGroup() {
        return mGroup;
    }

    public void adjustSize() throws GuichanException {
        int height = getFont().getHeight();

        setHeight(height);
        setWidth(getFont().getWidth(getCaption()) + height + height/2);
    }
}
