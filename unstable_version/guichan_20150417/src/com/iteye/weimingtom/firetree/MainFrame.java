package com.iteye.weimingtom.firetree;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends Panel implements Runnable, KeyListener,
	MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	
	//Fixing drawing issue, see below
	private static final boolean USE_LOCK = false;
	//use lock, but reduce CPU usage
	private static final boolean USE_LOCK_NOTIFY = true;
	
	private static final int NOTIFY_SLEEP = 5;
	private static final int NORMAL_SLEEP = 1;
	
	protected Frame frame;
	protected Graphics bufGraph;
	protected Image bufImage;
	protected Thread drawThread;
	protected long prevTime;
	
	protected String title;
	protected int canvasWidth, canvasHeight, tickPerSecond;
	protected boolean isStopped = false;
	protected long tick = 0;
	protected boolean enableTick = true;
	//
	//  Prevent drawing something to off-screen image and 
	//drawing off-screen image to screen at the same time.
	//
	//  It's necessary when drawing too many things and 
	// current window is before the browser which playing 
	// video in flash player under Windows 7.
	//
	//  Another method is using Thread.sleep(10) in run() 
	// function.
	//
	//see also:
	//  https://github.com/droolsjbpm/drools/
	//  blob/master/drools-examples/
	//  src/main/java/org/drools/games/GameFrame.java
	//
	protected final Object lockRedraw = new Object();

	public MainFrame(String title, int canvasWidth, int canvasHeight, int tickPerSecond) {
		this.title = title;
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.tickPerSecond = tickPerSecond;
		setPreferredSize(new Dimension(this.canvasWidth, this.canvasHeight));
		addMouseListener(this);
		//
		//The tab key is used to move focus between components and so it is being consumed.
		//see http://www.coderanch.com/t/624163/GUI/java/doesn-KeyListener-Notice-Tab-Key
		//
		setFocusTraversalKeysEnabled(false);
		addKeyListener(this);
		addMouseMotionListener(this);
		frame = new Frame();
		frame.add(this);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
//				System.out.println("windowClosing");
				isStopped = true;
				drawThread.interrupt();
//				System.out.println("windowClosing - 2");
				if (drawThread != null) {
					try {
						drawThread.join();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				onExit();
				System.exit(0);	
			}
		});
		frame.setTitle(title);
	}

	public void start() {
		//NOTE:setResizable(false) must before pack()
		//see http://www.javaworld.com.tw/jute/post/view?bid=5&id=235878&sty=3&tpg=4&age=0
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		this.requestFocus(); //listen for keyboard event
		bufImage = this.createImage(canvasWidth, canvasHeight);
		//FIXME:
		//bufImage = this.createVolatileImage(canvasWidth, canvasHeight);
		bufGraph = bufImage.getGraphics();
		bufGraph.clearRect(0, 0, canvasWidth, canvasHeight);
		drawThread = new Thread(this);
		drawThread.setDaemon(true);
		drawThread.start();
	}
	
	@Override
	public void run() {
		onInit();
		while (true) {
			if (isStopped) {
				break;
			}
			long curTime = System.currentTimeMillis();
			int totalTime = 1000 - NORMAL_SLEEP;
			if (USE_LOCK_NOTIFY) {
				totalTime = 1000 - NOTIFY_SLEEP;
			}
			if (curTime - (totalTime / tickPerSecond) > this.prevTime) {
				this.prevTime = curTime;
				if (enableTick) {
					if (USE_LOCK) {
						synchronized (lockRedraw) {
							onTick();
						}
					} else {
						onTick();
					}
				}
				tick++;
			}
			if (USE_LOCK) {
				synchronized (lockRedraw) {
					onDraw(bufGraph);
				}
			} else {
				onDraw(bufGraph);
			}
			if (USE_LOCK_NOTIFY) {
		        try {
		        	Thread.sleep(NOTIFY_SLEEP); //reduce CPU usage
	                synchronized (lockRedraw) {
		            	repaint();
		                lockRedraw.wait(); //reduce CPU usage
		            }
		        } catch (InterruptedException e) {
		        }
			} else {
				repaint();
				try {
					Thread.sleep(NORMAL_SLEEP); //reduce CPU usage
				} catch (InterruptedException e) {
				}
			}
		}
		//System.exit(0);
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
    }

	@Override
	public void paint(Graphics g) {
		if (USE_LOCK) {
			synchronized (lockRedraw) {
				g.drawImage(bufImage, 0, 0, this);
			}
		} else if (USE_LOCK_NOTIFY) {
			g.drawImage(bufImage, 0, 0, this);
//			Toolkit.getDefaultToolkit().sync();
			synchronized (lockRedraw) {
				lockRedraw.notify();
	        }
		} else {
			g.drawImage(bufImage, 0, 0, this);
		}
	}
		
	public void setEnableTick(boolean enableTick) {
		this.enableTick = enableTick;
	}
	
	public void setFrameTitle(String title) {
		if (this.frame != null) {
			this.frame.setTitle(title);
		}
	}
	
	public Graphics getBufGraph() {
		return this.bufGraph;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		onMouseClick(e.getX(), e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		onMouseDown(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		onMouseUp(e.getX(), e.getY());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		onMouseMove(e.getX(), e.getY());
	}
	
	protected void onInit() {
		
	}
	
	protected void onExit() {
		
	}
	
	protected void onDraw(Graphics g) {
		
	}
	
	protected void onTick() {
		
	}
	
	protected void onMouseClick(int x, int y) {
		
	}
	
	protected void onMouseDown(int x, int y) {
		
	}
	
	protected void onMouseUp(int x, int y) {
		
	}
	
	protected void onMouseMove(int x, int y) {
		
	}
	
	public void closeFrame() {
		WindowEvent wev = new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
}
