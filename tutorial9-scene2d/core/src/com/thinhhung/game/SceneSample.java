package com.thinhhung.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SceneSample implements ApplicationListener {

	public class MyActor extends Actor {
		Texture texture = new Texture(Gdx.files.internal("dante.png"));
		float actorX = 0;
		float actorY = 0;
		public boolean started = false;

		public MyActor () {
			setBounds(actorX, actorY, texture.getWidth(), texture.getHeight());
			addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					((MyActor) event.getTarget()).started = true;
					return true;
				}
			});
		}
		
		@Override
		public void draw(Batch batch, float parentAlpha) {
			batch.draw(texture, actorX, actorY);
		}

		@Override
		public void act(float delta) {
			if (started) {
				actorX += 5;
			}
		}
	}

	private Stage stage;
	
	@Override
	public void create () {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		MyActor myActor = new MyActor();
		myActor.setTouchable(Touchable.enabled);
		stage.addActor(myActor);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
