package models;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.GameSettings;
import controllers.ZombieController;
import java.util.ArrayList;

public class Zombie extends NPC {

	public static final double SPEED = 1.5; // speed for zombie to move
	private ZombieController controller;// assoc controller
	private static final int FRAME_COLS = 3;
	private static final int FRAME_ROWS = 4;
	private boolean moved = false;
	private int timer = 0;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	/**
	 * construct zombie object - link with assoc controller + animations
	 */
	public Zombie(int startX, int startY, TiledMapTileLayer collisions) {
		controller = new ZombieController(collisions, this);
		createSprite(FRAME_COLS, FRAME_ROWS, "sprite/zombie/2ZombieSpriteSheet.png", true);
		x = startX;
		y = startY;
		walkingDown = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[0], walkFrames[1], walkFrames[2]);
		walkingUp = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[7], walkFrames[6], walkFrames[8]);
		walkingRight = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[3], walkFrames[4], walkFrames[5]);
		walkingLeft = new Animation(GameSettings.TIME_PER_TILE / 2f, walkFrames[10], walkFrames[9], walkFrames[11]);
	}

	public Zombie(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void detectPlayerPosition(Player p) {
		controller.setPlayerPosition(p.getX(), p.getY());
	}

	public void update(float delta, ArrayList<Zombie> zombies) {
		controller.update(delta, zombies);
	}

	/**
	 * set down collision true/false
	 *
	 * @param value
	 */
	public void setDown(boolean value) {
		controller.setDown(value);
	}

	/**
	 * get down collision true/false
	 *
	 * @param value
	 */
	public boolean getDownBlocked() {
		return controller.getDown();
	}

	/**
	 * set up collision true/false
	 *
	 * @param value
	 */
	public void setUp(boolean value) {
		controller.setUp(value);
	}

	/**
	 * get up collision true/false
	 *
	 * @param value
	 */
	public boolean getUpBlocked() {
		return controller.getUp();
	}

	/**
	 * set right collision true/false
	 *
	 * @param value
	 */
	public void setRight(boolean value) {
		controller.setRight(value);
	}

	/**
	 * get right collision true/false
	 *
	 * @param value
	 */
	public boolean getRightBlocked() {
		return controller.getRight();
	}

	/**
	 * set left collision true/false
	 *
	 * @param value
	 */
	public void setLeft(boolean value) {
		controller.setLeft(value);
	}

	/**
	 * get left collision true/false
	 *
	 * @param value
	 */
	public boolean getLeftBlocked() {
		return controller.getRight();
	}

	public double getSpeed() {
		return SPEED;
	}

	public TiledMapTileLayer getCollisions() {
		return controller.getCollisions();
	}

	public void setMoved(boolean value) {
		this.moved = value;
	}

	public boolean getMoved() {
		return moved;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getTimer() {
		return timer;
	}

}
