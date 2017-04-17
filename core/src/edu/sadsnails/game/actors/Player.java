package edu.sadsnails.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.AfterAction;
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
	private Animation<TextureRegion> sleep;
	private Animation<TextureRegion> drawing;
	
	private int[] artLoc = {210, 110};
	private int[] sleepLoc = {130, 105}; // 145, 105
	
	private Room room;
	
	Pool<MoveToAction> pool = new Pool<MoveToAction>() { protected MoveToAction newObject () { return new MoveToAction(); }};
	
	public Player(MyGdxGame game, Room room) {
		super(game);
		
		this.room = room;
		
		// Load the player animations 
		idle 		= loadSheet((Texture) this.game.assets.manager.get("images/player/idle.png"), 2, 2, 1, .75f, true);
		lookaround 	= loadSheet((Texture) this.game.assets.manager.get("images/player/lookaround.png"),4, 4, 1, .75f, true);
		squat 		= loadSheet((Texture) this.game.assets.manager.get("images/player/squat.png"), 31, 7, 5, .125f, true); 
		walk 		= loadSheet((Texture) this.game.assets.manager.get("images/player/walk.png"), 4, 4, 1, .2f, true);
		sleep 		= loadSheet((Texture) this.game.assets.manager.get("images/player/sleep.png"), 2, 2, 1, .75f, true);
		drawing 	= loadSheet((Texture) this.game.assets.manager.get("images/player/draw.png"), 2, 2, 1, .25f, true);
		
		//squat.setPlayMode(Animation.PlayMode.NORMAL);
		this.setAnimation(idle);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(!hasActions()) {
			int a = game.rng.nextInt(100);
			
			/*
			 * Animation Selections B O I 
			 * 0 - 40	:	Walk to random location
			 * 40 - 60	:	(Idle) Look around
			 * 60 - 80	:	(Idle) Idle Stand
			 * 80 - 95	:	Idle for now, This is handled by the else
			 * 95 - 100	:	Squats
			 */
			
			if(a <= 40) { // MOVE TO RANDOM LOCAITON
				addAction(walkAction());
			} else if(a > 40 && a <= 80) { // GO IDLE
				if(a <= 60) { // Look around animation, <1 second of lookin around
					addAction(lookAction());
				} else { // Idle animation <2 second of just standn
					addAction(idleAction());
				}
			} else if(a > 95) { // Chance to do SQUATS
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
		int[] loc = room.randomLoc();
		return walkAction(loc[0], loc[1]);
	}
	
	/**
	 * Walk to action but moves from the current location to the x,y specified
	 */
	private ParallelAction walkAction(int x, int y) {
		int[] loc = room.randomLoc();
		return walkAction(getX(), getY(), x, y);
	}
	
	/**
	 * Walk but specify the starting location instead of the ending location
	 */
	private ParallelAction walkAction(float x, float y) {
		int[] loc = room.randomLoc();
		return walkAction(x, y, loc[0], loc[1]);
	}
	
	/**
	 * ParallelAction that moves the player to a specified location and sets the current animation to the walk anim.
	 * @return ParallelAction move to random location
	 */
	private ParallelAction walkAction(float startX, float startY, int x, int y) {
		MoveToAction m = pool.obtain();
		m.setPosition(x, y);
		float time = .1f * (float)dist(startX, startY, x, y);
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
		return squatAction(getX(), getY());
	}
	
	/**
	 * ParallelAction that causes the player to squat at a specified location
	 * @return ParallelAction squat at the specified location
	 */
	private ParallelAction squatAction(float x, float y) {
		MoveToAction m = pool.obtain();
		m.setPosition(x, y);
		m.setDuration(squat.getAnimationDuration());
		return parallel(m, run(new Runnable() { public void run () { setAnimation(squat);}}));
	}
	
	private SequenceAction artAction(final int type, final int subject) {
		final GameScreen screen = (GameScreen) game.getScreen();
		
		MoveToAction m = pool.obtain();
		m.setPosition(artLoc[0], artLoc[1]);
		
		// Set the time of the art making to the appropriate length
		m.setDuration(screen.getActions().getTime(type)*squat.getAnimationDuration());	
		
		return sequence(
					parallel(m, run(new Runnable() { 
						public void run () { 
							setAnimation(drawing);
						}})), 
					run(new Runnable() { 
						public void run () { 
							screen.getActions().makeArt(type, subject); 
							screen.getUI().updateState();
						}})
					);
	}
	
	/**
	 * ParallelAction that causes the player to squat at a specified location
	 * @return ParallelAction squat at the specified location
	 */
	private SequenceAction sleepAction(int x, int y, float duration, final int type, final GameScreen screen) {
		MoveToAction m = pool.obtain();
		m.setPosition(x, y);
		m.setDuration(duration);
		
		Runnable callToSleep = new Runnable() { 
			public void run () { 
				screen.getActions().sleep(type); 
				if(type == 2) 
					screen.dimForSleep(); 
				else 
					screen.dimForNap(); 
				setAnimation(sleep);
			}
		};
		
		Runnable updateStats = new Runnable() { 
			public void run() { 
				screen.getUI().updateState(); 
			}};
			
		return sequence(parallel(m, run(callToSleep)), run(updateStats));
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
		//clearActions();
		
		// Get a reference to the current GameScreen
		final GameScreen screen = (GameScreen) game.getScreen();

		// Add the action to walk to the art location, make art, call the makeArt Action, and return to the room
		addAction(after(sequence(walkAction(artLoc[0], artLoc[1]), artAction(type, subject), walkAction((float) artLoc[1], (float) artLoc[1]))));
	}
	
	/**
	 * Call to have the player sleep for the specified type of sleep
	 * @param type the type of sleep the player should perform
	 */
	public void sleep(final int type) {
		// Clear all the players current actions
		// clearActions();
		
		// Get a reference to the current GameScreen
		final GameScreen screen = (GameScreen) game.getScreen();
		
		// Add the action to walk to the sleep location, sleep, and call the sleep Action
		addAction(after(sequence(walkAction(sleepLoc[0], sleepLoc[1]), sleepAction(sleepLoc[0], sleepLoc[1], 16, type, screen),  walkAction((float) sleepLoc[1], (float) sleepLoc[1]))));	
	}
}
