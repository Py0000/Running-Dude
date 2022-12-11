package com.runningdude;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class GameCharacter {
    private static final String RUNNING_FRAME_0 = "running-1.png";
    private static final String RUNNING_FRAME_1 = "running-2.png";
    private static final String RUNNING_FRAME_2 = "running-3.png";
    private static final String RUNNING_FRAME_3 = "running-4.png";
    private static final String RUNNING_FRAME_4 = "running-5.png";
    private static final String RUNNING_FRAME_5 = "running-6.png";

    private static final String DIZZY_FRAME = "dizzy.png";

    private final float GRAVITY = 0.25f;
    private final float VELOCITY_ACCELERATOR = 3.75f;
    private final int JUMP_HEIGHT = -15;

    private Texture[] statesArray;
    private Texture dizzyState;

    public GameCharacter() {
        statesArray = new Texture[6];
        statesArray[0] = new Texture(RUNNING_FRAME_0);
        statesArray[1] = new Texture(RUNNING_FRAME_1);
        statesArray[2] = new Texture(RUNNING_FRAME_2);
        statesArray[3] = new Texture(RUNNING_FRAME_3);
        statesArray[4] = new Texture(RUNNING_FRAME_4);
        statesArray[5] = new Texture(RUNNING_FRAME_5);

        dizzyState = new Texture(DIZZY_FRAME);
    }

    public Texture[] getStatesArray(){
        return statesArray;
    }

    public Texture getDizzyState(){
        return dizzyState;
    }

    public float updateVelocity(float currentVelocity) {
        return currentVelocity + (GRAVITY * VELOCITY_ACCELERATOR);
    }

    public int getFallPosition(int currentHeight, int minHeight, float velocity) {
        currentHeight -= velocity;

        if (currentHeight <= minHeight) {
            currentHeight = minHeight;
        }

        return currentHeight;
    }

    public float getJumpPosition() {
        return JUMP_HEIGHT;
    }

    public Rectangle setGameCharacterHitRange(int xCoord, int yCoord, int width, int height) {
        return new Rectangle(xCoord, yCoord, width, height);
    }
}
