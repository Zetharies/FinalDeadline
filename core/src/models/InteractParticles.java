package models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;


public class InteractParticles {

	private int x, y;
	
	public InteractParticles(int startX, int startY) {
		x = startX;
		y = startY;
		
		/*
		pe = new ParticleEffect();
	    pe.load(Gdx.files.internal("particles/interact.p"),Gdx.files.internal(""));
	    pe.getEmitters().first().setPosition(x, y);
	    pe.start();
	    */
	}
	
	/**
	 * <p> Method designed to render the available interactions, using specific parameters
	 */
	public void render(SpriteBatch batch) {
		batch.draw(new Texture("images/exclamation.png"), // Print Texture
				(x * GameSettings.SCALED_TILE_SIZE), // Sets X Position
				(float) ((y * GameSettings.SCALED_TILE_SIZE) * 1.02), // Sets Y Position
				GameSettings.SCALED_TILE_SIZE * 1.0f, // Sets Width of Sprite
				GameSettings.SCALED_TILE_SIZE * 1.0f); // Sets Height of Sprite
		
		/*
			pe.draw(batch);
			if (pe.isComplete()) {
				pe.reset();
			}
		*/
		
	}

}
