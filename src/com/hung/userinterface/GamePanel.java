package com.hung.userinterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.hung.effect.Animation;
import com.hung.effect.FrameImage;

public class GamePanel extends JPanel implements Runnable,KeyListener{
	final int FPS = 80;
	final long PERIOD = 1000*1000000/80;
	long sleepTime;
	Thread thread;
	Boolean isRunning;
//	Animation animation;
//	FrameImage frame1;
	InputManager inputManager;
	public GamePanel() {
		inputManager = new InputManager();
		try {
			BufferedImage image = ImageIO.read(new File("Assets/megasprite.png"));
//			BufferedImage image1 = image.getSubimage(797, 44, 79, 88);
//			 frame1= new FrameImage(image1,"image1");
			
			
		} catch (IOException e) {;
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g) {
		System.out.println(5);
		g.setColor(Color.red);
		g.fillRect(0, 0, GameFrame.MASTER_WIDTH, GameFrame.MASTER_HEIGHT);
//		Graphics2D g2 = (Graphics2D)g;
//		frame1.draw(g2, 50, 100);
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
