package com.iteye.weimingtom.guichan.listener;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;

public interface ActionListener {
	public void action(ActionEvent actionEvent) throws GuichanException;
}
