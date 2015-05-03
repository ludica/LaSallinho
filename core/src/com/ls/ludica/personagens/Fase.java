package com.ls.ludica.personagens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.ls.ludica.fabricas.FabricaItem;
import com.ls.ludica.fabricas.FabricaMonstro;

public class Fase {

	public final int LARGURA_MAPA;
	public final int ALTURA_MAPA;
	public final int LARGURA_MAPA_EM_BLOCO;
	public final int ALTURA_MAPA_EM_BLOCO;
	public final int LARGURA_BLOCO;
	public final int ALTURA_BLOCO;
	
	private TiledMapTileLayer camadaColisao;
	private TiledMapTileLayer camadaItem;
	private TiledMapTileLayer camadaTeleporte;
	private TiledMapTileLayer camadaInimigo;
	
	private List<Item> itemLista = new ArrayList<Item>();
	private List<Monstro> mob = new ArrayList<Monstro>();
	
	public float gravidade = 12;
	
	private int camadaAreaSecreta[] = {0,1};
	private int camadaTampaSecreta[] = {2};
	private int camadaDeFundo[] = {3,4};
	private int camadaDaFrente[] = {5};
	
	// Hardcoded, o mapa deve fornecer essa informacao
	public int xInicial;
	public int yInicial;
	
	public Fase(Map mapa){
		//camadaColisao = (TiledMapTileLayer) mapa.getLayers().get("Collision");
		//camadaItem = (TiledMapTileLayer) mapa.getLayers().get("Itens");
		//camadaTeleporte = (TiledMapTileLayer) mapa.getLayers().get("Teleporte");
		//camadaInimigo = (TiledMapTileLayer) mapa.getLayers().get("Inimigos");
		
		camadaColisao = (TiledMapTileLayer) mapa.getLayers().get("colisao");
		
		MapProperties prop = mapa.getProperties();
		// Largura e altura do mapa em numero de blocos
		LARGURA_MAPA_EM_BLOCO = prop.get("width", Integer.class);
		ALTURA_MAPA_EM_BLOCO = prop.get("height",Integer.class);
				
		// Largura e altura dos blocos do mapa
		LARGURA_BLOCO = prop.get("tilewidth",Integer.class);
		ALTURA_BLOCO = prop.get("tileheight",Integer.class);
				
		LARGURA_MAPA = LARGURA_MAPA_EM_BLOCO * LARGURA_BLOCO;
		ALTURA_MAPA = ALTURA_MAPA_EM_BLOCO * ALTURA_BLOCO;
		
		xInicial = LARGURA_BLOCO * 1;
		yInicial = ALTURA_BLOCO * 6;
		
		//carregarListas();
		
	}
	
	/**
	 * 
	 * Carrega a lista de itens e monstros da fase
	 * 
	 */
	private void carregarListas(){
		FabricaItem fabricaItem = new FabricaItem(LARGURA_BLOCO,ALTURA_BLOCO);
		FabricaMonstro fabricaMonstro = new FabricaMonstro();
		int itemId;
		for(int i = 0; i <= LARGURA_MAPA; i++){
			for(int j = 0; j <= ALTURA_MAPA; j++){
				// Carregando os itens
				if(camadaItem.getCell(i, j) != null){
					itemId = camadaItem.getCell(i, j).getTile().getId();
					Item item = fabricaItem.criarItem(itemId, i * LARGURA_BLOCO, j * ALTURA_BLOCO);
					itemLista.add(item);
				}
				// Carregando os monstros
				if(camadaInimigo.getCell(i, j) != null){
					Monstro inimigo = fabricaMonstro.criarMonstro(0, i * LARGURA_BLOCO, j * ALTURA_BLOCO);
					mob.add(inimigo);
				}
			}
		}
	}
	/**
	 * 
	 * Quando o La Sallinho perder todas as vidas, a fase recomeça.
	 * Os itens devem voltar ao estado de nao coletado.
	 * 
	 */
	public void resetarListaItem(){
		for(Item item : itemLista){
			item.setNaoColetado();
		}
	}
	
	
	public TiledMapTileLayer getCamadaColisao() {
		return camadaColisao;
	}

	public TiledMapTileLayer getCamadaItem() {
		return camadaItem;
	}

	public TiledMapTileLayer getCamadaTeleporte() {
		return camadaTeleporte;
	}

	public TiledMapTileLayer getCamadaInimigo() {
		return camadaInimigo;
	}

	public List<Item> getItemLista() {
		return itemLista;
	}

	public List<Monstro> getMob() {
		return mob;
	}

	public int[] getCamadaDeFundo() {
		return camadaDeFundo;
	}

	public int[] getCamadaDaFrente() {
		return camadaDaFrente;
	}

	public int[] getCamadaTampaSecreta() {
		return camadaTampaSecreta;
	}

	public int[] getCamadaAreaSecreta() {
		return camadaAreaSecreta;
	}
	
	
	
}
