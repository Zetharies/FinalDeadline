package models.screenplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameSettings;

import models.EnumPlayerState;

/**
 * Allows us to set up the script of our game, including acting instructions and scene directions
 * @author Team 2, Aston University: CS2010
 */
public class Screenplay extends Table {
	
	private String currentText = "";
	private float animationTime, animationTotalTime;
	private static float TIME_PER_CHARACTER = 0.05f;
	private EnumPlayerState state = EnumPlayerState.IDLE;
	private Skin skin;
	private Label text;

	public Screenplay(String sceneDirection) {
		skin = new Skin(Gdx.files.internal("fonts/Holo-dark-hdpi.json"));
		text = new Label("\n", skin);
		text.setFontScale((float) 0.5);
		if(sceneDirection.equalsIgnoreCase("Flynn")) {
			this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("sprite/male/Flynn_Texture_Bg.png"))));
			this.setColor(46, 193, 231, 0.7f); // R-G-B & Opacity should be blue
		}
		if(sceneDirection.equalsIgnoreCase("Jessica")) {
			this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("sprite/female/Jessica_Texture_Bg.png"))));
			this.setColor(255, 192, 203, 0.7f); // R-G-B & Opacity should be pink
		}
		//this.setColor(0, 0, 0, 0.7f); // R-G-B & Opacity (If we don't want both characters to have a certain background)
		this.add(text).expand().align(Align.left).pad(5f);
	}
	
	public void setColor() {
		this.setColor(0, 0, 0, 0.7f); // R-G-B & Opacity should be pink
	}
	
	public void animateText(String text) {
		currentText = text;
		animationTotalTime = text.length()*TIME_PER_CHARACTER;
		state = EnumPlayerState.ANIMATING;
		animationTime = 0f;
	}
	
	public boolean isFinished() {
		if(state == EnumPlayerState.IDLE) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setText(String text) {
		if(!text.contains("\n")) {
			text += "\n";
		}
		this.text.setText(text);
	}
	
	public void act(float delta) {
		if(state == EnumPlayerState.ANIMATING) {
			animationTime += delta;
			if(animationTime > animationTotalTime) {
				state = EnumPlayerState.IDLE;
				animationTime = animationTotalTime;	
			}
			String displayedText = "";
			int charactersToDisplay = (int)((animationTime/animationTotalTime) * currentText.length());
			for(int i = 0; i < charactersToDisplay; i++) {
				displayedText += currentText.charAt(i);
			}
			if(!displayedText.equals(text.getText().toString())) {
				setText(displayedText);
			}
		}
		
	}
	
	public float getPrefWidth() {
		return GameSettings.SCREEN_PLAY_WIDTH;
	}

}
