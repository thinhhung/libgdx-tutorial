package com.thinhhung.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SceneSample implements ApplicationListener {

	public class MyActor extends Actor {
		Texture texture = new Texture(Gdx.files.internal("dante.png"));
		public boolean started = false;

		public MyActor () {
			setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
		}

        @Override
        public void draw(Batch batch, float alpha){
            batch.draw(texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                    this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                    texture.getWidth(), texture.getHeight(), false, false);
        }
	}

	private Stage stage;
	
	@Override
	public void create () {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		MyActor myActor = new MyActor();
        myActor.addAction(parallel(scaleTo(0.5f,0.5f,5f),rotateTo(90.0f,5f),moveTo(300.0f,0f,5f)));

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
