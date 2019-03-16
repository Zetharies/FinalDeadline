package screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import screens.intro.AbstractScreen;

public class GameOverScreen extends AbstractScreen {
	
	private Image bg;
	private Stage stage;
	private Table testTable;
	
	public GameOverScreen() {
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		
		bg = new Image(new TextureRegion((new Texture("images/gameOver.png"))));
		bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(bg);
		
		/*
		testTable = new Table();
        //fit stage into screen
        testTable.setFillParent(true);
        // turn on all debug lines (table, cell, and widget)
        testTable.setDebug(true);
        
        testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/gameOver.png"))));
		stage.addActor(testTable);
		*/
		
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
		
	}

}