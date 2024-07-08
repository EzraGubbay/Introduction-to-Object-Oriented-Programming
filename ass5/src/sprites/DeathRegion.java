package sprites;

import geometry.Point;
import motion.Collidable;
import motion.Velocity;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The DeathRegion class. A Block that destroys balls when hit.
 */
public class DeathRegion extends Block implements Sprite, Collidable {

    /**
     * Constructor.
     * @param upperLeft - The upper left point of the DeathRegion.
     * @param width - The width of the DeathRegion.
     * @param height - The height of the DeathRegion.
     */
    public DeathRegion(Point upperLeft, double width, double height) {
        super(upperLeft, width, height);
    }

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The method notifies the BallRemover hit listener that this Death Region is subscribed to of the hit.
     * Arbitrarily returns the current velocity. The BallRemover listener will remove the ball before it moves again.
     * @param hitter - The ball that is hitting the collidable.
     * @param collisionPoint - The point of the collision
     * @param currentVelocity - The balls current velocity
     * @return Arbitrarily returns the current velocity.
     * The BallRemover listener will remove the ball before it moves again.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        super.notifyHit(hitter);
        return currentVelocity;
    }
}
