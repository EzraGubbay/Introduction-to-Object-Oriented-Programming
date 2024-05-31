import biuoop.DrawSurface;
import jdk.jfr.consumer.RecordedStackTrace;
import org.w3c.dom.css.Rect;

import java.awt.Color;

public class Ball {

    private Point location;
    private int size;
    private java.awt.Color color;
    private Velocity velocity;
    private boolean inGrayRectangle;
    private Rectangle frame;
    private Rectangle gray = new Rectangle(50, 50, 450, 450, Color.GRAY);

    // Parameter constructor
    public Ball(Point center, int r, java.awt.Color color) {
        this.location = center;
        this.size = r;
        this.color = color;
        this.initializeLimits(this.location);
    }

    // Parameter constructor that receives the location's coordinates instead of a Point object.
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.location = new Point(x, y);
        this.size = r;
        this.color = color;
        this.initializeLimits(this.location);
    }

    /*
     * Checking if the ball was initialized within the gray rectangle or not.
     */
    public void initializeLimits(Point location) {

        // Here we are arbitrarily checking if the ball's initial x coordinate is in the range of the gray rectangle.
        // This is because the ball's x coordinate being within the range of the gray rectangle, is a necessity for the
        // Ball being within the gray rectangle.
        if (this.location.getX() >= 50 && this.location.getX() <= 500) {
            // If the ball is inside the gray rectangle, we use the gray rectangle's limits.
            this.inGrayRectangle = true;
            this.frame = this.gray;
        } else {
            // If the ball is outside the gray rectangle, we use the GUI limits.
            this.inGrayRectangle = false;
            this.frame = new Rectangle(0, 0, 800, 600, null);
        }

        // DEBUG
        System.out.println("[DEBUG] Location: " + this.getX() + " " + this.getY());
        System.out.println("[DEBUG] Ball in rectangle: " + this.inGrayRectangle);
        // DEBUG
    }

    // accessors
    public int getX() {
        return (int) this.location.getX();
    }

    public int getY() {
        return (int) this.location.getY();
    }

    public int getSize() {
        return this.size;
    }

    public java.awt.Color getColor() {
        return this.color;
    }

    // draw the ball on the given DrawSurface
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.location.getX(), (int) this.location.getY(), this.size);
    }

    // Set the ball's velocity - receives a Velocity object.
    public void setVelocity(Velocity v) {
        this.velocity = new Velocity(v.getDx(), v.getDy());
    }

    // Set the ball's velocity - receives dx, dy parameters.
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    public Velocity getVelocity() {
        return this.velocity;
    }

    public void moveOneStep() {
        Point[] edges = getBallEdges();
        Line[] trajectories = getEdgeTrajectories(edges);

        if (this.inGrayRectangle) {
            this.moveOneStepInGrayRectangle(edges, trajectories);
        } else {
            this.moveOneStepOutGrayRectangle(edges, trajectories);
        }
    }

    private void moveOneStepInGrayRectangle(Point[] edges, Line[] trajectories) {
        double dx = this.getVelocity().getDx(), dy = this.getVelocity().getDy();
        int changeDx = 1, changeDy = 1;
        Point[] intersections = this.getRectangleIntersections(this.gray, trajectories);
        Point intersection;

        // DEBUG
        System.out.println("[DEBUG] X: " + this.getX() + " Y: " + this.getY());
        System.out.println("[DEBUG] Dx: " + this.getVelocity().getDx() + " Dy: " + this.getVelocity().getDy());
        // DEBUG

        // Check if the ball will hit the left or right of the rectangle
        intersection = trajectories[0].intersectionWith(this.gray.getLines()[0]);
        if (validateIntersection(intersection, trajectories[0])) { // Checking left
            System.out.println("[DEBUG] NULL 0");
            dx = intersection.getX() - edges[0].getX();
            dy = intersection.getY() - edges[0].getY();
            Velocity v = new Velocity(dx, dy);
            this.location = v.applyToPoint(this.location);
            changeDx = -1;
        } else {
            intersection = trajectories[1].intersectionWith(this.gray.getLines()[1]);
            if (validateIntersection(intersection, trajectories[1])) { // Checking right
                System.out.println("[DEBUG] NULL 1");
                dx = intersection.getX() - edges[1].getX();
                dy = intersection.getY() - edges[1].getY();
                this.location = new Velocity(dx, dy).applyToPoint(this.location);
                changeDx = -1;
            }
        }

        // Check if the ball will hit the top or bottom of the rectangle
        intersection = trajectories[2].intersectionWith(this.gray.getLines()[2]);
        if (validateIntersection(intersection, trajectories[2])) { // Checking top
            System.out.println("[DEBUG] NULL 2");
            dx = intersection.getX() - edges[2].getX();
            dy = intersection.getY() - edges[2].getY();
            this.location = new Velocity(dx, dy).applyToPoint(this.location);
            changeDy = -1;
        } else {
            intersection = trajectories[3].intersectionWith(this.gray.getLines()[3]);
            if (validateIntersection(intersection, trajectories[3])) { // Checking bottom
                System.out.println("[DEBUG] NULL 3");
                dx = intersection.getX() - edges[3].getX();
                dy = intersection.getY() - edges[3].getY();
                this.location = new Velocity(dx, dy).applyToPoint(this.location);
                changeDy = -1;
            }
        }
        System.out.println(intersection);

        Velocity v = new Velocity(dx, dy);
        this.moveBall(v, changeDx, changeDy);
    }

    private void moveOneStepOutGrayRectangle(Point[] edges, Line[] trajectories) {
        double dx = this.getVelocity().getDx(), dy = this.getVelocity().getDy();
        int changeDx = 1, changeDy = 1;
        Point[] grayIntersections = getRectangleIntersections(this.gray, trajectories);
        Point[] frameIntersections = getRectangleIntersections(this.frame, trajectories);
        Point intersection;

        // First, let's check if the ball will hit the gray rectangle
        intersection = trajectories[0].intersectionWith(this.gray.getLines()[0]);
        if (intersection != null && this.getX() > 500 && valueInGrayRange(this.getY())) {
            // The left edge of the ball hits right side of the rectangle
            dx = trajectories[0].intersectionWith(this.gray.getLines()[1]).getX() - edges[0].getX();
            changeDx = -1;
        } else if (grayIntersections[1] != null && this.getX() < 50 && valueInGrayRange(this.getY())) {
            // The right edge of the ball hits the right side of the rectangle
            dx = grayIntersections[1].getX() - edges[1].getX();
            changeDx = -1;
        }

        if (grayIntersections[2] != null) {
            // The top edge of the ball hits the bottom side of the rectangle
            dy = grayIntersections[2].getY() - edges[2].getY();
            changeDy = -1;
        } else if (grayIntersections[3] != null) {
            // The bottom edge of the ball hits the top side of the rectangle
            dy = grayIntersections[3].getY() - edges[3].getY();
            changeDy = -1;
        }

        // Ball has intersected with the gray rectangle - no need to check frame
        if (changeDx == -1 || changeDy == -1) {
            Velocity v = new Velocity(dx, dy);
            this.moveBall(v, changeDx, changeDy);
            return;
        }

        // If we have reached here, ball has not collided with gray rectangle, let's check the frame.
        if (frameIntersections[0] != null) {
            dx = frameIntersections[0].getX() - edges[0].getX();
            changeDx = -1;
        } else if (frameIntersections[1] != null) {
            dx = frameIntersections[1].getX() - edges[1].getX();
            changeDx = -1;
        }
        if (frameIntersections[2] != null) {
            dy = frameIntersections[2].getY() - edges[2].getY();
            changeDy = -1;
        } else if (frameIntersections[3] != null) {
            dy = frameIntersections[3].getY() - edges[3].getY();
            changeDy = -1;
        }

        Velocity v = new Velocity(dx, dy);
        this.moveBall(v, changeDx, changeDy);
    }

    // Move ball using the appropriate velocity found
    private void moveBall(Velocity v, int changeDx, int changeDy) {
        if (changeDx == 1 && changeDy == 1) {
            this.location = v.applyToPoint(this.location);
        }
        this.setVelocity(changeDx * this.getVelocity().getDx(), changeDy * this.getVelocity().getDy());
    }

    private boolean validateIntersection(Point intersection, Line trajectory) {
        return intersection != null && !Point.threshold(trajectory.length(), 0);
    }

    // Returns true if a given value is greater than 50 and less than 500
    private boolean valueInGrayRange(double value) {
        return value >= 50 && value <= 500;
    }

    private Point[] getRectangleIntersections(Rectangle r, Line[] trajectories) {
        Point[] intersections = new Point[trajectories.length];
        for (int i = 0; i < intersections.length; i++) {
            Line trajectory = trajectories[i];
            intersections[i] = r.intersectionWith(trajectory);
        }

        return intersections;
    }

    private Point[] getBallEdges() {
        Point left, right, up, down;
        left = new Point(this.getX() - this.size, this.getY());
        right = new Point(this.getX() + this.size, this.getY());
        up = new Point(this.getX(), this.getY() - this.size);
        down = new Point(this.getX(), this.getY() + this.size);
        Point[] edges = {left, right, up, down};
        return edges;
    }

    private Line[] getEdgeTrajectories(Point[] edges) {
        Line left, right, up, down;
        left = this.getTrajectory(edges[0]);
        right = this.getTrajectory(edges[1]);
        up = this.getTrajectory(edges[2]);
        down = this.getTrajectory(edges[3]);
        Line[] trajectories = {left, right, up, down};
        return trajectories;

    }

    private Line getTrajectory(Point start) {
        Point end = this.velocity.applyToPoint(start);
        return new Line(start, end);
    }

    /*
    public void moveOneStep() {
        double dx = this.getVelocity().getDx();
        double dy = this.getVelocity().getDy();
        int dxChange = 1, dyChange = 1;
        boolean change = false;

        /*
        // DEBUG
        System.out.println("[DEBUG] X: " + this.getX() + " Y: " + this.getY());
        System.out.println("[DEBUG] Dx: " + this.getVelocity().getDx() + " Dy: " + this.getVelocity().getDy());
        System.out.println("[DEBUG] Simulation - X: " + this.simulateX() + " Y: " + this.simulateY());
        System.out.println("[DEBUG] Borders Result - RIGHT: " + this.checkRightBorder() + " LEFT: " + this.checkLeftBorder());
        System.out.println("[DEBUG] Borders Result - BOTTOM: " + this.checkBottomBorder() + " TOP: " + this.checkTopBorder());
        // DEBUG
        */
        /*
        System.out.println("[DEBUG] X: " + this.getX() +  " Y: " + this.getY());

        if (this.getVelocity().getDx() < 0 && this.checkLeftBorder()) {
            double distance;
            if (this.getX() > 500) {
                distance = this.location.distance(new Point(500, this.location.getY()));
            }
            dx = -1 * (this.location.distance(new Point(this.minLimit, this.location.getY())) - this.size);
            dxChange = -1;
            change = true;
            System.out.println("Left border jump activated.");
        }

        if (this.getVelocity().getDx() > 0 && this.checkRightBorder()) {
            dx = this.location.distance(new Point(this.maxX, this.location.getY())) - this.size;
            dxChange = -1;
            change = true;
            System.out.println("Right border jump activated.");
        }
        if (this.getVelocity().getDy() < 0 && this.checkUpperBorder()) {
            dy = -1 * (this.location.distance(new Point(this.location.getX(), this.minLimit)) - this.size);
            dyChange = -1;
            change = true;
            System.out.println("Upper border jump activated.");
        }
        if (this.getVelocity().getDy() > 0 && this.checkLowerBorder()) {
            dy = this.location.distance(new Point(this.location.getX(), this.maxY)) - this.size;
            dyChange = -1;
            change = true;
            System.out.println("Lower border jump activated.");
        }

        if (change) {
            Velocity bringToEdge = new Velocity(dx, dy);
            this.location = bringToEdge.applyToPoint(this.location);
            this.setVelocity(dxChange * this.getVelocity().getDx(), dyChange * this.getVelocity().getDy());
        } else {
            this.location = this.getVelocity().applyToPoint(this.location);
        }
    }

    // Methods that return the value of the ball's positional coordinates if velocity is applied to them.
    public double simulateX() {
        return this.location.getX() + this.getVelocity().getDx();
    }

    public double simulateY() {
        return this.location.getY() + this.getVelocity().getDy();
    }

    // These methods check if the ball will cross one of the boundaries on it's next move.
    private boolean checkLeftBorder() {
        double leftX = this.simulateX() - this.size;
        // Ball is either in the gray rectangle or in the narrow region of the GUI.
        if (this.inGrayRectangle || this.getX() - 50 < 0) {
            return leftX <= this.minLimit;
        } else { // Ball is in the wider region of the GUI.
            return leftX <= 500 && (this.getY() > 50 && this.getY() < 500);
        }
    }

    private boolean checkRightBorder() {
        double rightX = this.simulateX() + this.size;
        // Ball is either in the gray rectangle or in the wider region of the GUI.
        if (this.inGrayRectangle || this.getX() > 500) {
            return rightX >= this.maxX;
        } else { // The ball is to the left of the gray rectangle.
            return rightX >= 50 && (this.getY() > 50 && this.getY() < 500);
        }
    }

    private boolean checkUpperBorder() {
        double upperY = this.simulateY() - this.size;
        // Ball is either in the gray rectangle or in the narrow region above the gray rectangle.
        if (this.inGrayRectangle || this.getY() - 50 < 0) {
            return upperY <= this.minLimit;
        } else { // The ball is below the gray rectangle.
            return upperY <= 500 && (this.getX() > 50 && this.getX() < 500);
        }
    }

    private boolean checkLowerBorder() {
        double lowerY = this.simulateY() + this.size;
        // The ball is either in the gray rectangle or is below the gray rectangle.
        if (this.inGrayRectangle || this.getY() > 500) {
            return lowerY >= this.maxY;
        } else { // The ball is above the gray rectangle.
            return lowerY >= 50 && (this.getX() < 50 && this.getX() > 500);
        }
    }

    public void moveOneStep_V2() {

        if (this.inGrayRectangle) {
            moveOneStepInRectangle();
        } else {
            moveOneStepOutsideRectangle();
        }

    }

    private void moveOneStepInRectangle() {
        double dx = this.getVelocity().getDx();
        double dy = this.getVelocity().getDy();
        boolean left = this.getVelocity().getDx() < 0, right = this.getVelocity().getDx() > 0;
        boolean up = this.getVelocity().getDy() < 0, down = this.getVelocity().getDy() > 0;
        int dxChange = 1, dyChange = 1;
        boolean change = false;

        // Check if the ball will hit the rectangle frame.
        Line trajectory;
        trajectory = this.getTrajectory(new Point(this.simulateX() - this.size, this.getY()));
        if (right && trajectory.intersectionWith(this.gray.getL3()) != null) {
            dx = frameIntersectDx(trajectory);
            dy = frameIntersectDy(trajectory);
            dxChange = -1;
            change = true;
        } else {
            trajectory = this.getTrajectory(new Point(this.simulateX() + this.size, this.getY()));
            if (left && trajectory.intersectionWith(this.gray.getL2()) != null) {
                dx = frameIntersectDx(trajectory);
                dy = frameIntersectDy(trajectory);
                dxChange = -1;
                change = true;
            }
        }

        trajectory = this.getTrajectory(new Point(this.getX(), this.simulateY() - this.size));
        if (up && trajectory.intersectionWith(this.gray.getL1()) != null) {
            dx = frameIntersectDx(trajectory);
            dy = frameIntersectDy(trajectory);
            dyChange = -1;
            change = true;
        } else {
            trajectory = this.getTrajectory(new Point(this.getX(), this.simulateY() + this.size));
            if (down && trajectory.intersectionWith(this.gray.getL4()) != null) {
                dx = frameIntersectDx(trajectory);
                dy = frameIntersectDy(trajectory);
                dyChange = -1;
                change = true;
            }
        }

        if (change) {
            Velocity bringToEdge = new Velocity(dx, dy);
            this.location = bringToEdge.applyToPoint(this.location);
            this.setVelocity(dxChange * this.getVelocity().getDx(), dyChange * this.getVelocity().getDy());
        } else {
            this.location = this.getVelocity().applyToPoint(this.location);
        }
    }

    private void moveOneStepOutsideRectangle() {
        double dx = this.getVelocity().getDx();
        double dy = this.getVelocity().getDy();
        boolean left = this.getVelocity().getDx() < 0, right = this.getVelocity().getDx() > 0;
        boolean up = this.getVelocity().getDy() < 0, down = this.getVelocity().getDy() > 0;
        int dxChange = 1, dyChange = 1;
        boolean change = false;

        // Check if the ball will hit the GUI frame.
        if (left && checkLeftBorder_V2()) {
            dx = -1 * (this.location.distance(new Point(this.minLimit, this.location.getY())) - this.size);
            dxChange = -1;
            change = true;
            System.out.println("Left border jump activated.");
        } else if (right && checkRightBorder_V2()) {
            dx = this.location.distance(new Point(this.maxX, this.location.getY())) - this.size;
            dxChange = -1;
            change = true;
            System.out.println("Right border jump activated.");
        }
        if (up && checkUpperBorder_V2()) {
            dy = -1 * (this.location.distance(new Point(this.location.getX(), this.minLimit)) - this.size);
            dyChange = -1;
            change = true;
            System.out.println("Upper border jump activated.");
        } else if (down && checkLowerBorder_V2()) {
            dy = this.location.distance(new Point(this.location.getX(), this.maxY)) - this.size;
            dyChange = -1;
            change = true;
            System.out.println("Lower border jump activated.");
        }

        // Check if the ball will hit the rectangle frame.
        Line trajectory;
        trajectory = this.getTrajectory(new Point(this.simulateX() - this.size, this.getY()));
        if (left && trajectory.intersectionWith(this.gray.getL3()) != null) {
            double distance = trajectory.length();
            dx =
            dy = trajectory.end().getY() - trajectory.start().getY();
            dxChange = -1;
            change = true;
        }

        trajectory = this.getTrajectory(new Point(this.simulateX() + this.size, this.getY()));
        if (right && trajectory.intersectionWith(this.gray.getL2()) != null) {}

        if (change) {
            Velocity bringToEdge = new Velocity(dx, dy);
            this.location = bringToEdge.applyToPoint(this.location);
            this.setVelocity(dxChange * this.getVelocity().getDx(), dyChange * this.getVelocity().getDy());
        } else {
            this.location = this.getVelocity().applyToPoint(this.location);
        }
    }

    private Line getTrajectory(Point start) {
        Point end = this.velocity.applyToPoint(start);
        return new Line(start, end);
    }

    private double frameIntersectDx(Line trajectory) {
        return trajectory.end().getX() - trajectory.start().getX();
    }

    private double frameIntersectDy(Line trajectory) {
        return trajectory.end().getY() - trajectory.start().getY();
    }

    // These methods check if the ball will cross one of the boundaries on it's next move.
    private boolean checkLeftBorder_V2() {
        double leftX = this.simulateX() - this.size;
        // Ball is either in the gray rectangle or in the narrow region of the GUI.
        return leftX <= this.minLimit;
    }

    private boolean checkRightBorder_V2() {
        double rightX = this.simulateX() + this.size;
        // Ball is either in the gray rectangle or in the wider region of the GUI.
        return rightX >= this.maxX;
    }

    private boolean checkUpperBorder_V2() {
        double upperY = this.simulateY() - this.size;
        // Ball is either in the gray rectangle or in the narrow region above the gray rectangle.
        return upperY <= this.minLimit;
    }

    private boolean checkLowerBorder_V2() {
        double lowerY = this.simulateY() + this.size;
        // The ball is either in the gray rectangle or is below the gray rectangle.
        return lowerY >= this.maxY;
    }
    */
}