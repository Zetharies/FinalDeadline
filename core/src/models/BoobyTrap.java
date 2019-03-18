package models;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.ArrayList;

public class BoobyTrap {

    private ArrayList<Trap> traps;
    private TiledMapTileLayer collisions;

    /**
     * construct array of traps and add
     *
     * @param collisions
     */
    public BoobyTrap(TiledMapTileLayer collisions, int trap) {
        traps = new ArrayList<Trap>();
        this.collisions = collisions;
        addTraps(trap);
    }

    /**
     * set traps at specific coordinates- used to render traps
     */
    private void addTraps(int trap) {
        //x y collision set
        if (trap == 0) {
            traps.add(new Trap(65, 76, collisions));
            traps.add(new Trap(65, 77, collisions));
            traps.add(new Trap(65, 78, collisions));
        }
    }

    /**
     * return list of traps used for rendering and updating
     *
     * @return
     */
    public ArrayList<Trap> getTraps() {
        return traps;
    }
}
