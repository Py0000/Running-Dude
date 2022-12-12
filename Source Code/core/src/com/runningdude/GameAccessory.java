package com.runningdude;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;
import java.util.Random;

public class GameAccessory {
    public static final String DIAMOND_FILE = "diamond.png";
    public static final String TOXIN_FILE = "toxin.png";
    public static final String FIRST_AID_FILE = "first-aid.png";

    private Texture accessory;

    public GameAccessory(String fileName) {
        accessory = new Texture(fileName);
    }

    public Texture getGameAccessory() {
        return accessory;
    }

    private void makeAccessory(List<Integer> xCoords, List<Integer> yCoords, int appWidth, int appHeight) {
        final int FLOOR = 180;
        Random random = new Random();
        float height = (random.nextFloat() * appHeight) + FLOOR;

        yCoords.add((int)height);
        xCoords.add(appWidth);
    }

    public int putAccessoryAndUpdateCount(List<Integer> xCoords, List<Integer> yCoords, int appWidth, int appHeight, int count, int gap) {
        // Put a diamond after every 100 iterations of render() function execution
        if (count < gap) {
            count++;
        }

        else {
            count = 0;
            makeAccessory(xCoords, yCoords, appWidth, appHeight);
        }

        return count;
    }

    private void shiftAccessoryLeft(List<Integer> xCoords, int index, int shiftFactor) {
        int xPos = xCoords.get(index);
        int modifiedXPos = xPos - shiftFactor;

        xCoords.set(index, modifiedXPos);
    }

    private void setAccessoryHitBox(List<Integer> xCoords, List<Integer> yCoords, List<Rectangle> parameters, int index) {
        int xPos = xCoords.get(index);
        int yPos = yCoords.get(index);
        int accessoryWidth = accessory.getWidth();
        int accessoryHeight = accessory.getHeight();
        Rectangle hitBox = new Rectangle(xPos, yPos, accessoryWidth, accessoryHeight);

        parameters.add(hitBox);
    }

    private void drawAccessory(SpriteBatch batch, List<Integer> xCoords, List<Integer> yCoords, int index) {
        batch.draw(accessory, xCoords.get(index), yCoords.get(index));
    }

    public void setUpAccessory(SpriteBatch batch, List<Integer> xCoords, List<Integer> yCoords, List<Rectangle> parameters, int index, int factor) {
        drawAccessory(batch, xCoords, yCoords, index);
        shiftAccessoryLeft(xCoords, index, factor);
        setAccessoryHitBox(xCoords, yCoords, parameters, index);
    }

    public boolean isHitBoxInRange(Rectangle range, Rectangle hitBox) {
        return Intersector.overlaps(range, hitBox);
    }
}
