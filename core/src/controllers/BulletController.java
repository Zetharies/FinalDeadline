/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.badlogic.gdx.Gdx;
import models.Bullet;
import models.NPC;


public class BulletController extends NPCController{

    private Bullet bullet;
    private int rangeTimer;
    private final int RANGE = 130;

    public BulletController(Bullet bullet) {
        this.bullet = bullet;
        rangeTimer = 0;
    }

    public void update(float delta) {
        // this.shoot = true;
        rangeTimer++;
        if (rangeTimer == RANGE) {
            bullet.setShoot(false);
            rangeTimer = 0;
        } else {
            bullet.updateTimer(delta);
            if (this.bullet.x < playerX + 1.5) {
                this.bullet.x += Gdx.graphics.getDeltaTime() * Bullet.SPEED;
            }
            if (this.bullet.x >playerX + 1.5) {
                this.bullet.x -= Gdx.graphics.getDeltaTime() * Bullet.SPEED;
            }
            if (this.bullet.y < playerY + 0.5) {
                this.bullet.y += Gdx.graphics.getDeltaTime() * Bullet.SPEED;
            }
            if (this.bullet.y > playerY + 1.5) {
                this.bullet.y -= Gdx.graphics.getDeltaTime() * Bullet.SPEED;
            }
        }
    }
    
    protected boolean isUpBlocked() {
        //System.out.println();
        return isBlocked((int) (bullet.x), (int) (bullet.y + 0.5), collisions);
    }

    protected boolean isDownBlocked() {
        //return isBlocked((int) (bullet.x), (int) (bullet.y - 0.25), collisions);
        if (bullet.y - 0.25 >= 0) {
            return isBlocked((int) bullet.x, (int) (bullet.y - 0.25), collisions);
            // return false;
        }
        return true;
    }

    private boolean isLeftBlocked() {
//        return isBlocked((int) (bullet.x - 0.25), (int) bullet.y, collisions);
        if (bullet.y - 0.45 >= 0) {
            return isBlocked((int) (bullet.x - 0.45), (int) bullet.y, collisions);
            //return false;
        }
        return true;
    }

    private boolean isRightBlocked() {
        //  return isBlocked((int) (bullet.x + 1), (int) bullet.y, collisions);
        return isBlocked((int) (bullet.x + 0.45), (int) bullet.y, collisions);
    }
}
