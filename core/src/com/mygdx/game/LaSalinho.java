package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

public class LaSalinho {
	public static int vidas = 5;
	public static int pontos = 0;
	public static boolean isJump = false;
	public static boolean isChao = true;
	public static boolean isDireita = true;
	public static boolean isMovendo = false;
	public static float maxJump;

	private Texture texture;
	public Rectangle bounds;
	
	enum State {
		STANDING, WALKING, JUMPING, DOWNING
	}
	State state = State.STANDING;
	TextureRegion[] regions;
	Animation stand;
	Animation jump;
	Animation down;
	Animation walk;
	private float stateTime = 0;

	public LaSalinho() {
		texture = new Texture(Gdx.files.internal("LasallinhoSprite.png"));
		regions = TextureRegion.split(texture, 146, 236)[0];
		stand = new Animation(0, regions[0]);
		jump = new Animation(0, regions[5]);
		down = new Animation(0, regions[6]);
		walk = new Animation(0.1f, regions[1], regions[2], regions[3], regions[4]);
		walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);// 
		bounds = new Rectangle(Constantes.X_INICIAL, Constantes.Y_INICIAL, Constantes.TILESCALE, (236f/146f)*Constantes.TILESCALE);
	}

	public void Mover(TiledMapTileLayer layerCollision) {
		if (!Gdx.input.isKeyPressed(Keys.D) && !Gdx.input.isKeyPressed(Keys.A)) isMovendo = false;
		ArrayList<Rectangle> parede = new ArrayList<Rectangle>();

		if (Gdx.input.isKeyPressed(Keys.A)) {
			isMovendo = true;
			isDireita = false;
			parede.clear();
			for (int j = (int) (bounds.y + 1) / Constantes.TILESCALE; j <= (int) (bounds.y + bounds.height - 1) / Constantes.TILESCALE; j++) {
				int i = (int) ((bounds.x - Constantes.VELOCIDADE) / Constantes.TILESCALE);
				if (layerCollision.getCell(i, j) != null) {
					Rectangle solido = new Rectangle(i * Constantes.TILESCALE, j * Constantes.TILESCALE, Constantes.TILESCALE, Constantes.TILESCALE);
					parede.add(solido);
				}
			}
			if (bounds.x <= 0 + Constantes.VELOCIDADE) {
				while (bounds.x == 0) {
					bounds.x -= 0.1f;
				}
				bounds.x = 0;
				isMovendo = false;
			} else {
				bounds.setPosition(bounds.x - Constantes.VELOCIDADE, bounds.y);
				for (Rectangle paredeParte : parede) {
					if (paredeParte.overlaps(bounds)) {
						bounds.setPosition(bounds.x + Constantes.VELOCIDADE,
								bounds.y);
						while (!paredeParte.overlaps(bounds)) {
							bounds.setPosition(bounds.x - 0.1f, bounds.y);
						}
						bounds.setPosition(bounds.x + 0.1f, bounds.y);
						isMovendo = false;
					}
				}
			}
		}

		if (Gdx.input.isKeyPressed(Keys.D)) {
			isMovendo = true;
			isDireita = true;
			parede.clear();
			for (int j = (int) (bounds.y + 1) / Constantes.TILESCALE; j <= (int) (bounds.y + bounds.height - 1) / Constantes.TILESCALE; j++) {
				int i = (int) ((bounds.x + bounds.width + Constantes.VELOCIDADE) / Constantes.TILESCALE);
				if (layerCollision.getCell(i, j) != null) {
					Rectangle solido = new Rectangle(i * Constantes.TILESCALE, j * Constantes.TILESCALE, Constantes.TILESCALE, Constantes.TILESCALE);
					parede.add(solido);
				}
			}
			if (bounds.x >= Constantes.LARGURA_MAP - bounds.width
					- Constantes.VELOCIDADE) {
				while (bounds.x == Constantes.LARGURA_MAP - bounds.width) {
					bounds.x += 0.1f;
				}
				bounds.x = Constantes.LARGURA_MAP - bounds.width;
				isMovendo = false;
			} else {
				bounds.setPosition(bounds.x + Constantes.VELOCIDADE, bounds.y);
				for (Rectangle paredeParte : parede) {
					if (paredeParte.overlaps(bounds)) {
						bounds.setPosition(bounds.x - Constantes.VELOCIDADE,
								bounds.y);
						while (!paredeParte.overlaps(bounds)) {
							bounds.setPosition(bounds.x + 0.1f, bounds.y);
						}
						bounds.setPosition(bounds.x - 0.1f, bounds.y);
						isMovendo = false;
					}
				}
			}
		}
		if(Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.D)){
			isMovendo = false;
		}
	}

	public void Pular(TiledMapTileLayer layerCollision) {
		ArrayList<Rectangle> teto = new ArrayList<Rectangle>();
		if (isChao) {
			LaSalinho.maxJump = bounds.y + Constantes.MAXJUMP;
		}
		if ((Gdx.input.isKeyPressed(Keys.SPACE) && isChao) || isJump) {
			isJump = true;
			isChao = false;
			teto.clear();
			for (int i = (int) (bounds.x + 1) / Constantes.TILESCALE; i <= (int) (bounds.x + bounds.width - 1) / Constantes.TILESCALE; i++) {
				int j = (int) ((bounds.y + bounds.height + Constantes.UPFORCE) / Constantes.TILESCALE);
				if (layerCollision.getCell(i, j) != null) {
					Rectangle solido = new Rectangle(i * Constantes.TILESCALE, j * Constantes.TILESCALE, Constantes.TILESCALE, Constantes.TILESCALE);
					teto.add(solido);
				}
			}
			bounds.setPosition(bounds.x, bounds.y + Constantes.UPFORCE);
			for (Rectangle tetoParte : teto) {
				if (tetoParte.overlaps(bounds)) {
					isJump = false;
				}
			}
			if (bounds.y >= LaSalinho.maxJump) {
				isJump = false;
			}
		}
	}

	public void Gravidade(TiledMapTileLayer layerCollision) {
		ArrayList<Rectangle> chao = new ArrayList<Rectangle>();
		chao.clear();
		for (int i = (int) (bounds.x + 1) / Constantes.TILESCALE; i <= (int) (bounds.x + bounds.width - 1) / Constantes.TILESCALE; i++) {
			int j = (int) ((bounds.y - Constantes.GRAVIDADE) / Constantes.TILESCALE);
			if (layerCollision.getCell(i, j) != null) {
				Rectangle solido = new Rectangle(i * Constantes.TILESCALE, j * Constantes.TILESCALE, Constantes.TILESCALE, Constantes.TILESCALE);
				chao.add(solido);
			}
		}
		isChao = false;
		bounds.setPosition(bounds.x, bounds.y - Constantes.GRAVIDADE);
		for (Rectangle chaoParte : chao) {
			if (chaoParte.overlaps(bounds)) {
				bounds.setPosition(bounds.x, bounds.y + Constantes.GRAVIDADE);
				while (!chaoParte.overlaps(bounds)) {
					bounds.setPosition(bounds.x, bounds.y - 0.1f);
				}
				bounds.setPosition(bounds.x, bounds.y + 0.1f);
				isChao = true;
			}
		}
	}
	
	public TextureRegion Imagem(){
		if(isJump) state = State.JUMPING;
		else if(!isChao) state = State.DOWNING;
		else if(!isMovendo) state = State.STANDING;
		else state = State.WALKING;
		
		switch (state){
		case STANDING:
			return stand.getKeyFrame(getStateTime());
		case WALKING:
			return walk.getKeyFrame(getStateTime());
		case JUMPING:
			return jump.getKeyFrame(getStateTime());
		case DOWNING:
			return down.getKeyFrame(getStateTime());
		default:
			return stand.getKeyFrame(getStateTime());
		}
	}
	
	public void Teleporte (TiledMapTileLayer layerTeleporte){
		if(state == State.STANDING){	
			int x = (int) bounds.x/Constantes.TILESCALE;
			int y = (int) bounds.y/Constantes.TILESCALE;
			int id = 0;
			if (layerTeleporte.getCell(x, y) != null) {
				id = layerTeleporte.getCell(x, y).getTile().getId();
			}
			
			for (int i = 0; i < Constantes.TILESHORIZONTAL; i++) {
				for (int j = 0; j < Constantes.TILESVERTICAL; j++) {
					if (layerTeleporte.getCell(i, j) != null) {
						if(i!=x && i!=x-1 && i!=x+1 && j!=y){
							if(layerTeleporte.getCell(i, j).getTile().getId() == id){
								bounds.x = i*Constantes.TILESCALE;
								bounds.y = j*Constantes.TILESCALE;
							}
						}
					}
				}
			}
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean InimigoColide(Rectangle r){
		if (bounds.overlaps(r)) {
			return true;
		}
		return false;
	}

	public static void PerdeVida() {
		LaSalinho.vidas -= 1;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
}
