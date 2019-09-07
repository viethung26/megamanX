package com.hung.gameobject;

import java.awt.Graphics2D;

public abstract class Bullet extends ParticularObject {

	public Bullet(float x, float y, float width, float height, int blood, int damage, GameWorld gameWorld) {
		super(x,y,width,height,blood,gameWorld);
		setState(ALIVE);
		setDamage(damage);
	}
	abstract public void draw(Graphics2D g2);
	public void update() {
		super.update();
		setPosX(getPosX() + getSpeedX());
		ParticularObject object = getGameWorld().particularObjectManager.getCollisionWithEnemy(this);
		if(object != null && object.getState() == ALIVE){
			setBlood(0);
			object.beHurt(getDamage());
			System.out.println(object.getBlood());
		}

	}
	@Override
	public void hurtingCallBack() {
		
	}

	abstract public void attack();

	
}
