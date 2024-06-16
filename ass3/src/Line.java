import java.util.List;

/**
 * Name: Ezra Gubbay.
 * ID: 209184308
 * Description - The Line class. Represents a line-segment with a start point and end point.
 */
public class Line {

    private final Point start; // Starting point of the line
    private final Point end; // End point of the line

    /**
     * Parameter constructor that receives two Point objects.
     *
     * @param start - The line's start point
     * @param end   - The line's end point
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Parameter constructor that receives two pairs of x,y coordinates.
     *
     * @param x1 - X coordinate of the line's start point
     * @param y1 - Y coordinate of the line's start point
     * @param x2 - X coordinate of the line's end point
     * @param y2 - Y coordinate of the line's end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of the line.
     *
     * @return The length of the line
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return The middle point of the line
     */
    public Point middle() {
        double middleX = (this.start.getX() + this.end.getX()) / 2;
        double middleY = (this.start.getY() + this.end.getY()) / 2;
        return new Point(middleX, middleY);
    }

    /**
     * Returns the start point of the line.
     *
     * @return The start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return The end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns true if the lines intersect, false otherwise.
     *
     * @param other - Another line to check if it intersects with this line
     * @return True if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        // If there is a single intersection point between the lines, return true.
        if (intersectionWith(other) != null) {
            return true;
        }

        // Considering all possible cases, we only need to check if three of the four points of the two line-segments
        // are in the other line. We also check if the lines are equal as pointInSegment is not inclusive of the edges.
        // If this evaluates to true, there are infinite intersections between the lines.
        return this.equals(other) || this.pointInSegment(other.start()) || this.pointInSegment(other.end())
                || other.pointInSegment(this.start());
    }

    /**
     * Checks if this line is intersecting with another two given lines. Lines are also intersecting if they are
     * infinitely intersecting.
     * @param other1 - The first line to check
     * @param other2 - The second line to check
     * @return True if the line is intersecting with both lines, false otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return isIntersecting(other1) && isIntersecting(other2);
    }

    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     * @param other - The other line to find the intersection with (if it exists).
     * @return The intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        // Edge case: at least one of the lines is vertical.
        if (this.notHasSlope()) {
            return this.verticalIntersection(other);
        } else if (other.notHasSlope()) {
            return other.verticalIntersection(this);
        }

        // Lines are definitely not vertical - get the slopes of the lines.
        double currentSlope = this.getSlope();
        double otherSlope = other.getSlope();

        // Edge case: the line-segments are parallel or on the same line.
        if (Point.threshold(currentSlope, otherSlope)) {
            return this.isEdgeIntersection(other);
        }

        // The lines are not parallel or vertical and definitely intersect.
        // Calculate the intersection point of the lines.
        double intersectionX = (currentSlope * this.start.getX() - otherSlope * other.start.getX() - this.start.getY()
                + other.start().getY()) / (currentSlope - otherSlope);
        double intersectionY = currentSlope * (intersectionX - this.start().getX()) + this.start().getY();
        Point intersection = new Point(intersectionX, intersectionY);

        // If the intersection points of the lines are in both line-segments, return true. Otherwise, return null.
        if (this.pointInSegment(intersection) && other.pointInSegment(intersection)) {
            return intersection;
        }
        return null;
    }

    /**
     * Returns true if the lines are equal, false otherwise.
     *
     * @param other - Another line to be compared to this line
     * @return True if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {

        boolean same = this.start.equals(other.start())
                && this.end.equals(other.end());
        boolean opposite = this.start.equals(other.end())
                && this.end.equals(other.start());

        return same || opposite;
    }

    /**
     * Returns the slope of the current line.
     *
     * @return The slope of the current line.
     */
    public double getSlope() {
        // Edge case: The difference of the line's x values is zero.
        // This edge case is solved by calling hasSlope first.
        return (this.end.getY() - this.start.getY())
                / (this.end.getX() - this.start.getX());
    }

    /**
     * Return true if the current line is vertical or not (meaning slope is infinity).
     *
     * @return True if the current line is vertical or not (meaning slope is infinity).
     */
    public boolean notHasSlope() {
        // Edge case: The difference of the line's x values is zero.
        return Point.threshold(this.end.getX(), this.start.getX());
    }

    /**
     * Returns intersection point between this line when this line is vertical, and another line (if the intersection
     * exists).
     *
     * @param other - Another line for which we should find an intersection with this line
     * @return The intersection if one exists, otherwise null
     */
    public Point verticalIntersection(Line other) {
        if (other.notHasSlope()) {
            return this.isEdgeIntersection(other);
        }

        double otherSlope = other.getSlope();
        double intersectionX = this.start.getX();
        double intersectionY = otherSlope * (intersectionX - other.start().getX()) + other.start().getY();
        Point intersection = new Point(intersectionX, intersectionY);
        if (other.pointInSegment(intersection) && this.pointInSegment(intersection)) {
            return intersection;
        }
        return null;
    }

    /**
     * If one of the current line's edges equals one of the other line's edges, and the other edge
     * is not in the other line-segment, returns true.
     * Evaluates the case that the lines are intersecting at exactly one of their edges.
     *
     * @param other - Another line for which we should find an intersection with this line
     * @return If one of the current line's edges equals one of the other line's edges, and the other edge
     * is not in the other line-segment, returns true, otherwise false.
     */
    public Point isEdgeIntersection(Line other) {
        if (this.start.equals(other.start()) || this.start.equals(other.end())) {
            if (!other.pointInSegment(this.end) && !this.end.equals(other.start()) && !this.end.equals(other.end())) {
                return this.start;
            }
        } else if (this.end.equals(other.start()) || this.end.equals(other.end())) {
            if (!other.pointInSegment(this.start) && !this.start.equals(other.start())
                    && !this.start.equals(other.end())) {
                return this.end;
            }
        }

        return null;
    }

    /**
     * Check if a point on a line is within a segment of that line.
     *
     * @param point - A point we want to check
     * @return True if the point is in the segment, false otherwise.
     */
    public boolean pointInSegment(Point point) {
        // First check if the point's x value is in range of the line segment.
        if ((Point.lessThanOrEqualThreshold(point.getX(), this.start.getX())
                && Point.greaterThanOrEqualThreshold(point.getX(), this.end.getX()))
                || (Point.greaterThanOrEqualThreshold(point.getX(), this.start.getX())
                && Point.lessThanOrEqualThreshold(point.getX(), this.end.getX()))) {

            // Next, if the point's y value is in range of the line segment, return true, otherwise, false.
            return ((Point.lessThanOrEqualThreshold(point.getY(), this.start.getY())
                    && Point.greaterThanOrEqualThreshold(point.getY(), this.end.getY()))
                    || (Point.greaterThanOrEqualThreshold(point.getY(), this.start.getY())
                    && Point.lessThanOrEqualThreshold(point.getY(), this.end.getY())));
        }
        return false;
    }

    /**
     * Receives two lines. Checks if this line is part of a triangle with the other two lines.
     *
     * @param l1 - The first line to be checked for the triangle
     * @param l2 - The second line to be checked for the triangle
     * @return If this line and the two given lines form a triangle, returns true. Otherwise, false.
     */
    public boolean isInTriangle(Line l1, Line l2) {
        return intersectionWith(l1) != null && intersectionWith(l2) != null && l1.intersectionWith(l2) != null;
    }

    /**
     * Finds the intersection point between this line and a given rectangle that is closest to the start of this line.
     * @param rect - The rectangle to find the intersection with (if it exists).
     * @return The intersection point between this line and the given rectangle
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        Point closest;
        double minDistance;
        double epsilon = Point.getEpsilon();
        List<Point> intersections = rect.intersectionPoints(this);
        // If there are no intersections, return null.
        if (intersections.isEmpty()) {
            return null;
        }
        closest = intersections.get(0);
        minDistance = closest.distance(this.start());

        for (Point intersection : intersections) {
            if (intersection.distance(this.start()) + epsilon < minDistance) {
                closest = intersection;
                minDistance = intersection.distance(this.start());
            }
        }

        // If we reached here, then this line intersects with the rectangle.
        return closest;
    }
}
