package com.handlinginputdemo.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

public class HandlingInputDemo implements ApplicationListener, InputProcessor {
    private SpriteBatch batch;
    private BitmapFont font;
    private String message = "Bắt đầu chạm!";
    private int w,h;

    class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }

    private Map<Integer,TouchInfo> touches = new HashMap<Integer,TouchInfo>();

	@Override
	public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("arial-15.fnt"),false);
        font.setColor(Color.RED);
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        Gdx.input.setInputProcessor(this);
        for(int i = 0; i < 5; i++){
            touches.put(i, new TouchInfo());
        }
	}

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

	@Override
	public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        message = "";
        for(int i = 0; i < 5; i++){
            if(touches.get(i).touched)
                message += "Ngón tay:" + Integer.toString(i) + "chạm vào tọa độ:" +
                        Float.toString(touches.get(i).touchX) +
                        "," +
                        Float.toString(touches.get(i).touchY) +
                        "\n";

        }
        BitmapFont.TextBounds tb = font.getBounds(message);
        float x = w/2 - tb.width/2;
        float y = h/2 + tb.height/2;
        font.drawMultiLine(batch, message, x, y);

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
        return false;
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
        if(pointer < 5){
            touches.get(pointer).touchX = screenX;
            touches.get(pointer).touchY = screenY;
            touches.get(pointer).touched = true;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer < 5){
            touches.get(pointer).touchX = 0;
            touches.get(pointer).touchY = 0;
            touches.get(pointer).touched = false;
        }
        return true;
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
