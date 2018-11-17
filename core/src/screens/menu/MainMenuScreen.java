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
import java.util.ArrayList;
import managers.ScreenManager;
import managers.SoundManager;
import screens.intro.AbstractScreen;

public class MainMenuScreen extends AbstractScreen {

    private Table testTable;
    private Stage stage;
    private Skin skin;
    private Texture maleCard, femaleCard, customizeCard;
    private boolean swipe = false, playing = false;
    private Window window;
    private Label play, levels, settings, about, credits, quit, confirm, exit;
    private Music mp3Sound;
    private ImageButton customizeSelection, maleSelection, femaleSelection;
    private TextButton back;
    boolean soundResp = true;
    boolean musicResp = true;
    int resIndex = 0;
    boolean clickedSettings = false;
    
    public MainMenuScreen() {
        super();
        stage = new Stage();
        testTable = new Table();
        testTable.setFillParent(true);
        testTable.setDebug(true);

        mp3Sound = Gdx.audio.newMusic(Gdx.files.internal("music/adventureSoundtrack.mp3")); // background soundtrack, feel free to change
        mp3Sound.setLooping(true); // loop the soundtrack
        mp3Sound.play(); // play the soundtrack
        mp3Sound.setVolume(0.25f);
    }

    @Override
    public void render(float delta) {
        if (!SoundManager.getSound() || !SoundManager.getMusic()) {
            mp3Sound.pause();
        } else {
            mp3Sound.play();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            //swpies back to main screen
            exitCharacterSelection();
        }
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f); // Red, Green, Blue, Alpha (Alpha is transparency)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
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

    @Override
    public void resize(int width, int height) {

        stage.clear(); // Clear screen on resize
        swipe = false;
        testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gamemenu.png"))));
        skin = new Skin(Gdx.files.internal("fonts/Holo-dark-hdpi.json"));
        skin.getFont("default-font").getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        customizeCard = new Texture(Gdx.files.internal("images/customizeSelection.png"));
        maleCard = new Texture(Gdx.files.internal("images/flynnSelection.png"));
        femaleCard = new Texture(Gdx.files.internal("images/jessicaSelection.png"));
        customizeSelection = new ImageButton(new TextureRegionDrawable(new TextureRegion(customizeCard)));
        maleSelection = new ImageButton(new TextureRegionDrawable(new TextureRegion(maleCard)));
        femaleSelection = new ImageButton(new TextureRegionDrawable(new TextureRegion(femaleCard)));

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
        buttonListener(); // Call the buttonListener method
        // Add everything to screen
        addButtons();
        //stage input handling for actors
        Gdx.input.setInputProcessor(stage);

    }

    public void setButtons() {
        //character selection
        customizeSelection.setBounds((float) (getWidth() / 2), getHeight() / 3, (float) (Gdx.graphics.getWidth() / 5), Gdx.graphics.getHeight() / 2);
        customizeSelection.addAction(Actions.hide());
        maleSelection.setBounds(customizeSelection.getX() + customizeSelection.getWidth(), customizeSelection.getY(), (float) (Gdx.graphics.getWidth() / 5), (float) (Gdx.graphics.getHeight() / 2));
        maleSelection.addAction(Actions.hide());
        femaleSelection.setBounds(maleSelection.getX() + customizeSelection.getWidth(), customizeSelection.getY(), (float) (Gdx.graphics.getWidth() / 5), (float) (Gdx.graphics.getHeight() / 2));
        femaleSelection.addAction(Actions.hide());

        settings.setFontScale((float) (settings.getFontScaleX() + (settings.getFontScaleX() + 0.8)), (float) (settings.getFontScaleY() + (settings.getFontScaleY() + 0.8)));
        play.setFontScale((float) (play.getFontScaleX() + (play.getFontScaleX() + 0.8)), (float) (play.getFontScaleY() + (play.getFontScaleY() + 0.8)));
        levels.setFontScale((float) (levels.getFontScaleX() + (levels.getFontScaleX() + 0.8)), (float) (levels.getFontScaleY() + (levels.getFontScaleY() + 0.8)));
        about.setFontScale((float) (about.getFontScaleX() + 0.6), (float) (about.getFontScaleY() + 0.6));
        credits.setFontScale((float) (credits.getFontScaleX() + 0.6), (float) (credits.getFontScaleY() + 0.6));
        quit.setFontScale((float) (quit.getFontScaleX() + 0.6), (float) (quit.getFontScaleY() + 0.6));
        back.setSize(settings.getPrefWidth() / 2, settings.getPrefHeight() / 2);

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
        confirm.setPosition(0, window.getHeight());
        exit.setPosition(0, window.getHeight());
        window.add(confirm).pad(5);
        window.setSize(window.getPrefWidth(), window.getPrefHeight());
        //sets the position of the window to the middle of the screen
        window.setPosition((Gdx.graphics.getWidth() / 2) - (window.getWidth() / 2), Gdx.graphics.getHeight() / 2);
    }

    public void addButtons() {
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
    }

    public void buttonListener() {
        //enter exit is the hover listener

        //opens the pop up window to confirm exit
        quit.addListener(new ClickListener() {
            float heightFont = quit.getFontScaleX(), widthFont = quit.getFontScaleY();

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                quit.setFontScale(quit.getFontScaleX() + (quit.getFontScaleX() / 10), quit.getFontScaleY() + (quit.getFontScaleY() / 10));
                if (!playing && SoundManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                quit.setFontScale(widthFont, heightFont);
                playing = false;
            }

            @Override
            public void clicked(InputEvent e, float x, float y) {
                stage.addActor(window);
            }
        });

        levels.addListener(new ClickListener() {
            float heightFont = levels.getFontScaleX(), widthFont = levels.getFontScaleY();

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                levels.setFontScale(levels.getFontScaleX() + (levels.getFontScaleX() / 10), levels.getFontScaleY() + (levels.getFontScaleY() / 10));
                if (!playing &&  SoundManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                levels.setFontScale(widthFont, heightFont);
                playing = false;
            }
        });

        credits.addListener(new ClickListener() {
            float heightFont = credits.getFontScaleX(), widthFont = credits.getFontScaleY();

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                credits.setFontScale(credits.getFontScaleX() + (credits.getFontScaleX() / 10), credits.getFontScaleY() + (credits.getFontScaleY() / 10));
                if (!playing &&  SoundManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                credits.setFontScale(widthFont, heightFont);
                playing = false;
            }
        });

        about.addListener(new ClickListener() {
            float heightFont = about.getFontScaleX(), widthFont = about.getFontScaleY();

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                about.setFontScale(about.getFontScaleX() + (about.getFontScaleX() / 10), about.getFontScaleY() + (about.getFontScaleY() / 10));
                if (!playing &&  SoundManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                about.setFontScale(widthFont, heightFont);
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
            float heightFont = play.getFontScaleX(), widthFont = play.getFontScaleY();

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                play.setFontScale(play.getFontScaleX() + (play.getFontScaleX() / 10), play.getFontScaleY() + (play.getFontScaleY() / 10));
                if (!playing &&  SoundManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                play.setFontScale(widthFont, heightFont);
                playing = false;
            }

            @Override
            public void clicked(InputEvent e, float x, float y) {
                enterCharacterSelection();
            }
        });
        //back to main menu from character selection screen
        back.addListener(new ClickListener() {
            float heightFont = back.getScaleX(), widthFont = back.getScaleY();

            @Override
            public void clicked(InputEvent e, float x, float y) {
                exitCharacterSelection();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.setScale(back.getScaleX() + (back.getScaleY() / 10), back.getScaleY() + (back.getScaleY() / 10));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.setScale(widthFont, heightFont);
            }
        });
        settings.addListener(new ClickListener() {
            float heightFont = settings.getFontScaleX(), widthFont = settings.getFontScaleY();

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                settings.setFontScale(settings.getFontScaleX() + (settings.getFontScaleX() / 10), settings.getFontScaleY() + (settings.getFontScaleY() / 10));
                if (!playing &&  SoundManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                settings.setFontScale(widthFont, heightFont);
                playing = false;
            }

            @Override
            public void clicked(InputEvent e, float x, float y) {
                //settings will be added 
                Settings settings = new Settings(stage);
                //settings will be added 
                if (!testTable.hasChildren()) {
                    clickedSettings = false;
                }
                if (!clickedSettings) {
                    testTable.addActor(settings.getTable());
                    clickedSettings = true;
                }
            }
        });
        customizeSelection.addListener(new ClickListener() {
            float selectionHeight = customizeSelection.getHeight(), selectionWidth = customizeSelection.getWidth();

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (!playing &&  SoundManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
                //customizeSelection.setSize((float) (Gdx.graphics.getWidth() / 1.75), (float) (Gdx.graphics.getHeight() / 1.75));
                customizeSelection.setSize((float) (customizeSelection.getWidth() + 10), (float) (customizeSelection.getHeight() + 10));
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playing = false;
                //customizeSelection.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
                customizeSelection.setSize(selectionWidth, selectionHeight);
            }
        });
        maleSelection.addListener(new ClickListener() {
            float selectionHeight = maleSelection.getHeight(), selectionWidth = maleSelection.getWidth();

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (!playing &&  SoundManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
                maleSelection.setSize((float) (maleSelection.getWidth() + 10), (float) (maleSelection.getHeight() + 10));
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playing = false;
                //maleSelection.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
                maleSelection.setSize(selectionWidth, selectionHeight);
            }

            @Override
            public void clicked(InputEvent e, float x, float y) {
                mp3Sound.stop();
                if ( SoundManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/selectFX.mp3"));
                    sound.play(0.5F);
                }
                stage.addAction(new FadeOutAction(1.25f) {
                    @Override
                    public void run() {
                        ScreenManager.setGameScreen("Flynn"); // Sets the screen to our game >:D
                    }
                });

            }
        });
        femaleSelection.addListener(new ClickListener() {
            float selectionHeight = femaleSelection.getHeight(), selectionWidth = femaleSelection.getWidth();

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (!playing &&  SoundManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
                femaleSelection.setSize((float) (femaleSelection.getWidth() + 10), (float) (femaleSelection.getHeight() + 10));
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playing = false;
                femaleSelection.setSize(selectionWidth, selectionHeight);
            }

            @Override
            public void clicked(InputEvent e, float x, float y) {
                mp3Sound.stop();
                if ( SoundManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/selectFX.mp3"));
                    sound.play(0.5F);
                }
                stage.addAction(new FadeOutAction(1.25f) {
                    @Override
                    public void run() {
                        ScreenManager.setGameScreen("Jessica"); // Sets the screen to our game >:D
                    }
                });
            }
        });

    }

    public void exitCharacterSelection() {
        if (swipe) {
            Action action = Actions.moveBy(+(getWidth() / 4), 0, 0.8f);
            play.addAction(Actions.moveBy(+(play.getWidth() * 2), 0, 0.8f));
            levels.addAction(Actions.moveBy((float) +(levels.getWidth() * 1.5), 0, 0.8f));
            settings.addAction(Actions.moveBy((float) +(settings.getWidth() * 1.5), 0, 0.8f));
            credits.addAction(Actions.moveBy(+(credits.getWidth() * 2), 0, 0.8f));
            about.addAction(Actions.moveBy(+(about.getWidth() * 2), 0, 0.8f));
            quit.addAction(Actions.moveBy(+(quit.getWidth() * 2), 0, 0.8f));
            //button is hiden after move by action is completed
            back.addAction(Actions.sequence(action, Actions.hide()));

            //hide character selection
            customizeSelection.addAction(Actions.sequence(Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f), Actions.hide()));
            maleSelection.addAction(Actions.sequence(Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f), Actions.hide()));
            femaleSelection.addAction(Actions.sequence(Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f), Actions.hide()));
            swipe = false;
        }
    }

    private void enterCharacterSelection() {
        if (!swipe && back.getActions().size == 0) {
            //hides the buttons
            //0.8f is the duration
            play.addAction(Actions.moveBy(-(play.getWidth() * 2), 0, 0.8f));
            levels.addAction(Actions.moveBy((float) -(levels.getWidth() * 1.5), 0, 0.8f));
            settings.addAction(Actions.moveBy((float) -(settings.getWidth() * 1.5), 0, 0.8f));
            quit.addAction(Actions.moveBy(-(quit.getWidth() * 2), 0, 0.8f));
            credits.addAction(Actions.moveBy(-(credits.getWidth() * 2), 0, 0.8f));
            about.addAction(Actions.moveBy(-(about.getWidth() * 2), 0, 0.8f));
            //brings the back button to the screen
            back.addAction(Actions.show());
            back.addAction(Actions.moveBy(-(getWidth() / 4), 0, 0.8f));

            //brings character selection to screen
            customizeSelection.addAction(Actions.show());
            customizeSelection.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));
            maleSelection.addAction(Actions.show());
            maleSelection.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));
            femaleSelection.addAction(Actions.show());
            femaleSelection.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));

            swipe = true;
            
            if (clickedSettings) {
                testTable.removeActor(table);
            }
        }
    }

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

    private Table table;

    class Settings {

        private Stage stage;
        private Window settingsWindow;
        private TextButton music;
        private TextButton sound;
        private TextButton resolution;
        private TextButton controls;
        private TextButton done;
        private ArrayList<String> resolutionsSizes;

        public Settings(Stage stage) {
            this.stage = stage;
            table = new Table();
            settingsWindow = new Window("", skin);
            music = new TextButton("MUSIC:ON", skin);
            sound = new TextButton("SOUND:ON", skin);
            resolution = new TextButton("" + getWidth() + "x" + getHeight(), skin);
            controls = new TextButton("CONTROLS", skin);
            done = new TextButton("DONE", skin);
            resolutionsSizes = new ArrayList<String>();
            addResolutions();
            settings(stage);
        }

        private void addResolutions() {
            resolutionsSizes.add("1280x720");
            resolutionsSizes.add("1920x1080");
            resolutionsSizes.add("Full Screen");
            if (resolutionsSizes.contains(getHeight())) {
                resolutionsSizes.remove(resolutionsSizes.indexOf(getWidth() + "x" + getHeight()));
            }
        }

        private void settingsListener() {
            controls.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {

                }
            });
            music.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {

                    
                    if (musicResp) {
                        music.setText("MUSIC:OFF");
                        musicResp = false;
                        SoundManager.setMusic(false);
                    } else {
                        music.setText("MUSIC:ON");
                        musicResp = true;
                        SoundManager.setMusic(true);
                    }

                }
            });
            sound.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {

                    if (soundResp) {
                        sound.setText("SOUND:OFF");
                        soundResp = false;
                        SoundManager.setSound(false);
                    } else {
                        sound.setText("SOUND:ON");
                        soundResp = true;
                        SoundManager.setSound(true);
                    }

                }
            });
            resolution.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    if (resIndex < resolutionsSizes.size()) {
                        resolution.setText("" + resolutionsSizes.get(resIndex));
                        if (resolutionsSizes.get(resIndex).equalsIgnoreCase("full screen")) {
                           // Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                        } else {
                            String res[] = resolutionsSizes.get(resIndex).split("x");
                           // Gdx.graphics.setWindowedMode(Integer.parseInt(res[0]), Integer.parseInt(res[1]));
                        }
                        resIndex++;
                    } else {
                        resIndex = 0;
                        resolution.setText(getWidth() + "x" + getHeight());
                       // Gdx.graphics.setWindowedMode(getWidth(), getHeight());
                    }

                }
            });
            done.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    testTable.removeActor(table);
                }
            });
        }

        private void settings(Stage stage) {
            table.add(resolution).width(resolution.getPrefWidth() + (resolution.getPrefWidth() / 2));
            table.add(controls).width(resolution.getPrefWidth() + (resolution.getPrefWidth() / 2));
            table.row();
            table.add(music).width(resolution.getPrefWidth() + (resolution.getPrefWidth() / 2));
            table.add(sound).width(resolution.getPrefWidth() + (resolution.getPrefWidth() / 2));
            table.row();
            table.setSize(table.getPrefWidth(), table.getPrefHeight());
            table.setPosition((getWidth() / 2) - (table.getWidth() / 2), getHeight() / 2);
            table.add(done).width(table.getWidth()).colspan(3);

            settingsListener();
            Gdx.input.setInputProcessor(stage);

        }

        public Table getTable() {
            return table;
        }
    }
   
    @Override
    public void dispose() {
        stage.dispose();
    }

    public static int getHeight() {
        return Gdx.graphics.getHeight();
    }

    public static int getWidth() {
        return Gdx.graphics.getWidth();
    }
}
