package com.runningdude;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Vector;

public class RunningDude extends ApplicationAdapter {
	// Fields required to setup the application
	SpriteBatch batch;
	int gameHeight;
	int gameWidth;

	// Handles Game State
	int gameState = Utilities.GAME_WAITING_STATE;

	// Fields related to background of the application
	Background background;

	// Fields related to the running character
	GameCharacter gameCharacter;
	Rectangle dudeRectangle; // Holds parameter of game character
	int dudeState = Utilities.DEFAULT_CHARACTER_STATE;
	int pauseTimer = Utilities.DEFAULT_TIMER_VALUE;

	// Takes care of making the game character fall
	float velocity = Utilities.DEFAULT_VELOCITY;
	int dudeYCoord = Utilities.MIN_VERTICAL_POS;

	// Fields related to diamond accessory
	GameAccessory diamond;
	ArrayList<Integer> diamondXCoords = new ArrayList<>();
	ArrayList<Integer> diamondYCoords = new ArrayList<>();
	ArrayList<Rectangle> diamondParameters = new ArrayList<>(); //Holds the parameter of the diamond
	int diamondCount;

	// Fields related to toxin accessory
	GameAccessory toxin;
	ArrayList<Integer> toxinXCoords = new ArrayList<>();
	ArrayList<Integer> toxinYCoords = new ArrayList<>();
	ArrayList<Rectangle> toxinParameters = new ArrayList<>(); //Holds teh parameter of the toxin
	int toxinCount;

	// Handles the score
	int score = 0;
	BitmapFont scoreFont;

	// Waiting Page
	Texture waitingPage;

	// Game Over State
	Texture gameOver;

	// Highscore State
	BitmapFont highScoreFont;
	Preferences pref;

	// Input Motion Detector
	OrthographicCamera cam;

	// Game Mode
	int gameMode = Utilities.NORMAL_MODE;
	Texture easyBox;
	Rectangle easyRec;
	Texture normalBox;
	Rectangle normalRec;
	Texture hardBox;
	Rectangle hardRec;

	// Called when the app is opened for the first time
	@Override
	public void create () {
		// Intializes the app width and height on start up
		gameWidth = Gdx.graphics.getWidth();
		gameHeight = Gdx.graphics.getHeight();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, gameWidth, gameHeight);

		// Used to draw everything to the screen (visually)
		batch = new SpriteBatch();

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
		scoreFont = new BitmapFont();
		scoreFont.setColor(Color.WHITE);
		scoreFont.getData().setScale(8);

		// Set up the waiting page
		waitingPage = new Texture("start.png");
		easyBox = new Texture("easy.png");
		normalBox = new Texture("normal.png");
		hardBox = new Texture("hard.png");

		// Set up game over
		gameOver = new Texture("game-over.png");

		// HighScore
		pref = Gdx.app.getPreferences("highscore");
		highScoreFont = new BitmapFont();
		highScoreFont.setColor(Color.WHITE);
		highScoreFont.getData().setScale(8);
	}

	// Runs over and over again until the app is done
	@Override
	public void render () {
		// Start cam up
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
		cam.update();

		// Start things up
		batch.setProjectionMatrix(cam.combined);
		batch.begin();

		// Background starts at pos 0 for both x and y coordinates and fills entire screen.
		Texture bg = background.getBackground();
		batch.draw(bg, 0,0, gameWidth, gameHeight);


		if (gameState == Utilities.GAME_WAITING_STATE) {
			// Waiting to start
			setUpWaitingPage();
			proceedToStartGame();
			//executeGameStart();
		}

		// Game is ongoing
		if (gameState == Utilities.GAME_LIVE_STATE) {
			executeGameLive();
		}

		if (gameState == Utilities.GAME_OVER_STATE) {
			Texture dizzyDude = gameCharacter.getDizzyState();
			int dizzyXCoord = (gameWidth / 2) - (dizzyDude.getWidth() / 2);
			batch.draw(dizzyDude, dizzyXCoord, dudeYCoord);

			showScore();
			saveHighScore(score);
			showGameOverPage();
			executeGameStart();
		}

		calculateScore();
		detectGameOver();

		// Finish putting things on the screen
		batch.end();
	}

	// Called when the app is done running
	@Override
	public void dispose () {
		batch.dispose();
	}

	private void showScore() {
		// Shows the score on the screen
		scoreFont.draw(batch, String.valueOf(score), gameWidth - 200, 150);

		// Shows the highscore on the screen
		highScoreFont.draw(batch, "Best: " + pref.getInteger("highscore", 0), 100, 150);
	}

	private void proceedToStartGame() {
		int xCoord = Gdx.input.getX();
		int yCoord = Gdx.input.getY();

		Vector3 input = new Vector3(xCoord, yCoord, 0);
		cam.unproject(input);

		if (Gdx.input.justTouched()) {
			if (easyRec.contains(input.x, input.y)) {
				gameMode = Utilities.EASY_MODE;
			}

			if (normalRec.contains(input.x, input.y)) {
				gameMode = Utilities.NORMAL_MODE;
			}

			if (hardRec.contains(input.x, input.y)) {
				gameMode = Utilities.HARD_MODE;
			}

			executeGameStart();
		}

	}

	private void setUpWaitingPage() {
		int xEasy = (gameWidth / 2) - (easyBox.getWidth() / 2);
		int yEasy = 3 * (gameHeight / 4) - (easyBox.getHeight() / 2);

		int xNormal = (gameWidth / 2) - (normalBox.getWidth() / 2);
		int yNormal = (gameHeight / 2) - (normalBox.getHeight() / 2);

		int xHard = (gameWidth / 2) - (hardBox.getWidth() / 2);
		int yHard = (gameHeight / 4) - (hardBox.getHeight() / 2);

		easyRec = new Rectangle(xEasy, yEasy, easyBox.getWidth(), easyBox.getHeight());
		normalRec = new Rectangle(xNormal, yNormal, normalBox.getWidth(), normalBox.getHeight());
		hardRec = new Rectangle(xHard, yHard, hardBox.getWidth(), hardBox.getHeight());

		batch.draw(easyBox, xEasy, yEasy);
		batch.draw(normalBox, xNormal, yNormal);
		batch.draw(hardBox, xHard, yHard);
	}

	private void showGameOverPage() {
		int xCoord = (gameWidth / 2) - (gameOver.getWidth() / 2);
		int yCoord = (gameHeight / 2) - (gameOver.getHeight() / 2);
		batch.draw(gameOver, xCoord, yCoord);
	}

	private void saveHighScore(int score) {
		int current = pref.getInteger("highscore", 0);

		if (score > current) {
			pref.putInteger("highscore", score);
			pref.flush();
		}
	}

	private void executeGameStart() {
		if (Gdx.input.justTouched()) {
			gameState = Utilities.GAME_LIVE_STATE;
			dudeYCoord = (gameHeight / 2);
			score = Utilities.DEFAULT_SCORE;
			velocity = Utilities.DEFAULT_VELOCITY;
			diamondXCoords.clear();
			diamondYCoords.clear();
			diamondParameters.clear();
			diamondCount = Utilities.DEFAULT_DIAMOND_COUNT;
			toxinXCoords.clear();
			toxinYCoords.clear();
			toxinParameters.clear();
			toxinCount = Utilities.DEFAULT_TOXIN_COUNT;
		}
	}

	private void executeGameLive() {
		printToxinsToScreen();
		printDiamondsToScreen();

		// Set game character based on his state of the game
		Texture[] dudesArray = gameCharacter.getStatesArray();
		Texture dude = dudesArray[dudeState];
		int dudeHeight = dude.getHeight();  // Precisely calibrate game character horizontal width
		int dudeWidth = dude.getWidth();
		int dudeXCoord = (gameWidth / 2) - (dudeWidth / 2);

		updateCharacterState();

		// Handles jump
		if (Gdx.input.justTouched()) {
			jump();
		}

		fall();

		// Character will fill center of the screen
		batch.draw(dude, dudeXCoord, dudeYCoord);

		// Set parameter of game character to his current position, height and width
		dudeRectangle = gameCharacter.setGameCharacterHitRange(dudeXCoord, dudeYCoord, dudeWidth, dudeHeight);

		showScore();
	}

	private void updateCharacterState() {
		// Updates Character state every 10 iterations of render() function execution
		if (pauseTimer < Utilities.SPEED_CONTROL[gameMode]) {
			pauseTimer++;
		} else {
			// Reset pause timer
			pauseTimer = Utilities.DEFAULT_TIMER_VALUE;

			// "Loops" through the 6 different frame of dude
			// Creates an illusion of the dude "running"
			if (dudeState < gameCharacter.getStatesArray().length - 1) {
				dudeState++;
			} else {
				dudeState--;
			}
		}
	}

	private void jump() {
		velocity = gameCharacter.getJumpPosition();
	}

	private void fall() {
		// Updates falling velocity based on gravity
		velocity = gameCharacter.updateVelocity(velocity);

		// Updates game character vertical position as he falls
		dudeYCoord = gameCharacter.getFallPosition(dudeYCoord, Utilities.MIN_VERTICAL_POS, velocity);
	}

	private void printToxinsToScreen() {
		// Put a toxin after every 250 iterations of render() function execution
		toxinCount = toxin.putAccessoryAndUpdateCount(toxinXCoords, toxinYCoords, gameWidth, gameHeight, toxinCount, Utilities.TOXIN_FREQUENCIES[gameMode]);

		// Clear everything in toxin rectangle
		toxinParameters.clear();

		// Draw the toxins on the screen
		for (int i = 0; i < toxinXCoords.size(); i++) {
			toxin.setUpAccessory(batch, toxinXCoords, toxinYCoords, toxinParameters, i, Utilities.TOXIN_FACTORS[gameMode]);
		}
	}

	private void printDiamondsToScreen() {
		// Put a diamond after every 100 iterations of render() function execution
		diamondCount = diamond.putAccessoryAndUpdateCount(diamondXCoords, diamondYCoords, gameWidth, gameHeight, diamondCount, Utilities.DIAMOND_FREQUENCIES[gameMode]);

		// Clear everything in diamond rectangle
		diamondParameters.clear();

		// Draw the diamonds on the screen
		for (int i = 0; i < diamondXCoords.size(); i++) {
			diamond.setUpAccessory(batch, diamondXCoords, diamondYCoords, diamondParameters, i, Utilities.DIAMOND_FACTORS[gameMode]);
		}
	}

	private void calculateScore() {
		// Check if game character collects a coin
		for (int i = 0; i < diamondParameters.size(); i++) {
			Rectangle diamondHitBox = diamondParameters.get(i);
			if (diamond.isHitBoxInRange(dudeRectangle, diamondHitBox)) {
				score++;

				diamondParameters.remove(i);
				diamondXCoords.remove(i);
				diamondYCoords.remove(i);
				break;
			}
		}
	}

	private void detectGameOver() {
		// Check if game character hits a toxin
		for (int i = 0; i < toxinParameters.size(); i++) {
			Rectangle toxinHitBox = toxinParameters.get(i);
			if (toxin.isHitBoxInRange(dudeRectangle, toxinHitBox)) {
				gameState = Utilities.GAME_OVER_STATE;
			}
		}
	}
}
