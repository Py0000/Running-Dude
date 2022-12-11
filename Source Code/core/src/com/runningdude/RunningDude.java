package com.runningdude;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RunningDude extends ApplicationAdapter {
	SpriteBatch batch;

	Texture background;
	Texture[] dudesArray;

	int dudeState = 0;

	// Called when the app is opened for the first time
	@Override
	public void create () {
		// Used to draw everything to the screen (visually)
		batch = new SpriteBatch();

		//Set up background image
		background = new Texture("bg.png");

		// Set up the game character
		dudesArray = new Texture[6];
		dudesArray[0] = new Texture("running-1.png");
		dudesArray[1] = new Texture("running-2.png");
		dudesArray[2] = new Texture("running-3.png");
		dudesArray[3] = new Texture("running-4.png");
		dudesArray[4] = new Texture("running-5.png");
		dudesArray[5] = new Texture("running-6.png");
	}

	// Runs over and over again until the app is done
	@Override
	public void render () {
		int gameWidth = Gdx.graphics.getWidth();
		int gameHeight = Gdx.graphics.getHeight();

		// Start things up
		batch.begin();

		// Background starts at pos 0 for both x and y coordinates and fills entire screen.
		batch.draw(background, 0,0, gameWidth, gameHeight);

		// "Loops" through the 6 different frame of dude
		// Creates an illusion of the dude "running"
		if (dudeState < 5) {
			dudeState++;
		} else {
			dudeState--;
		}

		Texture dude = dudesArray[dudeState];

		int dudeWidth = dude.getWidth();
		int dudeHeight = dude.getHeight();
		int dudeXCoord = (gameWidth / 2) - (dudeWidth / 2);
		int dudeYCoord = (gameHeight / 2) - (dudeHeight / 2);

		// Character will fill center of the screen
		batch.draw(dude, dudeXCoord, dudeYCoord);

		// Finish putting things on the screen
		batch.end();
	}

	// Called when the app is done running
	@Override
	public void dispose () {
		batch.dispose();

	}
}
