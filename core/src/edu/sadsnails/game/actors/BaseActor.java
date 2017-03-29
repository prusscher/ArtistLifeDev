package edu.sadsnails.game.actors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import edu.sadsnails.game.MyGdxGame;

public class BaseActor extends Actor {
	TextureRegion region;
	
	public BaseActor(MyGdxGame game) {
		region = new TextureRegion((Texture) game.assets.manager.get("images/rooms/room1.png"));
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a*parentAlpha);
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), 
				getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
	
}
