package com.hung.gameobject;

import com.hung.effect.Animation;
import com.hung.effect.CacheDataLoader;

import java.awt.*;

public class RobotRBullet extends Bullet{
	
    private Animation forwardBulletAnim, backBulletAnim;
    
    public RobotRBullet(float x, float y, GameWorld gameWorld) {
        super(x, y, 60, 30, 1, 10, gameWorld);
        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("robotRbullet");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("robotRbullet");
        backBulletAnim.flipAllFrames();
    }

    
    
    @Override
    public Rectangle getBoundforCollisionWithEnemy() {
        // TODO Auto-generated method stub
        return getBoundforCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
            // TODO Auto-generated method stub
        if(getSpeedX() > 0){          
            forwardBulletAnim.update(System.nanoTime());
            forwardBulletAnim.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY());
        }else{
            backBulletAnim.update(System.nanoTime());
            backBulletAnim.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY());
        }
        //drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public void update() {
            // TODO Auto-generated method stub
        super.update();
    }

    @Override
    public void attack() {}

}
