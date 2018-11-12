package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.mygdx.game.GameSettings;
import managers.SoundManager;

public class Player {

    private int x, y;
    private EnumPlayerFacing direction;
    private EnumPlayerState state;
    private float linearX, linearY, walkTimer, animationTimer, refacingTimer;
    private int srcX, srcY, destX, destY;
    private boolean moveFrame;
    private AnimationSet animations;
    private Sound sound;

    public Player(int x, int y, AnimationSet animations) {
        super();
        this.x = x;
        this.y = y;
        this.animations = animations;
        this.linearX = x;
        this.linearY = y;
        this.state = EnumPlayerState.STANDING;
        this.direction = EnumPlayerFacing.S;
    }

    public void update(float delta) {
        if (state == EnumPlayerState.REFACING) {
            refacingTimer += delta;
            if (refacingTimer > GameSettings.REFACING_TIME) {
                refacingTimer = 0f;
                state = EnumPlayerState.STANDING;
            }
        }
        if (state == EnumPlayerState.WALKING) {
            walkTimer += delta * 2.05;
            animationTimer += delta * 2.05;
            linearX = Interpolation.linear.apply(srcX, destX, walkTimer / GameSettings.TIME_PER_TILE);
            linearY = Interpolation.linear.apply(srcY, destY, walkTimer / GameSettings.TIME_PER_TILE);
            if (walkTimer > GameSettings.TIME_PER_TILE) {
                animationTimer -= (walkTimer - GameSettings.TIME_PER_TILE);
                finishMove();
                if (moveFrame) {
                    move(direction);
                } else {
                    walkTimer = 0f;
                }
            }
        }

        moveFrame = false;
    }

    public void reface(EnumPlayerFacing dir) {
        if (direction != dir) {
            direction = dir;
        }
        state = EnumPlayerState.REFACING;
    }

    public boolean move(EnumPlayerFacing dir) {
        if (state == EnumPlayerState.WALKING) {
            if (direction == dir) {
                moveFrame = true;
            }
            return false;
        }
        if (dir != direction || state == EnumPlayerState.REFACING) {
            reface(dir);
            return false;
        }
        // sprite movement
        initializeMove(dir);

        // increments x and y after moving
        x += dir.getX();
        y += dir.getY();
        System.out.println("X, Y: " + x + ", " + y);
        return true;
    }

    private void initializeMove(EnumPlayerFacing dir) {
        this.direction = dir;
        this.srcX = x;
        this.srcY = y;
        this.destX = srcX + dir.getX();
        this.destY = srcY + dir.getY();
        this.linearX = x;
        this.linearY = y;
        walkTimer = 0f;
        state = EnumPlayerState.WALKING;
        if (SoundManager.getSound()) {
            sound = Gdx.audio.newSound(Gdx.files.internal("fx/footsteps.mp3"));
            sound.play(0.1F);
        }

    }

    private void finishMove() {
        if (SoundManager.getSound()) {
            sound.pause();
        }
        state = EnumPlayerState.STANDING;
        this.linearX = x;
        this.linearY = y;
        this.srcX = 0;
        this.srcY = 0;
        this.destX = 0;
        this.destY = 0;
    }

    // method not used
    public void movePlayer(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public float getLinearX() {
        return linearX;
    }

    public float getLinearY() {
        return linearY;
    }

    public TextureRegion getSprite() {
        switch (state) {
            case WALKING:
                return (TextureRegion) animations.getWalking(direction).getKeyFrame(animationTimer);
            case STANDING:
                return animations.getStanding(direction);
            case REFACING:
                return animations.getStanding(direction);
            default:
                return animations.getStanding(EnumPlayerFacing.N);
        }
    }

    /**
     *
     * @return <code> int </code> value of x.
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return <code> int </code> value of y.
     */
    public int getY() {
        return y;
    }
}
