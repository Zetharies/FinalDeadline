package models;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.GameSettings;
import java.util.ArrayList;
import java.util.Random;

public class Herd {

	/**
	 * Caps the number of Zombies to 50.
	 */
    private static final int MAX_ZOMBIES = 50;
    /**
     * An <code>ArrayList</code> of type <code>Zombie</code>.
     */
    private ArrayList<Zombie> zombies;
    /**
     * An <code>ArrayList</code> of type <code>Integer</code> containing X coordinates of Zombies.
     */
    private ArrayList<Integer> coordsX;
    /**
     * An <code>ArrayList</code> of type <code>Integer</code> containing Y coordinates of Zombies.
     */
    private ArrayList<Integer> coordsY;
    /**
     * A <code>Random</code> use to create a random number.
     */
    private Random random = new Random();
    /**
     * An <code>int</code> containing a random X coordinate. Initially set to 0.
     */
    private int randX = 0;
    /**
     * An <code>int</code> containing a random Y coordinate. Initially set to 0.
     */
    private int randY = 0;
    /**
     * A <code>TiledMapTileLayer</code> containing the given collisions of a map.
     */
    private TiledMapTileLayer collisions;

    //generate a group of zombies
    /**
     * Constructor for <code>Herd</code>
     * @param collisions <code>TiledMapTileLayer</code> of the collision layer of a map.
     */
    public Herd(TiledMapTileLayer collisions) {
        this.collisions = collisions;
        zombies = new ArrayList<Zombie>();
        coordsX = new ArrayList<Integer>();
        coordsY = new ArrayList<Integer>();
        zombies();
    }

    /**
     * Updates {@link #zombies} so that more Zombies are added.<br>
     * The locations of the Zombies are checked against the collision layer.
     */
    public void zombies() {
        //generate random zombies, change max zombies constant to increase/decrease npcs
        for (int i = 0; i < MAX_ZOMBIES; i++) {
            randX = random.nextInt((int) GameSettings.SCALED_TILE_SIZE);
            randY = random.nextInt((int) GameSettings.SCALED_TILE_SIZE);

            //ensure they dont render in blocked block will crash game
            if (!collisions.getCell(randX, randY).getTile().getProperties().containsKey("blocked")) {
                coordsX.add(randX);
                coordsY.add(randY);
            } /* else {
            	i--;
            } */

        }
        for (int i = 0; i < coordsX.size(); i++) {
            // System.out.println("x= " + coordsX.get(i) + " y= " + coordsY.get(i));
            //add zombies in random positions in the map 
            zombies.add(new Zombie(coordsX.get(i)+1, coordsY.get(i)+1, collisions));
        }
        //test zombies 
   //     zombies.add(new Zombie(12, 56, collisions));

    }

    /**
     * @return <code>ArrayList</code> {@link #zombies}.
     */
    public ArrayList<Zombie> getZombiesList() {
        return zombies;
    }

    /**
     * Re-assigns {@link #collisions}.<br>
     * @param collisions <code>TiledMapTileLayer</code> of the collision layer of a map.
     */
    public void setCollisions(TiledMapTileLayer collisions) {
        this.collisions = collisions;
    }

    /**
     * Gets rid of all zombies.<br>
     * Empties <code>ArrayLists</code> {@link #zombies}, {@link #coordsX} and {@link #coordsY}.
     */
    public void clearZombies() {
        zombies.clear();
        coordsX.clear();
        coordsY.clear();
    }

    /**
     * Re-spawns zombies.<br>
     * Clears calls {@link #clearZombies()} and {@link #zombies()}.
     */
    public void respawnZombies() {
        clearZombies();
        zombies();
    }

}
