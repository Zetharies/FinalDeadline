package models;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import models.EnumPlayerFacing;

public class AnimationSet {
	
	@SuppressWarnings("rawtypes")
	private Map<EnumPlayerFacing, Animation> walking;
	private Map<EnumPlayerFacing, TextureRegion> standing;
	
	@SuppressWarnings("rawtypes")
	public AnimationSet(Animation<?> moveNorth,
            Animation<?> movegSouth,
            Animation<?> moveEast,
            Animation<?> moveWest,
            TextureRegion standNorth,
            TextureRegion standSouth,
            TextureRegion standEast,
            TextureRegion standWest){
				walking = new HashMap<EnumPlayerFacing, Animation>();
				walking.put(EnumPlayerFacing.N, moveNorth);
				walking.put(EnumPlayerFacing.S, movegSouth);
				walking.put(EnumPlayerFacing.E, moveEast);
				walking.put(EnumPlayerFacing.W, moveWest);
				standing = new HashMap<EnumPlayerFacing, TextureRegion>();
				standing.put(EnumPlayerFacing.N, standNorth);
				standing.put(EnumPlayerFacing.S, standSouth);
				standing.put(EnumPlayerFacing.E, standEast);
				standing.put(EnumPlayerFacing.W, standWest);
	}
	
	public Animation<?> getWalking(EnumPlayerFacing dir) {
		return walking.get(dir);
	}
	
	public TextureRegion getStanding(EnumPlayerFacing dir) {
		return standing.get(dir);
	}

}
