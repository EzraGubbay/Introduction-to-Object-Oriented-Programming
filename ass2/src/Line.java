/**
 * @author Ezra Gubbay
 * ID: 209184308
 * start -- the start point of the line.
 * end -- the end point of the line.
 */
public class Line {

    private Point start;
    private Point end;

    // constructors
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    // Return the length of the line
    public double length() {
        return this.start.distance(this.end);
    }

    // Returns the middle point of the line
    public Point middle() {
        double middleX = (this.start.getX() + this.end.getX()) / 2;
        double middleY = (this.start.getY() + this.end.getY()) / 2;
        return new Point(middleX, middleY);
    }

    // Returns the start point of the line
    public Point start() {
        return this.start;
    }

    // Returns the end point of the line
    public Point end() {
        return this.end;
    }

    // Returns true if the lines intersect, false otherwise
    public boolean isIntersecting(Line other) {
        // If there is a single intersection point between the lines, return true.
        if (intersectionWith(other) != null) {
            Point intersection  = intersectionWith(other);
            return true;
        }

        // Considering all possible cases, we only need to check if three of the four points of the two line-segments
        // are in the other line. We also check if the lines are equal as pointInSegment is not inclusive of the edges.
        // If this evaluates to true, there are infinite intersections between the lines.
        return this.equals(other) || this.pointInSegment(other.start()) || this.pointInSegment(other.end()) ||
                other.pointInSegment(this.start());
    }

    // Returns true if this 2 lines intersect with this line, false otherwise
    public boolean isIntersecting(Line other1, Line other2) {
        return isIntersecting(other1) && isIntersecting(other2);
    }

    /* Returns the intersection point if the lines intersect,
     * and null otherwise.
     */
    public Point intersectionWith(Line other) {
        // Edge case: at least one of the lines is vertical.
        if (this.notHasSlope()) {
            return this.verticalIntersection(other);
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
        if (this.pointInSegment(intersection) && other.pointInSegment(intersection))
            return intersection;
        return null;
    }

    // equals -- return true is the lines are equal, false otherwise
    public boolean equals(Line other) {

        boolean same = this.start.equals(other.start()) &&
                this.end.equals(other.end());
        boolean opposite = this.start.equals(other.end()) &&
                this.end.equals(other.start());

        return same || opposite;
    }

    // getSlope -- returns the slope of the current line.
    public double getSlope() {
        // Edge case: The difference of the line's x values is zero.
        // This edge case is solved by calling hasSlope first.
        return (this.end.getY() - this.start.getY()) /
                (this.end.getX() - this.start.getX());
    }

    // notHasSlope -- return true if the current line is vertical or not (meaning slope is infinity).
    public boolean notHasSlope() {
        // Edge case: The difference of the line's x values is zero.
        return Point.threshold(this.end.getX(), this.start.getX());
    }

    // parallelIntersection -- returns intersection points between this line and
    public Point verticalIntersection(Line other) {
        if (other.notHasSlope()) {
            return this.isEdgeIntersection(other);
        }

        double otherSlope = other.getSlope();
        double intersectionX = this.start.getX();
        double intersectionY = otherSlope * (intersectionX - other.start().getX()) + other.start().getY();
        Point intersection = new Point(intersectionX, intersectionY);
        if (other.pointInSegment(intersection))
            return intersection;
        return null;
    }

    // isEdgeIntersection -- If one of the current line's edges equals one of the other line's edges, and the other edge
    // is not in the other line-segment, returns true.
    // Evaluates the case that the lines are intersecting at exactly one of their edges.
    public Point isEdgeIntersection(Line other) {
        if (this.start.equals(other.start()) || this.start.equals(other.end())) {
            if (!other.pointInSegment(this.end) && !this.end.equals(other.start()) && !this.end.equals(other.end()))
                return this.start;
        } else if (this.end.equals(other.start()) || this.end.equals(other.end())) {
            if (!other.pointInSegment(this.start) && !this.start.equals(other.start()) &&
                    !this.start.equals(other.end()))
                return this.end;
        }

        return null;
    }

    // pointInSegment -- check if a point on a line is within a segment of that line.
    public boolean pointInSegment(Point point) {
        // First check if the point's x value is in range of the line segment.
        if (((point.getX() <= this.start.getX() && point.getX() >= this.end.getX()) ||
                (point.getX() >= this.start.getX() && point.getX() <= this.end.getX()))) { //!Point.threshold(point.getX(), this.start.getX()) && !Point.threshold(point.getX(), this.end.getX())

            // Next, if the point's y value is in range of the line segment, return true, otherwise, false.
            return ((point.getY() <= this.start.getY() && point.getY() >= this.end.getY()) ||
                    (point.getY() >= this.start.getY() && point.getY() <= this.end.getY()));
                    //!Point.threshold(point.getY(), this.start.getY()) && Point.threshold(point.getY(), this.end.getY());
        }
        return false;
    }

    // Receives two lines. If this line and the two given lines form a triangle, returns true. Otherwise, false.
    public boolean isInTriangle(Line l1, Line l2) {
        return intersectionWith(l1) != null && intersectionWith(l2) != null && l1.intersectionWith(l2) != null;
    }
}
