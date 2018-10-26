package screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class MenuUI {

    private Table testTable;
    private TextButton play, levels, settings, credits, quit, back, male, female, confirm, exit;
    private boolean swipe = false;
    private Window window;
    private Skin skin;

    public MenuUI(Table testTable, Stage stage) {
        this.testTable = testTable;
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
        addButtons(stage);
    }


    public void addButtons(Stage stage) {

        //set the settings size first so others can replicate 
        settings.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));

        //10% taken off button height
        back.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        back.setPosition(getWidth() / 4, getHeight() / 2);
        male.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        male.setPosition((getWidth() / 3) + (getWidth() / 4), getHeight() / 2);
        female.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        female.setPosition((getWidth() / 3) + (getWidth() / 4) + 300, getHeight() / 2);
        back.addAction(Actions.hide());
        male.addAction(Actions.hide());
        female.addAction(Actions.hide());
        play.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        play.setPosition(0, getHeight() / 2);
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
        window.setPosition((getWidth() / 2) - (window.getWidth() / 2), getHeight() / 2);

        buttonListener();
        //add buttons to screen
        testTable.addActor(play);
        testTable.addActor(levels);
        testTable.addActor(settings);
        testTable.addActor(credits);
        testTable.addActor(quit);
        testTable.addActor(back);
        testTable.addActor(male);
        testTable.addActor(female);

        //stage input handling for actors
        Gdx.input.setInputProcessor(stage);

    }

    public void buttonListener() {
        //opens the pop up window to confirm exit
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                testTable.addActor(window);
            }
        });
        //cancels exit
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                testTable.removeActor(window);
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
                    back.addAction(Actions.moveBy(-(getWidth() / 4), 0, 0.8f));
                    male.addAction(Actions.show());
                    male.addAction(Actions.moveBy(-(getWidth() / 4), 0, 0.8f));
                    female.addAction(Actions.show());
                    female.addAction(Actions.moveBy(-(getWidth() / 4), 0, 0.8f));
                    swipe = true;
                }

            }
        });
        //back to main menu from character selection screen
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (swipe) {
                    Action action = Actions.moveBy((getWidth() / 4), 0, 0.8f);
                    play.addAction(Actions.moveBy(+(play.getWidth()), 0, 0.8f));
                    levels.addAction(Actions.moveBy(+(levels.getWidth()), 0, 0.8f));
                    settings.addAction(Actions.moveBy(+(settings.getWidth()), 0, 0.8f));
                    credits.addAction(Actions.moveBy(+(settings.getWidth()), 0, 0.8f));
                    quit.addAction(Actions.moveBy(+(quit.getWidth()), 0, 0.8f));
                    //button is hidden after move by action is completed
                    back.addAction(Actions.sequence(action, Actions.hide()));
                    male.addAction(Actions.sequence(Actions.moveBy((getWidth() / 4), 0, 0.8f), Actions.hide()));
                    female.addAction(Actions.sequence(Actions.moveBy((getWidth() / 4), 0, 0.8f), Actions.hide()));
                    swipe = false;
                }

            }
        });
        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                //settings will be added 
            }
        });
    }

    /**
     * 
     * @return the height of the client area in logical pixels
     */
    public static int getHeight() {
        return Gdx.graphics.getHeight();
    }

    /**
     * 
     * @return the width of the client area in logical pixels
     */
    public static int getWidth() {
        return Gdx.graphics.getWidth();
    }
}
