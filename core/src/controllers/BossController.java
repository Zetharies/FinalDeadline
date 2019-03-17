package controllers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import managers.SettingsManager;
import models.BossZombie;

public class BossController extends NPCController {

    private BossZombie zombie;//ref associated object
    private final float RADIUS = 15;//radius for boss to detectplayer and shoot
    private int toShoot; // control fire rate
    private boolean reset;//for sprite to teleport and reset health
    private int biteAudioTimer = 0;//timer to play audio

    /**
     * construct boss controller - boss zombie object
     * @param collisions
     * @param zombie 
     */
    public BossController(TiledMapTileLayer collisions, BossZombie zombie) {
        this.zombie = zombie;
        this.collisions = collisions;
        moveRandom = false;
        toShoot = 0;
        reset = false;
    }

    @SuppressWarnings("static-access")
    public void update(float delta) {
        toShoot++;
        updateTimers(delta);
        updateCollisions(this.zombie);
        //keys();

        //only use abilities once detection of player
        if (detectPlayer(this.zombie, RADIUS)) {
            System.out.println("testing");
            // bullet shot at rate of 90 delta
            if (toShoot == 90) {
                zombie.shoot();//create bullet and shoot
                //set shot to true to render
                zombie.getBullets().get(zombie.getBullets().size() - 1).setShoot(true);
                zombie.getBullets().get(zombie.getBullets().size() - 1).setSpeed(3.1f);
                toShoot = 0;//reset rate 
            } else {
                zombie.rushPlayer();// rush player to bite them - speed increased 
                moveToPlayer(this.zombie, BossZombie.speed);
            }
        } else {
            this.zombie.setSpeed(BossZombie.speed);//reset speed
        }
        if (toShoot == 90) {
            toShoot = 0;//reset to shoot - required when sprite does not detect player
        }
        //hp reset once below certain hp
        if (!reset) {
            zombie.resetHealth();
            reset = true; //only reset once
        }
        playerTouchingBoss();
    }

    /**
     * play biting audio when player touching sprite -
     */
    public void playerTouchingBoss() {
        biteAudioTimer++;
        //detect player with 0.5 radius
        if (((zombie.getX() <= (playerX + 0.5) && zombie.getX() >= playerX) || (zombie.getX() >= (playerX - 0.5)
                && zombie.getX() <= playerX)) && ((zombie.getY() <= (playerY + 0.5) && zombie.getY() >= playerY)
                || (zombie.getY() >= (playerY - 0.5)
                && zombie.getY() <= playerY))) {
            if (biteAudioTimer == 70 && SettingsManager.getSound()) {
                zombie.bite(true);//play bite audio
                biteAudioTimer = 0;//reset audio timer
            }
        } else {
            zombie.bite(false);
        }
        //timing for audio to be played
        if (biteAudioTimer == 150) {
            biteAudioTimer = 0;
        }
    }

    /**
     * increase health of sprite 
     */
    public void increaseHealth() {
        this.zombie.setHealth(100);
    }



    public void updateTimers(float delta) {  
    }

   

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
