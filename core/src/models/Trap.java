package models;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import controllers.TrapController;

/**
 *
 * @author Team 2
 */
public class Trap extends NPC { //trap is an NPC

    private TrapController controller;
    public static final float SPEED = 8.0f; //trap speed is set at 8
    private boolean shoot;
    public float x, y;
    private float startX, startY;
    private int posX, posY;
    private final float DAMAGE;

    private boolean used = false;
    private static final int FRAME_COLS = 2;
    private static final int FRAME_ROWS = 1;

    public Trap(float thisX, float thisY, TiledMapTileLayer collisions) {
        createSprite(FRAME_COLS,FRAME_ROWS,"sprite/boss/spike-ball.png",false); //creates sprite for traps
        this.x = thisX;
        this.y = thisY;
        this.startX = thisX;
        this.startY = thisY;
        shoot = false;
        DAMAGE = 0.15f; //trap does 0.15 damage
        controller = new TrapController(this,thisX,thisY,collisions);
    }

    public Trap(int x, int y) {
    	this.x = x;
    	this.y = y;
    	DAMAGE = 0.15f;
    }
    public boolean getUsed() { //when the trap gets used
        return used;
    }

    public TextureRegion getSprite() { // gets the sprite for each trap
        return sprite;
    }

    public float getPosX() { //x co-ordinate of trap
        return x;
    }

    public float getPosY() {//y co-ordinate of trap
        return y;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public boolean getShoot() {
        return shoot;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public float getDamage() { //returns the damage the trap does
        return this.DAMAGE;
    }

    public void resetTrap() { //resets the trap
        this.x = startX;
        this.y = startY;
    }
    
    public void update(float delta){
        controller.update(delta);    }
    
    public void setPlayerPosition(int playerX, int playerY) {
       controller.setPlayerPosition(playerX, playerY);
    }
    
    public void stopAudio(){ //stops the trap sound effects
        controller.stopAudio();
    }
    
    public void setX(int x) {
    	this.x = x;
    }
    public void setY(int y) {
    	this.y = y;
    }
    public float getTY() {
    	return this.y;
    }
    public float getTX() {
    	return this.x;
    }
}
