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
		Book book = new Book(88, 5);

		generateRandomPos();
		Keyboard keyboard = new Keyboard(89, 5);

		generateRandomPos();
		Drink drink = new Drink(90, 5);
		drink.setDrinkID(2);

		generateRandomPos();
		Potion1 firstPotion = new Potion1(91, 5);

		generateRandomPos();
		Potion2 secondPotion = new Potion2(92, 5);

		generateRandomPos();
		Potion3 thirdPotion = new Potion3(93, 5);

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
		
		if (mapNumber == 0) {
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
			
			setDrinksOnMap(5);

		} else if (mapNumber == 1) {
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
			
			setDrinksOnMap(5);

		} else if (mapNumber == 2) {
			inventory.get(0).setOnMap(false);
			inventory.get(1).setOnMap(false);
			inventory.get(2).setOnMap(false);
			inventory.get(3).setOnMap(true);
			inventory.get(4).setOnMap(true);
			inventory.get(5).setOnMap(true);
			
			inventory.get(0).setItemFound(true);
			inventory.get(1).setItemFound(true);
			inventory.get(2).setItemFound(false);
			inventory.get(3).setItemFound(false);
			inventory.get(4).setItemFound(false);
			inventory.get(5).setItemFound(false);
			
			setDrinksOnMap(5);

			// TEST NUMBER
		} else if (mapNumber == 100) {
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
