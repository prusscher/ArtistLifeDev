package edu.sadsnails.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

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
		
//		config.setTitle("Artist Life");
		config.title = "Artist Life";
		
		Settings settings = new Settings();
		
//		config.setWindowSizeLimits(400, 240, 9999, 9999);
		
//		config.setWindowedMode(settings.width(), settings.height());
		config.height = settings.height();
		config.width = settings.width();
		
		System.out.println("START: " + settings.width() + " " + settings.height());
		
//		config.setIdleFPS(15);
//		config.useVsync(true);
		config.foregroundFPS = LwjglApplicationConfiguration.getDesktopDisplayMode().refreshRate;
		config.backgroundFPS = 15;
		
		// Start the game
		new LwjglApplication(new MyGdxGame(settings), config);
	}
}
