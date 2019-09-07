package com.hung.gameobject;

import java.awt.Graphics2D;

public abstract class GameObject {
	private float posX;
	private float posY;
	private float width;
	private float height;
	private float speedX;
	private float speedY;
	
	final static float GRAVITY = 0.2f;
	public GameWorld gameWorld;
	
	public GameObject() {
		posX = posY = width = height = 0;
		gameWorld = null;
	}
	public GameObject(float posX, float posY, float width, float height, GameWorld gameWorld) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.gameWorld = gameWorld;
	}
	public float getPosX() {
		return posX;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public float getPosY() {
		return posY;
	}
	public void setPosY(float posY) {
		this.posY = posY;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	
	public float getSpeedX() {
		return speedX;
	}
	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}
	public float getSpeedY() {
		return speedY;
	}
	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}
	public GameWorld getGameWorld() {
		return gameWorld;
	}
	public void setGameWorld(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}
	
	abstract public void update();
	abstract public void draw(Graphics2D g2);
	
}
