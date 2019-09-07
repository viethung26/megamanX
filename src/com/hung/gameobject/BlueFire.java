package com.hung.gameobject;

import java.awt.*;

import com.hung.effect.Animation;
import com.hung.effect.CacheDataLoader;

public class BlueFire extends Bullet {

	private Animation forwardBulletAnim, backBulletAnim;
	public BlueFire(float x, float y, GameWorld gameWorld) {
		super(x,y,60,30,1,10,gameWorld);
		
		forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("bluefire");
		backBulletAnim = CacheDataLoader.getInstance().getAnimation("bluefire");
		backBulletAnim.flipAllFrames();
	}
	public void update() {
		if(forwardBulletAnim.isIgnoreFrames(0) || backBulletAnim.isIgnoreFrames(0)) {
			setPosX(getPosX() + getSpeedX());
		}
		ParticularObject object = getGameWorld().particularObjectManager.getCollisionWithEnemy(this);
		if(object!=null && object.getState() == ALIVE){
			setBlood(0);
			object.setBlood(object.getBlood() - getDamage());
			object.setState(BEHURT);
		}
	}
	@Override
	public void draw(Graphics2D g2) {
		Camera camera = getGameWorld().camera;
		if(getDirection() == DIR_RIGHT) {
			if(!forwardBulletAnim.isIgnoreFrames(0) && forwardBulletAnim.getCurrentFrame() == 3) {
				forwardBulletAnim.setIgnoreFrames(0);
				forwardBulletAnim.setIgnoreFrames(1);
				forwardBulletAnim.setIgnoreFrames(2);
			}
			forwardBulletAnim.update(System.nanoTime());
			forwardBulletAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() - (int)camera.getPosY());
		} else {
			if(!backBulletAnim.isIgnoreFrames(0) && backBulletAnim.getCurrentFrame() == 3) {
				backBulletAnim.setIgnoreFrames(0);
				backBulletAnim.setIgnoreFrames(1);
				backBulletAnim.setIgnoreFrames(2);
			}
			backBulletAnim.update(System.nanoTime());
			backBulletAnim.draw(g2, (int)getPosX() - (int)camera.getPosX(), (int)getPosY() - (int)camera.getPosY());
		}
	}

	@Override
	public void attack() {
	}

	@Override
	public Rectangle getBoundforCollisionWithEnemy() {
		return super.getBoundforCollisionWithMap();
	}
}
