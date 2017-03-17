package edu.sadsnails.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
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
	
	private boolean settingsVisible = false;
	private Table settingsTable;
	private Table fsTable;
	private SelectBox<DisplayMode> resolutions;
	private Button fsToggle;
	private Slider master;
	private Slider music;
	private Slider sfx;
	
	Animation<TextureRegion> walkAnimation;
    Texture walkSheet;
    
    private Image water;
    
    // A variable for tracking elapsed time for the animation
    float stateTime;
    
	private Stage stage;
	private FPSLogger fps;
	
	public MainMenuScreen(final MyGdxGame game) {
		stage = new Stage(new FitViewport(400, 240));
		Gdx.input.setInputProcessor(stage);
		
		this.game = game;
		
		fps = new FPSLogger();
		
		// FREE TYPE FONT TEST
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 8;
		parameter.shadowColor = Color.BLACK;
		parameter.shadowOffsetX = 1;
		parameter.shadowOffsetY = 1;
		BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		// Done
		skin = new Skin(Gdx.files.internal("uiskin.json"));
//		skin.add("default-font", new BitmapFont(Gdx.files.internal("fonts/nes_small.fnt")));
		skin.add("default-font", font12);
		
		// Create TextButtons
		TextButtonStyle style = skin.get("default", TextButtonStyle.class);
//		style.font = font12;
		LabelStyle labelstyle = skin.get("default", LabelStyle.class);
		labelstyle.font = font12;
		
		startGame = new TextButton("START GAME", style);
		settings = new TextButton("SETTINGS", style);
		title = new Label("ARTIST LIFE", labelstyle);
		title.setFontScale(3);
		
		// Listeners
		startGame.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) { changeView(new GameScreen(game)); }
		});
		
		settings.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				settingsVisible = !settingsVisible;
				settingsTable.setVisible(settingsVisible);
				title.setVisible(!settingsVisible);
				startGame.setVisible(!settingsVisible);
			}
		});
		
		// Setup Button Containers and Add to Screen
		startContainer = new Container<TextButton>(startGame);
		settingsContainer = new Container<TextButton>(settings);
		titleContainer = new Container<Label>(title);
		
		// Set the start
		startContainer.setFillParent(true);
		settingsContainer.setFillParent(true);
		titleContainer.setBounds(stage.getWidth()/2 - title.getWidth()/2, 140, title.getWidth(), title.getHeight());

		// Center the start button and set the settings button to the lower left
		startContainer.center();
		settingsContainer.padLeft(2).left().padBottom(2).bottom();
		
		startContainer.setZIndex(2);
		titleContainer.setZIndex(2);
		settingsContainer.setZIndex(2);
		
		// Add the containers to the scene
		stage.addActor(startContainer);
		stage.addActor(settingsContainer);
		stage.addActor(titleContainer);		
		
		// -----------SETTINGS MENU
		
		// Create the table buttons and sliders and such
		DisplayMode[] modes = game.setting.getModes();
		
		resolutions = new SelectBox<DisplayMode>(skin);
		resolutions.setItems(modes);
		resolutions.setSelectedIndex(0);
		
		fsToggle = new Button(skin);
		
		master = new Slider(0f, 100f, .5f, false, skin);
		music = new Slider(0f, 100f, .5f, false, skin);
		sfx = new Slider(0f, 100f, .5f, false, skin);
		
		master.setWidth(180);
		music.setWidth(180);
		sfx.setWidth(180);
		
		master.setValue(game.setting.masterVol());
		music.setValue(game.setting.musicVol());
		sfx.setValue(game.setting.sfxVol());
		
		// ----- Create the settings table -----
		settingsTable = new Table();
		settingsTable.add(new Label("Resolution", skin)).height(20);
		settingsTable.row();
		settingsTable.add(resolutions).height(20);
		settingsTable.row();
		
		fsTable = new Table();
		fsTable.add(new Label("Fullscreen ", skin)).height(20);
		fsTable.add(fsToggle).width(20); // Fullscreen Toggle
		
		settingsTable.add(fsTable);
		settingsTable.row();
		settingsTable.add(new Label("Master Volume", skin)).height(20);
		settingsTable.row();
		settingsTable.add(master).height(20);
		settingsTable.row();
		settingsTable.add(new Label("Music Volume", skin)).height(20);
		settingsTable.row();
		settingsTable.add(music).height(20);
		settingsTable.row();
		settingsTable.add(new Label("Sfx Volume", skin)).height(20);
		settingsTable.row();
		settingsTable.add(sfx).height(20);
		
		settingsTable.setBounds(100, 27, 200, 180);
		// ----- complete creating table -----

		// Slider Listeners and Button Listeners
		resolutions.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				DisplayMode mode = resolutions.getSelected();
				game.setting.setWindowMode(mode);
			}
		});
		fsToggle.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Fullscreen Toggle: " + fsToggle.isChecked());
			}
		});
		master.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setting.setMasterVol(master.getValue());
			}
		});
		music.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setting.setMusicVol(music.getValue());
			}
		});
		sfx.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setting.setSfxVol(sfx.getValue());
			}
		});
		
		stage.addActor(settingsTable);
		settingsTable.setVisible(settingsVisible);
		
		stage.setDebugAll(false);
		
		int FRAME_COLS = 6, FRAME_ROWS = 10;
		
		walkSheet = new Texture(Gdx.files.internal("images/mainMenu/mainmenuwater_sheet.png"));
		
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, 
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
		
        walkAnimation = new Animation<TextureRegion>(0.100f, walkFrames);
	
        water = new Image(walkAnimation.getKeyFrame(stateTime, true));
        water.setBounds(0, 0, 400, 240);
        water.setZIndex(0);
        
        stage.addActor(water);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
//		fps.log();
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        water.setDrawable(new SpriteDrawable(new Sprite(currentFrame)));
        water.toBack();
        
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
        walkSheet.dispose();
	}
}