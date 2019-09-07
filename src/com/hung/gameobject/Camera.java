package com.hung.gameobject;

import java.awt.Graphics2D;

public class Camera extends GameObject {
	
	private boolean isLocked;
	
	public Camera(float x,float y, float widthView, float heightView, GameWorld gameWorld) {
		super(x,y,widthView,heightView,gameWorld);
		unlock();
	}
	@Override
	public void update() {
		if(!isLocked) {
			Megaman megaman = getGameWorld().megaman;
			if(megaman.getPosX() - getPosX() > 400) setPosX(megaman.getPosX() - 400);
			if(megaman.getPosX() - getPosX() < 200) setPosX(megaman.getPosX() - 200);
			
			if(megaman.getPosY() - getPosY() > 400) setPosY(megaman.getPosY() - 400);
			else if(megaman.getPosY() - getPosY() < 250) setPosY(megaman.getPosY() - 250);
		}
		
	}

	@Override
	public void draw(Graphics2D g2) {
		
	}
	public void lock() {
		isLocked = true;
	}
	public void unlock() {
		isLocked = false;
	}
	
	
}
