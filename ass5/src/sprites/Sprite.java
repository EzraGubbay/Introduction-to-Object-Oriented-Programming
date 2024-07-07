package sprites;

import biuoop.DrawSurface;
import gameplay.Game;

/**
 * Name: Ezra Gubbay.
 * ID: 209184308
 * Description - The Sprite interface. Represents an item that can be drawn on the GUI.
 */
public interface Sprite {

    /**
     * Draw the sprite to the screen.
     * @param d - A DrawSurface object to draw on
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that time has passed.
     */
    void timePassed();

    /**
     * Provide the sprite with the gameplay.Game, so it can add itself to it as needed.
     * @param g - The gameplay.Game object representing the game we are currently playing.
     */
    void addToGame(Game g);
}