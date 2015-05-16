package com.iteye.weimingtom.guichan.platform.awt;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.iteye.weimingtom.firetree.MainFrame;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Gui;
import com.iteye.weimingtom.guichan.listener.ApplicationListener;
import com.iteye.weimingtom.guichan.platform.AppLauncher;
import com.iteye.weimingtom.guichan.platform.Image;

public class AWT implements AppLauncher {
	public String window_title = "guichan";
	public int window_width = 640;
	public int window_height = 480;
	public boolean isEscapeExit = true;
	
    private AWTGraphics graphics;
    private AWTInput input;
    private AWTImageLoader imageLoader;
    private MainFrame mainFrame;
    private ApplicationListener appListener;

    public AWT() {
    	
    }
    
    public AWT(int width, int height) {
    	this.window_width = width;
    	this.window_height = height;
    }

    public AWT(int width, int height, boolean isEscapeExit) {
    	this.window_width = width;
    	this.window_height = height;
    	this.isEscapeExit = isEscapeExit;
    }
    
    public void init() {
        mainFrame = new AWTMainFrame();
        
    	imageLoader = new AWTImageLoader();
        Image.setImageLoader(imageLoader);
        graphics = new AWTGraphics();
        input = new AWTInput();

        Globals.gui = new Gui();
        Globals.gui.setGraphics(graphics);
        Globals.gui.setInput(input);
        
    }

    public void halt() {
    	
    }
    
    public void dispose() {
    	if (mainFrame != null) {
    		mainFrame.closeFrame();
    	}
    }
    
    public void run(ApplicationListener appListener_) throws GuichanException {
    	if (appListener_ != null) {
    		init();
    		appListener_.onInit();
    	}
    	mainFrame.start();
    	//NOTE: setTarget must after start()
        graphics.setTarget(mainFrame.getBufGraph());
        appListener = appListener_;
    }
    
    private final class AWTMainFrame extends MainFrame {
		private static final long serialVersionUID = 1L;

		public AWTMainFrame() {
			super(window_title, window_width, window_height, 24);
		}
		
		@Override
		protected void onDraw(Graphics g) {
//			g.setColor(java.awt.Color.BLACK);
//			g.fillRect(0, 0, canvasWidth, canvasHeight);
		}
		
		@Override
		protected void onTick() {
            // Now we let the Gui object perform its logic.
//			System.out.println("draw");
			
            try {
				Globals.gui.logic();
			} catch (GuichanException e) {
				e.printStackTrace();
			}
            try {
				Globals.gui.draw();
			} catch (GuichanException e) {
				e.printStackTrace();
			}
            if (appListener != null) {
	            try {
	            	appListener.onTimer();
		        } catch (GuichanException e) {
					e.printStackTrace();
				}
            }
//            java.awt.Graphics g = this.getBufGraph();
//			g.setColor(java.awt.Color.YELLOW);
//			g.fillRect(0, 0, canvasWidth, canvasHeight);
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("keyPressed:" + e);
			input.pushInput(e, AWTInput.AWT_KEYDOWN);
			if (isEscapeExit && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				dispose();
			}
		}
		
		
//		@Override
//		public void keyReleased(KeyEvent e) {
//			super.keyReleased(e);
//		}
//
//		@Override
//		public void keyTyped(KeyEvent e) {
//			super.keyTyped(e);
//		}

		@Override
		public void mousePressed(MouseEvent e) {
			input.pushInput(e, AWTInput.AWT_MOUSEBUTTONDOWN);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			input.pushInput(e, AWTInput.AWT_MOUSEBUTTONUP);
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			input.pushInput(e, AWTInput.AWT_MOUSEMOTION);
//			System.out.println("move");
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			input.pushInput(e, AWTInput.AWT_MOUSEMOTION);
//			System.out.println("drag");
		}

		@Override
		protected void onExit() {
			super.onExit();
			if (appListener != null) {
				try {
					appListener.onHalt();
				} catch (GuichanException e) {
					e.printStackTrace();
				}
			}
			halt();
		}
    }
}
