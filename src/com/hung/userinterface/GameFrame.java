package com.hung.userinterface;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	public static final int MASTER_WIDTH = 1000;
	public static final int MASTER_HEIGHT = 800;
	
	GamePanel gamePanel;
	
	GameFrame(){
		Toolkit toolkit = this.getToolkit();
		Dimension dimension = toolkit.getScreenSize(); 
		setBounds((dimension.width - MASTER_WIDTH)/2,(dimension.height - MASTER_HEIGHT)/2,MASTER_WIDTH,MASTER_HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamePanel = new GamePanel();
		add(gamePanel);
	}	
	public static void main(String[] args) {
		new GameFrame();
	}
}
