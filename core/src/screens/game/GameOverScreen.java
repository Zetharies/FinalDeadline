package screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import managers.ScreenManager;
import managers.SettingsManager;
import screens.intro.AbstractScreen;

public class GameOverScreen extends AbstractScreen {
	
	private Image bg; // background for image
	private Stage stage;
	private Skin skin;
	private Label menu, exit;
	private boolean playing = false;
	
	/**
	 * Constructor for GameOverScreen
	 * Creates GameOver background along with options such as returning to main menu or exiting game
	 */
	public GameOverScreen() {
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		skin = new Skin(Gdx.files.internal("fonts/Holo-dark-hdpi.json"));
		menu = new Label("RETURN TO MENU", skin);
		exit = new Label("EXIT GAME", skin);
		bg = new Image(new TextureRegion((new Texture("images/gameOver.png"))));
		bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		/*menu.setFontScale((float) (menu.getFontScaleX() + (menu.getFontScaleX() + 0.8)), (float) (menu.getFontScaleY() + (menu.getFontScaleY() + 0.8)));
		menu.setBounds(10, (float) (Gdx.graphics.getHeight() / 8), menu.getPrefWidth(), menu.getPrefHeight());
		
		exit.setFontScale((float) (exit.getFontScaleX() + (exit.getFontScaleX() + 0.8)), (float) (exit.getFontScaleY() + (exit.getFontScaleY() + 0.8)));
		exit.setBounds(10, (float) (Gdx.graphics.getHeight() / 8), menu.getPrefWidth(), menu.getPrefHeight());
		*/
		menu.setBounds((float) (Gdx.graphics.getWidth()  / 6), Gdx.graphics.getHeight() / 2, (float) (Gdx.graphics.getWidth() / 2), Gdx.graphics.getHeight() / 2);
		menu.setSize((float) (Gdx.graphics.getWidth() / 1.25), (float) (Gdx.graphics.getHeight() / 1.25));
        
		exit.setBounds((float) (menu.getX() * 4.5), menu.getY(), (float) (Gdx.graphics.getWidth() / 5), (float) (Gdx.graphics.getHeight() / 2));
		exit.setSize((float) (Gdx.graphics.getWidth()  / 1.25), (float) (Gdx.graphics.getHeight() / 1.25));
		
		stage.addActor(bg);
		stage.addActor(menu);
		stage.addActor(exit);

	}
	
	/**
	 * Set buttons for the game over screen
	 */
	public void buttonListener() {
        //go to the main menu screen
		
		
		menu.addListener(new ClickListener(){
			@Override
            public void clicked(InputEvent e, float x, float y) {
                ScreenManager.setMainMenuScreen();
            }
		});
		
        menu.addListener(new ClickListener() {
            float heightFont = menu.getFontScaleX(), widthFont = menu.getFontScaleY();

            //check audio to play and play and increase font size
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                menu.setFontScale(menu.getFontScaleX() + (menu.getFontScaleX() / 10), menu.getFontScaleY() + (menu.getFontScaleY() / 10));
                if (!playing && SettingsManager.getSound()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("fx/menuHover.mp3"));
                    sound.play(0.1F);
                    playing = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                menu.setFontScale(widthFont, heightFont);//reset font size
                playing = false;//restricts audio playing
            }

            @Override
            public void clicked(InputEvent e, float x, float y) {
                ScreenManager.setMainMenuScreen();
            }
        });
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
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
		// TODO Auto-generated method stub
		stage.dispose();
		skin.dispose();
	}

}
