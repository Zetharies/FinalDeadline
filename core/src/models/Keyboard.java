package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class Keyboard {
	
	Texture keyboardTexture;
	String direction;
	float x, y;
	
	public Keyboard(String direction, float x, float y) {
		keyboardTexture = new Texture(Gdx.files.internal("images/items/itemKeyboard.png"));//loads texture used for keyboard
		this.direction = direction;
		this.x = x;
		this.y = y;
	}
	
	public void render(SpriteBatch batch) { //renders keyboard
		batch.draw(keyboardTexture, 
				  (x * GameSettings.SCALED_TILE_SIZE) - 10, 
				  (y * GameSettings.SCALED_TILE_SIZE) + 10, 
				  30, 
				  30);
	}
	
	public void update(float delta) { //directions keyboard can be thrown
		if(direction.equals("up")) {
			y += delta * 6;
		} else if(direction.equals("down")) {
			y -= delta * 6;
		} else if(direction.equals("left")) {
			x -= delta * 6;
		} else if(direction.equals("right")) {
			x += delta * 6;
		}
	}
	
	public float getX() { //retrieves x co-ordinate
		return x;
	}
	
	public float getY() { //retrieves y co-ordiante
		return y;
	}
}
