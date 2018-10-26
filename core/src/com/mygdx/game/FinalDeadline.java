package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import screens.intro.SplashScreen;
import screens.menu.MainMenuScreen;

public class FinalDeadline extends Game {

	private static long MINIMUM_TIME = 6500L; // How long "Aston Unviersity" intro screen appears. 6500L = 6.5 seconds

	public FinalDeadline() {
		super();
	}

	@Override
	public void create() {

		setScreen(new SplashScreen()); // Set the screen to the Intro scene of Aston Uni

		final long introTime = System.currentTimeMillis(); // How long intro should last for

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
									FinalDeadline.this.setScreen(new MainMenuScreen()); // After, set the screen to the main menu
								}
							}, (float) (FinalDeadline.MINIMUM_TIME - elapsedTime) / 1000f);
						} else {
							FinalDeadline.this.setScreen(new MainMenuScreen()); // Set screen to main menu
						}
					}
					
				});
			}
		}).start();

	}

	@Override
	public void dispose() {
		// DISPOSE ALL RESOURCES
		getScreen().dispose();
		Gdx.app.exit();
	}
}
