package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import models.HealthBar;

public class Hud {
	public Stage stage;
	private Viewport viewPort;
	private Label currentMap;
	private Skin skin;
	private HealthBar health;
	
	public Hud(SpriteBatch batch) {
		viewPort = new FitViewport(1200, 600);
		stage = new Stage(viewPort, batch);
		skin = new Skin(Gdx.files.internal("fonts/Holo-dark-hdpi.json"));
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		currentMap = new Label("Floor 1: Biology lab", skin);
		currentMap.setFontScale((float) 0.5);
		
		health = new HealthBar(145,8); // Create health bar
		health.getHealth(); // gets the value 
		//currentMap.setSize(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/4);
		table.add(currentMap).expandX().padRight(1040).padTop(0);
		health.setPosition((float)(Gdx.graphics.getWidth()/ 100), Gdx.graphics.getHeight()/2);
		stage.addActor(table);
		stage.addActor(health);
	}
	
	public void setLabel(String label) {
		currentMap.setText(label);
	}
	
	public void resetHealth() {
		health.setValue(1.0f);
	}
	
	public void reduceHealth() {
		health.setValue(health.getValue() - 0.01f);
	}
	
	public float getHealth() {
		return health.getValue();
	}

}
