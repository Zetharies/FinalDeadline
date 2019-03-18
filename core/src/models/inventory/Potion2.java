package models.inventory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class Potion2 extends Item{
	
	public Potion2(int startX, int startY) {
		super("Potion2", startX, startY, 4, "images/items/itemPotion.png" ,"invPotion", -60, -60);
	
	}
	
	public Potion2(int startX, int startY, String choice) {
		super("Potion2", startX, startY, 4, "invPotion", -60, -60);
	
	}
	
	/**
	 * <p> Method designed to render the Potion2 item, using specific parameters
	 */
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(getTexture(), // Print Texture
				(getX() * GameSettings.SCALED_TILE_SIZE), // Sets X Position
				(getY() * GameSettings.SCALED_TILE_SIZE), // Sets Y Position
				GameSettings.SCALED_TILE_SIZE * 1.0f, // Sets Width of Sprite
				GameSettings.SCALED_TILE_SIZE * 1.0f); // Sets Height of Sprite
		
	}

}
