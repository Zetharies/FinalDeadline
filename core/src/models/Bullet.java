package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import controllers.BulletController;
import controllers.NPCController;

public class Bullet extends NPC {

    private BulletController controller;
    public static float SPEED = 2.0f;
    private boolean shoot;
    private int timer;
    private TiledMapTileLayer collisions;
    private int posX, posY;
    private final float DAMAGE;
    public float x,y;
    public Bullet(float thisX, float thisY, TiledMapTileLayer collisions, String image, int rows, int columns) {
        texture = new Texture(Gdx.files.internal("sprite/boss/" + image));
        region = TextureRegion.split(texture, texture.getWidth() / columns, texture.getHeight() / rows);
        sprite = new Sprite(region[0][0]);
        this.x = thisX;
        this.y = thisY;
        shoot = false;
        timer = 0;
        this.collisions = collisions;
        DAMAGE = 0.15f;
        controller = new BulletController(this);
    }

  
    public Bullet(int x, int y) {
    	this.x = x;
    	this.y= y;
    	DAMAGE = 0.15f;
    }

    public void updateTimer(float delta) {
        timer += (delta * 100);
        if (timer >= 1000) {
            shoot = false;
        }
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

    public float getDamage() {
        return this.DAMAGE;
    }


   
    
    public void setPlayerPosition(int playerX, int playerY) {
       controller.setPlayerPosition(playerX, playerY);
    }
  
    
    public void update(float delta){
        controller.update(delta);
    }
    
    public void setSpeed(float speed){
        SPEED = speed;
    }
    
    public float getSpeed(){
        return SPEED;
    }
}

