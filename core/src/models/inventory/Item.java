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
