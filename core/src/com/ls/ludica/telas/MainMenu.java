package com.ls.ludica.telas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ls.ludica.game.AsAventurasDeLaSallinho;

public class MainMenu implements Screen {

	private final String JOGAR = "Jogar";
	private final String OPCOES = "Opcoes";
	private final String SAIR = "Sair";

	private AsAventurasDeLaSallinho lsGame;

	private Stage palco;
	private Skin skin;
	private Table tabela;

	private TextButton btnPlay;
	private TextButton btnOpcoes;
	private TextButton btnSair;
	private Image planoDeFundo;
	private Image titulo;

	public MainMenu(AsAventurasDeLaSallinho game) {
		lsGame = game;
		palco = lsGame.getPalco();
		skin = lsGame.getMenuSkin();

		montarTabela();
		setListeners();
	}

	/**
	 * 
	 * Instancia a tabela,e a monta, e seus filhos para exibicao
	 * 
	 */
	private void montarTabela() {
		planoDeFundo = new Image(new Texture(
				"planos-de-fundo/grass_background-1080.png"));
		planoDeFundo.setBounds(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		titulo = new Image(new Texture("ls-banner.png"));
		btnPlay = new TextButton(JOGAR, skin, "default");
		btnOpcoes = new TextButton(OPCOES, skin, "default");
		btnSair = new TextButton(SAIR, skin, "default");

		tabela = new Table();

		tabela.add(titulo).padBottom(40).row();
		tabela.add(btnPlay).size(200, 60).padBottom(20).row();
		tabela.add(btnOpcoes).size(200, 60).padBottom(20).fillX().row();
		tabela.add(btnSair).size(200, 60).row();
		tabela.setFillParent(true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		palco.act();
		palco.draw();
	}

	@Override
	public void show() {
		// aloca os "atores" no palco
		palco.addActor(planoDeFundo);
		palco.addActor(tabela);
	}

	private void setListeners() {
		btnPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// lsGame.setScreen(lsGame.getGameScreen());
				lsGame.setScreen(lsGame.getGameScreen());
			}
		});

		btnSair.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

	}

	@Override
	public void hide() {
		// Retira os "atores" atuais para que os próximos entrem
		tabela.remove();
		planoDeFundo.remove();
	}

	@Override
	public void dispose() {

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

}
