package controllers;


import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import models.Robot;

public class RobotController extends NPCController{

    private Robot robot;
    private final float RADIUS = 10;
    
    public RobotController(TiledMapTileLayer collisions, Robot robot) {
        this.robot = robot;
        this.collisions = collisions;

    }
    int toShoot = 0;
    boolean playAudio = false;

    @SuppressWarnings("static-access")
    public void update(float delta) {
        toShoot++;
        updateTimers(delta);
        updateCollisions(this.robot);
        detectPlayer(this.robot,RADIUS);
//        keys();
        if (detectPlayer(this.robot,RADIUS)) {
            moveToPlayer(this.robot,Robot.SPEED);
            if (toShoot == 50) {
                System.out.println("shoot");
                robot.shoot();
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


    private int count2 = 0;

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
