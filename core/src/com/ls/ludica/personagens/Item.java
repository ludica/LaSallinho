package com.ls.ludica.personagens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.ls.ludica.game.Constantes;

public class Item {
	
	private final String SOM_BIBLIA = "sons/som_biblia.ogg";
	private final String SOM_LIVRO = "sons/som_livro.ogg";
	
	private int id;
	private int pontos;
	private boolean coletado = false;
	
	private Rectangle bounds;
	// Os campos abaixo devem ser disposed
	private Texture textura;
	private Sprite sprite;
	// --
	private Sound som;
	
	public Item(int id, Texture textura, int pontos, int posX, int posY){
		this.id = id;
		this.textura = textura;
		sprite = new Sprite(textura);
		sprite.setSize(31, 31);
		// o bloco - largura da imagem = item centralizado
		bounds = new Rectangle(posX+((Constantes.TILESCALE-31)/2),posY+((Constantes.TILESCALE-31)/2),sprite.getWidth(),sprite.getHeight());
		String somPath = id == 1 ? SOM_LIVRO : SOM_BIBLIA;
		som = Gdx.audio.newSound(Gdx.files.internal(somPath));
		
		this.pontos = pontos;
		
	}


	public int coletar(Rectangle r) {
		int p = 0;
		if (bounds.overlaps(r) && !coletado) {
			coletado = true;
			p = this.pontos;
			som.play();
		}
		return p;
	}
	
	public boolean foiColetado(){
		return coletado;
	}
	public void setNaoColetado(){
		coletado = false;
	}
	
	public int getPontos(){
		return pontos;
	}
	
	public Rectangle getBounds(){
		return bounds;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public Texture getTextura(){
		return textura;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
