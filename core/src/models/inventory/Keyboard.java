package models.inventory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class Keyboard extends Item{

	public Keyboard(int startX, int startY) {
		super("Keyboard", startX, startY, 1, "images/items/itemKeyboard.png", "invKeyboard", -305, -298);

	}
	
	/**
	 * <p> Method designed to render the Keyboard item, using specific parameters
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
