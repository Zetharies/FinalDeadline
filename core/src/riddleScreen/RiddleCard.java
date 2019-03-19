package riddleScreen;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.GameSettings;

	
public class RiddleCard {
	private String itemName;
	private String atlasImage;
	
	private int id;

	private Texture texture;
	private int invX;	
	private int x;
	private int y;
	private TiledMapTileLayer layer;
	private SpriteBatch batch;
	
	public RiddleCard(String impName,int startX, int startY,String texturePath  ) {
		itemName = impName;
		
		//this.layer = layer;

		 this.x = startX;
		 this.y = startY;

		texture = new Texture(Gdx.files.internal(texturePath));
	}
	
	
	public RiddleCard(String string, int x, int y) {
		itemName = string;
		
		//this.layer = layer;

		 this.x = x;
		 this.y = y;
		// TODO Auto-generated constructor stub
	}


	public void render(SpriteBatch batch) {
		
		batch.draw(texture, // Print Texture
				(x * GameSettings.SCALED_TILE_SIZE), // Sets X Position
				(y * GameSettings.SCALED_TILE_SIZE), // Sets Y Position
				GameSettings.SCALED_TILE_SIZE * 0.5f, // Sets Width of Sprite
				GameSettings.SCALED_TILE_SIZE * 0.5f); // Sets Height of Sprite
		
	}
	
//	public void remove() {
//		
//		batch.dispose();
//	}
	
	


	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}


	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}
	
	public int newgetX() {
		// TODO Auto-generated method stub
		int xT = 35;
		this.x = xT;
		return x;
	}


	public int newgetY() {
		// TODO Auto-generated method stub
		int yT = 60;
		this.y=yT;
		return y;
	
}
}
