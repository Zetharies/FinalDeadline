/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package junit.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import models.InventorySystem;
import models.inventory.Book;
import models.inventory.Drink;
import models.inventory.Keyboard;
import models.inventory.Potion1;
import models.inventory.Potion2;
import models.inventory.Potion3;

public class InventorySystemTest {
	
	private InventorySystem invSystem;
	
	public InventorySystemTest() {
		invSystem = new InventorySystem();
		
		testAddItemsToInventory();
	
	}
	
	@Test
	public void testAddItemsToInventory() {
		Book book = new Book(0, 0, "");
		Keyboard keyboard = new Keyboard(0, 0, "");
		Drink drink = new Drink(0, 0, "");
		Potion1 firstPotion = new Potion1(0, 0, "");
		Potion2 secondPotion = new Potion2(0, 0, "");
		Potion3 thirdPotion = new Potion3(0, 0, "");

		// Adds items to inventory arraylist depending on Item ID
		invSystem.getInventory().add(book.getID(), book);
		invSystem.getInventory().add(keyboard.getID(), keyboard);
		invSystem.getInventory().add(drink.getID(), drink);	
		invSystem.getInventory().add(firstPotion.getID(), firstPotion);
		invSystem.getInventory().add(secondPotion.getID(), secondPotion);
		invSystem.getInventory().add(thirdPotion.getID(), thirdPotion);	
		
		for (int i = 6; i <= (6 + 15); i++) {
			Drink tempDrink = new Drink(0, 0, "");
			tempDrink.setDrinkID(i);
			
			invSystem.getInventory().add(tempDrink);

		}	
		
		assertTrue(invSystem.getInventory().get(0) instanceof Book);
		assertTrue(invSystem.getInventory().get(1) instanceof Keyboard);
		assertTrue(invSystem.getInventory().get(2) instanceof Drink);
		assertTrue(invSystem.getInventory().get(3) instanceof Potion1);
		assertTrue(invSystem.getInventory().get(4) instanceof Potion2);
		assertTrue(invSystem.getInventory().get(5) instanceof Potion3);
	}
	
	@Test
	public void testAllPotionsUsed() {
		assertEquals(invSystem.allPotionsUsed(), false);
		
	}

	@Test 
	public void testFindDrinkPosition() {
		assertEquals(invSystem.findDrinkPosition(), 2);
		
	}

	@Test
	public void testDrinksOnMap() {
		int numDrinks = 5;
		
		invSystem.setDrinksOnMap(numDrinks);
		
		for (int i = 6; i <= (6 + numDrinks); i++) {
			assertEquals(invSystem.getInventory().get(i).checkOnMap(), true);
			assertEquals(invSystem.getInventory().get(i).getFound(), false);
		}
	}
	
}
