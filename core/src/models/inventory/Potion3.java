package models.inventory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class Potion3 extends Item{
	
	public Potion3(int startX, int startY) {
		super("Potion3", startX, startY, 5, "images/items/itemPotion.png" ,"invPotion", 19, 19);
	
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
