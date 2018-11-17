package models.screenplay;

import java.util.HashMap;
import java.util.Map;

public class ScreenplayHandler {
	
	private Map<Integer, ScreenplayNode> nodes = new HashMap<Integer, ScreenplayNode>();
	
	public ScreenplayNode getNode(int id) {
		return nodes.get(id);
	}
	
	public void addNode(ScreenplayNode node) {
		this.nodes.put(node.getId(), node);
	}
	
	public int getStart() {
		return 0;
	}

}
