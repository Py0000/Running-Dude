package com.runningdude;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class ScoreBoard {
    private int score;
    private BitmapFont font;

    public ScoreBoard() {
        score = 0;

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(8);
    }

    public BitmapFont getScoreBoardFont() {
        return font;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int newScore) {
        score = newScore;
    }
}
