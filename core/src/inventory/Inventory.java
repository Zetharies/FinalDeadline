package inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import javafx.stage.Window;

public class Inventory extends Window {
	
	private Texture texture;

	public Inventory() {
		texture = new Texture(Gdx.files.internal("images/actionBar.png"));
		
	}

	public Texture getTexture() {
		return texture;
	}
	public void draw(Batch batch) {
		// First draw the left border.
		batch.draw(texture, 100, 100);
		
	}

	
	
	

}
