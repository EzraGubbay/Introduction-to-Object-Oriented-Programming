package motion;

import geometry.Point;

/**
 * Name: Ezra Gubbay.
 * ID: 209184308
 * Description - CollisionInfo class. Contains information about a collision, such as its point and the object
 * collided with.
 */
public class CollisionInfo {

    private final Point collisionPoint; // The point at which the collision occurs.
    private final Collidable collisionObject; // The collidable object involved in the collision.

    /**
     * Parameter constructor.
     *
     * @param collisionPoint  - The point at which the collision occurs.
     * @param collisionObject - The collidable object involved in the collision.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * The point at which the collision occurs.
     *
     * @return The point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * The collidable object involved in the collision.
     *
     * @return The collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
