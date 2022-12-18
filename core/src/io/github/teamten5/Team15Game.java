package io.github.teamten5;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.teamten5.views.MenuScreen;
import com.badlogic.gdx.Screen;


import com.badlogic.gdx.Game;


public class Team15Game extends Game{
	SpriteBatch batch;
	Texture img;
	public void create() {
		menuscreen = new MenuScreen(this);
		setScreen(menuscreen);
	}
	private MenuScreen menuscreen;
	public final static int MENU = 0;

	public void changeScreen(int screen) {
		switch (screen){
			case MENU:
			if (menuscreen == null) {
				menuscreen = new MenuScreen(this);
				this.setScreen(menuscreen);
				break;
			}
		}

	}

}
