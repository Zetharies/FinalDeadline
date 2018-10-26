package screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MainMenuScreen implements Screen {
    private Table testTable;
    private Stage stage;
    
    public MainMenuScreen() {
        super();
        //batch = new SpriteBatch();
        //texture = new Texture(Gdx.files.internal("gamemenu.png")); // Background image of main menu
        stage = new Stage();
        testTable = new Table();
        testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gamemenu.png"))));
        testTable.setFillParent(true);
        testTable.setDebug(true);
        stage.addActor(testTable);

        new MenuUI(testTable, stage);
    }
 
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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
        stage.dispose();
    }
}