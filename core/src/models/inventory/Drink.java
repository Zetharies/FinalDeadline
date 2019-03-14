package models.inventory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class Drink extends Item{
	
	public Drink(int startX, int startY) {
		super("Drink", startX, startY, 2, "images/items/itemDrink.png" ,"invDrink", -222, -219);

	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(getTexture(), // Print Texture
				(getX() * GameSettings.SCALED_TILE_SIZE), // Sets X Position
				(getY() * GameSettings.SCALED_TILE_SIZE), // Sets Y Position
				GameSettings.SCALED_TILE_SIZE * 1.0f, // Sets Width of Sprite
				GameSettings.SCALED_TILE_SIZE * 1.0f); // Sets Height of Sprite
		
	}
	
}
