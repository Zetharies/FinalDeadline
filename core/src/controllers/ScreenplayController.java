package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;

import models.screenplay.Screenplay;
import models.screenplay.ScreenplayHandler;
import models.screenplay.ScreenplayNode;
import models.screenplay.TextTraverser;
import models.screenplay.ScreenplayNode.TYPE;

/**
 * Controller for our Screenplay class, allows us to get user input
 * @see Screenplay
 * @author Team 2, Aston University: CS2010
 */
public class ScreenplayController extends InputAdapter {
	
	private TextTraverser traverser;
	private Screenplay playerDialogue;
	private ArrayList<Sound> arrayList;
	private String character;
	
	private int i = 0;
	
	/**
	 * Constructor for our ScreenplayController
	 * @param dialogue
	 */
	public ScreenplayController(Screenplay dialogue, String character) {
		this.playerDialogue = dialogue;
		this.character = character;
		if(character == "Flynn") {
			Sound sound = Gdx.audio.newSound(Gdx.files.internal("voices/flynn/Flynn_whats_going_on.wav"));
			arrayList = new ArrayList<Sound>();
			arrayList.add(sound);
		}
		if(character == "Jessica") {
			Sound sound = Gdx.audio.newSound(Gdx.files.internal("voices/jessica/Jessica_whats_going_on.wav"));
			arrayList = new ArrayList<Sound>();
			arrayList.add(sound);
		} else {
			
		}
		
	}
	
	/**
	 * Checks if the user is currently holding down a key
	 * Will allow user to move if there is no current dialogue, false otherwise
	 */
	@Override
	public boolean keyDown(int keycode) {
		if(playerDialogue.isVisible()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the user is currently holding down a key
	 * Allows user to carry onto the next dialogue
	 */
	@Override
	public boolean keyUp(int keycode) {
		if(traverser!= null && keycode == Keys.ENTER && playerDialogue.isFinished()) {
			if(character == "Flynn" || character == "Jessica") {
				if(i < arrayList.size()) {
					arrayList.get(i).play();
					i++;
				}
			}
			if(traverser.getType() == TYPE.END) {
				traverser = null;
				playerDialogue.setVisible(false);
			} else if (traverser.getType() == TYPE.LINEAR) {
				dialogueProgression(0);
			}
			return true;
		}
		if(playerDialogue.isVisible()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Starts and animates our dialogue
	 * @param dialogue
	 */
	public void startDialogue(ScreenplayHandler dialogue) {
		traverser = new TextTraverser(dialogue);
		playerDialogue.setVisible(true);
		playerDialogue.animateText(traverser.getText());
	}
	
	/**
	 * Sets up the next node in the dialogue
	 * @param index
	 */
	@SuppressWarnings("unused")
	private void dialogueProgression(int index) {
		ScreenplayNode nextNode = traverser.getNextNode(index);
		playerDialogue.animateText(nextNode.getText());
	}

}
