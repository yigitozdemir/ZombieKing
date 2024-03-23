package com.yigitozemir.zombieking.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Level {
	private int MAP_WIDTH = 30;
	private int MAP_HEIGHT = 30;
	private final int TILE_SIZE = 16;
	/**
	 * background texture region
	 */
	private TextureRegion backgroundTextureRegion;
	/**
	 * main camera of the game
	 */
	private OrthographicCamera camera;
	
	
	public Level() {
		FileHandle fileHandle = Gdx.files.internal("Spritesheet.png");
		Texture texture = new Texture(fileHandle);
		backgroundTextureRegion = new TextureRegion(texture, 0, 0, TILE_SIZE, TILE_SIZE);
		
		//camera = new OrthographicCamera(30, 30 * (h / w));
		camera = new OrthographicCamera(30, 30);
		
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
	}
	
	public void renderLevel(float delta, SpriteBatch spriteBatch) {
		handleCemeraInput();
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		
		renderBg(spriteBatch);
	}
	
	private void handleCemeraInput() {
		// zoom out
		if(Gdx.input.isKeyPressed(Keys.Q)) {
			if(camera.zoom < 10f) {
				camera.zoom += 0.05;
			}
		}
		// zoom in
		if(Gdx.input.isKeyPressed(Keys.E)) {
			if (camera.zoom > 0.1) {
				camera.zoom -= 0.05;
			}
		}
		// move camera left
		if(Gdx.input.isKeyPressed(Keys.A)) {
			camera.position.x -= 2;
		}
		// move camera right
		if(Gdx.input.isKeyPressed(Keys.D)) {
			camera.position.x += 2;
		}
		// move camera up
		if(Gdx.input.isKeyPressed(Keys.W)) {
			camera.position.y += 2;
		}
		// move camera down
		if(Gdx.input.isKeyPressed(Keys.S)) {
			camera.position.y -= 2;
		}
	}
	
	private void renderBg(SpriteBatch spriteBatch) {
		int i = 0, j = 0;
		
		for(;i < MAP_WIDTH; i++) {
			for(; j < MAP_HEIGHT; j++) {
				spriteBatch.draw(backgroundTextureRegion, i * TILE_SIZE, j * TILE_SIZE);
			}
			j = 0;
		}
	}
}
