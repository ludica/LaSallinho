package com.ls.ludica.personagens;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.ls.ludica.game.Constantes;

/**
 * 
 * Essa classe deve ser reformulada
 *
 */

public class Monstro {
	private Rectangle bounds;
	private Sprite sprite;
	private float velocidade;
	private float direcao = 1;
	private float largura = 0;
	private Animation animacao;

	public Monstro(Sprite sprite, Rectangle bounds, float velocidade) {
		this.sprite = sprite;
		this.bounds = bounds;
		this.velocidade = velocidade;
	}
	public Monstro(Animation animacao, Rectangle bounds, float velocidade) {
		this.sprite = null;
		this.animacao = animacao;
		this.bounds = bounds;
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
			if(!sprite.isFlipX()) sprite.flip(true, false);
		}
		if(direcao < 0){
			largura = 0;
			if(sprite.isFlipX()) sprite.flip(true, false);
		}
		ArrayList<Rectangle> parede = new ArrayList<Rectangle>();
		parede.clear();
		for (int j = (int) (bounds.y + 1) / Constantes.BLOCO; j <= (int) (bounds.y + bounds.height - 1) / Constantes.BLOCO; j++) {
			int i = (int) ((bounds.x + largura + (velocidade*direcao)) / Constantes.BLOCO);
			if (layerCollision.getCell(i, j) != null) {
				Rectangle solido = new Rectangle(i * Constantes.BLOCO, j * Constantes.BLOCO, Constantes.BLOCO, Constantes.BLOCO);
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
		for (int i = (int) (bounds.x + 1) / Constantes.BLOCO; i <= (int) (bounds.x + bounds.width - 1) / Constantes.BLOCO; i++) {
			int j = (int) ((bounds.y - Constantes.GRAVIDADE) / Constantes.BLOCO);
			if (layerCollision.getCell(i, j) != null) {
				Rectangle solido = new Rectangle(i * Constantes.BLOCO, j * Constantes.BLOCO, Constantes.BLOCO, Constantes.BLOCO);
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
		return bounds;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
}
