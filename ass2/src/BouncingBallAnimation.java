/**
 * @author Ezra Gubbay
 * ID 209184308
 * Description - The BouncingBallAnimation class
 */
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;

public class BouncingBallAnimation {

    /**
     * Draws the animation of the balls and the GUI. Responsible for managing when to move the ball and it's generation
     * @param start - The starting point of the ball
     * @param dx - The starting dx value of the ball's velocity
     * @param dy - The starting dy value of the ball's velocity
     */
    private static void drawAnimation(Point start, double dx, double dy) {
        GUI gui = new GUI("title",800,600);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(start.getX(), start.getY(), 30, Color.BLACK);
        ball.setVelocity(dx, dy);
        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50); // wait for 50 milliseconds.
        }
    }

    /**
     * Main method
     * @param args - Command line arguments containing the ball's starting point, dx and dy values.
     */
    public static void main(String[] args) {
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        double dx = Double.parseDouble(args[2]);
        double dy = Double.parseDouble(args[3]);
        if (x < 31 || x > 771 || y < 31 || y > 571) {
            System.out.println("Starting coordinates in invalid range.");
        } else {
            drawAnimation(new Point(x, y), dx, dy);
        }
    }
}
