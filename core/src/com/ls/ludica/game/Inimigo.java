package com.ls.ludica.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

/**
 * 
 * Essa classe deve ser reformulada
 *
 */

public class Inimigo {
	private Texture texture;
	public Rectangle bounds;
	public Sprite image;
	public float velocidade;
	public float direcao = 1;
	public float largura = 0;

	public Inimigo(float x, float y, float velocidade) {
		this.texture = new Texture(Gdx.files.internal("enemies/slime.png"));
		this.image = new Sprite(texture);
		image.setSize(50f/28f*50f, 50f);
		this.bounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
		this.velocidade = velocidade;
	}
	
	/**
	 * 
	 * Similar ao metodo de La Sallinho.
	 * 
	 * @param camadaColisao
	 */
	public void mover(TiledMapTileLayer layerCollision) {
		if(direcao > 0){
			largura = bounds.width;
			if(!image.isFlipX()) image.flip(true, false);
		}
		if(direcao < 0){
			largura = 0;
			if(image.isFlipX()) image.flip(true, false);
		}
		ArrayList<Rectangle> parede = new ArrayList<Rectangle>();
		parede.clear();
		for (int j = (int) (bounds.y + 1) / Constantes.TILESCALE; j <= (int) (bounds.y + bounds.height - 1) / Constantes.TILESCALE; j++) {
			int i = (int) ((bounds.x + largura + (velocidade*direcao)) / Constantes.TILESCALE);
			if (layerCollision.getCell(i, j) != null) {
				Rectangle solido = new Rectangle(i * Constantes.TILESCALE, j * Constantes.TILESCALE, Constantes.TILESCALE, Constantes.TILESCALE);
				parede.add(solido);
			}
		}
		bounds.setPosition(bounds.x + (velocidade*direcao), bounds.y);
		for (Rectangle paredeParte : parede) {
			if (paredeParte.overlaps(bounds)) {
				bounds.setPosition(bounds.x - (velocidade*direcao),
						bounds.y);
				while (!paredeParte.overlaps(bounds)) {
					bounds.setPosition(bounds.x + (0.1f*direcao), bounds.y);
				}
				bounds.setPosition(bounds.x - (0.1f*direcao), bounds.y);
				if(direcao > 0)direcao = -1;
				else direcao = 1;
			}
		}
	}

	/**
	 * 
	 * Similar ao metodo de La Sallinho.
	 * 
	 * @param camadaColisao
	 */
	public void gravidade(TiledMapTileLayer layerCollision) {
		ArrayList<Rectangle> chao = new ArrayList<Rectangle>();
		chao.clear();
		for (int i = (int) (bounds.x + 1) / Constantes.TILESCALE; i <= (int) (bounds.x + bounds.width - 1) / Constantes.TILESCALE; i++) {
			int j = (int) ((bounds.y - Constantes.GRAVIDADE) / Constantes.TILESCALE);
			if (layerCollision.getCell(i, j) != null) {
				Rectangle solido = new Rectangle(i * Constantes.TILESCALE, j * Constantes.TILESCALE, Constantes.TILESCALE, Constantes.TILESCALE);
				chao.add(solido);
			}
		}
		bounds.setPosition(bounds.x, bounds.y - Constantes.GRAVIDADE);
		for (Rectangle chaoParte : chao) {
			if (chaoParte.overlaps(bounds)) {
				bounds.setPosition(bounds.x, bounds.y + Constantes.GRAVIDADE);
				while (!chaoParte.overlaps(bounds)) {
					bounds.setPosition(bounds.x, bounds.y - 0.1f);
				}
				bounds.setPosition(bounds.x, bounds.y + 0.1f);
			}
		}
	}
	
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return bounds;
	}
}
