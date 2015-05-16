package com.iteye.weimingtom.guichan.gce.model;

import java.util.ArrayList;
import java.util.List;

import com.iteye.weimingtom.guichan.platform.ListModel;

public class GceStringList extends ListModel {
	public List<String> mList = new ArrayList<String>();
	
	@Override
	public int getNumberOfElements() {
		return mList.size();
	}

	@Override
	public String getElementAt(int i) {
		return mList.get(i);
	}

	public void addString(String s) {
		mList.add(s);
	}
	
	public void clearList() {
		mList.clear();
	}
}
