package screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameSettings;
import com.mygdx.game.Hud;
import com.badlogic.gdx.graphics.g2d.Animation;

import controllers.PlayerController;
import models.AnimationSet;
import models.Player;
import screens.intro.AbstractScreen;

public class GameScreen extends AbstractScreen {
	
	
	private SpriteBatch batch; // Allows us to render sprite to screen really fast
	private Player player;
	private PlayerController playerControls;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Viewport gamePort;
	private AssetManager assetManager;
	private String chosenCharacter, gender;
	private Hud hud;

	public GameScreen(String character) {
		this.chosenCharacter = character; // Chosen characters are either Flynn or Jessica
		if(chosenCharacter == "flynn") {
			gender = "male";
		} else if (chosenCharacter == "jessica") {
			gender = "female";
		}
	}
	
	@Override
	public void show() {
		batch = new SpriteBatch();
		hud = new Hud(batch);
		assetManager = new AssetManager();
		assetManager.load("sprite/" + gender + "/" + chosenCharacter + "_walking.atlas", TextureAtlas.class);
        assetManager.load("sprite/" + gender + "/" + chosenCharacter + "_standing.atlas", TextureAtlas.class);
        assetManager.finishLoading();
		
		TextureAtlas walking = this.getAssetManager().get("sprite/" + gender + "/" + chosenCharacter + "_walking.atlas", TextureAtlas.class);
        TextureAtlas standing = this.getAssetManager().get("sprite/" + gender + "/" + chosenCharacter + "_standing.atlas", TextureAtlas.class);
        
        AnimationSet animations = new AnimationSet(
                new Animation<Object>(GameSettings.TIME_PER_TILE/2f, walking.findRegions(chosenCharacter + "_walking_north"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<Object>(GameSettings.TIME_PER_TILE/2f, walking.findRegions(chosenCharacter + "_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<Object>(GameSettings.TIME_PER_TILE/2f, walking.findRegions(chosenCharacter + "_walking_east"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<Object>(GameSettings.TIME_PER_TILE/2f, walking.findRegions(chosenCharacter + "_walking_west"), Animation.PlayMode.LOOP_PINGPONG),
                standing.findRegion(chosenCharacter + "_standing_north"),
                standing.findRegion(chosenCharacter + "_standing_south"),
                standing.findRegion(chosenCharacter + "_standing_east"),
                standing.findRegion(chosenCharacter + "_standing_west")
                );
		
		
		player = new Player(0, 0, animations); // Create a new player object with the coordinates 0, 0, player animations
		playerControls = new PlayerController(player);

		map = new TmxMapLoader().load("maps/basicMap.tmx"); // map to load, extremely basic map, will be changed
		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		gamePort = new StretchViewport(1200, 600, camera);
		Gdx.input.setInputProcessor(playerControls);
	}

	private AssetManager getAssetManager() {
		// TODO Auto-generated method stub
		return assetManager;
	}

	@Override
	public void render(float delta) {
		playerControls.update(delta);
		player.update(delta);
		//camera.position.set(player.getX() * GameSettings.SCALED_TILE_SIZE, player.getY() * GameSettings.SCALED_TILE_SIZE, 0);
		camera.position.y = player.getLinearY() * 64;
		camera.position.x = player.getLinearX() * 64;
		camera.update();
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		renderer.setView(camera);
		renderer.render();
		batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(player.getSprite(),
				player.getLinearX() * GameSettings.SCALED_TILE_SIZE,
				player.getLinearY() * GameSettings.SCALED_TILE_SIZE,
				GameSettings.SCALED_TILE_SIZE*1.5f,
				GameSettings.SCALED_TILE_SIZE*1.5f); // Players character / X,Y position on screen / Width / Height
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
		gamePort.update(width, height);
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
