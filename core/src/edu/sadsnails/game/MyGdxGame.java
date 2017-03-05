package edu.sadsnails.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public Settings setting;
	
	public MyGdxGame(Settings setting) {
		super();
		this.setting = setting;
	}
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		// Calls the Game Render method
		// Renders the current game screen
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		setting.close();
	}

	
}
