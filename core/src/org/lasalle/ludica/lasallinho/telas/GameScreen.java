package org.lasalle.ludica.lasallinho.telas;

import java.util.ArrayList;

import org.lasalle.ludica.lasallinho.atores.Item;
import org.lasalle.ludica.lasallinho.fabricas.ItemFactory;

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
import com.mygdx.game.Constantes;
import com.mygdx.game.Inimigo;
import com.mygdx.game.LaSalinho;
import com.mygdx.game.LaSalinhoGame;

public class GameScreen implements Screen {
	public static TiledMap map;
	public static OrthogonalTiledMapRenderer renderer;
	
	public TiledMapTileLayer layerCollision;
	public TiledMapTileLayer layerItens;
	public TiledMapTileLayer layerTeleporte;
	public TiledMapTileLayer layerInimigos;
	
	public LaSalinhoGame lsGame;
	public OrthographicCamera camera;
	public SpriteBatch batch;
	public LaSalinho laSalinho;
	
	private int biblias;
	private int bibliasColetadas;
	private int pontos;
	public ArrayList<Item> itens = new ArrayList<Item>();
	public ArrayList<Inimigo> allInimigos = new ArrayList<Inimigo>();
	public int backgroundLayer[] = {0,1};
	public int foregroundLayer[] = {2};
	
	public GameScreen(LaSalinhoGame game) {// create()
		this.lsGame = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constantes.LARGURA_TELA, Constantes.ALTURA_TELA);
		batch = new SpriteBatch();
		map = new TmxMapLoader().load("Grass.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		layerCollision = (TiledMapTileLayer) map.getLayers().get("Collision");
		layerItens = (TiledMapTileLayer) map.getLayers().get("Itens");
		layerTeleporte = (TiledMapTileLayer) map.getLayers().get("Teleporte");
		layerInimigos = (TiledMapTileLayer) map.getLayers().get("Inimigos");
		laSalinho = new LaSalinho();
		laSalinho.bounds.x = Constantes.X_INICIAL;
		laSalinho.bounds.y = Constantes.Y_INICIAL;
		pontos = 0;
		criarItens();
		criarInimigos();
	}

    GameScreen() {
        this(null);
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
		enquadrarCamera(laSalinho.bounds);
		
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
		int p;
		for(Item item : itens){
			p = item.coletar(laSalinho.bounds);
			pontos += p;
			bibliasColetadas += p == ItemFactory.BIBLIA_PTS ? 1 : 0;
			if(!item.foiColetado()){
				Rectangle b = item.getBounds();
				batch.draw(item.getSprite(), b.x, b.y, b.width, b.height);
			}
		}
		for(Inimigo inimigo : allInimigos){
			batch.draw(inimigo.image, inimigo.bounds.x, inimigo.bounds.y, inimigo.bounds.width, inimigo.bounds.height);
			inimigo.gravidade(layerCollision);
			inimigo.mover(layerCollision);
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
			pontos = 0;
			biblias = 0;
			criarItens();
			lsGame.setScreen(new GameOver(lsGame));
		}
		System.out.println("Vidas: "+LaSalinho.vidas+" Pontos: "+pontos+" Biblias: "+bibliasColetadas+"/"+biblias);
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
		
		//for(Actor actor : lsGame.getStage().getActors()){
		//	actor.remove();
		//}
	}

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
	
	public void criarItens(){
		ItemFactory fabrica = new ItemFactory();
		itens.clear();
		int itemId;
		biblias = 0;
		for (int i = 0; i < Constantes.TILESHORIZONTAL; i++) {
			for (int j = 0; j < Constantes.TILESVERTICAL; j++) {
				if (layerItens.getCell(i, j) != null) {
					itemId = layerItens.getCell(i, j).getTile().getId();
					Item item = fabrica.criarItem(itemId, i * Constantes.TILESCALE, j * Constantes.TILESCALE);
					biblias += itemId == ItemFactory.BIBLIA ? 1 : 0;
					itens.add(item);
				}
			}
		}
	}
	
	public void criarInimigos(){
		allInimigos.clear();
		for (int i = 0; i < Constantes.TILESHORIZONTAL; i++) {
			for (int j = 0; j < Constantes.TILESVERTICAL; j++) {
				if (layerInimigos.getCell(i, j) != null) {
					Inimigo inimigo = new Inimigo((int) i * Constantes.TILESCALE, (int) j * Constantes.TILESCALE, MathUtils.random(4f,7f));
					allInimigos.add(inimigo);
				}
			}
		}
	}
}
