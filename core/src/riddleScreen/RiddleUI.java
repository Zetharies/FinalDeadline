package riddleScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class RiddleUI {

	private Label ok;

	private Window window;
	private Skin skin;

	public RiddleUI(String string) { //sets design format for riddle

		skin = new Skin(Gdx.files.internal("fonts/Holo-dark-hdpi.json"));
		skin.getFont("default-font").getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);
		Label.LabelStyle style = new Label.LabelStyle();
		style.font = generateFont(skin);

		window = new Window(
				string,
				skin);
	
		window.getTitleLabel().setStyle(style);
		window.setBounds(220,60, (Gdx.graphics.getWidth() / 2) - (window.getWidth() / 4), Gdx.graphics.getHeight() / 4);			
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