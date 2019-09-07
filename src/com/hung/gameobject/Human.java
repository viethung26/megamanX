package com.hung.gameobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Human extends ParticularObject {
	private boolean isJumping;
	private boolean isDucking;
	private boolean isLanding;
	private boolean isClimbing;
	
	public Human(float posX, float posY, float width, float height, int blood, GameWorld gameWorld) {
		super(posX, posY, width, height, blood, gameWorld);
		setState(ALIVE);
		setLanding(false);
	}
	
	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public boolean isDucking() {
		return isDucking;
	}

	public void setDucking(boolean isDucking) {
		this.isDucking = isDucking;
	}

	public boolean isLanding() {
		return isLanding;
	}

	public void setLanding(boolean isLanding) {
		this.isLanding = isLanding;
	}
	
	public boolean isClimbing() {
		return isClimbing;
	}

	public void setClimbing(boolean isClimbing) {
		this.isClimbing = isClimbing;
	}
	
	@Override
	public void update() {
		super.update();
		if(getState() == ALIVE || getState() == NOBEHURT) {
			if(!isLanding()) {
				if(!isDucking) setPosX(getPosX()+getSpeedX());//set to check right after that
				
				Rectangle rectLeft = getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundforCollisionWithMap());
				if(rectLeft != null && getDirection() == DIR_LEFT) {
					setPosX(rectLeft.x + rectLeft.width + getWidth()/2);
//					setSpeedX(0);
				}
				
				Rectangle rectRight = getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundforCollisionWithMap());
				if(rectRight != null && getDirection() == DIR_RIGHT) {
					setPosX(rectRight.x - getWidth()/2);
//					setSpeedX(0);
				}
//				setPosY(getPosY()+getSpeedY()); //set to check right after that, can't set before check right left because of causing check of them != null
				Rectangle futureBound = getBoundforCollisionWithMap(); //of Human
				futureBound.y+=(getSpeedY()!=0?getSpeedY():2);
				
				Rectangle rectLand = gameWorld.physicalMap.haveCollisionWithLand(futureBound);		
				Rectangle rectTop = getGameWorld().physicalMap.haveCollisionWithTop(futureBound);
				if(rectTop != null) {
					setPosY(rectTop.y + rectTop.height + getHeight()/2);
					setSpeedY(0);
				}else if(rectLand != null) {
					setJumping(false);
					if(getSpeedY() > 0) setLanding(true);
					setSpeedY(0);
					setPosY(rectLand.y- getHeight()/2 );
				}
				else {
//					setPosY(getPosY()+getSpeedY()); // can't set here because of causing check of left right != null
					setJumping(true);						
					setSpeedY(getSpeedY()+GRAVITY);
					setPosY(getPosY()+getSpeedY());
				}
			}
			
		}
	}
	@Override
	public void draw(Graphics2D g2) {	
		
	}
	abstract public void run();
	abstract public void stopRun();
	abstract public void jump();
	abstract public void duck();
	abstract public void standUp();
	abstract public Rectangle getBoundforCollisionWithEnemy();
	abstract public void hurtingCallBack();
	abstract public void attack();
	
}
