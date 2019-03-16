package models;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.game.GameSettings;

import models.inventory.Book;
import models.inventory.Drink;
import models.inventory.Item;
import models.inventory.Keyboard;
import models.inventory.Potion1;
import models.inventory.Potion2;
import models.inventory.Potion3;

public class InventorySystem{

	private ArrayList<Item> inventory;

	private Random rnd; 

	private TiledMapTileLayer collisions;

	private int[] randomPos;

	private Item currentItem;
	
	private int mapNumber;
	
	private Boolean drinkDrawn;

	public InventorySystem() {	
		inventory = new ArrayList<Item>();

		randomPos = new int[2];

		rnd = new Random();

		currentItem = null;
		
		drinkDrawn = false;

	}
	
	
	public void defineInventory(TiledMapTileLayer impCollisions, int currentMap) {
		collisions = impCollisions;
		addItemsToInventory();
		changeMap(currentMap);
		
	}

	public void addItemsToInventory() {
		// Creates all of the item objects		
		//		generateRandomPos();
		//		Book book = new Book(randomPos[0], randomPos[1]);

		generateRandomPos();
		Book book = new Book(randomPos[0], randomPos[1]);

		generateRandomPos();
		Keyboard keyboard = new Keyboard(randomPos[0], randomPos[1]);

		generateRandomPos();
		Drink drink = new Drink(randomPos[0], randomPos[1]);
		drink.setDrinkID(2);

		generateRandomPos();
		Potion1 firstPotion = new Potion1(randomPos[0], randomPos[1]);

		generateRandomPos();
		Potion2 secondPotion = new Potion2(randomPos[0], randomPos[1]);

		generateRandomPos();
		Potion3 thirdPotion = new Potion3(randomPos[0], randomPos[1]);

		// Adds items to inventory arraylist depending on Item ID
		inventory.add(book.getID(), book);
		inventory.add(keyboard.getID(), keyboard);
		inventory.add(drink.getID(), drink);	
		inventory.add(firstPotion.getID(), firstPotion);
		inventory.add(secondPotion.getID(), secondPotion);
		inventory.add(thirdPotion.getID(), thirdPotion);	
		
		addDrinks();

	}
	
	// Choose number of drinks which should appear on the map (x <= 15)
	public void setDrinksOnMap(int numDrinks) {
		for (int i = 6; i <= (6 + numDrinks); i++) {
			inventory.get(i).setOnMap(true);
			inventory.get(i).setItemFound(false);
		}
		
		
	}
	
	// Choose max number of drinks which can be added to a map
	public void addDrinks() {
		for (int i = 6; i <= (6 + 15); i++) {
			generateRandomPos();
			
			Drink tempDrink = new Drink(randomPos[0], randomPos[1]);
			tempDrink.setDrinkID(i);
			
			inventory.add(tempDrink);

		}	
		

		
	}

	public void changeMap(int impMapNumber) {
		mapNumber = impMapNumber;
		
		// TESTING
		if (mapNumber == -1) {
			inventory.get(0).setOnMap(true);
			inventory.get(1).setOnMap(true);
			inventory.get(2).setOnMap(true);
			inventory.get(3).setOnMap(true);
			inventory.get(4).setOnMap(true);
			inventory.get(5).setOnMap(true);

			inventory.get(0).setItemFound(false);
			inventory.get(1).setItemFound(false);
			inventory.get(2).setItemFound(false);
			inventory.get(3).setItemFound(false);
			inventory.get(4).setItemFound(false);
			inventory.get(5).setItemFound(false);
			
			setDrinksOnMap(11);
			
		// OUTSIDE
		} else if (mapNumber == 0) {
			inventory.get(0).setOnMap(false);
			inventory.get(1).setOnMap(false);
			inventory.get(2).setOnMap(false);
			inventory.get(3).setOnMap(false);
			inventory.get(4).setOnMap(false);
			inventory.get(5).setOnMap(false);

			inventory.get(0).setItemFound(false);
			inventory.get(1).setItemFound(false);
			inventory.get(2).setItemFound(false);
			inventory.get(3).setItemFound(false);
			inventory.get(4).setItemFound(false);
			inventory.get(5).setItemFound(false);

		// CAFETERIA
		} else if (mapNumber == 1) {
			inventory.get(0).setOnMap(false);
			inventory.get(1).setOnMap(false);
			inventory.get(2).setOnMap(false);
			inventory.get(3).setOnMap(false);
			inventory.get(4).setOnMap(false);
			inventory.get(5).setOnMap(false);
			
			inventory.get(0).setItemFound(false);
			inventory.get(1).setItemFound(false);
			inventory.get(2).setItemFound(false);
			inventory.get(3).setItemFound(false);
			inventory.get(4).setItemFound(false);
			inventory.get(5).setItemFound(false);

		// LIBRARY	
		} else if (mapNumber == 2) {
			inventory.get(0).setOnMap(true);
			inventory.get(1).setOnMap(true);
			inventory.get(2).setOnMap(true);
			inventory.get(3).setOnMap(false);
			inventory.get(4).setOnMap(false);
			inventory.get(5).setOnMap(false);
			
			inventory.get(0).setItemFound(false);
			inventory.get(1).setItemFound(false);
			inventory.get(2).setItemFound(false);
			inventory.get(3).setItemFound(false);
			inventory.get(4).setItemFound(false);
			inventory.get(5).setItemFound(false);
			
			setDrinksOnMap(3);

		// ENGINEERING
		} else if (mapNumber == 3) {
			inventory.get(0).setOnMap(false);
			inventory.get(1).setOnMap(false);
			inventory.get(2).setOnMap(true);
			inventory.get(3).setOnMap(false);
			inventory.get(4).setOnMap(false);
			inventory.get(5).setOnMap(false);
			
			inventory.get(0).setItemFound(true);
			inventory.get(1).setItemFound(true);
			inventory.get(2).setItemFound(false);
			inventory.get(3).setItemFound(false);
			inventory.get(4).setItemFound(false);
			inventory.get(5).setItemFound(false);
			
			setDrinksOnMap(6);
		
		// BOSS MAP 1
		} else if (mapNumber == 4) {
			inventory.get(0).setOnMap(false);
			inventory.get(1).setOnMap(false);
			inventory.get(2).setOnMap(true);
			inventory.get(3).setOnMap(false);
			inventory.get(4).setOnMap(false);
			inventory.get(5).setOnMap(false);
			
			inventory.get(0).setItemFound(true);
			inventory.get(1).setItemFound(true);
			inventory.get(2).setItemFound(false);
			inventory.get(3).setItemFound(false);
			inventory.get(4).setItemFound(false);
			inventory.get(5).setItemFound(false);
			
			setDrinksOnMap(0);

		// OPTOMETRY	
		} else if (mapNumber == 5) {
			inventory.get(0).setOnMap(false);
			inventory.get(1).setOnMap(false);
			inventory.get(2).setOnMap(true);
			inventory.get(3).setOnMap(false);
			inventory.get(4).setOnMap(false);
			inventory.get(5).setOnMap(false);
			
			inventory.get(0).setItemFound(true);
			inventory.get(1).setItemFound(true);
			inventory.get(2).setItemFound(false);
			inventory.get(3).setItemFound(false);
			inventory.get(4).setItemFound(false);
			inventory.get(5).setItemFound(false);
			
			setDrinksOnMap(6);

		// BOSS MAP
		} else if (mapNumber == 6) {
			inventory.get(0).setOnMap(false);
			inventory.get(1).setOnMap(false);
			inventory.get(2).setOnMap(true);
			inventory.get(3).setOnMap(false);
			inventory.get(4).setOnMap(false);
			inventory.get(5).setOnMap(false);
			
			inventory.get(0).setItemFound(true);
			inventory.get(1).setItemFound(true);
			inventory.get(2).setItemFound(false);
			inventory.get(3).setItemFound(false);
			inventory.get(4).setItemFound(false);
			inventory.get(5).setItemFound(false);

		// BIOLOGY
		} else if (mapNumber == 7) {
			inventory.get(0).setOnMap(false);
			inventory.get(1).setOnMap(false);
			inventory.get(2).setOnMap(true);
			inventory.get(3).setOnMap(true);
			inventory.get(4).setOnMap(true);
			inventory.get(5).setOnMap(true);
			
			inventory.get(0).setItemFound(true);
			inventory.get(1).setItemFound(true);
			inventory.get(2).setItemFound(false);
			inventory.get(3).setItemFound(false);
			inventory.get(4).setItemFound(false);
			inventory.get(5).setItemFound(false);
			
			setDrinksOnMap(3);

		// CHAX MAP
		} else if (mapNumber == 8) {
			inventory.get(0).setOnMap(false);
			inventory.get(1).setOnMap(false);
			inventory.get(2).setOnMap(false);
			inventory.get(3).setOnMap(false);
			inventory.get(4).setOnMap(false);
			inventory.get(5).setOnMap(false);
			
			inventory.get(0).setItemFound(true);
			inventory.get(1).setItemFound(true);
			inventory.get(2).setItemFound(false);
			inventory.get(3).setItemFound(false);
			inventory.get(4).setItemFound(false);
			inventory.get(5).setItemFound(false);

		}

	}

	public void generateRandomPos() {
		int randX = rnd.nextInt((int) GameSettings.SCALED_TILE_SIZE);
		int randY = rnd.nextInt((int) GameSettings.SCALED_TILE_SIZE);
		
		//collisions.getCell(randX, randY).setTile(blockedTile);

		while (collisions.getCell(randX, randY).getTile().getProperties().containsKey("blocked")) {			
			randX = rnd.nextInt((int) GameSettings.SCALED_TILE_SIZE);
			randY = rnd.nextInt((int) GameSettings.SCALED_TILE_SIZE);

		}

		randomPos[0] = randX;
		randomPos[1] = randY;

	}

	public void setAsCurrentItem(Item newItem) {
		currentItem = newItem;

	}
	
	public void setDrinkDrawn(Boolean choice) {
		drinkDrawn = choice;
		
	}
	
	
	public Boolean getDrinkDrawn() {
		return drinkDrawn;
	}
	
	public int getMapNumber() {
		return mapNumber;
		
	}
	
	public Item getCurrentItem() {
		return currentItem;

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

	public ArrayList<Item> getHUDItems() {
		ArrayList<Item> currentHUDItems = new ArrayList<Item>();

		for (Item currentItem: inventory) {
			if (currentItem.getFound() == true) {				
				currentHUDItems.add(currentItem);

			}

		}

		return currentHUDItems;
	}

}
