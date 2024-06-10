// Testing class for Part 1

// Import section
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.ArrayList;

public class CollisionTest {

    public static void main(String[] args) {

        // Create GUI
        GUI gui = new GUI("Part 1 Test - Collision Test", 800, 600);
        DrawSurface d;
        Sleeper sleeper = new Sleeper();

        // Create Ball
        Ball ball = new Ball(100, 200, 10, Color.BLACK);

        // Create ArrayList of Collidables
        ArrayList<Collidable> collidables = new ArrayList<>();
        ArrayList<Block> blocks = new ArrayList<>();

        // Frame Blocks
        collidables.add(new Block(new Point(0, 0), 10, 600));
        collidables.add(new Block(new Point(10, 0), 780, 10));
        collidables.add(new Block(new Point(10, 590), 780, 10));
        collidables.add(new Block(new Point(790, 0), 10, 600));
        blocks.add(new Block(new Point(0, 0), 10, 600));
        blocks.add(new Block(new Point(10, 0), 780, 10));
        blocks.add(new Block(new Point(10, 590), 780, 10));
        blocks.add(new Block(new Point(790, 0), 10, 600));

/*        // Add some custom blocks to collide with
        collidables.add(new Block(new Point(332, 435), 25, 10));
        collidables.add(new Block(new Point(592, 603), 25, 10));
        blocks.add(new Block(new Point(332, 435), 25, 10));
        blocks.add(new Block(new Point(592, 603), 25, 10));*/

        // Create GameEnvironment, add Collidables to GameEnvironment and add to Ball.
        GameEnvironment ge = new GameEnvironment(collidables);
        ball.setGameEnvironment(ge);

        //DEBUG
        long curr, end;
        //DEBUG

        while(true) {
            d = gui.getDrawSurface();
            ball.drawOn(d);
            for (Block block : blocks) {
                block.drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(50);
            ball.moveOneStep();
        }
    }
}
