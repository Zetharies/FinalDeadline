package models.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item{

	private String itemName, atlasImage;
	
	private Texture texture;

	private int id, x, y, invX, invBoxX, drinkID;

	protected boolean found, onMap;

	private boolean invDrawn, beingUsed, beingPressed, potionUsed;

	/**
	 * <p> Constructor used for to define an item, is inherited by sub-classes of the Items
	 * 
	 * @param impName Name of the Item
	 * @param startX X Position of Item on the Map
	 * @param startY Y Position of Item on the Map
	 * @param impID ID of the Item
	 * @param texturePath Path of Texture of the Item
	 * @param atlasImage Path for the Atlas Image for the Item
	 * @param invX X Position of Item within the Inventory Bar
	 * @param invBoxX X Position of the Equip box of the item within the Inventory Bar
	 */
	public Item(String impName, int startX, int startY, int impID, String texturePath, String atlasImage, int invX, int invBoxX) {
		itemName = impName;

		id = impID;
		x = startX;
		y = startY;
		this.invX = invX;
		this.invBoxX = invBoxX;

		texture = new Texture(Gdx.files.internal(texturePath));

		found = false;
		onMap = false;
		invDrawn = false;
		beingUsed = false;
		beingPressed = false;
		
		this.atlasImage = atlasImage;	
		
		drinkID = 0;
		
		potionUsed = false;
	}

	/**
	 * <p> Constructor used for testing purposes. Not able to use previous constructor as you are not able to use 
	 * 	   majority of LibGDX before it is initialized. This throws an error when trying to use the Texture files
	 * 
	 * @param impName Name of the Item
	 * @param startX X Position of Item on the Map
	 * @param startY Y Position of Item on the Map
	 * @param impID ID of the Item
	 * @param atlasImage Path for the Atlas Image for the Item
	 * @param invX X Position of Item within the Inventory Bar
	 * @param invBoxX X Position of the Equip box of the item within the Inventory Bar
	 */
	public Item(String impName, int startX, int startY, int impID, String atlasImage, int invX, int invBoxX) {
		itemName = impName;

		id = impID;
		x = startX;
		y = startY;
		this.invX = invX;
		this.invBoxX = invBoxX;

		found = false;
		onMap = false;
		invDrawn = false;
		beingUsed = false;
		beingPressed = false;
		
		this.atlasImage = atlasImage;	
		
		drinkID = 0;
		
		potionUsed = false;
	}
	
	/**
	 * <p> Method overridden by the sub-classes of each Item object
	 * 
	 * @param batch SpriteBatch used to draw each of the items
	 */
	public void render(SpriteBatch batch) {

	}

	
	public void setPotionUsed(Boolean choice) {
		potionUsed = choice;

	}

	public Boolean getPotionUsed() {
		return potionUsed;

	}
		
	public void setDrinkID(int choice) {
		drinkID = choice;
	}
	
	public int getDrinkID() {
		return drinkID;
	}
		
	public void setInvDrawn(Boolean choice) {
		invDrawn = choice;

	}

	public Boolean getInvDrawn() {
		return invDrawn;

	}

	public void setItemFound(Boolean choice) {
		found = choice;

	}
	
	public boolean getFound() {
		return found;

	}
	
	public void setOnMap(Boolean choice) {
		onMap = choice;

	} 
	
	public boolean checkOnMap() {
		return onMap;

	}
	
	public void setBeingUsed(Boolean choice) {
		beingUsed = choice;
		
	}
	
	public Boolean checkBeingUsed() {
		return beingUsed;
		
	}
	
	public void setBeingPressed(Boolean choice) {
		beingPressed = choice;
		
	}
	
	public Boolean checkBeingPressed() {
		return beingPressed;
		
	}
	
	public int getInvBoxX() {
		return invBoxX;
	}
	
	public int getInvX() {
		return invX;

	}

	public int getX() {
		return x;

	}

	public int getY() {
		return y;

	}

	public int getID() {
		return id;

	}

	public String getName() {
		return itemName;

	}

	public Texture getTexture() {
		return texture;

	}

	public String getAtlasImage() {
		return atlasImage;
	}

}
