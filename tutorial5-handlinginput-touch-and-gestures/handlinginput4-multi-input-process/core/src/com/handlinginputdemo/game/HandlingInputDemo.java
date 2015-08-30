package com.handlinginputdemo.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

public class HandlingInputDemo implements ApplicationListener, GestureDetector.GestureListener, InputProcessor {
    private SpriteBatch batch;
    private BitmapFont font;
    private String message = "Không có cử chỉ nào!";
    private int w,h;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("arial-15.fnt"),false);
        font.setColor(Color.RED);
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new GestureDetector(this));
        inputMultiplexer.addProcessor(this);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        message = "Touch down!";
        Gdx.app.log("INFO", message);
        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        message = "Tap được thực hiện, ngón tay " + Integer.toString(button);
        Gdx.app.log("INFO", message);
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        message = "Long press được thực hiện";
        Gdx.app.log("INFO", message);
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        message = "Fling được thực hiện, velocity:" + Float.toString(velocityX) +
                "," + Float.toString(velocityY);
        Gdx.app.log("INFO", message);
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        message = "Pan được thực hiện, delta:" + Float.toString(deltaX) +
                "," + Float.toString(deltaY);
        Gdx.app.log("INFO", message);
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        message = "Zoom được thực hiện, initial Distance:" + Float.toString(initialDistance) +
                " Distance: " + Float.toString(distance);
        Gdx.app.log("INFO", message);
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        message = "Pinch được thực hiện";
        Gdx.app.log("INFO", message);
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        message = "Key Down";
        Gdx.app.log("INFO", message);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        message = "Key Up";
        Gdx.app.log("INFO", message);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        message = "Key Typed";
        Gdx.app.log("INFO", message);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        message = "Touch Down";
        Gdx.app.log("INFO", message);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        message = "Touch up";
        Gdx.app.log("INFO", message);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        message = "Touch Dragged";
        Gdx.app.log("INFO", message);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        message = "Mouse moved";
        Gdx.app.log("INFO", message);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        message = "Scrolled";
        Gdx.app.log("INFO", message);
        return false;
    }
}
