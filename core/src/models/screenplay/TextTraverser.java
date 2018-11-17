package models.screenplay;

import java.util.List;

import models.screenplay.ScreenplayNode.TYPE;

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
	
	public List<String> getLabel() {
		return currentNode.getLabels();
	}
	
	public String getText() {
		return currentNode.getText();
	}
	
	public TYPE getType() {
		return currentNode.getType();
	}

}
