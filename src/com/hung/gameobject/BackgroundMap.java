package com.hung.gameobject;

import java.awt.Graphics2D;

import com.hung.effect.CacheDataLoader;
import com.hung.userinterface.GameFrame;

public class BackgroundMap extends GameObject {	
	
	final int SIZE = 30;
	int [][] map;
	public BackgroundMap(float posX, float posY, float width, float height, GameWorld gameWorld) {
		super(posX, posY, width, height, gameWorld);
		map = CacheDataLoader.getInstance().getBackGroundMap();
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g2) {
		Camera camera = getGameWorld().camera;
		
		for(int i=0; i<map.length; i++)
			for(int j=0; j<map[0].length; j++) {
				if(map[i][j] != 0 && j*SIZE - camera.getPosX() > -30 && j*SIZE - camera.getPosX() < GameFrame.MASTER_WIDTH &&
						i*SIZE - camera.getPosY() > -30 && i*SIZE - camera.getPosY()<GameFrame.MASTER_HEIGHT)
					g2.drawImage(CacheDataLoader.getInstance().getFrameImage("tiled"+map[i][j]).getImage(), (int)getPosX() + j*SIZE - (int)camera.getPosX() , 
							(int)getPosY() + i*SIZE - (int)camera.getPosY(),null);
				
							}
	}

}
