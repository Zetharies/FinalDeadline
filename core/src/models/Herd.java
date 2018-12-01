package models;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.GameSettings;
import java.util.ArrayList;
import java.util.Random;

public class Herd {

    private static final int MAX_ZOMBIES = 10;
    private ArrayList<Zombie> zombies;
    private ArrayList<Integer> coordsX;
    private ArrayList<Integer> coordsY;
    private Random random = new Random();
    private int randX = 0;
    private int randY = 0;
    private TiledMapTileLayer collisions;

    //generate a group of zombies
    public Herd(TiledMapTileLayer collisions) {
        this.collisions = collisions;
        zombies = new ArrayList<Zombie>();
        coordsX = new ArrayList<Integer>();
        coordsY = new ArrayList<Integer>();
        Zombies();
    }

    public void Zombies() {
        //generate random zombies, change max zombies constant to increase/decrease npcs
        for (int i = 0; i < MAX_ZOMBIES; i++) {
            randX = random.nextInt((int) GameSettings.SCALED_TILE_SIZE);
            randY = random.nextInt((int) GameSettings.SCALED_TILE_SIZE);

            //ensure they dont render in blocked block will crash game
            if (!collisions.getCell(randX, randY).getTile().getProperties().containsKey("blocked")) {
                coordsX.add(randX);
                coordsY.add(randY);
            }

        }
        for (int i = 0; i < coordsX.size(); i++) {
            System.out.println("x= " + coordsX.get(i) + " y= " + coordsY.get(i));
            //add zombies in random positions in the map 
            zombies.add(new Zombie(coordsX.get(i), coordsY.get(i), collisions));
        }
        //test zombies 
//        zombies.add(new Zombie(14, 80, collisions));
//        zombies.add(new Zombie(14, 79, collisions));
//        zombies.add(new Zombie(14, 78, collisions));

    }

    public ArrayList<Zombie> getZombiesList() {
        return zombies;
    }
}
