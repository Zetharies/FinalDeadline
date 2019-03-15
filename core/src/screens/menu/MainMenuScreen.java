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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import actions.FadeOutAction;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import managers.ScreenManager;
import managers.SettingsManager;
import screens.intro.AbstractScreen;

public class MainMenuScreen extends AbstractScreen {

    //main screen background image added to 
    private Table testTable;
    //stage for screen
    private Stage stage;
    //skin for the style of the text
    private Skin skin;
    //to display user ability to press on character / levels
    private Texture maleCard, femaleCard, customizeCard, level1, level2;
    //booleans to check animation is in play and audio playing
    private boolean swipe, playing = false;
    //window for quit button pop up
    private Window window;
    //labels for associated buttons 
    private Label play, levels, settings, about, credits, quit, confirm, exit;
    //music to play within 
    private static Music mp3Sound;
    //buttons for customise screen gender selection into game and level selection
    private ImageButton customizeSelection, maleSelection, femaleSelection, floor2, floor4;
    //button to back to homescreen shown when a defaultbutton is pressed
    private TextButton back;
    //used to check if button if off/on screen
    private boolean aboutSwipe = false, levelsSwipe = false, creditsSwipe = false;
    public static boolean characterSelectSwipe = false;

    //bool values for whether buttons prssed
    private boolean settingsClicked = false;
    private boolean creditsClicked = false;
    private boolean characterSelectionClicked = false;
    private boolean aboutClicked;
    private boolean levelsClicked = false;
    //object to load settings
    private SettingsUI settingsUI;
    //object to load about screen
    private About aboutUI;

    //popup for about - write about text onto
    private Table aboutTable = new Table();
    //object to load credits screen
    private Credits creditsWindow;
    public static int RES_INDEX;

    //booleans to detect button swipes
    private boolean left = false;
    private boolean right = false;

    public MainMenuScreen() {
        super();
        //create stage for screen
        stage = new Stage(new StretchViewport(getWidth(), getHeight()));
        testTable = new Table();
        //fit stage into screen
        testTable.setFillParent(true);
        // turn on all debug lines (table, cell, and widget)
        testTable.setDebug(true);

        //load sound
        mp3Sound = Gdx.audio.newMusic(Gdx.files.internal("music/adventureSoundtrack.mp3")); // background soundtrack, feel free to change
        mp3Sound.setLooping(true); // loop the soundtrack
        mp3Sound.play(); // play the soundtrack
        mp3Sound.setVolume(0.25f);
        skin = new Skin(Gdx.files.internal("fonts/Holo-dark-hdpi.json")); // skin to be applied to buttons 
        skin.getFont("default-font").getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

//        camera = new PerspectiveCamera();
//        viewPort = new StretchViewport(800, 480, camera);
    }

    /**
     * get music to play
     *
     * @return
     */
    public static Music getMP3() {
        return mp3Sound;
    }

    /**
     * render elements to screen
     *
     * @param delta
     */
    @Override
    public void render(float delta) {
        //sound settings
        if (!SettingsManager.getSound() || !SettingsManager.getMusic()) {
            mp3Sound.pause();
        } else {
            mp3Sound.play();
        }
        if (settingsUI.APPLY_CLICKED) {
            exitSettings();
        }
        //button functionality
        //exit methods move screen back to home screen showing start buttons
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            if (settingsClicked) {
                exitSettings();
                settingsClicked = false; // set to false 
            }
            if (levelsClicked) {
                exitLevels();
                levelsClicked = false;
            }
            if (aboutClicked) {
                exitAbout();
                aboutClicked = false;
            }
            if (creditsClicked) {
                exitCredits();
                creditsClicked = false;
            }
            if (characterSelectionClicked) {
                exitCharacterSelection();
                characterSelectionClicked = false;
            }
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f); // Red, Green, Blue, Alpha (Alpha is transparency)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act(delta);//limited 30fps 
        stage.draw();//render to screen
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
    }

    /**
     * method used to resize screen with resolution change
     *
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        CharacterSelection.EXIT = false; // set to false as rendered to main screen - set to true once button pressed
        swipe = false;

        settingsUI = new SettingsUI(stage, skin); //instatiate settings object - use method to render ui
        settingsUI.getTable().addAction(Actions.hide());//set settings hidden - set to show once button pressed

        stage.clear(); // Clear screen on resize
        stage.addAction(Actions.sequence(Actions.alpha((float) 0.35), Actions.fadeIn((float) 1.2)));//fade in motion

        //background to main screen set 
        testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gamemenu.png"))));

        //shown for when user pressed play
        customizeCard = new Texture(Gdx.files.internal("images/customizeSelection.png"));
        maleCard = new Texture(Gdx.files.internal("images/flynnSelection.png"));
        femaleCard = new Texture(Gdx.files.internal("images/jessicaSelection.png"));

        //levels buttons texture added
        level1 = new Texture(Gdx.files.internal("images/floor1Image.png"));
        level2 = new Texture(Gdx.files.internal("images/floor2Image.png"));

        //buttons for player cards
        customizeSelection = new ImageButton(new TextureRegionDrawable(new TextureRegion(customizeCard)));
        maleSelection = new ImageButton(new TextureRegionDrawable(new TextureRegion(maleCard)));
        femaleSelection = new ImageButton(new TextureRegionDrawable(new TextureRegion(femaleCard)));

        //levels texture buttons added
        floor2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(level1)));
        floor4 = new ImageButton(new TextureRegionDrawable(new TextureRegion(level2)));

        //style for button
        Label.LabelStyle style = new Label.LabelStyle();
        //returns the bitmap font
        style.font = generateFont(skin);
        //font passed as parameter to change label font
        settings = new Label("SETTINGS", style);
        play = new Label("PLAY", style);
        levels = new Label("LEVELS", style);
        about = new Label("ABOUT", style);
        credits = new Label("CREDITS", style);
        quit = new Label("QUIT", style);
        back = new TextButton("[ESC] BACK", skin);
        window = new Window("Would you like to exit?", skin);
        exit = new Label("Cancel", skin);
        confirm = new Label("Confirm", skin);
        setButtons();
        buttonListener(); // Call the buttonListener method - listen for events
        //add buttons to screen x y and width height
        addButtons();
        //stage input handling for actors
        creditsWindow = new Credits(stage, style, aboutTable);
        //hide credits - show when button pressed
        creditsWindow.getTable().addAction(Actions.hide());
        //add credits to screen
        stage.addActor(creditsWindow.getTable());

        //load about object
        aboutUI = new About(skin);
        //hide on screen - set to show when pressed about
        aboutUI.getTable().addAction(Actions.hide());
        stage.addActor(aboutUI.getTable()); // add to screen

        //viewPort.update(width, height);
        int heightView = stage.getViewport().getScreenHeight();
        stage.getViewport().getScreenWidth();

        //when res changed set to home screen 
        if (heightView != stage.getViewport().getScreenHeight()) {
            // moveButtonsRight();
            stage.addAction(Actions.sequence(Actions.alpha((float) 0.35), Actions.fadeIn((float) 1.2)));
            //reset background
            testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gamemenu.png"))));
            //hide elements
            settingsUI.getTable().addAction(Actions.hide());
            creditsWindow.getTable().addAction(Actions.hide());
            aboutUI.getTable().addAction(Actions.hide());
            //swipe buttons back to home screen
            settingsClicked = false;
            creditsClicked = false;
            aboutClicked = false;
            levelsClicked = false;
            characterSelectionClicked = false;
        }
        settingsUI.settingsListener();//button listeners for elements in setting ui

        stage.getViewport().update(width, height, true);
        Gdx.input.setInputProcessor(stage);
    }

    public void setButtons() {
        //set x y width height for cards and set default to hide
        customizeSelection.setBounds((float) (getWidth() / 2), getHeight() / 3, (float) (Gdx.graphics.getWidth() / 5), Gdx.graphics.getHeight() / 2);
        customizeSelection.addAction(Actions.hide());
        maleSelection.setBounds(customizeSelection.getX() + customizeSelection.getWidth(), customizeSelection.getY(), (float) (Gdx.graphics.getWidth() / 5), (float) (Gdx.graphics.getHeight() / 2));
        maleSelection.addAction(Actions.hide());
        femaleSelection.setBounds(maleSelection.getX() + customizeSelection.getWidth(), customizeSelection.getY(), (float) (Gdx.graphics.getWidth() / 5), (float) (Gdx.graphics.getHeight() / 2));
        femaleSelection.addAction(Actions.hide());

        //set floor textures x y width height and set default hidden
        floor2.setBounds((float) (getWidth() / 6), getHeight() / 8, (float) (Gdx.graphics.getWidth() / 2), Gdx.graphics.getHeight() / 2);
        floor2.setSize((float) (getWidth() / 1.25), (float) (getHeight() / 1.25));
        floor2.addAction(Actions.hide());
        floor4.setBounds((float) (floor2.getX() * 3.5), floor2.getY(), (float) (Gdx.graphics.getWidth() / 5), (float) (Gdx.graphics.getHeight() / 2));
        floor4.setSize((float) (getWidth() / 1.25), (float) (getHeight() / 1.25));
        floor4.addAction(Actions.hide());

        //add fonts to butotn
        settings.setFontScale((float) (settings.getFontScaleX() + (settings.getFontScaleX() + 0.8)), (float) (settings.getFontScaleY() + (settings.getFontScaleY() + 0.8)));
        play.setFontScale((float) (play.getFontScaleX() + (play.getFontScaleX() + 0.8)), (float) (play.getFontScaleY() + (play.getFontScaleY() + 0.8)));
        levels.setFontScale((float) (levels.getFontScaleX() + (levels.getFontScaleX() + 0.8)), (float) (levels.getFontScaleY() + (levels.getFontScaleY() + 0.8)));
        about.setFontScale((float) (about.getFontScaleX() + 0.6), (float) (about.getFontScaleY() + 0.6));
        credits.setFontScale((float) (credits.getFontScaleX() + 0.6), (float) (credits.getFontScaleY() + 0.6));
        quit.setFontScale((float) (quit.getFontScaleX() + 0.6), (float) (quit.getFontScaleY() + 0.6));
        //set back butotn size for spec size
        back.setSize(settings.getPrefWidth() / 2, settings.getPrefHeight() / 2);

        //set buttons x y widht height
        play.setBounds(10, (float) (getHeight() / 2), play.getPrefWidth(), play.getPrefHeight());
        levels.setBounds(10, play.getY() - (float) (play.getHeight() / 1.2), settings.getPrefWidth(), settings.getPrefHeight());
        settings.setBounds(10, levels.getY() - (float) (levels.getHeight() / 1.2), settings.getPrefWidth(), settings.getPrefHeight());
        credits.setBounds(10, settings.getY() - (float) (settings.getHeight() / 2.5), credits.getPrefWidth(), credits.getPrefHeight());
        about.setBounds(10, credits.getY() - (float) (credits.getHeight() / 1.2), about.getPrefWidth(), about.getPrefHeight());
        quit.setBounds(10, about.getY() - (float) (about.getHeight() / 1.2), quit.getPrefWidth(), quit.getPrefHeight());

        back.setPosition((float) (Gdx.graphics.getWidth() * 1.10), Gdx.graphics.getHeight() / 20);
        //back hidden at start
        back.addAction(Actions.hide());

        //confirmation for exit window
        //pops up after quit is pressed
        window.add(exit).pad(5);
        confirm.setPosition(0, window.getHeight());//left of screen
        exit.setPosition(0, window.getHeight());//left of screen
        window.add(confirm).pad(5);
        window.setSize(window.getPrefWidth(), window.getPrefHeight());
        //sets the position of the window to the middle of the screen
        window.setPosition((Gdx.graphics.getWidth() / 2) - (window.getWidth() / 2), Gdx.graphics.getHeight() / 2);
    }

    /**
     * add buttons to screen called in render method
     */
    public void addButtons() { //buttons for each selection
        stage.addActor(testTable);
        stage.addActor(play);
        stage.addActor(levels);
        stage.addActor(settings);
        stage.addActor(about);
        stage.addActor(credits);
        stage.addActor(quit);
        stage.addActor(back);
        stage.addActor(customizeSelection);
        stage.addActor(maleSelection);
        stage.addActor(femaleSelection);
        stage.addActor(floor2);
        stage.addActor(floor4);
    }

    /**
     * listen for events - called in the render method
     */
    public void buttonListener() {
        //enter exit is the hover listener
        //opens the pop up window to confirm exit
        quit.addListener(new ClickListener() {
            //reference default font scale to return to default after event
            float heightFont = quit.getFontScaleX(), widthFont = quit.getFontScaleY();

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                //increase text size over hover
                quit.setFontScale(quit.getFontScaleX() + (quit.getFontScaleX() / 10), quit.getFontScaleY() + (quit.getFontScaleY() / 10));
                //settings sound check
                if (!playing && SettingsManager.getSound()) {
                    //play audio when hovered
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
            }

            //one exit reduce text size and stop playing audio
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                quit.setFontScale(widthFont, heightFont);
                playing = false;
            }

            //add window to screen and show character selection
            @Override
            public void clicked(InputEvent e, float x, float y) {
                stage.addActor(window);
                characterSelectionClicked = true;
            }
        });

        //listener for levels cards
        levels.addListener(new ClickListener() {
            float heightFont = levels.getFontScaleX(), widthFont = levels.getFontScaleY();//default sizes 

            //show levels and swipe buttons
            @Override
            public void clicked(InputEvent e, float x, float y) {
                levelsClicked = true;
                enterLevels();
            }

            //set font size increase and play audio
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                levels.setFontScale(levels.getFontScaleX() + (levels.getFontScaleX() / 10), levels.getFontScaleY() + (levels.getFontScaleY() / 10));
                if (!playing && SettingsManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                levels.setFontScale(widthFont, heightFont);
                playing = false;//stop playing audio on exit
            }
        });

        //credits button listener
        credits.addListener(new ClickListener() {
            float heightFont = credits.getFontScaleX(), widthFont = credits.getFontScaleY();//default size

            @Override
            public void clicked(InputEvent e, float x, float y) {
                creditsClicked = true;
                enterCredits();//show credits 
            }

            //increase size on hover and play audio
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                credits.setFontScale(credits.getFontScaleX() + (credits.getFontScaleX() / 10), credits.getFontScaleY() + (credits.getFontScaleY() / 10));
                if (!playing && SettingsManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                credits.setFontScale(widthFont, heightFont);
                playing = false;//stop playing audio
            }
        });

        //about button listener
        about.addListener(new ClickListener() {
            float heightFont = about.getFontScaleX(), widthFont = about.getFontScaleY();//default size

            @Override
            public void clicked(InputEvent e, float x, float y) {
                aboutClicked = true;
                enterAbout();//show about
            }

            //set size and play audio
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                about.setFontScale(about.getFontScaleX() + (about.getFontScaleX() / 10), about.getFontScaleY() + (about.getFontScaleY() / 10));
                if (!playing && SettingsManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                about.setFontScale(widthFont, heightFont);//reset font size 
                playing = false;//stops playing audio
            }
        });

        //cancels exit
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                testTable.addAction(Actions.removeActor(window)); // remove window from screen - back
            }
        });
        //exits the game
        confirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                System.exit(0); // exit program
            }
        });
        //go to the character selection screen
        play.addListener(new ClickListener() {
            float heightFont = play.getFontScaleX(), widthFont = play.getFontScaleY();

            //check audio to play and play and increase font size
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                play.setFontScale(play.getFontScaleX() + (play.getFontScaleX() / 10), play.getFontScaleY() + (play.getFontScaleY() / 10));
                if (!playing && SettingsManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                play.setFontScale(widthFont, heightFont);//reset font size
                playing = false;//restricts audio playing
            }

            @Override
            public void clicked(InputEvent e, float x, float y) {
                System.out.println("play clicked");
                characterSelectionClicked = true;
                enterCharacterSelection();//show character selection
            }
        });
        //back to main menu from character selection screen
        back.addListener(new ClickListener() {
            float heightFont = back.getScaleX(), widthFont = back.getScaleY();

            @Override
            public void clicked(InputEvent e, float x, float y) {
                //swpies back to main screen
//                if (testTable.hasChildren() && settingsClicked) {
//                    for (int i = 0; i < testTable.getChildren().size; i++) {
//                        if (testTable.getChildren().get(i) == settingsUI.getTable()) {
//                            exitSettings();
//                            settingsClicked = false;
//
//                        }
//                    }
//                    
//                }

                //exit screens 
                if (settingsClicked) {
                    exitSettings();
                    settingsClicked = false;
                }
                if (levelsClicked) {
                    exitLevels();
                    levelsClicked = false;
                }
                if (aboutClicked) {
                    //swipe = true;
                    exitAbout();
                    //swipe = false;
                    aboutClicked = false;
                }
                if (creditsClicked) {
                    exitCredits();
                    creditsClicked = false;
                }
                if (characterSelectionClicked) {
                    exitCharacterSelection();
                    characterSelectionClicked = false;
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //increase font size
                back.setScale(back.getScaleX() + (back.getScaleY() / 10), back.getScaleY() + (back.getScaleY() / 10));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.setScale(widthFont, heightFont); // set size back to default
            }
        });
        //settings button listener
        settings.addListener(new ClickListener() {
            float heightFont = settings.getFontScaleX(), widthFont = settings.getFontScaleY();//default size 

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                //increase font size
                settings.setFontScale(settings.getFontScaleX() + (settings.getFontScaleX() / 10), settings.getFontScaleY() + (settings.getFontScaleY() / 10));
                if (!playing && SettingsManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F); // play audio on hover
                    playing = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                settings.setFontScale(widthFont, heightFont);//set font size to default
                playing = false;
            }

            @Override
            public void clicked(InputEvent e, float x, float y) {
                settingsUI.APPLY_CLICKED = false;
                settingsClicked = true;
                enterSettings(); // go into settings
            }
        });
        
        //customize button listener
        customizeSelection.addListener(new ClickListener() {
            //default height and width for button
            float selectionHeight = customizeSelection.getHeight(), selectionWidth = customizeSelection.getWidth();

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //play audio on hover
                if (!playing && SettingsManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
                //customizeSelection.setSize((float) (Gdx.graphics.getWidth() / 1.75), (float) (Gdx.graphics.getHeight() / 1.75));
                //increase font size 
                customizeSelection.setSize((float) (customizeSelection.getWidth() + 10), (float) (customizeSelection.getHeight() + 10));
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playing = false;
                //customizeSelection.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
                customizeSelection.setSize(selectionWidth, selectionHeight);//reset size back to default
            }
            
            @Override
            public void clicked(InputEvent e, float x, float y) {
                mp3Sound.stop();//stop audio playing
                stage.addAction(new FadeOutAction(1.25f) {
                    @Override
                    public void run() {
                        stage.clear();//required for back animation
                        ScreenManager.setCharacterSelectionScreen(); // Sets the screen to game
                    }
                });

            }

        });
        //male character selection listener
        maleSelection.addListener(new ClickListener() {
            float selectionHeight = maleSelection.getHeight(), selectionWidth = maleSelection.getWidth();//default height width

            //play audio on hover 
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (!playing && SettingsManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
                maleSelection.setSize((float) (maleSelection.getWidth() + 10), (float) (maleSelection.getHeight() + 10));
            }

            //stop playing audio on hover and reset size 
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playing = false;
                //maleSelection.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
                maleSelection.setSize(selectionWidth, selectionHeight);
            }

            
            @Override
            public void clicked(InputEvent e, float x, float y) {
                mp3Sound.stop();//stop playing music
                if (SettingsManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/selectFX.mp3"));
                    sound.play(0.5F); //play button selection audio
                }
                //fade into game screen
                stage.addAction(new FadeOutAction(1.25f) {
                    @Override
                    public void run() {
                        ScreenManager.setGameScreen("Flynn"); // Sets the screen to our game with character flynn
                    }
                });

            }
        });
        //female selection listener 
        femaleSelection.addListener(new ClickListener() {
            //default width height for buttons
            float selectionHeight = femaleSelection.getHeight(), selectionWidth = femaleSelection.getWidth();

            //when mouse enters button play audio 
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (!playing && SettingsManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
                //increase button size
                femaleSelection.setSize((float) (femaleSelection.getWidth() + 10), (float) (femaleSelection.getHeight() + 10));
            }

            //on mouse exit reduce size and stop playing audio
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playing = false;
                femaleSelection.setSize(selectionWidth, selectionHeight);
            }

            @Override
            public void clicked(InputEvent e, float x, float y) {
                mp3Sound.stop();
                if (SettingsManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/selectFX.mp3"));
                    sound.play(0.5F);//play button selection audio
                }
                stage.addAction(new FadeOutAction(1.25f) {
                    @Override
                    public void run() {
                        ScreenManager.setGameScreen("Jessica"); // Sets the screen to our game with character jessica
                    }
                });
            }
        });

    }

    /**
     * swipes required buttons left
     */
    public void moveButtonsLeft() {
        if (left) {
            //x y duration
            Action action = Actions.moveBy(+(getWidth() / 4), 0, 0.8f);
            play.addAction(Actions.moveBy(+(play.getWidth() * 2), 0, 0.8f));
            levels.addAction(Actions.moveBy((float) +(levels.getWidth() * 1.5), 0, 0.8f));
            settings.addAction(Actions.moveBy((float) +(settings.getWidth() * 1.5), 0, 0.8f));
            credits.addAction(Actions.moveBy(+(credits.getWidth() * 2), 0, 0.8f));
            about.addAction(Actions.moveBy(+(about.getWidth() * 2), 0, 0.8f));
            quit.addAction(Actions.moveBy(+(quit.getWidth() * 2), 0, 0.8f));
            //button is hiden after move by action is completed
            back.addAction(Actions.sequence(action, Actions.hide()));
            
            left = false;//ensures only swipes once
        }

    }

    /**
     * called when buttons off screen return them when back button pressed
     */
    public void moveButtonsRight() {
        //hides the buttons
        //x y duration 
        play.addAction(Actions.moveBy(-(play.getWidth() * 2), 0, 0.8f));        //0.8f is the duration
        levels.addAction(Actions.moveBy((float) -(levels.getWidth() * 1.5), 0, 0.8f));
        settings.addAction(Actions.moveBy((float) -(settings.getWidth() * 1.5), 0, 0.8f));
        quit.addAction(Actions.moveBy(-(quit.getWidth() * 2), 0, 0.8f));
        credits.addAction(Actions.moveBy(-(credits.getWidth() * 2), 0, 0.8f));
        about.addAction(Actions.moveBy(-(about.getWidth() * 2), 0, 0.8f));
        //brings the back button to the screen
        back.addAction(Actions.show());
        back.addAction(Actions.moveBy(-(getWidth() / 4), 0, 0.8f));
    }

    /**
     * hides character selection elements when back pressed
     */
    public void exitCharacterSelection() {
        if (characterSelectSwipe) {
            left = true;
            moveButtonsLeft();
            //hide character selection
            stage.addAction(Actions.sequence(Actions.alpha((float) 0.35), Actions.fadeIn((float) 1.2)));//fade in animation for main screen
            //reset background image
            testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gamemenu.png"))));
            //fade out buttons and required elements
            customizeSelection.addAction(Actions.sequence((Actions.fadeOut((float) 0.85)), Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f)));
            customizeSelection.addAction(Actions.hide());
            maleSelection.addAction(Actions.sequence((Actions.fadeOut((float) 0.85)), Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f)));
            maleSelection.addAction(Actions.hide());
            femaleSelection.addAction(Actions.sequence((Actions.fadeOut((float) 0.85)), Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f)));
            femaleSelection.addAction(Actions.hide());
            characterSelectSwipe = false;
        }
    }

    /**
     * method to return from setting to main screen
     */
    public void exitSettings() {
        if (swipe) {
            left = true;
            moveButtonsLeft();
            settingsClicked = false;
            //fade main screen back in
            stage.addAction(Actions.sequence(Actions.alpha((float) 0.35), Actions.fadeIn((float) 1.2)));
            testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gamemenu.png"))));
            settingsUI.getTable().addAction(Actions.hide());
            //System.out.println(settingsUI.getTable().isVisible());
            swipe = false;
        }
    }

    /**
     * method to exit levels to main screen
     */
    public void exitLevels() {
        if (levelsSwipe) {
            left = true;
            moveButtonsLeft();
            levelsClicked = false;
            stage.addAction(Actions.sequence(Actions.alpha((float) 0.35), Actions.fadeIn((float) 1.2)));
            testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gamemenu.png"))));
            floor2.addAction((Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f)));
            floor2.addAction(Actions.hide());
            floor4.addAction((Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f)));
            floor4.addAction(Actions.hide());
            levelsSwipe = false;
        }
    }

    /**
     * method to exit about to main screen
     */
    public void exitAbout() {
        System.out.println("true swipe? " + swipe);
        if (aboutSwipe) {
            left = true;
            moveButtonsLeft();
            aboutClicked = false;
            stage.addAction(Actions.sequence(Actions.alpha((float) 0.35), Actions.fadeIn((float) 1.2)));
            testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gamemenu.png"))));
            aboutUI.getTable().addAction(Actions.hide());
            aboutSwipe = false;
        }
    }

    /**
     * exit credits to main screen
     */
    public void exitCredits() {
        if (creditsSwipe) {
            left = true;
            moveButtonsLeft();
            creditsClicked = false;
            stage.addAction(Actions.sequence(Actions.alpha((float) 0.35), Actions.fadeIn((float) 1.2)));
            testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gamemenu.png"))));
            creditsWindow.getTable().addAction(Actions.hide());
            creditsSwipe = false;
        }
    }

    /**
     * swipe right into character selection page 
    */
    private void enterCharacterSelection() {
        if (!characterSelectSwipe && back.getActions().size == 0 || CharacterSelection.EXIT == true) {
            System.out.println("bool check " + characterSelectSwipe);
            customizeSelection.addAction(Actions.show());
            customizeSelection.addAction(Actions.fadeIn((float) 0.85));
            maleSelection.addAction(Actions.show());
            maleSelection.addAction(Actions.fadeIn((float) 0.85));
            femaleSelection.addAction(Actions.show());
            femaleSelection.addAction(Actions.fadeIn((float) 0.85));
            moveButtonsRight();
            stage.addAction(Actions.sequence(Actions.alpha((float) 0.35), Actions.fadeIn((float) 0.85)));
            testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gamemenu2.jpg"))));
            //brings character selection to screen

            customizeSelection.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));

            maleSelection.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));

            femaleSelection.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));

            characterSelectSwipe = true;

//            if (clickedSettings) {
//                testTable.removeActor(table);
//            }
        }
    }
    
    /**
     * swipe right into settings ui
    */
    private void enterSettings() {
        if (!swipe && back.getActions().size == 0 || CharacterSelection.EXIT == true) {
            moveButtonsRight();
            stage.addAction(Actions.sequence(Actions.alpha((float) 0.35), Actions.fadeIn((float) 0.85)));
            testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gamemenuBlur.jpg"))));
            stage.addActor(settingsUI.getTable());
            settingsUI.getTable().addAction(Actions.show());
            swipe = true;
        }
    }
    
    /**
     * swipe right into levels screen
    */
    private void enterLevels() {
        if (!levelsSwipe && back.getActions().size == 0 || CharacterSelection.EXIT == true) {
            moveButtonsRight();
            stage.addAction(Actions.sequence(Actions.alpha((float) 0.35), Actions.fadeIn((float) 0.85)));
            testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gamemenuBlur.jpg"))));

            floor2.addAction(Actions.show());
            floor2.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));
            floor4.addAction(Actions.show());
            floor4.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));

            levelsSwipe = true;
        }
    }

    /**
     * swipe right into about screen 
    */
    private void enterAbout() {
        if (!aboutSwipe && back.getActions().size == 0 || CharacterSelection.EXIT == true) {
            moveButtonsRight();
            // stage.addAction(Actions.sequence(Actions.alpha((float) 0.35), Actions.fadeIn((float) 0.85)));
            testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gamemenuBlur.jpg"))));
            aboutUI.getTable().addAction(Actions.show());
            aboutSwipe = true;
        }
    }
    
    /**
     * swipe right into credits page 
    */
    private void enterCredits() {
        if (!creditsSwipe && back.getActions().size == 0 || CharacterSelection.EXIT == true) {
            moveButtonsRight();
            //  stage.addAction(Actions.sequence(Actions.alpha((float) 0.35), Actions.fadeIn((float) 0.85)));
            testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/blackScreen.jpg"))));
            creditsWindow.getTable().addAction(Actions.show());
            creditsSwipe = true;
        }
    }

    /**
     * create font for buttons to use
     * @param skin
     * @return 
     */
    public BitmapFont generateFont(Skin skin) {
        //font for menu text 
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/di-vari.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 40;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        //add the font to the skin
        skin.add("font", font12);
        generator.dispose();
        return font12;
    }

    /**
     * remove stage from screen
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * height of screen
     * @return 
     */
    public static int getHeight() {
        return Gdx.graphics.getHeight();
    }

    /**
     * width of screen
     * @return 
     */
    public static int getWidth() {
        return Gdx.graphics.getWidth();
    }

//    public Actor getActor(String actor) {
//        Array<Actor> stageActors = mystage.getActors();
//        int len = stageActors.size;
//        for (int i = 0; i < len; i++) {
//            Actor a = stageActors.get(i);
//            if (a.getName().equals("myactor")) {
//                //a is your Actor!
//                break;
//            }
//        }
//    }
}
