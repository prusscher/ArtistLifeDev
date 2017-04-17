package edu.sadsnails.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public AssetManager manager = new AssetManager();
	public Skin uiskin = new Skin(Gdx.files.internal("uiskin.json"));
	//public static final AssetDescriptor<Skin> uiSkin = new AssetDescriptor<Skin>("uiskin.json", Skin.class);
	
	public AssetManager m() {
		return manager;
	}
	
	public void load()
	{
		// Textures
		manager.load("dev/grid.png", Texture.class);
		manager.load("dev/BaseActor.png", Texture.class);
		manager.load("dev/ColorAnim.png", Texture.class);
		manager.load("dev/background.png", Texture.class);
		
		manager.load("images/mainMenu/mainmenuwater_sheet.png", Texture.class);
		
		manager.load("images/player/man.png", Texture.class);
		manager.load("images/player/idle.png", Texture.class);
		manager.load("images/player/lookaround.png", Texture.class);
		manager.load("images/player/squat.png", Texture.class);
		manager.load("images/player/walk.png", Texture.class);
		manager.load("images/player/playerShadow.png", Texture.class);
		manager.load("images/player/sleep.png", Texture.class);
		manager.load("images/player/draw.png", Texture.class);
		
		manager.load("images/playerIcon/testman.png", Texture.class);
		manager.load("images/playerIcon/base.png", Texture.class);
		manager.load("images/playerIcon/faces.png", Texture.class);
		manager.load("images/playerIcon/hair.png", Texture.class);
		manager.load("images/playerIcon/shirts.png", Texture.class);

		manager.load("images/rooms/room.png", Texture.class);	
		manager.load("images/rooms/roomIcons.png", Texture.class);	
		
		manager.load("images/items/bed.png", Texture.class);
		manager.load("images/items/desk.png", Texture.class);
		
		// Sounds
		manager.load("sound/button.wav", Sound.class);
		
		// Music
		manager.load("music/DreamCulture.mp3", Music.class);
		manager.load("music/Eternity.mp3", Music.class);
	}
	
	public void dispose()
	{
	    manager.dispose();
	}
}