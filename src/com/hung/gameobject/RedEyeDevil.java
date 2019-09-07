package com.hung.gameobject;

import com.hung.effect.Animation;
import com.hung.effect.CacheDataLoader;

import java.applet.AudioClip;
import java.awt.*;

/**
 * Created by vieth on 04-Oct-17.
 */
public class RedEyeDevil extends ParticularObject {

    private Animation forwardAnim, backAnim;

    private AudioClip shooting;

    private long startTimeToShoot;

    public RedEyeDevil(float posX, float posY, GameWorld gameWorld) {
        super(posX, posY, 127, 89, 30, gameWorld);
        shooting = CacheDataLoader.getInstance().getSound("redeyeshooting");

        setDamage(100);
        backAnim = CacheDataLoader.getInstance().getAnimation("redeye");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("redeye");
        forwardAnim.flipAllFrames();
        setTimeForNoBeHurt(300000000);
        startTimeToShoot = 0;
    }

    @Override
    public void update() {
        super.update();
        if(System.nanoTime() - startTimeToShoot > 1000*1000000){
            attack();
            startTimeToShoot = System.nanoTime();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(!isObjectOutOfCameraView()){
            if(getState() == NOBEHURT && (System.nanoTime()%2 == 1)){
                //flash
            }else {
                if(getDirection() == DIR_LEFT){
                    backAnim.update(System.nanoTime());
                    backAnim.draw(g2,(int)(getPosX() - getGameWorld().camera.getPosX()),(int)(getPosY()- getGameWorld().camera.getPosY()));
                } else{
                    forwardAnim.update(System.nanoTime());
                    forwardAnim.draw(g2,(int)(getPosX() - getGameWorld().camera.getPosX()),(int)(getPosY()- getGameWorld().camera.getPosY()));
                }
            }
        }
    }

    @Override
    public void hurtingCallBack() {

    }

    @Override
    public void attack() {
        shooting.play();
        Bullet bullet = new RedEyeBullet(getPosX(),getPosY(),getGameWorld());
        if(getDirection() == DIR_LEFT)
            bullet.setSpeedX(-8);
        else bullet.setSpeedX(8);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);

    }

    @Override
    public Rectangle getBoundforCollisionWithEnemy() {
        Rectangle rect = getBoundforCollisionWithMap();
        rect.x += 20;
        rect.width -= 40;
        return rect;
    }
}
