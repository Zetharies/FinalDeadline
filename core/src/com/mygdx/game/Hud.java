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
<<<<<<< HEAD
		currentMap = new Label("Floor 4: Biology lab", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		table.add(currentMap).expandX().padRight(1050).padTop(10);
=======
		currentMap = new Label("Floor 4: Biology", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		table.add(currentMap).expandX().padRight(1000).padTop(10);
>>>>>>> 7e26cdfaafe98b37beea857f6856f532becffc57
		stage.addActor(table);
	}

}
