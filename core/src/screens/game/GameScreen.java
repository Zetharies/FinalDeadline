package screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.GameSettings;

import controllers.PlayerController;
import models.Player;
import screens.intro.AbstractScreen;

public class GameScreen extends AbstractScreen {
	
	
	private SpriteBatch batch; // Allows us to render sprite to screen really fast
	private Texture playerSprite; // Our players Sprite
	private Player player;
	private PlayerController playerControls;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	public GameScreen() {
	}
	
	@Override
	public void show() {
		playerSprite = new Texture(Gdx.files.internal("sprite/placeHolderSprite.png")); // Player image to be replaced with animation
		batch = new SpriteBatch();
		player = new Player(0, 0); // Create a new player object with the coordinates 0, 0
		playerControls = new PlayerController(player);

		map = new TmxMapLoader().load("maps/basicMap.tmx"); // map to load, extremely basic map, will be changed
		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		
		Gdx.input.setInputProcessor(playerControls);
	}

	@Override
	public void render(float delta) {
		
		playerControls.update(delta);
		camera.position.set(player.getX() * GameSettings.SCALED_TILE_SIZE, player.getY() * GameSettings.SCALED_TILE_SIZE, 0);
		camera.update();
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		renderer.setView(camera);
		renderer.render();
		
		batch.begin();
		batch.draw(playerSprite,
				player.getX() * GameSettings.SCALED_TILE_SIZE,
				player.getY() * GameSettings.SCALED_TILE_SIZE,
				GameSettings.SCALED_TILE_SIZE*1.5f,
				GameSettings.SCALED_TILE_SIZE*1.5f); // Players character / X,Y position on screen / Width / Height
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
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
		dispose();
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
	}

}
