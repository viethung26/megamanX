package com.hung.userinterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable,KeyListener{
	final int FPS = 80;
	final long PERIOD = 1000*1000000/80;
	long sleepTime;
	Thread thread;
	Boolean isRunning;
	InputManager inputManager;
	public GamePanel() {
		inputManager = new InputManager();
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(0, 0, GameFrame.MASTER_WIDTH, GameFrame.MASTER_HEIGHT);
	}
	
	public void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		while(isRunning) {
			long beginTime = System.nanoTime();
			//update
			//render
			repaint();
			long deltaTime = beginTime - System.nanoTime();
			sleepTime = PERIOD - deltaTime;
			try {
				if(sleepTime>0)
					Thread.sleep(sleepTime/1000000);
				else 
					Thread.sleep(PERIOD/2000000);
			} catch (InterruptedException e) {}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		inputManager.processKeyPress(e.getKeyCode());
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		inputManager.processKeyRelease(e.getKeyCode());
		
	}
	

}
