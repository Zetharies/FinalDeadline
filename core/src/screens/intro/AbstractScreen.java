package screens.intro;

import com.badlogic.gdx.Screen;

/**
 * Abstract screen is used for all of our game screens
 * @author Team 2f
 *
 */
public abstract class AbstractScreen implements Screen {

	@Override
	public abstract void show();

	@Override
	public abstract void render(float delta);
	
	@Override
	public abstract void resize(int width, int height);

	@Override
	public abstract void pause();

	@Override
	public abstract void resume();

	@Override
	public abstract void hide();

	@Override
	public abstract void dispose();

}
