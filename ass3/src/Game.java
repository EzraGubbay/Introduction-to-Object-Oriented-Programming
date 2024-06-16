import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Name: Ezra Gubbay.
 * ID: 209184308
 * Description - The Game class. Represents a game, and can initialize and run the game.
 * Manages passing of time in the game and notifying pieces to draw themselves.
 */
public class Game {

    private final SpriteCollection sprites; // The collection of sprites in the game
    private final GameEnvironment environment; // The Game's Environment for managing logic and collisions.
    private GUI gui;
    private Sleeper sleeper;

    /**
     * Constructor.
     * Initializes the sprite collection and the game environment
     */
    public Game() {
        sprites = new SpriteCollection();
        environment = new GameEnvironment(new ArrayList<Collidable>());
    }

    /**
     * Adds a collidable to the Game Environment's list of collidables.
     * @param c - A collidable to be added to the list
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a sprite to the Game's list of sprites.
     * @param s - A sprite to be added to the list
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Accessor for GameEnvironment object.
     * @return The Game's Game Environment.
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * Initialize a new game: create the GUI, Sleeper, Blocks, Ball and Paddle, and add them to the game.
     */
    public void initialize() {
        // Create the GUI and Sleeper
        this.gui = new GUI("Arkanoid - Assignment 3", 800, 600);
        this.sleeper = new Sleeper();

        // Create two balls
        Ball firstBall = new Ball(new Point(40, 400), 5, Color.BLACK);
        firstBall.addToGame(this);
        Ball secondBall = new Ball(new Point(760, 400), 5, Color.BLACK);
        secondBall.addToGame(this);

        // Create the frame
        this.createFrame();

        // Create the paddle
        Paddle paddle = new Paddle();
        paddle.addToGame(this);
        paddle.setGUI(this.gui);

        // Creating additional in-game blocks
        int blockWidth = 52, blockHeight = 30, rows = 7;
        Color[] rowPalette = {Color.GRAY, Color.GREEN, Color.PINK, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.ORANGE};
        for (int i = 0; i < rows; i++) {
            for (int x = 210 + i * blockWidth; x < 790; x += blockWidth) {
                Point upperLeft = new Point(x - 45, 100 + i * blockHeight);
                Block block = new Block(upperLeft, blockWidth, blockHeight);
                block.setColor(rowPalette[i]);
                block.addToGame(this);
            }
        }
    }

    /**
     * Creates the Block objects at the edge of the GUI, to prevent the ball from moving out of the GUI's boundaries.
     */
    private void createFrame() {
        Block left = new Block(new Point(0, 0), 10, 600);
        Block right = new Block(new Point(790, 0), 10, 600);
        Block top = new Block(new Point(10, 0), 780, 10);
        Block bottom = new Block(new Point(10, 590), 780, 10);
        Block[] frame = {left, right, top, bottom};

        for (Block block : frame) {
            block.addToGame(this);
        }
    }

    /**
     * Run the game - start the animation loop.
     */
    public void run() {
        // Calculating the necessary milliseconds for each frame.
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis();

            // Draw the items on the GUI and notify all items that time has passed.
            DrawSurface d = this.gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            this.gui.show(d);
            this.sprites.notifyAllTimePassed();

            // Timing
            long usedTime = System.currentTimeMillis() - startTime;
            long millisecondsLeftToSleep = millisecondsPerFrame - usedTime;
            if (millisecondsLeftToSleep > 0) {
                this.sleeper.sleepFor(millisecondsLeftToSleep);
            }
        }
    }
}
