package models.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item{

	private String itemName;
	private String atlasImage;
	private Texture texture;

	private int id;

	private int x;
	private int y;
	private int invX;
	private int invBoxX;

	protected boolean found;
	protected boolean onMap;

	private Boolean invDrawn;

	public Item(String impName, int startX, int startY, int impID, String texturePath, String atlasImage, int invX, int invBoxX ) {
		itemName = impName;

		id = impID;
		x = startX;
		y = startY;
		this.invX = invX;
		this.invBoxX = invBoxX;

		texture = new Texture(Gdx.files.internal(texturePath));

		found = false;

		onMap = false;

		this.atlasImage = atlasImage;

		invDrawn = false;

	}

	public void render(SpriteBatch batch) {

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
