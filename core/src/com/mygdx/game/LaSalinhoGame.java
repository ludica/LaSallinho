package com.mygdx.game;

import org.lasalle.ludica.lasallinho.telas.MainMenu;

import com.badlogic.gdx.Game;

public class LaSalinhoGame extends Game {

	public GameScreen gameScreen;

	@Override
	public void create() {
		//gameScreen = new GameScreen(this);

		//setScreen(gameScreen);
		setScreen(new MainMenu());
	}
}
