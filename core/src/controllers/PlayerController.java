package controllers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import models.EnumPlayerFacing;
import models.Player;

import com.badlogic.gdx.Input.Keys;

public class PlayerController extends InputAdapter {

	private Player p;
	private boolean up, down, left, right;
	private TiledMapTileLayer collisions;

	public PlayerController(Player p, TiledMapTileLayer collisions) {
		this.p = p;
		this.collisions = collisions;
	}

	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Keys.UP || keycode == Keys.W) {
			up = true;
			/*
			 * if(isBlocked()) { p.movePlayer(0, 0); } else { p.movePlayer(0, 1); }
			 */
			// p.movePlayer(0, 1); // 0 on the x axis, 1 on the y axis
		}
		if (keycode == Keys.DOWN || keycode == Keys.S) {
			down = true;
			// p.movePlayer(0, -1); // 0 on the x axis, -1 on the y axis
		}
		if (keycode == Keys.LEFT || keycode == Keys.A) {
			left = true;
			// p.movePlayer(-1, 0); // -1 on the x axis, 0 on the y axis
		}
		if (keycode == Keys.RIGHT || keycode == Keys.D) {
			right = true;
			// p.movePlayer(1, 0); // 1 on the x axis, 0 on the y axis
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		if (keycode == Keys.UP || keycode == Keys.W) {
			up = false;
			// p.movePlayer(0, 1); // 0 on the x axis, 1 on the y axis
		}
		if (keycode == Keys.DOWN || keycode == Keys.S) {
			down = false;
			// p.movePlayer(0, -1); // 0 on the x axis, -1 on the y axis
		}
		if (keycode == Keys.LEFT || keycode == Keys.A) {
			left = false;
			// p.movePlayer(-1, 0); // -1 on the x axis, 0 on the y axis
		}
		if (keycode == Keys.RIGHT || keycode == Keys.D) {
			right = false;
			// p.movePlayer(1, 0); // 1 on the x axis, 0 on the y axis
		}

		return false;
	}

	public void update(float delta) {
		if (up) {
			if (!isUpBlocked()) {
				p.move(EnumPlayerFacing.UP);
				return;
			}
		}
		if (down) {
			if (!isDownBlocked()) {
				p.move(EnumPlayerFacing.DOWN);
				return;
			}
		}
		if (left) {
			if (!isLeftBlocked()) {
				p.move(EnumPlayerFacing.LEFT);
				return;
			}
		}
		if (right) {
			if (!isRightBlocked()) {
				p.move(EnumPlayerFacing.RIGHT);
				return;
			}
		}
	}

	/**
	 * 
	 * @param x              coordinate
	 * @param y              coordinate
	 * @param collisionLayer
	 * @return <code> boolean </code> true if the specified coordinates on the given
	 *         collisionLayer has the property "blocked", false otherwise.
	 */
	public boolean isBlocked(int x, int y, TiledMapTileLayer collisionLayer) {
		return collisionLayer.getCell(x, y).getTile().getProperties().containsKey("blocked");
	}

	/**
	 * 
	 * @return <code> boolean </code> true if the cell above the player is blocked,
	 *         false otherwise.
	 */
	public boolean isUpBlocked() {
		return isBlocked(p.getX(), p.getY() + 1, collisions);
	}

	/**
	 * 
	 * @return <code> boolean </code> true if the cell below the player is blocked,
	 *         false otherwise.
	 */
	public boolean isDownBlocked() {
		if (p.getY() - 1 >= 0) {
			return isBlocked(p.getX(), p.getY() - 1, collisions);
		}
		return true;
	}

	/**
	 * 
	 * @return <code> boolean </code> true if the cell to the left of the player is
	 *         blocked, false otherwise.
	 */
	public boolean isLeftBlocked() {
		if (p.getY() - 1 >= 0) {
			return isBlocked(p.getX() - 1, p.getY(), collisions);
		}
		return true;
	}

	/**
	 * 
	 * @return <code> boolean </code> true if the cell to the right of the player is
	 *         blocked, false otherwise.
	 */
	public boolean isRightBlocked() {
		return isBlocked(p.getX() + 1, p.getY(), collisions);
	}

}