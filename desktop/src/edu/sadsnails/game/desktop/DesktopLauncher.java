package edu.sadsnails.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

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
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		config.setTitle("Artist Life");
		Settings settings = new Settings();
		
		config.setWindowSizeLimits(400, 240, 9999, 9999);
		
		config.setWindowedMode(settings.width(), settings.height());
		
		System.out.println("START: " + settings.width() + " " + settings.height());
		
		config.setIdleFPS(15);
		config.useVsync(true);
		
		// Start the game
		new Lwjgl3Application(new MyGdxGame(settings), config);
	}
}
