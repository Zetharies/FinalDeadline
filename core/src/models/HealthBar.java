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
	 */
	private TextureRegion leftBorder;
	private TextureRegion rightBorder;

	// Health bar properties
	public HealthBar(int width, int height) {
		super(0f, 1f, 0.01f, false, new ProgressBarStyle());
		TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("images/progress-bars.pack"));
		getStyle().background = new TextureRegionDrawable(textureAtlas.findRegion("loading-bar-2-background")); // background
																												// of
																												// the
																												// bar
		getStyle().knob = ImageBar.getColoredDrawable(0, height, Color.GREEN);
		getStyle().knobBefore = ImageBar.getColoredDrawable(width, height, Color.GREEN);

		leftBorder = textureAtlas.findRegion("loading-bar-2-left"); // left border of the bar
		rightBorder = textureAtlas.findRegion("loading-bar-2-right"); // right border

		setWidth(width);
		setHeight(height);

		setAnimateDuration(0.0f); // animation of the health

	}

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

	public boolean getHealth() {
		// returns the health bar value,
		return setValue(health);
	}

	public void setHealth(boolean health) {

		// method to set the value
		health = setValue(this.health);
	}
}