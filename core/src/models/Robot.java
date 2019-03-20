package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.GameSettings;
import java.util.ArrayList;

public class Robot extends NPC{

    public static final float SPEED = 0.8f; //movement speed - increase value to increase speed
    private ArrayList<Bullet> bullets;//bullet set
    private TiledMapTileLayer collisions;//collision set
    private boolean kill = false; // detect robot hp = 0
    private Sound audio; // shooting audio
     protected static final int FRAME_COLS = 3;
    protected static final int FRAME_ROWS = 4;
    /**
     * construct robot with assoc controller
     * @param startX
     * @param startY
     * @param collisions 
     */
    public Robot(int startX, int startY, TiledMapTileLayer collisions) {
        this.collisions = collisions;
        createSprite(FRAME_COLS,FRAME_ROWS, "sprite/boss/robot.png", true);
        x = (float) startX;
        y = (float) startY;
        //animation set
        walkingDown = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[0], walkFrames[1], walkFrames[2]);
        walkingRight = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[8], walkFrames[7], walkFrames[6]);
        walkingLeft = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[3], walkFrames[4], walkFrames[5]);
        walkingUp = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[11], walkFrames[10], walkFrames[9]);
       
        sprite.setRegion((TextureRegion) walkingRight.getKeyFrame(0));
        bullets = new ArrayList<Bullet>();
        audio = Gdx.audio.newSound(Gdx.files.internal("fx/singleShot.mp3"));
    }
    
   

    /**
     * call to play audio 
     */
    public void playAudio() {
        audio.play(0.5f);
    }

    /**
     * get bullets for updating rendering etc.
     * @return 
     */
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    /**
     * load new bullet to shoot
     */
    public void shoot() {
        bullets.add(new Bullet(x, y, collisions, "bullet.png",1,1));
    }

    /**
     * ensure bullets dont stack - called when bullet x y = p xy
     * @param index 
     */
    public void removeBullet(int index) {
        bullets.remove(index);
    }
    
    /**
     * method called when robot health = 0 
     * @param kill 
     */
    public void kill(boolean kill){
        this.kill = kill;
    }
    
    /**
     * get robot hp status
     * @return 
     */
    public boolean getKill(){
        return this.kill;
    }
    
    /**
     * robot speed returned for controller
     * @return 
     */
    public float getSpeed(){
        return SPEED;
    }
    
    public Sound getAudio(){
        return audio;
    }

}
