package com.ls.ludica.fabricas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.ls.ludica.personagens.Item;

/**
 * 
 * Classe utilitaria para a criacao dos itens.
 * Tem muita coisa para melhorar.
 *
 */
public class FabricaItem {

	private int larguraBloco;
	private int alturaBloco;
	
	// Identificando o item
	private final int ESTRELA_BRONZE = 0; // Original: 1
	private final int ESTRELA_OUTRO = 2;
	private final int CHAVE_AZUL = 1;
	
	// Quantidade de pontos que os itens dao
	private final int LIVRO_PTS = 50;
	private final int BIBLIA_PTS = 100;
	
	// Caminho para as texturas
	private final String ESTRELA_BRONZE_TEXTURA = "itens/star_bronze.png";
	private final String ESTRELA_OURO_TEXTURA = "itens/star_gold.png";
	private final String CHAVE_AZUL_TEXTURA = "itens/keyBlue.png";
	
	// Sprites
	private Sprite estrelaBronze;
	private Sprite estrelaOuro;
	private Sprite chaveAzul;
	
	// Caminho para os sons
	private final String SOM_ESTRELA_BRONZE = "sons/som_livro.ogg";
	private final String SOM_ESTRELA_OURO = "sons/som_biblia.ogg"; 
	
	// Som
	private Sound somEstrelaBronze;
	private Sound somEstrelaOuro;
	
	public FabricaItem(int larguraBloco, int alturaBloco){
		this.larguraBloco = larguraBloco;
		this.alturaBloco = alturaBloco;
		
		estrelaBronze = new Sprite(new Texture(ESTRELA_BRONZE_TEXTURA));
		// hardcoded, tirar depois
		estrelaBronze.setSize(31, 31);
		estrelaOuro = new Sprite(new Texture(ESTRELA_OURO_TEXTURA));
		// hardcoded, tirar depois
		estrelaOuro.setSize(31, 31);
		
		chaveAzul = new Sprite(new Texture(CHAVE_AZUL_TEXTURA));
		
		somEstrelaBronze = Gdx.audio.newSound(Gdx.files.internal(SOM_ESTRELA_BRONZE));
		somEstrelaOuro = Gdx.audio.newSound(Gdx.files.internal(SOM_ESTRELA_OURO));
		
	}
	
	public Item criarItem(int id, int posX, int posY){
		Item item;
		Rectangle bounds;
		
		/*
		 * Para (bem) depois:
		 * deixa o processo de centralizacao do item no bloco 'automatico'
		 */
		
		switch(id){
			case ESTRELA_OUTRO:
				bounds = new Rectangle(posX+((larguraBloco-31)/2),posY+((alturaBloco-31)/2),
						estrelaOuro.getWidth(),estrelaOuro.getHeight());
				item = new Item(id, estrelaOuro, BIBLIA_PTS, bounds, somEstrelaOuro);
				break;
			case ESTRELA_BRONZE:
				bounds = new Rectangle(posX+((larguraBloco-31)/2),posY+((alturaBloco-31)/2),
						estrelaOuro.getWidth(),estrelaOuro.getHeight());
				item = new Item(id, estrelaBronze, LIVRO_PTS, bounds, somEstrelaBronze);
				break;
			case CHAVE_AZUL:
				bounds = new Rectangle(posX,posY,
						chaveAzul.getWidth(),chaveAzul.getHeight());
				item = new Item(id, chaveAzul, 0, bounds, somEstrelaOuro);
				break;
			// Mais vindo...
				default:
				item = null;
		}
		
		return item;
	}
	
	public void dispose(){
		somEstrelaBronze.dispose();
		somEstrelaOuro.dispose();
	}
	
}
