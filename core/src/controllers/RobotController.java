package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.ArrayList;
import java.util.Random;
import models.Robot;

public class RobotController {

    private Random random = new Random();
    private Robot robot;
    private TiledMapTileLayer collisions;
    private boolean up = false, right = true, left = false, down = false;
    private int count = -1;
    private int randNum = 0;
    private int incr = 0;
    private int timer = 0;
    private boolean collision = false;
    private int playerX, playerY;
    private final float RADIUS = 5;
    private ArrayList<PlayerMovement> movements;
    private static String facing;

    public RobotController(TiledMapTileLayer collisions, Robot robot) {
        facing = "right";
        this.robot = robot;
        this.collisions = collisions;

    }
    int toShoot = 0;

    @SuppressWarnings("static-access")
    public void update(float delta) {
        toShoot++;
        updateTimers(delta);
        updateCollisions();
        detectPlayer();
        if (detectPlayer()) {
            moveToPlayer();
            if (toShoot == 90) {
                System.out.println("should shoot");
                robot.shoot();
                robot.getBullets().get(robot.getBullets().size() - 1).setShotDirection(facing);
                robot.getBullets().get(robot.getBullets().size() - 1).setShoot(true);
                toShoot = 0;
            }
        }
    }

    private void moveToPlayer() {
//        if (right && robot.getX() < playerX || (right && !up) || (right && !down)) {
//
//            robot.getSprite().setRegion((TextureRegion) robot.getRight().getKeyFrame(incr));
//            robot.x += Gdx.graphics.getDeltaTime() * robot.speed;
//            if (isRightBlocked()) {
//                System.out.println("right is now blocked");
//            }
//
//        }
//        if (left && robot.getX() > playerX) {
//
//            robot.getSprite().setRegion((TextureRegion) robot.getLeft().getKeyFrame(incr));
//            robot.x -= Gdx.graphics.getDeltaTime() * robot.speed;
//
//            if (isLeftBlocked()) {
//                System.out.println("left is now blocked");
//            }
//
//        }
         if (up && robot.getY() < playerY) {

            robot.getSprite().setRegion((TextureRegion) robot.getUp().getKeyFrame(incr));
            robot.y += Gdx.graphics.getDeltaTime() * robot.speed;

            if (isUpBlocked()) {
                System.out.println("up is now blocked");
            }

        } 
        if (down && robot.getY() > playerY) {
            robot.getSprite().setRegion((TextureRegion) robot.getDown().getKeyFrame(incr));
            robot.y -= Gdx.graphics.getDeltaTime() * robot.speed;

            if (isDownBlocked()) {
                System.out.println("down is now blocked");
            }
        }
        
        if((int)robot.getY() == (int)playerY){
            System.out.println("testing");
            robot.getSprite().setRegion((TextureRegion) robot.getLeft().getKeyFrame(0));
        }
    }
    private int count2 = 0;

    public void updateTimers(float delta) {
        timer += (delta * 100);
        count++;
        count2++;
        //counter to potentially change direction
        if ((count >= 80 || count == -1) && !collision) {
            //direction to move based on rng
            randNum = shouldMove();
            count = 0;
        }
        if (count2 >= 40) {
            count2 = 0;
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
        //System.out.println();
        return isBlocked((int) (robot.x), (int) (robot.y + 1), collisions);
    }

    public boolean isDownBlocked() {
        //return isBlocked((int) (robot.x), (int) (robot.y - 0.25), collisions);
        if (robot.y - 0.25 >= 0) {
            return isBlocked((int) robot.x, (int) (robot.y - 0.25), collisions);
            // return false;
        }
        return true;
    }

    public boolean isLeftBlocked() {
//        return isBlocked((int) (robot.x - 0.25), (int) robot.y, collisions);
        if (robot.y - 0.25 >= 0) {
            return isBlocked((int) (robot.x - 0.25), (int) robot.y, collisions);
            // return false;
        }
        return true;
    }

    public boolean isRightBlocked() {
        return isBlocked((int) (robot.x + 1), (int) robot.y, collisions);
    }

    @SuppressWarnings("static-access")
    public void keys() {
        if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT) && !isLeftBlocked()) {

            robot.x -= Gdx.graphics.getDeltaTime() * robot.speed;
            robot.getSprite().setRegion((TextureRegion) robot.getLeft().getKeyFrame(10));
            facing = "left";

        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT) && !isRightBlocked()) {

            robot.x += Gdx.graphics.getDeltaTime() * robot.speed;
            robot.getSprite().setRegion((TextureRegion) robot.getRight().getKeyFrame(5));
            facing = "right";

        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_UP) && !isUpBlocked()) {

            robot.y += Gdx.graphics.getDeltaTime() * robot.speed;
            robot.getSprite().setRegion((TextureRegion) robot.getUp().getKeyFrame(8));
            facing = "up";

        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN) && !isDownBlocked()) {

            robot.y -= Gdx.graphics.getDeltaTime() * robot.speed;
            robot.getSprite().setRegion((TextureRegion) robot.getDown().getKeyFrame(1));
            facing = "down";

        }
        if (Gdx.input.isKeyPressed(Keys.B)) {
            robot.shoot();
            robot.getBullets().get(robot.getBullets().size() - 1).setShotDirection(facing);
            robot.getBullets().get(robot.getBullets().size() - 1).setShoot(true);
            moveToPlayer();
        }
    }

    public static String getFacing() {
        return facing;
    }

    private boolean detectPlayer() {
        if (((robot.getX() <= (playerX + RADIUS) && robot.getX() >= playerX) || (robot.getX() >= (playerX - RADIUS)
                && robot.getX() <= playerX)) && ((robot.getY() <= (playerY + RADIUS) && robot.getY() >= playerY)
                || (robot.getY() >= (playerY - RADIUS)
                && robot.getY() <= playerY))) {
            return true;
        }
        return false;
    }

    public void setPlayerPosition(int playerX, int playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
    }

}
