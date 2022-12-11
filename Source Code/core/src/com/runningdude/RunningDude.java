package com.runningdude;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class RunningDude extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	// Called when the app is opened for the first time
	@Override
	public void create () {
		// Used to draw everything to the screen (visually)
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	// Runs over and over again until the app is done
	@Override
	public void render () {
		// Make background colour
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		// Draw the image to the screen
		batch.draw(img, 0, 0);

		batch.end();
	}

	// Called when the app is done running
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
