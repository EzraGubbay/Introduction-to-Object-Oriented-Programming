import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;

public class BouncingBallAnimation {

    private static void drawAnimation(Point start, double dx, double dy) {
        GUI gui = new GUI("title",800,600);
        Sleeper sleeper = new Sleeper();
        //Random rand = new Random();
        Ball ball = new Ball(start.getX(), start.getY(), 30, Color.BLACK);
        ball.setVelocity(dx, dy);
        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            //Ball ball = new Ball(rand.nextInt(200), rand.nextInt(200), 30, java.awt.Color.BLACK);
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50); // wait for 50 milliseconds.
        }
    }

    public static void main(String[] args) {
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        double dx = Double.parseDouble(args[2]);
        double dy = Double.parseDouble(args[3]);
        drawAnimation(new Point(x, y), dx, dy);
    }
}
