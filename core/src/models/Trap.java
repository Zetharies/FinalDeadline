/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import controllers.TrapController;

/**
 *
 * @author User
 */
public class Trap extends NPC {

    private TrapController controller;
    public static final float SPEED = 8.0f;
    private boolean shoot;
    public float x, y;
    private float startX, startY;
    private int posX, posY;
    private final float DAMAGE;

    private boolean used = false;
    private static final int FRAME_COLS = 2;
    private static final int FRAME_ROWS = 1;

    public Trap(float thisX, float thisY, TiledMapTileLayer collisions) {
        createSprite(FRAME_COLS,FRAME_ROWS,"sprite/boss/spike-ball.png",false);
        this.x = thisX;
        this.y = thisY;
        this.startX = thisX;
        this.startY = thisY;
        shoot = false;
        DAMAGE = 0.15f;
        controller = new TrapController(this,thisX,thisY,collisions);
    }

    public boolean getUsed() {
        return used;
    }

    public TextureRegion getSprite() {
        return sprite;
    }

    public float getPosX() {
        return x;
    }

    public float getPosY() {
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

    public float getDamage() {
        return this.DAMAGE;
    }

    public void resetTrap() {
        this.x = startX;
        this.y = startY;
    }
    
    public void update(float delta){
        controller.update(delta);
    }
    
    public void setPlayerPosition(int playerX, int playerY) {
       controller.setPlayerPosition(playerX, playerY);
    }
    
   
}
