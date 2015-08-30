package com.handlinginputdemo.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HandlingInputDemo implements ApplicationListener, InputProcessor {
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private float positionX, positionY;
	
	@Override
	public void create () {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        batch = new SpriteBatch();

        texture = new Texture(Gdx.files.internal("dante.png"));
        sprite = new Sprite(texture);

        positionX = width/2 - sprite.getWidth()/2;
        positionY = width/2 - sprite.getWidth()/2;
        sprite.setPosition(width/2 - sprite.getWidth()/2, height/2 - sprite.getHeight()/2);
        Gdx.input.setInputProcessor(this);
	}

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

	@Override
	public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sprite.setPosition(positionX, positionY);
        batch.begin();
        sprite.draw(batch);
        batch.end();
	}

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public boolean keyDown(int keycode) {
        float moveAmount = 10.0f;
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
            moveAmount = 1f;

        if(keycode == Input.Keys.LEFT)
            positionX -= moveAmount;
        if(keycode == Input.Keys.RIGHT)
            positionX += moveAmount;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Buttons.LEFT){
            positionX = screenX - sprite.getWidth()/2;
            positionY = Gdx.graphics.getHeight() - screenY - sprite.getHeight()/2;
        }
        if(button == Input.Buttons.RIGHT){
            positionX = Gdx.graphics.getWidth()/2 - sprite.getWidth()/2;
            positionY = Gdx.graphics.getHeight()/2 - sprite.getHeight()/2;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
