package com.iteye.weimingtom.guichan.gce.listener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;
import com.iteye.weimingtom.guichan.gce.GceLoader;

public class GceFileDialogLoadGui implements GceFileDialogCallback {
	@Override
	public void callback(String arg) throws GuichanException {
		loadGui(arg);
	}
	
	private static void loadGui(String filename) throws GuichanException {
	    GceBase.deleteChildren(EditorGui.top);
	    
		InputStream istr = null;
		Reader reader = null;
		if (filename != null) {
			try {
				istr = new FileInputStream(filename);
				reader = new InputStreamReader(istr, "utf-8");
		        SAXReader saxReader = new SAXReader();
		        Document doc = saxReader.read(reader);
		        
		        Element root = doc.getRootElement();
		        Element topNode = (Element)root.elements().get(0);
		        EditorGui.iterateXml(EditorGui.top, topNode.elementIterator());
		        
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (istr != null) {
					try {
						istr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		EditorGui.currentWidget = null;
	}

}
