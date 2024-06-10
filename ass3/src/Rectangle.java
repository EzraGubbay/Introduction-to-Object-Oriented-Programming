/**
 * @author Ezra Gubbay
 * ID 209184308
 * Description - The Rectangle class
 */

import java.util.ArrayList;
import java.util.List;

public class Rectangle {

    protected Point upperLeft; // The upper left point of the rectangle
    private final double width; // Rectangle's width
    private final double height; // Rectangle's height
    private static int count = 0;

    /**
     * Create a new rectangle with location and width/height.
     * @param upperLeft - The upper left point of the rectangle
     * @param width - Rectangle's width
     * @param height - Rectangle's width
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Accessor for the rectangle's width.
     * @return The rectangle's width.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Accessor for the rectangle's height.
     * @return The rectangle's height.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Accessor for the rectangle's upper left point.
     * @return The rectangle's upper left point.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Finds all the intersection (if any exist) between this rectangle and the given line.
     * @param line - A Line object. We will look for intersection between this rectangle and the line.
     * @return A (possibly empty) list of intersection points with the specified line.
     */
    public List<Point> intersectionPoints(Line line) {
        count++;
        Line[] sides = this.getRectangleSides();
        List<Point> intersections = new ArrayList<>();

        // Loop through the sides. If they intersect with the given line, add the intersection point to the list.
        for (Line side : sides) {
            Point intersection = line.intersectionWith(side);
            if (intersection != null) {
                intersections.add(intersection);
            }
        }
        return intersections;
    }

    /**
     * Returns an array with Line objects representing the four sides of the rectangle.
     * Line 0 is the top of the rectangle, line - 1 the left, line 2 - the right and line 3 - the bottom.
     * @return An array with Line objects representing the four sides of the rectangle.
     */
    private Line[] getRectangleSides() {
        Line[] sides = new Line[4];
        Point lowerRight = new Point(upperLeft.getX() + this.width, upperLeft.getY() + this.height);
        sides[0] = new Line(upperLeft, new Point(upperLeft.getX() + this.width, upperLeft.getY()));
        sides[1] = new Line(upperLeft, new Point(upperLeft.getX(), upperLeft.getY() + this.height));
        sides[2] = new Line(lowerRight, new Point(upperLeft.getX() + this.width, upperLeft.getY()));
        sides[3] = new Line(lowerRight, new Point(upperLeft.getX(), upperLeft.getY() + this.height));

        return sides;
    }


}