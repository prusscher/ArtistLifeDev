package edu.sadsnails.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.AfterAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
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
	
	private int[] artLoc = {93, 93};
	private int[] sleepLoc = {240, 96};
	
	private Room room;
	
	Pool<MoveToAction> pool = new Pool<MoveToAction>() { protected MoveToAction newObject () { return new MoveToAction(); }};
	
	public Player(MyGdxGame game, Room room) {
		super(game);
		
		this.room = room;
		
		// Load the player animations 
		idle = loadSheet((Texture) this.game.assets.manager.get("images/player/idle.png"), 2, 2, 1, .75f, true);
		lookaround = loadSheet((Texture) this.game.assets.manager.get("images/player/lookaround.png"),4, 4, 1, .75f, true);
		squat = loadSheet((Texture) this.game.assets.manager.get("images/player/squat.png"), 31, 7, 5, .125f, false); 
		walk = loadSheet((Texture) this.game.assets.manager.get("images/player/walk.png"), 4, 4, 1, .2f, true);
		
		//squat.setPlayMode(Animation.PlayMode.NORMAL);
		this.setAnimation(idle);
	}
	
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
	}
	
	/**
	 * ParallelAction that moves the player to a random location and sets the current animation to the walk anim.
	 * @return ParallelAction move to random location
	 */
	private ParallelAction walkAction() {
		MoveToAction m = pool.obtain();
		int[] loc = room.randomLoc();
		m.setPosition(loc[0], loc[1]);
		float time = .1f * (float)dist(getX(), getY(), loc[0], loc[1]);
		m.setDuration(time);
		return parallel(m, run(new Runnable() { public void run () { setAnimation(walk);}}));
	}
	
	/**
	 * ParallelAction that moves the player to a specified location and sets the current animation to the walk anim.
	 * @return ParallelAction move to random location
	 */
	private ParallelAction walkAction(int x, int y) {
		MoveToAction m = pool.obtain();
		m.setPosition(x, y);
		float time = .1f * (float)dist(getX(), getY(), x, y);
		m.setDuration(time);
		return parallel(m, run(new Runnable() { public void run () { setAnimation(walk);}}));
	}
	
	/**
	 * ParallelAction that causes the player to idle for a random amount of time between 2 and 4 seconds
	 * @return ParallelAction idle at the current location of the player
	 */
	private ParallelAction idleAction() {
		MoveToAction m = pool.obtain();
		m.setPosition(getX(), getY());
		m.setDuration(2 + game.rng.nextFloat()*2);
		return parallel(m, run(new Runnable() { public void run () { setAnimation(idle);}}));
	}
	
	/**
	 * ParallelAction that causes the player to look around for a random amount of time between 1 and 2 seconds
	 * @return ParallelAction look around at the current location of the player
	 */
	private ParallelAction lookAction() {
		MoveToAction m = pool.obtain();
		m.setPosition(getX(), getY());
		m.setDuration(1+ game.rng.nextFloat()*1);
		return parallel(m, run(new Runnable() { public void run () { setAnimation(lookaround);}}));
	}
	
	/**
	 * ParallelAction that causes the player to squat at their current location
	 * @return ParallelAction squat at the current location
	 */
	private ParallelAction squatAction() {
		MoveToAction m = pool.obtain();
		m.setPosition(getX(), getY());
		m.setDuration(squat.getAnimationDuration());
		return parallel(m, run(new Runnable() { public void run () { setAnimation(squat);}}));
	}
	
	/**
	 * ParallelAction that causes the player to squat at a specified location
	 * @return ParallelAction squat at the specified location
	 */
	private ParallelAction squatAction(int x, int y) {
		MoveToAction m = pool.obtain();
		m.setPosition(x, y);
		m.setDuration(squat.getAnimationDuration());
		return parallel(m, run(new Runnable() { public void run () { setAnimation(squat);}}));
	}
	
	/**
	 * ParallelAction that causes the player to squat at a specified location
	 * @return ParallelAction squat at the specified location
	 */
	private ParallelAction sleepAction(int x, int y, float duration, final int type, final GameScreen screen) {
		MoveToAction m = pool.obtain();
		m.setPosition(x, y);
		m.setDuration(duration);
		return parallel(m, run(new Runnable() { 
			public void run () { 
				screen.getActions().sleep(type); 
				screen.getUI().updateState(); 
				if(type == 2) 
					screen.dimForSleep(); 
				else 
					screen.dimForNap(); 
				}
			}));
	}
	
	/**
	 * Get the distance between two points
	 * @param x1 x of the first point
	 * @param y1 y of the first point
	 * @param x2 x of the second point
	 * @param y2 y of the second point
	 * @return The distance between the two points
	 */
	private double dist(float x1, float y1, float x2, float y2) { return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)); }
	
	/**
	 * Call to have the player move to the art location, make art, perform the art Action, and move back to a location in the room
	 * @param type the type of art to be made
	 * @param subject the subject of the art to be made
	 */
	public void makeArt(final int type, final int subject) {
		// Clear all the players current actions
		clearActions();
		
		// Get a reference to the current GameScreen
		final GameScreen screen = (GameScreen) game.getScreen();

		// Add the action to walk to the art location, make art, call the makeArt Action, and return to the room
		addAction(sequence(walkAction(artLoc[0], artLoc[1]), squatAction(artLoc[0], artLoc[1]), run(new Runnable() { 
			public void run () { 
				screen.getActions().makeArt(type, subject); 
				screen.getUI().updateState();
			}}), 
			walkAction()));
	}
	
	/**
	 * Call to have the player sleep for the specified type of sleep
	 * @param type the type of sleep the player should perform
	 */
	public void sleep(final int type) {
		// Clear all the players current actions
		clearActions();
		
		// Get a reference to the current GameScreen
		final GameScreen screen = (GameScreen) game.getScreen();
		
		// Add the action to walk to the sleep location, sleep, and call the sleep Action
		addAction(sequence(walkAction(sleepLoc[0], sleepLoc[1]), sleepAction(sleepLoc[0], sleepLoc[1], 16, type, screen), walkAction()));	
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
