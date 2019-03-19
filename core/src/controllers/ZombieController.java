package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.ArrayList;
import managers.SettingsManager;
import models.Zombie;
import controllers.NPCController;
public class ZombieController extends NPCController {

    private Zombie zombie;//assoc zombie object
    private int moveRandomTimer = 0;//time to begin next direction to move
    protected int count = -1;

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
        biteAudio = Gdx.audio.newSound(Gdx.files.internal("fx/bite.mp3"));

    }
    public ZombieController(Zombie zombie) {
    	this.zombie = zombie;
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
        biteAudioTimer++;
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
            moveToPlayer();
            this.zombie.setMoved(true);
        } else {
            randomMovement();//move randomly 
        }

        if (((zombie.getX() <= (playerX + 0.5) && zombie.getX() >= playerX) || (zombie.getX() >= (playerX - 0.5)
                && zombie.getX() <= playerX)) && ((zombie.getY() <= (playerY + 0.5) && zombie.getY() >= playerY)
                || (zombie.getY() >= (playerY - 0.5)
                && zombie.getY() <= playerY))) {
            System.out.println("hit player");
            System.out.println(biteAudioTimer);
            if (biteAudioTimer == 70 && SettingsManager.getSound()) {
                biteAudio.play();
                biteAudioTimer = 0;//reset audio timer
            }
        }else{
            biteAudio.stop();
        }
        if (biteAudioTimer == 70) {
            biteAudioTimer = 0;
        }
    }
    Sound biteAudio;
    int biteAudioTimer = 0;

    /**
     * if no player detected this method is called. zombie bounces off of
     * collision tiles animations increments are based on timer - updateTimer
     * method
     */
    private void randomMovement() {
        //right is not blocked and the zombie has chosen to go right
        if (right && direction == 0) {
            if (!isRightBlocked(this.zombie)) {
                //update zombie direction and increment through animation
                zombie.getSprite().setRegion((TextureRegion) zombie.getRight().getKeyFrame(incr));
                //increment x coord to move right
                zombie.x += Gdx.graphics.getDeltaTime() * Zombie.SPEED;
                collision = false;
                this.zombie.setMoved(true);
            }
            if (isRightBlocked(this.zombie)) {
                collision = true;
                //changing direction values bounces sprite in the opposite direction
                if (!isUpBlocked(this.zombie)) {
                    direction = 2;
                } else if (!isDownBlocked(this.zombie)) {
                    direction = 3;
                } else {
                    direction = 1;//move in opposite direction

                }
                System.out.println("right blocked");
            }
            // 1 =left 2 = up 3 = down 4=right
            //ensures left is not blocked and zombie has chosen to go left
        } else if (left && direction == 1) {
            if (!isLeftBlocked(this.zombie)) {// 1 =left 2 = up 3 = down 4=right
                //set direction to go left and increment through animation
                zombie.getSprite().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(incr));
                //decrement x coord to move left
                zombie.x -= Gdx.graphics.getDeltaTime() * Zombie.SPEED;
                collision = false;
                this.zombie.setMoved(true);
            }
            if (isLeftBlocked(this.zombie)) {
                collision = true;
                if (!isUpBlocked(this.zombie)) {
                    direction = 2;
                } else if (!isDownBlocked(this.zombie)) {
                    direction = 3;
                } else {
                    direction = 4;//move in opposite direction

                }
                System.out.println("left blocked");
            }
            //ensure up is not blocked and zombie has decided to go up
        } else if (up && direction == 2) {
            if (!isUpBlocked(this.zombie)) {
                //makes zombie face up and increments through animation based on timings
                zombie.getSprite().setRegion((TextureRegion) zombie.getUp().getKeyFrame(incr));
                //increment y to go up 
                zombie.y += Gdx.graphics.getDeltaTime() * Zombie.SPEED;
                collision = false;
                this.zombie.setMoved(true);
            }// 1 =left 2 = up 3 = down 4=right
            if (isUpBlocked(this.zombie)) {
                collision = true;
                if (!isLeftBlocked(this.zombie)) {
                    direction = 1;
                } else if (!isRightBlocked(this.zombie)) {
                    System.out.println("right isnt blocked");
                    direction = 4;
                } else {
                    direction = 3;//move in opposite direction

                }
                System.out.println("up blocked");
            }
            //ensures down is not blocked and zombie has decided to go down
        } else if (down && direction == 3) {
            if (!isDownBlocked(this.zombie)) {
                //set facing down and increment through down animation - ensure get correct animation
                zombie.getSprite().setRegion((TextureRegion) zombie.getDown().getKeyFrame(incr));
                //decrement to move down
                zombie.y -= Gdx.graphics.getDeltaTime() * Zombie.SPEED;
                collision = false;
                this.zombie.setMoved(true);
            }
            if (isDownBlocked(this.zombie)) {// 1 =left 2 = up 3 = down 4=right
                collision = true;
                if (!isLeftBlocked(this.zombie)) {
                    direction = 1;
                } else if (!isRightBlocked(this.zombie)) {
                    direction = 4;
                } else {
                    direction = 3;//move in opposite direction

                }
                System.out.println("down blocked");
            }

        }
    }
    private int timeRender = 0;
    /**
     * timers for zombie abilities and movement
     *
     * @param delta
     */
    private int time = 0;

    public void updateTimers(float delta) {
        time++;
        if (zombie.getTimer() < 85) {
            zombie.setTimer(time);
        }
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

    public TiledMapTileLayer getCollisions() {
        return this.collisions;
    }

    public void moveToPlayer() {
        //right must be true meaning up is not blocked - updated in updatecollisions methods
        if (right && zombie.getX() < playerX) {
            //set the zombie to face right and loop animation
            zombie.getSprite().setRegion((TextureRegion) zombie.getRight().getKeyFrame(incr));
            //increase zombie x coord to move right 
            zombie.x += Gdx.graphics.getDeltaTime() * zombie.getSpeed();
            //if right is blocked sprite should bounce off wall and move randomly.
            if (isRightBlocked(zombie) || !right) {
                moveRandom = true;
            }
            //left must be true meaning up is not blocked - updated in updatecollisions methods
        } else if (left && zombie.getX() > playerX) {
            //set the zombie to face left and increment animation
            zombie.getSprite().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(incr));
            //to move left decrement x 
            zombie.x -= Gdx.graphics.getDeltaTime() * zombie.getSpeed();

            //detection of left collision tile should move randomly
            if (isLeftBlocked(zombie) || !left) {
                moveRandom = true;
            }
            //up must be true meaning up is not blocked - updated in updatecollisions methods

        } else if (up && zombie.getY() < playerY) {
            //a
            zombie.getSprite().setRegion((TextureRegion) zombie.getUp().getKeyFrame(incr));
            zombie.y += Gdx.graphics.getDeltaTime() * zombie.getSpeed();

            if (isUpBlocked(zombie) || !up) {
                moveRandom = true;
            }
            //down must be true meaning up is not blocked - updated in updatecollisions methods
        } else if (down && zombie.getY() > playerY) {
            //zombie to face down and increment through the associated aniamtion
            zombie.getSprite().setRegion((TextureRegion) zombie.getDown().getKeyFrame(incr));
            //decrement y coord to move left 
            zombie.y -= Gdx.graphics.getDeltaTime() * zombie.getSpeed();

            if (isDownBlocked(zombie) || !down) {
                moveRandom = true;
            }
        }
    }
    
    public int getPlayerX() {
    	
    	return playerX;
    }
    public int getPlayerY() {
    	return playerY;
    }
    
    public boolean detect() {
    	return detectPlayer(this.zombie, radius);
    }
}
