
package com.mygdx.game;

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
    private TextButton play;
    private TextButton levels;
    private TextButton settings;
    private TextButton quit;
    private TextButton back;
    private boolean swipe = false;
    private Window window;
    private TextButton confirm;
    private TextButton exit;
    private Skin skin;

    public MenuUI(Table testTable, Stage stage) {
        this.testTable = testTable;
        skin = new Skin(Gdx.files.internal("Holo-dark-hdpi.json"));
        settings = new TextButton("SETTINGS", skin);
        play = new TextButton("PLAY", skin);
        confirm = new TextButton("Confirm", skin);
        exit = new TextButton("Cancel", skin);
        window = new Window("Would you like to exit?", skin);
        levels = new TextButton("LEVELS", skin);
        quit = new TextButton("QUIT", skin);
        back = new TextButton("BACK", skin);
        addButtons(stage);
    }

    public void addButtons(Stage stage) {

        //set the settings size first so others can replicate 
        settings.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));

        //10% taken off button height
        back.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        back.setPosition(getWidth() / 4, getHeight() / 2);
        back.addAction(Actions.hide());
        play.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        play.setPosition(0, getHeight() / 2);
        levels.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        levels.setPosition(0, play.getY() - play.getHeight());
        settings.setPosition(0, levels.getY() - levels.getHeight());
        quit.setSize(settings.getPrefWidth(), settings.getPrefHeight() - (settings.getPrefHeight() / 10));
        quit.setPosition(0, settings.getY() - settings.getHeight());

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
        testTable.addActor(quit);
        testTable.addActor(back);

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
                    quit.addAction(Actions.moveBy(-(quit.getWidth()), 0, 0.8f));
                    back.addAction(Actions.show());
                    back.addAction(Actions.moveBy(-(getWidth() / 4), 0, 0.8f));
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
                    quit.addAction(Actions.moveBy(+(quit.getWidth()), 0, 0.8f));
                    //button is hiden after move by action is completed
                    back.addAction(Actions.sequence(action, Actions.hide()));
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

    public static int getHeight() {
        return Gdx.graphics.getHeight();
    }

    public static int getWidth() {
        return Gdx.graphics.getWidth();
    }
}
