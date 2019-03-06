package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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

	private TextureAtlas invAtlas;
	private TextureAtlas itemAtlas;
	private Set<String> imagesToDraw;
	private Container<Image> invItems;

	public Hud(SpriteBatch batch) {

		viewPort = new FitViewport(1200, 600);
		stage = new Stage(viewPort, batch);
		skin = new Skin(Gdx.files.internal("fonts/Holo-dark-hdpi.json"));

		Table table = new Table();
		table.top();
		table.setFillParent(true);

		currentMap = new Label("Floor 1: Biology lab", skin);
		currentMap.setFontScale((float) 0.5);
		//currentMap.setSize(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/4);
		table.add(currentMap).expandX().padRight(1040).padTop(0);

		health = new HealthBar(145,8); // Create health bar
		health.getHealth(); // gets the value 
		health.setPosition((float)(Gdx.graphics.getWidth()/ 100), Gdx.graphics.getHeight()/2);

		Color color = new Color();

		invAtlas = new TextureAtlas(Gdx.files.internal("ActionBar/ActionBarAtlas.txt"));// atlas file

		Image invImage = new Image(new TextureRegion(invAtlas.findRegion("action")));

		invImage.scaleBy(-0.3f); // size bar
		invImage.setColor(color.FIREBRICK); //color of the bar

		Table invTable = new Table();
		invTable.top();
		invTable.setFillParent(true);
		invTable.setPosition(165, 30);

		invTable.add(invImage).expandX().padRight(0).padTop(500);		
		
		itemAtlas = new TextureAtlas(Gdx.files.internal("ActionBar/WeaponsAtlas.txt"));

		// KEEP THESE STATEMENTS, THEY ARE Y-POSITIONS FOR THE ITEMS IN INVENTORY<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		// Position 1: addItemsToDraw("book", -380);
		// Position 2: addItemsToDraw("book", -302);
		// Position 3: addItemsToDraw("book", -224);
		// Position 4: addItemsToDraw("book", -146);
		// Position 5: addItemsToDraw("book", -68);
		// Position 6: addItemsToDraw("book", 10);
		// Position 7: addItemsToDraw("book", 88);
		// Position 8: addItemsToDraw("book", 166);
		// Position 9: addItemsToDraw("book", 244);
		// Position 10: addItemsToDraw("book", 322);
		// KEEP THESE STATEMENTS, THEY ARE Y-POSITIONS FOR THE ITEMS IN INVENTORY<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		stage.addActor(invTable);
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

	public void addItemsToDraw(String atlasName, int padRight) {		
		invItems = new Container<Image>();
		invItems.top();
		invItems.setFillParent(true);
		invItems.setPosition(padRight, -538);

		Image currentImage = new Image(new TextureRegion(itemAtlas.findRegion(atlasName)));

		currentImage.scaleBy(0.9f); // size bar

		invItems.setActor(currentImage);

		stage.addActor(invItems);		

	}

}
