package com.yigitozdemir.zombieking.npc;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Human
 * TODO: Pathfinding
 * TODO: animation
 * TODO: different hooman with torch
 */
public class Human {
	private int x;
	private int y;
	private TextureRegion textureRegion;
	
	public Human(int x, int y) {
		this.x = x; 
		this.y = y;
		
		FileHandle fileHandle = new FileHandle("Spritesheet.png");
		textureRegion = new TextureRegion(new Texture(fileHandle), 16, 8, 8, 8);
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
