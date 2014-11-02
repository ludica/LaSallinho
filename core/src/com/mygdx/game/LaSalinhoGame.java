package com.mygdx.game;

import org.lasalle.ludica.lasallinho.telas.GameScreen;
import org.lasalle.ludica.lasallinho.telas.MainMenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class LaSalinhoGame extends Game {

	public Stage stage;
	
	public GameScreen gameScreen;

	@Override
	public void create() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		//gameScreen = new GameScreen(this);

		//setScreen(gameScreen);
		setScreen(new MainMenu(this));
	}

	public Stage getStage() {
		return stage;
	}
	
	
	
}
