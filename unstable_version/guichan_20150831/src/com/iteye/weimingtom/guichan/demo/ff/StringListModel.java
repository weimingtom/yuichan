package com.iteye.weimingtom.guichan.demo.ff;

import java.util.ArrayList;
import java.util.List;

import com.iteye.weimingtom.guichan.platform.ListModel;

public class StringListModel extends ListModel {
	private List<String> mStrings = new ArrayList<String>();
	
	@Override
	public int getNumberOfElements() {
		return mStrings.size();
	}

	@Override
	public String getElementAt(int i) {
		return mStrings.get(i);
	}

	public void add(String str) {
		mStrings.add(str);
	}
	
	public void destroy() {
		
	}
}
