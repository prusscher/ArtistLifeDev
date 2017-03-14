package edu.sadsnails.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen implements Screen{
	
	MyGdxGame game;
	
	private Skin skin;
	
	private Container<TextButton> startContainer;
	private Container<TextButton> settingsContainer;
	private TextButton startGame;
	private TextButton settings;
	
	private Container<Label> titleContainer;
	private Label title;
	
	private Stage stage;
	
	private FPSLogger fps;
	
	public MainMenuScreen(final MyGdxGame game) {
		stage = new Stage(new FitViewport(400, 240));
		Gdx.input.setInputProcessor(stage);
		
		this.game = game;
		
		fps = new FPSLogger();
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		skin.add("default-font", new BitmapFont(Gdx.files.internal("fonts/nes_small.fnt")));
		
		// Create TextButtons
		startGame = new TextButton("START GAME", skin);
		settings = new TextButton("SETTINGS", skin);
		title = new Label("ARTIST LIFE", skin);
		title.setFontScale(3);
		
		// Listeners
		startGame.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) { changeView(new GameScreen(game)); }
		});
		
		settings.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) { }
		});
		
		// Setup Button Containers and Add to Screen
		startContainer = new Container<TextButton>(startGame);
		settingsContainer = new Container<TextButton>(settings);
		titleContainer = new Container<Label>(title);
		
		startContainer.setFillParent(true);
		settingsContainer.setFillParent(true);
		System.out.println(title.getWidth() + " " + title.getHeight());
		titleContainer.setBounds(stage.getWidth()/2 - title.getWidth()/2, 140, title.getWidth(), title.getHeight());

		startContainer.center();
		settingsContainer.padLeft(2).left().padBottom(2).bottom();
		
		stage.addActor(startContainer);
		stage.addActor(settingsContainer);
		stage.addActor(titleContainer);		
		
		stage.setDebugAll(true);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		fps.log();
		
		stage.act();
		stage.draw();
	}
	
	private void changeView(Screen screen) {
		game.setScreen(screen);
		this.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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