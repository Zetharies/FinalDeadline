package screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import managers.ScreenManager;
import screens.intro.AbstractScreen;

public class CharacterSelection extends AbstractScreen {

    private Stage stage;
    private int currentCharacter;
    private Skin skin;
    private TextField usernameTextField;
    //private Table testTable;
    
    //make sure can use the play button when exiting custom
    public static boolean EXIT = false;
    
    public void prepareUI() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = generateFont(skin);

        final TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = generateFont(skin);
        buttonStyle.fontColor = Color.CYAN;

        if (currentCharacter >= 4) {
            currentCharacter = 1;
        }
        if (currentCharacter <= 0) {
            currentCharacter = 3;
        }

        usernameTextField = new TextField("", skin);
//        usernameTextField.setPosition((stage.getWidth() - usernameTextField.getWidth()) / 2, (float) ((stage.getHeight()) / 6));
//        usernameTextField.setSize(stage.getWidth() - usernameTextField.getWidth() / 6, stage.getHeight() / 6);
        usernameTextField.setBounds((stage.getWidth() - usernameTextField.getWidth()) / 2, (float) ((stage.getHeight()) / 6),
                stage.getWidth() - usernameTextField.getWidth() / 6, stage.getHeight() / 6);
        usernameTextField.setText("Name");
        usernameTextField.pack();
        stage.addActor(usernameTextField);

        Image customSprite = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("images/custom" + currentCharacter + ".png"))));
        customSprite.setPosition((stage.getWidth() - customSprite.getWidth()) / 2, (float) ((stage.getHeight() - customSprite.getHeight()) / 1.05));
        stage.addActor(customSprite);

        TextButton next = new TextButton(">>", buttonStyle);
        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                System.out.println("clicked");
                currentCharacter++;
                stage.clear();
                prepareUI();
            }
        });
        next.setSize(stage.getWidth() * 5 / 6 - next.getWidth() / 2, stage.getHeight() / 2);
        next.setPosition(stage.getWidth() * 5 / 6 - next.getWidth() / 2, stage.getHeight() / 2);

//        next.setBounds(stage.getWidth() - (stage.getWidth() / 4), stage.getHeight() / 2,
//                 stage.getWidth() * 5 / 6 - next.getWidth() / 2, stage.getHeight() / 2);
        stage.addActor(next);

        TextButton previous = new TextButton("<<", buttonStyle);
        previous.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                System.out.println("clicked");
                currentCharacter--;
                stage.clear();
                prepareUI();
            }
        });
        previous.setSize(stage.getWidth() * 5 / 6 - previous.getWidth() / 2, stage.getHeight() / 2);
        previous.setPosition(stage.getWidth() / 6 - previous.getWidth() / 2, stage.getHeight() / 2);
//        previous.setBounds(0 - 50, stage.getHeight() / 2,
//                stage.getWidth() * 5 / 6 - previous.getWidth() / 2, stage.getHeight() / 2);
        stage.addActor(previous);

        TextButton back = new TextButton("[ESC] BACK", skin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                EXIT = true;
                ScreenManager.setMainMenuScreen();
                MainMenuScreen.characterSelectSwipe = false;
            }
        });

//        back.setSize(previous.getWidth() / 2, previous.getHeight() / 2);
//        back.setPosition(stage.getWidth() * 3 / 6 - next.getWidth() / 2, (usernameTextField.getHeight()) / 30);
//        back.setSize((float)(back.getPrefWidth()), (float)(back.getPrefHeight() / 2.5));
//        back.setPosition((stage.getWidth() / 2) - (back.getPrefWidth() / 2) , (usernameTextField.getHeight()) / 30);
        back.setBounds((stage.getWidth() / 2) - (float)(back.getPrefWidth() / 1.35) , (usernameTextField.getHeight()) / 30,
                (float)(back.getPrefWidth() - (back.getPrefWidth() / 4.5)), (float)(back.getPrefHeight() / 2.5));
        stage.addActor(back);

        TextButton apply = new TextButton("APPLY", skin);
        apply.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                ScreenManager.setGameScreen("Custom" + currentCharacter);
            }
        });
//        apply.setSize(previous.getWidth() / 2, previous.getHeight() / 2);
//        apply.setPosition(stage.getWidth() * 5 / 6 - next.getWidth() / 2, (usernameTextField.getHeight()) / 30);
        apply.setBounds(back.getX() + (apply.getPrefWidth()), (usernameTextField.getHeight()) / 30,
                 (float)(back.getPrefWidth() - (back.getPrefWidth() / 4.5)), (float) (back.getPrefHeight() / 2.5));

        stage.addActor(apply);

    }

    public CharacterSelection() {
        super();
        skin = new Skin(Gdx.files.internal("fonts/Holo-dark-hdpi.json"));
        //skin.getFont("default-font").getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        skin.getFont("default-font").getData().setScale(0.33f, 0.33f);
        currentCharacter = 1;
        FitViewport viewport = new FitViewport(160, 120);
        stage = new Stage(viewport);
        prepareUI();
        Gdx.input.setInputProcessor(stage);

    }

    public String getName() {
        return usernameTextField.getText();
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ScreenManager.setMainMenuScreen();
        }
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
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

}
