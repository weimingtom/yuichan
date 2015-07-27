package com.iteye.weimingtom.guichan.platform;

import com.iteye.weimingtom.guichan.basic.GuichanException;

public abstract class ImageLoader {
	public Image load(String filename) throws GuichanException {
		return load(filename, true);
	}
	
	public abstract Image load(String filename, boolean convertToDisplayFormat) throws GuichanException;
}
