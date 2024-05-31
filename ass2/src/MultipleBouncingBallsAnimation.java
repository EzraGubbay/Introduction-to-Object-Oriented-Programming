import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;

public class MultipleBouncingBallsAnimation {

    /*
     * Calculates and returns a velocity based on a given ball size. The larger the ball, the slower the speed.
     */
    private static Velocity calculateVelocity(int size) {
        Random rand = new Random();

        /*
         * A base speed of 100 / size. 5 is added so very large balls will still move smoothly on the screen and won't
         * appear static.
         */
        double multiplier = 100, minimum = 5;
        double speed = multiplier * (1 / size) + minimum;

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
        for (int i = 0; i < sizes.length; i++) {
            balls[i] = new Ball(getRandomLocation(), sizes[i], Color.BLACK);
        }

        return balls;
    }

    private static Point getRandomLocation() {
        Random rand = new Random();
        double x = rand.nextDouble(800) + 1; // Get a random double in range 0-200;
        double y = rand.nextDouble(600) + 1; // Get a random double in range 0-200;
        return new Point(x, y);
    }

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

    public static void main(String[] args) {

        // Array with all the ball sizes parsed into integers.
        int[] sizes = new int[args.length];

        for (int i = 0; i < args.length; i++) {
            sizes[i] = Integer.parseInt(args[i]);
        }

        drawAnimation(sizes);
    }
}
