package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HealthBar extends ProgressBar {

	public float health = 1.0f;

	/**
	 * @param width  of the health bar
	 * @param height of the health bar
	 * @param color of the health  
	 */
	private TextureRegion leftBorder;
	private TextureRegion rightBorder;

	// Health bar properties
	public HealthBar(int width, int height, Color color) {
		super(0f, 1f, 0.01f, false, new ProgressBarStyle());
		TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("images/progress-bars.pack"));
		getStyle().background = new TextureRegionDrawable(textureAtlas.findRegion("loading-bar-2-background")); // background
																												// of
																												// the
																												// bar
		getStyle().knob = ImageBar.getColoredDrawable(0, height, color);
		getStyle().knobBefore = ImageBar.getColoredDrawable(width, height, color);

		leftBorder = textureAtlas.findRegion("loading-bar-2-left"); // left border of the bar
		rightBorder = textureAtlas.findRegion("loading-bar-2-right"); // right border

		setWidth(width);
		setHeight(height);

		setAnimateDuration(0.0f); // animation of the health

	}
	
	/**
	 * testing constructor
	 */

	public HealthBar() {
		// TODO Auto-generated constructor stub
		super(0f, 1f, 0.01f, false, new ProgressBarStyle());
	}
	
	/**
	 * Rendering a complete health bar texture 
	 * @param batch
	 * @param delta float to render it
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// First draw the left border.
		batch.draw(leftBorder, getX(), getY() - 6);
		// Save variables to restore their state after drawing
		float prevX = getX();
		float prevWidth = getWidth();
		// Set the variables which are used to draw the background
		setX(prevX + leftBorder.getRegionWidth());
		setWidth(prevWidth - leftBorder.getRegionWidth() - rightBorder.getRegionWidth());
		// Draw the progress bar as it would be without borders
		super.draw(batch, parentAlpha);
		// Set the variables to draw the right border
		setX(getX() + getWidth());
		// Draw the right border
		batch.draw(rightBorder, getX(), getY() - 6);
		// Reset the state of the variables so next cycle the drawing is done at correct
		// position
		setX(prevX);
		setWidth(prevWidth);
	}

	public boolean getHealthValue() {
		// returns the health bar value,
		return setValue(health);
	}

	public float getHealth() {
		//health float value dfor testing
		return health;
		
	}

	public void setHealth(boolean health) {

		// method to set the value
		health = setValue(this.health);
	}
}