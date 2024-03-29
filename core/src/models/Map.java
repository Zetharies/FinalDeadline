package models;

import java.util.ArrayList;

/**
 * The <code>Map</code> class represents a map that the player can move on.
 *
 */
public class Map {
	/**
	 * A <code>String</code> containing the file path of the map.
	 */
	private String mapLocation;
	/**
	 * An <code>int</code> containing the x coordinate of the starting location.
	 */
	private int x;
	/**
	 * An <code>int</code> containing the y coordinate of the starting location.
	 */
	private int y;
	/**
	 * An <code>ArrayList</code> containing a the coordinates of the exit location/s.
	 */
	private ArrayList<ArrayList<Integer>> exits;
	
	/**
	 * Constructor for <code>Map</code>.
	 * @param x <code>int</code> coordinate of the starting location
	 * @param y <code>int</code> coordinate of the starting location
	 * @param mapLocation <code>String</code> containing the file path of the map
	 */
	public Map(int x, int y, String mapLocation) {
		this.x = x;
		this.y = y;
		this.mapLocation = mapLocation;
		exits = new ArrayList<ArrayList<Integer>>();
	}
	
	/**
	 * 
	 * @return <code>int</code> x coordinate of the starting location
	 */
	public int getRespawnX() {
		return x;
	}
	
	/**
	 * 
	 * @return <code>int</code> x coordinate of the starting location
	 */
	public int getRespawnY() {
		return y;
	}
	
	/**
	 * 
	 * @return <code>String</code> file path of the map
	 */
	public String getMapLocation() {
		return mapLocation;
	}
	
	/**
	 * Adds an exit location of the map.
	 * @param x <code>int</code> coordinate of the exit location
	 * @param y <code>int</code> coordinate of the exit location
	 */
	public void addExit(int x, int y) {
		ArrayList<Integer> exit = new ArrayList<Integer>();
		exit.add(x);
		exit.add(y);
		exits.add(exit);
	}
	
	/**
	 * Returns all possible exit locations of the map.
	 * @return <code>ArrayList</code> containing an <code>ArrayList</code> containing <code>Integer</code>
	 */
	public ArrayList<ArrayList<Integer>> getExits(){
		return exits;
	}
	
}
