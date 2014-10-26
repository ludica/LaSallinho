package org.lasalle.ludica.lasallinho.telas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.LaSalinhoGame;

public class GameOver implements Screen{

	private final String GAMEOVER = "GAME OVER";
	private final String CONTINUAR = "Continuar?";
	private final String MAIN = "Menu Principal";
	
	private LaSalinhoGame lsGame;
	private Stage stage;
	private Skin skin;
	
	Table tabela;
	
	private Label overMsg;
	private TextButton btnContinuar;
	private TextButton btnMain;
	
	public GameOver(LaSalinhoGame game){
		lsGame = game;
		this.stage = lsGame.stage;
		skin = new Skin(Gdx.files.internal("menu/menu_ui.json"), new TextureAtlas(Gdx.files.internal("menu/menu_ui.atlas")));
		
		tabela = new Table();
		
		overMsg = new Label(GAMEOVER,skin,"title");
		btnContinuar = new TextButton(CONTINUAR,skin,"default");
		btnMain = new TextButton(MAIN,skin,"default");
		
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
		tabela.add(overMsg).padBottom(60).row();
		tabela.add(btnContinuar).size(250,60).padBottom(40).row();
		tabela.add(btnMain).size(350,60).padBottom(40).row();
		
		tabela.setFillParent(true);
        stage.addActor(tabela);
	}
	
	private void setListeners(){
		btnContinuar.addListener(new ClickListener(){
			@Override 
            public void clicked(InputEvent event, float x, float y){
				lsGame.setScreen(new GameScreen(lsGame));
            }
		});
		btnMain.addListener(new ClickListener(){
			@Override 
            public void clicked(InputEvent event, float x, float y){
				lsGame.setScreen(new MainMenu(lsGame));
            }
		});
	}
	
	@Override
	public void dispose() {
		skin.dispose();
		for(Actor actor : stage.getActors()){
			actor.remove();
		}
	}

	@Override
	public void resize(int width, int height) {}
	
	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

}
