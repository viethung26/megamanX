/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hung.gameobject;

import com.hung.effect.Animation;
import com.hung.effect.CacheDataLoader;

import java.awt.*;

public class SmallRedGun extends ParticularObject{

    private Animation forwardAnim, backAnim;
    
    private long startTimeToShoot;
    
    public SmallRedGun(float x, float y, GameWorld gameWorld) {
        super(x, y, 127, 89, 100, gameWorld);
        backAnim = CacheDataLoader.getInstance().getAnimation("smallredgun");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("smallredgun");
        forwardAnim.flipAllFrames();
        startTimeToShoot = 0;
        setTimeForNoBeHurt(300000000);
    }

    @Override
    public void attack() {
    
        Bullet bullet = new YellowFlowerBullet(getPosX(), getPosY(), getGameWorld());
        bullet.setSpeedX(-3);
        bullet.setSpeedY(3);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);
        
        bullet = new YellowFlowerBullet(getPosX(), getPosY(), getGameWorld());
        bullet.setSpeedX(3);
        bullet.setSpeedY(3);
        bullet.setTeamType(getTeamType());
         getGameWorld().bulletManager.addObject(bullet);
    }

    
    public void update(){
        super.update();
        if(System.nanoTime() - startTimeToShoot > 1000*10000000*2.0){
            attack();
            System.out.println("Red Eye attack");
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
