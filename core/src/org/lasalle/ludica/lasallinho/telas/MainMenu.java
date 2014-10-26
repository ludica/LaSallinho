package org.lasalle.ludica.lasallinho.telas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameScreen;

public class MainMenu implements Screen{
		private final String TITULO = "As Aventuras de La Sallinho";
		private final String JOGAR = "Jogar";
		private final String OPCOES = "Opcoes";
		private final String SAIR = "Sair";
	
		private Stage stage;
		private Skin skin;
		private Table tabela;
		
		private Label titulo;
		
		private TextButton btnPlay;
		private TextButton btnOpcoes;
		private TextButton btnSair;
		
	public MainMenu(){
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("menu/menu_ui.json"), new TextureAtlas(Gdx.files.internal("menu/menu_ui.atlas")));
		Gdx.input.setInputProcessor(stage);
		
		tabela = new Table();
		
		titulo = new Label(TITULO, skin, "menu_text");
		btnPlay = new TextButton(JOGAR, skin, "default");
		btnOpcoes = new TextButton(OPCOES, skin, "default");
		btnSair = new TextButton(SAIR, skin, "default");
		
		setListeners();
	}
		
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
	}


	@Override
	public void show() {
		tabela.add(titulo).padBottom(40).row();
		tabela.add(btnPlay).size(150,60).padBottom(20).row();
		tabela.add(btnOpcoes).size(150,60).padBottom(20).row();
		tabela.add(btnSair).size(150,60).row();
		
		tabela.setFillParent(true);
        stage.addActor(tabela);
	}

	private void setListeners(){
		btnPlay.addListener(new ClickListener(){
			@Override 
            public void clicked(InputEvent event, float x, float y){
				((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
		});
		
		btnSair.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	            Gdx.app.exit();
	        }
	    });
		
	}
	
	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
	
	@Override
	public void resize(int width, int height) {}
	
	@Override
	public void pause() {}

	@Override
	public void resume() {}


}
