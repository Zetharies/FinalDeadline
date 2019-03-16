package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.GameSettings;
import controllers.PlayerMovement;
import controllers.ZombieController;
import java.util.ArrayList;
import static models.NPC.FRAME_COLS;

public class Zombie extends NPC {

    public static final double speed = 1.5; // 10 pixels per second.
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
//    @SuppressWarnings("rawtypes")
//    private Animation walkingUp, walkingDown, walkingRight, walkingLeft;
    private ZombieController controller;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Zombie(int startX, int startY, TiledMapTileLayer collisions) {
        texture = new Texture(Gdx.files.internal("sprite/zombie/2ZombieSpriteSheet.png"));
        region = TextureRegion.split(texture, texture.getWidth() / FRAME_COLS, texture.getHeight() / FRAME_ROWS);
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        controller = new ZombieController(collisions, this);
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
        walkingDown = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[0], walkFrames[1], walkFrames[2]);
        walkingUp = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[7], walkFrames[6], walkFrames[8]);
        walkingRight = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[3], walkFrames[4], walkFrames[5]);
        walkingLeft = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[10], walkFrames[9], walkFrames[11]);
    }

    public void detectPlayerPosition(Player p) {
        controller.setPlayerPosition(p.getX(), p.getY());
    }

    public void setPlayerMovements(ArrayList<PlayerMovement> movements) {
//        controller.setPlayerMovement(movements);
    }

    public void update(float delta, ArrayList<Zombie>zombies) {
        controller.update(delta,zombies);
    }

    public TextureRegion getZombies() {
        return sprite;
    }

    @SuppressWarnings("rawtypes")
    public Animation getLeft() {
        return walkingLeft;
    }

    @SuppressWarnings("rawtypes")
    public Animation getRight() {
        return walkingRight;
    }

    @SuppressWarnings("rawtypes")
    public Animation getUp() {
        return walkingUp;
    }

    @SuppressWarnings("rawtypes")
    public Animation getDown() {
        return walkingDown;
    }
    
    public void setDown(boolean value){
        controller.setDown(value);
    }
    
    public boolean getDownBlocked(){
        return controller.getDown();
    }
    
    public void setUp(boolean value){
        controller.setUp(value);
    }
    
    public boolean getUpBlocked(){
        return controller.getUp();
    }
    public void setRight(boolean value){
        controller.setRight(value);
    }
    
    public boolean getRightBlocked(){
        return controller.getRight();
    }
    public void setLeft(boolean value){
        controller.setLeft(value);
    }
    
    public boolean getLeftBlocked(){
        return controller.getRight();
    }

   
   
    
    
}
