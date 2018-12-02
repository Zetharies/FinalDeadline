package screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;
import managers.SettingsManager;

public class SettingsUI {

    private Stage stage;
    private Window settingsWindow;
    private TextButton music, sound, resolution, controls;
    private ArrayList<String> resolutionsSizes;
    private ArrayList<String> controlOptions;
    private Table table;
    private boolean soundResp = true;
    private boolean musicResp = true;
    private int resIndex = 0, controlIndex = 0;

    public SettingsUI(Stage stage, Skin skin) {
        this.stage = stage;
        table = new Table();
        settingsWindow = new Window("", skin);
        music = new TextButton("MUSIC:ON", skin);
        sound = new TextButton("SOUND:ON", skin);
        resolution = new TextButton("" + getWidth() + "x" + getHeight(), skin);
        controls = new TextButton("CONTROL:Arrows", skin);
        resolutionsSizes = new ArrayList<String>();
        controlOptions = new ArrayList<String>();
        addResolutions();
        addControls();
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

    private void addControls() {
        controlOptions.add("WASD");
        controlOptions.add("Arrows");
    }

    public void settingsListener() {
        controls.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (controlIndex == 0) {
                    controls.setText("CONTROL:" + controlOptions.get(controlIndex));
                    controlIndex++;
                    SettingsManager.WASD = true;
                    SettingsManager.KEYS = false;
                } else if (controlIndex == 1) {
                    controls.setText("CONTROL:" + controlOptions.get(controlIndex));
                    controlIndex = 0;
                    SettingsManager.KEYS = true;
                    SettingsManager.WASD = false;
                }
            }
        });
        music.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {

                if (musicResp) {
                    music.setText("MUSIC:OFF");
                    musicResp = false;
                    SettingsManager.setMusic(false);
                } else {
                    music.setText("MUSIC:ON");
                    musicResp = true;
                    SettingsManager.setMusic(true);
                }

            }
        });
        sound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {

                if (soundResp) {
                    sound.setText("SOUND:OFF");
                    soundResp = false;
                    SettingsManager.setSound(false);
                } else {
                    sound.setText("SOUND:ON");
                    soundResp = true;
                    SettingsManager.setSound(true);
                }

            }
        });
        resolution.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (resIndex < resolutionsSizes.size()) {
                    resolution.setText("" + resolutionsSizes.get(resIndex));
                    if (resolutionsSizes.get(resIndex).equalsIgnoreCase("full screen")) {
                        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    } else {
                        String res[] = resolutionsSizes.get(resIndex).split("x");
                        Gdx.graphics.setWindowedMode(Integer.parseInt(res[0]), Integer.parseInt(res[1]));
                    }
                    resIndex++;
                } else {
                    resIndex = 0;
                    resolution.setText(getWidth() + "x" + getHeight());
                    Gdx.graphics.setWindowedMode(getWidth(), getHeight());
                }

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

        //settingsListener();
        Gdx.input.setInputProcessor(stage);

    }

    public Table getTable() {
        return table;
    }

    public static int getHeight() {
        return Gdx.graphics.getHeight();
    }

    public static int getWidth() {
        return Gdx.graphics.getWidth();
    }
}
