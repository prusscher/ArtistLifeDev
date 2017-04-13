package edu.sadsnails.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.sadsnails.game.MyGdxGame;

public class Desk extends BaseActor{

	int curDesk = 0;
	
	private TextureRegion[] desks = new TextureRegion[2];
	
	public Desk(MyGdxGame game) {
		super(game);
		
		Texture tempTex = (Texture) this.game.assets.manager.get("images/items/desk.png");
		TextureRegion[][] tmp = TextureRegion.split(tempTex, tempTex.getWidth()/2, tempTex.getHeight());
		
		desks[0] = tmp[0][0];
		desks[1] = tmp[0][1];
		
		region = desks[curDesk];
		//animation = color;
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		//super.act(delta);
	
		// Override BaseActors act so animations dont play d o g
	}
	
	public void nextDesk() {
		curDesk++;
		
		if(curDesk > desks.length-1)
			curDesk = 0;
		
		region = desks[curDesk];
	}
}
