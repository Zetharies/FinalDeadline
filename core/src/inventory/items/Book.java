package inventory.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class Book extends Item{
	
	public Book(int startX, int startY) {
		super("Book", startX, startY, 2, "images/itemBook.png" ,"invBook", -224);
	
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
