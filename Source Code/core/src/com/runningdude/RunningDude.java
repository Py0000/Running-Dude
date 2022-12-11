package com.runningdude;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class RunningDude extends ApplicationAdapter {
	SpriteBatch batch;

	int gameHeight;
	int gameWidth;

	Texture background;
	Texture[] dudesArray;
	Rectangle dudeRectangle; // Holds parameter of game character

	// Takes care of updating the frame of the game character
	final int speedControl = 10;
	final int defaultTimerValue = 0;

	int dudeState = 0;
	int pauseTimer = defaultTimerValue;

	// Takes care of making the game character fall
	final int minVerticalPos = 180;
	final float velocityMultipler = 3.75f;
	float gravity = 0.25f;
	float velocity = 0;
	int dudeYCoord = minVerticalPos;

	// Takes care of making the game character jump
	final int jumpHeight = -15;

	// Takes care of diamond position
	Random random = new Random();
	Texture diamond;
	ArrayList<Integer> diamondXPositions = new ArrayList<>();
	ArrayList<Integer> diamondYPositions = new ArrayList<>();
	ArrayList<Rectangle> diamondRectangles = new ArrayList<>(); //Holds the parameter of the diamond
	int diamondCount;

	// Takes care of toxins position
	Texture toxin;
	ArrayList<Integer> toxinXPositions = new ArrayList<>();
	ArrayList<Integer> toxinYPositions = new ArrayList<>();
	ArrayList<Rectangle> toxinRectangles = new ArrayList<>(); //Holds teh parameter of the toxin
	int toxinCount;


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

		// Set up the toxin
		diamond = new Texture("diamond.png");

		// Set up the toxin
		toxin = new Texture("toxin.png");
	}

	public void spreadDiamonds() {
		float height = (random.nextFloat() * gameHeight) + minVerticalPos;

		diamondYPositions.add((int)height);
		diamondXPositions.add(gameWidth);
	}

	public void spreadToxins() {
		float height = (random.nextFloat() * gameHeight) + minVerticalPos;

		toxinYPositions.add((int)height);
		toxinXPositions.add(gameWidth);
	}

	// Runs over and over again until the app is done
	@Override
	public void render () {
		// Start things up
		batch.begin();

		// Background starts at pos 0 for both x and y coordinates and fills entire screen.
		batch.draw(background, 0,0, gameWidth, gameHeight);

		// Put a toxin after every 100 iterations of render() function execution
		if (toxinCount < 250) {
			toxinCount++;
		} else {
			toxinCount = 0;
			spreadToxins();
		}

		// Clear everything in toxin rectangle
		toxinRectangles.clear();

		// Draw the toxins on the screen
		for (int i = 0; i < toxinXPositions.size(); i++) {
			batch.draw(toxin, toxinXPositions.get(i), toxinYPositions.get(i));

			// Moves to the left
			toxinXPositions.set(i, toxinXPositions.get(i) - 6);

			// Set parameter of toxin to its current position, height and width
			toxinRectangles.add(new Rectangle(toxinXPositions.get(i), toxinYPositions.get(i), toxin.getWidth(), toxin.getHeight()));
		}

		// Put a diamond after every 100 iterations of render() function execution
		if (diamondCount < 100) {
			diamondCount++;
		} else {
			diamondCount = 0;
			spreadDiamonds();
		}

		// Clear everything in diamond rectangle
		diamondRectangles.clear();

		// Draw the diamonds on the screen
		for (int i = 0; i < diamondXPositions.size(); i++) {
			batch.draw(diamond, diamondXPositions.get(i), diamondYPositions.get(i));

			// Moves to the left
			diamondXPositions.set(i, diamondXPositions.get(i) - 4);

			// Set parameter of diamond to its current position, height and width
			diamondRectangles.add(new Rectangle(diamondXPositions.get(i), diamondYPositions.get(i), diamond.getWidth(), diamond.getHeight()));
		}

		// Handles jump
		if (Gdx.input.justTouched()) {
			velocity = jumpHeight;
		}

		// Updates Character state every 10 iterations of render() function execution
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
		velocity += (gravity * velocityMultipler);

		// Updates game character vertical position as he falls
		dudeYCoord -= velocity;

		if (dudeYCoord <= minVerticalPos) {
			dudeYCoord = minVerticalPos;
		}

		// Precisely calibrate game character horizontal width
		int dudeHeight = dude.getHeight();
		int dudeWidth = dude.getWidth();
		int dudeXCoord = (gameWidth / 2) - (dudeWidth / 2);

		// Character will fill center of the screen
		batch.draw(dude, dudeXCoord, dudeYCoord);

		// Set parameter of game character to his current position, height and width
		dudeRectangle = new Rectangle(dudeXCoord, dudeYCoord, dudeWidth, dudeHeight);

		// Check if game character collects a coin
		for (int i = 0; i < diamondRectangles.size(); i++) {
			if (Intersector.overlaps(dudeRectangle, diamondRectangles.get(i))) {
				Gdx.app.log("Diamond", "Gotten it!!!");
			}
		}

		// Check if game character hits a toxin
		for (int i = 0; i < toxinRectangles.size(); i++) {
			if (Intersector.overlaps(dudeRectangle, toxinRectangles.get(i))) {
				Gdx.app.log("Toxin", "Oh No!!!");
			}
		}


		// Finish putting things on the screen
		batch.end();
	}

	// Called when the app is done running
	@Override
	public void dispose () {
		batch.dispose();
	}
}
