/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 *
 * @author User
 */
public class Trap {

    private Texture texture;
    private Sprite sprite;
    public float x, y;
    private final float SPEED = 8.0f;
    private boolean shoot;
    private int timer;
    private String direction;
    private TiledMapTileLayer collisions;
    private int posX, posY;
    private float startX, startY;
    private int rangeTimer;
    private final int RANGE;
    private final float DAMAGE;

    private boolean used = false;
    private TextureRegion region[][];
    private static final int FRAME_COLS = 2;
    private static final int FRAME_ROWS = 1;

    private Sound audio;

    public Trap(float thisX, float thisY, TiledMapTileLayer collisions) {
//        texture = new Texture(Gdx.files.internal("sprite/boss/bullet.png"));
        texture = new Texture(Gdx.files.internal("sprite/boss/spike-ball.png"));
        region = TextureRegion.split(texture, texture.getWidth() / FRAME_COLS, texture.getHeight() / FRAME_ROWS);
        sprite = new Sprite(region[0][0]);
        this.x = thisX;
        this.y = thisY;
        this.startX = thisX;
        this.startY = thisY;

        shoot = false;
        timer = 0;
        direction = "";
        this.collisions = collisions;
        RANGE = 700;
        rangeTimer = 0;
        DAMAGE = 0.15f;

        audio = Gdx.audio.newSound(Gdx.files.internal("fx/metronome.mp3"));
    }

    public boolean getUsed() {
        return used;
    }

    public void update(float delta) {
        updateTimer(delta);
        if (used) {
            this.shoot = false;
            if (timer >= 200) {
                resetTrap();
                timer = 0;
            }
            used = false;
        }
        if (isRightBlocked() || isLeftBlocked() || isUpBlocked() || isDownBlocked()) {
            this.shoot = false;
            this.used = true;
            audio.pause();
        }
        if (shoot) {
            //this.y -= Gdx.graphics.getDeltaTime() * this.SPEED;
            this.x -= Gdx.graphics.getDeltaTime() * this.SPEED;
            audio.play();
        }

    }

    public void setPosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void setShotDirection(String direction) {
        this.direction = direction;
    }

    public void updateTimer(float delta) {
        timer += (delta * 100);
        if (timer >= 1000) {
            // shoot = false;
        }
    }

    public TextureRegion getSprite() {
        return sprite;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public boolean getShoot() {
        return shoot;
    }

    public boolean isBlocked(int x, int y, TiledMapTileLayer collisionLayer) {
        return collisionLayer.getCell(x, y).getTile().getProperties().containsKey("blocked");
    }

    public boolean isUpBlocked() {
        return isBlocked((int) (this.x), (int) (this.y), collisions);
    }

    public boolean isDownBlocked() {
        if (this.y - 0.25 >= 0) {
            return isBlocked((int) this.x, (int) (this.y), collisions);
        }
        return true;
    }

    public boolean isLeftBlocked() {
        if (this.y - 0.60 >= 0) {
            return isBlocked((int) (this.x - 0.60), (int) this.y, collisions);
        }
        return true;
    }

    public boolean isRightBlocked() {
        return isBlocked((int) (this.x), (int) this.y, collisions);
    }

    public float getDamage() {
        return this.DAMAGE;
    }

    public void resetTrap() {
        this.x = startX;
        this.y = startY;
    }
}
