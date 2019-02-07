package inventory;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.GameSettings;

import inventory.items.Book;
import inventory.items.ChaxID;
import inventory.items.Item;
import inventory.items.Keyboard;
import inventory.items.Torch;

public class InventorySystem{

	private ArrayList<Item> inventory;

	private Random rnd; 

	private TiledMapTileLayer collisions;
	
	private int[] randomPos;

	public InventorySystem(TiledMapTileLayer collisions) {
		inventory = new ArrayList<Item>();
		
		randomPos = new int[2];

		rnd = new Random();

		this.collisions = collisions;

		addItemsToInventory();

	}

	public void addItemsToInventory() {
		inventory.add(new Torch(0, 0)); //PRESET LOCATION
		
		generateRandomPos();
		inventory.add(new Book(randomPos[0], randomPos[1])); //USES RANDOM LOCATION
		
		inventory.add(new ChaxID(0, 0)); //PRESET LOCATION

		//generateRandomPos();
		inventory.add(new Keyboard(0, 0)); //USES RANDOM LOCATION

	}

	public ArrayList<Item> getInventory() {
		return inventory;

	}

	public ArrayList<Item> getMapItems() {
		ArrayList<Item> currentMapItems = new ArrayList<Item>();

		for (Item currentItem: inventory) {
			if (currentItem.checkOnMap() == true) {				
				currentMapItems.add(currentItem);

			}

		}
		
		return currentMapItems;
		
	}
	
	public void generateRandomPos() {
		int randX = rnd.nextInt((int) GameSettings.SCALED_TILE_SIZE);
		int randY = rnd.nextInt((int) GameSettings.SCALED_TILE_SIZE);
		
		while (collisions.getCell(randX, randY).getTile().getProperties().containsKey("blocked")) {			
			randX = rnd.nextInt((int) GameSettings.SCALED_TILE_SIZE);
			randY = rnd.nextInt((int) GameSettings.SCALED_TILE_SIZE);
			
		}
		
		randomPos[0] = randX;
		randomPos[1] = randY;
		
		System.out.println("Position Found");
		System.out.println("Chosen InventorySystem CurrentX: " + randX);
		System.out.println("Chosen InventorySystem CurrentY: " + randY);
		
	}

	public void changeToMap0() {
		inventory.get(0).setOnMap();
		inventory.get(1).setNotOnMap();
		inventory.get(2).setNotOnMap();
		inventory.get(3).setNotOnMap();
		
		inventory.get(0).itemNotFound();
		inventory.get(1).itemNotFound();
		inventory.get(2).itemNotFound();
		inventory.get(3).itemNotFound();

	}

	public void changeToMap1() {
		inventory.get(0).setNotOnMap();
		inventory.get(1).setOnMap();
		inventory.get(2).setNotOnMap();
		inventory.get(3).setNotOnMap();
		
		inventory.get(0).itemNotFound();
		inventory.get(1).itemNotFound();
		inventory.get(2).itemNotFound();
		inventory.get(3).itemNotFound();

	}

}
