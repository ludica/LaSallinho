package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.LaSalinhoGame;
import com.mygdx.game.Constantes;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "La Salinho";
		config.width = Constantes.LARGURA_TELA/2;
		config.height = Constantes.ALTURA_TELA/2;
		config.resizable = false;
		new LwjglApplication(new LaSalinhoGame(), config);
	}
}
