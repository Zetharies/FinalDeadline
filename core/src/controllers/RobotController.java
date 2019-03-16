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
    private final float RADIUS = 10;
    private ArrayList<PlayerMovement> movements;
    private static String facing;

    public RobotController(TiledMapTileLayer collisions, Robot robot) {
        facing = "right";
        this.robot = robot;
        this.collisions = collisions;

    }
    int toShoot = 0;
    boolean playAudio = false;

    @SuppressWarnings("static-access")
    public void update(float delta) {
        toShoot++;
        updateTimers(delta);
        updateCollisions();
        detectPlayer();
        keys();
        if (detectPlayer()) {
            moveToPlayer();
            if (toShoot == 50) {
                System.out.println("shoot");
                robot.shoot();
                robot.getBullets().get(robot.getBullets().size() - 1).setShotDirection(facing);
                robot.getBullets().get(robot.getBullets().size() - 1).setShoot(true);
                toShoot = 0;
                playAudio = true;
            }
        }
        if (playAudio) {
            robot.playAudio();
            playAudio = false;
        }
        if (toShoot == 50) {
            toShoot = 0;
        }
    }

    private void moveToPlayer() {
        if (robot.getY() < playerY) {

            robot.getSprite().setRegion((TextureRegion) robot.getUp().getKeyFrame(incr));
            robot.y += Gdx.graphics.getDeltaTime() * robot.speed;

            if (isUpBlocked()) {
                System.out.println("up is now blocked");
            }

        }
        if (robot.getY() > playerY) {
            robot.getSprite().setRegion((TextureRegion) robot.getDown().getKeyFrame(incr));
            robot.y -= Gdx.graphics.getDeltaTime() * robot.speed;

            if (isDownBlocked()) {
            }
        }
        if (robot.getX() > playerX) {
            robot.getSprite().setRegion((TextureRegion) robot.getLeft().getKeyFrame(incr));
            robot.x -= Gdx.graphics.getDeltaTime() * robot.speed;
            facing = "left";

        }
        if (robot.getX() < playerX) {
            System.out.println("need to go right");
            robot.getSprite().setRegion((TextureRegion) robot.getRight().getKeyFrame(incr));
            robot.x += Gdx.graphics.getDeltaTime() * robot.speed;
            facing = "right";

        }

        if ((int) robot.getY() == (int) playerY) {
          //  robot.getSprite().setRegion((TextureRegion) robot.getLeft().getKeyFrame(0));
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
        if (robot.y - 0.25 >= 0) {
            return isBlocked((int) robot.x, (int) (robot.y - 0.25), collisions);
        }
        return true;
    }

    public boolean isLeftBlocked() {
        if (robot.y - 0.25 >= 0) {
            return isBlocked((int) (robot.x - 0.25), (int) robot.y, collisions);
        }
        return true;
    }

    public boolean isRightBlocked() {
        return isBlocked((int) (robot.x + 1), (int) robot.y, collisions);
    }

    @SuppressWarnings("static-access")
    public void keys() {
        if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {

            robot.x -= Gdx.graphics.getDeltaTime() * robot.speed;
            robot.getSprite().setRegion((TextureRegion) robot.getLeft().getKeyFrame(incr));
            facing = "left";

        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {

            robot.x += Gdx.graphics.getDeltaTime() * robot.speed;
            robot.getSprite().setRegion((TextureRegion) robot.getRight().getKeyFrame(incr));
            facing = "right";

        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_UP)) {

            robot.y += Gdx.graphics.getDeltaTime() * robot.speed;
            robot.getSprite().setRegion((TextureRegion) robot.getUp().getKeyFrame(incr));
            facing = "up";

        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {

            robot.y -= Gdx.graphics.getDeltaTime() * robot.speed;
            robot.getSprite().setRegion((TextureRegion) robot.getDown().getKeyFrame(incr));
            facing = "down";

        }
        if (Gdx.input.isKeyPressed(Keys.B)) {
          //  robot.kill(true);
//            robot.shoot();
//            robot.getBullets().get(robot.getBullets().size() - 1).setShotDirection(facing);
//            robot.getBullets().get(robot.getBullets().size() - 1).setShoot(true);
//            moveToPlayer();
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
