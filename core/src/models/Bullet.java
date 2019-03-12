package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Bullet {

    private Texture texture;
    private Sprite sprite;
    public float x, y;
    private  float SPEED = 2.0f;
    private boolean shoot;
    private int timer;
    private String direction;
    private TiledMapTileLayer collisions;
    private int posX, posY;
    private TextureRegion[][] region;
    
    private int rangeTimer;
    private final int RANGE;
    private final float DAMAGE;

    public Bullet(float thisX, float thisY, TiledMapTileLayer collisions, String image, int rows, int columns) {
        texture = new Texture(Gdx.files.internal("sprite/boss/" + image));
        region = TextureRegion.split(texture, texture.getWidth() / columns, texture.getHeight() / rows);
        sprite = new Sprite(region[0][0]);
        this.x = thisX;
        this.y = thisY;
        shoot = false;
        timer = 0;
        direction = "";
        this.collisions = collisions;
        RANGE = 700;
        rangeTimer = 0;
        DAMAGE = 0.15f;

    }

    public void update(float delta) {
        rangeTimer++;
        if (isRightBlocked() || isLeftBlocked() || isUpBlocked() || isDownBlocked() || rangeTimer == RANGE) {
            this.shoot = false;
            rangeTimer = 0;
        } else if (shoot) {
            updateTimer(delta);
            if (this.x < posX + 1.5) {
                this.x += Gdx.graphics.getDeltaTime() * this.SPEED;
            }
            if (this.x > posX + 1.5) {
                this.x -= Gdx.graphics.getDeltaTime() * this.SPEED;
            }
            if (this.y < posY + 0.5) {
                this.y += Gdx.graphics.getDeltaTime() * this.SPEED;
            }
            if (this.y > posY + 1.5) {
                this.y -= Gdx.graphics.getDeltaTime() * this.SPEED;
            }
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
            shoot = false;
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
        if (this.y - 0.25 >= 0) {
            return isBlocked((int) (this.x - 0.50), (int) this.y, collisions);
        }
        return true;
    }

    public boolean isRightBlocked() {
        return isBlocked((int) (this.x), (int) this.y, collisions);
    }

    public float getDamage() {
        return this.DAMAGE;
    }
    
    public void setSpeed(float speed){
        this.SPEED = speed;
    }
}
