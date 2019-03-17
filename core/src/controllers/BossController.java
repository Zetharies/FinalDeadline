package controllers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import models.BossZombie;

public class BossController extends NPCController {

    private BossZombie zombie;
    private final float RADIUS = 15;
    private int toShoot;

    public BossController(TiledMapTileLayer collisions, BossZombie zombie) {
        this.zombie = zombie;
        this.collisions = collisions;
        moveRandom = false;
        toShoot = 0;
    }
    int methodInAction = 0;

    @SuppressWarnings("static-access")
    public void update(float delta) {
        System.out.println(zombie.getHealth());
        methodInAction++;
        toShoot++;
        //control a zombie - in game screen only update one zombie

        updateTimers(delta);
        updateCollisions(this.zombie);
        //keys();

        //below certain value teleport and slowly regain hp but keep if player stays to long near boss
        if (detectPlayer(this.zombie, RADIUS)) {
            System.out.println("testing");
            if (toShoot == 90) {
                zombie.shoot();
                zombie.getBullets().get(zombie.getBullets().size() - 1).setShoot(true);
                zombie.getBullets().get(zombie.getBullets().size() - 1).setSpeed(2.3f + 0.8f);
                toShoot = 0;
            } else {
                zombie.rushPlayer();
                moveToPlayer(this.zombie, BossZombie.speed);
            }
        } else {
            this.zombie.setSpeed(2.3f);
        }
        if (toShoot == 90) {
            toShoot = 0;
        }
        zombie.resetHealth();
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

    boolean moveRandom = false;

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
            direction = shouldMove();
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

    public int shouldMove() {
        return random.nextInt(4);
    }

    private boolean doOnce = false;
//
//    @SuppressWarnings("static-access")
//    public void keys() {
//        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) && !isLeftBlocked()) {
//
//            zombie.x -= Gdx.graphics.getDeltaTime() * zombie.speed;
//            zombie.getZombies().setRegion((TextureRegion) zombie.getLeft().getKeyFrame(10));
//
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) && !isRightBlocked()) {
//
//            zombie.x += Gdx.graphics.getDeltaTime() * zombie.speed;
//            zombie.getZombies().setRegion((TextureRegion) zombie.getRight().getKeyFrame(5));
//
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) && !isUpBlocked()) {
//
//            zombie.y += Gdx.graphics.getDeltaTime() * zombie.speed;
//            zombie.getZombies().setRegion((TextureRegion) zombie.getUp().getKeyFrame(8));
//
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN) && !isDownBlocked()) {
//
//            zombie.y -= Gdx.graphics.getDeltaTime() * zombie.speed;
//            zombie.getZombies().setRegion((TextureRegion) zombie.getDown().getKeyFrame(1));
//
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.B) && !doOnce) {
//           zombie.setHealth(20);
//        }
//    }
}
