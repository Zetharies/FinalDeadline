package screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameSettings;
import com.mygdx.game.Hud;
import com.badlogic.gdx.graphics.g2d.Animation;

import controllers.ScreenplayController;
import controllers.PlayerController;
import models.AnimationSet;
import models.Player;
import models.screenplay.Screenplay;
import models.screenplay.ScreenplayHandler;
import models.screenplay.ScreenplayNode;
import screens.intro.AbstractScreen;

public class GameScreen extends AbstractScreen {
	
	private SpriteBatch batch; // Allows us to render sprite to screen really fast
	private Player player;
	private PlayerController playerControls;
	private ScreenplayController dialogueController;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Viewport gamePort;
	private AssetManager assetManager;
	private String chosenCharacter, gender;
	private Hud hud;
	
	private Stage stage;
	private Table table;
	private Screenplay dialogue;
	private ScreenplayHandler handler;
	private InputMultiplexer processor;

	public GameScreen(String character) {
		this.chosenCharacter = character; // Chosen characters are either Flynn or Jessica
		if(chosenCharacter == "Flynn") {
			gender = "male";
		} else if (chosenCharacter == "Jessica") {
			gender = "female";
		}
		initUI();
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

		map = new TmxMapLoader().load("newMap/Lab Floor HACK.tmx"); // map to load, extremely basic map, will be changed
		
		player = new Player(12, 50, animations); // Create a new player object with the coordinates 0, 0, player animations
		playerControls = new PlayerController(player, (TiledMapTileLayer) map.getLayers().get(3));
		
		renderer = new OrthogonalTiledMapRenderer(map, 1.5658f);
		camera = new OrthographicCamera();
		gamePort = new StretchViewport(900, 450, camera);
		
		processor = new InputMultiplexer(); // Ordered lists of processors we can use for prioritising controls
		
		dialogueController = new ScreenplayController(dialogue);
		processor.addProcessor(0, dialogueController);
		processor.addProcessor(1, playerControls);
		handler = new ScreenplayHandler();
		
		// Create a new dialogue or instruction. Then add the order in which it comes.
		ScreenplayNode dialogue1 = new ScreenplayNode(chosenCharacter+":\nWhat the...\nWhere am I?...   [ENTER]", 0);
		ScreenplayNode dialogue2 = new ScreenplayNode(chosenCharacter+":\nWhat's going on here...\nWhere is everyone?!   [ENTER]", 1);
		ScreenplayNode instruction1 = new ScreenplayNode("Press 'W', 'A', 'S', 'D' or your arrow keys to move around the map   [ENTER]", 2);
		dialogue1.makeLinear(dialogue2.getId());
		dialogue2.makeLinear(instruction1.getId());
		
		handler.addNode(dialogue1);
		handler.addNode(dialogue2);
		handler.addNode(instruction1);

		dialogueController.startDialogue(handler);
		Gdx.input.setInputProcessor(processor);
	}

	private AssetManager getAssetManager() {
		// TODO Auto-generated method stub
		return assetManager;
	}
	
	private void initUI() {
		stage = new Stage(new ScreenViewport());
		
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		dialogue = new Screenplay(chosenCharacter);
		table.add(dialogue).expand().align(Align.bottom).pad(10f);
	}

	@Override
	public void render(float delta) {
		playerControls.update(delta);
		player.update(delta);
		//camera.position.set(player.getX() * GameSettings.SCALED_TILE_SIZE, player.getY() * GameSettings.SCALED_TILE_SIZE, 0);
		camera.position.y = player.getLinearY() * 50;
		camera.position.x = player.getLinearX() * 50;
		camera.update();
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		renderer.setView(camera);
		renderer.render();
		batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		stage.act(delta);
		gamePort.apply(); // Changes how the graphics is drawn on the screen
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(player.getSprite(),
				player.getLinearX() * GameSettings.SCALED_TILE_SIZE,
				player.getLinearY() * GameSettings.SCALED_TILE_SIZE,
				GameSettings.SCALED_TILE_SIZE*1.5f,
				GameSettings.SCALED_TILE_SIZE*1.5f); // Players character / X,Y position on screen / Width / Height
		batch.end();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
		gamePort.update(width, height);
		stage.getViewport().update(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, true);
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
