package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Itens {
	public static int hollybookTotal;
	public static int hollybookColetado;
	
	public Rectangle bounds;
	public boolean isColetado = false;
	public int pts = 50;
	private Texture texture;
	public Sprite image;

	public Itens(float x, float y) {
		this.texture = new Texture(Gdx.files.internal("penBook.png"));
		this.image = new Sprite(texture);
		image.setSize(Constantes.TILESCALE, Constantes.TILESCALE);
		this.bounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
	}

	public Itens(float x, float y, int pontos) {
		this.texture = new Texture(Gdx.files.internal("hollyBook.png"));
		this.image = new Sprite(texture);
		image.setSize(Constantes.TILESCALE, Constantes.TILESCALE);
		this.bounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
		this.pts = pontos;
	}

	public void Coletar(Rectangle r) {
		if (bounds.overlaps(r)) {
			if(!isColetado){
				if(this.pts == 100){
					Itens.hollybookColetado++;
				}
				isColetado = true;
				LaSalinho.pontos += pts;
			}
		}
	}
}
