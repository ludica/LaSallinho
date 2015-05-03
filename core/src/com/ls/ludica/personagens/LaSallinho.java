package com.ls.ludica.personagens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.ls.ludica.game.Constantes;

public class LaSallinho {
	/*
	 * Se houver tempo:
	 * 
	 * Criar vetores de posicao, aceleracao e velocidade para uma movimentacao mais suave.
	 * 
	 */
	public int vidas = 5;
	public int pontos = 0;
	public boolean pulando = false;
	public boolean noChao = true;
	public boolean isDireita = true;
	public boolean movendo = false;
	public float puloMaximo;

	public Rectangle bounds;
	
	enum State {
		STANDING, WALKING, JUMPING, DOWNING
	}
	State state = State.STANDING;
	private TextureAtlas textureAtlas;
	TextureRegion[] regions;
	Animation stand;
	Animation jump;
	Animation down;
	Animation walk;
	// Usado para animacao
	private float stateTime = 0;
	

	public LaSallinho() {
		textureAtlas = new TextureAtlas(Gdx.files.internal("LaSallinho.pack"));
		stand = new Animation(0,textureAtlas.findRegion("lasallinho001"));
		jump = new Animation(0,textureAtlas.findRegion("lasallinho006"));
		down = new Animation(0,textureAtlas.findRegion("lasallinho007"));
		walk = new Animation(0.1f,textureAtlas.findRegion("lasallinho001"),
				textureAtlas.findRegion("lasallinho002"),textureAtlas.findRegion("lasallinho003"),
				textureAtlas.findRegion("lasallinho004"));
		walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		bounds = new Rectangle(Constantes.X_INICIAL, Constantes.Y_INICIAL, Constantes.BLOCO, (236f/146f)*Constantes.BLOCO);
	}

	/**
	 * 
	 * Processa o movimento de La Sallinho no eixo X (Botões A e D)
	 * 
	 * @param camadaColisao
	 */
	public void Mover(TiledMapTileLayer layerCollision) {
		if (!Gdx.input.isKeyPressed(Keys.D) && !Gdx.input.isKeyPressed(Keys.A)) movendo = false;
		ArrayList<Rectangle> parede = new ArrayList<Rectangle>();

		if (Gdx.input.isKeyPressed(Keys.A)) {
			movendo = true;
			isDireita = false;
			parede.clear();
			/*
			 * Pega os blocos, tiles, próximos de La Sallinho, a esquerda e insere na lista de possíveis 
			 * blocos de colisão
			 * 
			 */
			for (int j = (int) (bounds.y + 1) / Constantes.BLOCO; j <= (int) (bounds.y + bounds.height - 1) / Constantes.BLOCO; j++) {
				int i = (int) ((bounds.x - Constantes.VELOCIDADE) / Constantes.BLOCO);
				// A propriedade teto é usada somente no "morro" proximo a vila. Para La Sallinho nao ficar batendo no topo dos
				// blocos no topo do morro.
				if (layerCollision.getCell(i, j) != null && !layerCollision.getCell(i, j).getTile().getProperties().containsKey("nuvem")
						&& !layerCollision.getCell(i, j).getTile().getProperties().containsKey("teto")) {
					Rectangle solido = new Rectangle(i * Constantes.BLOCO, j * Constantes.BLOCO, Constantes.BLOCO, Constantes.BLOCO);
					parede.add(solido);
				}
			}
			if (bounds.x <= 0 + Constantes.VELOCIDADE) {
				while (bounds.x == 0) {
					bounds.x -= 0.1f;
				}
				bounds.x = 0;
				movendo = false;
			} else {
				bounds.setPosition(bounds.x - Constantes.VELOCIDADE, bounds.y);
				/*
				 * Aqui a colisao é checada. Só os blocos ao lado de La Sallinho sao checados.
				 * 
				 */
				for (Rectangle paredeParte : parede) {
					if (paredeParte.overlaps(bounds)) {
						bounds.setPosition(bounds.x + Constantes.VELOCIDADE,
								bounds.y);
						while (!paredeParte.overlaps(bounds)) {
							bounds.setPosition(bounds.x - 0.1f, bounds.y);
						}
						bounds.setPosition(bounds.x + 0.1f, bounds.y);
						movendo = false;
					}
				}
			}
		}

		if (Gdx.input.isKeyPressed(Keys.D)) {
			movendo = true;
			isDireita = true;
			parede.clear();
			/*
			 * Pega os blocos, tiles, próximos de La Sallinho, a direita e insere na lista de possíveis 
			 * blocos de colisão
			 * 
			 */
			for (int j = (int) (bounds.y + 1) / Constantes.BLOCO; j <= (int) (bounds.y + bounds.height - 1) / Constantes.BLOCO; j++) {
				int i = (int) ((bounds.x + bounds.width + Constantes.VELOCIDADE) / Constantes.BLOCO);
				// A propriedade teto é usada somente no "morro" proximo a vila. Para La Sallinho nao ficar batendo no topo dos
				// blocos no topo do morro.
				if (layerCollision.getCell(i, j) != null && !layerCollision.getCell(i, j).getTile().getProperties().containsKey("nuvem")
						&& !layerCollision.getCell(i, j).getTile().getProperties().containsKey("teto")) {
					Rectangle solido = new Rectangle(i * Constantes.BLOCO, j * Constantes.BLOCO, Constantes.BLOCO, Constantes.BLOCO);
					parede.add(solido);
				}
			}
			if (bounds.x >= Constantes.LARGURA_MAPA - bounds.width
					- Constantes.VELOCIDADE) {
				while (bounds.x == Constantes.LARGURA_MAPA - bounds.width) {
					bounds.x += 0.1f;
				}
				bounds.x = Constantes.LARGURA_MAPA - bounds.width;
				movendo = false;
			} else {
				bounds.setPosition(bounds.x + Constantes.VELOCIDADE, bounds.y);
				/*
				 * Aqui a colisao é checada. Só os blocos ao lado de La Sallinho sao checados.
				 * 
				 */
				for (Rectangle paredeParte : parede) {
					if (paredeParte.overlaps(bounds)) {
						bounds.setPosition(bounds.x - Constantes.VELOCIDADE,
								bounds.y);
						while (!paredeParte.overlaps(bounds)) {
							bounds.setPosition(bounds.x + 0.1f, bounds.y);
						}
						bounds.setPosition(bounds.x - 0.1f, bounds.y);
						movendo = false;
					}
				}
			}
		}
		if(Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.D)){
			movendo = false;
		}
	}

	public void Pular(TiledMapTileLayer layerCollision) {
		ArrayList<Rectangle> teto = new ArrayList<Rectangle>();
		if (noChao) {
			puloMaximo = bounds.y + Constantes.MAXJUMP;
		}
		if ((Gdx.input.isKeyPressed(Keys.SPACE) && noChao) || pulando) {
			pulando = true;
			noChao = false;
			teto.clear();
			/*
			 * Pega os blocos, tiles, próximos de La Sallinho, em cima e insere na lista de possíveis 
			 * blocos de colisão
			 * 
			 */
			for (int i = (int) (bounds.x + 1) / Constantes.BLOCO; i <= (int) (bounds.x + bounds.width - 1) / Constantes.BLOCO; i++) {
				int j = (int) ((bounds.y + bounds.height + Constantes.UPFORCE) / Constantes.BLOCO);
				// A propriedade teto é usada somente no "morro" proximo a vila. Para La Sallinho nao ficar batendo no topo dos
				// blocos no topo do morro.
				if (layerCollision.getCell(i, j) != null && !layerCollision.getCell(i, j).getTile().getProperties().containsKey("teto")) {
					Rectangle solido = new Rectangle(i * Constantes.BLOCO, j * Constantes.BLOCO, Constantes.BLOCO, Constantes.BLOCO);
					teto.add(solido);
				}
			}
			bounds.setPosition(bounds.x, bounds.y + Constantes.UPFORCE);
			/*
			 * Falta realizar a checagem do teto
			 */
			for (Rectangle tetoParte : teto) {
				if (tetoParte.overlaps(bounds)) {
					pulando = false;
				}
			}
			if (bounds.y >= puloMaximo) {
				pulando = false;
			}
		}
	}

	public void Gravidade(TiledMapTileLayer layerCollision) {
		ArrayList<Rectangle> chao = new ArrayList<Rectangle>();
		chao.clear();
		/*
		 * Pega os blocos, tiles, próximos de La Sallinho, abaixo dele, e insere na lista de possíveis 
		 * blocos de colisão
		 * 
		 */
		for (int i = (int) (bounds.x + 1) / Constantes.BLOCO; i <= (int) (bounds.x + bounds.width - 1) / Constantes.BLOCO; i++) {
			int j = (int) ((bounds.y - Constantes.GRAVIDADE) / Constantes.BLOCO);
			if (layerCollision.getCell(i, j) != null) {
				Rectangle solido;
				// checando se é uma nuvem...
				if(layerCollision.getCell(i, j).getTile().getProperties().containsKey("nuvem")){
					solido = new Rectangle(i * Constantes.BLOCO, j * Constantes.BLOCO, Constantes.BLOCO, 15f);
				} else
				solido = new Rectangle(i * Constantes.BLOCO, j * Constantes.BLOCO, Constantes.BLOCO, Constantes.BLOCO);
				chao.add(solido);
			}
		}
		noChao = false;
		bounds.setPosition(bounds.x, bounds.y - Constantes.GRAVIDADE);
		/*
		 * Aqui a colisao é checada. Só os blocos embaixo de La Sallinho sao checados.
		 * 
		 */
		for (Rectangle chaoParte : chao) {
			if (chaoParte.overlaps(bounds)) {
				bounds.setPosition(bounds.x, bounds.y + Constantes.GRAVIDADE);
				while (!chaoParte.overlaps(bounds)) {
					bounds.setPosition(bounds.x, bounds.y - 0.1f);
				}
				bounds.setPosition(bounds.x, bounds.y + 0.1f);
				noChao = true;
			}
		}
	}
	
	/**
	 * Retorna a textura a ser desenhada 
	 * 
	 * @return
	 */
	public TextureRegion Imagem(){
		if(pulando) state = State.JUMPING;
		else if(!noChao) state = State.DOWNING;
		else if(!movendo) state = State.STANDING;
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
	
	/**
	 * 
	 * "Teletransporte" para voltar ao inicio da fase depois de chegar no final
	 * 
	 * @param camadaTeleporte
	 */
	public void Teleporte (TiledMapTileLayer layerTeleporte){
		if(state == State.STANDING){	
			int x = (int) bounds.x/Constantes.BLOCO;
			int y = (int) bounds.y/Constantes.BLOCO;
			int id = 0;
			if (layerTeleporte.getCell(x, y) != null) {
				id = layerTeleporte.getCell(x, y).getTile().getId();
			}
			
			for (int i = 0; i < Constantes.LARGURA_MAPA_EM_BLOCO; i++) {
				for (int j = 0; j < Constantes.ALTURA_MAPA_EM_BLOCO; j++) {
					if (layerTeleporte.getCell(i, j) != null) {
						if(i!=x && i!=x-1 && i!=x+1 && j!=y){
							if(layerTeleporte.getCell(i, j).getTile().getId() == id){
								bounds.x = i*Constantes.BLOCO;
								bounds.y = j*Constantes.BLOCO;
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
	
	/**
	 * 
	 * Verifica se houve colisoes com algum monstro
	 * 
	 * @param r
	 * @return Se houve colisao ou nao
	 */
	public boolean InimigoColide(Rectangle r){
		if (bounds.overlaps(r)) {
			return true;
		}
		return false;
	}

	public void resetar(int x, int y){
		vidas = 5;
		pontos = 0;
		pulando = false;
		noChao = true;
		isDireita = true;
		movendo = false;
		bounds.x = x;
		bounds.y = y;
	}
	
	public void PerdeVida() {
		vidas -= 1;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
}
