package com.mygdx.game;

import com.badlogic.gdx.Game;

public class LaSalinhoGame extends Game {

	public GameScrenn game_screen;

	@Override
	public void create() {
		game_screen = new GameScrenn(this);

		setScreen(game_screen);
	}
}
