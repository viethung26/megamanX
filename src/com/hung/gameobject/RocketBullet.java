package com.hung.gameobject;

import com.hung.effect.Animation;
import com.hung.effect.CacheDataLoader;

import java.awt.*;

/**
 * Created by vieth on 05-Oct-17.
 */
public class RocketBullet extends Bullet {

    private Animation forwardBulletAnimUp, forwardBulletAnimDown, forwardBulletAnim;
    private Animation backBulletAnimUp, backBulletAnimDown, backBulletAnim;

    private long startTimeForChangeSpeedY;

    public RocketBullet(float x, float y, GameWorld gameWorld) {
        super(x, y, 30, 30, 1, 10, gameWorld);

        backBulletAnimUp = CacheDataLoader.getInstance().getAnimation("rocketUp");
        backBulletAnimDown = CacheDataLoader.getInstance().getAnimation("rocketDown");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("rocket");

        forwardBulletAnimUp = CacheDataLoader.getInstance().getAnimation("rocketUp");
        forwardBulletAnimUp.flipAllFrames();
        forwardBulletAnimDown = CacheDataLoader.getInstance().getAnimation("rocketDown");
        forwardBulletAnimDown.flipAllFrames();
        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("rocket");
        forwardBulletAnim.flipAllFrames();
    }

    @Override
    public void draw(Graphics2D g2) {
        if(getSpeedX() > 0){
            if(getSpeedY() > 0){
                forwardBulletAnimDown.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY());
            }else if(getSpeedY() < 0){
                forwardBulletAnimUp.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY());
            }else
                forwardBulletAnim.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY());
        }else{
            if(getSpeedY() > 0){
                backBulletAnimDown.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY());
            }else if(getSpeedY() < 0){
                backBulletAnimUp.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY());
            }else
                backBulletAnim.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY());
        }
    }

    private void changeSpeedY(){
        if(System.currentTimeMillis() % 3 == 0){
            setSpeedY(getSpeedX());
        }else if(System.currentTimeMillis() % 3 == 1){
            setSpeedY(-getSpeedX());
        }else {
            setSpeedY(0);

        }
    }

    @Override
    public void update() {
        super.update();
        if(System.nanoTime() - startTimeForChangeSpeedY > 500*1000000){
            changeSpeedY();
            startTimeForChangeSpeedY = System.nanoTime();
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
