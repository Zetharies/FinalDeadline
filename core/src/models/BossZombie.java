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

    public static float speed = 2.3f; // 10 pixels per second.

    public float startX, startY;
    private BossController controller;
    private TiledMapTileLayer collisions;

    private boolean bite = false;
    private ArrayList<Bullet> bullets;
    private Sound biteAudio;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public BossZombie(int startX, int startY, TiledMapTileLayer collisions) {
        this.collisions = collisions;
        texture = new Texture(Gdx.files.internal("sprite/zombie/bossZombie.png"));
        region = TextureRegion.split(texture, texture.getWidth() / FRAME_COLS, texture.getHeight() / FRAME_ROWS);
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        controller = new BossController(collisions, this);
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = region[i][j];
            }
        }
        sprite = new Sprite(walkFrames[currentFrame + 1]);
        sprite.setOriginCenter();
        x =  startX;
        y =  startY;
        this.startX = (float) startX;
        this.startY = (float) startY;
        walkingDown = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[0], walkFrames[1], walkFrames[2]);
        walkingUp = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[7], walkFrames[6], walkFrames[8]);
        walkingRight = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[4], walkFrames[3], walkFrames[5]);
        walkingLeft = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[10], walkFrames[9], walkFrames[11]);

        bullets = new ArrayList<Bullet>();
        biteAudio = Gdx.audio.newSound(Gdx.files.internal("fx/bite.mp3"));

    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void shoot() {
        bullets.add(new Bullet(x, y, collisions, "spriteHead2.png", 1, 1));
    }

    public void removeBullet(int index) {
        bullets.remove(index);
    }

    public void rushPlayer() {
        this.setSpeed(2.3f + 0.6f);
    }

    public void resetHealth() {
        if (this.health <= 30) {
            teleport();
            this.health = 100;
        }
    }

    public void bite(boolean play) {
        if (play) {
            biteAudio.play();
        } else {
            biteAudio.pause();
        }
    }

    public void teleport() {
        this.x = this.startX;
        this.y = this.startY;
    }

    public void update(float delta) {
        controller.update(delta);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void die() {
    }

}
