package com.ls.ludica.telas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.ls.ludica.game.AsAventurasDeLaSallinho;
import com.ls.ludica.personagens.Fase;
import com.ls.ludica.personagens.LaSallinho;

public class DemoScreen implements Screen {

	public static TiledMap map;
	public static OrthogonalTiledMapRenderer renderer;
	
	private Fase fase;
	
	public AsAventurasDeLaSallinho lsGame;
	public OrthographicCamera camera;
	public SpriteBatch batch;
	public LaSallinho laSalinho;
	private boolean colidiu = false;
	
	private int larguraCamera;
	private int alturaCamera;
	
	private ShapeRenderer debugRenderer = new ShapeRenderer();
	
	public DemoScreen(AsAventurasDeLaSallinho game){
		lsGame = game;
		
		map = new TmxMapLoader().load("fase-demo/fase-demo.tmx");
		fase = new Fase(map);
		renderer = new OrthogonalTiledMapRenderer(map);
		
		camera = new OrthographicCamera();
		// Exibir 20x10 blocos
		larguraCamera = fase.LARGURA_BLOCO * 20;
		alturaCamera = fase.ALTURA_BLOCO * 10;
		camera.setToOrtho(false, larguraCamera, alturaCamera);
		
		batch = new SpriteBatch();
		
		laSalinho = new LaSallinho();
		laSalinho.bounds.x = fase.xInicial;
		laSalinho.bounds.y = fase.yInicial;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(85/255f,170/229f,255/255f, 0.8f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(colidiu){
			try {
				Sound som = Gdx.audio.newSound(Gdx.files.internal("sons/morte.ogg"));
				som.play();
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			laSalinho.PerdeVida();
			laSalinho.resetar(fase.xInicial,fase.yInicial);
			colidiu = false;
		}
		
		
		laSalinho.Mover(fase.getCamadaColisao());
		laSalinho.Pular(fase.getCamadaColisao());
		laSalinho.Gravidade(fase.getCamadaColisao());
		
		renderer.setView(camera);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		enquadrarCamera(laSalinho.bounds);
		
		laSalinho.setStateTime(laSalinho.getStateTime() + delta);
		TextureRegion frame = laSalinho.Imagem();
		
		if(laSalinho.isDireita){
			if(frame.isFlipX()) frame.flip(true, false);
		}else{
			if(!frame.isFlipX()) frame.flip(true, false);
		}
		
		renderer.render(fase.getCamadaAreaSecreta());
		renderer.render(fase.getCamadaTampaSecreta());
		renderer.render(fase.getCamadaDeFundo());
		
		batch.begin();
		batch.draw(frame, laSalinho.bounds.x, laSalinho.bounds.y, laSalinho.bounds.width, laSalinho.bounds.height);
		
		batch.end();
		
		renderer.render(fase.getCamadaDaFrente());
		
		if (laSalinho.bounds.y < 0) {
			laSalinho.PerdeVida();
			laSalinho.resetar(fase.xInicial,fase.yInicial);
		}
		if(laSalinho.vidas == 0){
			laSalinho.vidas = 5;
			fase.resetarListaItem();
			lsGame.setScreen(new GameOver(lsGame));
		}
		
		drawDebug(laSalinho.bounds,Color.GREEN);

	}
	
	private void enquadrarCamera(Rectangle r) {
		if (r.y >= alturaCamera / 2) {
			camera.position.y = r.y;
		} else {
			camera.position.y = alturaCamera / 2;
		}

		if (r.x >= larguraCamera / 2 && r.x <= fase.LARGURA_MAPA - larguraCamera / 2) {
			camera.position.x = r.x;
		} else {
			if (r.x <= (fase.LARGURA_MAPA / 2)) {
				camera.position.x = larguraCamera / 2;
			} else {
				camera.position.x = fase.LARGURA_MAPA - larguraCamera / 2;
			}
		}
	}
	
	private void drawDebug(Rectangle bounds, Color color) {
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);
		debugRenderer.setColor(color);
		debugRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		debugRenderer.end();
	}
	
	
	
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
