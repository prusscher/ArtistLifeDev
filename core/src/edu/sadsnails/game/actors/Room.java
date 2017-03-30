package edu.sadsnails.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.sadsnails.game.MyGdxGame;

public class Room extends BaseActor{

	public Room(MyGdxGame game) {
		super(game);
	
		TextureRegion colorFrames = new TextureRegion((Texture) this.game.assets.manager.get("images/rooms/room1.png"));
		
		Animation<TextureRegion> color = new Animation<TextureRegion>(.25f, colorFrames);
		color.setPlayMode(Animation.PlayMode.NORMAL);
		
		animation = color;
	
	}

}
