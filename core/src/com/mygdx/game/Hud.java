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

import models.HealthBar;
import models.inventory.Item;

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

		invAtlas = new TextureAtlas(Gdx.files.internal("inventory/InventoryAtlas.txt"));// atlas file

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

	public void reduceHealth(float damage) {
		health.setValue(health.getValue() - damage);

	}

	public void increaseHealth(float healthInc) {
		if ((health.getValue() + healthInc) >= 1.0f) {
			health.setValue(1.0f);

		} else {
			health.setValue(health.getValue() + healthInc);

		}

	}

	public float getHealth() {
		return health.getValue();
	}

	// Adds the found item to the inventory bar, un-equipped
	public void addLatestFoundItemToInv(String atlasName, int padRight, Boolean drinkDrawn, String objectType) {	
		if (objectType.equals("drink")) {
			if (drinkDrawn == false) {
				invItems = new Container<Image>();
				invItems.top();
				invItems.setFillParent(true);
				invItems.setPosition(padRight, -535);

				Image currentItem = new Image(new TextureRegion(invAtlas.findRegion(atlasName)));

				currentItem.scaleBy(0.9f);

				invItems.setActor(currentItem);
				invItems.setName("invItems." + atlasName);

				stage.addActor(invItems);	
			}
			
		} else {
			invItems = new Container<Image>();
			invItems.top();
			invItems.setFillParent(true);
			invItems.setPosition(padRight, -535);

			Image currentItem = new Image(new TextureRegion(invAtlas.findRegion(atlasName)));

			currentItem.scaleBy(0.9f);

			invItems.setActor(currentItem);
			invItems.setName("invItems." + atlasName);

			stage.addActor(invItems);	

		}

	}

	public void drawEquippedItem(Item equippedItem) {	
		for (Actor currentActor : stage.getActors()) {
			if (currentActor.getName().equals("invBox")) {
				currentActor.remove();

				if (equippedItem != null) {
					invBox = new Container<Image>();
					invBox.top();
					invBox.setFillParent(true);
					invBox.setPosition(equippedItem.getInvBoxX(), -504);

					invBoxImage = new Image(new TextureRegion(invAtlas.findRegion("invBox")));

					// NEED TO ADD ACCURATE SCALING 
					invBoxImage.scaleBy(0.17f, -0.05f);		

					invBox.setActor(invBoxImage);
					invBox.setName("invBox");

					stage.addActor(invBox);

				} else {
					invBox = new Container<Image>();
					invBox.setName("invBox");
					
					stage.addActor(invBox);
					
				}
			}


		}


	}

	public void removeEquippedItem(Item equippedItem) {		
		for (Actor currentActor : stage.getActors()) {			
			if (currentActor.getName().equals("invItems." + equippedItem.getAtlasImage())) {
				currentActor.clear();

			}

		}


	}

}
