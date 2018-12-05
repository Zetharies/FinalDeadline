package screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import managers.SettingsManager;
import controllers.ScreenplayController;
import controllers.PlayerController;
import java.util.ArrayList;
import models.AnimationSet;
import models.Herd;
import models.Player;
import models.Zombie;
import models.screenplay.Screenplay;
import models.screenplay.ScreenplayHandler;
import models.screenplay.ScreenplayNode;
import screens.intro.AbstractScreen;
import screens.menu.MainMenuScreen;

public class GameScreen extends AbstractScreen {

    private Music inGameMp3;

    private SpriteBatch batch; // Allows us to render sprite to screen really fast
    private Player player;
    private PlayerController playerControls;
    private ScreenplayController dialogueController;
    private int spawnX;
    private int spawnY;

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

    private Herd herd;
    private ArrayList<Zombie> zombies;

    public GameScreen(String character) {
        this.chosenCharacter = character; // Chosen characters are either Flynn or Jessica
        if (chosenCharacter == "Flynn") {
            gender = "male";
        } else if (chosenCharacter == "Jessica") {
            gender = "female";
        } else {
            gender = "custom";
        }
        initUI();
    }

    @Override
    public void show() {
        MainMenuScreen.getMP3().pause();
        
        batch = new SpriteBatch();
        hud = new Hud(batch);
        assetManager = new AssetManager();
        assetManager.load("sprite/" + gender + "/" + chosenCharacter + "_walking.atlas", TextureAtlas.class);
        assetManager.load("sprite/" + gender + "/" + chosenCharacter + "_standing.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas walking = this.getAssetManager().get("sprite/" + gender + "/" + chosenCharacter + "_walking.atlas",
                TextureAtlas.class);
        TextureAtlas standing = this.getAssetManager()
                .get("sprite/" + gender + "/" + chosenCharacter + "_standing.atlas", TextureAtlas.class);

        AnimationSet animations = new AnimationSet(
                new Animation<Object>(GameSettings.TIME_PER_TILE / 2f,
                        walking.findRegions(chosenCharacter + "_walking_north"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<Object>(GameSettings.TIME_PER_TILE / 2f,
                        walking.findRegions(chosenCharacter + "_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<Object>(GameSettings.TIME_PER_TILE / 2f,
                        walking.findRegions(chosenCharacter + "_walking_east"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<Object>(GameSettings.TIME_PER_TILE / 2f,
                        walking.findRegions(chosenCharacter + "_walking_west"), Animation.PlayMode.LOOP_PINGPONG),
                standing.findRegion(chosenCharacter + "_standing_north"),
                standing.findRegion(chosenCharacter + "_standing_south"),
                standing.findRegion(chosenCharacter + "_standing_east"),
                standing.findRegion(chosenCharacter + "_standing_west"));

        // map = new TmxMapLoader().load("maps/floor2/updatedEngineeringLab.tmx"); //
        // map to load, extremely basic map,
        // will be changed
        map = new TmxMapLoader().load("maps/floor4/Floor4.tmx");

        // player = new Player(14, 90, animations); // Create a new player object with
        // the coordinates 0, 0, player
        // animations
        spawnX = 26;
        spawnY = 82;
        player = new Player(spawnX, spawnY, animations);
        playerControls = new PlayerController(player, (TiledMapTileLayer) map.getLayers().get(0));

        renderer = new OrthogonalTiledMapRenderer(map, 2f); // 1.5658f
        setGameScreen();

//		camera = new OrthographicCamera();
//		//gamePort = new ScreenViewport(camera);
//		gamePort = new StretchViewport(1200, 600, camera);
//		// herd a group of zombies
//		herd = new Herd((TiledMapTileLayer) map.getLayers().get(0));
//		// put zombies in list
//		zombies = herd.getZombiesList();
        processor = new InputMultiplexer(); // Ordered lists of processors we can use for prioritising controls

        dialogueController = new ScreenplayController(dialogue, chosenCharacter);
        processor.addProcessor(0, dialogueController);
        processor.addProcessor(1, playerControls);
        handler = new ScreenplayHandler();

        // Create a new dialogue or instruction. Then add the order in which it comes.
        if (chosenCharacter == "Jessica") {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("voices/jessica/Jessica_what_the.wav"));
            sound.play();
        }
        if (chosenCharacter == "Flynn") {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("voices/flynn/Flynn_what_the2.wav"));
            sound.play();
        }
        ScreenplayNode dialogue1 = new ScreenplayNode(chosenCharacter + ":\nWhat the...\nWhere am I?...   [ENTER]", 0);
        ScreenplayNode dialogue2 = new ScreenplayNode(
                chosenCharacter + ":\nWhat's going on here...\nWhere is everyone?!   [ENTER]", 1);

        ScreenplayNode instruction1 = null;
        if (SettingsManager.KEYS) {
            instruction1 = new ScreenplayNode("Press your arrow keys to move around the map   [ENTER]", 2);
        } else {
            if (SettingsManager.WASD) {
                instruction1 = new ScreenplayNode("Press 'W','A','S','D' to move around the map   [ENTER]", 2);
            }
        }
          inGameMp3 = Gdx.audio.newMusic(Gdx.files.internal("music/extendedSoundtrack2.wav"));
        inGameMp3.setLooping(true); // loop the soundtrack
        inGameMp3.play(); // play the soundtrack
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

    public void setGameScreen() {
        //renderer = new OrthogonalTiledMapRenderer(map, 2f); // 1.5658f
        camera = new OrthographicCamera();
        // gamePort = new ScreenViewport(camera);
        gamePort = new StretchViewport(1200, 600, camera);
        // herd a group of zombies
        herd = new Herd((TiledMapTileLayer) map.getLayers().get(0));
        // put zombies in list
        zombies = herd.getZombiesList();
    }

    public void setMap() {
        if (playerControls.getMapChange()) {
            map = new TmxMapLoader().load("maps/floor2/updatedEngineeringLab.tmx");
        }
    }
    
    public void setSpawnX(int x) {
    	spawnX = x;
    }
    
    public void setSpawnY(int y) {
    	spawnY = y;
    }

    @Override
    public void render(float delta) {
      
        inGameMp3.setVolume(0.15f);
        playerControls.checkExit();
        
        // Checks if the map needs changing
        if (playerControls.getMapChange()) {
            map.dispose();
            setMap();
            renderer.setMap(map);
            playerControls.setCollisions((TiledMapTileLayer) map.getLayers().get(0));
            herd.setCollisions((TiledMapTileLayer) map.getLayers().get(0));
            herd.respawnZombies();
            hud.setLabel("Floor 2: Engineering Lab");
            playerControls.setMapChange(false);
            spawnX = 14;
            spawnY = 90;
        }
        
        // Checks if the player's health needs reducing due to a zombie
        if(playerControls.isOnZombie(herd.getZombiesList())) {
        	hud.reduceHealth();
        }
        
        // Checks if the player's health is 0, if so re-spawn them
        if(hud.getHealth() == 0.0f) {
        	hud.resetHealth();
        	playerControls.updatePlayerCoordinates(spawnX, spawnY);
        }
        for (int i = 0; i < zombies.size(); i++) {
            // update all zombies
            zombies.get(i).update(delta);
        }
        // control one zombie to test collisions
        // zombies.get(0).update(delta);
        playerControls.update(delta);
        player.update(delta);
        // camera.position.set(player.getX() * GameSettings.SCALED_TILE_SIZE,
        // player.getY() * GameSettings.SCALED_TILE_SIZE, 0);
        camera.position.y = player.getLinearY() * 64;
        camera.position.x = player.getLinearX() * 64;

        // follow that zombie
//		camera.position.y = zombies.get(0).y * 64;
//		camera.position.x = zombies.get(0).x * 64;
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
        batch.draw(player.getSprite(), (player.getLinearX() * GameSettings.SCALED_TILE_SIZE) - 10,
                (player.getLinearY() * GameSettings.SCALED_TILE_SIZE) + 10, GameSettings.SCALED_TILE_SIZE * 1.3f,
                GameSettings.SCALED_TILE_SIZE * 1.5f); // Players character / X,Y position on screen / Width / Height

        // changing height and width changes collisions
        for (int i = 0; i < zombies.size(); i++) {
            batch.draw(zombies.get(i).getZombies(),
                    (zombies.get(i).x * GameSettings.SCALED_TILE_SIZE) - (GameSettings.SCALED_TILE_SIZE / 2),
                    zombies.get(i).y * GameSettings.SCALED_TILE_SIZE, GameSettings.SCALED_TILE_SIZE * 1f,
                    GameSettings.SCALED_TILE_SIZE * 1f);
        }
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        gamePort.update(width, height);
        stage.getViewport().update(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, true);
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
