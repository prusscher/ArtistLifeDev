package edu.sadsnails.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen{
	MyGdxGame game;
	
	private Skin skin;
	private Stage stage;
	
	private Container<Label> clock;
	private Label time;
	private Container<Label> rank;
	private Label level;
	private Container<Label> exp;
	private Label xp;
	private Container<Label> money;
	private Label amount;
	private Container<Label> energy;
	private Label energybar;
	
	private Image testImage;
	private TextButton pause;
	private TextButton action;
	
	private Texture player;

	public GameScreen(final MyGdxGame game) {
		this.game = game;
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		action = new TextButton("Action", skin);
		pause = new TextButton("Pause", skin);
		
		action.setBounds((Gdx.graphics.getWidth() - 250) / 2, 10, 250, 40);
		pause.setBounds(Gdx.graphics.getWidth() - 75, 0, 75, 75);
		
		// Listeners
		action.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
			}
		});
		
		pause.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
			}
		});
		
		time = new Label("12:00",skin);
		clock = new Container<Label>(time);
		clock.setBounds(15, 5, 40, 40);
		//clock.debug();

		level = new Label("Beginner",skin);
		rank = new Container<Label>(level);
		rank.setBounds(120, Gdx.graphics.getHeight() - 80, 50, 50);
		//rank.debug();

		xp = new Label("0/100",skin);
		exp = new Container<Label>(xp);
		exp.setBounds(120, Gdx.graphics.getHeight() - 40, 40, 40);
		//exp.debug();
		
		amount = new Label("Money: $0",skin);
		money = new Container<Label>(amount);
		money.setBounds(Gdx.graphics.getWidth() - 85, Gdx.graphics.getHeight() - 60, 75, 75);
		//money.debug();
		
		energybar = new Label("100/100",skin);
		energy = new Container<Label>(energybar);
		energy.setBounds(Gdx.graphics.getWidth() - 85, Gdx.graphics.getHeight() - 95, 75, 75);
		//energy.debug();

		// Test image, Checking to see if IntelliJ compiling works.
		player = new Texture(Gdx.files.internal("badlogic.jpg"));
		testImage = new Image(player);
		testImage.setBounds(0, Gdx.graphics.getHeight() - 100, 100, 100);

		// Add the FPS and Table to the Stage
		stage.addActor(clock);
		stage.addActor(exp);
		stage.addActor(energy);
		stage.addActor(money);
		stage.addActor(rank);
		stage.addActor(testImage);
		stage.addActor(action);
		stage.addActor(pause);
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
	}

	
	@Override
	public void resize(int width, int height) {
		/*stage.getViewport().update(width, height, true);
		fpsCon.setX(10);
		fpsCon.setY(height-30);
		pause.right();
		pause.bottom();*/
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