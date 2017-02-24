package edu.sadsnails.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen{
	// Reference to the main game class
	MyGdxGame game;
	
	// Skin for scene2d.ui
	private Skin skin;
	
	// Containers for Start button and Settings button
	private Container<TextButton> startContainer;
	private Container<TextButton> settingsContainer;
	private TextButton startGame;
	private TextButton settings;
	
	// Container for Title
	private Container<Label> titleContainer;
	private Label title;
	
	// Scene2d Stage
	private Stage stage;
	
	public MainMenuScreen(final MyGdxGame game) {
		// Create the stage and set the input processor
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		this.game = game;
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		// Create TextButtons
		startGame = new TextButton("Start Game", skin);
		settings = new TextButton("Setting", skin);
		title = new Label("Artist Life", skin);
		title.setFontScale(3);
		
		// Button Listeners
		startGame.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) { changeView(new GameScreen(game)); }
		});
		
		settings.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) { }
		});
		
		// Create Containers to fill screen
		startContainer = new Container<TextButton>(startGame);
		settingsContainer = new Container<TextButton>(settings);
		titleContainer = new Container<Label>(title);
		
		// Orient the Title and Buttons
		titleContainer.setBounds(Gdx.graphics.getWidth()/2-title.getWidth()/2, (Gdx.graphics.getHeight()/2 + (2*title.getHeight())), title.getWidth(), title.getHeight());
		startContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		settingsContainer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	
		
		// Proper Scene2d Containers
		startContainer.center();
		settingsContainer.padLeft(15).left().padBottom(15).bottom();
		
		// add Containers
		stage.addActor(startContainer);
		stage.addActor(settingsContainer);
		stage.addActor(titleContainer);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		// Draw the stage
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
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void hide() { }

	@Override
	public void dispose() {
		stage.dispose();
	}
}