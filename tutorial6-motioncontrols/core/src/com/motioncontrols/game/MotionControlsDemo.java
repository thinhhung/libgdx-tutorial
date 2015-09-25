package com.motioncontrols.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MotionControlsDemo extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    private BitmapFont bitmapFont;
    private String message = "Do something already!";
    private float highestY = 0.0f;
	
	@Override
	public void create () {
        spriteBatch = new SpriteBatch();
        bitmapFont = new BitmapFont(Gdx.files.internal("arial-15.fnt"),false);
        bitmapFont.setColor(Color.RED);
	}

    @Override

    public void dispose() {
        spriteBatch.dispose();
        bitmapFont.dispose();
    }

	@Override
	public void render () {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        int deviceAngle = Gdx.input.getRotation();
        Input.Orientation orientation = Gdx.input.getNativeOrientation();
        float accelerometerY = Gdx.input.getAccelerometerY();
        if(accelerometerY > highestY) {
            highestY = accelerometerY;
        }
        message = "Rotated to:" + Integer.toString(deviceAngle) + " degrees\n";
        message += "Orientation: ";
        switch(orientation) {
            case Landscape:
                message += " landscape.\n";
                break;
            case Portrait:
                message += " portrait. \n";
                break;
            default:
                message += " complete crap!\n";
                break;
        }
        message += "Resolution: " + Integer.toString(w) + "," + Integer.toString(h) + "\n";
        message += "Y axis accelerometer: " + Float.toString(accelerometerY) + " \n";
        message += "Highest Y value: " + Float.toString(highestY) + " \n";
        if(Gdx.input.isPeripheralAvailable(Input.Peripheral.Vibrator)){
            if(accelerometerY > 7){
                Gdx.input.vibrate(100);
            }
        }
        if(Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass)){
            message += "Azimuth:" + Float.toString(Gdx.input.getAzimuth()) + "\n";
            message += "Pitch:" + Float.toString(Gdx.input.getPitch()) + "\n";
            message += "Roll:" + Float.toString(Gdx.input.getRoll()) + "\n";
        }
        else{
            message += "No compass available\n";
        }

        bitmapFont.drawMultiLine(spriteBatch, message, 0, h);
        spriteBatch.end();
    }

    @Override

    public void resize(int width, int height) {
        spriteBatch.dispose();
        spriteBatch = new SpriteBatch();
        String resolution = Integer.toString(width) + "," + Integer.toString(height);
        Gdx.app.log("LOG", "Resolution change " + resolution);
    }
}
