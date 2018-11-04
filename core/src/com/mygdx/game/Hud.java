package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {
	public Stage stage;
	private Viewport viewPort;
	private Label currentMap;
	
	public Hud(SpriteBatch batch) {
		viewPort = new FitViewport(1200, 600);
		stage = new Stage(viewPort, batch);
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		currentMap = new Label("Floor 1: Placeholder map", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		table.add(currentMap).expandX().padRight(1000).padTop(10);
		stage.addActor(table);
	}

}
