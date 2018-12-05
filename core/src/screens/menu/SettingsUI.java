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
    private TextButton music, sound, resolution, controls, apply;
    private ArrayList<String> resolutionsSizes;
    private ArrayList<String> controlOptions;
    private Table table;
    private boolean soundResp = true;
    private boolean musicResp = true;
    private boolean resChange = false, defaultRes = false;
    private int controlIndex = 0;
    public boolean APPLY_CLICKED = false;
    private int oWidth = Gdx.graphics.getWidth();
    private int oHeight = Gdx.graphics.getHeight();
    public SettingsUI(Stage stage, Skin skin) {
        table = new Table();
        apply = new TextButton("APPLY", skin);
        music = new TextButton("MUSIC:ON", skin);
        sound = new TextButton("SOUND:ON", skin);
        resolution = new TextButton("" + getWidth() + "x" + getHeight(), skin);
        controls = new TextButton("CONTROL:ARROWS", skin);
        resolutionsSizes = new ArrayList<String>();
        controlOptions = new ArrayList<String>();
        addResolutions();
        addControls();
        settings(stage);
    }

    private void addResolutions() {
        resolutionsSizes.add(oWidth + "x" + oHeight);
        resolutionsSizes.add("1280x720");
        resolutionsSizes.add("1920x1080");
        resolutionsSizes.add("Full Screen");
//        if (resolutionsSizes.contains(getHeight())) {
//            resolutionsSizes.remove(resolutionsSizes.indexOf(getWidth() + "x" + getHeight()));
//        }
          if(resolutionsSizes.contains(resolutionsSizes.get(currentRes))){
              resolutionsSizes.remove(resolutionsSizes.indexOf(resolutionsSizes.get(currentRes)));
          }
    }

    private void addControls() {
        controlOptions.add("WASD");
        controlOptions.add("ARROWS");
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
                System.out.println("music clicked");
                if (musicResp) {
                    music.setText("MUSIC:OFF");
                    musicResp = false;
                    //  SettingsManager.setMusic(false);
                } else if (!musicResp) {
                    music.setText("MUSIC:ON");
                    musicResp = true;
                    //  SettingsManager.setMusic(true);
                }

            }
        });
        sound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {

                if (soundResp) {
                    sound.setText("SOUND:OFF");
                    soundResp = false;
                    // SettingsManager.setSound(false);
                } else {
                    sound.setText("SOUND:ON");
                    soundResp = true;
                    // SettingsManager.setSound(true);
                }

            }
        });
        resolution.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
//                if (resIndex < resolutionsSizes.size()) {
//                    resolution.setText("" + resolutionsSizes.get(resIndex));
////                    if (resolutionsSizes.get(resIndex).equalsIgnoreCase("full screen")) {
////                        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
////                    } else {
////                        String res[] = resolutionsSizes.get(resIndex).split("x");
////                        Gdx.graphics.setWindowedMode(Integer.parseInt(res[0]), Integer.parseInt(res[1]));
////                    }
//                    currentRes = resIndex;
//                    resIndex++;
//                    resChange = true;
//                } else {
//                    resChange = false;
//                    resIndex = 0;
//                    currentRes = resIndex;
//                    // resolution.setText(getWidth() + "x" + getHeight());
//                    resolution.setText(resolutionsSizes.get(resIndex));
//                }
                resolution.setText("" + resolutionsSizes.get(MainMenuScreen.RES_INDEX));
                currentRes = MainMenuScreen.RES_INDEX;
                MainMenuScreen.RES_INDEX++;
                if(MainMenuScreen.RES_INDEX == resolutionsSizes.size()){
                    MainMenuScreen.RES_INDEX = 0;
                }
                resChange = true;
            }
        });
        apply.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (!musicResp) {
                    SettingsManager.setMusic(false);
                } else if (musicResp) {
                    SettingsManager.setMusic(true);
                }
                if (!soundResp) {
                    SettingsManager.setSound(false);
                } else {
                    SettingsManager.setSound(true);
                }

                if (resChange) {
                    if (resolutionsSizes.get(currentRes).equalsIgnoreCase("full screen")) {
                        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    } else {
                        String res[] = resolutionsSizes.get(currentRes).split("x");
                        Gdx.graphics.setWindowedMode(Integer.parseInt(res[0]), Integer.parseInt(res[1]));
                    }
                    resChange = false;
                } 
//                else if (!resChange) {
//                    String res[] = resolutionsSizes.get(currentRes).split("x");
//                    Gdx.graphics.setWindowedMode(Integer.parseInt(res[0]), Integer.parseInt(res[1]));
//                    // Gdx.graphics.setWindowedMode(getWidth(), getHeight());
//                }

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
    int currentRes = 0;

    private void settings(Stage stage) {
        table.add(resolution).width(controls.getPrefWidth() + (controls.getPrefWidth() / 2));
        table.add(controls).width(controls.getPrefWidth() + (controls.getPrefWidth() / 2));
        table.row();
        table.add(music).width(controls.getPrefWidth() + (controls.getPrefWidth() / 2));
        table.add(sound).width(controls.getPrefWidth() + (controls.getPrefWidth() / 2));
        table.row();
        table.add(apply).colspan(3).expandX().fillX();
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
