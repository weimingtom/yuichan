package com.iteye.weimingtom.guichan.platform.awt;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.iteye.weimingtom.firetree.MainFrame;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Gui;
import com.iteye.weimingtom.guichan.platform.Image;

public class AWT {
	private static final long serialVersionUID = 1L;

	private static boolean running = true;

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
    
    public static void run() throws GuichanException {
    	mainFrame.start();
    	//NOTE: setTarget must after start()
        graphics.setTarget(mainFrame.getBufGraph());
        
    	// The main loop
//        while (running) {
            // Check user input
//            SDL_Event event;
//            while(SDL_PollEvent(&event))
//            {
//                if (event.type == SDL_KEYDOWN)
//                {
//                    if (event.key.keysym.sym == SDLK_ESCAPE)
//                    {
//                        running = false;
//                    }
//                    if (event.key.keysym.sym == SDLK_f)
//                    {
//                        if (event.key.keysym.mod & KMOD_CTRL)
//                        {
//                            // Works with X11 only
//                            SDL_WM_ToggleFullScreen(screen);
//                        }
//                    }
//                }
//                else if(event.type == SDL_QUIT)
//                {
//                    running = false;
//                }
//                
//                // After we have manually checked user input with SDL for
//                // any attempt by the user to halt the application we feed
//                // the input to Guichan by pushing the input to the Input
//                // object.
//                input.pushInput(event);
//            }

//        }
    }
    
    private static final class AWTMainFrame extends MainFrame {
		private static final long serialVersionUID = 1L;

		public AWTMainFrame() {
			super("guichan", 800, 600, 24);
		}
		
		@Override
		protected void onDraw(Graphics g) {
//			g.setColor(java.awt.Color.YELLOW);
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
			input.pushInput(e);
		}
    }
}
