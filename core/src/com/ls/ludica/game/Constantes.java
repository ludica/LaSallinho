package com.ls.ludica.game;

/**
 * 
 * Classe de Conveniencia...
 * Seus campos devem ser postos nos seus devidos lugares.
 * 
 */
public class Constantes {
	
	/*
	 * 
	 * A maioria desses campos não deve ser hard coded. 
	 * Eles vao ficar na classe Fase
	 * 
	 */

	public static final int TILESCALE = 70;        // tamanho de um bloco
	public static final int TILESHORIZONTAL = 200; // largura do mapa (em quantidade de blocos)
	public static final int TILESVERTICAL = 32;    // altura do mapa (em quantidade de blocos)
	public static final int LARGURA_TELA = TILESCALE * 20;
	public static final int ALTURA_TELA = TILESCALE * 10;
	
	// A informacao da posicao Inicial de La Sallinho na fase deve vim como atributo do mapa
	public static final int X_INICIAL = TILESCALE * 11; // posicao X inicial do La Sassinho
	public static final int Y_INICIAL = TILESCALE * 17; // posicao Y inicial do La Sallinho
	
	public static final float GRAVIDADE = 12f;
	public static final float UPFORCE = GRAVIDADE + 10f;
	public static final float VELOCIDADE = 10f;
        
	public static final float MAXJUMP = TILESCALE * 4.5f;
	
	public static final int IDITEM1 = 1;
	public static final int IDITEM2 = 2;
	
	public static final int LARGURA_MAP = TILESCALE * TILESHORIZONTAL; // largura do mapa (em px)
	public static final int ALTURA_MAP = TILESCALE * TILESVERTICAL;    // altura do mapa (em px)
}