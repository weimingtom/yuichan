package com.iteye.weimingtom.guichan.listener;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.MouseEvent;

public interface MouseListener {
	public void mouseEntered(MouseEvent mouseEvent);
    public void mouseExited(MouseEvent mouseEvent);
	public void mousePressed(MouseEvent mouseEvent) throws GuichanException;
	public void mouseReleased(MouseEvent mouseEvent) throws GuichanException;
	public void mouseClicked(MouseEvent mouseEvent) throws GuichanException;
	public void mouseWheelMovedUp(MouseEvent mouseEvent) throws GuichanException;
	public void mouseWheelMovedDown(MouseEvent mouseEvent) throws GuichanException;
    public void mouseMoved(MouseEvent mouseEvent);
    public void mouseDragged(MouseEvent mouseEvent) throws GuichanException;
}
