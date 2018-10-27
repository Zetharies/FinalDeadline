package screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import screens.intro.AbstractScreen;

public class GameScreen extends AbstractScreen {
	
	
	private SpriteBatch batch; // Allows us to render sprite to screen really fast
	private Texture playerSprite; // Our players Sprite

	public GameScreen() {
	}
	
	@Override
	public void show() {
		
		playerSprite = new Texture(Gdx.files.internal("sprite/soon.png"));
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		batch.begin();
		batch.draw(playerSprite, 0, 0, 16, 24); // Players character / X,Y position on screen / Width / Height
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
