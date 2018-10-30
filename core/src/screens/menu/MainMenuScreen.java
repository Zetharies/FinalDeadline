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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import managers.ScreenManager;
import screens.intro.AbstractScreen;

public class MainMenuScreen extends AbstractScreen {
    private Table testTable;
    private Stage stage;
    private Skin skin;
    private Texture maleCard, femaleCard;
    private boolean swipe = false, playing = false;
    private Window window;
    private Label play, levels, settings, about, credits, quit, confirm, exit;
    private Music mp3Sound;
    private ImageButton maleSelection, femaleSelection;
    private TextButton back;

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
        maleCard = new Texture(Gdx.files.internal("images/maleSelection.png"));
        femaleCard = new Texture(Gdx.files.internal("images/femaleSelection.png"));
        maleSelection = new ImageButton(new TextureRegionDrawable(new TextureRegion(maleCard)));
        femaleSelection = new ImageButton(new TextureRegionDrawable(new TextureRegion(femaleCard)));

        settings = new Label("SETTINGS", skin);
        play = new Label("PLAY", skin);
        confirm = new Label("Confirm", skin);
        exit = new Label("Cancel", skin);
        window = new Window("Would you like to exit?", skin);
        levels = new Label("LEVELS", skin);
        about = new Label("ABOUT", skin);
        credits = new Label("CREDITS", skin);
        quit = new Label("QUIT", skin);
        back = new TextButton("[ESC] BACK", skin);

        setButtons();
        buttonListener(); // Call the buttonListener method
        // Add everything to screen
        addButtons();
        //stage input handling for actors
        Gdx.input.setInputProcessor(stage);

    }

    public void setButtons() {
        //character selection
        maleSelection.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        maleSelection.setPosition((Gdx.graphics.getWidth() / 3), Gdx.graphics.getHeight() / 4);
        maleSelection.addAction(Actions.hide());
        femaleSelection.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        femaleSelection.setPosition((float) (maleSelection.getWidth() * 1.25), Gdx.graphics.getHeight() / 4);
        femaleSelection.addAction(Actions.hide());

        settings.setFontScale((float)(settings.getFontScaleX() + (settings.getFontScaleX() + 0.8)), (float) (settings.getFontScaleY() + (settings.getFontScaleY() + 0.8)));
        play.setFontScale((float) (play.getFontScaleX() + (play.getFontScaleX() + 0.8)), (float) (play.getFontScaleY() + (play.getFontScaleY() + 0.8)));
        levels.setFontScale((float) (levels.getFontScaleX() + (levels.getFontScaleX() + 0.8)), (float) (levels.getFontScaleY() + (levels.getFontScaleY() + 0.8)));
        about.setFontScale((float) (about.getFontScaleX() + 0.6), (float) (about.getFontScaleY() + 0.6));
        credits.setFontScale((float) (credits.getFontScaleX() + 0.6), (float) (credits.getFontScaleY() + 0.6));
        quit.setFontScale((float) (quit.getFontScaleX() + 0.6), (float) (quit.getFontScaleY() + 0.6));
        back.setSize(settings.getPrefWidth() / 2 , settings.getPrefHeight() / 2);

        play.setPosition(0, getHeight() / 2);
        levels.setPosition(0, (float) (play.getY() - play.getHeight() * 1.5));
        settings.setPosition(0, (float) (levels.getY() - levels.getHeight() * 1.5));
        credits.setPosition(0, (float) (settings.getY() - settings.getHeight() * 1.15));
        about.setPosition(0, (float) (credits.getY() - credits.getHeight() * 1.15));
        quit.setPosition(0, (float) (about.getY() - about.getHeight() * 1.15));
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
                if (!playing) {
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
                if (!playing) {
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
                if (!playing) {
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
                if (!playing) {
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
                if (!playing) {
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
                if (!swipe && back.getActions().size == 0) {
                    //hides the buttons
                    //0.8f is the duration
                    play.addAction(Actions.moveBy(-(play.getWidth() * 2), 0, 0.8f));
                    levels.addAction(Actions.moveBy((float) -(levels.getWidth() * 2.5), 0, 0.8f));
                    settings.addAction(Actions.moveBy(-(settings.getWidth() * 3), 0, 0.8f));
                    quit.addAction(Actions.moveBy(-(quit.getWidth() * 2), 0, 0.8f));
                    credits.addAction(Actions.moveBy(-(credits.getWidth() * 2), 0, 0.8f));
                    about.addAction(Actions.moveBy(-(about.getWidth() * 2), 0, 0.8f));
                    //brings the back button to the screen
                    back.addAction(Actions.show());
                    back.addAction(Actions.moveBy(-(getWidth() / 4), 0, 0.8f));

                    //brings character selection to screen
                    maleSelection.addAction(Actions.show());
                    maleSelection.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));
                    femaleSelection.addAction(Actions.show());
                    femaleSelection.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));

                    swipe = true;
                }

            }
        });
        //back to main menu from character selection screen
        back.addListener(new ClickListener() {
            float heightFont = back.getScaleX(), widthFont = back.getScaleY();

            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (swipe) {
                    Action action = Actions.moveBy(+(getWidth() / 4), 0, 0.8f);
                    play.addAction(Actions.moveBy(+(play.getWidth() * 2), 0, 0.8f));
                    levels.addAction(Actions.moveBy((float) +(levels.getWidth() * 2.5), 0, 0.8f));
                    settings.addAction(Actions.moveBy(+(settings.getWidth() * 3), 0, 0.8f));
                    credits.addAction(Actions.moveBy(+(credits.getWidth() * 2), 0, 0.8f));
                    about.addAction(Actions.moveBy(+(about.getWidth() * 2), 0, 0.8f));
                    quit.addAction(Actions.moveBy(+(quit.getWidth() * 2), 0, 0.8f));
                    //button is hiden after move by action is completed
                    back.addAction(Actions.sequence(action, Actions.hide()));

                    //hide character selection
                    maleSelection.addAction(Actions.sequence(Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f), Actions.hide()));
                    femaleSelection.addAction(Actions.sequence(Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f), Actions.hide()));
                    swipe = false;
                }
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
                if (!playing) {
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
            }
        });
        maleSelection.addListener(new ClickListener() {
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
        femaleSelection.addListener(new ClickListener() {
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

    public static int getHeight() {
        return Gdx.graphics.getHeight();
    }

    public static int getWidth() {
        return Gdx.graphics.getWidth();
    }
}
