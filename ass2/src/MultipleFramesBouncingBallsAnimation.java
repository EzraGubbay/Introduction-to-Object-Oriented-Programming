import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;

public class MultipleFramesBouncingBallsAnimation {

    /*
     * Calculates and returns a velocity based on a given ball size. The larger the ball, the slower the speed.
     */
    private static Velocity calculateVelocity(int size) {
        Random rand = new Random();

        /*
         * A base speed of 100 / size. 5 is added so very large balls will still move smoothly on the screen and won't
         * appear static.
         */
        double multiplier = 100, minimum = 10;
        double speed = multiplier * (1.0 / size) + minimum;

        double angle = rand.nextDouble(360 + 1);
        return Velocity.fromAngleAndSpeed(angle, speed);

    }

    /*
     * Assigns velocities to each ball in the given array.
     */
    private static void setVelocities(Ball[] balls) {
        Velocity v;
        for (Ball ball : balls) {
            v = calculateVelocity(ball.getSize());
            ball.setVelocity(v);
        }
    }

    private static Ball[] generateBalls(int[] sizes) {
        Ball[] balls = new Ball[sizes.length];
        int outside = (int) (sizes.length / 2);
        Random rand = new Random();

        // Generate balls outside gray rectangle.
        for (int i = 0; i < outside; i++) {

            // Generate random doubles. These will later be resolved to be coordinates outside the gray rectangle.
            double randX = rand.nextDouble(350) + 1;
            double randY = rand.nextDouble(150) + 1;

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
            double x = rand.nextDouble(450) + 51;
            double y = rand.nextDouble(450) + 51;

            // Create the ball
            Point center = new Point(x, y);
            balls[i] = new Ball(center, sizes[i], Color.BLACK);
        }

        return balls;
    }

    /*
     * X Coordinate: if randX is a value within the range of x coordinate values of the gray rectangle,
     * we add 450 to randX, so it will land outside the gray rectangle.
     * Otherwise, we use the randX value as is. Similarly, for the y coordinate value.
     */
    private static double resolveCoordinate(double value) {
        int limit = 50;
        if (value > limit) {
            return value + 450;
        }
        return value;
    }

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
            yellow.drawOn(d);
            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(d);
            }

            gui.show(d);
            sleeper.sleepFor(500); // Sleep for 50 milliseconds, creating roughly 20 frames per second.
        }
    }

    public static void main(String[] args) {
        // Array with all the ball sizes parsed into integers.
        int[] sizes = new int[args.length];

        for (int i = 0; i < args.length; i++) {
            sizes[i] = Integer.parseInt(args[i]);
        }

        drawAnimation(sizes);
    }
}
