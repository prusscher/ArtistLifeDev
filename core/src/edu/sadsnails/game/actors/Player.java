package edu.sadsnails.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.sadsnails.game.MyGdxGame;

public class Player extends BaseActor {

	public Player(MyGdxGame game) {
		super(game);
		
		TextureRegion colorFrames = new TextureRegion((Texture) this.game.assets.manager.get("images/player/man.png"));
		
		Animation<TextureRegion> color = new Animation<TextureRegion>(.25f, colorFrames);
		color.setPlayMode(Animation.PlayMode.NORMAL);
		
		animation = color;
	}

}
