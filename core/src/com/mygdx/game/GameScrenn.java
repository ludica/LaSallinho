package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class GameScrenn implements Screen {
	public static TiledMap map;
	public static OrthogonalTiledMapRenderer renderer;
	
	public TiledMapTileLayer layerCollision;
	public TiledMapTileLayer layerItens;
	public TiledMapTileLayer layerTeleporte;
	public TiledMapTileLayer layerInimigos;
	public LaSalinhoGame game;
	public OrthographicCamera camera;
	public SpriteBatch batch;
	public LaSalinho laSalinho;
	public ArrayList<Itens> allItens = new ArrayList<Itens>();
	public ArrayList<Inimigos> allInimigos = new ArrayList<Inimigos>();
	public int backgroundLayer[] = {0,1};
	public int foregroundLayer[] = {2};
	
	public GameScrenn(LaSalinhoGame game) {// create()
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constantes.LARGURA_TELA, Constantes.ALTURA_TELA);
		batch = new SpriteBatch();
		map = new TmxMapLoader().load("Mapa.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		layerCollision = (TiledMapTileLayer) map.getLayers().get("Collision");
		layerItens = (TiledMapTileLayer) map.getLayers().get("Itens");
		layerTeleporte = (TiledMapTileLayer) map.getLayers().get("Teleporte");
		layerInimigos = (TiledMapTileLayer) map.getLayers().get("Inimigos");
		laSalinho = new LaSalinho();
		laSalinho.bounds.x = Constantes.X_INICIAL;
		laSalinho.bounds.y = Constantes.Y_INICIAL;
		CriarItens();
		CriarInimigos();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1F, 1F, 1F, 1F);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		laSalinho.Mover(layerCollision);
		laSalinho.Pular(layerCollision);
		laSalinho.Gravidade(layerCollision);
		if (Gdx.input.isKeyPressed(Keys.S)) {
			laSalinho.Teleporte(layerTeleporte);
		}

		renderer.setView(camera);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		EnquadrarCamera(laSalinho.bounds);
		
		laSalinho.stateTime += delta;
		TextureRegion frame = laSalinho.Imagem();
		
		if(LaSalinho.isDireita){
			if(frame.isFlipX()) frame.flip(true, false);
		}else{
			if(!frame.isFlipX()) frame.flip(true, false);
		}

		renderer.render(backgroundLayer);
		batch.begin();
		batch.draw(frame, laSalinho.bounds.x, laSalinho.bounds.y, laSalinho.bounds.width, laSalinho.bounds.height);
		for(Itens item : allItens){
			item.Coletar(laSalinho.bounds);
			if(!item.isColetado){
				batch.draw(item.image, item.bounds.x, item.bounds.y, item.bounds.width, item.bounds.height);
			}
		}
		for(Inimigos inimigo : allInimigos){
			batch.draw(inimigo.image, inimigo.bounds.x, inimigo.bounds.y, inimigo.bounds.width, inimigo.bounds.height);
			inimigo.Gravidade(layerCollision);
			inimigo.Mover(layerCollision);
			if(laSalinho.InimigoColide(inimigo.bounds)){
				LaSalinho.PerdeVida();
				laSalinho = new LaSalinho();
			}
		}
		batch.end();
		renderer.render(foregroundLayer);
		
		if (laSalinho.bounds.y < 0) {
			LaSalinho.PerdeVida();
			laSalinho = new LaSalinho();
		}
		if(LaSalinho.vidas == 0){
			LaSalinho.vidas = 5;
			LaSalinho.pontos = 0;
			Itens.hollybookColetado = 0;
			CriarItens();
		}
		System.out.println("Vidas: "+LaSalinho.vidas+" Pontos: "+LaSalinho.pontos+" Biblias: "+Itens.hollybookColetado+"/"+Itens.hollybookTotal);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	private void EnquadrarCamera(Rectangle r) {
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
	
	public void CriarItens(){
		allItens.clear();
		Itens.hollybookTotal = 0;
		for (int i = 0; i < Constantes.TILESHORIZONTAL; i++) {
			for (int j = 0; j < Constantes.TILESVERTICAL; j++) {
				if (layerItens.getCell(i, j) != null) {
					if (layerItens.getCell(i, j).getTile().getId() == Constantes.IDITEM1) {
						Itens item = new Itens((int) i * Constantes.TILESCALE, (int) j * Constantes.TILESCALE);
						allItens.add(item);
					}
					if (layerItens.getCell(i, j).getTile().getId() == Constantes.IDITEM2) {
						Itens item = new Itens((int) i * Constantes.TILESCALE, (int) j * Constantes.TILESCALE, 100);
						allItens.add(item);
						Itens.hollybookTotal++;
					}
				}
			}
		}
	}
	
	public void CriarInimigos(){
		allInimigos.clear();
		for (int i = 0; i < Constantes.TILESHORIZONTAL; i++) {
			for (int j = 0; j < Constantes.TILESVERTICAL; j++) {
				if (layerInimigos.getCell(i, j) != null) {
					Inimigos inimigo = new Inimigos((int) i * Constantes.TILESCALE, (int) j * Constantes.TILESCALE, MathUtils.random(1f,2.5f));
					allInimigos.add(inimigo);
				}
			}
		}
	}
}
