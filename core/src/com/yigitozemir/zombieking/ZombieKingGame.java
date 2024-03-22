package com.yigitozemir.zombieking;

import com.badlogic.gdx.Game;
import com.yigitozemir.zombieking.screens.GameScreen;

public class ZombieKingGame extends Game {
	
	@Override
	public void create() {
		GameScreen gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

}
