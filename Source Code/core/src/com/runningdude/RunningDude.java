package com.runningdude;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RunningDude extends ApplicationAdapter {
	SpriteBatch batch;

	int gameHeight;
	int gameWidth;

	Texture background;
	Texture[] dudesArray;

	// Takes care of updating the frame of the game character
	final int speedControl = 9;
	final int defaultTimerValue = 0;

	int dudeState = 0;
	int pauseTimer = defaultTimerValue;

	// Takes care of making the game character fall
	final int minVerticalPos = 180;
	float gravity = 0.25f;
	float velocity = 0;
	int dudeYCoord = minVerticalPos;


	// Called when the app is opened for the first time
	@Override
	public void create () {
		// Used to draw everything to the screen (visually)
		batch = new SpriteBatch();

		// Intialise the app width and height on start up
		gameWidth = Gdx.graphics.getWidth();
		gameHeight = Gdx.graphics.getHeight();

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

		// Initialise dude vertical position on start up
		dudeYCoord = (gameHeight / 2);

	}

	// Runs over and over again until the app is done
	@Override
	public void render () {


		// Start things up
		batch.begin();

		// Background starts at pos 0 for both x and y coordinates and fills entire screen.
		batch.draw(background, 0,0, gameWidth, gameHeight);

		// Updates Character state every 10 iteration of render() function execution
		if (pauseTimer < speedControl) {
			pauseTimer++;
		} else {
			// Reset pause timer
			pauseTimer = defaultTimerValue;

			// "Loops" through the 6 different frame of dude
			// Creates an illusion of the dude "running"
			if (dudeState < dudesArray.length - 1) {
				dudeState++;
			} else {
				dudeState--;
			}
		}

		// Set game character based on his state of the game
		Texture dude = dudesArray[dudeState];

		// Updates falling velocity based on gravity
		velocity += gravity;

		// Updates game character vertical position as he falls
		dudeYCoord -= velocity;

		if (dudeYCoord <= minVerticalPos) {
			dudeYCoord = minVerticalPos;
		}

		// Precisely calibrate game character horizontal width
		int dudeWidth = dude.getWidth();
		int dudeXCoord = (gameWidth / 2) - (dudeWidth / 2);

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
