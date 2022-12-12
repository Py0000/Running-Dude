package com.runningdude;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameMode {
    private static final String EASY_FILE = "easy.png";
    private static final String NORMAL_FILE = "normal.png";
    private static final String HARD_FILE = "hard.png";
    private static final String KILLER_FILE = "killer.png";
    public static final String GAMEOVER_FILE = "game-over.png";

    Texture easyBox;
    Rectangle easyRec;

    Texture normalBox;
    Rectangle normalRec;

    Texture hardBox;
    Rectangle hardRec;

    Texture killerBox;
    Rectangle killerRec;

    public GameMode() {
        easyBox = new Texture(EASY_FILE);
        normalBox = new Texture(NORMAL_FILE);
        hardBox = new Texture(HARD_FILE);
        killerBox = new Texture(KILLER_FILE);

        easyRec = null;
        normalRec = null;
        hardRec = null;
        killerRec = null;
    }

    public void setUpWaitingPage(SpriteBatch batch, int gameWidth, int gameHeight) {
        int modifiedGameWidth = gameWidth / 2;

        int xEasy = modifiedGameWidth - (easyBox.getWidth() / 2);
        int yEasy = 4 * (gameHeight / 5) - (easyBox.getHeight() / 2);

        int xNormal = modifiedGameWidth - (normalBox.getWidth() / 2);
        int yNormal = 3 * (gameHeight / 5) - (normalBox.getHeight() / 2);

        int xHard = modifiedGameWidth - (hardBox.getWidth() / 2);
        int yHard = 2 * (gameHeight / 5) - (hardBox.getHeight() / 2);

        int xKiller = modifiedGameWidth - (killerBox.getWidth() / 2);
        int yKiller = (gameHeight / 5) - (killerBox.getHeight() / 2);

        easyRec = new Rectangle(xEasy, yEasy, easyBox.getWidth(), easyBox.getHeight());
        normalRec = new Rectangle(xNormal, yNormal, normalBox.getWidth(), normalBox.getHeight());
        hardRec = new Rectangle(xHard, yHard, hardBox.getWidth(), hardBox.getHeight());
        killerRec = new Rectangle(xKiller, yKiller, killerBox.getWidth(), killerBox.getHeight());

        batch.draw(easyBox, xEasy, yEasy);
        batch.draw(normalBox, xNormal, yNormal);
        batch.draw(hardBox, xHard, yHard);
        batch.draw(killerBox, xKiller, yKiller);
    }

    public int setGameMode(Vector3 input, int gameMode) {
        if (easyRec.contains(input.x, input.y)) {
            gameMode = Utilities.EASY_MODE;
        }

        if (normalRec.contains(input.x, input.y)) {
            gameMode = Utilities.NORMAL_MODE;
        }

        if (hardRec.contains(input.x, input.y)) {
            gameMode = Utilities.HARD_MODE;
        }

        if (killerRec.contains(input.x, input.y)) {
            gameMode = Utilities.KILLER_MODE;
        }

        return gameMode;
    }

}
