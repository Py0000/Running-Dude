package com.runningdude;

import com.badlogic.gdx.graphics.Texture;

public class Background {
    private static final String BG_FILE = "bg.png";

    private Texture background;

    public Background() {
        background = new Texture(BG_FILE);
    }

    public Texture getBackground() {
        return background;
    }
}
