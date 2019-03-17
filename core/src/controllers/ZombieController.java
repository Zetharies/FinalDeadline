package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.ArrayList;
import models.Zombie;

public class ZombieController extends NPCController {

    private Zombie zombie;//assoc zombie object
    private int moveRandomTimer = 0;//time to begin next direction to move

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
        radius = 7;
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
        updateCollisions(this.zombie);

        //if up/down blocked set timer = true for time to move randomly
        if (detectPlayer(this.zombie, radius) && !up) {
            setTimer = true;
        } else if (detectPlayer(this.zombie, radius) && !down) {
            setTimer = true;
        }
        if (setTimer) {
            testTimer++;
        }
        //once timer reached detect player again
        if (testTimer == 100) {
            setTimer = false;
            testTimer = 0;
        }
        //detect other zombies - code used for ensuring zombies to enter same tile / overlap
        for (int j = 0; j < zombies.size(); j++) {
            //check other zombies not associated one
            if (zombie != zombies.get(j)) {
                //zombie next to zombie on left
                if ((int) zombies.get(j).x == (int) zombie.x && (int) zombies.get(j).y == (int) zombie.y - 1) {
                    zombie.setDown(false);
                    break;
                    //zombie next to other zombie on right
                } else if ((int) zombies.get(j).x == (int) zombie.x && (int) zombies.get(j).y == (int) zombie.y + 1) {
                    zombie.setUp(false);

                    break;
                    //zombie next to other zombie on 1 tile up
                } else if ((int) zombies.get(j).x == (int) zombie.x + 1 && (int) zombies.get(j).y == (int) zombie.y) {
                    zombie.setRight(false);
                    break;
                    //zombie next to other zombie 1 tile below
                } else if ((int) zombies.get(j).x == (int) zombie.x - 1 && (int) zombies.get(j).y == (int) zombie.y) {
                    zombie.setLeft(false);

                    break;

                }
            }
        }

        //set timer used for random movement when blocked whilst detecting player
        if (detectPlayer(this.zombie, radius) && !moveRandom && !setTimer) {
            moveToPlayer(this.zombie, Zombie.SPEED);
        } else {
            randomMovement();//move randomly 
        }

    }

    /**
     * if no player detected this method is called. NPC bounces off of collision
     * tiles animations increments are based on timer - updateTimer method
     */
    private void randomMovement() {
        //right is not blocked and the npc has chosen to go right
        if (right && direction == 0) {
            if (!isRightBlocked(this.zombie)) {
                //update zombie direction and increment through animation
                zombie.getSprite().setRegion((TextureRegion) zombie.getRight().getKeyFrame(incr));
                //increment x coord to move right
                zombie.x += Gdx.graphics.getDeltaTime() * Zombie.SPEED;

                collision = false;
            }
            if (isRightBlocked(this.zombie)) {
                collision = true;
                //changing direction values bounces sprite in the opposite direction
                direction = 1;//move in opposite direction

            }
            //ensures left is not blocked and npc has chosen to go left
        } else if (left && direction == 1) {
            if (!isLeftBlocked(this.zombie)) {
                //set direction to go left and increment through animation
                zombie.getSprite().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(incr));
                //decrement x coord to move left
                zombie.x -= Gdx.graphics.getDeltaTime() * Zombie.SPEED;
                collision = false;
            }
            if (isLeftBlocked(this.zombie)) {
                collision = true;
                direction = 0;//move in opposite direction
            }
            //ensure up is not blocked and npc has decided to go up
        } else if (up && direction == 2) {
            if (!isUpBlocked(this.zombie)) {
                //makes zombie face up and increments through animation based on timings
                zombie.getSprite().setRegion((TextureRegion) zombie.getUp().getKeyFrame(incr));
                //increment y to go up 
                zombie.y += Gdx.graphics.getDeltaTime() * Zombie.SPEED;
                collision = false;
            }
            if (isUpBlocked(this.zombie)) {
                collision = true;
                direction = 3;//move in opposite direction
            }
            //ensures down is not blocked and npc has decided to go down
        } else if (down && direction == 3) {
            if (!isDownBlocked(this.zombie)) {
                //set facing down and increment through down animation - ensure get correct animation
                zombie.getSprite().setRegion((TextureRegion) zombie.getDown().getKeyFrame(incr));
                //decrement to move down
                zombie.y -= Gdx.graphics.getDeltaTime() * Zombie.SPEED;
                collision = false;
            }
            if (isDownBlocked(this.zombie)) {
                collision = true;
                direction = 2;//move in opposite direction
            }

        }
    }

    /**
     * timers for zombie abilities and movement
     *
     * @param delta
     */
    public void updateTimers(float delta) {
        timer += (delta * 100);
        count++;
        moveRandomTimer++;
        //counter to potentially change direction
        if ((count >= 80 || count == -1) && !collision) {
            //direction to move based on rng
            direction = shouldMove();
            count = 0;//rest time to move next 
        }
        if (moveRandomTimer >= 80) {
            moveRandom = false;
        }
        //walking animation timer 50
        if (timer >= 50) {
            timer = 0;//reset timer
            incr++;
        }
        //increments through walking frames
        if (incr > 2) {
            incr = 0;//back to initial animation
        }
    }

    /**
     *
     * @return direction to move 1 =left 2 = up 3 = down 4=right
     */
    public int shouldMove() {
        return random.nextInt(4);
    }

    /**
     * method used for npc testing
     */
//    @SuppressWarnings("static-access")
//    public void keys(float delta) {
//        if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT) && !isLeftBlocked() && left) {
//
//            zombie.x -= (Gdx.graphics.getDeltaTime()) * zombie.speed;
//            zombie.getSprite().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(incr));
//        }
//        if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT) && !isRightBlocked() && right) {
//
//            zombie.x += (Gdx.graphics.getDeltaTime()) * zombie.speed;
//            zombie.getSprite().setRegion((TextureRegion) zombie.getRight().getKeyFrame(incr));
//        }
//        if (Gdx.input.isKeyPressed(Keys.DPAD_UP) && !isUpBlocked() && up) {
//            zombie.y += (Gdx.graphics.getDeltaTime()) * zombie.speed;
//            zombie.getSprite().setRegion((TextureRegion) zombie.getUp().getKeyFrame(incr));
//        }
//        if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN) && !isDownBlocked() && down) {
//            zombie.y -= (Gdx.graphics.getDeltaTime()) * zombie.speed;
//            zombie.getSprite().setRegion((TextureRegion) zombie.getDown().getKeyFrame(incr));
//        }
//    }
    /**
     * set down collision true / false
     *
     * @param value
     */
    public void setDown(boolean value) {
        down = value;
    }

    /**
     * get down collision
     *
     * @return
     */
    public boolean getDown() {
        return down;
    }

    /**
     * set up collision true / false
     *
     * @param value
     */
    public void setUp(boolean value) {
        up = value;
    }

    /**
     * get up collision
     *
     * @return
     */
    public boolean getUp() {
        return up;
    }

    /**
     * set right collision true / false
     *
     * @param value
     */
    public void setRight(boolean value) {
        right = value;
    }

    /**
     * get right collision
     *
     * @return
     */
    public boolean getRight() {
        return right;
    }

    /**
     * set left collision true/false
     *
     * @param value
     */
    public void setLeft(boolean value) {
        left = value;
    }

    /**
     * get left collision
     *
     * @return
     */
    public boolean getLeft() {
        return left;
    }

}
