package com.iteye.weimingtom.guichan.gce;

import java.util.ArrayList;
import java.util.List;

import com.iteye.weimingtom.guichan.gce.plugins.GcePlugin;

public class GcePluginManager {
    public static List<GcePlugin> plugins = new ArrayList<GcePlugin>();
    
	public static void initialisePlugs() {
		for (String file : GcePlugin.PLUG_PATHS) {
		    GcePlugin plug = GcePlugin.newPlugin(file);
		    if (plug == null) {
		    	System.out.println(file + " aborted");
		    } else {
		    	plugins.add(plug);
		        System.out.println(plug.componentName() + " component loaded. ");
		    }
		}
	}
	
	public static void free_all_plugins() {
	    plugins.clear();
	}
}
