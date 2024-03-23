package com.yigitozemir.zombieking.npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/**
 * Zombie
 * TODO: implement animation
 */
public class Zombie {
	private int x;
	private int y;
	
	private TextureRegion textureRegion;
	
	
	public Zombie(int x, int y) {
		this.x = x;
		this.y = y;

		FileHandle fileHandle = Gdx.files.internal("Spritesheet.png");
		textureRegion = new TextureRegion(new Texture(fileHandle), 16, 0, 8, 8);
	}


	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}
}
