package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.ArrayList;
import java.util.Random;
import models.BossZombie;
import models.NPC;
import models.Zombie;

public class NPCController {

    protected Random random = new Random();
    protected TiledMapTileLayer collisions;
    protected boolean up = false, right = true, left = false, down = false;
    protected int count = -1;
    protected int randNum = 0;
    protected int incr = 0;
    protected int timer = 0;
    protected boolean collision = false;
    protected int playerX, playerY;
    protected final float RADIUS = 5;
    protected boolean moveRandom = false;
    protected NPC zombie;

    public int shouldMove() {
        return random.nextInt(4);
    }

    protected boolean detectPlayer(NPC zombie) {
        if (((zombie.getX() <= (playerX + RADIUS) && zombie.getX() >= playerX) || (zombie.getX() >= (playerX - RADIUS)
                && zombie.getX() <= playerX)) && ((zombie.getY() <= (playerY + RADIUS) && zombie.getY() >= playerY)
                || (zombie.getY() >= (playerY - RADIUS)
                && zombie.getY() <= playerY))) {
            return true;
        }
        return false;
    }

//    protected void moveToPlayer(NPC zombie) {
//        if (right && zombie.getX() < playerX) {
//
//            zombie.getZombies().setRegion((TextureRegion) zombie.getRight().getKeyFrame(incr));
//            zombie.x += Gdx.graphics.getDeltaTime() * zombie.speed;
//            if (isRightBlocked(zombie)) {
//                System.out.println("right is now blocked");
//                moveRandom = true;
//            }
//
//        } else if (left && zombie.getX() > playerX) {
//
//            zombie.getZombies().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(incr));
//            zombie.x -= Gdx.graphics.getDeltaTime() * zombie.speed;
//
//            if (isLeftBlocked(zombie)) {
//                System.out.println("left is now blocked");
//                moveRandom = true;
//            }
//
//        } else if (up && zombie.getY() < playerY) {
//
//            zombie.getZombies().setRegion((TextureRegion) zombie.getUp().getKeyFrame(incr));
//            zombie.y += Gdx.graphics.getDeltaTime() * zombie.speed;
//
//            if (isUpBlocked(zombie)) {
//                System.out.println("up is now blocked");
//                moveRandom = true;
//            }
//
//        } else if (down && zombie.getY() > playerY) {
//            zombie.getZombies().setRegion((TextureRegion) zombie.getDown().getKeyFrame(incr));
//            zombie.y -= Gdx.graphics.getDeltaTime() * zombie.speed;
//
//            if (isDownBlocked(zombie)) {
//                System.out.println("down is now blocked");
//                moveRandom = true;
//            }
//        }
//    }

//    public boolean isBlocked(int x, int y, TiledMapTileLayer collisionLayer) {
//        return collisionLayer.getCell(x, y).getTile().getProperties().containsKey("blocked");
//    }
//
//    public boolean isUpBlocked(NPC zombie) {
//        //System.out.println();
//        return isBlocked((int) (zombie.x), (int) (zombie.y + 1), collisions);
//    }
//
//    public boolean isDownBlocked(NPC zombie) {
//        //return isBlocked((int) (zombie.x), (int) (zombie.y - 0.25), collisions);
//        if (zombie.y - 0.25 >= 0) {
//            return isBlocked((int) zombie.x, (int) (zombie.y - 0.25), collisions);
//            // return false;
//        }
//        return true;
//    }
//
//    public boolean isLeftBlocked(NPC zombie) {
////        return isBlocked((int) (zombie.x - 0.25), (int) zombie.y, collisions);
//        if (zombie.y - 0.25 >= 0) {
//            return isBlocked((int) (zombie.x - 0.25), (int) zombie.y, collisions);
//            // return false;
//        }
//        return true;
//    }
//
//    public boolean isRightBlocked(NPC zombie) {
//        return isBlocked((int) (zombie.x + 1), (int) zombie.y, collisions);
//    }
//
//        public void updateCollisions() {
//        if (isRightBlocked(zombie)) {
//            right = false;
//        } else {
//            right = true;
//        }
//        if (isLeftBlocked(zombie)) {
//            left = false;
//        } else {
//            left = true;
//        }
//        if (isUpBlocked(zombie)) {
//            up = false;
//        } else {
//            up = true;
//        }
//        if (isDownBlocked(zombie)) {
//            down = false;
//        } else {
//            down = true;
//        }
//    }
}
