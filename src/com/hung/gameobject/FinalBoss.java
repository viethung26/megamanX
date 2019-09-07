package com.hung.gameobject;

import com.hung.effect.Animation;
import com.hung.effect.CacheDataLoader;

import java.awt.*;
import java.util.Hashtable;

/**
 * Created by vieth on 05-Oct-17.
 */
public class FinalBoss extends Human {

    private Animation idleforward, idleback;
    private Animation shootingforward, shootingback;
    private Animation slideforward, slideback;

    private Hashtable<String, Long> timeAttack = new Hashtable<String, Long>();
    private String[] attackType = new String[4];
    private int attackIndex = 0;
    private long lastAttackTime;
    private long startTimeForAttacked;

    public FinalBoss(float posX, float posY, GameWorld gameWorld) {
        super(posX, posY, 110, 150, 100, gameWorld);

        idleback = CacheDataLoader.getInstance().getAnimation("boss_idle");
        idleforward = CacheDataLoader.getInstance().getAnimation("boss_idle");
        idleforward.flipAllFrames();

        shootingback = CacheDataLoader.getInstance().getAnimation("boss_shooting");
        shootingforward = CacheDataLoader.getInstance().getAnimation("boss_shooting");
        shootingforward.flipAllFrames();

        slideback = CacheDataLoader.getInstance().getAnimation("boss_slide");
        slideforward = CacheDataLoader.getInstance().getAnimation("boss_slide");
        slideforward.flipAllFrames();

        setTimeForNoBeHurt(500*1000000);
        setDamage(10);

        attackType[0] = "NONE";
        attackType[1] = "shooting";
        attackType[2] = "NONE";
        attackType[3] = "slide";

        timeAttack.put("NONE", new Long(2000));
        timeAttack.put("shooting", new Long(500));
        timeAttack.put("slide", new Long(5000));

        setTeamType(ENEMY_TEAM);
    }

    @Override
    public void update() {
        super.update();

        if(getGameWorld().megaman.getPosX() > getPosX())
            setDirection(DIR_RIGHT);
        else setDirection(DIR_LEFT);

        if(startTimeForAttacked == 0)
            startTimeForAttacked = System.currentTimeMillis();
        else if(System.currentTimeMillis() - startTimeForAttacked > 300){
            attack();
            startTimeForAttacked = System.currentTimeMillis();
        }
        if(!attackType[attackIndex].equals("NONE")) {
            if (attackType[attackIndex].equals("shooting")) {

                Bullet bullet = new RocketBullet(getPosX(), getPosY() - 50, getGameWorld());
                if (getDirection() == DIR_LEFT) bullet.setSpeedX(-4);
                else bullet.setSpeedX(4);
                bullet.setTeamType(getTeamType());
                getGameWorld().bulletManager.addObject(bullet);

            } else if (attackType[attackIndex].equals("slide")) {

                if (getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundforCollisionWithMap()) != null)
                    setSpeedX(5);
                if (getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundforCollisionWithMap()) != null)
                    setSpeedX(-5);

                setPosX(getPosX() + getSpeedX());
            }
        }else{
            // stop attack
            setSpeedX(0);
        }


    }

    @Override
    public void attack() {

        if(System.currentTimeMillis() - lastAttackTime > timeAttack.get(attackType[attackIndex])){
            lastAttackTime = System.currentTimeMillis();

            attackIndex ++;
            if(attackIndex >= attackType.length) attackIndex = 0;

            if(attackType[attackIndex].equals("slide")){
                if(getPosX() < getGameWorld().megaman.getPosX()) setSpeedX(5);
                else setSpeedX(-5);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1)
        {
            System.out.println("Flash...");
        }else{
            if(attackType[attackIndex].equals("NONE")){
                if(getDirection() == DIR_RIGHT){
                    idleforward.update(System.nanoTime());
                    idleforward.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY());
                }else{
                    idleback.update(System.nanoTime());
                    idleback.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY());
                }
            }else if(attackType[attackIndex].equals("shooting")){

                if(getDirection() == DIR_RIGHT){
                    shootingforward.update(System.nanoTime());
                    shootingforward.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY());
                }else{
                    shootingback.update(System.nanoTime());
                    shootingback.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY());
                }

            }else if(attackType[attackIndex].equals("slide")){
                if(getSpeedX() > 0){
                    slideforward.update(System.nanoTime());
                    slideforward.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY() + 50);
                }else{
                    slideback.update(System.nanoTime());
                    slideback.draw(g2,(int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY() + 50);
                }
            }
        }
    }

    @Override
    public void run() {

    }

    @Override
    public void stopRun() {

    }

    @Override
    public void jump() {
        setSpeedY(-5);
    }

    @Override
    public void duck() {

    }

    @Override
    public void standUp() {

    }

    @Override
    public Rectangle getBoundforCollisionWithEnemy() {

        if(attackType[attackIndex].equals("slide")){
            Rectangle rect = getBoundforCollisionWithMap();
            rect.y += 100;
            rect.height -= 100;
            return rect;
        }else
            return getBoundforCollisionWithMap();

    }

    @Override
    public void hurtingCallBack() {

    }

}
