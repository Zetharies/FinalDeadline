/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.ArrayList;
import java.util.Random;
import models.BossZombie;
/**
 *
 * @author User
 */
public class BossController {

    private Random random = new Random();
    private BossZombie zombie;
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

    private int attackMethod;
    private int toShoot;

    public BossController(TiledMapTileLayer collisions, BossZombie zombie) {
        this.zombie = zombie;
        this.collisions = collisions;
        moveRandom = false;
        attackMethod = -1;
        toShoot = 0;
    }
    int methodInAction = 0;

    @SuppressWarnings("static-access")
    public void update(float delta) {
        methodInAction++;
        toShoot++;
        if (methodInAction == 60) {
            attackMethod = random.nextInt(6);//0 1 2
            methodInAction = 0;
        }
        //control a zombie - in game screen only update one zombie
        //System.out.println("zombie x and y " + zombie.x + " " + zombie.y);

        updateTimers(delta);
        updateCollisions();
        detectPlayer();
        //keys();

        //below certain value teleport and slowly regain hp but keep if player stays to long near boss
        if (detectPlayer()) {
            //state pattern?
            if (toShoot == 90) {
                zombie.shoot();
                zombie.getBullets().get(zombie.getBullets().size() - 1).setShoot(true);
                zombie.getBullets().get(zombie.getBullets().size() - 1).setSpeed(2.3f + 0.8f);
                toShoot = 0;
            } else if (attackMethod == 1) {
                // zombie.teleport();
            } else {
                zombie.rushPlayer();
                moveToPlayer();
            }
        } else {
            this.zombie.setSpeed(2.3f);
            // randomMovement();
        }
        if (toShoot == 90) {
            toShoot = 0;
        }
        playerTouchingBoss();

    }
    private int energyTimer = 0;
    private boolean startTimer = false;
    private int biteAudioTimer = 0;

    public void playerTouchingBoss() {
        biteAudioTimer++;
        if (((zombie.getX() <= (playerX + 0.5) && zombie.getX() >= playerX) || (zombie.getX() >= (playerX - 0.5)
                && zombie.getX() <= playerX)) && ((zombie.getY() <= (playerY + 0.5) && zombie.getY() >= playerY)
                || (zombie.getY() >= (playerY - 0.5)
                && zombie.getY() <= playerY))) {
            if (biteAudioTimer == 70) {
                zombie.bite(true);
                biteAudioTimer = 0;
            }
            startTimer = true;
            if (this.zombie.getHealth() < 100 && energyTimer == 140) {
                increaseHealth();
                energyTimer = 0;
            }
        } else {
            zombie.bite(false);
        }

        if (energyTimer == 140) {
            energyTimer = 0;
        }
        if (startTimer) {
            energyTimer++;
        }
        if (biteAudioTimer == 150) {
            biteAudioTimer = 0;
        }
    }

    public void increaseHealth() {
        this.zombie.setHealth(this.zombie.getHealth() + 10);
    }

    public void setPlayerMovement(ArrayList<PlayerMovement> movements) {
        this.movements = movements;
    }

    public void setPlayerPosition(int playerX, int playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
    }

    private boolean detectPlayer() {
        if (((zombie.getX() <= (playerX + RADIUS) && zombie.getX() >= playerX) || (zombie.getX() >= (playerX - RADIUS)
                && zombie.getX() <= playerX)) && ((zombie.getY() <= (playerY + RADIUS) && zombie.getY() >= playerY)
                || (zombie.getY() >= (playerY - RADIUS)
                && zombie.getY() <= playerY))) {
            return true;
        }
        return false;
    }
    boolean moveRandom = false;

    private void moveToPlayer() {
        if (right && zombie.getX() < playerX) {

            zombie.getZombies().setRegion((TextureRegion) zombie.getRight().getKeyFrame(incr));
            zombie.x += Gdx.graphics.getDeltaTime() * zombie.speed;
            if (isRightBlocked()) {
                System.out.println("right is now blocked");
                moveRandom = true;
            }

        } else if (left && zombie.getX() > playerX) {

            zombie.getZombies().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(incr));
            zombie.x -= Gdx.graphics.getDeltaTime() * zombie.speed;

            if (isLeftBlocked()) {
                System.out.println("left is now blocked");
                moveRandom = true;
            }

        } else if (up && zombie.getY() < playerY) {

            zombie.getZombies().setRegion((TextureRegion) zombie.getUp().getKeyFrame(incr));
            zombie.y += Gdx.graphics.getDeltaTime() * zombie.speed;

            if (isUpBlocked()) {
                System.out.println("up is now blocked");
                moveRandom = true;
            }

        } else if (down && zombie.getY() > playerY) {
            zombie.getZombies().setRegion((TextureRegion) zombie.getDown().getKeyFrame(incr));
            zombie.y -= Gdx.graphics.getDeltaTime() * zombie.speed;

            if (isDownBlocked()) {
                System.out.println("down is now blocked");
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
    private int testTimer = 0;

    public void updateTimers(float delta) {
        testTimer++;
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
        if (testTimer == 50) {
            doOnce = false;
            testTimer = 0;
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
        return isBlocked((int) (zombie.x), (int) (zombie.y + 1), collisions);
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
        if (zombie.y - 0.25 >= 0) {
            return isBlocked((int) (zombie.x - 0.25), (int) zombie.y, collisions);
            // return false;
        }
        return true;
    }

    public boolean isRightBlocked() {
        return isBlocked((int) (zombie.x + 1), (int) zombie.y, collisions);
    }

    private boolean doOnce = false;

    @SuppressWarnings("static-access")
    public void keys() {
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) && !isLeftBlocked()) {

            zombie.x -= Gdx.graphics.getDeltaTime() * zombie.speed;
            zombie.getZombies().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(10));

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) && !isRightBlocked()) {

            zombie.x += Gdx.graphics.getDeltaTime() * zombie.speed;
            zombie.getZombies().setRegion((TextureRegion) zombie.getRight().getKeyFrame(5));

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) && !isUpBlocked()) {

            zombie.y += Gdx.graphics.getDeltaTime() * zombie.speed;
            zombie.getZombies().setRegion((TextureRegion) zombie.getUp().getKeyFrame(8));

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN) && !isDownBlocked()) {

            zombie.y -= Gdx.graphics.getDeltaTime() * zombie.speed;
            zombie.getZombies().setRegion((TextureRegion) zombie.getDown().getKeyFrame(1));

        }
        if (Gdx.input.isKeyPressed(Input.Keys.B) && !doOnce) {
            zombie.shoot();
            zombie.getBullets().get(zombie.getBullets().size() - 1).setShoot(true);
            doOnce = true;
            //  moveToPlayer();
        }
    }
}
