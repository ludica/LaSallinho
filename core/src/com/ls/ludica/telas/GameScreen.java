package com.ls.ludica.telas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import com.ls.ludica.game.Constantes;
import com.ls.ludica.personagens.Fase;
import com.ls.ludica.personagens.Item;
import com.ls.ludica.personagens.LaSalinho;
import com.ls.ludica.personagens.Monstro;

/**
 * Classe que vai rodar a fase selecionada na tela "SelecionarFase" 
 * 
 * Essa classe está fazendo muito trabalho.
 * 
 */
public class GameScreen implements Screen {
	public static TiledMap map;
	public static OrthogonalTiledMapRenderer renderer;
	
	private Fase fase;
	
	public AsAventurasDeLaSallinho lsGame;
	public OrthographicCamera camera;
	public SpriteBatch batch;
	public LaSalinho laSalinho;
	private boolean colidiu = false;
	
	// Campo para debug 
	private ShapeRenderer debugRenderer = new ShapeRenderer();
	
	private int biblias;
	private int bibliasColetadas;
	private int pontos;
	
	public GameScreen(AsAventurasDeLaSallinho game) {
		this.lsGame = game;
		
		map = new TmxMapLoader().load("Grass.tmx");
		fase = new Fase(map);
		renderer = new OrthogonalTiledMapRenderer(map);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constantes.LARGURA_TELA, Constantes.ALTURA_TELA);
		
		batch = new SpriteBatch();
		
		laSalinho = new LaSalinho();
		laSalinho.bounds.x = Constantes.X_INICIAL;
		laSalinho.bounds.y = Constantes.Y_INICIAL;
		pontos = 0;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(85/255f,170/229f,255/255f, 0.8f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Revisar
		if(colidiu){
			try {
				Sound som = Gdx.audio.newSound(Gdx.files.internal("sons/morte.ogg"));
				som.play();
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LaSalinho.PerdeVida();
			laSalinho = new LaSalinho();
			colidiu = false;
		}
		/*
		 * Checagem das colisoes
		 */
		laSalinho.Mover(fase.getCamadaColisao());
		laSalinho.Pular(fase.getCamadaColisao());
		laSalinho.Gravidade(fase.getCamadaColisao());
		if (Gdx.input.isKeyPressed(Keys.S)) {
			laSalinho.Teleporte(fase.getCamadaTeleporte());
		}

		renderer.setView(camera);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		enquadrarCamera(laSalinho.bounds);
		
		laSalinho.setStateTime(laSalinho.getStateTime() + delta);
		TextureRegion frame = laSalinho.Imagem();
		
		if(LaSalinho.isDireita){
			if(frame.isFlipX()) frame.flip(true, false);
		}else{
			if(!frame.isFlipX()) frame.flip(true, false);
		}
		/*
		 * O mapa de demonstracao tem 3 camadas. As 2 primeiras são exibidas agora
		 */
		renderer.render(fase.getCamadaDeFundo());
		/*
		 * Começo da "pintura" na tela
		 */
		batch.begin();
		batch.draw(frame, laSalinho.bounds.x, laSalinho.bounds.y, laSalinho.bounds.width, laSalinho.bounds.height);
		int p;
		/*
		 * Checagem para colisao com itens
		 */
		for(Item item : fase.getItemLista()){
			p = item.coletar(laSalinho.bounds);
			pontos += p;
			//bibliasColetadas += p == ItemFactory.BIBLIA_PTS ? 1 : 0;
			if(!item.foiColetado()){
				Rectangle b = item.getBounds();
				batch.draw(item.getSprite(), b.x, b.y, b.width, b.height);
			}
		}
		/*
		 * Checagem para colisao com monstros
		 */
		for(Monstro inimigo : fase.getMob()){
			Rectangle bounds = inimigo.getBounds();
			batch.draw(inimigo.getSprite(), bounds.x, bounds.y, bounds.width, bounds.height);
			inimigo.gravidade(fase.getCamadaColisao());
			inimigo.mover(fase.getCamadaColisao());
			if(laSalinho.InimigoColide(bounds)){
				colidiu = true;
			}
		}
		batch.end();
		/*
		 * Renderizando a 3º camada
		 */
		renderer.render(fase.getCamadaDaFrente());
		
		if (laSalinho.bounds.y < 0) {
			LaSalinho.PerdeVida();
			laSalinho = new LaSalinho();
		}
		if(LaSalinho.vidas == 0){
			LaSalinho.vidas = 5;
			pontos = 0;
			biblias = 0;
			fase.resetarListaItem();
			lsGame.setScreen(new GameOver(lsGame));
		}
		// DEBUG
		drawDebug(laSalinho.bounds,Color.GREEN);
		for(Monstro i : fase.getMob())
			drawDebug(i.getBounds(),Color.RED);
		//System.out.println("Vidas: "+LaSalinho.vidas+" Pontos: "+pontos+" Biblias: "+bibliasColetadas+"/"+biblias);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {
		// Quando a fase for terminada, e necessario dar dispose() em alguns recursos, como texturas e som.
		// Chame-os aqui
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		// Ver hide()
	}
	
	/**
	 * 
	 * Enquadra a camera para seguir La Sallinho e evitar que ela nao saia das margens da fase
	 * 
	 * @param lsBloco
	 */
	private void enquadrarCamera(Rectangle r) {
		if (r.y >= Constantes.ALTURA_TELA / 2) {
			camera.position.y = r.y;
		} else {
			camera.position.y = Constantes.ALTURA_TELA / 2;
		}

		if (r.x >= Constantes.LARGURA_TELA / 2 && r.x <= Constantes.LARGURA_MAP - Constantes.LARGURA_TELA / 2) {
			camera.position.x = r.x;
		} else {
			if (r.x <= (Constantes.LARGURA_MAP/2)) {
				camera.position.x = Constantes.LARGURA_TELA / 2;
			} else {
				camera.position.x = Constantes.LARGURA_MAP - Constantes.LARGURA_TELA / 2;
			}
		}
	}
	
	/**
	 * 
	 * Metodo para debuggar as colisoes.
	 * 
	 */
	private void drawDebug(Rectangle bounds, Color color) {
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);
		debugRenderer.setColor(color);
		debugRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		debugRenderer.end();
	}
}
