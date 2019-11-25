package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.screens.LoadingScreen;

public class MyGdxGame extends Game {

	Texture img;
	public Stage stage;
	public ShaderProgram shaderProgram;

	@Override
	public void create() {
		stage = new Stage(new StretchViewport(720, 1280));

		shaderProgram = new ShaderProgram(
				Gdx.files.internal("shaders/mask.vert"),
				Gdx.files.internal("shaders/mask.frag"));
		if (!shaderProgram.isCompiled()) {
			Gdx.app.error("Shader1", shaderProgram.getLog());
			shaderProgram = null;
		} else {
			shaderProgram.begin();
			shaderProgram.setUniformMatrix("u_projTrans", stage.getCamera().combined);
			shaderProgram.setUniformi("u_mask", 1);
			shaderProgram.end();
		}

		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);
		setScreen(new LoadingScreen(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (stage != null) {
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		if (stage != null)
			stage.getViewport().setScreenSize(width, height);
	}

	@Override
	public void dispose() {

		img.dispose();
	}
}