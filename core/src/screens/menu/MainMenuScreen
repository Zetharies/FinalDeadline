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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private Texture placeholder;
    private boolean swipe = false, playing = false;
    private Window window;
    private Label play, levels, settings, credits, quit, back, confirm, exit;
    private TextButton male, female;
    private Music mp3Sound;
    private Image maleImage;
    private Image femaleImage;

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
        placeholder = new Texture(Gdx.files.internal("images/placeholder.png"));

        settings = new Label("SETTINGS", skin);
        play = new Label("PLAY", skin);
        confirm = new Label("Confirm", skin);
        exit = new Label("Cancel", skin);
        window = new Window("Would you like to exit?", skin);
        levels = new Label("LEVELS", skin);
        credits = new Label("CREDITS", skin);
        quit = new Label("QUIT", skin);
        back = new Label("BACK", skin);
        male = new TextButton("MALE", skin);
        female = new TextButton("FEMALE", skin);
        maleImage = new Image(placeholder);
        femaleImage = new Image(placeholder);

        setButtons();
        buttonListener(); // Call the buttonListener method
        // Add everything to screen
        addButtons();
        //stage input handling for actors
        Gdx.input.setInputProcessor(stage);

    }

    public void setButtons() {
        //character selection
        male.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        male.setPosition((Gdx.graphics.getWidth() / 3) + (Gdx.graphics.getWidth() / 4), Gdx.graphics.getHeight() / 2);
        maleImage.setSize(male.getWidth(), male.getWidth());
        maleImage.setPosition((Gdx.graphics.getWidth() / 3) + (Gdx.graphics.getWidth() / 4), (Gdx.graphics.getHeight() / 2) + 50);
        female.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        female.setPosition((Gdx.graphics.getWidth() / 3) + (Gdx.graphics.getWidth() / 4) + 300, Gdx.graphics.getHeight() / 2);
        femaleImage.setSize(female.getWidth(), female.getWidth());
        femaleImage.setPosition((Gdx.graphics.getWidth() / 3) + (Gdx.graphics.getWidth() / 4) + 300, (Gdx.graphics.getHeight() / 2) + 50);
        male.addAction(Actions.hide());
        female.addAction(Actions.hide());
        maleImage.addAction(Actions.hide());
        femaleImage.addAction(Actions.hide());

        settings.setFontScale(settings.getFontScaleX() + (settings.getFontScaleX() / 4), settings.getFontScaleY() + (settings.getFontScaleY() / 4));
        play.setFontScale(play.getFontScaleX() + (play.getFontScaleX() / 4), play.getFontScaleY() + (play.getFontScaleY() / 4));
        levels.setFontScale(levels.getFontScaleX() + (play.getFontScaleX() / 4), levels.getFontScaleY() + (play.getFontScaleY() / 4));
        credits.setFontScale(credits.getFontScaleX() + (credits.getFontScaleX() / 4), credits.getFontScaleY() + (credits.getFontScaleY() / 4));
        quit.setFontScale(quit.getFontScaleX(), quit.getFontScaleY());
        back.setFontScale(back.getFontScaleX() + (back.getFontScaleX() / 4), back.getFontScaleY() + (back.getFontScaleY() / 4));

        play.setPosition(0, getHeight() / 2);
        levels.setPosition(0, play.getY() - play.getHeight());
        settings.setPosition(0, levels.getY() - levels.getHeight());
        credits.setPosition(0, settings.getY() - settings.getHeight());
        quit.setPosition(0, credits.getY() - credits.getHeight());
        back.setPosition(getWidth() / 4, getHeight() / 2);
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
        stage.addActor(credits);
        stage.addActor(quit);
        stage.addActor(back);
        stage.addActor(male);
        stage.addActor(female);
        stage.addActor(maleImage);
        stage.addActor(femaleImage);
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
                    levels.addAction(Actions.moveBy(-(levels.getWidth() * 2), 0, 0.8f));
                    settings.addAction(Actions.moveBy(-(settings.getWidth() * 2), 0, 0.8f));
                    quit.addAction(Actions.moveBy(-(quit.getWidth() * 2), 0, 0.8f));
                    credits.addAction(Actions.moveBy(-(credits.getWidth() * 2), 0, 0.8f));
                    //brings the back button to the screen
                    back.addAction(Actions.show());
                    back.addAction(Actions.moveBy(-(getWidth() / 4), 0, 0.8f));

                    //brins character selection to screen
                    male.addAction(Actions.show());
                    male.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));
                    maleImage.addAction(Actions.show());
                    maleImage.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));
                    female.addAction(Actions.show());
                    female.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));
                    femaleImage.addAction(Actions.show());
                    femaleImage.addAction(Actions.moveBy(-(Gdx.graphics.getWidth() / 4), 0, 0.8f));

                    swipe = true;
                }

            }
        });
        //back to main menu from character selection screen
        back.addListener(new ClickListener() {
            float heightFont = back.getFontScaleX(), widthFont = back.getFontScaleY();

            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (swipe) {
                    Action action = Actions.moveBy(+(getWidth() / 4), 0, 0.8f);
                    play.addAction(Actions.moveBy(+(play.getWidth() * 2), 0, 0.8f));
                    levels.addAction(Actions.moveBy(+(levels.getWidth() * 2), 0, 0.8f));
                    settings.addAction(Actions.moveBy(+(settings.getWidth() * 2), 0, 0.8f));
                    credits.addAction(Actions.moveBy(+(credits.getWidth() * 2), 0, 0.8f));
                    quit.addAction(Actions.moveBy(+(quit.getWidth() * 2), 0, 0.8f));
                    //button is hiden after move by action is completed
                    back.addAction(Actions.sequence(action, Actions.hide()));

                    //hide character selection
                    male.addAction(Actions.sequence(Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f), Actions.hide()));
                    female.addAction(Actions.sequence(Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f), Actions.hide()));
                    maleImage.addAction(Actions.sequence(Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f), Actions.hide()));
                    femaleImage.addAction(Actions.sequence(Actions.moveBy((Gdx.graphics.getWidth() / 4), 0, 0.8f), Actions.hide()));
                    swipe = false;
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.setFontScale(back.getFontScaleX() + (back.getFontScaleX() / 10), back.getFontScaleY() + (back.getFontScaleY() / 10));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.setFontScale(widthFont, heightFont);
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

    public static int getHeight() {
        return Gdx.graphics.getHeight();
    }

    public static int getWidth() {
        return Gdx.graphics.getWidth();
    }
}
