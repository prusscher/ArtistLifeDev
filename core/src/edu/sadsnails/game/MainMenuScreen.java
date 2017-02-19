package edu.sadsnails.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class MainMenuScreen implements Screen{
	
	MyGdxGame game;
	OrthographicCamera camera;
	
	public MainMenuScreen(final MyGdxGame game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
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
		game.font.draw(game.batch, "Yeah theres no button here yet, click to continue", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 - 50);
		
		// Draw FPS
		double fps = 1/delta;
		game.font.draw(game.batch, Double.toString(fps), 0, 0);

		// End the sprite batch
		game.batch.end();
	
		// Check to see if the screen was clicked
		if(Gdx.input.isTouched()){
			game.setScreen(new GameScreen(game));
			this.dispose();
		}
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
