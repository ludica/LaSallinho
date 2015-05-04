package com.ls.ludica.fabricas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.ls.ludica.personagens.Monstro;

/**
 * 
 * Classe utilitaria para a criacao dos monstros.
 * 
 */
public class FabricaMonstro {
	
	private final int COBRA = 1;
	private final int MOSQUITO = 2;
	private final int FANTASMA = 15;
	
	private final String SLIME_TEXTURA = "enemies/slime.png";
	private final TextureAtlas textureAtlas;
	
	
	private Texture slimeTextura;
	
	public FabricaMonstro(){
		textureAtlas = new TextureAtlas(Gdx.files.internal("enemies/monstros.atlas"));
		slimeTextura = new Texture(SLIME_TEXTURA);
	}
	
	public Monstro criarMonstro(int id, int posX, int posY){
		//Animation animacao;
		Sprite sprite;
		Rectangle bounds;
		switch(id){
			case COBRA:
				sprite = new Sprite(textureAtlas.findRegion("snake"));
				bounds = new Rectangle(posX,posY,sprite.getWidth(),
						sprite.getHeight());
				break;
			case MOSQUITO:
				sprite = new Sprite(textureAtlas.findRegion("flyFly1"));
				bounds = new Rectangle(posX,posY,sprite.getWidth(),
						sprite.getHeight());
				break;
			case FANTASMA:
				sprite = new Sprite(textureAtlas.findRegion("ghost_normal"));
				bounds = new Rectangle(posX,posY,sprite.getWidth(),
						sprite.getHeight());
				break;
			default:
				sprite = null;
				bounds = null;
		}
		return new Monstro(sprite, bounds, MathUtils.random(4f,7f));
		// Por enquanto só temos slime e ele não tem ID...
		// Mas a ideia eh ficar parecido com a criaItem()
		//Sprite sprite = new Sprite(slimeTextura);
		//sprite.setSize(90f, 50f);
		//Rectangle bounds = new Rectangle(posX,posY,sprite.getWidth(),sprite.getHeight());
		//return new Monstro(sprite, bounds, MathUtils.random(4f,7f));
	}
	
	public void dispose(){
		slimeTextura.dispose();
	}
	
}
