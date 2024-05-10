package com.yigitozdemir.zombieking.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yigitozdemir.zombieking.ZombieKingGame;
import com.yigitozdemir.zombieking.data.Level;

public class GameScreen implements Screen {
	/**
	 * Game 
	 */
	private ZombieKingGame game;
	
	/**
	 * Level object which holds level data
	 */
	private Level level;
	
	/**
	 * Spritebatch the renderer of the game
	 */
	SpriteBatch spriteBatch;
	
	
	public GameScreen(ZombieKingGame game) {
		this.game = game;
		level = new Level();
		spriteBatch = new SpriteBatch();
	}
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.graphics.setTitle("FPS:" + String.valueOf(Gdx.graphics.getFramesPerSecond()));
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		//spriteBatch.begin();
		
		level.renderLevel(delta, spriteBatch);
		
		//spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
