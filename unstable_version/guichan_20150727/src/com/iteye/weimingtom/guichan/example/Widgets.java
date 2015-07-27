package com.iteye.weimingtom.guichan.example;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.font.ImageFont;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ApplicationListener;
import com.iteye.weimingtom.guichan.platform.Image;
import com.iteye.weimingtom.guichan.platform.ListModel;
import com.iteye.weimingtom.guichan.platform.awt.AWT;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.CheckBox;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.DropDown;
import com.iteye.weimingtom.guichan.widget.Icon;
import com.iteye.weimingtom.guichan.widget.Label;
import com.iteye.weimingtom.guichan.widget.ListBox;
import com.iteye.weimingtom.guichan.widget.RadioButton;
import com.iteye.weimingtom.guichan.widget.ScrollArea;
import com.iteye.weimingtom.guichan.widget.Slider;
import com.iteye.weimingtom.guichan.widget.TabbedArea;
import com.iteye.weimingtom.guichan.widget.TextBox;
import com.iteye.weimingtom.guichan.widget.TextField;
import com.iteye.weimingtom.guichan.widget.Window;

public class Widgets implements ApplicationListener {
    private ImageFont font;
    private Container top;
    private Label label;
    private Icon icon;
    private Button button;
    private TextField textField;
    private TextBox textBox;
    private ScrollArea textBoxScrollArea;
    private ListBox listBox;
    private DropDown dropDown;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private Slider slider;
    private Image image;
    private Window window;
    private Image darkbitsImage;
    private Icon darkbitsIcon;
    private TabbedArea tabbedArea;
    private Button tabOneButton;
    private CheckBox tabTwoCheckBox;

    private final class DemoListModel extends ListModel {
        public int getNumberOfElements() {
            return 5;
        }

        public String getElementAt(int i) {
            switch(i) {
            case 0:
            	return "zero";
              
            case 1:
                return "one";
              
            case 2:
                return "two";
              
            case 3:
                return "three";
              
            case 4:
                return "four";
              
            default: // Just to keep warnings away
                return "";
            }
        }
    };

    private DemoListModel demoListModel = new DemoListModel();
    
	public void main(String[] args) throws GuichanException {
		Globals.app = new AWT();
		Globals.app.run(new ApplicationListener() {
			@Override
			public void onInit() throws GuichanException {
				init();
			}
			
			@Override
			public void onTimer() throws GuichanException {
				
			}

			@Override
			public void onHalt() throws GuichanException {
				halt();
		    }
    	});
	}

	public void init() throws GuichanException {
        top = new Container();
        top.setDimension(new Rectangle(0, 0, 640, 480));
        Globals.gui.setTop(top);

        font = new ImageFont("fixedfont.bmp", " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        Widget.setGlobalFont(font);

        label = new Label("Label");

        image = Image.load("gui-chan.bmp");
        icon = new Icon(image);

        button = new Button("Button");

        textField = new TextField("Text field");

        textBox = new TextBox("Multiline\nText box");
        textBoxScrollArea = new ScrollArea(textBox);
        textBoxScrollArea.setWidth(200);
        textBoxScrollArea.setHeight(100);
        textBoxScrollArea.setFrameSize(1);

        listBox = new ListBox(demoListModel);
        listBox.setFrameSize(1);
        dropDown = new DropDown(demoListModel);
        
        checkBox1 = new CheckBox("Checkbox 1");
        checkBox2 = new CheckBox("Checkbox 2");

        radioButton1 = new RadioButton("RadioButton 1", "radiogroup", true);
        radioButton2 = new RadioButton("RadioButton 2", "radiogroup");
        radioButton3 = new RadioButton("RadioButton 3", "radiogroup");

        slider = new Slider(0, 10);
        slider.setSize(100, 10);

        window = new Window("I am a window  Drag me");
        window.setBaseColor(new Color(255, 150, 200, 190));

        darkbitsImage = Image.load("darkbitslogo_by_haiko.bmp");
        darkbitsIcon = new Icon(darkbitsImage);
        window.add(darkbitsIcon);
        window.resizeToContent();

        tabbedArea = new TabbedArea();
        tabbedArea.setSize(200, 100);
        tabOneButton = new Button("A button in tab 1");
        tabbedArea.addTab("Tab 1", tabOneButton);
        tabTwoCheckBox = new CheckBox("A check box in tab 2");
        tabbedArea.addTab("Tab 2", tabTwoCheckBox);
        
        top.add(label, 10, 10);
        top.add(icon, 10, 30);
        top.add(button, 200, 10);
        top.add(textField, 250, 10);
        top.add(textBoxScrollArea, 200, 50);
        top.add(listBox, 200, 200);
        top.add(dropDown, 500, 10);
        top.add(checkBox1, 500, 130);
        top.add(checkBox2, 500, 150);
        top.add(radioButton1, 500, 200);
        top.add(radioButton2, 500, 220);
        top.add(radioButton3, 500, 240);
        top.add(slider, 500, 300);
        top.add(window, 50, 350);
        top.add(tabbedArea, 400, 350);
	}

	public static void halt() {

	}

	@Override
	public void onInit() throws GuichanException {
		init();
	}

	@Override
	public void onTimer() throws GuichanException {
		
	}

	@Override
	public void onHalt() throws GuichanException {
		halt();
	}
}
