package com.iteye.weimingtom.guichan.gce;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.gce.listener.GceFileDialogCallback;
import com.iteye.weimingtom.guichan.gce.model.GceStringList;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.ListBox;
import com.iteye.weimingtom.guichan.widget.ScrollArea;
import com.iteye.weimingtom.guichan.widget.TextField;
import com.iteye.weimingtom.guichan.widget.Window;

public class GceFileDialog implements ActionListener {
    private Window fdWin;
    private ListBox fileList;
    private ListBox dirList;
    private GceStringList dirs;
    private GceStringList files;
    private ScrollArea fArea;
    private ScrollArea dArea;
    private Button fdQuitButton;
    private Button fdCancelButton;
    private TextField namebox;
    private String mStartPath; 
        
    private GceFileDialogCallback mCallback;
    private void fillDir() throws GuichanException {
    	dirs.addString("..");
    	String[] d = new File(".").list();
    	if (d != null) {
    	    String fwidest = "";
    	    String dwidest = "";
    	    int dw = 0;
    	    int fw = 0;

    	    for (String fname : d) {
    	    	if (new File(fname).isDirectory()) {
    	    		dirs.addString(fname);
    	    		if (fname.length() > dw) {
    	    			dw = fname.length();
    	    			dwidest = fname;
    	    		}
    	    	} else {
    	    		files.addString(fname);
    	    		if (fname.length() > fw) {
    	    			fw = fname.length();
    	    			fwidest = fname;
    	    		}
    	    	}
    	    }
    	    fileList.setSize(fileList.getFont().getWidth(fwidest) + 4,
    	    	files.getNumberOfElements() * fileList.getFont().getHeight()); 
    	    dirList.setSize(dirList.getFont().getWidth(dwidest) + 4, 
    	    	dirs.getNumberOfElements() * dirList.getFont().getHeight()); 
    	}
    	  
    	Collections.sort(files.mList, new Comparator<String>() {
    		@Override
    		public int compare(String o1, String o2) {
    			return sortFileList(o1, o2);
    		}
    	});
    	Collections.sort(dirs.mList, new Comparator<String>() {
    		@Override
  		  	public int compare(String o1, String o2) {
    			return sortFileList(o1, o2);
  		  	}
    	});
    }
    
    
    
    public GceFileDialog(GceFileDialogCallback cbFunction) throws GuichanException {
        mCallback = cbFunction;
        mStartPath = System.getProperty("user.dir");

        fdWin = new Window();
        fdWin.setPosition(EditorGui.top.getWidth() / 2 - 200,
        	EditorGui.top.getHeight() / 2 - 180);
        fdWin.setSize(400,360);
        fdQuitButton = new Button("OK");
        fdWin.add(fdQuitButton, 10, 10);
        fdQuitButton.setActionEventId("ok");
        fdQuitButton.addActionListener(this);
        
        fdCancelButton = new Button("Cancel");
        fdWin.add(fdCancelButton,100,10);
        fdCancelButton.setActionEventId("cancel");
        fdCancelButton.addActionListener(this);
        
        fArea = new ScrollArea();
        fArea.setPosition(210, 48);
        fArea.setSize(175, 252);
        fdWin.add(fArea);

        dArea = new ScrollArea();
        dArea.setPosition(10, 48);
        dArea.setSize(175, 252);
        fdWin.add(dArea);
            
        dirs = new GceStringList();
        files = new GceStringList();
        
        fileList = new ListBox(files);
        fileList.setActionEventId("fileclicked");
        fileList.addActionListener(this);
        
        dirList = new ListBox(dirs);
        dirList.setActionEventId("dirclicked");
        dirList.addActionListener(this);
        
        namebox = new TextField();
        namebox.setPosition(10,310);
        namebox.setSize(160,20);
        fdWin.add(namebox);

        EditorGui.top.add(fdWin);
        fillDir();   
        
        fArea.setContent(fileList);
        dArea.setContent(dirList);
    }
    
	@Override
	public void action(ActionEvent actionEvent) throws GuichanException {
		System.out.println("fd action listener: " + actionEvent.getId());
	    String id = actionEvent.getId();
	    if ("ok".equals(id) || 
	    	"cancel".equals(id)) {
	        if ("ok".equals(id)) {
	        	mCallback.callback(namebox.getText());
	        }
	        //g_chdir(mStartPath);
	        //delete mStartPath; 

	        EditorGui.widgetsToDelete.add(fileList);
	        EditorGui.widgetsToDelete.add(dirList);
	        EditorGui.widgetsToDelete.add(fArea);
	        EditorGui.widgetsToDelete.add(dArea);
	        EditorGui.widgetsToDelete.add(fdQuitButton);
	        EditorGui.widgetsToDelete.add(fdCancelButton);
	        EditorGui.widgetsToDelete.add(namebox);
	        //FIXME:
	        //EditorGui.widgetsToDelete.add((gceBase*)dirs);
	        //EditorGui.widgetsToDelete.add((gceBase*)files);        
	        EditorGui.widgetsToDelete.add(fdWin); 
	        //FIXME:
	        //EditorGui.widgetsToDelete.add((gceBase*)this);  
	    }
	    if ("fileclicked".equals(id)) {
	    	int i = fileList.getSelected();
	    	namebox.setText(files.getElementAt(i));
	    }
	    if ("dirclicked".equals(id)) {
	    	int i = dirList.getSelected();        
	    	//FIXME:
	    	//g_chdir(dirs.getElementAt(i));
	    	dirs.clearList();
	    	files.clearList();
	    	fillDir();
	    }
	}
	
	private static int sortFileList(String a, String b) {
		return a.compareTo(b);
	}

}
