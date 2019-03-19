package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import models.HealthBar;
import models.inventory.Item;
import riddleScreen.RiddleUI;

/**
 * Hud class, sets up a hud for the game.
 * 
 * @author Team 2f
 *
 */
public class Hud {
	public Stage stage;
	private Viewport viewPort;
	private Label currentMap, score, lives; // Labels to use
	private Skin skin;
	private HealthBar health; // Hud health bar for the player

	private TextureAtlas invAtlas;
	private TextureAtlas invBoxAtlas;

	private Container<Image> invItems; // Inventory items
	private Container<Image> invBox; // Inventory boxes
	private Image invBoxImage; // Inventory images

	private int currentScore, currentLives;
	private Label.LabelStyle style = new Label.LabelStyle();
	private RiddleUI riddleUI, riddleWin, riddleLose;
	private Label imagination, nothing, air, darkness;
	private Window temp, win, lose;
	private static final int MAX_LIVES = 5; // Max lives the player can have

	/**
	 * Constructor for out Hud class, sets up the game hud.
	 * 
	 * @param batch
	 */
	public Hud(SpriteBatch batch) {

		currentScore = 0;
		currentLives = 5;

		viewPort = new FitViewport(1200, 600);
		stage = new Stage(viewPort, batch);

		skin = new Skin(Gdx.files.internal("fonts/Holo-dark-hdpi.json"));

		// skin = new Skin(Gdx.files.internal("fonts/Holo-dark-hdpi.json"));

		style.font = generateFont(skin);

		imagination = new Label("\t V: Imagination", style);
		nothing = new Label("\t\t C: Nothing", style);
		darkness = new Label("\t\t X:Darkness", style);
		air = new Label("\t\t Z:air", style);

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		Table mapTable = new Table();
		mapTable.top();
		mapTable.setFillParent(true);

		currentMap = new Label("University Campus", skin);
		currentMap.setFontScale((float) 0.5);

		score = new Label("Score: " + currentScore, skin);
		score.setFontScale((float) 0.5);

		lives = new Label("Lives: " + currentLives + "/" + MAX_LIVES, skin);
		lives.setFontScale((float) 0.5);

		// currentMap.setSize(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/4);
		mapTable.add(currentMap).expandX().padTop(10);
		mapTable.add(score).expandX().padTop(10);
		mapTable.add(lives).expandX().padTop(10);
		mapTable.setName("mapTable");

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		health = new HealthBar(145, 8, Color.GREEN); // Create health bar
		health.getHealth(); // gets the value
		health.setPosition(555, 415);
		health.setName("health");

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		invAtlas = new TextureAtlas(Gdx.files.internal("inventory/InventoryAtlas.txt"));// atlas file
		invBoxAtlas = new TextureAtlas(Gdx.files.internal("inventory/InventoryBox Atlas.txt"));// atlas file

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		Table invTable = new Table();
		invTable.top();
		invTable.setFillParent(true);
		invTable.setPosition(165, 30);

		Image invImage = new Image(new TextureRegion(invAtlas.findRegion("invBar")));
		invImage.scaleBy(-0.3f);

		invTable.add(invImage).expandX().padRight(0).padTop(500);
		invTable.setName("invTable");

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		invBox = new Container<Image>();
		invBox.setName("invBox");

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		imagination.setPosition(100, 100);
		air.setPosition(100, 100);
		darkness.setPosition(100, 100);
		nothing.setPosition(100, 100);

		riddleUI = new RiddleUI(
				"If you look you cannot see me. \nAnd if you see me you cannot see anything else.  \n I can make anything you want happen, \n but later everything goes back to normal. What am I?");
		riddleUI.windowAdd(imagination);
		riddleUI.windowAdd(air);
		riddleUI.windowAdd(darkness);
		riddleUI.windowAdd(nothing);

		riddleWin = new RiddleUI("\n Correct! Go to the next map.\t  R:reset the riddle!");
		riddleWin.getWindow().setSize(500, 200);
		riddleLose = new RiddleUI("Wrong! find the riddle again!");

		stage.addActor(mapTable);
		stage.addActor(health);
		stage.addActor(invTable);
		stage.addActor(invBox);

	}

	/**
	 * Set the map label of the hud
	 * 
	 * @param newMapLabel The currentMap label
	 */
	public void setMapLabel(String newMapLabel) {
		currentMap.setText(newMapLabel);
	}

	/**
	 * Reset players health
	 */
	public void resetHealth() {
		health.setValue(1.0f);
	}

	/**
	 * Reduce health of the player
	 * 
	 * @param damage
	 */
	public void reduceHealth(float damage) {
		health.setValue(health.getValue() - damage);

	}

	/**
	 * Increase health of the player
	 * 
	 * @param healthInc
	 */
	public void increaseHealth(float healthInc) {
		if ((health.getValue() + healthInc) >= 1.0f) {
			health.setValue(1.0f);

		} else {
			health.setValue(health.getValue() + healthInc);

		}

	}

	/**
	 * Get the health of the player
	 * 
	 * @return
	 */
	public float getHealth() {
		return health.getValue();
	}

	/**
	 * <p>
	 * Method designed to draw the latest item found by the player onto the
	 * inventory
	 * 
	 * @param impItem    The last item which was found by the Player
	 * @param drinkDrawn Variable to check if the Drink item has already been drawn
	 *                   within the Inventory
	 */
	public void addLatestFoundItemToInv(Item impItem, Boolean drinkDrawn) {
		if (impItem.getName().equals("Drink") && drinkDrawn == false) {
			invItems = new Container<Image>();
			invItems.top();
			invItems.setFillParent(true);
			invItems.setPosition(impItem.getInvX(), -535);

			Image currentItem = new Image(new TextureRegion(invAtlas.findRegion(impItem.getAtlasImage())));

			currentItem.scaleBy(0.9f);

			invItems.setActor(currentItem);
			invItems.setName("invItems." + impItem.getName());

			stage.addActor(invItems);

		} else {
			invItems = new Container<Image>();
			invItems.top();
			invItems.setFillParent(true);
			invItems.setPosition(impItem.getInvX(), -535);

			Image currentItem = new Image(new TextureRegion(invAtlas.findRegion(impItem.getAtlasImage())));

			currentItem.scaleBy(0.9f);

			invItems.setActor(currentItem);
			invItems.setName("invItems." + impItem.getName());

			stage.addActor(invItems);

		}

	}

	/**
	 * <p>
	 * Method designed to re-draw a square onto the position of the current item,
	 * within the inventory
	 * 
	 * @param equippedItem The item which has currently been equipped by the player
	 */
	public void drawEquippedItem(Item equippedItem) {
		for (Actor currentActor : stage.getActors()) {
			if (currentActor.getName().equals("invBox")) {
				currentActor.remove();

				if (equippedItem != null) {
					invBox = new Container<Image>();
					invBox.top();
					invBox.setFillParent(true);
					invBox.setPosition(equippedItem.getInvBoxX(), -504);

					invBoxImage = new Image(new TextureRegion(invBoxAtlas.findRegion("invBox-Red")));

					
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

	/**
	 * <p>
	 * Method designed to remove the item from the inventory bar
	 * 
	 * @param equippedItem The item which has last been used
	 */
	public void removeEquippedItem(Item equippedItem) {
		for (Actor currentActor : stage.getActors()) {
			if (currentActor.getName().equals("invItems." + equippedItem.getName())) {
				currentActor.clear();

			}

		}
	}

	/**
	 * Remove the items that have been found
	 * <p>
	 * Method designed to remove all the items within the inventory bar
	 * 
	 */
	public void removeAllFoundItems() {
		for (Actor currentActor : stage.getActors()) {
			if (currentActor.getName().contains("invItems.")) {
				currentActor.clear();

			}

		}

	}

	/**
	 * <p>
	 * Increase the score of the player depending on the monster
	 * 
	 * @param monster
	 */
	public void increaseScore(String monster) {
		if (monster.equalsIgnoreCase("zombie")) {
			currentScore += 10;
			score.setText("Score: " + currentScore);
		} else if (monster.equalsIgnoreCase("boss")) {
			currentScore += 50;
			score.setText("Score: " + currentScore);
		}
	}

	/**
	 * Decrease the life of the player
	 */
	public void decreaseLife() {
		currentLives--;
		if (currentLives < 0) {
			currentLives = 0;
		}
		lives.setText("Lives: " + currentLives + "/" + MAX_LIVES);
	}

	/**
	 * Get the lives of the player
	 * 
	 * @return
	 */
	public int getLives() {
		return currentLives;
	}

	/**
	 * Generate the font of the BitMap
	 * 
	 * @param skin
	 * @return
	 */
	public BitmapFont generateFont(Skin skin) {
		// font for menu text
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/di-vari.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 40;

		BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
		// add the font to the skin
		skin.add("font", font12);

		generator.dispose();
		return font12;
	}

	/**
	 * Add a window for the stage.
	 */
	public void addWindow() {
		temp = riddleUI.getWindow();
		temp.setName("window");
		stage.addActor(temp);
	}

	/**
	 * Add a win label to the stage.
	 */
	public void addWinLabel() {

		// correct = new Label("Correct! Proceed to the next map", style);
//		riddleWin.windowAdd(correct);
		win = riddleWin.getWindow();
		win.setName("win");
		stage.addActor(win);

	}

	/**
	 * Add a lose label to the stage.
	 */
	public void addLoseLabel() {
		lose = riddleLose.getWindow();
		lose.setName("lose");
		stage.addActor(lose);

	}

	/**
	 * Reset the riddle
	 */
	public void resetRiddle() {
		for (Actor currentActor : stage.getActors()) {
			if (currentActor.getName().equals("win")) {
				currentActor.remove();
			}
		}
	}

	/**
	 * Remove the window of the riddle.
	 */
	public void removeWindow() {
		for (Actor currentActor : stage.getActors()) {
			if (currentActor.getName().equals("window") || currentActor.getName().equals("win")
					|| currentActor.getName().equals("lose")) {
				currentActor.remove();
			}
		}
	}

	public void setActor(Actor actor) {
		stage.addActor(actor);

	}
}
