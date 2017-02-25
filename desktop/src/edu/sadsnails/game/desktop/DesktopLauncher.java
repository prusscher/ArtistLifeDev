package edu.sadsnails.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.sadsnails.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Artist Life";
		
		config.width = 1280;
		config.height = 720;
		
		config.foregroundFPS = 60;
		config.backgroundFPS = 15;
		
		new LwjglApplication(new MyGdxGame(), config);
	}
}
