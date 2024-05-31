import biuoop.DrawSurface;

import java.awt.Color;

public class Rectangle {

    private Point topLeft;
    private double width;
    private double height;
    private Color color;
    private Line[] lines = new Line[4];

    public Rectangle(Point topLeft, double width, double height, Color color) {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
        this.color = color;
        createRectangleFrame();
    }

    public Rectangle(double x, double y, double width, double height, Color color) {
        this.topLeft = new Point(x, y);
        this.width = width;
        this.height = height;
        this.color = color;
        createRectangleFrame();
    }

    /*
     *          l3
     *      ----------
     *     |          |
     *  l1 |          | l2
     *     |          |
     *      ----------
     *          l4
     */
    private void createRectangleFrame() {
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

    public Point getTopLeft() {
        return this.topLeft;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public Line[] getLines(){
        return this.lines;
    }

    public Point intersectionWith(Line trajectory) {
        Point intersection = null, temp = null, trajectoryStart = trajectory.start();
        for (Line line : this.lines) {
            if (line.intersectionWith(trajectory) != null) {
                if (intersection == null && !line.intersectionWith(trajectory).equals(trajectoryStart)) {
                    intersection = line.intersectionWith(trajectory);
                    continue;
                }
                temp = line.intersectionWith(trajectory);
                double tempDistance = temp.distance(trajectoryStart);
                if (!temp.equals(trajectoryStart) && tempDistance < intersection.distance(trajectoryStart)) {
                    intersection = temp;
                }
            }
        }

        return intersection;
    }

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
