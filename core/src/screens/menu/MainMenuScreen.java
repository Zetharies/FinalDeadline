package screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import actions.FadeOutAction;
import managers.ScreenManager;
import screens.intro.AbstractScreen;

public class MainMenuScreen extends AbstractScreen {
    private Table testTable;
    private Stage stage;
    private Skin skin;
    private boolean swipe = false, playing = false;
    private Window window;
    private TextButton play, levels, settings, credits, quit, back, male, female, confirm, exit;
    private Music mp3Sound;
    
    public MainMenuScreen() {
    	
        super();
        stage = new Stage();
        testTable = new Table();
        testTable.setFillParent(true);
        testTable.setDebug(true);
        
        mp3Sound = Gdx.audio.newMusic(Gdx.files.internal("music/adventureSoundtrack.mp3")); // background soundtrack, feel free to change
        mp3Sound.setLooping(true); // loop the soundtrack
        mp3Sound.play(); // play the soundtrack
    }
 
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f); // Red, Green, Blue, Alpha (Alpha is transparency)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
 
    @Override
    public void hide() { }
 
    @Override
    public void pause() { }
 
    @Override
    public void resume() { }
    
    @Override
    public void show() { }
    
    @Override
    public void resize(int width, int height) {
    	
    	stage.clear(); // Clear screen on resize
    	swipe = false;
    	
    	
    	testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gamemenu.png"))));
    	skin = new Skin(Gdx.files.internal("fonts/Holo-dark-hdpi.json"));
    	
    	settings = new TextButton("SETTINGS", skin);
        play = new TextButton("PLAY", skin);
        confirm = new TextButton("Confirm", skin);
        exit = new TextButton("Cancel", skin);
        window = new Window("Would you like to exit?", skin);
        levels = new TextButton("LEVELS", skin);
        credits = new TextButton("CREDITS", skin);
        quit = new TextButton("QUIT", skin);
        back = new TextButton("BACK", skin);
        male = new TextButton("MALE", skin);
        female = new TextButton("FEMALE", skin);
        
        //set the settings size first so others can replicate 
        settings.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));

        //10% taken off button height
        back.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        back.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 2);
        male.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        male.setPosition((Gdx.graphics.getWidth() / 3) + (Gdx.graphics.getWidth() / 4), Gdx.graphics.getHeight() / 2);
        female.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        female.setPosition((Gdx.graphics.getWidth() / 3) + (Gdx.graphics.getWidth() / 4) + 300, Gdx.graphics.getHeight() / 2);
        back.addAction(Actions.hide());
        male.addAction(Actions.hide());
        female.addAction(Actions.hide());
        play.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        play.setPosition(0, Gdx.graphics.getHeight() / 2);
        levels.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        levels.setPosition(0, play.getY() - play.getHeight());
        settings.setPosition(0, levels.getY() - levels.getHeight());
        credits.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        credits.setPosition(0, settings.getY() - levels.getHeight());
        quit.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        quit.setPosition(0, credits.getY() - settings.getHeight());

        //confirmation for exit window
        window.add(exit).pad(5);
        confirm.setPosition(0, window.getHeight());
        exit.setPosition(0, window.getHeight());
        window.add(confirm).pad(5);
        window.setSize(window.getPrefWidth(), window.getPrefHeight());
        //sets the position of the window to the middle of the screen
        window.setPosition((Gdx.graphics.getWidth() / 2) - (window.getWidth() / 2), Gdx.graphics.getHeight() / 2);
    	
        
        
        buttonListener(); // Call the buttonListener method
        
        
        // Add everything to screen
        
        stage.addActor(testTable);
    	stage.addActor(play);
    	stage.addActor(levels);
    	stage.addActor(settings);
    	stage.addActor(credits);
    	stage.addActor(quit);
    	stage.addActor(back);
    	stage.addActor(male);
    	stage.addActor(female);
    	
    	
    	//stage input handling for actors
        Gdx.input.setInputProcessor(stage);
    	
    }
    
    public void buttonListener() {
        //opens the pop up window to confirm exit
        quit.addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (!playing) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
                
                
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                playing = false;
            }
        	
            @Override
            public void clicked(InputEvent e, float x, float y) {
                stage.addActor(window);
            }
        });
        
        levels.addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (!playing) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
                
                
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                playing = false;
            }
        });
        
        credits.addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (!playing) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
                
                
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                playing = false;
            }
        });
        
        //cancels exit
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                testTable.addAction(Actions.removeActor(window));
            }
        });
        //exits the game
        confirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                System.exit(0);
            }
        });
        //go to the character selection screen
        play.addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (!playing) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                playing = false;
            }
        	
        	
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (!swipe && back.getActions().size == 0) {
                    //should bring the character selection options also
                    //0.8f is the duration
                    play.addAction(Actions.moveBy(-(play.getWidth()), 0, 0.8f));
                    levels.addAction(Actions.moveBy(-(levels.getWidth()), 0, 0.8f));
                    settings.addAction(Actions.moveBy(-(settings.getWidth()), 0, 0.8f));
                    credits.addAction(Actions.moveBy(-(settings.getWidth()), 0, 0.8f));
                    quit.addAction(Actions.moveBy(-(quit.getWidth()), 0, 0.8f));
                    back.addAction(Actions.show());
                    back.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));
                    male.addAction(Actions.show());
                    male.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));
                    female.addAction(Actions.show());
                    female.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));
                    swipe = true;
                }

            }
        });
        //back to main menu from character selection screen
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (swipe) {
                    Action action = Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f);
                    play.addAction(Actions.moveBy(+(play.getWidth()), 0, 0.8f));
                    levels.addAction(Actions.moveBy(+(levels.getWidth()), 0, 0.8f));
                    settings.addAction(Actions.moveBy(+(settings.getWidth()), 0, 0.8f));
                    credits.addAction(Actions.moveBy(+(settings.getWidth()), 0, 0.8f));
                    quit.addAction(Actions.moveBy(+(quit.getWidth()), 0, 0.8f));
                    //button is hidden after move by action is completed
                    back.addAction(Actions.sequence(action, Actions.hide()));
                    male.addAction(Actions.sequence(Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f), Actions.hide()));
                    female.addAction(Actions.sequence(Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f), Actions.hide()));
                    swipe = false;
                }

            }
        });
        settings.addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (!playing) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                playing = false;
            }
        	
            @Override
            public void clicked(InputEvent e, float x, float y) {
                //settings will be added 
            }
        });
        male.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
            	mp3Sound.pause();
            	Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/selectFX.mp3"));
                sound.play(0.5F);
            	stage.addAction(new FadeOutAction(1.25f) {
            		@Override
                    public void run() {
            			ScreenManager.setGameScreen(); // Sets the screen to our game >:D
                    }
            	});
               
            }
        });
        female.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
            	mp3Sound.pause();
            	Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/selectFX.mp3"));
                sound.play(0.5F);
            	stage.addAction(new FadeOutAction(1.25f) {
            		@Override
                    public void run() {
            			ScreenManager.setGameScreen(); // Sets the screen to our game >:D
                    }
            	});
               
            }
        });

    }
    
    public void hoverFX() {
    	
    }
 
    @Override
    public void dispose() {
        stage.dispose();
    }
}