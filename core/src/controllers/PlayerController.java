package controllers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import models.Book;
import models.EnumPlayerFacing;
import models.Player;
import models.Zombie;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import managers.SettingsManager;

public class PlayerController extends InputAdapter {

    private Player p;
    private boolean up, down, left, right;
    private TiledMapTileLayer collisions;
    private boolean mapChange;
    private ArrayList<Book> books;

    public PlayerController(Player p, TiledMapTileLayer collisions) {
        this.p = p;
        this.collisions = collisions;
        mapChange = false;
        books = new ArrayList<Book>();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (SettingsManager.KEYS) {
            if (keycode == Keys.UP) {
                up = true;
            }
        } else if (SettingsManager.WASD) {
            if (keycode == Keys.W) {
                up = true;
            }
        }
//        if (SettingsManager.KEYS) {
//            if (keycode == Keys.UP) {
//                up = true;
//            }
//        } else if (SettingsManager.WASD) {
//            if (keycode == Keys.W) {
//                up = true;
//            }
//        }
        if (SettingsManager.KEYS) {
            if (keycode == Keys.DOWN) {
                down = true;
            }
        } else if (SettingsManager.WASD) {
            if (keycode == Keys.S) {
                down = true;
            }
        }

        if (SettingsManager.KEYS) {
            if (keycode == Keys.LEFT) {
                left = true;
            }
        } else if (SettingsManager.WASD) {
            if (keycode == Keys.A) {
                left = true;
            }
        }

        if (SettingsManager.KEYS) {
            if (keycode == Keys.RIGHT ) {
                right = true;
            }
        } else if (SettingsManager.WASD) {
            if (keycode == Keys.D) {
                right = true;
            }
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Keys.UP || keycode == Keys.W) {
            up = false;
            // p.movePlayer(0, 1); // 0 on the x axis, 1 on the y axis
        }
        if (keycode == Keys.DOWN || keycode == Keys.S) {
            down = false;
            // p.movePlayer(0, -1); // 0 on the x axis, -1 on the y axis
        }
        if (keycode == Keys.LEFT || keycode == Keys.A) {
            left = false;
            // p.movePlayer(-1, 0); // -1 on the x axis, 0 on the y axis
        }
        if (keycode == Keys.RIGHT || keycode == Keys.D) {
            right = false;
            // p.movePlayer(1, 0); // 1 on the x axis, 0 on the y axis
        }
        if (keycode == Keys.SPACE) {
        	books.add(p.shoot(p.getDirection(), p.getX(), p.getY()));
        }

        return false;
    }

    public void update(float delta) {
        if (up) {
            if (!isUpBlocked()) {
                p.move(EnumPlayerFacing.UP);
                return;
            }
        }
        if (down) {
            if (!isDownBlocked()) {
                p.move(EnumPlayerFacing.DOWN);
                return;
            }
        }
        if (left) {
            if (!isLeftBlocked()) {
                p.move(EnumPlayerFacing.LEFT);
                return;
            }
        }
        if (right) {
            if (!isRightBlocked()) {
                p.move(EnumPlayerFacing.RIGHT);
                return;
            }
        }
    }

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param collisionLayer
     * @return <code> boolean </code> true if the specified coordinates on the
     * given collisionLayer has the property "blocked", false otherwise.
     */
    public boolean isBlocked(int x, int y, TiledMapTileLayer collisionLayer) {
        return collisionLayer.getCell(x, y).getTile().getProperties().containsKey("blocked");
        // return false;
    }

    /**
     *
     * @return <code> boolean </code> true if the cell above the player is
     * blocked, false otherwise.
     */
    public boolean isUpBlocked() {
        return isBlocked(p.getX(), p.getY() + 1, collisions);
        // return false;
    }

    /**
     *
     * @return <code> boolean </code> true if the cell below the player is
     * blocked, false otherwise.
     */
    public boolean isDownBlocked() {
        if (p.getY() - 1 >= 0) {
            return isBlocked(p.getX(), p.getY() - 1, collisions);
            // return false;
        }
        return true;
        // return false;
    }

    /**
     *
     * @return <code> boolean </code> true if the cell to the left of the player
     * is blocked, false otherwise.
     */
    public boolean isLeftBlocked() {
        if (p.getY() - 1 >= 0) {
            return isBlocked(p.getX() - 1, p.getY(), collisions);
            // return false;
        }
        return true;
        // return false;
    }

    /**
     *
     * @return <code> boolean </code> true if the cell to the right of the
     * player is blocked, false otherwise.
     */
    public boolean isRightBlocked() {
        return isBlocked(p.getX() + 1, p.getY(), collisions);
        // return false;
    }
    
    public boolean isOnZombie(ArrayList<Zombie> zombies) {
    	for(Zombie zombie : zombies) {
    		if(zombie.getX() == p.getX() && zombie.getY() == p.getY()) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void updatePlayerCoordinates(int x, int y) {
    	p.updateCoordinates(x, y);
    }
    
    public void changeMap() {
    	p.updateCoordinates(14, 90);
    	mapChange = true;
    }
    
    public boolean getMapChange() {
    	return mapChange;
    }
    
    public void setMapChange(boolean value) {
    	mapChange = value;
    }
    
    public void checkExit() {
    	if(p.isPlayerOnExit(p.getX(), p.getY())) {
    		changeMap();
    	}
    }
    
    public void setCollisions(TiledMapTileLayer collisions) {
    	this.collisions = collisions;
    }
    
    public TiledMapTileLayer getCollisionLayer() {
    	return collisions;
    }
    
    public ArrayList<Book> getBooks() {
    	return books;
    }
    
    public void resetDirection() {
    	up = false;
    	down = false;
    	left = false;
    	right = false;
    }
}
