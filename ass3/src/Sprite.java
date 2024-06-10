import biuoop.DrawSurface;

public interface Sprite {

    /**
     * Draw the sprite to the screen
     * @param d - A DrawSurface object to draw on
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that time has passed
     */
    void timePassed();

    /**
     * Provide the sprite with the Game, so it can add itself to it as needed.
     * @param g - The Game object representing the game we are currently playing.
     */
    void addToGame(Game g);
}