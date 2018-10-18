package Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen implements Screen {
    private SpriteBatch batch;
    private Texture texture;
 
    public MainMenuScreen() {
        super();
        batch = new SpriteBatch();
        texture = new Texture("GameMenu.jpg"); // BACKGROUND IMAGE FOR MAIN MENU
    }
 
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
 
        batch.begin();
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        batch.end();
    }
 
    @Override
    public void hide() { }
 
    @Override
    public void pause() { }
 
    @Override
    public void resume() { }
    
    @Override
    public void show() { }
    
    @Override
    public void resize(int width, int height) { }
 
    @Override
    public void dispose() {
        texture.dispose();
        batch.dispose();
    }
}