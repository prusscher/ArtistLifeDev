package edu.sadsnails.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen{
	MyGdxGame game;
	OrthographicCamera camera;
	
	private double fps;
	
	public GameScreen(final MyGdxGame game) {
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		
		fps = 0.0;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.2f, .2f, .2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		game.font.draw(game.batch, "Hay, You're in the game :^)", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		game.font.draw(game.batch, "Press Backspace to go back to the Main Menu", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 - 25);
		
//		// Draw FPS every frame
//		if(true) //(TimeUtils.millis() - lastFpsDraw > 100)
//		{
//			//lastFpsDraw = TimeUtils.millis();
//			fps = (int)(1/delta);
//		}
		fps = (int)(1/delta);
		game.font.draw(game.batch, "FPS :" + Double.toString(fps), 20, Gdx.graphics.getHeight()-20);
		
		game.batch.end();
		
		if(Gdx.input.isKeyPressed(Keys.BACKSPACE)){
			game.setScreen(new MainMenuScreen(game));
			this.dispose();
		}
		
	}

	@Override
	public void resize(int width, int height) {
		// Basic Resizing
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
		camera = null;
	}

}
