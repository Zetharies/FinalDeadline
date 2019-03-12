package controllers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import models.Book;
import models.EnumPlayerFacing;
import models.InventorySystem;
import models.Player;
import models.Zombie;
import models.inventory.Item;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import managers.SettingsManager;

public class PlayerController extends InputAdapter {

	private ArrayList<PlayerMovement> movements;
	private Player p;
	private boolean up, down, left, right;
	private TiledMapTileLayer collisions;
	private boolean mapChange;
	private ArrayList<Book> books;

	private InventorySystem currentInv;

	public PlayerController(Player p, TiledMapTileLayer collisions) {
		this.p = p;
		this.collisions = collisions;
		mapChange = false;
		books = new ArrayList<Book>();
		movements = new ArrayList<PlayerMovement>();

		currentInv = new InventorySystem();

	}

	@Override
	public boolean keyDown(int keycode) {
		if (SettingsManager.KEYS) {
			if (keycode == Keys.UP) {
				up = true;
			}
		} else if (SettingsManager.WASD) {
			if (keycode == Keys.W) {
				up = true;
			}
		}
		//        if (SettingsManager.KEYS) {
		//            if (keycode == Keys.UP) {
		//                up = true;
		//            }
		//        } else if (SettingsManager.WASD) {
		//            if (keycode == Keys.W) {
		//                up = true;
		//            }
		//        }
		if (SettingsManager.KEYS) {
			if (keycode == Keys.DOWN) {
				down = true;
			}
		} else if (SettingsManager.WASD) {
			if (keycode == Keys.S) {
				down = true;
			}
		}

		if (SettingsManager.KEYS) {
			if (keycode == Keys.LEFT) {
				left = true;
			}
		} else if (SettingsManager.WASD) {
			if (keycode == Keys.A) {
				left = true;
			}
		}

		if (SettingsManager.KEYS) {
			if (keycode == Keys.RIGHT ) {
				right = true;
			}
		} else if (SettingsManager.WASD) {
			if (keycode == Keys.D) {
				right = true;
			}
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
		if (keycode == Keys.SPACE) {
			books.add(p.shoot(p.getDirection(), p.getX(), p.getY()));
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
	 * @param x coordinate
	 * @param y coordinate
	 * @param collisionLayer
	 * @return <code> boolean </code> true if the specified coordinates on the
	 * given collisionLayer has the property "blocked", false otherwise.
	 */
	public boolean isBlocked(int x, int y, TiledMapTileLayer collisionLayer) {
		return collisionLayer.getCell(x, y).getTile().getProperties().containsKey("blocked");
		// return false;
	}

	/**
	 *
	 * @return <code> boolean </code> true if the cell above the player is
	 * blocked, false otherwise.
	 */
	public boolean isUpBlocked() {
		return isBlocked(p.getX(), p.getY() + 1, collisions);
		// return false;
	}

	/**
	 *
	 * @return <code> boolean </code> true if the cell below the player is
	 * blocked, false otherwise.
	 */
	public boolean isDownBlocked() {
		if (p.getY() - 1 >= 0) {
			return isBlocked(p.getX(), p.getY() - 1, collisions);
			// return false;
		}
		return true;
		// return false;
	}

	/**
	 *
	 * @return <code> boolean </code> true if the cell to the left of the player
	 * is blocked, false otherwise.
	 */
	public boolean isLeftBlocked() {
		if (p.getY() - 1 >= 0) {
			return isBlocked(p.getX() - 1, p.getY(), collisions);
			// return false;
		}
		return true;
		// return false;
	}

	/**
	 *
	 * @return <code> boolean </code> true if the cell to the right of the
	 * player is blocked, false otherwise.
	 */
	public boolean isRightBlocked() {
		return isBlocked(p.getX() + 1, p.getY(), collisions);
		// return false;
	}

	public boolean isOnZombie(ArrayList<Zombie> zombies) {
		for(Zombie zombie : zombies) {
			if(zombie.getX() == p.getX() && zombie.getY() == p.getY()) {
				return true;
			}
		}
		return false;
	}

	//BHAVEN EDIT<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	public boolean isOnItem(Item collidedItem) {    	
		if(collidedItem.getX() == p.getX() && collidedItem.getY() == p.getY() && Gdx.input.isKeyPressed(Input.Keys.E)) {
			return true;

		} else {
			return false;

		}

	}

	public Item equipItem(InventorySystem inventory) {
		ArrayList<Item> currentALInventory = inventory.getInventory();

		if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
			if (currentALInventory.get(0).getFound() == true) {				
				inventory.setAsCurrentItem(currentALInventory.get(0));

				System.out.println("You have now equipped: " + inventory.getCurrentItem().getName());

			} else {
				System.out.println("You have not found this item yet, " + currentALInventory.get(0).getName());

			}

		} else if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
			if (currentALInventory.get(1).getFound() == true) {				
				inventory.setAsCurrentItem(currentALInventory.get(1));

				System.out.println("You have now equipped: " + inventory.getCurrentItem().getName());

			} else {
				System.out.println("You have not found this item yet, " + currentALInventory.get(1).getName());

			}

		} else if(Gdx.input.isKeyPressed(Input.Keys.NUM_3)){
			if (currentALInventory.get(2).getFound() == true) {				
				inventory.setAsCurrentItem(currentALInventory.get(2));

				System.out.println("You have now equipped: " + inventory.getCurrentItem().getName());

			} else {
				System.out.println("You have not found this item yet, " + currentALInventory.get(2).getName());

			}

		} else if(Gdx.input.isKeyPressed(Input.Keys.NUM_4)){
			if (currentALInventory.get(3).getFound() == true) {			
				inventory.setAsCurrentItem(currentALInventory.get(3));

				System.out.println("You have now equipped: " + inventory.getCurrentItem().getName());

			} else {
				System.out.println("You have not found this item yet, " + currentALInventory.get(3).getName());

			}

		} else if(Gdx.input.isKeyPressed(Input.Keys.NUM_5)){
			if (currentALInventory.get(4).getFound() == true) {			
				inventory.setAsCurrentItem(currentALInventory.get(4));

				System.out.println("You have now equipped: " + inventory.getCurrentItem().getName());

			} else {
				System.out.println("You have not found this item yet, " + currentALInventory.get(4).getName());

			}
		} else if(Gdx.input.isKeyPressed(Input.Keys.NUM_6)){
			if (currentALInventory.get(5).getFound() == true) {			
				inventory.setAsCurrentItem(currentALInventory.get(5));

				System.out.println("You have now equipped: " + inventory.getCurrentItem().getName());

			} else {
				System.out.println("You have not found this item yet, " + currentALInventory.get(5).getName());

			}
		}

		return inventory.getCurrentItem();

	}

	public void setInventory(InventorySystem impInv) {
		currentInv = impInv;

	}
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public void updatePlayerCoordinates(int x, int y) {
		p.updateCoordinates(x, y);
	}

	public boolean getMapChange() {
		return mapChange;
	}

	public void setMapChange(boolean value) {
		mapChange = value;
	}

	public boolean checkExit(ArrayList<ArrayList<Integer>> exits) {
		for(ArrayList<Integer> exit : exits) {
			if(exit.get(0) == p.getX() && exit.get(1) == p.getY()) {
				return true;
			}
		}
		return false;
	}

	public void setCollisions(TiledMapTileLayer collisions) {
		this.collisions = collisions;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisions;
	}

	public ArrayList<Book> getBooks() {
		return books;
	}

	public void resetDirection() {
		up = false;
		down = false;
		left = false;
		right = false;
	}

	public Player getPlayer() {
		return p;
	}

	public ArrayList<PlayerMovement> getPlayerMovements(){
		return movements;
	}
}
