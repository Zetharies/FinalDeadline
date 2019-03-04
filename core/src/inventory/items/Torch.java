package inventory.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class Torch extends Item{

	public Torch(int startX, int startY) {
		super("Torch", startX, startY, 0, "images/itemBook.png", "invKeyboard", -380);
		
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
