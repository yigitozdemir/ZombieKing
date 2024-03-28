package com.yigitozemir.zombieking.npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
/**
 * Zombie
 * TODO: implement animation
 */
public class Zombie {
	private float x;
	private float y;
	
	private TextureRegion textureRegion;
	
	private boolean moving = false;
	private Vector2 targetPosition = null;
	
	public Zombie(float x, float y) {
		this.x = x;
		this.y = y;

		FileHandle fileHandle = Gdx.files.internal("Spritesheet.png");
		textureRegion = new TextureRegion(new Texture(fileHandle), 16, 0, 8, 8);
	}
	/**
	 * Method for doing zombie calculations
	 * TODO: make it common in statemachine
	 */
	public void update() {
		if(isMoving()) {
			System.out.println("moving");
		}
	}

	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}
	public boolean isMoving() {
		return moving;
	}
	public Vector2 getTargetPosition() {
		return targetPosition;
	}
	public void setMoving(boolean isMoving) {
		this.moving = isMoving;
	}
	public void setTargetPosition(Vector2 targetPosition) {
		this.targetPosition = targetPosition;
	}
	public Vector2 getCurrentPosition() {
		return new Vector2(x, y);
	}
}
