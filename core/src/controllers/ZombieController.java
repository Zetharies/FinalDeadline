package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.ArrayList;
import models.Zombie;

public class ZombieController extends NPCController {

    private Zombie zombie;
    private ArrayList<PlayerMovement> movements;

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

        if (detectPlayer(this.zombie, radius) && !up) {
            setTimer = true;
        } else if (detectPlayer(this.zombie, radius) && !down) {
            setTimer = true;
        }

        if (setTimer) {
            testTimer++;
        }//40
        if (testTimer == 100) {
            setTimer = false;
            testTimer = 0;
        }
        //detect other zombies - code used for ensuring zombies to enter same tile / overlap
        for (int j = 0; j < zombies.size(); j++) {
            //check other zombies not associated one
            if (zombie != zombies.get(j)) {
                if ((int) zombies.get(j).x == (int) zombie.x && (int) zombies.get(j).y == (int) zombie.y - 1) {
                    zombie.setDown(false);
                    break;
                } else if ((int) zombies.get(j).x == (int) zombie.x && (int) zombies.get(j).y == (int) zombie.y + 1) {
                    zombie.setUp(false);

                    break;

                } else if ((int) zombies.get(j).x == (int) zombie.x + 1 && (int) zombies.get(j).y == (int) zombie.y) {
                    zombie.setRight(false);
                    break;

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
            randomMovement();
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
                direction = 1;

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
                direction = 0;
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
                direction = 3;
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
                direction = 2;
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
            direction = shouldMove();
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
