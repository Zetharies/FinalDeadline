package controllers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import managers.SettingsManager;

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
            moveToPlayer(this.robot, Robot.SPEED);
            if (toShoot == 50) {//50 fire rate 
                System.out.println("shoot");
                robot.shoot();//shoot - create new bullet
                robot.getBullets().get(robot.getBullets().size() - 1).setShoot(true);//set shot to true to render
                toShoot = 0;//reset rate 
                if (SettingsManager.getSound()) { // only play audio if settings returns true
                    robot.playAudio();
                }
            }
        }
        if (toShoot == 50) {
            toShoot = 0;
        }
    }

    public void setAudio(boolean audio) {
        playAudio = audio;
    }


    public void updateTimers(float delta) {
       
    }

//    @SuppressWarnings("static-access")
//    public void keys() {
//        if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
//
//            robot.x -= Gdx.graphics.getDeltaTime() * robot.speed;
//            robot.getSprite().setRegion((TextureRegion) robot.getLeft().getKeyFrame(incr));
//
//        }
//        if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
//
//            robot.x += Gdx.graphics.getDeltaTime() * robot.speed;
//            robot.getSprite().setRegion((TextureRegion) robot.getRight().getKeyFrame(incr));
//
//        }
//        if (Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
//
//            robot.y += Gdx.graphics.getDeltaTime() * robot.speed;
//            robot.getSprite().setRegion((TextureRegion) robot.getUp().getKeyFrame(incr));
//
//        }
//        if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
//
//            robot.y -= Gdx.graphics.getDeltaTime() * robot.speed;
//            robot.getSprite().setRegion((TextureRegion) robot.getDown().getKeyFrame(incr));
//
//        }
//        if (Gdx.input.isKeyPressed(Keys.B)) {
//          //  robot.kill(true);
////            robot.shoot();
////            robot.getBullets().get(robot.getBullets().size() - 1).setShotDirection(facing);
////            robot.getBullets().get(robot.getBullets().size() - 1).setShoot(true);
////            moveToPlayer();
//        }
//    }
}
