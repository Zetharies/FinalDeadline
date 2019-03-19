package models;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
	
	/**
	 * <p> Method designed to define the inventory parameters, depending on the current map and its collisions
	 * 
	 * @param impCollisions TiledMap collisions of the current map
	 * @param currentMap Integer to represent the number of the current map
	 * 
	 */
	public void defineInventory(TiledMapTileLayer impCollisions, int currentMap) {
		collisions = impCollisions;
		addItemsToInventory();
		changeMap(currentMap);
		
	}

	/**
	 * <p> Method designed to create all of the item objects and add them to an Item Arraylist
	 */
	public void addItemsToInventory() {
		// Creates all of the item objects		
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
	
	/**
	 * <p> Method designed to set the number of drinks which should appear on the current map 
	 * 
	 * @param numDrinks Number of drinks to be added to map
	 */
	public void setDrinksOnMap(int numDrinks) {
		for (int i = 6; i <= (6 + numDrinks); i++) {
			inventory.get(i).setOnMap(true);
			inventory.get(i).setItemFound(false);
		}
		
		
	}
	
	/**
	 * <p> Method designed to add 15 drink items to the Item Arraylist 
	 * 
	 */
	public void addDrinks() {
		for (int i = 6; i <= (6 + 15); i++) {
			generateRandomPos();
			
			Drink tempDrink = new Drink(randomPos[0], randomPos[1]);
			tempDrink.setDrinkID(i);
			
			inventory.add(tempDrink);

		}	
	}

	/**
	 * <p> Method designed to set the specific items which should appear on the map and which should already appear within the inventory bar
	 * 
	 * @param impMapNumber Integer to show the number of the current map
	 */
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

	/**
	 * <p> Method designed to generate a X,Y position on the map, ensuring that the chosen position does not have a 'Blocked' property
	 */
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

	/**
	 * <p> Method designed to check if all of the Potion objects have been used appropriately
	 * 
	 * @return True/False depending if all of the Potion objects have been used
	 */
	public boolean allPotionsUsed() {
		boolean answer = false;
		
		if (inventory.get(3).getPotionUsed() == true && inventory.get(4).getPotionUsed() == true && inventory.get(5).getPotionUsed() == true) {
			answer = true;
			
		}
		
		return answer;
		
	}
	
    /**
     * <p> Method used to find the current position of the Drink object within the current Inventory
     * 
     * @param currentInv InventorySystem used to find the Drink object
     * @return Int of the position of the Drink object
     */
    public int findDrinkPosition() {
        int pos = 0;

        for (Item currentItem : inventory) {
            if (currentItem instanceof Drink) {
                pos = inventory.indexOf(currentItem);

                break;

            }

        }

        return pos;

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
