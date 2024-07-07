package sprites;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import motion.Collidable;
import motion.Velocity;
import play.Game;
import play.HitListener;
import play.HitNotifier;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

/**
 * Name: Ezra Gubbay.
 * ID: 209184308
 * Description - A collidable, rectangular block in the game. Can tell Ball what velocity it should have if it is
 * collided with.
 */
public class Block extends Rectangle implements Collidable, Sprite, HitNotifier {

    private Color color;
    private List<HitListener> hitListeners;

    /**
     * Parameter constructor.
     *
     * @param upperLeft - The upper-left point of the Block's rectangle
     * @param width     - The width of the Block's rectangle
     * @param height    - The height of the Block's rectangle
     */
    public Block(Point upperLeft, double width, double height) {
        super(upperLeft, width, height);
        this.color = Color.BLACK;
        this.hitListeners = new ArrayList<HitListener>();
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Accessor for the rectangle's upper left point.
     * @return The rectangle's upper left point.
     */
    public Point getUpperLeft() {
        return super.getUpperLeft();
    }

    /**
     * Receives a new point and sets the current upper left point to the received point.
     * @param upperLeft - The new point to set the Block's upper left point.
     */
    public void setUpperLeft(Point upperLeft) {
        super.setUpperLeft(upperLeft);
    }

    /**
     * Setter for the Block's color.
     * @param color - The color the Block should change to.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    /**
     * Draw the ball on the given DrawSurface.
     *
     * @param surface - A DrawSurface object to draw on
     */
    public void drawOn(DrawSurface surface) {
        int upperLeftX = (int) this.getUpperLeft().getX();
        int upperLeftY = (int) this.getUpperLeft().getY();
        int width = (int) this.getWidth();
        int height = (int) this.getHeight();
        surface.setColor(this.color);
        surface.fillRectangle(upperLeftX, upperLeftY, width, height);
        // We will give definition to the rectangle by drawing its frame in black, to distinguish this block from other
        // adjacent blocks.
        surface.setColor(Color.BLACK);
        surface.drawRectangle(upperLeftX, upperLeftY, width, height);
    }

    /**
     * Notify the block that time has passed.
     */
    @Override
    public void timePassed() {

    }

    /**
     * @return The "collision shape" of the object.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(super.getUpperLeft(), super.getWidth(), super.getHeight());
    }

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param collisionPoint  - The point of the collision
     * @param currentVelocity - The balls current velocity
     * @return The new velocity the ball should have after the collision
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx(), dy = currentVelocity.getDy();
        // Check if the collisionPoint hit Block horizontally or vertically.
        // We are checking both statement separately - this resolves an edge case where the Ball collides with a corner,
        // In this case, it should return exactly the way it came - velocity will be flipped in both directions.
        if (Point.threshold(collisionPoint.getX(), this.getUpperLeft().getX())
                || Point.threshold(collisionPoint.getX(), this.getUpperLeft().getX() + this.getWidth())) {
            // Collision is horizontal
            dx = -1 * currentVelocity.getDx();
        }
        if (Point.threshold(collisionPoint.getY(), this.getUpperLeft().getY())
                || Point.threshold(collisionPoint.getY(), this.getUpperLeft().getY() + this.getHeight())) {
            // Collision is vertical
            dy = -1 * currentVelocity.getDy();
        }

        // If the ball does not match this block's color, notify the hit listeners.
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }

        // Change the ball color
        hitter.setColor(this.color);
        return new Velocity(dx, dy);
    }

    /**
     * Provide the block with the play.Game, so it can add itself to it.
     * @param g - The play.Game object representing the game we are currently playing.
     */
    @Override
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    protected void notifyHit(Ball hitter) {
        // Copy the list of listeners before iteration.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);

        // Iterate over the list of listeners, notifying each of the hit event.
        for(HitListener hl: listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    protected boolean ballColorMatch(Ball hitter) {
        return hitter.getColor() == this.color;
    }

    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }
}
