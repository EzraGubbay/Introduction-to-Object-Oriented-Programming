/**
 * @author Ezra Gubbay
 * ID 209184308
 * Description - The MultipleFramesBouncingBallAnimation class
 */
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.Random;

public class MultipleFramesBouncingBallsAnimation {

    /**
     * Calculates and returns a velocity based on a given ball size. The larger the ball, the slower the speed.
     * @param size - A radius of a ball for which we want to calculate a velocity
     * @return A Velocity object containing the velocity of the ball
     */
    private static Velocity calculateVelocity(int size) {
        Random rand = new Random();

        /*
         * A base speed of 100 / size. 5 is added so very large balls will still move smoothly on the screen and won't
         * appear static.
         */
        double multiplier = 100, minimum = 10;
        double speed = multiplier * (1.0 / size) + minimum;

        double angle = rand.nextDouble() * 360 + 1;
        return Velocity.fromAngleAndSpeed(angle, speed);

    }

    /**
     * Assigns velocities to each ball in the given array.
     * @param balls - An array of all the balls we will have in the GUI
     */
    private static void setVelocities(Ball[] balls) {
        Velocity v;
        for (Ball ball : balls) {
            v = calculateVelocity(ball.getSize());
            ball.setVelocity(v);
        }
    }

    /**
     * Generates the balls that should bounce in the GUI
     * @param sizes - An array of sizes, each for a separate ball that should be created.
     * @return An array of Balls
     */
    private static Ball[] generateBalls(int[] sizes) {
        Ball[] balls = new Ball[sizes.length];
        int outside = (int) (sizes.length / 2.0);
        Random rand = new Random();

        for (int i = 0; i < sizes.length; i++) {
            // We will limit the balls to reasonable size 50.
            if (sizes[i] > 50) {
                sizes[i] = 50;
            }
        }

        // Generate balls outside gray rectangle.
        for (int i = 0; i < outside; i++) {

            // Generate random doubles. These will later be resolved to be coordinates outside the gray rectangle.
            double randX = rand.nextDouble() * (350 - sizes[i]) + sizes[i];
            double randY = rand.nextDouble() * (150 - sizes[i]) + sizes[i];

            // Resolve coordinates
            double x = resolveCoordinate(randX);
            double y = resolveCoordinate(randY);

            // Create the ball
            Point center = new Point(x, y);
            balls[i] = new Ball(center, sizes[i], Color.BLACK);
        }

        // Generate balls within gray rectangle.
        for (int i = outside; i < sizes.length; i++) {

            // Generate random coordinates. We add 51 to make the range inclusive and within the gray rectangle.
            double x = rand.nextDouble() * (500 - sizes[i]) + sizes[i];
            double y = rand.nextDouble() * (500 - sizes[i]) + sizes[i];

            // Create the ball
            Point center = new Point(x, y);
            balls[i] = new Ball(center, sizes[i], Color.BLACK);
        }

        return balls;
    }

    /**
     * X Coordinate: if randX is a value within the range of x coordinate values of the gray rectangle,
     * we add 450 to randX, so it will land outside the gray rectangle.
     * Otherwise, we use the randX value as is. Similarly, for the y coordinate value.
     * Used as an auxiliary function for calculating coordinates of new balls that should be placed in the
     * gray rectangle
     * @param value - A coordinate that should be resolved
     * @return A double variable containing the resolved coordinate
     */
    private static double resolveCoordinate(double value) {
        int limit = 50;
        if (value > limit) {
            return value + 450;
        }
        return value;
    }

    /**
     * Draws the animation of the balls and the GUI. Responsible for managing initialization and movement of the balls
     * in the GUI
     * @param sizes - An array of sizes of the balls that should be generated
     */
    private static void drawAnimation(int[] sizes) {

        // Create GUI
        GUI gui = new GUI("Multiple Bouncing Balls Animation", 800, 600);
        Sleeper sleeper = new Sleeper();

        // Generate rectangles
        Rectangle gray = new Rectangle(50, 50, 450, 450, Color.GRAY);
        Rectangle yellow = new Rectangle(450, 450, 150, 150, Color.YELLOW);

        // Generate balls
        Ball[] balls = generateBalls(sizes);

        // Set ball velocities - velocity varies based on ball size. The larger the ball, the slower it is.
        setVelocities(balls);

        while (true) {
            DrawSurface d = gui.getDrawSurface();
            gray.drawOn(d);
            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(d);
            }
            yellow.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50); // Sleep for 50 milliseconds, creating roughly 20 frames per second.
        }
    }

    /**
     * Main method
     * @param args - Command line arguments containing the balls' sizes.
     */
    public static void main(String[] args) {
        // Array with all the ball sizes parsed into integers.
        int[] sizes = new int[args.length];

        for (int i = 0; i < args.length; i++) {
            sizes[i] = Integer.parseInt(args[i]);
        }

        drawAnimation(sizes);
    }
}
