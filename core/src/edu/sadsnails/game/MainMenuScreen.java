package edu.sadsnails.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen{
	
	MyGdxGame game;
	OrthographicCamera camera;

	private int count;

	private double fps;
	
	private Skin skin;
	private TextButton start;
	private Stage stage;
	
	public MainMenuScreen(final MyGdxGame game) {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		fps = 0.0;
		count = 0;
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		TextButton button = new TextButton("Start Game", skin);
		TextButton setting = new TextButton("Setting", skin);
		
		// Listeners
		button.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
			}
		});
		
		setting.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
			}
		});
		
		// position
		button.setBounds(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2,150 ,150/2);
		setting.setBounds(15, 15, 100, 50);
		
		// add actors
		stage.addActor(button);
		stage.addActor(setting);
		
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Update the Ortho Camera and set the projection to the spritebatch
		// ...not really sure what it does, we'll find out later.
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		// Begin the batch
		game.batch.begin();
		
		// Main Menu Text
		game.font.draw(game.batch, "Main Menu", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		game.font.draw(game.batch, "Press Space for Ball Bounce, Click for Main Game : ", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 - 50);
		
		stage.act();
		stage.draw();
		
		// Draw FPS
		fps = (int)(1/delta);
		game.font.draw(game.batch, "FPS :" + Double.toString(fps), 20, Gdx.graphics.getHeight()-20);
		
		// End the sprite batch
		game.batch.end();

		// Check to see if the screen was clicked
		if(Gdx.input.isTouched()){
			System.out.println("Creating GameScreen");
			game.setScreen(new GameScreen(game));
			this.dispose();
		}
		else if(Gdx.input.isKeyPressed(Keys.SPACE)){
			System.out.println("Creating BallBounce");
			game.setScreen(new BallBounceTest(game));
			this.dispose();
		}

	}
	
	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		game.batch.setProjectionMatrix(camera.combined);
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
		// TODO Auto-generated method stub
		
	}
}
