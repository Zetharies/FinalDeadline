package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.Random;
import models.Zombie;

public class ZombieController {

    private Random random = new Random();
    private Zombie zombie;
    private TiledMapTileLayer collisions;
    private boolean up = false, right = true, left = false, down = false;
    private int count = -1;
    private int randNum = 0;
    private int incr = 0;
    private int timer = 0;
    private boolean collision = false;

    public ZombieController(TiledMapTileLayer collisions, Zombie zombie) {
        this.zombie = zombie;
        this.collisions = collisions;

    }

    public void update(float delta) {
        //control a zombie - in game screen only update one zombie
        updateTimers(delta);
        updateCollisions();
        if (right && randNum == 0) {
            if (!isRightBlocked()) {
                zombie.getZombies().setRegion((TextureRegion) zombie.getRight().getKeyFrame(incr));
                zombie.x += Gdx.graphics.getDeltaTime() * zombie.speed;
                collision = false;
            }
            if (isRightBlocked()) {
                collision = true;
                //changing randnum values bounces sprite in the opposite direction
                randNum = 1;

            }
        } else if (left && randNum == 1) {
            if (!isLeftBlocked()) {
                zombie.getZombies().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(incr));
                zombie.x -= Gdx.graphics.getDeltaTime() * zombie.speed;
                collision = false;
            }
            if (isLeftBlocked()) {
                collision = true;
                randNum = 0;
            }
        } else if (up && randNum == 2) {
            if (!isUpBlocked()) {
                zombie.getZombies().setRegion((TextureRegion) zombie.getUp().getKeyFrame(incr));
                zombie.y += Gdx.graphics.getDeltaTime() * zombie.speed;
                collision = false;
            }
            if (isUpBlocked()) {
                collision = true;
                randNum = 3;
            }
        } else if (down && randNum == 3) {
            if (!isDownBlocked()) {
                zombie.getZombies().setRegion((TextureRegion) zombie.getDown().getKeyFrame(incr));
                zombie.y -= Gdx.graphics.getDeltaTime() * zombie.speed;
                collision = false;
            }
            if (isDownBlocked()) {
                collision = true;
                randNum = 2;
            }

        }

    }

    public void updateTimers(float delta) {
        timer += (delta * 100);
        count++;

        //counter to potentially change direction
        if ((count >= 80 || count == -1) && !collision) {
            //direction to move based on rng
            randNum = shouldMove();
            count = 0;
        }
        //walking animation timer
        if (timer >= 10) {
            timer = 0;
            incr++;
        }
        //increments through walking frames
        if (incr > 2) {
            incr = 0;
        }
    }

    public void updateCollisions() {
        if (isRightBlocked()) {
            right = false;
        } else {
            right = true;
        }
        if (isLeftBlocked()) {
            left = false;
        } else {
            left = true;
        }
        if (isUpBlocked()) {
            up = false;
        } else {
            up = true;
        }
        if (isDownBlocked()) {
            down = false;
        } else {
            down = true;
        }
    }

    public int shouldMove() {
        return random.nextInt(4);
    }

    public boolean isBlocked(int x, int y, TiledMapTileLayer collisionLayer) {
        return collisionLayer.getCell(x, y).getTile().getProperties().containsKey("blocked");
    }

    public boolean isUpBlocked() {
        return isBlocked((int) zombie.x, (int) (zombie.y + 1), collisions);
    }

    public boolean isDownBlocked() {
        return isBlocked((int) (zombie.x), (int) (zombie.y - 1), collisions);
    }

    public boolean isLeftBlocked() {
        return isBlocked((int) (zombie.x - 1), (int) zombie.y, collisions);
    }

    public boolean isRightBlocked() {
        return isBlocked((int) (zombie.x + 1), (int) zombie.y, collisions);

    }
}
