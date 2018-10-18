package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;

import Screens.MainMenuScreen;
import Screens.SplashScreen;

public class Main extends Game {

	private static long MINIMUM_TIME = 6500L; // How long "Aston Unviersity" into screen appears. 6500L = 6.5 seconds

	public Main() {
		super();
	}

	@Override
	public void create() {

		setScreen(new SplashScreen());

		final long introTime = System.currentTimeMillis();

		new Thread(new Runnable() {

			@Override
			public void run() {
				Gdx.app.postRunnable(new Runnable() {
					
					@Override
					public void run() {
						long elapsedTime = System.currentTimeMillis() - introTime;
						if (elapsedTime < Main.MINIMUM_TIME) {
							Timer.schedule(new Timer.Task() {
								@Override
								public void run() {
									Main.this.setScreen(new MainMenuScreen());
								}
							}, (float) (Main.MINIMUM_TIME - elapsedTime) / 1000f);
						} else {
							Main.this.setScreen(new MainMenuScreen());
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
