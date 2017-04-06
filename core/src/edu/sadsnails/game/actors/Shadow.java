package edu.sadsnails.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.sadsnails.game.MyGdxGame;

public class Shadow extends BaseActor{
	
	private Player player;
	
	public Shadow(MyGdxGame game, Player player) {
		super(game);
	
		TextureRegion colorFrames = new TextureRegion((Texture) this.game.assets.manager.get("images/player/playerShadow.png"));
		
		Animation<TextureRegion> color = new Animation<TextureRegion>(.25f, colorFrames);
		color.setPlayMode(Animation.PlayMode.NORMAL);
		
		animation = color;
		this.player = player;
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		this.setX(player.getX());
		this.setY(player.getY());
	}
}
