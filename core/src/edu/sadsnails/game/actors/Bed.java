package edu.sadsnails.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.sadsnails.game.MyGdxGame;

public class Bed extends BaseActor{

	int curBed = 0;
	
	private TextureRegion[] beds = new TextureRegion[3];
	
	public Bed(MyGdxGame game) {
		super(game);
	
		Texture tempTex = (Texture) this.game.assets.manager.get("images/items/bed.png");
		TextureRegion[][] tmp = TextureRegion.split(tempTex, tempTex.getWidth()/3, tempTex.getHeight());
		
		beds[0] = tmp[0][0];
		beds[1] = tmp[0][1];
		beds[2] = tmp[0][2];
		
		region = beds[curBed];
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		//super.act(delta);
	
		// Override BaseActors act so animations dont play d o g
	}
	
	public void nextBed() {
		curBed++;
		
		if(curBed > beds.length-1)
			curBed = 0;
		
		region = beds[curBed];
	}
	
	public int getBedIndex() { return curBed; }
	
	public void setBed(int num) {
		if(num > beds.length-1)
			curBed = 0;
		else
			curBed = num;
		
		region = beds[curBed];
	}
}
