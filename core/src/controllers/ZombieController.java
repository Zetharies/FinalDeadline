package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.GameSettings;
import java.util.ArrayList;
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
    private int playerX, playerY;
    private final float RADIUS = 5;
    private ArrayList<PlayerMovement> movements;

    public ZombieController(TiledMapTileLayer collisions, Zombie zombie) {
        this.zombie = zombie;
        this.collisions = collisions;
        moveRandom = false;
    }

    //if any thing is blocked move randomly and then set a timer before it can check for the player
    @SuppressWarnings("static-access")
    private int testTimer = 0;
    private boolean setTimer = false;

    public void update(float delta) {
        //control a zombie - in game screen only update one zombie
        //System.out.println("zombie x and y " + zombie.x + " " + zombie.y);
        //keys();

        updateTimers(delta);
        updateCollisions();
        if (detectPlayer() && moveRandom) {
        }
        

        if (detectPlayer() && !up) {
            //System.out.println("blocked up");
            setTimer = true;
        }else if (detectPlayer() && !down) {
            //System.out.println("blocked up");
            setTimer = true;
        }
        
        if (setTimer) {
            testTimer++;
        }
        if (testTimer == 40) {
            setTimer = false;
            testTimer = 0;
        }

        if (detectPlayer() && !moveRandom && !setTimer) {
            moveToPlayer();
        } else {
            randomMovement();
        }
    }

    public void setPlayerMovement(ArrayList<PlayerMovement> movements) {
        this.movements = movements;
    }

    public void setPlayerPosition(int playerX, int playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
    }

    private boolean detectPlayer() { //if a player's position is in a radius of a zombie the zombie will detect the player
        if (((zombie.getX() <= (playerX + RADIUS) && zombie.getX() >= playerX) || (zombie.getX() >= (playerX - RADIUS)
                && zombie.getX() <= playerX)) && ((zombie.getY() <= (playerY + RADIUS) && zombie.getY() >= playerY)
                || (zombie.getY() >= (playerY - RADIUS)
                && zombie.getY() <= playerY))) {
            return true;
        }
        return false;
    }
    boolean moveRandom = false;

    private void moveToPlayer() {//once the zombie has detected the player, it will be moving towards the player's position
        if (right && zombie.getX() < playerX || (right && !up) || (right && !down)) {

            zombie.getZombies().setRegion((TextureRegion) zombie.getRight().getKeyFrame(incr));
            zombie.x += Gdx.graphics.getDeltaTime() * zombie.speed;
            if (isRightBlocked()) {
                //System.out.println("right is now blocked");
                moveRandom = true;
            }

        } else if (left && zombie.getX() > playerX) {

            zombie.getZombies().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(incr));
            zombie.x -= Gdx.graphics.getDeltaTime() * zombie.speed;

            if (isLeftBlocked()) {
                //System.out.println("left is now blocked");
                moveRandom = true;
            }

        } else if (up && zombie.getY() < playerY) {

            zombie.getZombies().setRegion((TextureRegion) zombie.getUp().getKeyFrame(incr));
            zombie.y += Gdx.graphics.getDeltaTime() * zombie.speed;

            if (isUpBlocked()) {
                //System.out.println("up is now blocked");
                moveRandom = true;
            }

        } else if (down && zombie.getY() > playerY) {
            zombie.getZombies().setRegion((TextureRegion) zombie.getDown().getKeyFrame(incr));
            zombie.y -= Gdx.graphics.getDeltaTime() * zombie.speed;

            if (isDownBlocked()) {
                //System.out.println("down is now blocked");
                moveRandom = true;
            }
        }
    }

    private void randomMovement() {
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
        if (count2 >= 80) {
            moveRandom = false;
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
        return isBlocked((int) (zombie.x), (int) (zombie.y + 0.5), collisions);
    }

    public boolean isDownBlocked() {
        //return isBlocked((int) (zombie.x), (int) (zombie.y - 0.25), collisions);
        if (zombie.y - 0.25 >= 0) {
            return isBlocked((int) zombie.x, (int) (zombie.y - 0.25), collisions);
            // return false;
        }
        return true;
    }

    public boolean isLeftBlocked() {
//        return isBlocked((int) (zombie.x - 0.25), (int) zombie.y, collisions);
        if (zombie.y - 0.45 >= 0) {
            return isBlocked((int) (zombie.x - 0.45), (int) zombie.y, collisions);
            //return false;
        }
           return true;
    }

    public boolean isRightBlocked() {
        //  return isBlocked((int) (zombie.x + 1), (int) zombie.y, collisions);
          return isBlocked((int) (zombie.x + 0.45), (int) zombie.y, collisions);

    }

    @SuppressWarnings("static-access")
    public void keys() {
        if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT) && !isLeftBlocked()) {

            zombie.x -= Gdx.graphics.getDeltaTime() * zombie.speed;
            zombie.getZombies().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(incr));

        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT) && !isRightBlocked()) {

            zombie.x += Gdx.graphics.getDeltaTime() * zombie.speed;
            zombie.getZombies().setRegion((TextureRegion) zombie.getRight().getKeyFrame(incr));
            System.out.println("going right");

        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_UP) && !isUpBlocked()) {

            zombie.y += Gdx.graphics.getDeltaTime() * zombie.speed;
            zombie.getZombies().setRegion((TextureRegion) zombie.getUp().getKeyFrame(incr));

        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN) && !isDownBlocked()) {

            zombie.y -= Gdx.graphics.getDeltaTime() * zombie.speed;
            zombie.getZombies().setRegion((TextureRegion) zombie.getDown().getKeyFrame(incr));

        }
    }
}
