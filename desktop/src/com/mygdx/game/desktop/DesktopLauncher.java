package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ls.ludica.game.AsAventurasDeLaSallinho;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "La Salinho";
		//config.width = Constantes.LARGURA_TELA/2;
		//config.height = Constantes.ALTURA_TELA/2;
		//config.resizable = false;
		config.width = 1128;
		config.height = 620;
		new LwjglApplication(new AsAventurasDeLaSallinho(), config);
	}
}
