package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import managers.SettingsManager;
import models.NPC;

import models.Robot;

public class RobotController extends NPCController {

    private Robot robot;//associated robot object
    private final float RADIUS = 20;//radius to detect player
    private int toShoot;//fire rate
    private boolean playAudio; //bool audio - audio plays when shot taken

    /**
     * construct controller - game screen
     *
     * @param collisions
     * @param robot
     */
    public RobotController(TiledMapTileLayer collisions, Robot robot) {
        this.robot = robot;
        this.collisions = collisions;
        toShoot = 0;
        playAudio = false;
    }

    @SuppressWarnings("static-access")
    /**
     * update robot
     */
    public void update(float delta) {
        toShoot++;//timer for shooting fire rate
        updateTimers(delta);
        updateCollisions(this.robot);
//        keys();
        //shoot when player detected and move to player
        if (detectPlayer(this.robot, RADIUS)) {
            moveToPlayer();
            if (toShoot == 80) {//50 fire rate 
                System.out.println("shoot");
                robot.shoot();//shoot - create new bullet
                robot.getBullets().get(robot.getBullets().size() - 1).setShoot(true);//set shot to true to render
                toShoot = 0;//reset rate 
                if (SettingsManager.getSound()) { // only play audio if settings returns true
                   // robot.playAudio();
                }
            }
        }
        if (toShoot == 80) {
            toShoot = 0;
        }
    }

    
    public void moveToPlayer() {
        //right must be true meaning up is not blocked - updated in updatecollisions methods
        if (robot.getX() < playerX) {
            //set the zombie to face right and loop animation
            robot.getSprite().setRegion((TextureRegion) robot.getRight().getKeyFrame(incr));
            //increase zombie x coord to move right 
            robot.x += Gdx.graphics.getDeltaTime() * robot.getSpeed();

        }else if (robot.getX() > playerX) {
            //set the zombie to face left and increment animation
            robot.getSprite().setRegion((TextureRegion) robot.getLeft().getKeyFrame(incr));
            //to move left decrement x 
            robot.x -= Gdx.graphics.getDeltaTime() * robot.getSpeed();

        }else if (robot.getY() < playerY) {
            robot.getSprite().setRegion((TextureRegion) robot.getUp().getKeyFrame(incr));
            robot.y += Gdx.graphics.getDeltaTime() * robot.getSpeed();

        }else if (robot.getY() > playerY) {
            //zombie to face down and increment through the associated aniamtion
            robot.getSprite().setRegion((TextureRegion) robot.getDown().getKeyFrame(incr));
            //decrement y coord to move left 
            robot.y -= Gdx.graphics.getDeltaTime() * robot.getSpeed();
        }
    }

    public void setAudio(boolean audio) {
        playAudio = audio;
    }

    public void updateTimers(float delta) {
        timer += (delta * 100);

        //walking animation timer 50
        if (timer >= 30) {
            timer = 0;//reset timer
            incr++;
        }
        //increments through walking frames
        if (incr == 3) {
            incr = 0;//back to initial animation
        }
    }

}
