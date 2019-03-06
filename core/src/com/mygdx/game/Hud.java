package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import inventory.items.Item;
import models.HealthBar;

public class Hud {
	public Stage stage;
	private Viewport viewPort;
	private Label currentMap;
	private Skin skin;
	private HealthBar health;

	private TextureAtlas invAtlas;
	private Container<Image> invItems;
	private Container<Image> invBox;
	private Image invBoxImage;

	public Hud(SpriteBatch batch) {
		viewPort = new FitViewport(1200, 600);
		stage = new Stage(viewPort, batch);
		skin = new Skin(Gdx.files.internal("fonts/Holo-dark-hdpi.json"));

		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		Table mapTable = new Table();
		mapTable.top();
		mapTable.setFillParent(true);

		currentMap = new Label("Floor 1: Biology lab", skin);
		currentMap.setFontScale((float) 0.5);
		//currentMap.setSize(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/4);
		mapTable.add(currentMap).expandX().padRight(1040).padTop(0);
		mapTable.setName("mapTable");

		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		health = new HealthBar(145,8); // Create health bar
		health.getHealth(); // gets the value 
		health.setPosition((float)(Gdx.graphics.getWidth()/ 100), Gdx.graphics.getHeight()/2);
		health.setName("health");

		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		invAtlas = new TextureAtlas(Gdx.files.internal("inventory/inventoryAtlas.txt"));// atlas file

		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		Table invTable = new Table();
		invTable.top();
		invTable.setFillParent(true);
		invTable.setPosition(165, 30);

		Image invImage = new Image(new TextureRegion(invAtlas.findRegion("invBar"))); 
		invImage.scaleBy(-0.3f);

		invTable.add(invImage).expandX().padRight(0).padTop(500);
		invTable.setName("invTable");

		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		invBox = new Container<Image>();
		invBox.setName("invBox");

		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		stage.addActor(mapTable);
		stage.addActor(health);	
		stage.addActor(invTable);
		stage.addActor(invBox);	


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

	// Adds the found item to the inventory bar, un-equipped
	public void addLatestFoundItemToInv(String atlasName, int padRight) {	
		invItems = new Container<Image>();
		invItems.top();
		invItems.setFillParent(true);
		invItems.setPosition(padRight, -538);

		Image currentItem = new Image(new TextureRegion(invAtlas.findRegion(atlasName)));

		currentItem.scaleBy(0.9f);

		invItems.setActor(currentItem);
		invItems.setName("invItems." + atlasName);
		
		stage.addActor(invItems);		

	}

	public void drawEquippedItem(Item equippedItem) {	
		float padRight = ( equippedItem.getInvX() + 7.0f);

		for (Actor currentActor : stage.getActors()) {

			if (currentActor.getName().equals("invBox")) {
				currentActor.remove();

				invBox = new Container<Image>();
				invBox.top();
				invBox.setFillParent(true);
				invBox.setPosition(padRight, -503);

				invBoxImage = new Image(new TextureRegion(invAtlas.findRegion("invBox")));

				// NEED TO ADD ACCURATE SCALING 
				invBoxImage.scaleBy(0.17f, -0.1f);

				invBox.setActor(invBoxImage);
				invBox.setName("invBox");

				stage.addActor(invBox);

			}


		}


	}

}
