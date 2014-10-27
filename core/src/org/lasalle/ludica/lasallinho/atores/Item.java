package org.lasalle.ludica.lasallinho.atores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Constantes;

public class Item {
	
	private final String SOM_BIBLIA = "sons/som_biblia.ogg";
	private final String SOM_LIVRO = "sons/som_livro.ogg";
	
	private int id;
	private int pontos;
	private boolean coletado = false;
	
	private Texture textura;
	private Rectangle bounds;
	private Sprite sprite;
	private Sound som;
	
	public Item(int id, Texture textura, int pontos, int posX, int posY){
		this.id = id;
		this.textura = textura;
		sprite = new Sprite(textura);
		sprite.setSize(Constantes.TILESCALE, Constantes.TILESCALE);		
		bounds = new Rectangle(posX,posY,sprite.getWidth(),sprite.getHeight());
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
			// é necessário dar dispose() no som!!! -Felipe
		}
		return p;
	}
	
	public boolean foiColetado(){
		return coletado;
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
	
	//para limpar a memória
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
