package com.hung.gameobject;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.hung.effect.Animation;
import com.hung.effect.CacheDataLoader;

public class Megaman extends Human{
	
	private boolean isShooting = false;
	private long lastShootingTime;
	
	private Animation runForwardAnim, runBackAnim, runShootingForwardAnim, runShootingBackAnim;
	private Animation idleForwardAnim, idleBackAnim, idleShootingForwardAnim, idleShootingBackAnim ;
	private Animation jumpForwardAnim, jumpBackAnim, jumpShootingForwardAnim, jumpShootingBackAnim;
	private Animation landingForwardAnim, landingBackAnim;
	private Animation duckingForwardAnim, duckingBackAnim;
	private AudioClip hurtingSound, shooting;
	
	public Megaman(float posX, float posY, GameWorld gameWorld) {
		super(posX,posY,70,90,100,gameWorld);

		shooting = CacheDataLoader.getInstance().getSound("bluefireshooting");
		hurtingSound = CacheDataLoader.getInstance().getSound("megamanhurt");

		setTeamType(LEAGUE_TEAM);
		setTimeForNoBeHurt(2000*1000000);
		
		runForwardAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAnim.flipAllFrames();   
        runShootingForwardAnim = CacheDataLoader.getInstance().getAnimation("runshoot");
        runShootingBackAnim = CacheDataLoader.getInstance().getAnimation("runshoot");
        runShootingBackAnim.flipAllFrames();
        
        idleForwardAnim = CacheDataLoader.getInstance().getAnimation("idle");
        idleBackAnim = CacheDataLoader.getInstance().getAnimation("idle");
        idleBackAnim.flipAllFrames();
        
        idleShootingForwardAnim = CacheDataLoader.getInstance().getAnimation("idleshoot");
        idleShootingBackAnim = CacheDataLoader.getInstance().getAnimation("idleshoot");
        idleShootingBackAnim.flipAllFrames();
        
        jumpForwardAnim = CacheDataLoader.getInstance().getAnimation("flyingup");
        jumpForwardAnim.setIsRepeated(false);
        jumpBackAnim = CacheDataLoader.getInstance().getAnimation("flyingup");
        jumpBackAnim.flipAllFrames();
        jumpBackAnim.setIsRepeated(false);
        
        jumpShootingForwardAnim = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
        jumpShootingForwardAnim.setIsRepeated(false);
        jumpShootingBackAnim = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
        jumpShootingBackAnim.flipAllFrames();
        jumpShootingBackAnim.setIsRepeated(false);
        
        landingForwardAnim = CacheDataLoader.getInstance().getAnimation("landing");
        landingForwardAnim.setIsRepeated(false);
        landingBackAnim = CacheDataLoader.getInstance().getAnimation("landing");
        landingBackAnim.flipAllFrames();
        landingBackAnim.setIsRepeated(false);
        
        duckingForwardAnim = CacheDataLoader.getInstance().getAnimation("duck");
        duckingBackAnim = CacheDataLoader.getInstance().getAnimation("duck"); 
        duckingBackAnim.flipAllFrames();

		hurtingForwardAnim = CacheDataLoader.getInstance().getAnimation("hurting");
		hurtingForwardAnim.setIsRepeated(false);
		hurtingBackAnim = CacheDataLoader.getInstance().getAnimation("hurting");
		hurtingBackAnim.flipAllFrames();
		hurtingBackAnim.setIsRepeated(false);
		setSpeedX(0);
	}
	
	public void update() {
		super.update();		
		if(System.nanoTime() - lastShootingTime > 500*1000000){
            isShooting = false;
        }
		if(isLanding()){
			landingBackAnim.update(System.nanoTime());
			if(landingBackAnim.isLastFrame()) {
				setLanding(false);
				landingBackAnim.reset();
				runBackAnim.reset();
				runForwardAnim.reset();
				jumpForwardAnim.reset();
				jumpBackAnim.reset();
			}
		}
		if(isJumping()) {
			jumpForwardAnim.update(System.nanoTime());
		}
		if(isDucking()) {
			setSpeedX(0);
			duckingForwardAnim.update(System.nanoTime());
		}
			
	}
	
	public void draw(Graphics2D g2) {
		Camera camera = getGameWorld().camera;
		switch(getState()) {
			case ALIVE:
			case NOBEHURT:
				//Render for jumping
				if(isJumping()) {
					if(isShooting) {
						if(getDirection() == DIR_RIGHT) {
							jumpShootingForwardAnim.setCurrentFrame(jumpForwardAnim.getCurrentFrame());
							jumpShootingForwardAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() - (int)camera.getPosY());
						}
						else {
							jumpShootingBackAnim.setCurrentFrame(jumpForwardAnim.getCurrentFrame());
							jumpShootingBackAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() - (int)camera.getPosY());
						}
					}
					else {
						if(getDirection() == DIR_RIGHT) {
							jumpForwardAnim.setCurrentFrame(jumpForwardAnim.getCurrentFrame());
							jumpForwardAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() - (int)camera.getPosY());
						}
						else {
							jumpBackAnim.setCurrentFrame(jumpForwardAnim.getCurrentFrame());
							jumpBackAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() - (int)camera.getPosY());
						}
					}
					
				}else if (isLanding()){
					//render for landing
					if(getDirection()==DIR_LEFT) {
						landingBackAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY()+2 - (int)camera.getPosY()) ;
					}
					else {
						landingForwardAnim.setCurrentFrame(landingBackAnim.getCurrentFrame());
						landingForwardAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY()+2 - (int)camera.getPosY());
					}
				}else if(isDucking()){
					if(getDirection()==DIR_RIGHT) {
						duckingForwardAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() + 10 - (int)camera.getPosY());
					}
					else {
						duckingBackAnim.setCurrentFrame(duckingForwardAnim.getCurrentFrame());
						duckingBackAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() + 10 - (int)camera.getPosY());
					}
				}else {
					//render for running
					if(getSpeedX()>0) {
						if(isShooting) {
							runShootingForwardAnim.update(System.nanoTime());
							runShootingForwardAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() - (int)camera.getPosY());
						}else {
							runForwardAnim.update(System.nanoTime());
							runForwardAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() - (int)camera.getPosY());
						}
						
					}else if(getSpeedX()<0) {
						if(isShooting) {
							runShootingBackAnim.update(System.nanoTime());
							runShootingBackAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() - (int)camera.getPosY());
						}else {
							runBackAnim.update(System.nanoTime());
							runBackAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() - (int)camera.getPosY());
						}
						
					}else {
						//render for idling
						if(isShooting) {
							if(getDirection() == DIR_RIGHT) {
								idleShootingForwardAnim.update(System.nanoTime());
								idleShootingForwardAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() - (int)camera.getPosY());
							}
							else {
								idleShootingBackAnim.update(System.nanoTime());
								idleShootingBackAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() - (int)camera.getPosY());
							}
						}else {
							if(getDirection() == DIR_RIGHT) {
								idleForwardAnim.update(System.nanoTime());
								idleForwardAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() - (int)camera.getPosY());
							}
							else {
								idleBackAnim.update(System.nanoTime());
								idleBackAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() - (int)camera.getPosY());
							}
						}
						
					}
				}

				
				break;
			case BEHURT:
				if(getDirection() == DIR_RIGHT){
					hurtingForwardAnim.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY());
				}else{
					hurtingBackAnim.setCurrentFrame(hurtingForwardAnim.getCurrentFrame());
					hurtingBackAnim.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY());
				}
				break;
			case FEY:
				break;
			case DEATH:
				break;
		}
		drawBoundForCollisionWithMap(g2);
		drawBoundForCollisionWithEnemy(g2);
	}

	@Override
	public void attack() {
		if (!isShooting && !isDucking()) {
			lastShootingTime = System.nanoTime();
			isShooting = true;
			shooting.play();
			BlueFire bullet = new BlueFire(getPosX(), getPosY(), getGameWorld());
			bullet.setTeamType(getTeamType());
			if (getDirection() == DIR_RIGHT) {
				bullet.setPosX(bullet.getPosX() + 40);
				bullet.setSpeedX(10);
				bullet.setDirection(getDirection());
				if (getSpeedX() >0 && getSpeedY() == 0) {
					bullet.setPosX(bullet.getPosX() + 10);
					bullet.setPosY(bullet.getPosY() - 5);
				}
			} else {
				bullet.setPosX(bullet.getPosX() - 40);
				bullet.setSpeedX(-10);
				bullet.setDirection(getDirection());
				bullet.setPosY(bullet.getPosY() - 5);
				if (getSpeedX() < 0 && getSpeedY() == 0) {
					bullet.setPosX(bullet.getPosX() - 10);
					bullet.setPosY(bullet.getPosY() - 5);
				}
			}
			if (isJumping()) {
				bullet.setPosY(bullet.getPosY() - 20);
			}
			getGameWorld().bulletManager.addObject(bullet);
			lastShootingTime = System.nanoTime();
			isShooting = true;
		}
	}

	@Override
	public void hurtingCallBack() {
		hurtingSound.play();
		
	}

	@Override
	public void run() {
		if(getDirection()==DIR_LEFT)
			setSpeedX(-5);
		else 
			setSpeedX(5);
	}

	@Override
	public void stopRun() {
		setSpeedX(0);
		
	}

	@Override
	public void jump() {
		if(!isJumping()) {
			setJumping(true);
			setSpeedY(-7.0f);
			jumpBackAnim.reset();
			jumpForwardAnim.reset();
		} else{

			Rectangle rectRight = getBoundforCollisionWithMap();
			rectRight.x+=1;
			Rectangle rectLeft = getBoundforCollisionWithMap();
			rectLeft.x-=1;
			if(getGameWorld().physicalMap.haveCollisionWithRightWall(rectRight)!=null && getDirection() == DIR_RIGHT) {
				jumpBackAnim.reset();
				jumpForwardAnim.reset();
				setSpeedY(-7);
			} else if(getGameWorld().physicalMap.haveCollisionWithLeftWall(rectLeft) != null && getDirection() == DIR_LEFT) {
				jumpBackAnim.reset();
				jumpForwardAnim.reset();
				setSpeedY(-7);
			}
		}
		
	}

	@Override
	public void duck() {
		if(!isJumping()) {
			setDucking(true);
//			setHeight(65);
		}
	}
	@Override
	public void standUp() {
		setDucking(false);
//		setHeight(80);
	}
	

	@Override
	public Rectangle getBoundforCollisionWithEnemy() {
		Rectangle rect = getBoundforCollisionWithMap();
		if(isDucking()) {
			rect.x = (int) getPosX() - 22;
			rect.y = (int) getPosY() - 25;
			rect.width = 44;
			rect.height = 65;
		}else {
			rect.x = (int) getPosX() - 22;
			rect.y = (int) getPosY() - 40;
			rect.width = 44;
			rect.height = 80;
		}
		return rect;
	}
	
}
