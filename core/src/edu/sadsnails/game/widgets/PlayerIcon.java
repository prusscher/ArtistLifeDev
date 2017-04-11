package edu.sadsnails.game.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import edu.sadsnails.game.MyGdxGame;

public class PlayerIcon extends Actor {
	
	private TextureRegion base;
	private TextureRegion[] faces;
	private TextureRegion[] hair;
	private TextureRegion[] shirts;
	
	private int curFace;
	private int curHair;
	private int curShirt;
	
	public PlayerIcon(MyGdxGame game) {
		
		// THESE SHOULD BE PRESERVED IN SETTINGS FOR LATER PARKER
		// DONT FORGET ABOUT THESE
		curFace = 0;
		curHair = 0;
		curShirt = 0;
		
		base = new TextureRegion((Texture) game.assets.manager.get("images/playerIcon/base.png"));
		
		// Get Face Textures
		Texture tempTex = game.assets.manager.get("images/playerIcon/faces.png");
		
		// Get and Set the face
		faces = new TextureRegion[3];
		TextureRegion[][] tmp = TextureRegion.split(tempTex, tempTex.getWidth()/3, tempTex.getHeight());
		for(int y = 0; y < 3; y++)
			faces[y] = tmp[0][y];
		
		// Get and set Hair
		hair = new TextureRegion[3];
		tempTex = game.assets.manager.get("images/playerIcon/hair.png");
		tmp = TextureRegion.split(tempTex, tempTex.getWidth()/3, tempTex.getHeight());
		for(int y = 0; y < 3; y++)
			hair[y] = tmp[0][y];
		
		// Get and set Shirts
		shirts = new TextureRegion[4];
		tempTex = game.assets.manager.get("images/playerIcon/shirts.png");
		tmp = TextureRegion.split(tempTex, tempTex.getWidth()/4, tempTex.getHeight());
		for(int y = 0; y < 4; y++)
			shirts[y] = tmp[0][y];
		
		setWidth(64);
		setHeight(64);
		
	}
	
	public PlayerIcon(MyGdxGame game, int hair, int shirt, int face) {
		this(game);
		
		setItems(hair, shirt, face);
	}
	
	public PlayerIcon(MyGdxGame game, int[] items) {
		this(game);
		
		setItems(items[0], items[1], items[2]);
	}
	
	public void setItems(int[] items) {
		setItems(items[0], items[1], items[2]);
	}
	
	public void setItems(int hair, int shirt, int face) {
		if(hair >= 0 && hair < this.hair.length)
			curHair = hair;
		else
			curHair = 0;
		
		if(shirt >= 0 && shirt < this.shirts.length)
			curShirt = shirt;
		else
			curShirt = 0;
		
		if(face >= 0 && face < this.faces.length)
			curFace = face;
		else
			curFace = 0;
	}
	
	public int[] getItems() {
		int[] out = {curHair, curShirt, curFace};
		return out;
	}
	
	public void nextHair() {
		curHair++;
		if(curHair >= hair.length)
			curHair = 0;
	}
	
	public void prevHair() {
		curHair--;
		if(curHair < 0)
			curHair = hair.length-1;
	}
	
	public void nextShirt() {
		curShirt++;
		if(curShirt >= shirts.length)
			curShirt = 0;
	}
	
	public void prevShirt() {
		curShirt--;
		if(curShirt < 0)
			curShirt = shirts.length-1;
	}
	
	public void nextFace() {
		curFace++;
		if(curFace >= faces.length)
			curFace = 0;
	}
	
	public void prevFace() {
		curFace--;
		if(curFace < 0)
			curFace = faces.length-1;
	}
	
	public String toString() {
		return ("PLAYER ICON: " + getX() + " " + getY() + " " + getWidth() + " " + getHeight() + " " + getOriginX() + " " + getOriginY());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		batch.setColor(1, 1, 1, 1);
		
		batch.draw(base, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		batch.draw(faces[curFace], getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		batch.draw(hair[curHair], getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		batch.draw(shirts[curShirt], getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
	
}
