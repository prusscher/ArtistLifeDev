package edu.sadsnails.game.actors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import edu.sadsnails.game.MyGdxGame;

public class BaseActor extends Actor {
	protected TextureRegion region;
	protected Animation<TextureRegion> animation;
	
	public float stateTime;
	
	protected MyGdxGame game;
	
	// Base Test Animation
	public BaseActor(MyGdxGame game) {
		this.game = game;
		
		Texture colorTex = this.game.assets.manager.get("dev/ColorAnim.png");
		
		TextureRegion[][] tmp = TextureRegion.split(colorTex, colorTex.getWidth()/3, colorTex.getHeight()/4);
		TextureRegion[] colorFrames = new TextureRegion[12];
		int i = 0;
		for(int y = 0; y < 4; y++)
			for(int x = 0; x < 3; x++)
				colorFrames[i++] = tmp[y][x];
		
		Animation<TextureRegion> color = new Animation<TextureRegion>(.25f, colorFrames);
		color.setPlayMode(Animation.PlayMode.LOOP);
		
		animation = color;
	}
	
	public BaseActor(MyGdxGame game, Animation<TextureRegion> animation) {
		this.animation = animation;
		this.game = game;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		stateTime += delta;
		region = animation.getKeyFrame(stateTime);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a*parentAlpha);
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), 
				getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
	
	public void setAnimation(Animation<TextureRegion> animation) {  
        if (animation != null) {
        	this.animation = animation;
        	stateTime = 0;
        }
    } 
	
	public Animation<TextureRegion> loadSheet(Texture texToAnim, int numFrames, int width, int height, float time, boolean loop) {
		TextureRegion[][] tmp = TextureRegion.split(texToAnim, texToAnim.getWidth()/width, texToAnim.getHeight()/height);
		
//		System.out.println(numFrames + " " + width + "x" + height + " " + tmp.length + " " +tmp[0].length);
		
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
