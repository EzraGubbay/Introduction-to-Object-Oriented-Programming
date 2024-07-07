package sprites;

import geometry.Point;
import motion.Collidable;
import motion.Velocity;

public class DeathRegion extends Block implements Sprite, Collidable {

    public DeathRegion(Point upperLeft, double width, double height) {
        super(upperLeft, width, height);
    }

    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        super.notifyHit(hitter);
        return currentVelocity;
    }
}
