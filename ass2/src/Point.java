public class Point {

    private double x;
    private double y;
    private static double epsilon = 0.00001; // Used for threshold comparison of doubles.

    // Parameter Constructor
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // distance -- return the distance of this point to the other point.
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    // equals -- return true is the points are equal, false otherwise
    public boolean equals(Point other) {
        return threshold(this.x, other.x) && threshold(this.y, other.y);
    }

    // Return the x and y values of this point.
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    // Auxiliary method for implementing threshold comparison for doubles.
    public static boolean threshold(double m, double n) {
        return Math.abs(m - n) <= epsilon;
    }
}