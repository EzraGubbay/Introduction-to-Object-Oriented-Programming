/**
 * @author Ezra Gubbay
 * ID 209184308
 * Description - The MultipleBouncingBallAnimation class
 */
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.Random;

public class MultipleBouncingBallsAnimation {

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
        double multiplier = 100, minimum = 5;
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
        for (int i = 0; i < sizes.length; i++) {
            // We will limit the balls to reasonable size 50.
            if (sizes[i] > 50) {
                sizes[i] = 50;
            }
            balls[i] = new Ball(getRandomLocation(sizes[i]), sizes[i], Color.BLACK);
        }

        return balls;
    }

    /**
     * Provides a random point within in fixed GUI sizes.
     * @param sizeLimit - The size of a ball for which the random location is being generated
     * @return A Point representing the random location in the GUI where the ball should be generated.
     */
    private static Point getRandomLocation(int sizeLimit) {
        Random rand = new Random();
        double x = rand.nextDouble() * (800 - sizeLimit) + sizeLimit; // Get a random double in range 0-200
        double y = rand.nextDouble() * (600 - sizeLimit) + sizeLimit; // Get a random double in range 0-200
        return new Point(x, y);
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

        // Generate balls
        Ball[] balls = generateBalls(sizes);

        // Set ball velocities - velocity varies based on ball size. The larger the ball, the slower it is.
        setVelocities(balls);

        while (true) {
            DrawSurface d = gui.getDrawSurface();
            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(d);
            }
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
