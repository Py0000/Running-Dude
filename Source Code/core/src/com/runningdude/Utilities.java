package com.runningdude;

public abstract class Utilities {
    // Used generally by the application
    public static final int MIN_VERTICAL_POS = 180;
    public static final int GAME_WAITING_STATE = 0;
    public static final int GAME_LIVE_STATE = 1;
    public static final int GAME_OVER_STATE = 2;

    // Involves with updating the frame of the game character
    public static final int DEFAULT_CHARACTER_STATE = 0;
    public static final int[] SPEED_CONTROL = {11, 8, 3};
    public static final float DEFAULT_VELOCITY = 0f;
    public static final int DEFAULT_TIMER_VALUE = 0;

    // Involves with generating the diamond
    public static final int[] DIAMOND_FREQUENCIES = {90, 100, 100};
    public static final int[] DIAMOND_FACTORS = {6, 10, 18};
    public static final int DEFAULT_DIAMOND_COUNT = 0;

    // Involves with generating the toxin
    public static final int[] TOXIN_FREQUENCIES = {225, 225, 125};
    public static final int[] TOXIN_FACTORS = {10, 15, 23};
    public static final int DEFAULT_TOXIN_COUNT = 0;

    // Involves with scoreboard
    public static final int DEFAULT_SCORE = 0;

    // Modes
    public static final int EASY_MODE = 0;
    public static final int NORMAL_MODE = 1; // Default Mode
    public static final int HARD_MODE = 2;
}
