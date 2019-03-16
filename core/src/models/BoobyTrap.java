
package models;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.ArrayList;


public class BoobyTrap {
    private ArrayList<Trap> traps;
    private TiledMapTileLayer collisions; 
    
    public BoobyTrap(TiledMapTileLayer collisions) {
        traps = new ArrayList<Trap>();
        this.collisions = collisions;
        addTraps();
    }

    /**
     * set traps at specific coordinates- used to render traps
     */
    private void addTraps() {
        traps.add(new Trap(65, 75, collisions));
        traps.add(new Trap(65, 76, collisions));
        traps.add(new Trap(65, 77, collisions));
    }
    
    /**
     * return list of traps used for rendering and updating
     * @return 
     */
    public ArrayList<Trap> getTraps(){
        return traps;
    }
}
