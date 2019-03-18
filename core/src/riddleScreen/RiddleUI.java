package riddleScreen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameSettings;
import com.mygdx.game.Hud;

import controllers.PlayerController;
import controllers.RobotController;
import controllers.ScreenplayController;
import models.Book;
import models.Herd;
import models.Map;
import models.Player;
import models.Robot;
import models.Zombie;
import models.screenplay.Screenplay;
import models.screenplay.ScreenplayHandler;

public class RiddleUI {

	private Label ok;

	private Window window;
	private Container<Actor> container;

	private Skin skin;

	private Stage stage;

	private Table table;

	public RiddleUI(String string) {

		skin = new Skin(Gdx.files.internal("fonts/Holo-dark-hdpi.json"));
		skin.getFont("default-font").getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);
		Label.LabelStyle style = new Label.LabelStyle();
		style.font = generateFont(skin);

		
		window = new Window(string, skin);

		window.getTitleLabel().setStyle(style);

		window.setBounds(220, 60, (Gdx.graphics.getWidth() / 2) - (window.getWidth() / 4),
				Gdx.graphics.getHeight() / 4);

		window.add(ok).pad(5);

	}

	public BitmapFont generateFont(Skin skin) {
		// font for menu text
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/di-vari.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 25;

		BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
		// add the font to the skin
		skin.add("font", font12);

		generator.dispose();
		return font12;
	}

	public Window getWindow() {
		return window;
	}

	public void windowAdd(Label label) {
		this.ok = label;

		window.add(label);
	}

}