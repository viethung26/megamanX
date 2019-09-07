package com.hung.gameobject;

import com.hung.effect.Animation;
import com.hung.effect.CacheDataLoader;

import java.awt.*;

public class RedEyeBullet extends Bullet {

    Animation forwardBulletAnim, backBulletAnim;

    public RedEyeBullet(float x, float y, GameWorld gameWorld) {
        super(x, y, 30, 30, 1 , 10, gameWorld);
        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("redeyebullet");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("redeyebullet");
        backBulletAnim.flipAllFrames();
    }

    @Override
    public void draw(Graphics2D g2) {
        Camera camera = getGameWorld().camera;
        if(getSpeedX()>0){
            forwardBulletAnim.update(System.nanoTime());
            forwardBulletAnim.draw(g2,(int)(getPosX() - camera.getPosX()),(int)(getPosY() - camera.getPosY()));

        }else{
            backBulletAnim.update(System.nanoTime());
            backBulletAnim.draw(g2,(int)(getPosX() - camera.getPosX()),(int)(getPosY() - camera.getPosY()));
        }
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void hurtingCallBack() {
        super.hurtingCallBack();
    }

    @Override
    public void attack() {
    }

    @Override
    public Rectangle getBoundforCollisionWithEnemy() {
        return getBoundforCollisionWithMap();
    }
}
