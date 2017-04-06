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
	
	/**
	 * Get random location inside of the room
	 * Basically, just get a random x and y in the specified range.
	 * if it isnt in the cookie cutter area of ok, get another x and y
	 * @return
	 */
	public int[] randomLoc() {
		int[] out = new int[2];
		
		int xMin = 110;
		int xMax = 285;
		int yMin = 55;
		int yMax = 130;
		
		int width = xMax - xMin;
		int height = yMax - yMin;
		
		boolean valid = true;
		
		int x = 0;
		int y = 0;
		do {
			x = game.rng.nextInt(width);
			y = game.rng.nextInt(height)-(height/2);
			
			int tempX = x;
			int tempY = y;
			
			double slope = 75.0/175.0;
			
			// Mirror leftno
			if(tempX > width/2)
				tempX = (width/2) - (tempX-(width/2));
			if(tempY < 0)
				tempY = -tempY;
				
			//System.out.println("\t" + x + " " + y + "\t" + tempX + " " + tempY);
			
			// Translate d o w n
			//tempY -= (height/2);
			
			if(tempY < ((int)(slope*tempX)))
				valid = false;
			else
				valid = true;
			
		} while(valid);
		
		out[0] = x+xMin;
		out[1] = y+yMin+(height/2);
		return out;
	}
	
}
