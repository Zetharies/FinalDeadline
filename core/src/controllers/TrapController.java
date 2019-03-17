/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import models.Trap;

/**
 *
 * @author User
 */
public class TrapController extends NPCController {

    private int timer;

    private Trap trap;
    private Sound audio;
    private TiledMapTileLayer collisions;
    public TrapController(Trap trap, float thisX, float thisY, TiledMapTileLayer collisions) {
        timer = 0;
        audio = Gdx.audio.newSound(Gdx.files.internal("fx/metronome.mp3"));
        this.trap = trap;
        this.collisions = collisions;
    }

    public void update(float delta) {
        updateTimer(delta);
        if (trap.getUsed()) {
            this.trap.setShoot(false);
            if (timer >= 200) {
                this.trap.resetTrap();
                timer = 0;
            }
            this.trap.setUsed(false);
        }
        if ( isLeftBlocked() || isRightBlocked() || isUpBlocked() || isDownBlocked()) {
            this.trap.setShoot(false);
            this.trap.setUsed(true);
            audio.pause();
        }
        if (this.trap.getShoot()) {
            //this.y -= Gdx.graphics.getDeltaTime() * this.SPEED;
            this.trap.x -= Gdx.graphics.getDeltaTime() * Trap.SPEED;
            audio.play();
        }

    }

    public void updateTimer(float delta) {
        timer += (delta * 100);
        if (timer >= 1000) {
            // shoot = false;
        }
    }

    protected boolean isUpBlocked() {
        //System.out.println();
        return isBlocked((int) (trap.x), (int) (trap.y + 0.5), collisions);
    }

    protected boolean isDownBlocked() {
        //return isBlocked((int) (trap.x), (int) (trap.y - 0.25), collisions);
        if (trap.getPosY() - 0.25 >= 0) {
            return isBlocked((int) trap.x, (int) (trap.getPosY() - 0.25), collisions);
            // return false;
        }
        return true;
    }

    private boolean isLeftBlocked() {
//        return isBlocked((int) (trap.x - 0.25), (int) trap.y, collisions);
        if (trap.getPosY() - 0.45 >= 0) {
            return isBlocked((int) (trap.getPosX() - 0.45), (int) trap.getPosY(), collisions);
            //return false;
        }
        return true;
    }

    private boolean isRightBlocked() {
        //  return isBlocked((int) (trap.x + 1), (int) trap.y, collisions);
        return isBlocked((int) (trap.x + 0.45), (int) trap.y, collisions);
    }
}
