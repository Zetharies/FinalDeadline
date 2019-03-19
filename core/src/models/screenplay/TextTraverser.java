package models.screenplay;

import java.util.List;

import models.screenplay.ScreenplayNode.TYPE;
/** allows the dialogue to flow through the game, being linked to each other*/
public class TextTraverser {
	
	private ScreenplayHandler dialogue;
	private ScreenplayNode currentNode;
	
	public TextTraverser(ScreenplayHandler dialogue) {
		this.dialogue = dialogue;
		currentNode = dialogue.getNode(dialogue.getStart());
	}
	
	public ScreenplayNode getNextNode(int pointerIndex) {
		ScreenplayNode nextNode = dialogue.getNode(currentNode.getPointers().get(pointerIndex));
		currentNode = nextNode;
		return nextNode;
	}
	
	public List<String> getLabel() { //getter method for the labels
		return currentNode.getLabels();
	}
	
	public String getText() { //getter method for the text
		return currentNode.getText();
	}
	
	public TYPE getType() { //getter method for the type
		return currentNode.getType();
	}

}
