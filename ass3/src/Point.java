/**
 * @author Ezra Gubbay
 * ID 209184308
 * Description - The Point class
 */
public class Point {

    private final double x; // The point's X coordinate
    private final double y; // The point's Y coordinate
    public static double EPSILON = 0.00001; // Used for threshold comparison of doubles.

    /**
     * Parameter constructor
     *
     * @param x - The X coordinate of the point
     * @param y - The Y coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates the distance of this point to another point.
     *
     * @param other - Another point to calculate the distance to
     * @return The distance between this point and other point
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /**
     * Checks if the points are equal.
     *
     * @param other - Another point to compare to this point
     * @return True is the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        return threshold(this.x, other.x) && threshold(this.y, other.y);
    }

    /**
     * Accessor for this point's X coordinate
     *
     * @return This point's X coordinate
     */
    public double getX() {
        return this.x;
    }

    /**
     * Accessor for this point's Y coordinate
     *
     * @return This point's Y coordinate
     */
    public double getY() {
        return this.y;
    }

    /**
     * Auxiliary method for implementing threshold comparison for doubles.
     *
     * @param m - First double to compare
     * @param n - Second double to compare
     * @return True if the doubles' difference is within the epsilon threshold, false otherwise.
     */
    public static boolean threshold(double n, double m) {

        return Math.abs(m - n) <= EPSILON;
    }

    public static boolean nLessThanMThreshold(double n, double m) {
        return m - EPSILON > n;
    }

    public static boolean nGreaterThanMThreshold(double n, double m) {
        return nLessThanMThreshold(m, n);
    }

    public static boolean lessThanOrEqualThreshold(double n, double m) {
        return threshold(n, m) || nLessThanMThreshold(n, m);
    }

    public static boolean greaterThanOrEqualThreshold(double n, double m) {
        return threshold(n, m) || nGreaterThanMThreshold(n, m);
    }

    /**
     * toString method. Prints the X and Y coordinate values of this point.
     */
    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }
}