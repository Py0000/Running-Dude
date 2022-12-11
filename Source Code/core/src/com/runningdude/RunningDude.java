package com.runningdude;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class RunningDude extends ApplicationAdapter {
	// Fields required to setup the application
	SpriteBatch batch;
	int gameHeight;
	int gameWidth;

	// Fields related to background of the application
	Background background;

	// Fields related to the running character
	GameCharacter gameCharacter;
	Rectangle dudeRectangle; // Holds parameter of game character

	// Fields related to diamond accessory
	GameAccessory diamond;
	ArrayList<Integer> diamondXCoords = new ArrayList<>();
	ArrayList<Integer> diamondYCoords = new ArrayList<>();
	ArrayList<Rectangle> diamondParameters = new ArrayList<>(); //Holds the parameter of the diamond
	int diamondCount;
	final int DIAMOND_FREQUENCY = 100;
	final int DIAMOND_FACTOR = 5;

	// Fields related to toxin accessory
	GameAccessory toxin;
	ArrayList<Integer> toxinXCoords = new ArrayList<>();
	ArrayList<Integer> toxinYCoords = new ArrayList<>();
	ArrayList<Rectangle> toxinParameters = new ArrayList<>(); //Holds teh parameter of the toxin
	int toxinCount;
	final int TOXIN_FREQUENCY = 225;
	final int TOXIN_FACTOR = 9;



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


	// Handles the score
	int score = 0;
	BitmapFont font;

	// Handles Game State
	int gameState = 0;

	// Called when the app is opened for the first time
	@Override
	public void create () {
		// Used to draw everything to the screen (visually)
		batch = new SpriteBatch();

		// Intializes the app width and height on start up
		gameWidth = Gdx.graphics.getWidth();
		gameHeight = Gdx.graphics.getHeight();

		// Initialise dude vertical position on start up
		dudeYCoord = (gameHeight / 2);

		//Set up background
		background = new Background();

		// Set up the game character
		gameCharacter = new GameCharacter();

		// Set up the diamond
		diamond = new GameAccessory("diamond.png");

		// Set up the toxin
		toxin = new GameAccessory("toxin.png");

		// Set up the scoreboard
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(8);
	}


	// Runs over and over again until the app is done
	@Override
	public void render () {
		// Start things up
		batch.begin();

		// Background starts at pos 0 for both x and y coordinates and fills entire screen.
		Texture bg = background.getBackground();
		batch.draw(bg, 0,0, gameWidth, gameHeight);

		// Handles Game State
		// Game is ongoing
		if (gameState == 1) {
			// Put a toxin after every 250 iterations of render() function execution
			toxinCount = toxin.putAccessoryAndUpdateCount(toxinXCoords, toxinYCoords, gameWidth, gameHeight, toxinCount, TOXIN_FREQUENCY);

			// Clear everything in toxin rectangle
			toxinParameters.clear();

			// Draw the toxins on the screen
			for (int i = 0; i < toxinXCoords.size(); i++) {
				toxin.setUpAccessory(batch, toxinXCoords, toxinYCoords, toxinParameters, i, TOXIN_FACTOR);
			}

			// Put a diamond after every 100 iterations of render() function execution
			diamondCount = diamond.putAccessoryAndUpdateCount(diamondXCoords, diamondYCoords, gameWidth, gameHeight, diamondCount, DIAMOND_FREQUENCY);

			// Clear everything in diamond rectangle
			diamondParameters.clear();

			// Draw the diamonds on the screen
			for (int i = 0; i < diamondXCoords.size(); i++) {
				diamond.setUpAccessory(batch, diamondXCoords, diamondYCoords, diamondParameters, i, DIAMOND_FACTOR);
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
				if (dudeState < gameCharacter.getStatesArray().length - 1) {
					dudeState++;
				} else {
					dudeState--;
				}
			}

			// Set game character based on his state of the game
			Texture[] dudesArray = gameCharacter.getStatesArray();
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

		}

		else if (gameState == 0) {
			// Waiting to start
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}

		}


		else if (gameState == 2) {
			// Game over
			Texture dizzyDude = gameCharacter.getDizzyState();
			batch.draw(dizzyDude, (gameWidth / 2) - (dizzyDude.getWidth()/2), dudeYCoord);

			if (Gdx.input.justTouched()) {
				gameState = 1;
				dudeYCoord = (gameHeight / 2);
				score = 0;
				velocity = 0;
				diamondXCoords.clear();
				diamondYCoords.clear();
				diamondParameters.clear();
				diamondCount = 0;
				toxinXCoords.clear();
				toxinYCoords.clear();
				toxinParameters.clear();
				toxinCount = 0;
			}

		}


		// Check if game character collects a coin
		for (int i = 0; i < diamondParameters.size(); i++) {
			if (Intersector.overlaps(dudeRectangle, diamondParameters.get(i))) {
				score++;

				diamondParameters.remove(i);
				diamondXCoords.remove(i);
				diamondYCoords.remove(i);
				break;
			}
		}

		// Check if game character hits a toxin
		for (int i = 0; i < toxinParameters.size(); i++) {
			if (Intersector.overlaps(dudeRectangle, toxinParameters.get(i))) {
				gameState = 2;
			}
		}

		// Shows the score on the screen
		font.draw(batch, String.valueOf(score), gameWidth - 200, 150);

		// Finish putting things on the screen
		batch.end();
	}

	// Called when the app is done running
	@Override
	public void dispose () {
		batch.dispose();
	}
}
