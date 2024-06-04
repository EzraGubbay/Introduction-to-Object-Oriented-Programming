/**
 * @author Ezra Gubbay
 * ID 209184308
 * Description - The Rectangle class
 */
import biuoop.DrawSurface;
import java.awt.Color;
public class Rectangle {

    private final Point topLeft;
    private final double width;
    private final double height;
    private final Color color;
    private final Line[] lines = new Line[4];

    /**
     * Parameter constructor
     * @param topLeft - The Top Left corner point of the rectangle
     * @param width - The rectangle's width
     * @param height - The rectangle's height
     * @param color - The rectangle's fill color
     */
    public Rectangle(Point topLeft, double width, double height, Color color) {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
        this.color = color;
        createRectangleFrame();
    }

    /**
     * Parameter constructor
     * @param x - The x coordinate of the rectangle's Top Left corner point
     * @param y - The x coordinate of the rectangle's Top Left corner point
     * @param width - The rectangle's width
     * @param height - The rectangle's height
     * @param color - The rectangle's fill color
     */
    public Rectangle(double x, double y, double width, double height, Color color) {
        this.topLeft = new Point(x, y);
        this.width = width;
        this.height = height;
        this.color = color;
        createRectangleFrame();
    }

    /**
     * Creates the lines representing the rectangle's frame.
     */
    private void createRectangleFrame() {
        /*
         *          l3
         *      ----------
         *     |          |
         *  l1 |          | l2
         *     |          |
         *      ----------
         *          l4
         */
        // L1
        Point startL1 = new Point(topLeft.getX(), topLeft.getY());
        Point endL1 = new Point(this.topLeft.getX(), this.topLeft.getY() + this.height);

        // L2
        Point startL2 = new Point(this.topLeft.getX() + this.width, this.topLeft.getY() + this.height);
        Point endL2 = new Point(this.topLeft.getX() + this.width, this.topLeft.getY());

        // L3
        Point startL3 = new Point(this.topLeft.getX(), this.topLeft.getY());
        Point endL3 = new Point(this.topLeft.getX() + this.width, this.topLeft.getY());

        // L4
        Point startL4 = new Point(this.topLeft.getX() + this.width, this.topLeft.getY() + this.height);
        Point endL4 = new Point(this.topLeft.getX(), this.topLeft.getY() + this.height);

        // Creating the lines
        this.lines[0] = new Line(startL1, endL1);
        this.lines[1] = new Line(startL2, endL2);
        this.lines[2] = new Line(startL3, endL3);
        this.lines[3] = new Line(startL4, endL4);
    }

    /**
     * Accessor for this rectangle's Top Left corner point
     * @return this rectangle's Top Left corner point
     */
    public Point getTopLeft() {
        return this.topLeft;
    }

    /**
     * Accessor for this rectangle's width
     * @return this rectangle's width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Accessor for this rectangle's height
     * @return this rectangle's height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Accessor for this rectangle's array of Lines
     * @return this rectangle's array of Lines
     */
    public Line[] getLines(){
        return this.lines;
    }

    /**
     * Calculates intersections between the given trajectory and the rectangle's vertical sides
     * @param trajectory - A Line representing movement that may intersect with the rectangle
     * @return The closest intersection point to the trajectory's start point
     */
    public Point intersectionWithSides(Line trajectory) {

        Point intersection = null, trajectoryStart = trajectory.start();
        Point[] intersections = new Point[4];
        double minDistance = Double.MAX_VALUE, currentDistance;
        // Get the intersections
        for (int i = 0; i < 2; i++) {
            intersections[i] = this.lines[i].intersectionWith(trajectory);
            if (intersections[i] != null && intersection == null) {
                intersection = intersections[i];
                minDistance = intersection.distance(trajectoryStart);
            } else if (intersections[i] != null) {
                currentDistance = intersection.distance(trajectoryStart);
                if (currentDistance < minDistance) {
                    minDistance = currentDistance;
                    intersection = intersections[i];
                }
            }
        }

        return intersection;
    }

    /**
     * Calculates intersections between the given trajectory and the rectangle's horizontal sides
     * @param trajectory - A Line representing movement that may intersect with the rectangle
     * @return The closest intersection point to the trajectory's start point
     */
    public Point intersectionWithTopBottom(Line trajectory) {
        Point intersection = null, temp = null, trajectoryStart = trajectory.start();
        Point[] intersections = new Point[4];
        double minDistance = Double.MAX_VALUE, currentDistance;
        // Get the intersections
        for (int i = 2; i < 4; i++) {
            intersections[i] = this.lines[i].intersectionWith(trajectory);
            if (intersections[i] != null && intersection == null) {
                intersection = intersections[i];
                minDistance = intersection.distance(trajectoryStart);
            } else if (intersections[i] != null) {
                currentDistance = intersection.distance(trajectoryStart);
                if (currentDistance < minDistance) {
                    minDistance = currentDistance;
                    intersection = intersections[i];
                }
            }
        }

        return intersection;
    }

    /**
     * draw the ball on the given DrawSurface
     * @param d - A DrawSurface object to draw on
     */
    public void drawOn(DrawSurface d) {
        // Explicit Typecasting
        int x = (int) topLeft.getX();
        int y = (int) topLeft.getY();
        int width = (int) this.width;
        int height = (int) this.height;
        d.setColor(this.color);
        d.fillRectangle(x, y, width, height);
    }
}
