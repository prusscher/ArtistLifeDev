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

public class GameScreen implements Screen 	{
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
	private Container<Label> datecontainer;
	private Label date;
	
	private Image testImage;
	private TextButton pause;
	private TextButton action;
//	private TextButton statistics;
	
	private Texture grid;
	private Image gridImage;
	
	private Table actions_popupTable;
	private TextButton sleep;
	private TextButton art;
	private TextButton buyCoffee;
	
	private Table stats_popupTable;

	// MEME
	private Table makeArt_popupTable;
	protected SelectBox drawing_type;
	protected SelectBox drawing_subject;
	
	// ------------THIS NEEDS TO BE MOVED TO ITS OWN CLASS FOR STORAGE AND CALLS -------------
	protected String [] drawing_type_array
			= {"Pixel", "Sketch", "Abstract", "Surreal", "Realistic", "Painting"};
	protected String [] drawing_subject_array
			= {"Anime", "Retro", "Funny", "Fantasy", "Animal", "Nature", "People"};
	
	private TextButton submitArt;
	private int selected_type_index;	// These are actually useless
	private int selected_subject_index;	// These are actually useless
	
	private Table sleep_popupTable;
	private TextButton sleep1; 	// sleep all night
	private TextButton sleep2;	// sleep for 12 hours
	
	private Texture scribbler3;
	private Texture scribbler2;
	private Texture scribbler1;
	private Texture testPlayerIcon;
	
	private boolean action_popup 	= true;
	private boolean stats_popup 	= true;
	private boolean sleep_popup		= true;
	private boolean art_popup		= true;
	
	private boolean debug = true;
	
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
		scribbler3 = new Texture(Gdx.files.internal("images/playerIcon/scribbler3.gif"));
		scribbler2 = new Texture(Gdx.files.internal("images/playerIcon/scribbler2.gif"));
		scribbler1 = new Texture(Gdx.files.internal("images/playerIcon/scribbler.gif"));
		testPlayerIcon = new Texture(Gdx.files.internal("images/playerIcon/testman.png"));
	}
	
	private void createUI(boolean debug) {
		
		if(debug) {
			stage.setDebugAll(true);
			
			// Create and add the Debug Grid to the stage
			grid = new Texture(Gdx.files.internal("dev/grid.png"));
			gridImage = new Image(grid);
			gridImage.setBounds(0, 0, 400, 240);
			stage.addActor(gridImage);
			gridImage.setZIndex(0);
		}
		
		// Create the Screen Buttons
		action 		= new TextButton("Action", skin);
		pause 		= new TextButton("Pause", skin);
		
		action.setBounds((stage.getWidth() - 120) / 2, 2, 120, 20);
		pause.setBounds(stage.getWidth() - 52, 2, 50, 20);
		
		// Button Listeners
		action.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(action_popup)
					stage.addActor(actions_popupTable);
				else
					actions_popupTable.remove();
				
				sleep_popupTable.remove();
				makeArt_popupTable.remove();

				action_popup = !action_popup;
			}
		});
		
		// ----- create makeArt user interface -----
		
		makeArt_popupTable = new Table();
		
		drawing_type = new SelectBox(skin);
		drawing_subject = new SelectBox(skin);
		
		pause.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
			}
		});
		
		// ----- MAIN UI ELEMENTS -----
		
		// Upper Left Hand Corner
		testImage = new Image(testPlayerIcon);
		testImage.setBounds(0, stage.getHeight()-64, 64, 64);
		
		xp = new Label("",skin);
		exp = new Container<Label>(xp);
		exp.align(Align.left);
		exp.padLeft(2f);
		exp.setBounds(64, stage.getHeight() - 20, 100, 20);
		
		level = new Label("",skin);
		rank = new Container<Label>(level);
		rank.align(Align.left);
		rank.padLeft(2f);
		rank.setBounds(64, stage.getHeight() - 40, 100, 20);
		
		// Lower Left Hand Corner
		time = new Label("",skin);
		clock = new Container<Label>(time);
		clock.align(Align.left);
		clock.padLeft(2f);
		clock.setBounds(0, 0, 100, 20);
		
		date = new Label("",skin);					
		datecontainer = new Container<Label>(date); 
		datecontainer.align(Align.left);
		datecontainer.padLeft(2f);
		datecontainer.setBounds(0, 20, 100, 20);		
		
		// Upper Right Hand Corner
		amount = new Label("",skin);
		money = new Container<Label>(amount);
		money.align(Align.right);
		money.padRight(2f);
		money.setBounds(stage.getWidth()-100, stage.getHeight() - 20, 100, 20);
		
		energybar = new Label("",skin);
		energy = new Container<Label>(energybar);
		energy.align(Align.right);
		energy.padRight(2f);
		energy.setBounds(stage.getWidth()-100, stage.getHeight() - 40, 100, 20);


		
		// ----- Create sleep options popup UI -----
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
		stage.addActor(datecontainer);
//		stage.addActor(statistics);
		
		clock.setZIndex(2);
		exp.setZIndex(2);
		energy.setZIndex(2);
		money.setZIndex(2);
		rank.setZIndex(2);
		testImage.setZIndex(2);
		action.setZIndex(2);
		pause.setZIndex(2);
		datecontainer.setZIndex(2);
	}
	
	private void updateState() {
		time.setText(Integer.toString(state.hour) + ":00");
		level.setText("Level: " + state.title);
		xp.setText("XP: " + Integer.toString(state.xp));
		amount.setText("$" + Double.toString(state.money) + "0");
		energybar.setText("E: " + Integer.toString(state.energy) + "/100");
		date.setText("Year " + state.date[0] + " Month " + state.date[1] + " Day " + state.date[2]);

		// **************************BIG ISSUE******************************88
		// DONT ADD THE IMAGE AGAIN AND AGAIN, JUST RESET THE WIDGET'S IMAGE
		// TACTICAL MEMORY LEAK INCOMING.
		// Im not changing this right now... Its late
//		if((state.energy <= 50) && (state.energy > 20)) {
//			testImage = new Image(scribbler2);
//			testImage.setBounds(0, Gdx.graphics.getHeight() - 100, 100, 100);
//			stage.addActor(testImage);
//		}
//		else if(state.energy <= 20) {
//			testImage = new Image(scribbler3);
//			testImage.setBounds(0, Gdx.graphics.getHeight() - 100, 100, 100);
//			stage.addActor(testImage);
//		}
//		else if(state.energy > 50) {
//			testImage = new Image(scribbler1);
//			testImage.setBounds(0, Gdx.graphics.getHeight() - 100, 100, 100);
//			stage.addActor(testImage);
//		}
		
	
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
		if(Gdx.input.isKeyPressed(Keys.SPACE) && Gdx.input.isKeyJustPressed(Keys.SPACE)){
			System.out.println(stage.getActors().size);
			for(Actor a : stage.getActors()) {
				System.out.println(a.getClass().getName());
			}
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