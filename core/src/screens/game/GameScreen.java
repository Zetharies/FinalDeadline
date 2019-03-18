package screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameSettings;
import com.mygdx.game.Hud;
import com.badlogic.gdx.graphics.g2d.Animation;

import managers.ScreenManager;
import managers.SettingsManager;
import controllers.ScreenplayController;
import controllers.PlayerController;
import controllers.RobotController;
import java.util.ArrayList;

import models.AnimationSet;
import models.Book;
import models.Herd;
import models.InventorySystem;
import models.Keyboard;
import models.Map;
import models.Player;
import models.Zombie;
import models.inventory.Drink;
import models.inventory.Item;
import models.Robot;
import models.screenplay.Screenplay;
import models.screenplay.ScreenplayHandler;
import models.screenplay.ScreenplayNode;
import riddleScreen.RiddleCard;
import screens.intro.AbstractScreen;
import screens.menu.MainMenuScreen;
import models.BossZombie;
import controllers.BossController;
import models.BoobyTrap;

public class GameScreen extends AbstractScreen {

    private Music inGameMp3, cafe, library, engineering, gameOver, firstBoss;
    private ArrayList<String> currentMapLabel;

    private SpriteBatch batch, mapBatch; // Allows us to render sprite to screen really fast
    private Player player;
    private PlayerController playerControls;
    private ScreenplayController dialogueController;
    private int spawnX;
    private int spawnY;
    private boolean been = false;

    private TiledMap loadedMap;
    private Map map;
    private ArrayList<Map> maps;
    private ArrayList<ArrayList<Integer>> exits;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Viewport gamePort;
    private AssetManager assetManager;
    private String chosenCharacter, gender;
    private Hud hud;

    private Stage stage;
    private Table table, table2;
    private Screenplay dialogue;
    private ScreenplayHandler handler;
    private InputMultiplexer processor;

    private Herd herd;
    private ArrayList<Zombie> zombies;
    private ArrayList<Book> books;
    private ArrayList<Keyboard> keyboards;
    private Robot robot;
    private RobotController robotController;
    private BossZombie bossZombie;
    private BossController bossController;
    private InventorySystem currentInv;
    private int currentDrinkID;
    private int currentList = 0;

    private Particles smoke, smoke2, smoke3;

    private ArrayList<Music> musicList;

    private float elapsed = 0.0f;

    private BoobyTrap traps;

    private RiddleCard riddle;

    private RiddleCard riddle2;

    private boolean wrong;

    public GameScreen(String character) {
        Assets.load();
        this.chosenCharacter = character; // Chosen characters are either Flynn or Jessica
        if (chosenCharacter == "Flynn") {
            gender = "male";
        } else if (chosenCharacter == "Jessica") {
            gender = "female";
        } else {
            gender = "custom";
        }

        inGameMp3 = Gdx.audio.newMusic(Gdx.files.internal("music/floor2.mp3"));
        inGameMp3.setLooping(true); // loop the soundtrack
        inGameMp3.setVolume(0.15f);

        cafe = Gdx.audio.newMusic(Gdx.files.internal("music/Cafe.wav"));
        cafe.setLooping(true); // loop the soundtrack
        cafe.setVolume(0.15f);

        library = Gdx.audio.newMusic(Gdx.files.internal("music/Library.wav"));
        library.setLooping(true); // loop the soundtrack
        library.setVolume(0.15f);

        engineering = Gdx.audio.newMusic(Gdx.files.internal("music/Engineering.wav"));
        engineering.setLooping(true); // loop the soundtrack
        engineering.setVolume(0.15f);

        gameOver = Gdx.audio.newMusic(Gdx.files.internal("music/GameOver.wav"));
        gameOver.setVolume(0.15f);

        firstBoss = Gdx.audio.newMusic(Gdx.files.internal("music/FirstBoss.wav"));
        firstBoss.setLooping(true); // loop the soundtrack
        firstBoss.setVolume(0.15f);

        musicList = new ArrayList<Music>();
        musicList.add(inGameMp3);
        musicList.add(cafe);
        musicList.add(library);
        musicList.add(engineering);
        musicList.add(firstBoss);

        if (SettingsManager.getMusic()) {
            musicList.get(0).play();
        }

        currentMapLabel = new ArrayList<String>();
        currentMapLabel.add("Cafeteria");
        currentMapLabel.add("Floor 1: Library");
        currentMapLabel.add("Floor 2: Engineering");
        currentMapLabel.add("Unknown: Floor Boss");

        books = new ArrayList<Book>();
        keyboards = new ArrayList<Keyboard>();
        initUI();
        processor = new InputMultiplexer(); // Ordered lists of processors we can use for prioritising controls
        dialogueController = new ScreenplayController(dialogue, chosenCharacter);
        maps = new ArrayList<Map>();

        Map entrance = new Map(29, 41, "maps/entrance/universityEntrance.tmx");
        entrance.addExit(52, 48);
        entrance.addExit(53, 48);
        entrance.addExit(54, 48);
        entrance.addExit(55, 48);

        Map cafeteria = new Map(50, 23, "maps/cafeteria/cafeteria.tmx");
        cafeteria.addExit(61, 79);
        cafeteria.addExit(62, 79);
        cafeteria.addExit(63, 79);
        cafeteria.addExit(64, 79);
        cafeteria.addExit(65, 79);

        Map floor1 = new Map(21, 56, "maps/floor1/library.tmx");
        floor1.addExit(76, 21);

        Map floor2 = new Map(14, 90, "maps/floor2/updatedEngineeringLab.tmx");
        floor2.addExit(88, 15);
        floor2.addExit(89, 15);

        Map floor3 = new Map(91, 3, "maps/floor3/optometry.tmx");
        floor3.addExit(5, 94);
        floor3.addExit(6, 94);
        floor3.addExit(7, 94);
        floor3.addExit(8, 94);
        floor3.addExit(9, 94);

        Map floor4 = new Map(87, 13, "maps/floor4/Floor4.tmx");

        Map boss1 = new Map(46, 40, "maps/Minimaps/Boss 1/1stBossMap.tmx");
        Map boss2 = new Map(47, 34, "maps/Minimaps/Boss 2/2ndBossMap.tmx");
        Map chaxMap = new Map(51, 24, "maps/Minimaps/Chax boss map/ChaxMap.tmx");

        maps.add(entrance);
        maps.add(cafeteria);
        maps.add(floor1);
        maps.add(floor2);
        maps.add(boss1);
        maps.add(floor3);
        maps.add(boss2);
        maps.add(floor4);
        maps.add(chaxMap);
        exits = entrance.getExits();

        smoke = new Particles();
        smoke2 = new Particles();

    }

    @Override
    public void show() {
        MainMenuScreen.getMP3().pause();

        batch = new SpriteBatch();
        mapBatch = new SpriteBatch();
        // batch.setBlendFunction(-1, -1);
        // Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA,
        // GL20.GL_ONE_MINUS_SRC_ALPHA,GL20.GL_SRC_ALPHA, GL20.GL_DST_ALPHA);
        hud = new Hud(batch);

        assetManager = new AssetManager();
        assetManager.load("sprite/" + gender + "/" + chosenCharacter + "_walking.atlas", TextureAtlas.class);
        assetManager.load("sprite/" + gender + "/" + chosenCharacter + "_standing.atlas", TextureAtlas.class);
        assetManager.load("sprite/" + gender + "/" + chosenCharacter + "_powered_walking.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        /**
         * Call upon the texture files within their directory
         */
        TextureAtlas walking = this.getAssetManager().get("sprite/" + gender + "/" + chosenCharacter + "_walking.atlas",
                TextureAtlas.class);
        TextureAtlas standing = this.getAssetManager()
                .get("sprite/" + gender + "/" + chosenCharacter + "_standing.atlas", TextureAtlas.class);
        TextureAtlas poweredUp = this.getAssetManager()
                .get("sprite/" + gender + "/" + chosenCharacter + "_powered_walking.atlas", TextureAtlas.class);

        /**
         * Call upon the sprite for the animations 'walking' and the texture
         * region 'standing'
         */
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

        /**
         * Call upon the sprite for the animation of Flynn being powered up
         */
        AnimationSet flynnPoweredAnimation = new AnimationSet(
                new Animation<Object>(GameSettings.TIME_PER_TILE / 2f,
                        poweredUp.findRegions("flynnPowered1"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<Object>(GameSettings.TIME_PER_TILE / 2f,
                        poweredUp.findRegions("flynnPowered2"), Animation.PlayMode.LOOP_PINGPONG));
        /**
         * Call upon the sprite for the animation of Jessica being powered up
         */
        AnimationSet jessicaPoweredAnimation = new AnimationSet(
                new Animation<Object>(GameSettings.TIME_PER_TILE / 2f,
                        poweredUp.findRegions("jessicacPowered1"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<Object>(GameSettings.TIME_PER_TILE / 2f,
                        poweredUp.findRegions("jessicaPowered2"), Animation.PlayMode.LOOP_PINGPONG));

        // map = new TmxMapLoader().load("maps/floor2/updatedEngineeringLab.tmx"); //
        // map to load, extremely basic map,
        // will be changed
        map = maps.get(0);
        loadedMap = new TmxMapLoader().load(map.getMapLocation());

        TiledMap mapCollisionsTraps = new TmxMapLoader().load(maps.get(1).getMapLocation());
        TiledMap mapCollisionsRobot = new TmxMapLoader().load(maps.get(4).getMapLocation());
        TiledMap mapCollisionsBoss = new TmxMapLoader().load(maps.get(0).getMapLocation());

        currentInv = new InventorySystem();
        currentInv.defineInventory(((TiledMapTileLayer) loadedMap.getLayers().get(0)), 0);

        // player = new Player(14, 90, animations); // Create a new player object with
        // the coordinates 0, 0, player
        // animations
        spawnX = map.getRespawnX();
        spawnY = map.getRespawnY();
        player = new Player(spawnX, spawnY, animations);
        playerControls = new PlayerController(player, (TiledMapTileLayer) loadedMap.getLayers().get(0));

        traps = new BoobyTrap((TiledMapTileLayer) mapCollisionsTraps.getLayers().get(0));

        robot = new Robot(46, 47, (TiledMapTileLayer) mapCollisionsRobot.getLayers().get(0));
        robotController = new RobotController((TiledMapTileLayer) mapCollisionsRobot.getLayers().get(0), robot);
        bossZombie = new BossZombie(47, 41, (TiledMapTileLayer) loadedMap.getLayers().get(0));
        bossController = new BossController((TiledMapTileLayer) loadedMap.getLayers().get(0), bossZombie);
        renderer = new OrthogonalTiledMapRenderer(loadedMap, 2f); // 1.5658f
        setGameScreen();

        // camera = new OrthographicCamera();
        // //gamePort = new ScreenViewport(camera);
        // gamePort = new StretchViewport(1200, 600, camera);
        // // herd a group of zombies
        // herd = new Herd((TiledMapTileLayer) map.getLayers().get(0));
        // // put zombies in list
        // zombies = herd.getZombiesList();
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
        table.add(dialogue).expand().align(Align.bottom).pad(85f);
    }

    public void setGameScreen() {
        // renderer = new OrthogonalTiledMapRenderer(map, 2f); // 1.5658f
        camera = new OrthographicCamera();
        // gamePort = new ScreenViewport(camera);
        gamePort = new StretchViewport(1200, 600, camera);
        // herd a group of zombies
        herd = new Herd((TiledMapTileLayer) loadedMap.getLayers().get(0));
        // put zombies in list
        zombies = herd.getZombiesList();

    }

    @Override
    public void render(float delta) {
        // Checks if the map needs changing
        if (playerControls.checkExit(exits)) {
            musicList.get(currentList).stop();
            hud.setMapLabel(currentMapLabel.get(currentList));
            currentList++;
            if (currentList > 4) {
                currentList = 4;
            }
            loadedMap.dispose();
            updateMap();
            renderer.setMap(loadedMap);
            if (SettingsManager.getMusic()) {
                musicList.get(currentList).play();
            }
            playerControls.setCollisions((TiledMapTileLayer) loadedMap.getLayers().get(0));
            herd.setCollisions((TiledMapTileLayer) loadedMap.getLayers().get(0));
            herd.respawnZombies();
            playerControls.setMapChange(false);

            spawnX = map.getRespawnX();
            spawnY = map.getRespawnY();

            playerControls.resetDirection();

            handler = new ScreenplayHandler();
            ScreenplayNode faint = new ScreenplayNode(chosenCharacter + ":\n*You hear faint sounds far away*   [ENTER]",
                    0);
            ScreenplayNode faint2 = new ScreenplayNode(chosenCharacter + ":\n.. ...   [ENTER]", 1);

            faint.makeLinear(faint2.getId());
            handler.addNode(faint);
            handler.addNode(faint2);
            dialogueController.startDialogue(handler);

            Gdx.input.setInputProcessor(processor);
        }

        // Checks if the player's health needs reducing due to a zombie
        if (playerControls.isOnZombie(herd.getZombiesList())) {
            hud.reduceHealth(0.01f); // Parameter may need changing
        }

        // Checks if the player's health is 0, if so re-spawn them
        if (hud.getHealth() == 0.0f) {
            hud.resetHealth();
            playerControls.updatePlayerCoordinates(spawnX, spawnY);
            hud.decreaseLife();
            if (hud.getLives() == 0) {
                musicList.get(currentList).stop();
                ScreenManager.setGameOver(); // game over
                gameOver.play();
            }
        }

        // control one zombie to test collisions
        // zombies.get(0).update(delta);
        playerControls.update(delta);
        player.update(delta);

        camera.position.set(player.getX() * GameSettings.SCALED_TILE_SIZE,
                player.getY() * GameSettings.SCALED_TILE_SIZE, 0);
        camera.position.y = player.getLinearY() * 64;
        camera.position.x = player.getLinearX() * 64;
        for (int i = 0; i < zombies.size(); i++) {
            zombies.get(i).setDown(true);
            zombies.get(i).setUp(true);
            zombies.get(i).setRight(true);
            zombies.get(i).setLeft(true);
        }
        for (int i = 0; i < zombies.size(); i++) {
            // update all zombies
            zombies.get(i).detectPlayerPosition(playerControls.getPlayer());
            zombies.get(i).update(delta, zombies);
        }
        // follow that zombie
        // camera.position.y = zombies.get(0).y * 64;
        // camera.position.x = zombies.get(0).x * 64;
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
            // Access Each Zombie in the zombies arraylist and render
            batch.draw(zombies.get(i).getSprite(),
                    ((int) zombies.get(i).x * GameSettings.SCALED_TILE_SIZE),
                    ((int) zombies.get(i).y) * GameSettings.SCALED_TILE_SIZE,
                    GameSettings.SCALED_TILE_SIZE * 1f,
                    GameSettings.SCALED_TILE_SIZE * 1f);
        }
        if (maps.indexOf(map) == 1) {//traps located on second map
            for (int i = 0; i < traps.getTraps().size(); i++) {
                traps.getTraps().get(i).setPlayerPosition(player.getX(), player.getY());
                if ((int) player.getY() + 1 == (int) traps.getTraps().get(i).getPosY()) {//start trap when player in line with trrap
                    traps.getTraps().get(i).setShoot(true);
                }
                //update trap and render
                if (traps.getTraps().get(i).getShoot() || traps.getTraps().get(i).getUsed()) {
                    traps.getTraps().get(i).update(delta);
                    batch.draw(traps.getTraps().get(i).getSprite(),
                            (traps.getTraps().get(i).x * GameSettings.SCALED_TILE_SIZE) - (GameSettings.SCALED_TILE_SIZE / 2),
                            traps.getTraps().get(i).y * GameSettings.SCALED_TILE_SIZE, GameSettings.SCALED_TILE_SIZE * 0.4f,
                            GameSettings.SCALED_TILE_SIZE * 0.4f);
                }
                //detect whether trap  x y= player x y
                if ((((int) (traps.getTraps().get(i).getPosX()) >= (int) (player.getX())
                        && (int) (traps.getTraps().get(i).getPosX()) <= (int) (player.getX() + 1)))
                        && (((int) (traps.getTraps().get(i).getPosY()) >= (int) (player.getY())
                        && (int) (traps.getTraps().get(i).getPosY()) <= (int) (player.getY()) + 1))) {
                    hud.reduceHealth(0.01f);//reduce player health
                }
            }
        }
        if (maps.indexOf(map) == 4) {//show robot on 1st boss map

            //update robot
            if (!robot.isDead()) {
                robotController.setPlayerPosition(playerControls.getPlayer().getX(), playerControls.getPlayer().getY());
                robotController.update(delta);
                for (int i = 0; i < robot.getBullets().size(); i++) {
                    robot.getBullets().get(i).setPlayerPosition(player.getX(), player.getY());
                    robot.getBullets().get(i).update(delta);//send bullet to player xy
                    if (robot.getBullets().get(i).getShoot()) {//if bullet shot render
                        batch.draw(robot.getBullets().get(i).getSprite(),
                                (robot.getBullets().get(i).x * GameSettings.SCALED_TILE_SIZE)
                                - (GameSettings.SCALED_TILE_SIZE / 2),
                                robot.getBullets().get(i).y * GameSettings.SCALED_TILE_SIZE,
                                GameSettings.SCALED_TILE_SIZE / 5f, GameSettings.SCALED_TILE_SIZE / 5f);
                    }
                    //if bullet xy = player xy reduce health and remove bullet
                    if ((((int) (robot.getBullets().get(i).x) >= (int) (player.getX())
                            && (int) (robot.getBullets().get(i).x) <= (int) (player.getX() + 1)))
                            && (((int) (robot.getBullets().get(i).y) >= (int) (player.getY())
                            && (int) (robot.getBullets().get(i).y) <= (int) (player.getY()) + 1))) {
                        robot.getBullets().get(i).setShoot(false);
                        hud.reduceHealth(robot.getBullets().get(i).getDamage());
                        robot.getBullets().remove(robot.getBullets().get(i));
                    }
                    //once player respawns any bullets previously shot remove
                    if (hud.getHealth() == 0) {
                        robot.getBullets().removeAll(robot.getBullets());
                    }
                }
                //render robot
                batch.draw(robot.getSprite(),
                        (robot.x * GameSettings.SCALED_TILE_SIZE) - (GameSettings.SCALED_TILE_SIZE / 2) + 20,
                        robot.y * GameSettings.SCALED_TILE_SIZE, GameSettings.SCALED_TILE_SIZE * 1.7f,
                        GameSettings.SCALED_TILE_SIZE * 2f);

                books = playerControls.getBooks();
                ArrayList<Book> booksToRemove = new ArrayList<Book>();
                for (int i = 0; i < books.size(); i++) {
                    Book b = books.get(i);
                    b.render(batch);
                    if (playerControls.isBlocked((int) b.getX(), (int) b.getY(), playerControls.getCollisionLayer())) {
                        booksToRemove.add(b);
                    }
                    float robotX = (robot.x * GameSettings.SCALED_TILE_SIZE) - (GameSettings.SCALED_TILE_SIZE / 2);
                    float robotWidth = robotX + (GameSettings.SCALED_TILE_SIZE * 2f);
                    float robotY = (robot.y * GameSettings.SCALED_TILE_SIZE);
                    float robotHeight = robotY + (GameSettings.SCALED_TILE_SIZE * 2f);

                    float bookX = (b.getX() * GameSettings.SCALED_TILE_SIZE) - 10;
                    float bookWidth = bookX + 9;
                    float bookY = (b.getY() * GameSettings.SCALED_TILE_SIZE) + 10;
                    float bookHeight = bookY + 9;

                    if ((robotWidth >= bookWidth) && (robotX <= bookWidth)) {
                        if ((robotHeight >= bookHeight) && (robotY <= bookHeight)) {
                            robot.damage(5);
                            System.out.println("hit boss");
                            if (robot.getHealth() <= 0) {
                                hud.increaseScore("boss");
                                robot.setDead();
                            }
                            booksToRemove.add(b);

                        }
                    }
                    b.update(delta);
                }
                books.removeAll(booksToRemove);

                keyboards = playerControls.getKeyboards();
                ArrayList<Keyboard> keyboardsToRemove = new ArrayList<Keyboard>();
                for (int i = 0; i < keyboards.size(); i++) {
                    Keyboard k = keyboards.get(i);
                    k.render(batch);

                    if (playerControls.isBlocked((int) k.getX(), (int) k.getY(), playerControls.getCollisionLayer())) {
                        keyboardsToRemove.add(k);
                    }
                    float robotX = (robot.x * GameSettings.SCALED_TILE_SIZE) - (GameSettings.SCALED_TILE_SIZE / 2);
                    float robotWidth = robotX + (GameSettings.SCALED_TILE_SIZE * 2f);
                    float robotY = (robot.y * GameSettings.SCALED_TILE_SIZE);
                    float robotHeight = robotY + (GameSettings.SCALED_TILE_SIZE * 2f);

                    float keyboardX = (k.getX() * GameSettings.SCALED_TILE_SIZE) - 10;
                    float keyboardWidth = keyboardX + 9;
                    float keyboardY = (k.getY() * GameSettings.SCALED_TILE_SIZE) + 10;
                    float keyboardHeight = keyboardY + 9;

                    if ((robotWidth >= keyboardWidth) && (robotX <= keyboardWidth)) {
                        if ((robotHeight >= keyboardHeight) && (robotY <= keyboardHeight)) {
                            System.out.println(robot.getHealth());
                            System.out.println(k.getX());
                            System.out.println("Keyboard Hit");
                            robot.damage(7);
                            if (robot.getHealth() <= 0) {
                                hud.increaseScore("boss");
                                robot.setDead();
                            }
                            keyboardsToRemove.add(k);

                        }
                    }
                    k.update(delta);
                }
                keyboards.removeAll(keyboardsToRemove);
            }
            if (robot.isDead()) {
                updateMap();
            }
        }

        if (maps.indexOf(map) == 6) {//second boss map

            bossController.setPlayerPosition(player.getX(), player.getY());
            bossController.update(delta);
            batch.draw(bossZombie.getSprite(),
                    (bossZombie.x * GameSettings.SCALED_TILE_SIZE) - (GameSettings.SCALED_TILE_SIZE / 2),
                    bossZombie.y * GameSettings.SCALED_TILE_SIZE, GameSettings.SCALED_TILE_SIZE * 1f,
                    GameSettings.SCALED_TILE_SIZE * 1f);

            for (int i = 0; i < bossZombie.getBullets().size(); i++) {//boss shooting heads
                bossZombie.getBullets().get(i).setPlayerPosition(player.getX(), player.getY());
                bossZombie.getBullets().get(i).update(delta);
                if (bossZombie.getBullets().get(i).getShoot()) {
                    //render head bullets
                    batch.draw(bossZombie.getBullets().get(i).getSprite(),
                            (bossZombie.getBullets().get(i).x * GameSettings.SCALED_TILE_SIZE)
                            - (GameSettings.SCALED_TILE_SIZE / 2),
                            bossZombie.getBullets().get(i).y * GameSettings.SCALED_TILE_SIZE,
                            GameSettings.SCALED_TILE_SIZE / 3f, GameSettings.SCALED_TILE_SIZE / 3f);
                }
                //if bullet/head xy = p xy reduce health and remove
                if ((((int) (bossZombie.getBullets().get(i).x) >= (int) (player.getX())
                        && (int) (bossZombie.getBullets().get(i).x) <= (int) (player.getX() + 1)))
                        && (((int) (bossZombie.getBullets().get(i).y) >= (int) (player.getY())
                        && (int) (bossZombie.getBullets().get(i).y) <= (int) (player.getY()) + 1))) {
                    bossZombie.getBullets().get(i).setShoot(false);
                    hud.reduceHealth(bossZombie.getBullets().get(i).getDamage());
                    bossZombie.getBullets().remove(bossZombie.getBullets().get(i));
                }
            }
        }
        books = playerControls.getBooks();
        ArrayList<Book> booksToRemove = new ArrayList<Book>();
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            b.render(batch);

            if (playerControls.isBlocked((int) b.getX(), (int) b.getY(), playerControls.getCollisionLayer())) {
                booksToRemove.add(b);
            }
            for (int j = 0; j < herd.getZombiesList().size(); j++) {
                Zombie zombie = herd.getZombiesList().get(j);

                float zombieX = (zombie.x * GameSettings.SCALED_TILE_SIZE) - (GameSettings.SCALED_TILE_SIZE / 2);
                float zombieWidth = zombieX + (GameSettings.SCALED_TILE_SIZE * 1f);
                float zombieY = (zombie.y * GameSettings.SCALED_TILE_SIZE);
                float zombieHeight = zombieY + (GameSettings.SCALED_TILE_SIZE * 1f);

                float bookX = (b.getX() * GameSettings.SCALED_TILE_SIZE) - 10;
                float bookWidth = bookX + 9;
                float bookY = (b.getY() * GameSettings.SCALED_TILE_SIZE) + 10;
                float bookHeight = bookY + 9;

                if ((zombieWidth >= bookWidth) && (zombieX <= bookWidth)) {
                    if ((zombieHeight >= bookHeight) && (zombieY <= bookHeight)) {
                        System.out.println(zombie.getHealth());
                        System.out.println(b.getX());
                        System.out.println("hit");
                        zombie.damage(30);
                        if (zombie.getHealth() <= 0) {
                            herd.getZombiesList().remove(j);
                            hud.increaseScore("zombie");
                        }
                        booksToRemove.add(b);

                    }
                }
            }
            b.update(delta);
        }
        books.removeAll(booksToRemove);

        keyboards = playerControls.getKeyboards();
        ArrayList<Keyboard> keyboardsToRemove = new ArrayList<Keyboard>();
        for (int i = 0; i < keyboards.size(); i++) {
            Keyboard k = keyboards.get(i);
            k.render(batch);

            if (playerControls.isBlocked((int) k.getX(), (int) k.getY(), playerControls.getCollisionLayer())) {
                keyboardsToRemove.add(k);
            }
            for (int j = 0; j < herd.getZombiesList().size(); j++) {
                Zombie zombie = herd.getZombiesList().get(j);

                float zombieX = (zombie.x * GameSettings.SCALED_TILE_SIZE) - (GameSettings.SCALED_TILE_SIZE / 2);
                float zombieWidth = zombieX + (GameSettings.SCALED_TILE_SIZE * 1f);
                float zombieY = (zombie.y * GameSettings.SCALED_TILE_SIZE);
                float zombieHeight = zombieY + (GameSettings.SCALED_TILE_SIZE * 1f);

                float keyboardX = (k.getX() * GameSettings.SCALED_TILE_SIZE) - 10;
                float keyboardWidth = keyboardX + 9;
                float keyboardY = (k.getY() * GameSettings.SCALED_TILE_SIZE) + 10;
                float keyboardHeight = keyboardY + 9;

                if ((zombieWidth >= keyboardWidth) && (zombieX <= keyboardWidth)) {
                    if ((zombieHeight >= keyboardHeight) && (zombieY <= keyboardHeight)) {
                        System.out.println(zombie.getHealth());
                        System.out.println(k.getX());
                        System.out.println("Keyboard Hit");
                        zombie.damage(50);
                        if (zombie.getHealth() <= 0) {
                            herd.getZombiesList().remove(j);
                            hud.increaseScore("zombie");
                        }
                        keyboardsToRemove.add(k);

                    }
                }
            }
            k.update(delta);
        }
        keyboards.removeAll(keyboardsToRemove);

        ArrayList<Item> currentMapItems = currentInv.getMapItems();
        ArrayList<Item> currentHUDItems = currentInv.getHUDItems();
        ArrayList<Item> foundMapItems = new ArrayList<Item>();

        for (Item currentItem : currentHUDItems) {

            if (currentItem.getFound() == true && currentItem.getInvDrawn() == false) {
                hud.addLatestFoundItemToInv(currentItem, currentInv.getDrinkDrawn());

                currentItem.setInvDrawn(true);

            }

        }

        for (Item currentItem : currentMapItems) {
            currentItem.render(batch);

            if (playerControls.isOnItem(currentItem) == true) {
                foundMapItems.add(currentItem);
                currentItem.setOnMap(false);
                currentItem.setItemFound(true);

                System.out.println();
                System.out.println("GS: Item Found: " + currentItem);

                if (currentItem.getInvDrawn() == false) {
                    if (currentItem.getName().equals("Drink")) {
                        if (currentInv.getDrinkDrawn() == false) {
                            hud.addLatestFoundItemToInv(currentItem, currentInv.getDrinkDrawn());

                            currentDrinkID = currentItem.getDrinkID();
                            currentInv.setDrinkDrawn(true);
                            currentInv.getInventory().get(currentInv.findDrinkPosition()).setItemFound(true);
                        }

                    } else {
                        hud.addLatestFoundItemToInv(currentItem, currentInv.getDrinkDrawn());

                        currentItem.setInvDrawn(true);
                    }

                }
            }
        }

        currentMapItems.removeAll(foundMapItems);

        currentInv = playerControls.equipItem(currentInv);

        if (currentInv.getCurrentItem() != null) {
            hud.drawEquippedItem(currentInv.getCurrentItem());

            if (currentInv.getCurrentItem().getName().equals("Book")) {
                updateToBook();

            } else if (currentInv.getCurrentItem().getName().equals("Keyboard")) {
                updateToKeyboard();

            } else {
                resetPlayerAnimations();

            }

        } else {
            resetPlayerAnimations();

        }

        Item currentUsedItem = playerControls.itemPressed();

        if (currentUsedItem != null) {
            for (Item currentItem : currentInv.getInventory()) {
                if (currentUsedItem.getName().equals("Drink")) {
                    if (currentInv.getCurrentItem() != null && currentItem.getDrinkID() == currentDrinkID) {
                        System.out.println("GS: Increasing Health");

                        //showDrinkAnimation();
                        //resetPlayerAnimations();
                        hud.increaseHealth(0.25f);
                        hud.removeEquippedItem(currentItem);

                        currentInv.setDrinkDrawn(false);
                        currentInv.getInventory().get(currentInv.findDrinkPosition()).setItemFound(false);

                        currentInv.getCurrentItem().setBeingUsed(false);
                        currentInv.setAsCurrentItem(null);
                        hud.drawEquippedItem(null);

                    }

                } else if (currentUsedItem.getName().contains("Potion")) {
                    if (currentItem.equals(currentUsedItem) && playerControls.isOnVent()) {
                        System.out.println("GS: Using " + currentItem.getName() + " on Vent");

                        hud.removeEquippedItem(currentItem);

                        currentInv.getCurrentItem().setItemFound(false);
                        currentInv.getCurrentItem().setBeingUsed(false);
                        currentInv.getCurrentItem().setInvDrawn(false);
                        currentInv.getCurrentItem().setPotionUsed(true);
                        currentInv.setAsCurrentItem(null);
                        hud.drawEquippedItem(null);

                    }
                }
            }
        }

        playerControls.getPlayerXY();

        if (currentInv.allPotionsUsed()) {
            System.out.println("POTIONS HAVE DEACTIVATED VIRUS");

        }

        if (player.getX() == 92 && player.getY() == 4 && playerControls.getInteract()) {
            elapsed += delta;
            playerControls.resetDirection();
            table2.setFillParent(true);
            table2.setDebug(true);
            table2.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/horror.png"))));
            stage.addActor(table2);
            handler = new ScreenplayHandler();
            ScreenplayNode faint = new ScreenplayNode("Someone has too much time on their hands!  [ENTER]", 0);
            ScreenplayNode faint2 = new ScreenplayNode(
                    chosenCharacter + ":\n.....   [ENTER]", 1);
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("music/scream.mp3"));
            if (elapsed == delta) {
                sound.play();
            }

            faint.makeLinear(faint2.getId());
            handler.addNode(faint);
            handler.addNode(faint2);
            dialogueController.startDialogue(handler);

            if (elapsed > 1.0f) {
                table2.clear();
                stage.addAction(Actions.removeActor(table2));
                playerControls.setInteractFalse();
                elapsed = 0.0f;
            }
        }

        if (maps.indexOf(map) == 0 && player.getX() > 51 && player.getY() > 45 && player.getX() < 57 && been == false) {
            playerControls.resetDirection();
            handler = new ScreenplayHandler();
            ScreenplayNode faint = new ScreenplayNode(chosenCharacter + ":\nTime for another stressful day  [ENTER]", 0);
            ScreenplayNode faint2 = new ScreenplayNode(
                    chosenCharacter + ":\nWhat's that sound?   [ENTER]", 1);

            faint.makeLinear(faint2.getId());
            handler.addNode(faint);
            handler.addNode(faint2);
            dialogueController.startDialogue(handler);

            been = true;
        }

        if (maps.indexOf(map) == 0 || maps.indexOf(map) == 1 || maps.indexOf(map) == 4) {
            zombies.removeAll(zombies);
        }

        riddle = new RiddleCard("card", 25, 60, "images/card 111px.png");
        riddle2 = new RiddleCard("card", 35, 60, "images/card 111px.png");

        // riddleUI.windowAdd(ok, label);
        if (playerControls.isOnRiddle(riddle) == true || playerControls.isOnRiddle(riddle2) == true) {
            hud.addWindow();
        }
        // stage.addActor(riddleUI.getWindow());
        if (Gdx.input.isKeyPressed(Input.Keys.V)) {
            hud.addWinLabel();
        } else if (Gdx.input.isKeyPressed(Input.Keys.Z)
                || (Gdx.input.isKeyPressed(Input.Keys.X) || (Gdx.input.isKeyPressed(Input.Keys.C)))) {

            hud.addLoseLabel();
            wrong = true;
        } else if ((Gdx.input.isKeyPressed(Input.Keys.R))) {
            hud.resetRiddle();
        } else {

            hud.removeWindow();

        }

        if (wrong == true) {

            riddle2.render(batch);
        } else {
            riddle.render(batch);
        }

        batch.end();

        mapBatch.begin();
        smoke.smokeUpdateAndDraw(mapBatch, delta);
        smoke2.smokeUpdateAndDraw2(mapBatch, delta);
        mapBatch.end();
        stage.draw();
    }

    /**
     * Updates {@link #map} to the next map in {@link #maps}. The player's
     * coordinates are updated for the new map. {@link #exits} is updated with
     * the new map's exit coordinates. {@link #loadedMap} is changed to be the
     * <code>TiledMap</code> for the new map.
     */
    private void updateMap() {
        int newMap = maps.indexOf(map) + 1;

        map = maps.get(newMap);
        player.updateCoordinates(map.getRespawnX(), map.getRespawnY());
        exits = map.getExits();
        loadedMap = new TmxMapLoader().load(map.getMapLocation());

        int mapInv = currentInv.getMapNumber();
        currentInv = new InventorySystem();
        currentInv.defineInventory(((TiledMapTileLayer) loadedMap.getLayers().get(0)), mapInv + 1);
        currentInv.setDrinkDrawn(false);
        hud.removeAllFoundItems();
        hud.drawEquippedItem(null);
    }

    /**
     * Changes the player sprite so that it is equipped with a book.
     */
    public void updateToBook() {
        assetManager = new AssetManager();
        assetManager.load("sprite/" + gender + "/book/" + chosenCharacter + "_walking.atlas", TextureAtlas.class);
        assetManager.load("sprite/" + gender + "/book/" + chosenCharacter + "_standing.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas walking = this.getAssetManager()
                .get("sprite/" + gender + "/book/" + chosenCharacter + "_walking.atlas", TextureAtlas.class);
        TextureAtlas standing = this.getAssetManager()
                .get("sprite/" + gender + "/book/" + chosenCharacter + "_standing.atlas", TextureAtlas.class);

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

        player.setAnimations(animations);
    }

    /**
     * Changes the player sprite so that it is equipped with a keyboard.
     */
    public void updateToKeyboard() {
        assetManager = new AssetManager();
        assetManager.load("sprite/" + gender + "/keyboard/" + chosenCharacter + "_walking.atlas", TextureAtlas.class);
        assetManager.load("sprite/" + gender + "/keyboard/" + chosenCharacter + "_standing.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas walking = this.getAssetManager()
                .get("sprite/" + gender + "/keyboard/" + chosenCharacter + "_walking.atlas", TextureAtlas.class);
        TextureAtlas standing = this.getAssetManager()
                .get("sprite/" + gender + "/keyboard/" + chosenCharacter + "_standing.atlas", TextureAtlas.class);

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

        player.setAnimations(animations);
    }

    /**
     * <p>
     * Method used to reset the player animations, so that they are not holding
     * any items
     */
    public void resetPlayerAnimations() {
        assetManager = new AssetManager();
        assetManager.load("sprite/" + gender + "/" + chosenCharacter + "_walking.atlas", TextureAtlas.class);
        assetManager.load("sprite/" + gender + "/" + chosenCharacter + "_standing.atlas", TextureAtlas.class);
        assetManager.load("sprite/" + gender + "/" + chosenCharacter + "_powered_walking.atlas", TextureAtlas.class);
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

        player.setAnimations(animations);

    }

    /**
     * <p>
     * Method used to find the current position of the Drink object within the
     * current Inventory
     *
     * @param currentInv InventorySystem used to find the Drink object
     * @return Int of the position of the Drink object
     */
    public int findDrinkPosition(InventorySystem currentInv) {
        int pos = 0;

        for (Item currentItem : currentInv.getInventory()) {
            if (currentItem instanceof Drink) {
                pos = currentInv.getInventory().indexOf(currentItem);

                break;

            }

        }

        return pos;

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
        //dispose();
    }

    @Override
    public void dispose() {
        inGameMp3.dispose();
        loadedMap.dispose();
        assetManager.dispose();
        batch.dispose();
        mapBatch.dispose();
        stage.dispose();
        renderer.dispose();
    }

    public InventorySystem getInventory() {
        return currentInv;

    }

}
