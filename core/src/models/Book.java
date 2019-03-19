package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class Book {
	
	Texture bookTexture;
	String direction;
	float x, y;
	
	public Book(String direction, float x, float y) {
		bookTexture = new Texture(Gdx.files.internal("images/book1.png"));//loads texture for book
		this.direction = direction;
		this.x = x;
		this.y = y;
	}
	
	public void render(SpriteBatch batch) { //renders (draws) the books
		batch.draw(bookTexture, (x * GameSettings.SCALED_TILE_SIZE) - 10, (y * GameSettings.SCALED_TILE_SIZE) + 10, 18, 18);
	}
	
	public void update(float delta) { //directions books can go in
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
	
	public float getX() { //retrieves x co-ordiantes
		return x;
	}
	
	public float getY() { //retrieves y co-ordinates
		return y;
	}
}
