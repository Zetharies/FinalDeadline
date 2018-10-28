package models;

public class Player {
	
	private int x, y;

	public Player(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public void movePlayer(int dx, int dy) {
		x += dx;
		y += dy;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	

}
