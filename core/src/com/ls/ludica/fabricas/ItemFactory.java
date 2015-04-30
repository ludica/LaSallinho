package com.ls.ludica.fabricas;

import com.badlogic.gdx.graphics.Texture;
import com.ls.ludica.personagens.Item;

public class ItemFactory {

	public static final int LIVRO = 1;
	public static final int BIBLIA = 2;
	
	public static final int LIVRO_PTS = 50;
	public static final int BIBLIA_PTS = 100;
	
	public static final String LIVRO_TEXTURA = "itens/star_bronze.png";
	public static final String BIBLIA_TEXTURA = "itens/star_gold.png";
	
	public Item criarItem(int id, int posX, int posY){
		Item item;
		
		switch(id){
			case BIBLIA:
				item = new Item(id, new Texture(BIBLIA_TEXTURA), BIBLIA_PTS, posX, posY);
				break;
			// Mais vindo...
			default:
				item = new Item(id, new Texture(LIVRO_TEXTURA), LIVRO_PTS, posX, posY);
				break;
			
		}
		
		return item;
	}
	
}
