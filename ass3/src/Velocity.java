/**
 * @author Ezra Gubbay
 * ID 209184308
 * Description - The Velocity class
 */
public class Velocity {

    private final double dx;
    private final double dy;

    /**
     * Parameter constructor
     * @param dx - The horizontal change in the X coordinate
     * @param dy - The horizontal change in the Y coordinate
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Accessor for this velocity's dx value
     * @return This velocity's dx value
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Accessor for this velocity's dy value
     * @return This velocity's dy value
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy)
     * @param p - The point this velocity should be applied to
     * @return The new point with the applied velocity
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * Receives an angle and a speed, creates a velocity based on that.
     * @param angle - The angle the object should move at
     * @param speed - The speed at which the object should move
     * @return Returns a velocity object.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.cos(Math.toRadians(angle)) * speed;
        double dy = Math.sin(Math.toRadians(angle)) * speed;

        return new Velocity(dx, dy);
    }
}
