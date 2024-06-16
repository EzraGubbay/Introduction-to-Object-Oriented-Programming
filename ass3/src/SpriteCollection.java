import java.util.ArrayList;
import biuoop.DrawSurface;

/**
 * Name: Ezra Gubbay.
 * ID: 209184308
 * Description - The SpriteCollection class. Represents a collection of all Sprites in a game.
 */
public class SpriteCollection {

    private final ArrayList<Sprite> sprites;

    /**
     * Constructor.
     */
    public SpriteCollection() {
        sprites = new ArrayList<>();
    }

    /**
     * Adds a Sprite to the Sprite collection.
     * @param s - A new Sprite to be added to the collection.
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * Call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        for (Sprite s : sprites) {
            s.timePassed();
        }
    }

    /**
     * Call drawOn(d) on all sprites.
     * @param d - A DrawSurface object to draw on
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : sprites) {
            s.drawOn(d);
        }
    }
}
