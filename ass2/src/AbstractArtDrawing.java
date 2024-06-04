/**
 * @author Ezra Gubbay
 * ID 209184308
 * Description - The AbstractArtDrawing class
 */
import biuoop.GUI;
import biuoop.DrawSurface;
import java.util.Random;
import java.awt.Color;

public class AbstractArtDrawing {

    /**
     * Colors the lines as follows:
     * red - intersection points between lines
     * blue - the middle points of the lines
     * green - parts of lines that form a triangle with other lines
     * @param lines - An array of lines in the GUI
     * @param d - A DrawSurface object to draw the lines and points on
     */
    public static void colorPoints(Line[] lines, DrawSurface d){
        Point middle, intersection; // Used to contain middle and intersection points of the lines when drawing them.
        int radius = 3; // We have chosen an arbitrary radius of 5 to draw the colored points and make them visible.

        // Drawing the middle points of each line in blue.
        for (Line line : lines) {
            middle = line.middle();
            int middleX = (int) middle.getX(), middleY = (int) middle.getY();
            d.setColor(Color.BLUE);
            d.fillCircle(middleX, middleY, radius);
        }

        for (int i = 0; i < lines.length - 1; i++) {
            // Drawing intersection points in red.
            for (int j = i + 1; j < lines.length; j++) {
                if (lines[i].intersectionWith(lines[j]) != null) {
                    intersection = lines[i].intersectionWith(lines[j]);
                    int intersectionX = (int) intersection.getX(), intersectionY = (int) intersection.getY();
                    d.setColor(Color.RED);
                    d.fillCircle(intersectionX, intersectionY, radius);
                }
            }
        }

        for (int i = 0; i < lines.length - 2; i++) {

            // Drawing triangles formed by the lines' intersections in green.
            for (int j = i + 1; j < lines.length - 1; j++) {
                for (int k = j + 1; k < lines.length; k++) {
                    if (lines[i].isInTriangle(lines[j], lines[k])) {
                        Point intersectionA = lines[i].intersectionWith(lines[j]);
                        Point intersectionB = lines[i].intersectionWith(lines[k]);
                        Point intersectionC = lines[j].intersectionWith(lines[k]);
                        d.setColor(Color.GREEN);
                        d.drawLine((int) intersectionA.getX(), (int) intersectionA.getY(),
                                (int) intersectionB.getX(), (int) intersectionB.getY());
                        d.drawLine((int) intersectionA.getX(), (int) intersectionA.getY(),
                                (int) intersectionC.getX(), (int) intersectionC.getY());
                        d.drawLine((int) intersectionB.getX(), (int) intersectionB.getY(),
                                (int) intersectionC.getX(), (int) intersectionC.getY());
                    }
                }
            }
        }
    }

    /**
     * Receives a line and draws it on the GUI
     * @param l - A Line object to draw on the GUI
     * @param d - A DrawSurface object to draw the line on
     */
    public void drawLine(Line l, DrawSurface d) {
        // drawLine method in biuoop receives only integers and not doubles, therefore we must typecast the x/y values.
        d.drawLine((int) l.start().getX(), (int) l.start().getY(), (int) l.end().getX(), (int) l.end().getY());
    }

    /**
     * Generates a random line
     * @return A randomly generated Line object within the limits of the GUI frame.
     */
    public Line generateRandomLine() {
        Random rand = new Random();
        // get integer in range 1-400
        int x1 = rand.nextInt(400) + 1;
        // get integer in range 1-300
        int y1 = rand.nextInt(300) + 1;
        // get integer in range 1-400
        int x2 = rand.nextInt(400) + 1;
        // get integer in range 1-300
        int y2 = rand.nextInt(300) + 1;

        return new Line(x1, y1, x2, y2);
    }

    /**
     * Draws 10 random lines on the GUI, with colored intersection and middle points and triangles formed by the lines
     */
    public void drawRandomLines() {

        Line[] lines = new Line[10];

        // create a random-number generator
        // Create a window with the title "Random Lines Example"
        // which is 400 pixels wide and 300 pixels high.
        GUI gui = new GUI("AbstractArtDrawing", 400, 300);
        DrawSurface d = gui.getDrawSurface();

        // Generate the lines randomly
        for (int i = 0; i < 10; ++i) {
            lines[i] = generateRandomLine();
            drawLine(lines[i], d);
        }

        // Color the points and lines as needed.
        colorPoints(lines, d);
        gui.show(d);
    }

    /**
     * Main method
     * @param args - Command line arguments. None are utilized in this main program
     */
    public static void main(String[] args) {
        AbstractArtDrawing gui = new AbstractArtDrawing();
        gui.drawRandomLines();
    }
}