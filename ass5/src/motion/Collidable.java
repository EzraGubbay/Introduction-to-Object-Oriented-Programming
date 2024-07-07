package motion;

import geometry.Point;
import geometry.Rectangle;
import sprites.Ball;
import motion.Velocity;

/**
 * Name: Ezra Gubbay.
 * ID: 209184308
 * Description - Collidable interface. Represents an object in the game that can be collided with.
 */
public interface Collidable {

    /**
     * @return The "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

     /**
      * Notify the object that we collided with it at collisionPoint with a given velocity.
      * The return is the new velocity expected after the hit (based on
      * the force the object inflicted on us).
      * @param collisionPoint - The point of the collision
      * @param currentVelocity - The balls current velocity
      * @return The new velocity the ball should have after the collision
      */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
