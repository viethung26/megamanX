package com.hung.userinterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.hung.gameobject.GameWorld;


public class GamePanel extends JPanel implements Runnable,KeyListener{
//	final int FPS = 80;
//	final long PERIOD = 1000*1000000/FPS;
	long sleepTime;
	Thread thread;
	Boolean isRunning;
	InputManager inputManager;
	Graphics2D bufG2;
	
	GameWorld gameWorld;
	
	public GamePanel() {
		gameWorld = new GameWorld();
		inputManager = new InputManager(gameWorld);
	}

	
	public void paint(Graphics g) {
		g.drawImage(gameWorld.getBufferedImage(), 0, 0, this);
		
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
			gameWorld.update();
			gameWorld.render();
			repaint();
			long PERIOD = 1000000000/80;
			long deltaTime = System.nanoTime() - beginTime; 
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
