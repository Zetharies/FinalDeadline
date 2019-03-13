package screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Particles {
	
	void smokeUpdateAndDraw(SpriteBatch batch, float deltaTime){
		Assets.chimneySmoke.start();
		Assets.chimneySmoke.draw(batch);
		Assets.chimneySmoke.setPosition((float)(Gdx.graphics.getWidth()/6.5), (float)(Gdx.graphics.getHeight()/9.65));
		Assets.chimneySmoke.update(deltaTime);
	}
	
	void smokeUpdateAndDraw2(SpriteBatch batch, float deltaTime){
		Assets.chimneySmoke.start();
		Assets.chimneySmoke.draw(batch);
		Assets.chimneySmoke.setPosition((float)((Gdx.graphics.getWidth()/6.5) * 5.35), (float)(Gdx.graphics.getHeight()/9.65));
		Assets.chimneySmoke.update(deltaTime);
	}
	
	
	void interactParticles(SpriteBatch batch, float deltaTime){
		Assets.interact.start();
		Assets.interact.draw(batch);
		Assets.interact.setPosition(91, 3);
		Assets.interact.update(deltaTime);
		
	}

}
