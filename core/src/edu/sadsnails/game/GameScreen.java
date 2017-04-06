package edu.sadsnails.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import edu.sadsnails.game.actors.*;
import edu.sadsnails.game.widgets.PlayerIcon;

public class GameScreen implements Screen 	{
	MyGdxGame game;

	private Stage stage;

	protected Actions actions;
	protected State state;
	
	private MainUI ui;
	
	public Sound buttonSound;
	public Music gameMusic;
	
	private Room room;
	private Player player;
	
	private boolean debug = false;

	public GameScreen(final MyGdxGame game) {
		this.game = game;

		state = new State();
		actions = new Actions(state);

		// CREATE UI HERE
		loadSounds();
		ui = new MainUI(game, this);
		stage = ui.getStage();
		
		// Add all of the actors to the stage
		addActors();
	}
	
	private void loadSounds() {
		buttonSound = game.assets.manager.get("sound/button.wav");
		gameMusic = game.assets.manager.get("music/Furious-Freak.mp3");
		
		gameMusic.setVolume((game.setting.musicVol()*(game.setting.masterVol()/100))/100);
		System.out.println((game.setting.musicVol()*(game.setting.masterVol()/100))/100);
		gameMusic.setLooping(true);
		//gameMusic.play();
	}
	
	private void addActors() {
		room = new Room(game);
		room.setBounds(80, 0, 240, 240);
		stage.addActor(room);
		room.setZIndex(0);
	
		player = new Player(game, room);
		player.setBounds(176, 96, 48, 48);
		player.setX(176);
		player.setY(96);
		System.out.println("PLAYER LOCATION: " + player.getX() + " " + player.getY() + " " + player.getOriginX() + " " + player.getOriginY());
		stage.addActor(player);
		player.setZIndex(1);
	}
	
	@Override
	public void show() { }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.2f, .2f, .2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw The Scene2d stage
		stage.act();
		stage.draw();
		
		// Return to the main menu
		if(Gdx.input.isKeyPressed(Keys.BACKSPACE)){
			game.setScreen(new MainMenuScreen(game));
			this.dispose();
		}
		if(Gdx.input.isKeyPressed(Keys.SPACE) && Gdx.input.isKeyJustPressed(Keys.SPACE)){
			debug = !debug;
			stage.setDebugAll(debug);
			MainUI.gridImage.setVisible(debug);
			MainUI.mousePos.setVisible(debug);
			System.out.println(player.getActions().toString());
		}
		
		// Debug: Set the mouse Pos
		if(debug) {
			int relX = (int)(((double)Gdx.input.getX() / Gdx.graphics.getWidth()) * 400);
			int relY = 240 - (int)(((double)Gdx.input.getY() / Gdx.graphics.getHeight()) * 240);
			MainUI.mousePos.setText("x: " + relX + ", y: " + relY);
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void hide() { }
	
	public Actions getActions() { return actions; }
	public State getState() { return state; }
	public Room getRoom() { return room; }
	public Player getPlayer() { return player; }
	
	public MainUI getUI() { return ui; }
	
	@Override
	public void dispose() {
		state.save();
		stage.dispose();
		ui = null;
		buttonSound.dispose();
		gameMusic.dispose();
		
	}
}
