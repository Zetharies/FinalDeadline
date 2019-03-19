package models;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import models.EnumPlayerFacing;

public class AnimationSet {
	
	@SuppressWarnings("rawtypes") //animations for walking, standing, and powered up
	private Map<EnumPlayerFacing, Animation> walking;
	private Map<EnumPlayerFacing, TextureRegion> standing;
	private Map<EnumPlayerFacing, Animation> poweredUp;
	
	public AnimationSet(Animation<?> powered1, //animations for powered up states
            Animation<?> powered2) {
		poweredUp = new HashMap<EnumPlayerFacing, Animation>();
		poweredUp.put(EnumPlayerFacing.N, powered1);
		poweredUp.put(EnumPlayerFacing.S, powered2);
		
	}
		
	@SuppressWarnings("rawtypes") //animations for moving in different directions
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
