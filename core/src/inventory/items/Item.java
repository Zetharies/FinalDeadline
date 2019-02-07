package inventory.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.GameSettings;

public class Item {

	private String itemName;
	
	private int id;

	private Texture texture;

	private int x;
	private int y;

	protected boolean found;
	
	protected boolean onMap;

	public Item(String impName, int startX, int startY, int impID, String texturePath) {
		itemName = impName;
		
		id = impID;
		x = startX;
		y = startY;

		texture = new Texture(Gdx.files.internal(texturePath));

		found = false;
		
		onMap = false;

	}
	
	public void render(SpriteBatch batch) {
		batch.draw(getTexture(), // Print Texture
				(getX() * GameSettings.SCALED_TILE_SIZE), // Sets X Position
				(getY() * GameSettings.SCALED_TILE_SIZE), // Sets Y Position
				GameSettings.SCALED_TILE_SIZE * 1f, // Sets Width of Sprite
				GameSettings.SCALED_TILE_SIZE * 1f); // Sets Height of Sprite
		
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
	
	public boolean getFound() {
		return found;
		
	}

	public boolean getOnMap() {
		return onMap;
		
	}
	
	public void itemFound() {
		found = true;

	}
	
	public void itemNotFound() {
		found = false;

	}
	
	public boolean checkOnMap() {
		return onMap;
		
	}
	
	public void setOnMap() {
		onMap = true;
		
	}
	
	public void setNotOnMap() {
		onMap = false;
		
	}
	
	public Texture getTexture() {
		return texture;
		
	}

}
