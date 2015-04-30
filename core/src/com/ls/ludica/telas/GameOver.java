package com.ls.ludica.telas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ls.ludica.game.AsAventurasDeLaSallinho;

public class GameOver implements Screen{

	private final String CONTINUAR = "Continuar?";
	private final String MAIN = "Menu";
	
	private AsAventurasDeLaSallinho lsGame;
	private Stage palco;
	private Skin skin;
	private Table tabela;
	
	private Image planoDeFundo;
	private Image titulo;
	private TextButton btnContinuar;
	private TextButton btnMain;
	
	public GameOver(AsAventurasDeLaSallinho game){
		lsGame = game;
		this.palco = lsGame.getPalco();
		skin = new Skin(Gdx.files.internal("menu/menu_ui.json"), new TextureAtlas(Gdx.files.internal("menu/menu_ui.atlas")));
		
		montarTabela();
		setListeners();
	}
	
	private void montarTabela(){
		planoDeFundo = new Image(new Texture("planos-de-fundo/grave_background-1080.png"));
		planoDeFundo.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		titulo = new Image(new Texture("gameover.png"));
		btnContinuar = new TextButton(CONTINUAR,skin,"default");
		btnMain = new TextButton(MAIN,skin,"default");
		
		tabela = new Table();
		tabela.add(titulo).padBottom(60).row();
		tabela.add(btnContinuar).size(200,60).padBottom(40).row();
		tabela.add(btnMain).size(200,60).padBottom(40).row();
		
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
	
	private void setListeners(){
		btnContinuar.addListener(new ClickListener(){
			@Override 
            public void clicked(InputEvent event, float x, float y){
				lsGame.setScreen(lsGame.getGameScreen());
            }
		});
		btnMain.addListener(new ClickListener(){
			@Override 
            public void clicked(InputEvent event, float x, float y){
				lsGame.setScreen(lsGame.getMenuPrincipal());
            }
		});
	}
	
	@Override
	public void dispose() {}

	@Override
	public void resize(int width, int height) {}
	
	@Override
	public void hide() {
		// Retira os "atores" atuais para que os próximos entrem
		planoDeFundo.remove();
		tabela.remove();
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}
}
