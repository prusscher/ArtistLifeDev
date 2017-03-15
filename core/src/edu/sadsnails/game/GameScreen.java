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
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TestGenerator;

public class GameScreen implements Screen 	{
	MyGdxGame game;
	
	private Skin skin;
	private Stage stage;
	
	protected Actions actions;
	protected State state;
	
	private Label time;
	private Label level;
	private Label xp;
	private Label money;
	private Label energy;
	private Label date;
	
	private Image testImage;
	
	private TextButton pause;
	private TextButton action;
	
	private Texture grid;
	private Image gridImage;
	
	
	// ------------THIS NEEDS TO BE MOVED TO ITS OWN CLASS FOR STORAGE AND CALLS -------------
	protected String [] drawing_type_array
			= {"Pixel", "Sketch", "Abstract", "Surreal", "Realistic", "Painting"};
	protected String [] drawing_subject_array
			= {"Anime", "Retro", "Funny", "Fantasy", "Animal", "Nature", "People"};
	
	private Texture scribbler3;
	private Texture scribbler2;
	private Texture scribbler1;
	private Texture testPlayerIcon;
	
	private boolean debug = false;
	
	public GameScreen(final MyGdxGame game) {
		this.game = game;
		
		state = new State();
		actions = new Actions(state);
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		stage = new Stage(new FitViewport(400, 240));
		Gdx.input.setInputProcessor(stage);
		
		loadPlayerImages();
		createUI(debug);
	}
	
	private void loadPlayerImages() {
//		scribbler3 = new Texture(Gdx.files.internal("images/playerIcon/scribbler3.gif"));
//		scribbler2 = new Texture(Gdx.files.internal("images/playerIcon/scribbler2.gif"));
//		scribbler1 = new Texture(Gdx.files.internal("images/playerIcon/scribbler.gif"));
		testPlayerIcon = new Texture(Gdx.files.internal("images/playerIcon/testman.png"));
	}
	
	private void createUI(boolean debug) {
		if(debug)
			stage.setDebugAll(true);
		
		// ----- MAIN UI BUTTONS -----
		action 		= new TextButton("Action", skin);
		pause 		= new TextButton("Pause", skin);
		
		action.setFillParent(false);
		action.setWidth(120);
		action.setHeight(20);
		pause.setFillParent(false);
		pause.setWidth(50);
		pause.setHeight(20);
		
		// Button Listeners
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
		
		// ----- MAIN UI ELEMENTS -----
		
		// Upper Left Hand Corner
		testImage = new Image(testPlayerIcon);
		xp = new Label("",skin);
		level = new Label("",skin);
		
		// Lower Left Hand Corner
		time = new Label("",skin);
		date = new Label("",skin);		
		
		// Upper Right Hand Corner
		money = new Label("",skin);
		energy = new Label("",skin);
		
		// ----- Main UI Layout -----
		VerticalGroup MainUI = new VerticalGroup();
		MainUI.setBounds(0, 0, 400, 240);
		
		// Top Main UI Container
		Table top = new Table();
			top.setWidth(400);
			top.add(testImage);
			Table xlGroup = new Table();
				xlGroup.add(xp).height(20).align(Align.left);
				xlGroup.row();
				xlGroup.add(level).height(20).align(Align.left);
				xlGroup.align(Align.left);
				xlGroup.padLeft(2f);
			top.add(xlGroup).width(100).top().padTop(2f);
			top.add().width(136);
			Table meGroup = new Table();
				meGroup.add(money).height(20).right();
				meGroup.row();
				meGroup.add(energy).height(20).right();
				meGroup.align(Align.right);
				meGroup.padRight(2f);
			top.add(meGroup).width(100).top().padTop(2f);
			
		MainUI.addActor(top);
		
		// Middle Spacer
		Table spacer = new Table();
			spacer.add().height(134).width(400);
		MainUI.addActor(spacer);
		
		// Bottom UI Container
		Table bottom = new Table();
			bottom.setWidth(400);
			Table dtGroup = new Table();
				dtGroup.add(date).height(20).align(Align.left);
				dtGroup.row();
				dtGroup.add(time).height(20).align(Align.left);
				dtGroup.align(Align.left);
				dtGroup.padLeft(2f);
			bottom.add(dtGroup).width(100).bottom().left().padRight(40);
			bottom.add(action).width(120).bottom();
			bottom.add(pause).width(50).bottom().right().padRight(2f).padLeft(90f);
			
		MainUI.addActor(bottom);
			
		// Update the labels with the correct value
		updateState();
		
		// Add the MainUI to the stage
		stage.addActor(MainUI);
		MainUI.setZIndex(2);
		// ----- end of MainUI -----
		
		// Popup UI Goes Here
		// Ill do this in a bit
		
		
		
		
		
		// -------- DEV STUFF ---------
		// Rear Grid
		grid = new Texture(Gdx.files.internal("dev/grid.png"));
		gridImage = new Image(grid);
		gridImage.setBounds(0, 0, 400, 240);
		stage.addActor(gridImage);
		gridImage.setZIndex(0);
		gridImage.setVisible(false);
	}
	
	private void updateState() {
		time.setText(Integer.toString(state.hour) + ":00");
		level.setText("Level: " + state.title);
		xp.setText("XP: " + Integer.toString(state.xp));
		money.setText("$" + Double.toString(state.money) + "0");
		energy.setText("E: " + Integer.toString(state.energy) + "/100");
		date.setText("" + state.date[2] + "/" + state.date[1] + "/" + state.date[0]);
//		date.setText("Year " + state.date[0] + " Month " + state.date[1] + " Day " + state.date[2]);
		
		// Change the player image here
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
		if(Gdx.input.isKeyPressed(Keys.SPACE) && Gdx.input.isKeyJustPressed(Keys.SPACE)){
			debug = !debug;
			stage.setDebugAll(debug);
			gridImage.setVisible(debug);
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

	@Override
	public void dispose() {
		stage.dispose();
	}
}

/*
//----- Create sleep options popup UI -----

		makeArt_popupTable = new Table();
		
		drawing_type = new SelectBox(skin);
		drawing_subject = new SelectBox(skin);
		
		sleep_popupTable = new Table();
		
		float height = action.getHeight();
		float width = action.getWidth() + 100;
		
		sleep1 = new TextButton("Take a nap", skin);
		sleep2 = new TextButton("Sleep until tomorrow", skin);
		
		sleep_popupTable.add(sleep1).width(width).height(50);
		sleep_popupTable.row();
		sleep_popupTable.add(sleep2).width(width).height(50);
		
		sleep_popupTable.setBounds(action.getX() - 50, action.getY() + 100, width, 2*height);
		
		sleep1.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sleep_popup = !sleep_popup;
				sleep_popupTable.remove();				
				actions.sleep(1);
				updateState();
			}
		});
		
		sleep2.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sleep_popup = !sleep_popup;
				sleep_popupTable.remove();				
				actions.sleep(2);
				updateState();
			}
		});
		
		// ----- Create actions popup UI -----		
		actions_popupTable = new Table();
		
		sleep = new TextButton("Sleep", skin);
		art = new TextButton("Make Art", skin);
		buyCoffee = new TextButton("Buy Coffee (-$5 | +40 Energy)", skin);
		
		sleep.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				action_popup = !action_popup;
				actions_popupTable.remove();
				stage.addActor(sleep_popupTable);
				//actions.sleep();
				//updateState();
			}
		});
		
		// ----- create makeArt interface ----
		// makeArt menu integration still in progress
		art.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				action_popup = !action_popup;
				actions_popupTable.remove();
				stage.addActor(makeArt_popupTable);
				//actions.makeArt();
				//updateState();
			}
		});
		
		makeArt_popupTable = new Table(skin);
		
		drawing_type = new SelectBox(skin);
		drawing_subject = new SelectBox(skin);
		
		submitArt = new TextButton("Submit", skin);
		
		drawing_type.setItems(drawing_type_array);
		drawing_subject.setItems(drawing_subject_array);
		
		makeArt_popupTable.add(new Label("Type of drawing:", skin)).center().height(20);
		makeArt_popupTable.row();
		makeArt_popupTable.add(drawing_type).width(drawing_type.getWidth() + 100).height(drawing_type.getHeight());
		makeArt_popupTable.row();
		makeArt_popupTable.add(new Label("Subject of drawing:", skin)).center().height(20);
		makeArt_popupTable.row();
		makeArt_popupTable.add(drawing_subject).width(drawing_type.getWidth() + 100).height(drawing_type.getHeight());
		makeArt_popupTable.row();
		makeArt_popupTable.add(submitArt).width(drawing_type.getWidth() + 100).height(drawing_type.getHeight());
		
		makeArt_popupTable.setBounds(stage.getWidth() / 2 - 50, action.getY()+action.getHeight()+2, 100, 200);
		
		
		submitArt.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				selected_type_index = drawing_type.getSelectedIndex();
				selected_subject_index = drawing_subject.getSelectedIndex();
				
				//System.out.println(drawing_type_array[selected_type_index]);
				//System.out.println(drawing_subject_array[selected_subject_index]);
				actions.makeArt(selected_type_index, selected_subject_index);
				
				makeArt_popupTable.remove();
				action_popup = !action_popup;
				actions_popupTable.remove();
				updateState();
			}
		});
		// ----- end of makeArt interface -----
		
		buyCoffee.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				action_popup = !action_popup;
				actions_popupTable.remove();
				actions.buyBooster();
				updateState();
			}
		});
		
		float h = action.getHeight() + 20;
		float w = action.getWidth();

		actions_popupTable.add(buyCoffee).width(w).height(h);
		actions_popupTable.row();
		actions_popupTable.add(sleep).width(w).height(h);
		actions_popupTable.row();
		actions_popupTable.add(art).width(w).height(h);
		
		actions_popupTable.setBounds(action.getX(), action.getY() + h + 10, w, 2*h);
		
*/