package com.iteye.weimingtom.firetree;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
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
	
	private Frame frame;
	private Graphics bufGraph;
	private Image bufImage;
	private Thread drawThread;
	private long prevTime;
	
	protected String title;
	protected int canvasWidth, canvasHeight, tickPerSecond;
	protected boolean isStopped = false;
	protected long tick = 0;
	protected boolean enableTick = true;
	
	public MainFrame(String title, int canvasWidth, int canvasHeight, int tickPerSecond) {
		this.title = title;
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.tickPerSecond = tickPerSecond;
		setPreferredSize(new Dimension(this.canvasWidth, this.canvasHeight));
		addMouseListener(this);
		addKeyListener(this);
		addMouseMotionListener(this);
		frame = new Frame();
		frame.add(this);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				isStopped = true;
				if (drawThread != null) {
					try {
						drawThread.join(1000);
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
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		this.requestFocus(); //listen for keyboard event
		bufImage = this.createImage(canvasWidth, canvasHeight);
		bufGraph = bufImage.getGraphics();
		bufGraph.clearRect(0, 0, canvasWidth, canvasHeight);
		drawThread = new Thread(this);
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
			if (curTime - (1000 / tickPerSecond) > this.prevTime) {
				this.prevTime = curTime;
				if (enableTick) {
					onTick();
				}
				tick++;
			}
			onDraw(bufGraph);
			repaint();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
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
		g.drawImage(bufImage, 0, 0, this);
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
}
