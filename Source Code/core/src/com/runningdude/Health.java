package com.runningdude;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Health {
    private static final String FULL_HEALTH = "full-health.png";
    private static final String ACCEPTABLE_HEALTH = "acceptable-health.png";
    private static final String DANGER_HEALTH = "danger-health.png";

    Texture fullHealth;
    Texture accpetableHealth;
    Texture dangerHealth;

    public Health() {
        fullHealth = new Texture(FULL_HEALTH);
        accpetableHealth = new Texture(ACCEPTABLE_HEALTH);
        dangerHealth = new Texture(DANGER_HEALTH);
    }

    public void showHealthBar(SpriteBatch batch, int healthState, int gameWidth, int gameHeight) {
        if (healthState == Utilities.HEALTH_FULL) {
            batch.draw(fullHealth, gameWidth - 400, gameHeight - 150);
        }

        else if (healthState == Utilities.HEALTH_ACCEPTABLE) {
            batch.draw(accpetableHealth, gameWidth - 400, gameHeight - 150);
        }

        else if (healthState == Utilities.HEALTH_DANGER) {
            batch.draw(dangerHealth, gameWidth - 400, gameHeight - 150);
        }
    }

    public int updateHealth(int currentHealth, SpriteBatch batch, int gameWidth, int gameHeight, int mode) {
        if (mode == Utilities.DAMAGE_MODE) {
            if (currentHealth == Utilities.HEALTH_FULL) {
                batch.draw(accpetableHealth, gameWidth - 400, gameHeight - 150);
                return Utilities.HEALTH_ACCEPTABLE;
            }

            if (currentHealth == Utilities.HEALTH_ACCEPTABLE) {
                batch.draw(dangerHealth, gameWidth - 400, gameHeight - 150);
                return Utilities.HEALTH_DANGER;
            }
        }

        if (mode == Utilities.BLESSING_MODE) {
            if (currentHealth == Utilities.HEALTH_DANGER) {
                batch.draw(accpetableHealth, gameWidth - 400, gameHeight - 150);
                return Utilities.HEALTH_ACCEPTABLE;
            }

            if (currentHealth == Utilities.HEALTH_ACCEPTABLE) {
                batch.draw(fullHealth, gameWidth - 400, gameHeight - 150);
                return Utilities.HEALTH_FULL;
            }
        }

        // should not reach here
        batch.draw(dangerHealth, gameWidth - 400, gameHeight - 150);
        return Utilities.HEALTH_DANGER;
    }
}
