package edu.sadsnails.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import edu.sadsnails.game.actors.*;

public class GameScreen implements Screen 	{
	MyGdxGame game;

	private Stage stage;
	private Stage uiStage;
	
	protected Actions actions;
	protected State state;
	
	private MainUI ui;
	
	public Sound buttonSound;
	public Music gameMusic;
	
	private Room room;
	private Bed bed;
	private Desk desk;
	private Player player;
	
	private boolean debug = false;

	public GameScreen(final MyGdxGame game) {
		this.game = game;

		state = new State();
		actions = new Actions(game, state);

		// Load Game Sounds and load Main Game UI to stage
		loadSounds();
		setStage();
		
		// Add all of the actors to the stage
		addActors();
	}
	
	private void loadSounds() {
		buttonSound = game.assets.manager.get("sound/button.wav");
		gameMusic = game.assets.manager.get("music/Dream-Culture.mp3");
		
		gameMusic.setVolume((game.setting.musicVol()*(game.setting.masterVol()/100))/100);
		System.out.println((game.setting.musicVol()*(game.setting.masterVol()/100))/100);
		gameMusic.setLooping(true);
		gameMusic.play();
	}
	
	private void setStage() {
		ui = new MainUI(game, this);
		uiStage = ui.getStage();
		stage = new Stage(new FitViewport(400, 240, new OrthographicCamera()));
	}
	
	private void addActors() {
		room = new Room(game);
		room.setName("room");
		room.setBounds(80, 0, 240, 240);
		stage.addActor(room);
		room.setZIndex(0);
		
		bed = new Bed(game);
		bed.setName("bed");
		bed.setBounds(125, 95, 64, 64);
		stage.addActor(bed);
		bed.setZIndex(2);
		
		desk = new Desk(game);
		desk.setName("desk");
		desk.setBounds(210, 95, 64, 64);
		stage.addActor(desk);
		desk.setZIndex(2);
		
		player = new Player(game, room);
		player.setName("player");
		player.setBounds(176, 96, 48, 48);
		player.setX(176);
		player.setY(96);
		System.out.println("PLAYER LOCATION: " + player.getX() + " " + player.getY() + " " + player.getOriginX() + " " + player.getOriginY());
		stage.addActor(player);
		player.setZIndex(3);
		
		Shadow shadow = new Shadow(game, player);
		shadow.setBounds(176, 96, 48, 48);
		stage.addActor(shadow);
		shadow.setZIndex(2);
		
		bed.toBack();
		desk.toBack();
		shadow.toBack();
		room.toBack();
		
		for(Actor a : stage.getActors()) {
			System.out.println(a.getName() + " " + a.getClass().getSimpleName() + " " + "\tZ: " + a.getZIndex());
		}
	}
	
	@Override
	public void show() { }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.2f, .2f, .2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Draw The Game stage
		stage.act();
		stage.draw();
		
		// Draw the UI Stage
		uiStage.act();
		uiStage.draw();
		
		// Shift the camera to center on the player
		// Interpolate change in X and Y on Frame Time and Lerp Value
		float lerp = .75f;
		Vector3 pos = stage.getCamera().position;
		float xDelta = (player.getX() + (player.getWidth()/2) - pos.x) * lerp * delta;
		float yDelta = (player.getY() + (player.getHeight()/2) - pos.y) * lerp * delta;
		pos.x += xDelta;
		pos.y += yDelta;
		
		
		// Zooming Code :( no zoom
//		if(false) {
//			// Get the distance currently traveled and set Zoom 
//			float xyDelta = (float) Math.sqrt((xDelta * xDelta) + (yDelta * yDelta));
//					
//			// Set the zoom according to current movement
//			// As the player moves more, zoom out. Zoom in when there is no movement
//			((OrthographicCamera)stage.getCamera()).zoom = 1+ xyDelta * .5f;
//		}
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT) && Gdx.input.isKeyJustPressed(Keys.RIGHT)){
			if(((OrthographicCamera)stage.getCamera()).zoom < 1.5f)
				((OrthographicCamera)stage.getCamera()).zoom += .1f;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT) && Gdx.input.isKeyJustPressed(Keys.LEFT)){
			if(((OrthographicCamera)stage.getCamera()).zoom > .7f)
				((OrthographicCamera)stage.getCamera()).zoom -= .1f;
		}
		
		// Set the stage camera to look at the interpolated player position
		stage.getCamera().lookAt(pos);
			
		// Return to the main menu
		if(Gdx.input.isKeyPressed(Keys.BACKSPACE)){
			game.setScreen(new MainMenuScreen(game));
			this.dispose();
		}
		if(Gdx.input.isKeyPressed(Keys.F11) && Gdx.input.isKeyJustPressed(Keys.F11)){
			debug = !debug;
			stage.setDebugAll(debug);
			uiStage.setDebugAll(debug);
			MainUI.gridImage.setVisible(debug);
			MainUI.mousePos.setVisible(debug);
			System.out.println("Z: " + pos.z);
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
	
	private double dist(float x1, float y1, float x2, float y2) { return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)); }
	
	public void dimForSleep() {
		this.stage.addAction(sequence(fadeOut(4f), moveBy(0, 0, 6f), fadeIn(4f)));
	}
	public void dimForNap() {
		this.stage.addAction(sequence(alpha(.2f, 4f), moveBy(0, 0, 6f), alpha(1f, 4f)));
	}
	
	@Override
	public void dispose() {
		state.save();
		stage.dispose();
		ui = null;
		buttonSound.dispose();
		gameMusic.dispose();
		
	}
}
