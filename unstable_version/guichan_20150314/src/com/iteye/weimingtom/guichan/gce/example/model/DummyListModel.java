package com.iteye.weimingtom.guichan.gce.example.model;

import com.iteye.weimingtom.guichan.platform.ListModel;

public class DummyListModel extends ListModel {
    private static String[] mStrings = {
    	"zero",
    	"one",
    	"two",
    	"three",
    	"four",
	    "five",
	    "six",
	    "seven",
	    "eight",
	    "nine",
    };
    
	@Override
	public int getNumberOfElements() {
		return 10;
	}

	@Override
	public String getElementAt(int i) {
		return mStrings[i];
	}
}
