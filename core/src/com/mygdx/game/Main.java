package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


public class Main extends Game {

	TMan tman;
	Stage render;
	ExtendViewport extendViewport;
	VMTScreen vmtScreen;
	
	@Override
	public void create () {
		tman = new TMan();
		tman.load();
		extendViewport = new ExtendViewport(720,1280,
											1080,2400,new OrthographicCamera());
		extendViewport.apply();
		render = new Stage(extendViewport);
		vmtScreen = new VMTScreen();
		setScreen(vmtScreen);
		Gdx.input.setInputProcessor(render);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.89f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();



	}
	
	@Override
	public void dispose () {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	public static TMan getAssset()
	{
		return ((Main) Gdx.app.getApplicationListener()).tman;
	}

	public  static Stage getRender(){
		return ((Main) Gdx.app.getApplicationListener()).render;
	}


}
