package sprites;

import biuoop.DrawSurface;
import gameplay.Counter;
import geometry.Point;
import gameplay.Game;
import java.awt.Color;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The ScoreIndicator class. Displays the player's score on the GUI.
 */
public class ScoreIndicator implements Sprite {
    private final Counter score; // The player's score.
    private final Point upperLeft; // The upper left corner of where the score should be displayed.
    private final Color color; // The color of the score's text.
    private final int fontSize; // The size of the score's font.

    /**
     * Constructor.
     * @param upperLeft - The upper left corner of where the score should be displayed.
     * @param score - The player's score.
     */
    public ScoreIndicator(Point upperLeft, Counter score) {
        this.score = score;
        this.upperLeft = upperLeft;
        this.color = Color.BLACK;
        this.fontSize = 20;
    }

    /**
     * Draws the Score Indicator on the GUI's draw surface.
     * @param d - A DrawSurface object to draw on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        int x = (int) this.upperLeft.getX(), y = (int) this.upperLeft.getY();
        String text = "Score: " + this.score.getValue();
        int fontSize = this.fontSize;
        d.setColor(this.color);
        d.drawText(x, y, text, fontSize);
    }

    /**
     * Adds this Score Indicator to the given game.
     * @param g - The gameplay.Game object representing the game we are currently playing.
     */
    @Override
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    /**
     * Notifies the ScoreIndicator that time has passed.
     */
    @Override
    public void timePassed() {

    }


}
