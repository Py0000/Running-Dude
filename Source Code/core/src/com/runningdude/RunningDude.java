package com.runningdude;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RunningDude extends ApplicationAdapter {
	SpriteBatch batch;

	// Called when the app is opened for the first time
	@Override
	public void create () {
		// Used to draw everything to the screen (visually)
		batch = new SpriteBatch();

	}

	// Runs over and over again until the app is done
	@Override
	public void render () {

	}

	// Called when the app is done running
	@Override
	public void dispose () {
		batch.dispose();

	}
}
