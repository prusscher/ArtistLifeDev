package edu.sadsnails.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.sadsnails.game.MyGdxGame;

public class Player extends BaseActor {
	
	private Animation<TextureRegion> idle;
	private Animation<TextureRegion> drawing;
	private Animation<TextureRegion> walking;
	
	public Player(MyGdxGame game) {
		super(game);
		
		Texture texToAnim = this.game.assets.manager.get("images/player/idle.png");
		
		TextureRegion[][] tmp = TextureRegion.split(texToAnim, texToAnim.getWidth()/2, texToAnim.getHeight());
		
		System.out.println(tmp.length);
		
		TextureRegion[] frames = new TextureRegion[2];
		for(int y = 0; y < 2; y++)
			frames[y] = tmp[0][y];
		
		idle = new Animation<TextureRegion>(.75f, frames);
		idle.setPlayMode(Animation.PlayMode.LOOP);
		
		animation = idle;
	
	
	}
	
	public void makeArt() {
		//animation = drawing;
	}
	
}
