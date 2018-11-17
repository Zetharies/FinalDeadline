package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {
	public Stage stage;
	private Viewport viewPort;
	private Label currentMap;
	private Skin skin;
	
	public Hud(SpriteBatch batch) {
		viewPort = new FitViewport(900, 450);
		stage = new Stage(viewPort, batch);
		skin = new Skin(Gdx.files.internal("fonts/Holo-dark-hdpi.json"));
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		currentMap = new Label("Floor 4: Biology lab", skin);
		currentMap.setFontScale((float) 0.5);
		//currentMap.setSize(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/4);
		table.add(currentMap).expandX().padRight(750).padTop(0);
		stage.addActor(table);
	}

}
