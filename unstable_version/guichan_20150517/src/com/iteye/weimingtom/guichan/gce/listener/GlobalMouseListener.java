package com.iteye.weimingtom.guichan.gce.listener;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.MouseEvent;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.plugins.GceEditPlugin;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.MouseListener;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.ScrollArea;
import com.iteye.weimingtom.guichan.widget.Window;

public class GlobalMouseListener implements MouseListener {
	private int mx;
	private int my;
	private boolean isDrag;
	private boolean isResize;
	
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
	    EditorGui.timesClicked++;

	    EditorGui.oldCurrent = EditorGui.currentWidget;
	    EditorGui.currentWidget = mouseEvent.getSource();
	    mx = mouseEvent.getX();
	    my = mouseEvent.getY();
	    int w, h;
	    w = EditorGui.currentWidget.getWidth();
	    h = EditorGui.currentWidget.getHeight();
	    
	    boolean isP = false;
        for (GceEditPlugin plug_ : EditorGui.plugins) {
            if (plug_.currentIsThisType()) {
            	plug_.updateExtendedProperties();
            	if (EditorGui.parentMode) {
            		isP = plug_.canParent();
            	}
	            break;
            }
        }
	    if (EditorGui.parentMode) {
	        if (isP)  {
	        	System.out.println("====apply Parent command");
	            if (EditorGui.currentWidget instanceof ScrollArea) {
	                ((Container)EditorGui.oldCurrent.getParent()).remove(EditorGui.oldCurrent);
	                Widget t = ((ScrollArea)EditorGui.currentWidget).getContent();
	                ((ScrollArea)EditorGui.currentWidget).setContent(EditorGui.oldCurrent);
	                if (t != null) {
	                    EditorGui.top.add(t, 16, 16);
	                }
	            } else {
	                Container c = (Container)EditorGui.currentWidget;
	                ((Container)EditorGui.oldCurrent.getParent()).remove(EditorGui.oldCurrent);
	                c.add(EditorGui.oldCurrent, 16, 16);  
	            }
	            EditorGui.parentMode = false;
	            EditorGui.parent.setCaption("Parent");
	            return; 
	        }
	    }

	    if (EditorGui.deleteMode) {
	        if (EditorGui.currentWidget != EditorGui.top) {
	            GceBase.deleteChildren(EditorGui.currentWidget);
	            
	            EditorGui.deleteMode = false;
	            EditorGui.del.setCaption("DeleteWidget");
	            
	            EditorGui.currentWidget = null;
	            return; 
	        }   
	        
	    }

	    if (EditorGui.currentWidget != EditorGui.top) {
	        if (mouseEvent.getX() < 12) {
	            if (mouseEvent.getY() < 12) {
	                isDrag=true;
	            }
	        }
	        if (mouseEvent.getX() > w-12)
	        {
	            if (mouseEvent.getY() > h-12)
	            {
	                isResize=true;
	            }
	        }
	    }    
	    EditorGui.updateBaseProperties();
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) throws GuichanException {
		isDrag = false;
		isResize = false;
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) throws GuichanException {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMovedUp(MouseEvent mouseEvent)
			throws GuichanException {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMovedDown(MouseEvent mouseEvent)
			throws GuichanException {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) throws GuichanException {
	    Widget wid = mouseEvent.getSource();

	    int x,y,w,h,b,th;
	    w = wid.getWidth();
	    h = wid.getHeight();
	    x = wid.getX();
	    y = wid.getY();
	    Widget p = wid.getParent();
	    b = (int)p.getFrameSize();
	    th = 0;
	    if (p instanceof Window) {
	    	th = ((Window)p).getTitleBarHeight();
	    }
	    if (isDrag) {
	        x = x + (mouseEvent.getX()-mx);
			y = y + (mouseEvent.getY()-my);
			wid.setPosition(x,y);
	        if(p != EditorGui.top) {
	            mx=mouseEvent.getX();
	            my=mouseEvent.getY();
	        }
	        mouseEvent.consume();
	    }
	    
	    if (isResize) {
	        w = w + (mouseEvent.getX() - mx);
			h = h + (mouseEvent.getY() - my);
			wid.setWidth(w);
			wid.setHeight(h);
		    mx = mouseEvent.getX();
		    my = mouseEvent.getY();			    
	        mouseEvent.consume();
	    }
	}

}
