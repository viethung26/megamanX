package com.hung.userinterface;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import com.hung.effect.CacheDataLoader;

public class GameFrame extends JFrame {
	public static final int MASTER_WIDTH = 1000;
	public static final int MASTER_HEIGHT = 800;
	
	GamePanel gamePanel;
	
	public GameFrame(){
		Toolkit toolkit = this.getToolkit();
		Dimension dimension = toolkit.getScreenSize(); 
		setBounds((dimension.width - MASTER_WIDTH)/2,(dimension.height - MASTER_HEIGHT)/2,MASTER_WIDTH,MASTER_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			CacheDataLoader.getInstance().loadData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		gamePanel = new GamePanel();
		add(gamePanel);
	}	
	public void startFrame() {
		gamePanel.start();
	}
	public static void main(String[] args) {
		GameFrame gameFrame = new GameFrame();
		gameFrame.setVisible(true);
		gameFrame.startFrame();
	}
}
