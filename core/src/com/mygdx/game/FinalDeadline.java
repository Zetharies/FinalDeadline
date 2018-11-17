package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.utils.Timer;

import managers.ScreenManager;

public class FinalDeadline extends Game {

	public static final String VERSION = "0.9 Alpha";
	private static long MINIMUM_TIME = 6500L; // How long "Aston University" intro screen appears. 6500L = 6.5 seconds
	private final long introTime = System.currentTimeMillis(); // How long intro should last for

	public FinalDeadline() {
		super();
	}

	@Override
	public void create() {
		ScreenManager.initialize(this); // Sets the ScreenManager to use this as the game
		ScreenManager.setSplashScreen(); // Set the screen to the Intro scene of Aston Uni

		new Thread(new Runnable() {

			@Override
			public void run() {
				Gdx.app.postRunnable(new Runnable() {
					
					@Override
					public void run() {
						long elapsedTime = System.currentTimeMillis() - introTime; // Current time - intro time
						if (elapsedTime < FinalDeadline.MINIMUM_TIME) { // Checks if current time is less than minimum time
							Timer.schedule(new Timer.Task() {
								@Override
								public void run() {
									ScreenManager.setMainMenuScreen(); // After, set the screen to the main menu
								}
							}, (float) (FinalDeadline.MINIMUM_TIME - elapsedTime) / 1000f);
						} else {
							ScreenManager.setMainMenuScreen(); // Set screen to main menu
						}
					}
					
				});
			}
		}).start();
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
        super.render();
	}

	@Override
	public void dispose() {
		// DISPOSE ALL RESOURCES
		getScreen().dispose();
		Gdx.app.exit();
	}
}
