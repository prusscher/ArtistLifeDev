package edu.sadsnails.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.sadsnails.game.MyGdxGame;


/**
 * DesktopLauncher.java
 * The game's main class. Sets the initial game, and launches the application
 * 
 * @author Parker Russcher
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		// Setup the window config
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Artist Life";
		
		config.width = 1280;
		config.height = 720;
		
		config.foregroundFPS = 60;
		config.backgroundFPS = 15;
		
		// Start the game
		new LwjglApplication(new MyGdxGame(), config);
	}
}
