package com.iteye.weimingtom.guichan.platform.awt;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.iteye.weimingtom.firetree.MainFrame;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Gui;
import com.iteye.weimingtom.guichan.platform.Image;

public class AWT {
	public static String window_title = "guichan";
	public static int window_width = 640;
	public static int window_height = 480;
	
    private static AWTGraphics graphics;
    private static AWTInput input;
    private static AWTImageLoader imageLoader;
    private static MainFrame mainFrame;

    public static void init() {
        mainFrame = new AWTMainFrame();
        
    	imageLoader = new AWTImageLoader();
        Image.setImageLoader(imageLoader);
        graphics = new AWTGraphics();
        input = new AWTInput();

        Globals.gui = new Gui();
        Globals.gui.setGraphics(graphics);
        Globals.gui.setInput(input);
        
    }

    public static void halt() {
    	
    }
    
    public static void dispose() {
    	if (mainFrame != null) {
    		mainFrame.closeFrame();
    	}
    }
    
    public static void run() throws GuichanException {
    	mainFrame.start();
    	//NOTE: setTarget must after start()
        graphics.setTarget(mainFrame.getBufGraph());
    }
    
    private static final class AWTMainFrame extends MainFrame {
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
			
//            java.awt.Graphics g = this.getBufGraph();
//			g.setColor(java.awt.Color.YELLOW);
//			g.fillRect(0, 0, canvasWidth, canvasHeight);
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				this.isStopped = true;
			}
			input.pushInput(e, AWTInput.AWT_KEYDOWN);
		}
		
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
    }
}
