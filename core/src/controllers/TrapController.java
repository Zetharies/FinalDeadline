
package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import models.Trap;


public class TrapController extends NPCController {

    private int timer;
    private Trap trap;//assoc object
    private Sound audio;//audio when trap triggered
    private TiledMapTileLayer collisions;//collsion set
    /**
     * trap controller for movement + reset trap
     * @param trap
     * @param thisX
     * @param thisY
     * @param collisions 
     */
    public TrapController(Trap trap, float thisX, float thisY, TiledMapTileLayer collisions) {
        timer = 0;
        audio = Gdx.audio.newSound(Gdx.files.internal("fx/metronome.mp3"));
        this.trap = trap;
        this.collisions = collisions;
    }

    /**
     * update trap movement and collision detection
     * @param delta 
     */
    public void update(float delta) {
        updateTimer(delta);//update timings 
        if (trap.getUsed()) {
            this.trap.setShoot(false);
            if (timer >= 200) {
                this.trap.resetTrap();//reset trap once used - back to starting x y
                timer = 0;
            }
            this.trap.setUsed(false);
        }
        if ( isLeftBlocked() || isRightBlocked() || isUpBlocked() || isDownBlocked()) {
            this.trap.setShoot(false);//stop moving trap
            this.trap.setUsed(true);//get ready to reset
            audio.pause();//stop playing audio
        }
        if (this.trap.getShoot()) {
            this.trap.x -= Gdx.graphics.getDeltaTime() * Trap.SPEED;//move trap from right to left 
            audio.play();//play audio when trap triggerd
        }

    }

    /**
     * update timing for trap reset 
     * @param delta 
     */
    public void updateTimer(float delta) {
        timer += (delta * 100);
    }

    protected boolean isUpBlocked() {
        return isBlocked((int) (trap.x), (int) (trap.y + 0.5), collisions);
    }

    protected boolean isDownBlocked() {
        if (trap.getPosY() - 0.25 >= 0) {
            return isBlocked((int) trap.x, (int) (trap.getPosY() - 0.25), collisions);
        }
        return true;
    }

    private boolean isLeftBlocked() {
        if (trap.getPosY() - 0.45 >= 0) {
            return isBlocked((int) (trap.getPosX() - 0.45), (int) trap.getPosY(), collisions);
        }
        return true;
    }

    private boolean isRightBlocked() {
        return isBlocked((int) (trap.x + 0.45), (int) trap.y, collisions);
    }
}
