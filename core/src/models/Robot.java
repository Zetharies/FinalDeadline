package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.GameSettings;
import controllers.PlayerMovement;
import java.util.ArrayList;
import static models.NPC.FRAME_COLS;

public class Robot extends NPC{

    public static final float SPEED = 0.8f; // 10 pixels per second.
//    public float x;
//    public float y;
//    private int currentFrame = 6;
//    private int health = 100;
//    private Sprite sprite;
//    private Texture texture;
//    private TextureRegion region[][];
//    private static final int FRAME_COLS = 3;
//    private static final int FRAME_ROWS = 4;
//    private TextureRegion[] walkFrames;
//    private Animation walkingUp, walkingDown, walkingRight, walkingLeft;
    private ArrayList<Bullet> bullets;
    private TiledMapTileLayer collisions;
    private boolean kill = false;
    private Sound audio;

    public Robot(int startX, int startY, TiledMapTileLayer collisions) {
        this.collisions = collisions;
        texture = new Texture(Gdx.files.internal("sprite/boss/robot.png"));
        region = TextureRegion.split(texture, texture.getWidth() / FRAME_COLS, texture.getHeight() / FRAME_ROWS);
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = region[i][j];
            }
        }
        sprite = new Sprite(walkFrames[currentFrame + 1]);
        sprite.setOriginCenter();
        x = (float) startX;
        y = (float) startY;
        walkingDown = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[0], walkFrames[1], walkFrames[2]);
        walkingRight = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[7], walkFrames[6], walkFrames[8]);
        walkingLeft = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[3], walkFrames[4], walkFrames[5]);
        walkingUp = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[10], walkFrames[9], walkFrames[11]);
        sprite.setRegion((TextureRegion) walkingRight.getKeyFrame(0));
        bullets = new ArrayList<Bullet>();

        audio = Gdx.audio.newSound(Gdx.files.internal("fx/singleShot.mp3"));
    }

    public void playAudio() {
        audio.play(0.5f);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void shoot() {
        bullets.add(new Bullet(x, y, collisions, "bullet.png",1,1));
    }

    public void removeBullet(int index) {
        bullets.remove(index);
    }
    
    public void kill(boolean kill){
        this.kill = kill;
    }
    
    public boolean getKill(){
        return this.kill;
    }
    
    public float getSpeed(){
        return SPEED;
    }

}
