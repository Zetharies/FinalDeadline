package screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;
import managers.SettingsManager;

public class SettingsUI {

    @SuppressWarnings("unused")
    private TextButton music, sound, resolution, controls, skipDialogue, apply;//settings buttons
    private ArrayList<String> resolutionsSizes;//res sizes to support
    private ArrayList<String> controlOptions;//control options - wasd / keys
    private Table table; // table to add elements to 
    private boolean checkDialogue = false;//to show/skip dialogue
    private boolean soundResp = true;//play sound
    private boolean musicResp = true;//play music
    private boolean resChange = false, defaultRes = false;//check for res change
    private int controlIndex = 0;//starting controls = keys
    private int currentRes = 0; // starting res option
    public boolean APPLY_CLICKED = false;
    private int oWidth = Gdx.graphics.getWidth();
    private int oHeight = Gdx.graphics.getHeight();

    /**
     * settings ui construct in main screen
     *
     * @param stage
     * @param skin
     */
    public SettingsUI(Stage stage, Skin skin) {
        table = new Table();
        apply = new TextButton("APPLY", skin);
        music = new TextButton("MUSIC: ON", skin);
        sound = new TextButton("SOUND: ON", skin);
        skipDialogue = new TextButton("SKIP DIALOGUE: FALSE", skin);
        resolution = new TextButton("" + getWidth() + "x" + getHeight(), skin);
        controls = new TextButton("CONTROL:ARROWS", skin);
        resolutionsSizes = new ArrayList<String>();
        controlOptions = new ArrayList<String>();
        addResolutions();
        addControls();
        settings(stage);
    }

    /**
     * add resolutions to support - removes res if = to current and show once
     */
    private void addResolutions() {
        resolutionsSizes.add(oWidth + "x" + oHeight);
        resolutionsSizes.add("1280x720");
        resolutionsSizes.add("1920x1080");
        resolutionsSizes.add("Full Screen");
        //if res options = current res remove 
        if (resolutionsSizes.contains(resolutionsSizes.get(currentRes))) {
            resolutionsSizes.remove(resolutionsSizes.indexOf(resolutionsSizes.get(currentRes)));
        }
    }

    /**
     * add moving player options wasd / arrow keys
     */
    private void addControls() {
        controlOptions.add("WASD");
        controlOptions.add("ARROWS");
    }

    /**
     * listener for buttons
     */
    public void settingsListener() {
        //listener for controls button
        controls.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                //starting index = 0 default controls = keys
                if (controlIndex == 0) {
                    controls.setText("CONTROL:" + controlOptions.get(controlIndex));
                    controlIndex++;
                    //update settings
                    SettingsManager.WASD = true;
                    SettingsManager.KEYS = false;
                } else if (controlIndex == 1) {
                    controls.setText("CONTROL:" + controlOptions.get(controlIndex));
                    controlIndex = 0;
                    //update settings
                    SettingsManager.KEYS = true;
                    SettingsManager.WASD = false;
                }
            }
        });
        //music option listener
        music.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {//event when button clicked
                if (musicResp) {//turn music off
                    music.setText("MUSIC: OFF");
                    musicResp = false;
                } else if (!musicResp) {//turn on
                    music.setText("MUSIC: ON");
                    musicResp = true;
                }

            }
        });
        //sound button listner
        sound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (soundResp) {//code runs once button clicked
                    sound.setText("SOUND: OFF");
                    soundResp = false;
                } else {
                    sound.setText("SOUND: ON");
                    soundResp = true;
                }

            }
        });
        //resolution button listener
        resolution.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                resolution.setText("" + resolutionsSizes.get(MainMenuScreen.RES_INDEX));//set res to starting window res
                currentRes = MainMenuScreen.RES_INDEX;
                MainMenuScreen.RES_INDEX++;
                if (MainMenuScreen.RES_INDEX == resolutionsSizes.size()) {
                    MainMenuScreen.RES_INDEX = 0;//back to starting res options
                }
                resChange = true;
            }
        });
        //listener for skip button to skip in game dialogue
        skipDialogue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (!checkDialogue) {//dialogue to play
                    skipDialogue.setText("SKIP DIALOGUE: TRUE");
                    checkDialogue = true;
                } else {//dialogy wont play in game
                    skipDialogue.setText("SKIP DIALOGUE: FALSE");
                    checkDialogue = false;
                }
            }
        });
        //apply options chosen
        apply.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (!musicResp) {
                    SettingsManager.setMusic(false);//set music off
                } else if (musicResp) {
                    SettingsManager.setMusic(true);//set music on
                }
                if (!soundResp) {
                    SettingsManager.setSound(false);//set sound off
                } else {
                    SettingsManager.setSound(true);//set sound on
                }
                if (!checkDialogue) {
                    SettingsManager.setDialogueSkipper(false);//set dialogue off 
                } else if (checkDialogue) {
                    SettingsManager.setDialogueSkipper(true);//set dialogue on
                }

                if (resChange) {
                    if (resolutionsSizes.get(currentRes).equalsIgnoreCase("full screen")) {
                        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());//full screen
                    } else {
                        String res[] = resolutionsSizes.get(currentRes).split("x");
                        Gdx.graphics.setWindowedMode(Integer.parseInt(res[0]), Integer.parseInt(res[1]));//change screen res
                    }
                    resChange = false;
                }
                //set control options
                if (controlIndex == 1) {
                    SettingsManager.WASD = true;
                    SettingsManager.KEYS = false;
                } else if (controlIndex == 0) {
                    SettingsManager.KEYS = true;
                    SettingsManager.WASD = false;
                }

            }
        });
    }

    private void settings(Stage stage) {
        table.add(resolution).width(controls.getPrefWidth() + (controls.getPrefWidth() / 2));
        table.add(controls).width(controls.getPrefWidth() + (controls.getPrefWidth() / 2));
        table.row();
        table.add(music).width(controls.getPrefWidth() + (controls.getPrefWidth() / 2));
        table.add(sound).width(controls.getPrefWidth() + (controls.getPrefWidth() / 2));
        table.row();
        table.add(skipDialogue).colspan(3).expandX().fillX();
        table.row();
        table.add(apply).colspan(3).expandX().fillX();
        table.setSize(table.getPrefWidth(), table.getPrefHeight());
        table.setPosition((getWidth() / 2) - (table.getWidth() / 2), getHeight() / 2);

        //settingsListener();
        Gdx.input.setInputProcessor(stage);

    }

    /**
     * use table to display settings in main screen
     * @return 
     */
    public Table getTable() {
        return table;
    }

    /**
     * window height
     * @return 
     */
    public static int getHeight() {
        return Gdx.graphics.getHeight();
    }

    /**
     * window width
     * @return 
     */
    public static int getWidth() {
        return Gdx.graphics.getWidth();
    }
}
