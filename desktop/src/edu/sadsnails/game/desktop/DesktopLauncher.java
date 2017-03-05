package edu.sadsnails.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import edu.sadsnails.game.MyGdxGame;
import edu.sadsnails.game.Settings;


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
		
		Settings settings = new Settings();
		
		config.width = settings.width();
		config.height = settings.height();
		
		System.out.println("START: " + settings.width() + " " + settings.height());
		
		config.foregroundFPS = 60;
		config.backgroundFPS = 15;
		
		// Start the game
		new LwjglApplication(new MyGdxGame(settings), config);
	}
}
