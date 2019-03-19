package screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Particles {
	
	void smokeUpdateAndDraw(SpriteBatch batch, float deltaTime){
		Assets.smokeParticles.start();
		Assets.smokeParticles.draw(batch);
		Assets.smokeParticles.setPosition((float)(Gdx.graphics.getWidth()/6.65), (float)(Gdx.graphics.getHeight()/9.65));
		Assets.smokeParticles.update(deltaTime);
	}
	/** both smokeUpdateAndDraw methods create smoke effects for each side of the action bar*/
	void smokeUpdateAndDraw2(SpriteBatch batch, float deltaTime){
		Assets.smokeParticles.start();
		Assets.smokeParticles.draw(batch);
		Assets.smokeParticles.setPosition((float)((Gdx.graphics.getWidth()/6.5) * 5.36), (float)(Gdx.graphics.getHeight()/9.65));
		Assets.smokeParticles.update(deltaTime);
	}
	/** Allows particles to signify an interactable object*/
	void interactParticles(SpriteBatch batch, float deltaTime){
		Assets.interact.start();
		Assets.interact.draw(batch);
		Assets.interact.setPosition(91, 3);
		Assets.interact.update(deltaTime);
		
	}

}
