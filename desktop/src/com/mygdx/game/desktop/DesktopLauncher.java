package com.mygdx.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.FinalDeadline;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Final Dead-Line " + FinalDeadline.VERSION; // Title of the game
		config.useGL30 = true;
		config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width; // Max display width
        config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height; // Max display height
        config.vSyncEnabled = true;
        config.addIcon("images/fdGameIcon.png", FileType.Internal);
        new LwjglApplication(new FinalDeadline(), config); // Start the main application for the game
        	
		/** FULLSCREEN, ONLY TO BE ADDED WHEN OPTIONS FOR RESOLUTIONS ARE AVAILABLE
        config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
        config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
        config.fullscreen = true;
		//config.resizable = false;
		 */
	}
}
