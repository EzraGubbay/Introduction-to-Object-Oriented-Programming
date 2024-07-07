package motion;

import geometry.Line;
import geometry.Point;
import java.util.ArrayList;

/**
 * Name: Ezra Gubbay.
 * ID: 209184308
 * Description - The play.Game Environment. Manages the logic in the game and calculates collision points for Ball.
 */
public class GameEnvironment {

    private final ArrayList<Collidable> collidables; // A list of all collidable objects in the game.

    /**
     * Parameter constructor.
     * @param collidables - A list of all collidable objects in the game.
     */
    public GameEnvironment(ArrayList<Collidable> collidables) {
        this.collidables = collidables;
    }

    /**
     * Add the given collidable to the environment.
     * @param c - A new Collidable object that we will add to the current list of collidables.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * @param trajectory - The trajectory the Ball is about to travel assuming it doesn't collide with anything.
     * @return If a collision occurred, a collisionInfo object with the information about the collision, otherwise null
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        ArrayList<CollisionInfo> collisionInfos = new ArrayList<>();
        CollisionInfo closestCollision = null;
        Point collisionPoint = null, tempPoint = null;
        Collidable collisionCollidable = null;
        double closestDistance;

        // Get all the closest collision points between the trajectory of the Ball and each collidable object (if any).
        for (Collidable c : this.collidables) {
            tempPoint = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (tempPoint != null) {
                collisionInfos.add(new CollisionInfo(tempPoint, c));
            }
        }

        // If the list is empty, there are no collisions - return null.
        if (collisionInfos.isEmpty()) {
            return null;
        }

        // If we reached here, there is definitely a collision with Ball.
        // Out of all closest collision points, get the one that is closest to the start of trajectory.
        closestCollision = collisionInfos.get(0);
        closestDistance = trajectory.start().distance(closestCollision.collisionPoint());

        for (CollisionInfo ci : collisionInfos) {
            double epsilon = Point.getEpsilon();
            if (trajectory.start().distance(ci.collisionPoint()) + epsilon < closestDistance) {
                closestCollision = ci;
                closestDistance = trajectory.start().distance(ci.collisionPoint());
            }
        }

        return closestCollision;
    }

    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }
}
