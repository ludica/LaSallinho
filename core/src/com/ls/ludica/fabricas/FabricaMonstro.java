package com.ls.ludica.fabricas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.ls.ludica.personagens.Monstro;

/**
 * 
 * Classe utilitaria para a criacao dos monstros.
 * 
 */
public class FabricaMonstro {
	
	private final String SLIME_TEXTURA = "enemies/slime.png";
	
	private Texture slimeTextura;
	
	public FabricaMonstro(){
		slimeTextura = new Texture(SLIME_TEXTURA);
	}
	
	public Monstro criarMonstro(int id, int posX, int posY){
		// Por enquanto só temos slime e ele não tem ID...
		// Mas a ideia eh ficar parecido com a criaItem()
		Sprite sprite = new Sprite(slimeTextura);
		sprite.setSize(90f, 50f);
		Rectangle bounds = new Rectangle(posX,posY,sprite.getWidth(),sprite.getHeight());
		return new Monstro(sprite, bounds, MathUtils.random(4f,7f));
	}
	
	public void dispose(){
		slimeTextura.dispose();
	}
	
}
