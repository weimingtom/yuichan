package com.iteye.weimingtom.guichan.gce.listener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gce.EditorGui;
import com.iteye.weimingtom.guichan.gce.GceBase;

public class GceFileDialogSaveGui implements GceFileDialogCallback {
	@Override
	public void callback(String arg) throws GuichanException {
		saveGui(arg);
	}
	
	private static void saveGui(String filename) {
	    if (filename != null) {
			Document doc = DocumentFactory.getInstance().createDocument();

		    doc.setXMLEncoding("utf-8");
		    
		    Element root = DocumentFactory.getInstance().createElement("GUI");
		    doc.setRootElement(root);
		    
		    GceBase.iterate(EditorGui.top, root/*, 0*/);
		    EditorGui.currentWidget = null;
		    
		    XMLWriter writer = null;
		    OutputFormat format = OutputFormat.createPrettyPrint();
		    format.setEncoding("utf-8");
	    	
		    try {
				writer = new XMLWriter(new OutputStreamWriter(
						new FileOutputStream(filename), 
						format.getEncoding()), 
						format);
			    writer.write(doc);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
				    try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	    }
	}

}
