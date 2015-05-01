package com.ls.ludica.personagens;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Item {
	private int id;
	private int pontos;
	private boolean coletado = false;
	
	private Rectangle bounds;
	private Sprite sprite;
	private Sound som;
	
	public Item(int id, Sprite sprite, int pontos, Rectangle bounds, Sound som){
		this.id = id;
		this.sprite = sprite;
		this.bounds = bounds;
		this.som = som;
		this.pontos = pontos;
	}


	public int coletar(Rectangle r) {
		int p = 0;
		if (!coletado && bounds.overlaps(r)) {
			coletado = true;
			p = pontos;
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
	
	public int getId() {
		return id;
	}

}
