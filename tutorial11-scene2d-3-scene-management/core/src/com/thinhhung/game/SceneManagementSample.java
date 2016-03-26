package com.thinhhung.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.Random;

public class SceneManagementSample implements ApplicationListener {
	// Create an Actor "Person" that displays the TextureRegion passed in
	class Person extends Actor {
		private TextureRegion _texture;

		public Person(TextureRegion texture){
			_texture = texture;
			setBounds(getX(),getY(),_texture.getRegionWidth(), _texture.getRegionHeight());

			this.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
					System.out.println("Touched" + getName());
					setVisible(false);
					return true;
				}
			});
		}

		// Implement the full form of draw() so we can handle rotation and scaling.
		public void draw(Batch batch, float alpha){
			batch.draw(_texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
					getScaleX(), getScaleY(), getRotation());
		}

		// This hit() instead of checking against a bounding box, checks a bounding circle.
		public Actor hit(float x, float y, boolean touchable){
			// If this Actor is hidden or untouchable, it cant be hit
			if(!this.isVisible() || this.getTouchable() == Touchable.disabled)
				return null;

			// Get center point of bounding circle, also known as the center of the rect
			float centerX = getWidth()/2;
			float centerY = getHeight()/2;

			// Calculate radius of circle
			float radius = (float) Math.sqrt(centerX * centerX +
					centerY * centerY);

			// And distance of point from the center of the circle
			float distance = (float) Math.sqrt(((centerX - x) * (centerX - x))
					+ ((centerY - y) * (centerY - y)));

			// If the distance is less than the circle radius, it's a hit
			if(distance <= radius) return this;

			// Otherwise, it's not
			return null;
		}
	}

	private Person[] people;
	private Stage stage;

	@Override
	public void create() {
		stage = new Stage();
		final TextureRegion personTexture = new TextureRegion(new Texture("dante.png"));

		people = new Person[100];

		// Create/seed our random number for positioning people randomly
		Random random = new Random();

		// Create 100 Person objects at random on screen locations
		for(int i = 0; i < 100; i++){
			people[i] = new Person(personTexture);

			//Assign the position of the jet to a random value within the screen boundaries
			people[i].setPosition(random.nextInt(Gdx.graphics.getWidth() - (int) people[i].getWidth())
					, random.nextInt(Gdx.graphics.getHeight() - (int) people[i].getHeight()));

			// Set the name of the Person to it's index within the loop
			people[i].setName(Integer.toString(i));

			// Add them to the stage
			stage.addActor(people[i]);
		}

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
