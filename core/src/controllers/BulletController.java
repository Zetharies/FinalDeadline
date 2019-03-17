/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.badlogic.gdx.Gdx;
import models.Bullet;


public class BulletController extends NPCController{

    private Bullet bullet;//assoc bullet object
    private int rangeTimer;//timer to detect when bullet has reached range
    private final int RANGE = 130;//range of bullet

    /**
     * controller for bullet object
     * @param bullet 
     */
    public BulletController(Bullet bullet) {
        this.bullet = bullet;
        rangeTimer = 0;
    }

    public void update(float delta) {
        rangeTimer++;//update bullet distance
        //delete bullet if hit collison or met ranged
        if (rangeTimer == RANGE) {
            bullet.setShoot(false);
            rangeTimer = 0;
        } else {
            //update bullet timings
            bullet.updateTimer(delta);
            //make bullet go to p x y - follows player to increase difficulty
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
        return isBlocked((int) (bullet.x), (int) (bullet.y + 0.5), collisions);
    }

    protected boolean isDownBlocked() {
        if (bullet.y - 0.25 >= 0) {
            return isBlocked((int) bullet.x, (int) (bullet.y - 0.25), collisions);
        }
        return true;
    }

    private boolean isLeftBlocked() {
        if (bullet.y - 0.45 >= 0) {
            return isBlocked((int) (bullet.x - 0.45), (int) bullet.y, collisions);
        }
        return true;
    }

    private boolean isRightBlocked() {
        return isBlocked((int) (bullet.x + 0.45), (int) bullet.y, collisions);
    }
}
