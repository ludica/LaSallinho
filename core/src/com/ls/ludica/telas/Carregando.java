package com.ls.ludica.telas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ls.ludica.game.AsAventurasDeLaSallinho;

public class Carregando implements Screen{

	private AsAventurasDeLaSallinho lsGame;
	
	private Stage palco;
	private Table tabela;
	private Label texto;
	
	private boolean cargaInicial = true;
	
	public Carregando(AsAventurasDeLaSallinho game){
		this.lsGame = game;
		palco = lsGame.getPalco();
		
		carregarSkinsETexturas();
		montarTabela();
	}
	
	private void montarTabela(){
		texto = new Label("Carregando...",lsGame.getMenuSkin(),"carregando");
		tabela = new Table();
		tabela.add(texto).fillX().row();
		
		tabela.setFillParent(true);
	}
	
	/**
	 * 
	 * Carrega as telas mais usadas.
	 * 
	 */
	private void carregarTelas(){
		lsGame.setMenuPrincipal(new MainMenu(lsGame));
		lsGame.setGameScreen(new GameScreen(lsGame));
		lsGame.setGameOver(new GameOver(lsGame));
	}
	/**
	 * 
	 * Carrega a Skin e as texturas utilizada globalmente.
	 * No momento, apenas a skin do menu
	 * 
	 */
	private void carregarSkinsETexturas(){
		lsGame.setMenuSkin(new Skin(Gdx.files.internal("menu/menu_ui.json"), 
				new TextureAtlas(Gdx.files.internal("menu/menu_ui.atlas"))));
	}
	
	@Override
	public void show() {
		palco.addActor(tabela);
		// Ainda nao esta funcionando...
		if(cargaInicial){
			Gdx.app.postRunnable(new Runnable() {
			     @Override
			     public void run() {
			    	 carregarTelas();
					 lsGame.setScreen(lsGame.getMenuPrincipal());
			     }
			});
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        palco.act();
        palco.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		cargaInicial = false;
		tabela.remove();
	}

	@Override
	public void dispose() {}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
}
