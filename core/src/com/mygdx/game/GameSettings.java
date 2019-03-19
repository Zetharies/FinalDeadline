package com.mygdx.game;

/**
 * GameSettings class, responsible for the main settings of the game
 * @author Team 2f
 *
 */
public class GameSettings {
	public static int TILE_SIZE = 32; //sets tile size
	public static float SCALE = 2f; //sets scale
	public static float SCALED_TILE_SIZE = TILE_SIZE * SCALE; //tile size relative to player
	public static float TIME_PER_TILE = 0.5f;
   	public static float REFACING_TIME = 0.1f;
   	public static float SCREEN_PLAY_WIDTH = 175f; //width visible during gameplay
}
