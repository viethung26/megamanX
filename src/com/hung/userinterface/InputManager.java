package com.hung.userinterface;

import java.awt.event.KeyEvent;

import com.hung.gameobject.GameWorld;
import com.hung.gameobject.Megaman;
import com.hung.gameobject.PhysicalMap;

public class InputManager {
	
	GameWorld gameWorld;
	
	public InputManager(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}
	
	public void processKeyPress(int keyCode) {
		
		switch(keyCode) {
		
			case KeyEvent.VK_UP:
				break;
			case KeyEvent.VK_DOWN:
				gameWorld.megaman.duck();
				break;
			case KeyEvent.VK_RIGHT:
				gameWorld.megaman.setDirection(Megaman.DIR_RIGHT);
				if(!gameWorld.megaman.isDucking()) gameWorld.megaman.run();
				break;
			case KeyEvent.VK_LEFT:
				gameWorld.megaman.setDirection(Megaman.DIR_LEFT);
				if(!gameWorld.megaman.isDucking()) gameWorld.megaman.run();
				break;
			case KeyEvent.VK_ENTER:
				if(gameWorld.state == GameWorld.PAUSEGAME){
					if(gameWorld.previousState == GameWorld.GAMEPLAY)
						gameWorld.switchState(GameWorld.GAMEPLAY);
					else gameWorld.switchState(GameWorld.TUTORIAL);
					gameWorld.backgroundSound.loop();
					gameWorld.backgroundSound.play();
				}
				if(gameWorld.state == GameWorld.TUTORIAL && gameWorld.storyTutorial >= 1){
					if(gameWorld.storyTutorial<=3){
						gameWorld.storyTutorial ++;
						gameWorld.currentSize = 1;
						gameWorld.textTutorial = gameWorld.texts1[gameWorld.storyTutorial-1];
					}else{
						gameWorld.switchState(GameWorld.GAMEPLAY);
					}

					// for meeting boss tutorial
					if(gameWorld.tutorialState == GameWorld.MEETFINALBOSS){
						gameWorld.switchState(GameWorld.GAMEPLAY);
					}
				}
				break;
			case KeyEvent.VK_SPACE:
				gameWorld.megaman.jump();
				break;
			case KeyEvent.VK_A:
				gameWorld.megaman.attack();
				break;
		}
	}
	public void processKeyRelease(int keyCode) {
		
		switch(keyCode) {
		
			case KeyEvent.VK_UP:
				break;
			case KeyEvent.VK_DOWN:
				gameWorld.megaman.standUp();
				break;
			case KeyEvent.VK_RIGHT:
				if(gameWorld.megaman.getSpeedX()>0) gameWorld.megaman.stopRun();
				break;
			case KeyEvent.VK_LEFT:
				if(gameWorld.megaman.getSpeedX()<0) gameWorld.megaman.stopRun();
				break;
			case KeyEvent.VK_ENTER:
				break;
			case KeyEvent.VK_SPACE:
				break;
			case KeyEvent.VK_A:
				break;
		}
}
		
		
}
