package models.screenplay;

import java.util.ArrayList;
import java.util.List;

public class ScreenplayNode {
	
	private ArrayList<Integer> pointers = new ArrayList<Integer>();
	private ArrayList<String> labels = new ArrayList<String>();
	
	private String text;
	private int id;
	
	private TYPE type;
	
	public enum TYPE {
		LINEAR,
		END
	}
	
	public ScreenplayNode(String text, int id) {
		this.text = text;
		this.id = id;
		type = TYPE.END;
	}
	
	public void makeLinear(int nodeId) {
		pointers.clear();
		labels.clear();
		pointers.add(nodeId);
		type = TYPE.LINEAR;	
	}
	
	public List<Integer> getPointers(){
		return pointers;
	}
	
	public List<String> getLabels(){
		return labels;
	}
	
	public int getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public TYPE getType() {
		return type;
	}
	

}
