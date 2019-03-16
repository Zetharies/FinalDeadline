package models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class NPC {

    //x cord for sprite will be converted to int
    public float x;
    //y cord for sprite will be converted to int
    public float y;
    //start frame of sprite set to 6 up
    protected int currentFrame = 6;
    //health of all npc will be 100
    protected int health = 100;
    //used to set animations 
    protected Sprite sprite;
    protected Texture texture;
    protected TextureRegion region[][];
    //all npc that inherit npc class have 3 cols and 4 rows
    protected static final int FRAME_COLS = 3;
    protected static final int FRAME_ROWS = 4;
    //add individual sprites to walk frames 
    protected TextureRegion[] walkFrames;
    @SuppressWarnings("rawtypes")
    //index of walkframes added to animation - will be incremented in required classes
    protected Animation walkingUp, walkingDown, walkingRight, walkingLeft;

    /**
     * 
     * @return x cord of npc
     */
    public int getX() {
        return (int) x;
    }

    /**
     * 
     * @return y coord of npc 
     */
    public int getY() {
        return (int) y;
    }

    /**
     * decrement damage of npc when player applies combot
     * @param damage  amount to decrement boss
     */
    public void damage(int damage) {
        health -= damage;
    }
    
    /**
     * 
     * @return health of npc
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * set health used - when npc regens health
     * @param health 
     */
    public void setHealth(int health) {
        this.health = health;
    }

    public void die() {
    }

}
