package com.iteye.weimingtom.guichan.example;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.font.ImageFont;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.listener.ApplicationListener;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.widget.Button;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.Label;

public class Action implements ApplicationListener {
    private Container top;
    private ImageFont font;
    private Button button1; 
    private Button button2;
    private Label label1;
    private Label label2;

    private int clickCountButton1 = 0; // Holds clicks for button1
    private int clickCountButton2 = 0; // Holds clicks for button2
    
    private class ButtonActionListener implements ActionListener {
    	public void action(ActionEvent actionEvent) throws GuichanException {
            if (actionEvent.getSource() == button1) {
                clickCountButton1++;
                label1.setCaption("Button1 clicks " + clickCountButton1);
                label1.adjustSize();
            } else if (actionEvent.getId().equals("button2")) {
                clickCountButton2++;
                label2.setCaption("Button2 clicks " + clickCountButton2);
                label2.adjustSize();
            }
        }
    };
    
    private ButtonActionListener buttonActionListener;

	public void init() throws GuichanException {
       top = new Container();
       top.setDimension(new Rectangle(0, 0, 640, 480));
       Globals.gui.setTop(top);
       font = new ImageFont(ImageFont.FIXMEDFONT_BMP, ImageFont.SPACE_ALPHABET_NUMBER);
       Widget.setGlobalFont(font);

       button1 = new Button("Button 1");
       button2 = new Button("Button 2");
       button1.setPosition(120, 230);
       button2.setPosition(420, 230);
       top.add(button1);
       top.add(button2);

       label1 = new Label("Button1 clicks 0");
       label2 = new Label("Button2 clicks 0");
       label1.setPosition(100, 200);
       label2.setPosition(400, 200);
       top.add(label1);
       top.add(label2);

       button1.setActionEventId("button1");
       button2.setActionEventId("button2");

       buttonActionListener = new ButtonActionListener();

       button1.addActionListener(buttonActionListener);
       button2.addActionListener(buttonActionListener);
	}
	
	public void halt() {
		
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

	@Override
	public void preDraw(Graphics graphics) throws GuichanException {

	}

	@Override
	public void onKeyPressed(KeyEvent keyEvent) throws GuichanException {
		
	}
}
