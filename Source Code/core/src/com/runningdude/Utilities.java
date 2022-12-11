package com.runningdude;

public abstract class Utilities {
    // Used generally by the application
    public static final int MIN_VERTICAL_POS = 180;
    public static final int GAME_WAITING_STATE = 0;
    public static final int GAME_LIVE_STATE = 1;
    public static final int GAME_OVER_STATE = 2;

    // Involves with updating the frame of the game character
    public static final int DEFAULT_CHARACTER_STATE = 0;
    public static final int SPEED_CONTROL = 8;
    public static final float DEFAULT_VELOCITY = 0f;
    public static final int DEFAULT_TIMER_VALUE = 0;

    // Involves with generating the diamond
    public static final int DIAMOND_FREQUENCY = 100;
    public static final int DIAMOND_FACTOR = 5;
    public static final int DEFAULT_DIAMOND_COUNT = 0;

    // Involves with generating the toxin
    public static final int TOXIN_FREQUENCY = 225;
    public static final int TOXIN_FACTOR = 9;
    public static final int DEFAULT_TOXIN_COUNT = 0;

    // Involves with scoreboard
    public static final int DEFAULT_SCORE = 0;

    // Involves with Waiting Page
    public static final String WORD_1 = "TOUCH (ANYWHERE)";
    public static final String WORD_2 = "THE SCREEN";
    public static final String WORD_3 = "TO START";
}
