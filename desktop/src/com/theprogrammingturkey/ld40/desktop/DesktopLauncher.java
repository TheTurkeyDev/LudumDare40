package com.theprogrammingturkey.ld40.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.theprogrammingturkey.ld40.LD40;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "LudumDare 40";
		config.width = 1260;
		config.height = 720;
		config.resizable = false;
		new LwjglApplication(new LD40(), config);
	}
}
