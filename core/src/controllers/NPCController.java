package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.ArrayList;
import java.util.Random;
import models.BossZombie;
import models.NPC;
import models.Zombie;

public abstract class NPCController {

    protected Random random = new Random();
    protected TiledMapTileLayer collisions;
    protected boolean up = false, right = true, left = false, down = false;
    protected int count = -1;
    protected int direction = 0;
    protected int incr = 0;
    protected int timer = 0;
    protected boolean collision = false;
    protected int playerX, playerY;
    protected float radius;
    protected boolean moveRandom = false;

    public int shouldMove() {
        return random.nextInt(4);
    }

    /**
     * set player position for player detection
     *
     * @param playerX
     * @param playerY
     */
    public void setPlayerPosition(int playerX, int playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
    }

    protected void moveToPlayer(NPC zombie, double speed) {
        //right must be true meaning up is not blocked - updated in updatecollisions methods
        //if (right && zombie.getX() < playerX || (right && !up) || (right && !down)) {
        if (right && zombie.getX() < playerX) {

            //set the zombie to face right and loop animation
            zombie.getSprite().setRegion((TextureRegion) zombie.getRight().getKeyFrame(incr));
            //increase zombie x coord to move right 
            zombie.x += Gdx.graphics.getDeltaTime() * speed;
            //if right is blocked sprite should bounce off wall and move randomly.
            if (isRightBlocked(zombie) || !right) {
                moveRandom = true;
            }
            //left must be true meaning up is not blocked - updated in updatecollisions methods
        } else if (left && zombie.getX() > playerX) {
            //set the zombie to face left and increment animation
            zombie.getSprite().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(incr));
            //to move left decrement x 
            zombie.x -= Gdx.graphics.getDeltaTime() * speed;

            //detection of left collision tile should move randomly
            if (isLeftBlocked(zombie) || !left) {
                moveRandom = true;
            }
            //up must be true meaning up is not blocked - updated in updatecollisions methods

        } else if (up && zombie.getY() < playerY) {
            //
            zombie.getSprite().setRegion((TextureRegion) zombie.getUp().getKeyFrame(incr));
            zombie.y += Gdx.graphics.getDeltaTime() * speed;

            if (isUpBlocked(zombie) || !up) {
                moveRandom = true;
            }
            //down must be true meaning up is not blocked - updated in updatecollisions methods
        } else if (down && zombie.getY() > playerY) {
            //zombie to face down and increment through the associated aniamtion
            zombie.getSprite().setRegion((TextureRegion) zombie.getDown().getKeyFrame(incr));
            //decrement y coord to move left 
            zombie.y -= Gdx.graphics.getDeltaTime() * speed;

            if (isDownBlocked(zombie) || !down) {
                moveRandom = true;
            }
        }
    }
    
    /**
//     * player detection used with a radius field
//     *
//     * @return true if player detected else return false
//     */
    public boolean detectPlayer(NPC zombie, float radius) {
        if (((zombie.getX() <= (playerX + radius) && zombie.getX() >= playerX) || (zombie.getX() >= (playerX - radius)
                && zombie.getX() <= playerX)) && ((zombie.getY() <= (playerY + radius) && zombie.getY() >= playerY)
                || (zombie.getY() >= (playerY - radius)
                && zombie.getY() <= playerY))) {
            return true;
        }
        return false;
    }
  
     protected void updateCollisions(NPC npc) {
        //is right is blocked set false - cannot go right otherwise true and can go right
        if (isRightBlocked(npc)) {
            right = false;
        } else {
            right = true;

        }
        //if left is blocked set false - cannot go left otherwise true and can go left
        if (isLeftBlocked(npc)) {
            left = false;
        } else {
            left = true;

        }
        //if up is blocked set false - cannot go up otherwise true and can go up
        if (isUpBlocked(npc)) {
            up = false;
        } else {
            up = true;
        }
        //if down is blocked set to false - cannot go down otherwise true and can go down
        if (isDownBlocked(npc)) {
            down = false;
        } else {
            down = true;

        }
    }
     
     protected boolean isBlocked(int x, int y, TiledMapTileLayer collisionLayer) {
        return collisionLayer.getCell(x, y).getTile().getProperties().containsKey("blocked");
    }

    protected boolean isUpBlocked(NPC zombie) {
        //System.out.println();
        return isBlocked((int) (zombie.x), (int) (zombie.y + 0.5), collisions);
    }

    protected boolean isDownBlocked(NPC zombie) {
        //return isBlocked((int) (zombie.x), (int) (zombie.y - 0.25), collisions);
        if (zombie.y - 0.25 >= 0) {
            return isBlocked((int) zombie.x, (int) (zombie.y - 0.25), collisions);
            // return false;
        }
        return true;
    }

    protected boolean isLeftBlocked(NPC zombie) {
//        return isBlocked((int) (zombie.x - 0.25), (int) zombie.y, collisions);
        if (zombie.y - 0.45 >= 0) {
            return isBlocked((int) (zombie.x - 0.45), (int) zombie.y, collisions);
            //return false;
        }
        return true;
    }

    protected boolean isRightBlocked(NPC zombie) {
        //  return isBlocked((int) (zombie.x + 1), (int) zombie.y, collisions);
        return isBlocked((int) (zombie.x + 0.45), (int) zombie.y, collisions);
    }
    
}
