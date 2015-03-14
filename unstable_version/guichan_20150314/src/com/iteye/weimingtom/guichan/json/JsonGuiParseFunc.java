package com.iteye.weimingtom.guichan.json;

import org.json.JSONObject;

import com.iteye.weimingtom.guichan.gui.Widget;

public interface JsonGuiParseFunc {
	void jsonGuiParse(JSONObject val, Widget parent, JsonGui caller);
}
