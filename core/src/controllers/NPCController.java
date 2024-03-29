package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.Random;
import models.NPC;

/**
 * NPCController class that allows us to control NPCs
 * @author Team 2f
 *
 */
public abstract class NPCController {

    protected Random random = new Random();
    protected TiledMapTileLayer collisions;//npcs use collision set 
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

    protected void moveToPlayer(NPC npc, double speed) {
        //right must be true meaning up is not blocked - updated in updatecollisions methods
        if (right && npc.getX() < playerX) {
            //set the zombie to face right and loop animation
            npc.getSprite().setRegion((TextureRegion) npc.getRight().getKeyFrame(incr));
            //increase zombie x coord to move right 
            npc.x += Gdx.graphics.getDeltaTime() * speed;
            //if right is blocked sprite should bounce off wall and move randomly.
            if (isRightBlocked(npc) || !right) {
                moveRandom = true;
            }
            //left must be true meaning up is not blocked - updated in updatecollisions methods
        } else if (left && npc.getX() > playerX) {
            //set the zombie to face left and increment animation
            npc.getSprite().setRegion((TextureRegion) npc.getLeft().getKeyFrame(incr));
            //to move left decrement x 
            npc.x -= Gdx.graphics.getDeltaTime() * speed;

            //detection of left collision tile should move randomly
            if (isLeftBlocked(npc) || !left) {
                moveRandom = true;
            }
            //up must be true meaning up is not blocked - updated in updatecollisions methods

        } else if (up && npc.getY() < playerY) {
            //
            npc.getSprite().setRegion((TextureRegion) npc.getUp().getKeyFrame(incr));
            npc.y += Gdx.graphics.getDeltaTime() * speed;

            if (isUpBlocked(npc) || !up) {
                moveRandom = true;
            }
            //down must be true meaning up is not blocked - updated in updatecollisions methods
        } else if (down && npc.getY() > playerY) {
            //zombie to face down and increment through the associated aniamtion
            npc.getSprite().setRegion((TextureRegion) npc.getDown().getKeyFrame(incr));
            //decrement y coord to move left 
            npc.y -= Gdx.graphics.getDeltaTime() * speed;

            if (isDownBlocked(npc) || !down) {
                moveRandom = true;
            }
        }
    }

    /**
     * player detection used with a radius field
     *
     * @return true if player detected else return false
     */
    public boolean detectPlayer(NPC zombie, float radius) {
        if (((zombie.getX() <= (playerX + radius) && zombie.getX() >= playerX) || (zombie.getX() >= (playerX - radius)
                && zombie.getX() <= playerX)) && ((zombie.getY() <= (playerY + radius) && zombie.getY() >= playerY)
                || (zombie.getY() >= (playerY - radius)
                && zombie.getY() <= playerY))) {
            return true;
        }
        return false;
    }

    /**
     * checks collisions to update up down left right for npcs
     * @param npc 
     */
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

    /**
     * Check if a layer is blocked
     * @param x
     * @param y
     * @param collisionLayer
     * @return
     */
    protected boolean isBlocked(int x, int y, TiledMapTileLayer collisionLayer) {
        return collisionLayer.getCell(x, y).getTile().getProperties().containsKey("blocked");
    }

    /**
     * Check if the above layer is a collision
     * @param npc
     * @return if there is a collision
     */
    protected boolean isUpBlocked(NPC npc) {
        return isBlocked((int) (npc.x), (int) (npc.y + 0.5), collisions);
    }

    /**
     * Check if the below layer is a collision
     * @param npc
     * @return if there is a collision below
     */
    protected boolean isDownBlocked(NPC npc) {
        //return isBlocked((int) (zombie.x), (int) (zombie.y - 0.25), collisions);
        if (npc.y - 0.25 >= 0) {
            return isBlocked((int) npc.x, (int) (npc.y - 0.25), collisions);
            // return false;
        }
        return true;
    }

    /**
     * Check if the left layer is a collision
     * @param npc
     * @return if there is a collision towards the left
     */
    protected boolean isLeftBlocked(NPC npc) {
//        return isBlocked((int) (zombie.x - 0.25), (int) zombie.y, collisions);
        if (npc.y - 0.45 >= 0) {
            return isBlocked((int) (npc.x - 0.45), (int) npc.y, collisions);
            //return false;
        }
        return true;
    }

    /**
     * Check if the right layer is a collision
     * @param npc
     * @return if there is a collision towards the right
     */
    protected boolean isRightBlocked(NPC npc) {
        //  return isBlocked((int) (zombie.x + 1), (int) zombie.y, collisions);
        return isBlocked((int) (npc.x + 0.45), (int) npc.y, collisions);
    }

}
