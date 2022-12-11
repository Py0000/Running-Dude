package com.runningdude;

import com.badlogic.gdx.graphics.Texture;

public class GameCharacter {
    private static final String RUNNING_FRAME_0 = "running-1.png";
    private static final String RUNNING_FRAME_1 = "running-2.png";
    private static final String RUNNING_FRAME_2 = "running-3.png";
    private static final String RUNNING_FRAME_3 = "running-4.png";
    private static final String RUNNING_FRAME_4 = "running-5.png";
    private static final String RUNNING_FRAME_5 = "running-6.png";

    private static final String DIZZY_FRAME = "dizzy.png";

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
}
