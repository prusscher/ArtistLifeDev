package edu.sadsnails.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen{
	
	MyGdxGame game;
	
	private Skin skin;
	
	private Container<TextButton> startContainer;
	private Container<TextButton> settingsContainer;
	private TextButton startGame;
	private TextButton settings;
	
	private Stage stage;
	
	public MainMenuScreen(final MyGdxGame game) {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		this.game = game;
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		// Create TextButtons
		startGame = new TextButton("Start Game", skin);
		settings = new TextButton("Setting", skin);
		
		// Listeners
		startGame.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) { changeView(new GameScreen(game)); }
		});
		
		settings.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) { 
				// Change to settings menu or bring up settings menu
				// changeView(new Settings(game));
			}
		});
		
		// Create Containers to fill screen
		startContainer = new Container<TextButton>(startGame);
		settingsContainer = new Container<TextButton>(settings);
		
		startContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		settingsContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	
		
		// Proper Scene2d Containers
		startContainer.center();
		settingsContainer.padLeft(15).left().padBottom(15).bottom();
		
		// add Containers
		stage.addActor(startContainer);
		stage.addActor(settingsContainer);
		
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		stage.act();
		stage.draw();
	}
	
	private void changeView(Screen screen) {
		game.setScreen(screen);
		this.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
	
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
