package com.yigitozemir.zombieking.data;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A unit of building
 * buildings consist of different units
 */
public class BuildingUnit {
	private int x;
	private int y; 
	private TextureRegion textureRegion;
	
	public BuildingUnit(int x, int y, TextureRegion textureRegion) {
		this.x = x;
		this.y = y;
		this.textureRegion = textureRegion;
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
