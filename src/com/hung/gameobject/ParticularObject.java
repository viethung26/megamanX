package com.hung.gameobject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.hung.effect.Animation;

public abstract class ParticularObject extends GameObject {
	
	public static final int LEAGUE_TEAM = 0;
	public static final int ENEMY_TEAM = 1;
	
	public static final int DIR_LEFT = 0;
	public static final int DIR_RIGHT = 1;
	
	final public static int ALIVE = 0;
	final public static int BEHURT = 1;
	final public static int NOBEHURT = 2;
	final public static int FEY = 3;
	final public static int DEATH =4;
	
	
	private int teamType;
	private int direction;
	private int state;
	private int blood;
	private int damage;
	
	private long startTimeNoBeHurt;
	private long timeForNoBeHurt;
	protected Animation hurtingForwardAnim, hurtingBackAnim;

	public ParticularObject(float posX, float posY, float width, float height, int blood, GameWorld gameWorld) {
		
		super(posX,posY,width,height,gameWorld);
		setBlood(blood);
		direction = DIR_RIGHT;
		
	}
	
	public int getTeamType() {
		return teamType;
	}

	public void setTeamType(int teamType) {
		this.teamType = teamType;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getBlood() {
		return blood;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	
	public long getStartTimeNoBeHurt() {
		return startTimeNoBeHurt;
	}

	public void setStartTimeNoBeHurt(long startTimeNoBeHurt) {
		this.startTimeNoBeHurt = startTimeNoBeHurt;
	}

	public long getTimeForNoBeHurt() {
		return timeForNoBeHurt;
	}

	public void setTimeForNoBeHurt(long timeForNoBeHurt) {
		this.timeForNoBeHurt = timeForNoBeHurt;
	}

	public Rectangle getBoundforCollisionWithMap() {
		Rectangle bound = new Rectangle();
		bound.x = (int)(getPosX() - getWidth()/2);
		bound.y = (int)(getPosY() - getHeight()/2);
		bound.width = (int)getWidth();
		bound.height = (int)getHeight();
		return bound;
	}
	
	public void beHurt(int damageEat) {
		setBlood(getBlood()-damageEat);
		setState(BEHURT);
		hurtingCallBack();
	}
	
	@Override
	public void update() {
		switch(getState()) {
			case ALIVE:
				ParticularObject object = getGameWorld().particularObjectManager.getCollisionWithEnemy(this);
				if(object != null ) {
					beHurt(getDamage());
				}
				break;
			case BEHURT:
				if(hurtingBackAnim == null)
				{
					setState(NOBEHURT);
					startTimeNoBeHurt = System.nanoTime();
					if(getBlood() == 0) {
						setState(FEY);
					}
				}
				else {
					hurtingForwardAnim.update(System.nanoTime());
					if(hurtingForwardAnim.isLastFrame()) {
						hurtingForwardAnim.reset();
						setState(NOBEHURT);
						if(getBlood() == 0)
							setState(FEY);
						startTimeNoBeHurt = System.nanoTime();
					}
				}
				break;
			case NOBEHURT:
				if(System.nanoTime() - startTimeNoBeHurt > timeForNoBeHurt)
					setState(ALIVE);
				break;
			case FEY:
				setState(DEATH);
				break;
			case DEATH:
				
				break;
			default:
				
				break;
			
		}
	}
	public void drawBoundForCollisionWithMap(Graphics2D g2) {
		Camera camera = getGameWorld().camera;
		Rectangle rect = getBoundforCollisionWithMap();
		g2.setColor(Color.blue);
		g2.drawRect(rect.x - (int)camera.getPosX(), rect.y - (int)camera.getPosY(), rect.width, rect.height );
	}
	public void drawBoundForCollisionWithEnemy(Graphics2D g2) {
		Camera camera = getGameWorld().camera;
		Rectangle rect = getBoundforCollisionWithEnemy();
		g2.setColor(Color.red);
		g2.drawRect(rect.x - (int)camera.getPosX(), rect.y - (int)camera.getPosY(), rect.width, rect.height );
	}
	public Rectangle getBoundforCollisionWithEnemy() {
		Rectangle rect = getBoundforCollisionWithMap();
		return rect;
	}
	
	public boolean isObjectOutOfCameraView() {
		Camera camera = getGameWorld().camera;
		if(getPosX() - camera.getPosX() > camera.getWidth() || getPosX() - camera.getPosX() < -50 ||
				getPosY() - camera.getPosY() > camera.getHeight() || getPosY() - camera.getPosY() < -50)
			return true;
		return false;
	}
	
	abstract public void draw(Graphics2D g2);
	abstract public void hurtingCallBack();
	abstract public void attack();

	
}
