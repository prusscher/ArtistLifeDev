package edu.sadsnails.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import edu.sadsnails.game.Actions;
import edu.sadsnails.game.GameScreen;
import edu.sadsnails.game.MainMenuScreen;
import edu.sadsnails.game.MyGdxGame;
import edu.sadsnails.game.widgets.PlayerIcon;

public class MainUI {
	private MyGdxGame game;

	// ----- Main UI Elements ----
	// Game UI Labels
	private Label time;
	private Label level;
	private Label xp;
	private Label money;
	private Label energy;
	private Label date;

	// Player Icon
	private PlayerIcon playerIcon;

	// Main UI Action and Pause Button
	private TextButton pause;
	private TextButton action;
	// ----- End of Main UI elements -----

	// ----- Action Menu Elements -----
	private VerticalGroup ActionUI;
	private TextButton artButton;
	private TextButton napButton;
	private TextButton sleepButton;
	private TextButton itemButton;
	private TextButton customizeButton;
	// ----- Art Creation sub-menu
	private VerticalGroup artUI;
	private VerticalGroup customizeUI;
	// CustomizeUI sub-menu
	private HorizontalGroup customize;
	private HorizontalGroup play;
	private VerticalGroup left;
	private VerticalGroup right;
	private PlayerIcon player;
	private TextButton left1;
	private TextButton left2;
	private TextButton left3;
	private TextButton right1;
	private TextButton right2;
	private TextButton right3;
	private TextButton customizeSubmit;
	private SelectBox<String> typeSelBox;
	private SelectBox<String> subjectSelBox;
	private TextButton submitArtButton;
	// ----- end of sub-menu
	// ----- End of Action Menu Elements -----

	// ----- Pause Menu Elements
	private VerticalGroup PauseUI;
	private TextButton settingsButton;
	private TextButton quitToMMButton;
	private TextButton quitToDeskButton;
	// Settings Sub-menu
	private Table settingsTable;
	private Table fsTable;
	private SelectBox<DisplayMode> resolutions;
	private Button fsToggle;
	private Slider master;
	private Slider music;
	private Slider sfx;
	// ----- End Of Action Menu Elements -----

	// UI State Variables
	private boolean popupDisplayed = false;

	private boolean actionMenuDisplayed = false;
	private boolean a_ArtMenuDisplayed = false;
	private boolean a_ItemMenuDisplayed = false;
	private boolean a_CustomMenuDisplayed = false;

	private boolean pauseMenuDisplayed = false;
	private boolean p_SettingsMenuDisplayed = false;

	// Debug Grid Image
	private Texture grid;
	public static Image gridImage;

	private Sound buttonSound;
	private Music gameMusic;

	private Skin skin;
	private Stage stage;

	private GameScreen screen;
	
	// ------------THIS NEEDS TO BE MOVED TO ITS OWN CLASS FOR STORAGE AND CALLS -------------
	protected String [] drawing_type_array
			= {"Pixel", "Sketch", "Abstract", "Surreal", "Realistic", "Painting"};
	protected String [] drawing_subject_array
			= {"Anime", "Retro", "Funny", "Fantasy", "Animal", "Nature", "People"};
	
	public MainUI(MyGdxGame game, GameScreen screen) {
		// Set the references to both the Game and the GameScreen
		this.game = game;
		this.screen = screen;
		
		// Get the UISkin
		skin = game.assets.uiskin;
		
		// Create the stage and set the input processor
		stage = new Stage(new FitViewport(400, 240));
		Gdx.input.setInputProcessor(stage);
		
		// Load and Create the UI
		load();
		createUI(false);
	}
	
	public Stage getStage() {
		return stage;
	}
	
	private void loadPlayerIcons() {
		playerIcon = new PlayerIcon(game);
		playerIcon.setHeight(64);
		playerIcon.setWidth(64);
	}

	private void load() {
		buttonSound = screen.buttonSound;
		gameMusic = screen.gameMusic;

		loadPlayerIcons();
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
				buttonSound.play((game.setting.sfxVol()*(game.setting.masterVol()/100))/100);
				if(!popupDisplayed) {
					popupDisplayed = true;
					actionMenuDisplayed = true;
					ActionUI.setVisible(true);
				} else if(pauseMenuDisplayed || p_SettingsMenuDisplayed) {
					closePopups();
					popupDisplayed = true;
					actionMenuDisplayed = true;
					ActionUI.setVisible(true);
				} else {
					closePopups();
				}
			}
		});

		pause.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				buttonSound.play((game.setting.sfxVol()*(game.setting.masterVol()/100))/100);
				if(!popupDisplayed) {
					popupDisplayed = true;
					pauseMenuDisplayed = true;
					PauseUI.setVisible(true);
				} else if(actionMenuDisplayed || a_ArtMenuDisplayed || a_CustomMenuDisplayed || a_ItemMenuDisplayed) {
					closePopups();
					popupDisplayed = true;
					pauseMenuDisplayed = true;
					PauseUI.setVisible(true);
				} else if(p_SettingsMenuDisplayed) {
					closePopups();
					pause.setText("Pause");
					action.setVisible(true);
				} else {
					closePopups();
				}
			}
		});

		// ----- MAIN UI ELEMENTS -----

		// Upper Left Hand Corner
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
		top.add(playerIcon);
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

		// ----- Popup UI Goes Here -----
		// Popup UI Element creation
		artButton = new TextButton("MAKE ART", skin);
		napButton = new TextButton("NAP", skin);
		sleepButton = new TextButton("SLEEP", skin);
		itemButton = new TextButton("ITEMS", skin);
		customizeButton = new TextButton("CUSTOMIZE", skin);

		typeSelBox = new SelectBox<String>(skin);
		subjectSelBox = new SelectBox<String>(skin);
		submitArtButton = new TextButton("SUBMIT", skin);

		artButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("ArtButton");
				buttonSound.play((game.setting.sfxVol()*(game.setting.masterVol()/100))/100);
				closePopups();
				popupDisplayed = true;
				a_ArtMenuDisplayed = true;
				artUI.setVisible(true);
			}
		});
		napButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("NapButton");
				buttonSound.play((game.setting.sfxVol()*(game.setting.masterVol()/100))/100);
				screen.getActions().sleep(1);
				updateState();
				closePopups();
			}
		});
		sleepButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("sleepButton");
				buttonSound.play((game.setting.sfxVol()*(game.setting.masterVol()/100))/100);
				screen.getActions().sleep(2);
				updateState();
				closePopups();
			}
		});
		itemButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("itemButton");
				buttonSound.play((game.setting.sfxVol()*(game.setting.masterVol()/100))/100);
				closePopups();
			}
		});
		customizeButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("customizeButton");
				buttonSound.play((game.setting.sfxVol()*(game.setting.masterVol()/100))/100);
				closePopups();
				popupDisplayed = true;
				a_CustomMenuDisplayed = true;
				customizeUI.setVisible(true);
			}
		});
		submitArtButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("submitButton: " + typeSelBox.getSelected() + " " + subjectSelBox.getSelected());
				buttonSound.play((game.setting.sfxVol()*(game.setting.masterVol()/100))/100);
				screen.getActions().makeArt(typeSelBox.getSelectedIndex(), subjectSelBox.getSelectedIndex());
				updateState();
				closePopups();
			}
		});

		settingsButton = new TextButton("SETTINGS", skin);
		quitToMMButton = new TextButton("Quit to Main Menu", skin);
		quitToDeskButton = new TextButton("Quit to Desktop", skin);

		settingsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("settingsButton");
				buttonSound.play((game.setting.sfxVol()*(game.setting.masterVol()/100))/100);
				popupDisplayed = true;
				p_SettingsMenuDisplayed = true;
				settingsTable.setVisible(true);
				PauseUI.setVisible(false);
				pause.setText("Exit ");
				action.setVisible(false);
			}
		});
		quitToMMButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Screen thisScreen = game.getScreen();
				buttonSound.play((game.setting.sfxVol()*(game.setting.masterVol()/100))/100);
				game.setScreen(new MainMenuScreen(game));
				thisScreen.dispose();
			}
		});
		quitToDeskButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				buttonSound.play((game.setting.sfxVol()*(game.setting.masterVol()/100))/100);
				game.getScreen().dispose();
				Gdx.app.exit();
			}
		});

		// ACTION MENU
		ActionUI = new VerticalGroup();
		ActionUI.setBounds(138, 22, 120, 120);
		ActionUI.fill();
		ActionUI.bottom();

		ActionUI.addActor(artButton);
		Table sleepTable = new Table();
		sleepTable.setWidth(120);
		sleepTable.add(napButton).height(20).width(60);
		sleepTable.add(sleepButton).height(20).width(60);
		ActionUI.addActor(sleepTable);
		ActionUI.addActor(itemButton);
		ActionUI.addActor(customizeButton);

		stage.addActor(ActionUI);
		ActionUI.setZIndex(3);
		ActionUI.setVisible(false);
		// End of action menu

		// MAKE ART MENU
		artUI = new VerticalGroup();
		artUI.setBounds(138, 22, 120, 120);
		artUI.fill();
		artUI.center();
		artUI.top();
		typeSelBox.setItems(drawing_type_array);
		subjectSelBox.setItems(drawing_subject_array);

		artUI.addActor(new Label("Type", skin));
		artUI.addActor(typeSelBox);
		artUI.addActor(new Label("Subject", skin));
		artUI.addActor(subjectSelBox);
		artUI.addActor(submitArtButton);

		stage.addActor(artUI);
		artUI.setZIndex(3);
		artUI.setVisible(false);
		// end of make art menu

		// MAKE CUSTOMIZE MENU
		customizeUI = new VerticalGroup();
		customizeUI.setBounds(128, 22, 140, 140);
		customizeUI.fill();
		customizeUI.center();
		customizeUI.top();

		// Create buttons
		left1 = new TextButton("<", skin);
		left2 = new TextButton("<", skin);
		left3 = new TextButton("<", skin);
		right1 = new TextButton(">", skin);
		right2 = new TextButton(">", skin);
		right3 = new TextButton(">", skin);
		customizeSubmit = new TextButton("SUBMIT", skin);

		left1.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { player.prevHair(); } });
		left2.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { player.prevFace(); } });
		left3.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { player.prevShirt(); } });
		right1.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { player.nextHair(); } });
		right2.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { player.nextFace(); } });
		right3.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { player.nextShirt(); } });

		customizeSubmit.addListener(new ChangeListener() { @Override public void changed(ChangeEvent event, Actor actor) { playerIcon.setItems(player.getItems()); closePopups();}});

		float buttonW = 30f;
		float bottonH = 20f;

		left1.setWidth(buttonW);
		left1.setHeight(bottonH);
		left2.setWidth(buttonW);
		left2.setHeight(bottonH);
		left3.setWidth(buttonW);
		left3.setHeight(bottonH);
		right1.setWidth(buttonW);
		right1.setHeight(bottonH);
		right2.setWidth(buttonW);
		right2.setHeight(bottonH);
		right3.setWidth(buttonW);
		right3.setHeight(bottonH);
		customizeSubmit.setHeight(bottonH);
		customizeSubmit.setWidth(124);

		// Create the groups
		customize = new HorizontalGroup();
		customize.center();
		customize.setHeight(40);

		customize.addActor(new Label("Customize", skin));

		play = new HorizontalGroup();
		left = new VerticalGroup();
		player = new PlayerIcon(game, playerIcon.getItems());	// Create the Customization PlayerIcon with current settings
		//			player = testImage;

		right = new VerticalGroup();

		play.setWidth(64);
		play.setHeight(64);
		left.setWidth(40);
		left.align(Align.center);
		right.setWidth(40);
		right.align(Align.center);

		// Add the buttons to the left and right containers
		left.addActor(left1);
		left.addActor(left2);
		left.addActor(left3);
		right.addActor(right1);
		right.addActor(right2);
		right.addActor(right3);

		play.addActor(left);
		play.addActor(player);
		play.addActor(right);		

		customizeUI.addActor(customize);
		customizeUI.addActor(play);
		customizeUI.addActor(customizeSubmit);

		stage.addActor(customizeUI);
		customizeUI.setZIndex(3);
		customizeUI.setVisible(false);
		// end of customize menu

		// PAUSE MENU
		PauseUI = new VerticalGroup();
		PauseUI.setBounds(138, 22, 120, 120);
		PauseUI.fill();

		PauseUI.addActor(settingsButton);
		PauseUI.addActor(quitToMMButton);
		PauseUI.addActor(quitToDeskButton);

		stage.addActor(PauseUI);
		PauseUI.setZIndex(3);
		PauseUI.setVisible(pauseMenuDisplayed);

		// Settings Menu
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
				gameMusic.setVolume(((game.setting.musicVol()*(game.setting.masterVol()/100))/100)/100);
				System.out.println((game.setting.musicVol()*(game.setting.masterVol()/100))/100);
			}
		});
		music.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setting.setMusicVol(music.getValue());
				gameMusic.setVolume((game.setting.musicVol()*(game.setting.masterVol()/100))/100);
			}
		});
		sfx.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setting.setSfxVol(sfx.getValue());
			}
		});

		stage.addActor(settingsTable);
		settingsTable.setVisible(p_SettingsMenuDisplayed);
		// end of settings menu items

		// -------- DEV STUFF ---------
		// Rear Grid
		grid = game.assets.manager.get("dev/grid.png");
		gridImage = new Image(grid);
		gridImage.setBounds(0, 0, 400, 240);
		stage.addActor(gridImage);
		gridImage.setZIndex(0);
		gridImage.setVisible(false);
	}

	private void closePopups() {
		popupDisplayed = false;

		actionMenuDisplayed = false;
		a_ArtMenuDisplayed = false;
		a_CustomMenuDisplayed = false;
		a_ItemMenuDisplayed = false;

		pauseMenuDisplayed = false;
		p_SettingsMenuDisplayed = false;

		artUI.setVisible(false);
		customizeUI.setVisible(false);
		settingsTable.setVisible(false);

		ActionUI.setVisible(false);
		PauseUI.setVisible(false);
	}

	private void updateState() {
		time.setText(Integer.toString(screen.getState().getHour()) + ":00");
		level.setText("Level: " + screen.getState().getTitle());
		xp.setText("XP: " + Integer.toString(screen.getState().getXp()));
		money.setText("$" + Double.toString(screen.getState().getMoney()) + "0");
		energy.setText("E: " + Integer.toString(screen.getState().getEnergy()) + "/100");
		int[] curDate = screen.getState().getDate();
		date.setText("" + curDate[2] + "/" + curDate[1] + "/" + curDate[0]);
		//			date.setText("Year " + state.date[0] + " Month " + state.date[1] + " Day " + state.date[2]);

		// Change the player image here
	}
}
