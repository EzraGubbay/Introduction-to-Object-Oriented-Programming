/**
 * @author Ezra Gubbay
 * ID 209184308
 * Description: The Ball class
 */

import biuoop.DrawSurface;

import java.awt.*;

public class Ball {

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

    // Parameter constructor
    public Ball(Point center, int r, Color color) {
        this.location = center;
        this.size = r;
        this.color = color;
    }

    // Parameter constructor that receives the location's coordinates instead of a Point object.
    public Ball(double x, double y, int r, Color color) {
        this.location = new Point(x, y);
        this.size = r;
        this.color = color;
    }

    /**
     * Accessor for the ball's X coordinate
     *
     * @return The ball's X coordinate
     */
    public int getX() {
        
        return (int) this.location.getX();
    }

    /**
     * Accessor for the ball's Y coordinate
     *
     * @return The ball's Y coordinate
     */
    public int getY() {
        
        return (int) this.location.getY();
    }

    /**
     * Accessor for the ball's Velocity
     *
     * @return The ball's Velocity
     */
    public Velocity getVelocity() {
        
        return this.velocity;
    }

    /**
     * draw the ball on the given DrawSurface
     *
     * @param surface - A DrawSurface object to draw on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.location.getX(), (int) this.location.getY(), this.size);
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
     * Moves the ball one step with its velocity.
     */
    public void moveOneStep() {
        Point[] edges = getBallEdges();
        Line[] trajectories = getEdgeTrajectories(edges);

        if (this.inGrayRectangle) {
            //System.out.println("[DEBUG] Velocity - " + this.velocity.getDx() + " " + this.velocity.getDy());
            this.moveOneStepInGrayRectangle(edges, trajectories);
        } else {
            this.moveOneStepOutGrayRectangle(edges, trajectories);
        }
    }

    /**
     * Responsible for handling the ball's movement within the gray rectangle
     */
    private void moveOneStepInGrayRectangle(Point[] edges, Line[] trajectories) {
        int changeDx = 1, changeDy = 1;
        Point[] intersections = this.getRectangleIntersections(this.gray, trajectories);

        changeDx = checkSides(intersections, trajectories, edges, changeDx);
        if (changeDx == -1) {
            edges = getBallEdges();
        }
        changeDy = checkTopBottom(intersections, trajectories, edges, changeDy, changeDx);
        this.moveBall(changeDx, changeDy);
    }

    /**
     * Responsible for handling the ball's movement outside the gray rectangle and within the GUI frame
     */
    private void moveOneStepOutGrayRectangle(Point[] edges, Line[] trajectories) {
        double dx, dy;
        int changeDx = 1, changeDy = 1;
        Point[] grayIntersections = getRectangleIntersections(this.gray, trajectories);
        Point[] frameIntersections = getRectangleIntersections(this.frame, trajectories);

        // Check if the ball will hit the left or right of the rectangle
        changeDx = checkSides(grayIntersections, trajectories, edges, changeDx);

        if (changeDx == -1) {
            edges = getBallEdges();
        }

        // Check if the ball will hit the top or bottom of the rectangle
        changeDy = checkTopBottom(grayIntersections, trajectories, edges, changeDy, changeDx);

        // Ball has intersected with the gray rectangle - no need to check frame
        if (changeDx == -1 || changeDy == -1) {
            this.moveBall(changeDx, changeDy);
            return;
        }

        // If we have reached here, ball has not collided with gray rectangle, let's check the frame.
        double spacing = getSpacing();
        if (frameIntersections[0] != null) {
            dx = frameIntersections[0].getX() - edges[0].getX() + spacing * Point.EPSILON;
            dy = frameIntersections[0].getY() - edges[0].getY();
            Velocity v = new Velocity(dx, dy);
            this.location = v.applyToPoint(this.location);
            changeDx = -1;
        } else if (frameIntersections[1] != null) {
            dx = frameIntersections[1].getX() - edges[1].getX() + spacing * Point.EPSILON;
            dy = frameIntersections[1].getY() - edges[1].getY();
            Velocity v = new Velocity(dx, dy);
            this.location = v.applyToPoint(this.location);
            changeDx = -1;
        }
        if (frameIntersections[2] != null) {
            dx = frameIntersections[2].getX() - edges[2].getX() + spacing * Point.EPSILON;
            dy = frameIntersections[2].getY() - edges[2].getY();
            Velocity v = new Velocity(dx, dy);
            this.location = v.applyToPoint(this.location);
            changeDy = -1;
        } else if (frameIntersections[3] != null) {
            dx = frameIntersections[3].getX() - edges[3].getX() + spacing * Point.EPSILON;
            dy = frameIntersections[3].getY() - edges[3].getY();
            Velocity v = new Velocity(dx, dy);
            this.location = v.applyToPoint(this.location);
            changeDy = -1;
        }

        this.moveBall(changeDx, changeDy);
    }

    /**
     * Move ball if it has not been moved already and give ball new velocity.
     */
    private void moveBall(int changeDx, int changeDy) {
        if (changeDx == 1 && changeDy == 1) {
            this.location = this.getVelocity().applyToPoint(this.location);
        }
        this.setVelocity(changeDx * this.getVelocity().getDx(), changeDy * this.getVelocity().getDy());
    }

    /**
     * Check if the ball will hit the left or right of the rectangle
     *
     * @param intersections - Intersections the ball has with some rectangle
     * @param trajectories  - The trajectories of the ball's four edges
     * @param edges         - The leftmost, rightmost, topmost and bottommost points on the ball
     * @param changeDx      - A variable that holds whether the ball currently needs to change in the x direction or not
     * @return 1, if the ball's velocity should NOT be changed in the x direction, -1 if it should
     */
    private int checkSides(Point[] intersections, Line[] trajectories, Point[] edges, int changeDx) {
        double dx, dy;
        double spacing = this.getSpacing();

        // Check if the left has an intersecting trajectory that is closest to the rectangle and move the ball there
        if (validateIntersection(intersections[0], trajectories[0])) { // Checking left
            dx = intersections[0].getX() - edges[0].getX() + spacing * Point.EPSILON;
            dy = intersections[0].getY() - edges[0].getY();
            Velocity v = new Velocity(dx, dy);
            this.location = v.applyToPoint(this.location);
            changeDx = -1;
        } else {
            // Check if the right has an intersecting trajectory that is closest to the rectangle
            // and move the ball there
            if (validateIntersection(intersections[1], trajectories[1])) { // Checking right
                dx = intersections[1].getX() - edges[1].getX() - spacing * Point.EPSILON;
                dy = intersections[1].getY() - edges[1].getY();
                this.location = new Velocity(dx, dy).applyToPoint(this.location);
                changeDx = -1;
            }
        }
        return changeDx;
    }

    /**
     * Check if the ball will hit the top or bottom of the rectangle
     *
     * @param intersections - Intersections the ball has with some rectangle
     * @param trajectories  - The trajectories of the ball's four edges
     * @param edges         - The leftmost, rightmost, topmost and bottommost points on the ball
     * @param changeDy      - A variable that holds whether the ball currently needs to change in the y direction or not
     * @param changeDx      - A variable that holds whether the ball currently needs to change in the x direction or not
     * @return 1, if the ball's velocity should NOT be changed in the y direction, -1 if it should
     */
    private int checkTopBottom(Point[] intersections, Line[] trajectories, Point[] edges, int changeDy, int changeDx) {
        double dx, dy;
        double spacing = this.getSpacing();

        // Check if the top has an intersecting trajectory that is closest to the rectangle and move the ball there
        if (validateIntersection(intersections[2], trajectories[2])) { // Checking top
            if (changeDx == -1) {
                dx = 0;
            } else {
                dx = intersections[2].getX() - edges[2].getX();
            }
            dy = intersections[2].getY() - edges[2].getY() + spacing * Point.EPSILON;
            this.location = new Velocity(dx, dy).applyToPoint(this.location);
            changeDy = -1;
        } else {
            // Check if the bottom has an intersecting trajectory that is closest to the rectangle and
            // move the ball there
            if (validateIntersection(intersections[3], trajectories[3])) { // Checking bottom
                if (changeDx == -1) {
                    dx = 0;
                } else {
                    dx = intersections[3].getX() - edges[3].getX();
                }
                dy = intersections[3].getY() - edges[3].getY() - spacing * Point.EPSILON;
                this.location = new Velocity(dx, dy).applyToPoint(this.location);
                changeDy = -1;
            }
        }
        return changeDy;
    }

    /**
     * Validates if an intersection that was found is relevant for intersections with the gray rectangle
     *
     * @param intersection - The intersection that should be validated
     * @param trajectory   - The trajectory of the edge of the ball that is intersecting
     * @return True, if the intersection is relevant, False otherwise
     */
    private boolean validateIntersection(Point intersection, Line trajectory) {
        return intersection != null && !Point.threshold(trajectory.length(), 0) && pointInGrayRange(intersection);
    }


    /**
     * Receives a point and checks if the point is either in the gray rectangle or on it.
     *
     * @param p - A point that should be validated
     * @return True if the point meets the condition, false otherwise
     */
    private boolean pointInGrayRange(Point p) {
        return (p.getX() > 50 || Point.threshold(p.getX(), 50)) &&
                (p.getX() < 500 || Point.threshold(p.getX(), 500)) &&
                (p.getY() > 50 || Point.threshold(p.getY(), 50)) &&
                (p.getY() < 500 || Point.threshold(p.getY(), 500));
    }

    /**
     * Calculates the appropriate spacing multiplier factor that should be used to space the ball slightly away but
     * very close to the rectangle it is colliding with
     *
     * @return 0.5 if the ball is inside the gray rectangle, -0.5 otherwise.
     */
    private double getSpacing() {
        return this.inGrayRectangle ? 0.5 : -0.5;
    }

    /**
     * Receives a rectangle, trajectories of the edges of the ball
     *
     * @param r            - A rectangle object the ball may be colliding with
     * @param trajectories - The trajectories of the ball's edge points
     * @return An array of intersections that the ball will have with the rectangle if it proceeds moving with
     * its velocity
     */
    private Point[] getRectangleIntersections(Rectangle r, Line[] trajectories) {
        Point[] intersections = new Point[trajectories.length];
        for (int i = 0; i < intersections.length; i++) {
            Line trajectory = trajectories[i];
            if (i < 2) {
                if ((i == 0 && this.getVelocity().getDx() < 0) || (i == 1 && this.getVelocity().getDx() > 0)) {
                    intersections[i] = r.intersectionWithSides(trajectory);
                } else {
                    intersections[i] = null;
                }
            } else {
                if ((i == 2 && this.getVelocity().getDy() < 0) || (i == 3 && this.getVelocity().getDy() > 0)) {
                    intersections[i] = r.intersectionWithTopBottom(trajectory);
                } else {
                    intersections[i] = null;
                }
            }
        }

        return intersections;
    }

    /**
     * Provides the ball's edge points - leftmost, rightmost, topmost and bottommost points of the ball.
     *
     * @return An array with the ball's edge points
     */
    private Point[] getBallEdges() {
        Point left, right, up, down;
        left = new Point(this.getX() - this.size, this.getY());
        right = new Point(this.getX() + this.size, this.getY());
        up = new Point(this.getX(), this.getY() - this.size);
        down = new Point(this.getX(), this.getY() + this.size);
        return new Point[]{left, right, up, down};
    }

    /**
     * Provides the trajectories of the ball's edges
     *
     * @param edges - An array of Points containing the ball's edge points
     * @return An array of Lines where each line's start is the corresponding edge point of the ball,
     * and each line's end is where the edge will end up if the ball moves along its regular trajectory
     */
    private Line[] getEdgeTrajectories(Point[] edges) {
        Line left, right, up, down;
        left = this.getTrajectory(edges[0]);
        right = this.getTrajectory(edges[1]);
        up = this.getTrajectory(edges[2]);
        down = this.getTrajectory(edges[3]);
        return new Line[]{left, right, up, down};

    }

    /**
     * Receives a single point and returns a line representing the trajectory that point will travel across
     * if the ball's velocity is applied to it
     *
     * @param start - The point for which the trajectory should be given
     * @return A line representing the trajectory of the point
     */
    private Line getTrajectory(Point start) {
        Point end = this.velocity.applyToPoint(start);
        return new Line(start, end);
    }
}