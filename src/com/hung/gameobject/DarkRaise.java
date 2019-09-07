package com.hung.gameobject;

import com.hung.effect.Animation;
import com.hung.effect.CacheDataLoader;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class DarkRaise extends ParticularObject{

    private Animation forwardAnim, backAnim;
    
    private long startTimeToShoot;
    private float x1, x2;
    
    public DarkRaise(float x, float y, GameWorld gameWorld) {
        super(x, y, 127, 89, 30, gameWorld);
        backAnim = CacheDataLoader.getInstance().getAnimation("darkraise");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("darkraise");
        forwardAnim.flipAllFrames();
        startTimeToShoot = 0;
        setTimeForNoBeHurt(300000000);
        
        x1 = x - 100;
        x2 = x + 100;
        setSpeedX(1);
        
        setDamage(10);
    }

    @Override
    public void attack() {
    
        float megaManX = getGameWorld().megaman.getPosX();
        float megaManY = getGameWorld().megaman.getPosY();
        
        float deltaX = megaManX - getPosX();
        float deltaY = megaManY - getPosY();
        
        float speed = 3;
        float a = Math.abs(deltaX/deltaY);
        
        float speedX = (float) Math.sqrt(speed*speed*a*a/(a*a + 1));
        float speedY = (float) Math.sqrt(speed*speed/(a*a + 1));
        
        
        
        Bullet bullet = new DarkRaiseBullet(getPosX(), getPosY(), getGameWorld());
        
        if(deltaX < 0)
            bullet.setSpeedX(-speedX);
        else bullet.setSpeedX(speedX);
        bullet.setSpeedY(speedY);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);

    }

    
    public void update(){
        super.update();
        if(getPosX() < x1)
            setSpeedX(1);
        else if(getPosX() > x2)
            setSpeedX(-1);
        setPosX(getPosX() + getSpeedX());
        
        if(System.nanoTime() - startTimeToShoot > 1000*10000000*1.5){
            attack();
            startTimeToShoot = System.nanoTime();
        }
    }
    
    @Override
    public Rectangle getBoundforCollisionWithEnemy() {
        Rectangle rect = getBoundforCollisionWithMap();
        rect.x += 20;
        rect.width -= 40;
        
        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {
        if(!isObjectOutOfCameraView()){
            if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1){
                // plash...
            }else{
                if(getDirection() == DIR_LEFT){
                    backAnim.update(System.nanoTime());
                    backAnim.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()),
                            (int)(getPosY() - getGameWorld().camera.getPosY()));
                }else{
                    forwardAnim.update(System.nanoTime());
                    forwardAnim.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()),
                            (int)(getPosY() - getGameWorld().camera.getPosY()));
                }
            }
        }
        //drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public void hurtingCallBack() {

    }

}
