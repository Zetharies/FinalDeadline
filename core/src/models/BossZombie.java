package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.GameSettings;
import controllers.BossController;
import controllers.PlayerMovement;
import java.util.ArrayList;
import static models.NPC.FRAME_COLS;

public class BossZombie extends NPC {

    public static float speed = 2.3f; // speed for zombie to move;

    public float startX, startY; // start x y required for teleportation
    private BossController controller;//ref associated controller
    private TiledMapTileLayer collisions;//collision set for collision detection

    private ArrayList<Bullet> bullets;//have a set of bullets to shoot
    private Sound biteAudio;//bite audio - used with ability
    private boolean bite;
    private static final int FRAME_COLS = 3;
    private static final int FRAME_ROWS = 4;
    /**
     * construct boss zombie object with associated controller
     *
     * @param startX
     * @param startY
     * @param collisions
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public BossZombie(int startX, int startY, TiledMapTileLayer collisions) {
        this.collisions = collisions;
        createSprite(FRAME_COLS,FRAME_ROWS,"sprite/zombie/bossZombie.png",true);
        controller = new BossController(collisions, this);
      
        x = startX;
        y = startY;
        this.startX = (float) startX;
        this.startY = (float) startY;
        //animation set
        walkingDown = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[0], walkFrames[1], walkFrames[2]);
        walkingUp = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[7], walkFrames[6], walkFrames[8]);
        walkingRight = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[4], walkFrames[3], walkFrames[5]);
        walkingLeft = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[10], walkFrames[9], walkFrames[11]);

        bullets = new ArrayList<Bullet>();
        biteAudio = Gdx.audio.newSound(Gdx.files.internal("fx/bite.mp3"));
        bite = false;
    }
    
    public BossZombie(int x, int y) {
    	this.x = x;
    	this.y = y;
    }

    /**
     * get bullets - used to render method
     *
     * @return
     */
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    /**
     * called in controller class - create new bullet once boss decides to shoot
     */
    public void shoot() {
        bullets.add(new Bullet(x, y, collisions, "spriteHead2.png", 1, 1));
    }

    /**
     * method to be called once bullet is redundant
     *
     * @param index
     */
    public void removeBullet(int index) {
        bullets.remove(index);
    }

    /**
     * one ability of boss
     */
    public void rushPlayer() {
        this.setSpeed(2.3f + 0.6f);
    }

    /**
     * second ability of boss
     */
    public void resetHealth() {
        if (this.health <= 30) {
            teleport();
            this.health = 100;
        }
    }

    /**
     * third ability of boss
     *
     * @param play
     */
    public void bite(boolean play) {
        if (play) {
            biteAudio.play();
        } else {
            biteAudio.pause();
        }
    }

    /**
     * teleport to be used with reset health
     */
    public void teleport() {
        this.x = this.startX;
        this.y = this.startY;
    }

    /**
     * call update method to update boss zombie - movement, abilities
     *
     * @param delta
     */
    public void update(float delta) {
        controller.update(delta);
    }

    /**
     * change speed - required for rushing ability
     *
     * @param speed
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void die() {
    }

}
