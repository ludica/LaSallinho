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

	public static final int BLOCO = 70;        // tamanho de um bloco
	public static final int LARGURA_MAPA_EM_BLOCO = 200; // largura do mapa (em quantidade de blocos)
	public static final int ALTURA_MAPA_EM_BLOCO = 32;    // altura do mapa (em quantidade de blocos)
	public static final int LARGURA_TELA = BLOCO * 20;
	public static final int ALTURA_TELA = BLOCO * 10;
	
	// A informacao da posicao Inicial de La Sallinho na fase deve vim como atributo do mapa
	public static final int X_INICIAL = BLOCO * 11; // posicao X inicial do La Sassinho
	public static final int Y_INICIAL = BLOCO * 17; // posicao Y inicial do La Sallinho
	
	public static final float GRAVIDADE = 12f;
	public static final float UPFORCE = GRAVIDADE + 10f;
	public static final float VELOCIDADE = 10f;
        
	public static final float MAXJUMP = BLOCO * 4.5f;
	
	public static final int LARGURA_MAPA = BLOCO * LARGURA_MAPA_EM_BLOCO; // largura do mapa (em px)
	public static final int ALTURA_MAPA = BLOCO * ALTURA_MAPA_EM_BLOCO;    // altura do mapa (em px)
}