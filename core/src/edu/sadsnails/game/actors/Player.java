package edu.sadsnails.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import edu.sadsnails.game.MyGdxGame;

public class Player extends BaseActor {
	
	private Animation<TextureRegion> idle;
	private Animation<TextureRegion> lookaround;
	private Animation<TextureRegion> squat;
	private Animation<TextureRegion> walk;
	private Animation<TextureRegion> drawing;
	
	public Player(MyGdxGame game) {
		super(game);
		
		// Load the player animations 
		idle = loadSheet((Texture) this.game.assets.manager.get("images/player/idle.png"), 2, 2, 1, .75f, true);
		lookaround = loadSheet((Texture) this.game.assets.manager.get("images/player/lookaround.png"),4, 4, 1, .75f, true);
		squat = loadSheet((Texture) this.game.assets.manager.get("images/player/squat.png"), 31, 7, 5, .125f, false); 
		walk = loadSheet((Texture) this.game.assets.manager.get("images/player/walk.png"), 4, 4, 1, .5f, true);
		
		animation = idle;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		
	}
	
	public void makeArt() {
		SequenceAction makeArt = new SequenceAction();
		MoveToAction m = new MoveToAction();
		
	}
	
	private Animation<TextureRegion> loadSheet(Texture texToAnim, int numFrames, int width, int height, float time, boolean loop) {
		TextureRegion[][] tmp = TextureRegion.split(texToAnim, texToAnim.getWidth()/width, texToAnim.getHeight()/height);
		
		System.out.println(numFrames + " " + width + "x" + height + " " + tmp.length + " " +tmp[0].length);
		
		TextureRegion[] frames = new TextureRegion[numFrames];
		int frameCount = 0;
		
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				if(frameCount < numFrames)
					frames[frameCount++] = tmp[y][x];
				else
					break;
		Animation<TextureRegion> out = new Animation<TextureRegion>(time, frames);
		if(loop)	
			out.setPlayMode(Animation.PlayMode.LOOP);
		
		return out;
	}
}
