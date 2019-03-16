package controllers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import models.Book;
import models.EnumPlayerFacing;
import models.InventorySystem;
import models.Keyboard;
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
	private boolean up, down, left, right, interact;
	private TiledMapTileLayer collisions;
	private boolean mapChange;
	private ArrayList<Book> books;
	private ArrayList<Keyboard> keyboards;

	private InventorySystem currentInv;	

	public PlayerController(Player p, TiledMapTileLayer collisions) {
		this.p = p;
		this.collisions = collisions;
		mapChange = false;
		books = new ArrayList<Book>();
		keyboards = new ArrayList<Keyboard>();
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
		if(keycode == Keys.SHIFT_LEFT) {
			interact = true;
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
		if(keycode == Keys.SHIFT_LEFT) {
			interact = false;
		}
		if (keycode == Keys.SPACE) {
			if (currentInv.getCurrentItem() == null) {
				System.out.println("No Item has been equipped");

			} else if (currentInv.getCurrentItem().getName() == "Book") {
				books.add(p.shootBook(p.getDirection(), p.getX(), p.getY()));

			} else if (currentInv.getCurrentItem().getName() == "Keyboard") {
				keyboards.add(p.shootKeyboard(p.getDirection(), p.getX(), p.getY()));

			} else if (currentInv.getCurrentItem().getName() == "Drink") {
				System.out.println("Drink Being Used");

				currentInv.getCurrentItem().setBeingPressed(true);


			} else if (currentInv.getCurrentItem().getName() == "Potion1") {
				System.out.println("Potion1 Being Used");

				currentInv.getCurrentItem().setBeingPressed(true);


			} else if (currentInv.getCurrentItem().getName() == "Potion2") {
				System.out.println("Potion2 Being Used");

				currentInv.getCurrentItem().setBeingPressed(true);


			} else if (currentInv.getCurrentItem().getName() == "Potion3") {
				System.out.println("Potion3 Being Used");

				currentInv.getCurrentItem().setBeingPressed(true);

			}

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

	public boolean isOnVent() {
		boolean answer = false;
		
		if (Gdx.input.isKeyPressed(Input.Keys.E)){
			if (p.getX() == 24 && p.getY() == 42) {
				return true;
				
			} else if (p.getX() == 25 && p.getY() == 42) {
				return true;
				
			}
		}
		
		return answer;
	}

	public InventorySystem equipItem(InventorySystem inventory) {
		currentInv = inventory;

		if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
			if (currentInv.getInventory().get(0).getFound() == true) {				
				currentInv.setAsCurrentItem(currentInv.getInventory().get(0));

				System.out.println("You have now equipped: " + currentInv.getCurrentItem().getName());

			} else {
				System.out.println("You have not found this item yet, " + currentInv.getInventory().get(0).getName());

			}

		} else if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
			if (currentInv.getInventory().get(1).getFound() == true) {				
				currentInv.setAsCurrentItem(currentInv.getInventory().get(1));

				System.out.println("You have now equipped: " + currentInv.getCurrentItem().getName());

			} else {
				System.out.println("You have not found this item yet, " + currentInv.getInventory().get(1).getName());

			}

		} else if(Gdx.input.isKeyPressed(Input.Keys.NUM_3)){
			if (currentInv.getInventory().get(2).getFound() == true) {				
				currentInv.setAsCurrentItem(currentInv.getInventory().get(2));

				System.out.println("You have now equipped: " + currentInv.getCurrentItem().getName());

			} else {								
				System.out.println("You have not found this item yet, " + currentInv.getInventory().get(2).getName());

			}

		} else if(Gdx.input.isKeyPressed(Input.Keys.NUM_4)){
			if (currentInv.getInventory().get(3).getFound() == true) {			
				currentInv.setAsCurrentItem(currentInv.getInventory().get(3));

				System.out.println("You have now equipped: " + currentInv.getCurrentItem().getName());

			} else {
				System.out.println("You have not found this item yet, " + currentInv.getInventory().get(3).getName());

			}

		} else if(Gdx.input.isKeyPressed(Input.Keys.NUM_5)){
			if (currentInv.getInventory().get(4).getFound() == true) {			
				currentInv.setAsCurrentItem(currentInv.getInventory().get(4));

				System.out.println("You have now equipped: " + currentInv.getCurrentItem().getName());

			} else {
				System.out.println("You have not found this item yet, " + currentInv.getInventory().get(4).getName());

			}
		} else if(Gdx.input.isKeyPressed(Input.Keys.NUM_6)){
			if (currentInv.getInventory().get(5).getFound() == true) {			
				currentInv.setAsCurrentItem(currentInv.getInventory().get(5));

				System.out.println("You have now equipped: " + currentInv.getCurrentItem().getName());

			} else {
				System.out.println("You have not found this item yet, " + currentInv.getInventory().get(5).getName());

			}
		}

		return currentInv;

	}

	public Item itemPressed() {
		if (currentInv.getCurrentItem() == null){
			return null;

		} else if (currentInv.getCurrentItem().checkBeingUsed() == false && currentInv.getCurrentItem().checkBeingPressed() == true) {
			currentInv.getCurrentItem().setBeingUsed(true);
			currentInv.getCurrentItem().setBeingPressed(false);

			return currentInv.getCurrentItem();

		} else {
			return null;

		}

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

	public ArrayList<Keyboard> getKeyboards() {
		return keyboards;
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

	public boolean getInteract() {
		return interact;
	}

	public void setInteractFalse() {
		interact = false;
	}
}
