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
	
	protected Actions actions;
	protected State state;
	
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
	
	private Table popupTable;
	private TextButton sleep;
	private TextButton art;
	
	private Texture player;

	private boolean popup = true;
	
	private boolean debug = false;
	
	public GameScreen(final MyGdxGame game) {
		this.game = game;
		
		state = new State();
		actions = new Actions(state);
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		createUI(debug);
		
	}
	
	private void createUI(boolean debug) {
		
		if(debug)
			stage.setDebugAll(true);
		
		// Create the Screen Buttons
		action = new TextButton("Action", skin);
		pause = new TextButton("Pause", skin);
		
		action.setBounds((Gdx.graphics.getWidth() - 250) / 2, 10, 250, 40);
		pause.setBounds(Gdx.graphics.getWidth() - 75, 0, 75, 75);
		
		// Button Listeners
		action.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(popup)
					stage.addActor(popupTable);
				else
					popupTable.remove();

				popup = !popup;
			}
		});
		
		pause.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
			}
		});
		
		// ----- MAIN UI ELEMENTS -----
		time = new Label("",skin);
		clock = new Container<Label>(time);
		clock.setBounds(15, 5, 40, 40);
		
		level = new Label("",skin);
		rank = new Container<Label>(level);
		rank.setBounds(120, Gdx.graphics.getHeight() - 80, 50, 50);
		
		xp = new Label("",skin);
		exp = new Container<Label>(xp);
		exp.setBounds(120, Gdx.graphics.getHeight() - 40, 40, 40);
		
		amount = new Label("",skin);
		money = new Container<Label>(amount);
		money.setBounds(Gdx.graphics.getWidth() - 85, Gdx.graphics.getHeight() - 60, 75, 75);
		
		energybar = new Label("",skin);
		energy = new Container<Label>(energybar);
		energy.setBounds(Gdx.graphics.getWidth() - 85, Gdx.graphics.getHeight() - 95, 75, 75);

		// Test image, Checking to see if IntelliJ compiling works.
		player = new Texture(Gdx.files.internal("badlogic.jpg"));
		testImage = new Image(player);
		testImage.setBounds(0, Gdx.graphics.getHeight() - 100, 100, 100);
		
		// ----- Create popup UI -----		
		popupTable = new Table();
		
		sleep = new TextButton("Sleep", skin);
		art = new TextButton("Make Art", skin);
		
		sleep.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				popup = !popup;
				popupTable.remove();
				actions.sleep();
				updateState();
			}
		});
		art.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				popup = !popup;
				popupTable.remove();
				actions.makeArt();
				updateState();
			}
		});
		
		float h = action.getHeight();
		float w = action.getWidth();
		
		popupTable.add(sleep).width(w).height(h);
		popupTable.row();
		popupTable.add(art).width(w).height(h);
		
		popupTable.setBounds(action.getX(), action.getY() + h + 10, w, 2*h);
		
		
		// Add the UI to the stage
		updateState();
		stage.addActor(clock);
		stage.addActor(exp);
		stage.addActor(energy);
		stage.addActor(money);
		stage.addActor(rank);
		stage.addActor(testImage);
		stage.addActor(action);
		stage.addActor(pause);
	}
	
	private void updateState() {
		time.setText(Integer.toString(state.hour + 1) + ":00");
		level.setText("Level: " + Integer.toString(state.level));
		xp.setText("XP: " + Integer.toString(state.xp));
		amount.setText("$" + Double.toString(state.money) + "0");
		energybar.setText("E: " + Integer.toString(state.energy) + "/100");
	}
	
	@Override
	public void show() { }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.2f, .2f, .2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Update the FPS Counter
		//fpsText.setText("FPS: " + Double.toString(fps));
		
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
		//stage.getViewport().update(width, height, true);
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