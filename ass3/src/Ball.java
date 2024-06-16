

import biuoop.DrawSurface;

import java.awt.Color;
import java.util.Random;

/**
 * Name: Ezra Gubbay.
 * ID: 209184308
 * Description: The Ball class
 */
public class Ball implements Sprite {

    /**
     * The ball class.
     * location - the center of the ball
     * size - ball's radius
     * color - ball's color
     * velocity - ball's velocity
     * inGrayRectangle - describes if the ball was generated in the gray rectangle or not
     * frame - holds a rectangle that the ball is residing inside of
     * gray - holds a gray rectangle that the ball is either inside or outside of
     */
    private Point location;
    private final int size;
    private final Color color;
    private Velocity velocity;
    private GameEnvironment ge;

    /**
     * Parameter constructor.
     *
     * @param center - The location of the Ball
     * @param r      - The radius of the Ball
     * @param color  - The color of the Ball
     */
    public Ball(Point center, int r, Color color) {
        this.location = center;
        this.size = r;
        this.color = color;
        this.setVelocity(this.calculateInitialVelocity());
    }

    /**
     * Parameter constructor.
     *
     * @param x     - The X coordinate of the location of the Ball
     * @param y     - The Y coordinate of the location of the Ball
     * @param r     - The radius of the Ball
     * @param color - The color of the Ball
     */
    public Ball(double x, double y, int r, Color color) {
        this.location = new Point(x, y);
        this.size = r;
        this.color = color;
        this.setVelocity(this.calculateInitialVelocity());
    }

    /**
     * Calculates and returns a velocity based on a given ball size. The larger the ball, the slower the speed.
     *
     * @return A Velocity object containing the velocity of the ball
     */
    private Velocity calculateInitialVelocity() {
        Random rand = new Random();

        /*
         * A base speed of 100 / size. 5 is added so very large balls will still move smoothly on the screen and won't
         * appear static.
         */
        double multiplier = 15, minimum = 5;
        double speed = multiplier * (1.0 / this.size) + minimum;

        double angle = rand.nextDouble() * 360 + 1;
        return Velocity.fromAngleAndSpeed(angle, speed);

    }

    /**
     * Accessor for the ball's X coordinate.
     *
     * @return The ball's X coordinate
     */
    public int getX() {

        return (int) this.location.getX();
    }

    /**
     * Accessor for the ball's Y coordinate.
     *
     * @return The ball's Y coordinate
     */
    public int getY() {

        return (int) this.location.getY();
    }

    /**
     * Accessor for the ball's Velocity.
     *
     * @return The ball's Velocity
     */
    public Velocity getVelocity() {

        return this.velocity;
    }

    /**
     * Draw the ball on the given DrawSurface.
     *
     * @param surface - A DrawSurface object to draw on
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.location.getX(), (int) this.location.getY(), this.size);
    }

    /**
     * Notify the ball that time has passed - in this case the Ball will move one step.
     */
    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Provide the ball with the Game, so it can add itself to it.
     * @param g - The Game object representing the game we are currently playing.
     */
    @Override
    public void addToGame(Game g) {
        g.addSprite(this);
        this.ge = g.getEnvironment();
    }

    /**
     * Set the ball's velocity - receives a Velocity object.
     *
     * @param v - A new velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = new Velocity(v.getDx(), v.getDy());
    }

    /**
     * Set the ball's velocity - receives dx, dy parameters.
     *
     * @param dx - The distance the ball moves horizontally per frame.
     * @param dy - The distance the ball moves vertically per frame.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Notifies the Ball of its Game Environment.
     * @param ge - The Ball's Game Environment.
     */
    public void setGameEnvironment(GameEnvironment ge) {
        this.ge = ge;
    }

    /**
     * Moves the ball one step with the appropriate velocity.
     */
    public void moveOneStep() {
        Line trajectory = new Line(this.location, this.getVelocity().applyToPoint(this.location));
        CollisionInfo collision = this.ge.getClosestCollision(trajectory);

        /*
         * If collision is null, there is no collision and Ball should move normally with its existing velocity.
         * Otherwise, we will move the ball to "almost" the collision point, notify the Collidable that
         * the ball is colliding with that we have collided with it, and set the ball's velocity to that which
         * is returned from the Collidable.
         */
        if (collision == null) {
            this.location = this.getVelocity().applyToPoint(this.location);
        } else {
            double almostX, almostY;
            almostX = getAlmostCoordinate(this.location.getX(), collision.collisionPoint().getX());
            almostY = getAlmostCoordinate(this.location.getY(), collision.collisionPoint().getY());
            this.location = new Point(almostX, almostY);
            this.setVelocity(collision.collisionObject().hit(collision.collisionPoint(), this.getVelocity()));
        }
    }

    /**
     * Calculates the value of a one coordinate that the ball will move to when moving almost to a collision coordinate.
     *
     * @param currentCoord   - The coordinate value (X or Y) of the Ball's current location.
     * @param collisionCoord - The coordinate value (X or Y, corresponding with currentCoord), of the collision point.
     * @return The coordiante value (corresponding with the given coordinate X or Y), that the ball should move to.
     */
    private double getAlmostCoordinate(double currentCoord, double collisionCoord) {
        int epsilonMultiplier = 1000;
        double epsilon = Point.getEpsilon();
        return currentCoord + epsilon < collisionCoord ? collisionCoord - epsilonMultiplier * epsilon
                : collisionCoord + epsilonMultiplier * epsilon;
    }
}