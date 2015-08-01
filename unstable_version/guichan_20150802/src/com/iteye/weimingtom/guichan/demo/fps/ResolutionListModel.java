package com.iteye.weimingtom.guichan.demo.fps;

import com.iteye.weimingtom.guichan.platform.ListModel;

public class ResolutionListModel extends ListModel {

	@Override
	public int getNumberOfElements() {
		return 2;
	}

	@Override
	public String getElementAt(int i) {
		switch (i) {
		case 0:
			return "1024x768";
			
		case 1:
			return "800x600";
			
		default:
			return "";
		}
	}

}
