package inventory.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class Glasses extends Item {
	
	public Glasses(int startX, int startY) {
		super("Book", startX, startY, 5, "images/book1.png");
	
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(getTexture(), // Print Texture
				(getX() * GameSettings.SCALED_TILE_SIZE), // Sets X Position
				(getY() * GameSettings.SCALED_TILE_SIZE), // Sets Y Position
				GameSettings.SCALED_TILE_SIZE * 1f, // Sets Width of Sprite
				GameSettings.SCALED_TILE_SIZE * 1f); // Sets Height of Sprite
		
	}
}
