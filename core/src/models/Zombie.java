package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.GameSettings;
import controllers.ZombieController;

public class Zombie {

    public static final float speed = 1.0f; // 10 pixels per second.
    public float x;
    public float y;
    private int currentFrame = 6;
    private Sprite sprite;
    private Texture texture;
    private TextureRegion region[][];
    private static final int FRAME_COLS = 3;
    private static final int FRAME_ROWS = 4;
    private TextureRegion[] walkFrames;
    private Animation walkingUp;
    private Animation walkingDown;
    private Animation walkingRight;
    private Animation walkingLeft;
    private ZombieController controller;

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
        x = (float) startX;
        y = (float) startY;
        walkingDown = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[0], walkFrames[1], walkFrames[2]);
        walkingUp = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[6], walkFrames[7], walkFrames[8]);
        walkingRight = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[3], walkFrames[4], walkFrames[5]);
        walkingLeft = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[9], walkFrames[10], walkFrames[11]);
    }

    public void update(float delta) {
        controller.update(delta);
    }

    public TextureRegion getZombies() {
        return sprite;
    }

    public Animation getLeft() {
        return walkingLeft;
    }

    public Animation getRight() {
        return walkingRight;
    }

    public Animation getUp() {
        return walkingUp;
    }

    public Animation getDown() {
        return walkingDown;
    }

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

}
