package models;

import com.badlogic.gdx.Gdx;
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
     *
     * @param damage amount to decrement boss
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
     *
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * kill npc
     */
    public void die() {
    }

    /**
     * get sprite to render
     *
     * @return
     */
    public TextureRegion getSprite() {
        return sprite;
    }

    /**
     * get left animation - loop through left walking animation
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Animation getLeft() {
        return walkingLeft;
    }

    /**
     * get right animation - used to loop through right walking animation
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Animation getRight() {
        return walkingRight;
    }

    /**
     * get up animation - used to loop through up walking animation
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Animation getUp() {
        return walkingUp;
    }

    /**
     * get down animation - used to loop through down walking animation
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Animation getDown() {
        return walkingDown;
    }

    public void createSprite(int cols, int rows, String file, boolean split) {
        texture = new Texture(Gdx.files.internal(file));//load texture
        //split sprite sheet
        region = TextureRegion.split(texture, texture.getWidth() / cols, texture.getHeight() / rows);
        if (split) {
            walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
            int index = 0;
            for (int i = 0; i < FRAME_ROWS; i++) {
                for (int j = 0; j < FRAME_COLS; j++) {
                    walkFrames[index++] = region[i][j];
                }
            }
            sprite = new Sprite(walkFrames[currentFrame + 1]);//set starting animation
            sprite.setOriginCenter();
        }else{
            sprite = new Sprite(region[0][0]);
        }

    }

}
