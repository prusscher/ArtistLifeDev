package edu.sadsnails.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.AfterAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.utils.Pool;

import edu.sadsnails.game.GameScreen;
import edu.sadsnails.game.MyGdxGame;

public class Player extends BaseActor {
	
	private Animation<TextureRegion> idle;
	private Animation<TextureRegion> lookaround;
	private Animation<TextureRegion> squat;
	private Animation<TextureRegion> walk;
	private Animation<TextureRegion> drawing;
	
	private Room room;
	
	Pool<MoveToAction> pool = new Pool<MoveToAction>() { protected MoveToAction newObject () { return new MoveToAction(); }};
	
	public Player(MyGdxGame game, Room room) {
		super(game);
		
		this.room = room;
		
		// Load the player animations 
		idle = loadSheet((Texture) this.game.assets.manager.get("images/player/idle.png"), 2, 2, 1, .75f, true);
		lookaround = loadSheet((Texture) this.game.assets.manager.get("images/player/lookaround.png"),4, 4, 1, .75f, true);
		squat = loadSheet((Texture) this.game.assets.manager.get("images/player/squat.png"), 31, 7, 5, .125f, true); 
		walk = loadSheet((Texture) this.game.assets.manager.get("images/player/walk.png"), 4, 4, 1, .2f, true);
		
		squat.setPlayMode(Animation.PlayMode.NORMAL);
		
		System.out.println(squat.getAnimationDuration());
	}
	
	private float totalDelta = 0;
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(!hasActions()) {
			int a = game.rng.nextInt(100);
			
			System.out.println("Player Action: " + a);
			
			if(a <= 40) { // MOVE TO RANDOM LOCAITON
				addAction(walkAction());
			} else if(a > 40 && a <= 80) { // GO IDLE
				if(a <= 60) { // Look around animation, <1 second of lookin around
					addAction(lookAction());
				} else { // Idle animation <2 second of just standn
					addAction(idleAction());
				}
			} else if(a > 80) { // Chance to do SQUATS
				addAction(squatAction());
			} else { // All others, just here for now
				addAction(idleAction());
			}
		} 
		totalDelta += delta;
		if(totalDelta > 1f) {
			System.out.println(animation.getAnimationDuration());
			totalDelta = 0;
		}
	}
	
	private MoveToAction walkAction() {
		MoveToAction m = pool.obtain();
		int[] loc = room.randomLoc();
		m.setPosition(loc[0], loc[1]);
		float time = .1f * (float)dist(getX(), getY(), loc[0], loc[1]);
		m.setDuration(time);
		animation = walk;
		return m;
	}
	
	private MoveToAction idleAction() {
		MoveToAction m = pool.obtain();
		m.setPosition(getX(), getY());
		m.setDuration(game.rng.nextFloat()*4);
		animation = idle;
		return m;
	}
	
	private MoveToAction lookAction() {
		MoveToAction m = pool.obtain();
		m.setPosition(getX(), getY());
		m.setDuration(game.rng.nextFloat()*2);
		animation = lookaround;
		return m;
	}
	
	private MoveToAction squatAction() {
		MoveToAction m = new MoveToAction();
		m.setPosition(getX(), getY());
		m.setDuration(squat.getAnimationDuration());
		animation = squat;
		return m;
	}
	
	private MoveToAction artAction() {
		MoveToAction m = new MoveToAction();
		m.setPosition(93, 93);
		m.setDuration(.05f * (float)dist(getX(), getY(), 93, 93));
		//m.setDuration(2f);
		animation = walk;
		return m;
	}
	
	private double dist(float x1, float y1, float x2, float y2) { return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)); }
	
	public void makeArt(final int type, final int subject) {
		clearActions();
		
		//RunnableAction r = new RunnableAction();
		final GameScreen screen = (GameScreen) game.getScreen();
		//r.setRunnable(new Runnable() { @Override public void run () {screen.getActions().makeArt(type, subject); }});
		
		addAction(sequence(artAction(), squatAction(), run(new Runnable() { 
			public void run () { 
				screen.getActions().makeArt(type, subject); 
				screen.getUI().updateState();
			}}), 
			walkAction()));
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
