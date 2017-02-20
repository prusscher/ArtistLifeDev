package edu.sadsnails.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen{
	MyGdxGame game;
	
	private Skin skin;
	private Stage stage;
	private Table table;
	
	private Container<Label> fpsCon;
	private Label fpsText;
	
	private double fps = 60.0;
	
	private boolean debug = false;
	
	public GameScreen(final MyGdxGame game) {
		this.game = game;
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		// Create the table, fill the stage, and add the table to the stage
		table = new Table();
		table.setFillParent(true); 
		table.setWidth(Gdx.graphics.getWidth());
		table.setHeight(Gdx.graphics.getHeight());
		table.setOrigin(0, 0);
		
		// Setup FPS with Container
		fpsText = new Label("FPS: " + Double.toString(fps) ,skin);
		fpsCon = new Container<Label>(fpsText);
		fpsCon.debug();
		fpsCon.setBounds(10, Gdx.graphics.getHeight()-30, fpsText.getWidth(), fpsText.getHeight());
		
		// Add the FPS and Table to the Stage
		stage.addActor(fpsCon);
		stage.addActor(table);
		
		// This is optional, but enables debug lines for tables.
		table.setDebug(debug); 
		
		// Center the table layout
		table.center();
		
		// Create and Add the text to the table
		Label label1 = new Label("This is the Game Screen :^)", skin);
		Label label2 = new Label("This should be centered text, Press Backspace to go back to the Main Menu", skin);
		Label label3 = new Label("\nClick to toggle table.setDebug. Notice the lines!", skin);
		
		// Create a button and on click, change the table debug
		TextButton button = new TextButton("Toggle Debug", skin);
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				debug = !debug;
				table.setDebug(debug);
			}
		});
		
		// Add all the actors to the table
		table.add(label1).expandX();
		table.row();
		table.add(label2);
		table.row();
		table.add(label3);
		table.row();
		table.add(button);
		
		fps = 0.0;
	}
	
	@Override
	public void show() { }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.2f, .2f, .2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Update the FPS Counter
		fpsText.setText("FPS: " + Double.toString(fps));
		
		// Draw The Scene2d stage
		stage.act();
		stage.draw();
	
		fps = (int)(1/delta);
		
		// Return to the main menu
		if(Gdx.input.isKeyPressed(Keys.BACKSPACE)){
			game.setScreen(new MainMenuScreen(game));
			this.dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		fpsCon.setX(10);
		fpsCon.setY(height-30);
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
