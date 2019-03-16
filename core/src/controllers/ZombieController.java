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
    private final float RADIUS = 7;
    private ArrayList<PlayerMovement> movements;
    private boolean moveRandom = false;

    /**
     * constructor to initialise zombiecontroller used - to control movement and
     * abilities of zombie
     *
     * @param collisions collision set to detect collisions
     * @param zombie associated zombie object
     */
    public ZombieController(TiledMapTileLayer collisions, Zombie zombie) {
        this.zombie = zombie;
        this.collisions = collisions;
        moveRandom = false;
    }

    //if any thing is blocked move randomly and then set a timer before it can check for the player
    @SuppressWarnings("static-access")
    private int testTimer = 0;
    private boolean setTimer = false;

    /**
     * update linked zombie timer,collisions,position and player attack
     *
     * @param delta
     */
    ArrayList<Zombie> zombies;

    public void update(float delta, ArrayList<Zombie> zombies) {
        this.zombies = zombies;
        //keys(delta);
        // timers for animations random movement and abilities
        updateTimers(delta);
        updateCollisions();
        if (detectPlayer() && moveRandom) {
        }

        if (detectPlayer() && !up) {
            setTimer = true;
        } else if (detectPlayer() && !down) {
            setTimer = true;
        }

        if (setTimer) {
            testTimer++;
        }//40
        if (testTimer == 100) {
            setTimer = false;
            testTimer = 0;
        }
        boolean check = true;
        
       
            for (int j = 0; j < zombies.size(); j++) {
                if (zombie != zombies.get(j)) {
                    if ((int) zombies.get(j).x == (int) zombie.x && (int) zombies.get(j).y == (int) zombie.y - 1) {
                        check = false;
                        zombie.setDown(false);
                        break;
                    } else if ((int) zombies.get(j).x == (int) zombie.x && (int) zombies.get(j).y == (int) zombie.y + 1) {
                        check = false;
                        zombie.setUp(false);

                        break;

                    } else if ((int) zombies.get(j).x == (int) zombie.x + 1 && (int) zombies.get(j).y == (int) zombie.y) {
                        check = false;
                        zombie.setRight(false);
                        break;

                    } else if ((int) zombies.get(j).x == (int) zombie.x - 1 && (int) zombies.get(j).y == (int) zombie.y) {
                        check = false;
                        zombie.setLeft(false);

                        break;

                    } else {
                        check = true;
                    }
                }
            }
            if (check) {

            }
        
       
        //!setTimer
        if (detectPlayer() && !moveRandom && !setTimer) {
            moveToPlayer();
        } else {
            randomMovement();
        }

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

    /**
     * player detection used with a radius field
     *
     * @return true if player detected else return false
     */
    public boolean detectPlayer() {
        if (((zombie.getX() <= (playerX + RADIUS) && zombie.getX() >= playerX) || (zombie.getX() >= (playerX - RADIUS)
                && zombie.getX() <= playerX)) && ((zombie.getY() <= (playerY + RADIUS) && zombie.getY() >= playerY)
                || (zombie.getY() >= (playerY - RADIUS)
                && zombie.getY() <= playerY))) {
            return true;
        }
        return false;
    }

    /**
     * if player detected this method called. Will follow within radius moves
     * randomly if collides with a wall between player and sprite
     */
    private void moveToPlayer() {
        //right must be true meaning up is not blocked - updated in updatecollisions methods
        //if (right && zombie.getX() < playerX || (right && !up) || (right && !down)) {
        if (right && zombie.getX() < playerX) {

            //set the zombie to face right and loop animation
            zombie.getZombies().setRegion((TextureRegion) zombie.getRight().getKeyFrame(incr));
            //increase zombie x coord to move right 
            zombie.x += Gdx.graphics.getDeltaTime() * zombie.speed;
            //if right is blocked sprite should bounce off wall and move randomly.
            if (isRightBlocked() || !right) {
                moveRandom = true;
            }
            //left must be true meaning up is not blocked - updated in updatecollisions methods
        } else if (left && zombie.getX() > playerX) {
            //set the zombie to face left and increment animation
            zombie.getZombies().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(incr));
            //to move left decrement x 
            zombie.x -= Gdx.graphics.getDeltaTime() * zombie.speed;

            //detection of left collision tile should move randomly
            if (isLeftBlocked() || !left) {
                moveRandom = true;
            }
            //up must be true meaning up is not blocked - updated in updatecollisions methods

        } else if (up && zombie.getY() < playerY) {
            //
            zombie.getZombies().setRegion((TextureRegion) zombie.getUp().getKeyFrame(incr));
            zombie.y += Gdx.graphics.getDeltaTime() * zombie.speed;

            if (isUpBlocked() || !up) {
                moveRandom = true;
            }
            //down must be true meaning up is not blocked - updated in updatecollisions methods
        } else if (down && zombie.getY() > playerY) {
            //zombie to face down and increment through the associated aniamtion
            zombie.getZombies().setRegion((TextureRegion) zombie.getDown().getKeyFrame(incr));
            //decrement y coord to move left 
            zombie.y -= Gdx.graphics.getDeltaTime() * zombie.speed;

            if (isDownBlocked() || !down) {
                moveRandom = true;
            }
        }
    }

    /**
     * if no player detected this method is called. NPC bounces off of collision
     * tiles animations increments are based on timer - updateTimer method
     */
    private void randomMovement() {
        //right is not blocked and the npc has chosen to go right
        if (right && randNum == 0) {
            if (!isRightBlocked()) {
                //update zombie direction and increment through animation
                zombie.getZombies().setRegion((TextureRegion) zombie.getRight().getKeyFrame(incr));
                //increment x coord to move right
                zombie.x += Gdx.graphics.getDeltaTime() * zombie.speed;

                collision = false;
            }
            if (isRightBlocked()) {
                collision = true;
                //changing randnum values bounces sprite in the opposite direction
                randNum = 1;

            }
            //ensures left is not blocked and npc has chosen to go left
        } else if (left && randNum == 1) {
            if (!isLeftBlocked()) {
                //set direction to go left and increment through animation
                zombie.getZombies().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(incr));
                //decrement x coord to move left
                zombie.x -= Gdx.graphics.getDeltaTime() * zombie.speed;
                collision = false;
            }
            if (isLeftBlocked()) {
                collision = true;
                randNum = 0;
            }
            //ensure up is not blocked and npc has decided to go up
        } else if (up && randNum == 2) {
            if (!isUpBlocked()) {
                //makes zombie face up and increments through animation based on timings
                zombie.getZombies().setRegion((TextureRegion) zombie.getUp().getKeyFrame(incr));
                //increment y to go up 
                zombie.y += Gdx.graphics.getDeltaTime() * zombie.speed;
                collision = false;
            }
            if (isUpBlocked()) {
                collision = true;
                randNum = 3;
            }
            //ensures down is not blocked and npc has decided to go down
        } else if (down && randNum == 3) {
            if (!isDownBlocked()) {
                //set facing down and increment through down animation - ensure get correct animation
                zombie.getZombies().setRegion((TextureRegion) zombie.getDown().getKeyFrame(incr));
                //decrement to move down
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

    /**
     * timers for zombie abilities and movement
     *
     * @param delta
     */
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
        //walking animation timer 10
        if (timer >= 50) {
            timer = 0;
            incr++;
        }
        //increments through walking frames
        if (incr > 2) {
            incr = 0;
        }
    }

    /**
     * collisions used for movetoplayer and randommovement
     */
    public void updateCollisions() {
        //is right is blocked set false - cannot go right otherwise true and can go right
        if (isRightBlocked()) {
            right = false;
        } else {
            right = true;

        }
        //if left is blocked set false - cannot go left otherwise true and can go left
        if (isLeftBlocked()) {
            left = false;
        } else {
            left = true;

        }
        //if up is blocked set false - cannot go up otherwise true and can go up
        if (isUpBlocked()) {
            up = false;
        } else {
            up = true;
        }
        //if down is blocked set to false - cannot go down otherwise true and can go down
        if (isDownBlocked()) {
            down = false;
        } else {
            down = true;

        }
    }

    /**
     *
     * @return direction to move 1 =left 2 = up 3 = down 4=right
     */
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

    /**
     * method used for npc testing
     */
    @SuppressWarnings("static-access")
    public void keys(float delta) {
        if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT) && !isLeftBlocked() && left) {

            zombie.x -= (Gdx.graphics.getDeltaTime()) * zombie.speed;
            zombie.getZombies().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(incr));
        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT) && !isRightBlocked() && right) {

            zombie.x += (Gdx.graphics.getDeltaTime()) * zombie.speed;
            zombie.getZombies().setRegion((TextureRegion) zombie.getRight().getKeyFrame(incr));
        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_UP) && !isUpBlocked() && up) {
            zombie.y += (Gdx.graphics.getDeltaTime()) * zombie.speed;
            zombie.getZombies().setRegion((TextureRegion) zombie.getUp().getKeyFrame(incr));
        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN) && !isDownBlocked() && down) {
            zombie.y -= (Gdx.graphics.getDeltaTime()) * zombie.speed;
            zombie.getZombies().setRegion((TextureRegion) zombie.getDown().getKeyFrame(incr));
        }
    }

    public void setDown(boolean value) {
        down = value;
    }

    public boolean getDown() {
        return down;
    }

    public void setUp(boolean value) {
        up = value;
    }

    public boolean getUp() {
        return up;
    }

    public void setRight(boolean value) {
        right = value;
    }

    public boolean getRight() {
        return right;
    }

    public void setLeft(boolean value) {
        left = value;
    }

    public boolean getLeft() {
        return left;
    }

}
