package gameplay;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import geometry.Point;
import sprites.Ball;
import sprites.Sprite;
import sprites.Block;
import sprites.DeathRegion;
import sprites.ScoreIndicator;
import sprites.SpriteCollection;
import sprites.Paddle;
import motion.GameEnvironment;
import motion.Collidable;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Name: Ezra Gubbay.
 * ID: 209184308
 * Description - The gameplay.Game class. Represents a game, and can initialize and run the game.
 * Manages passing of time in the game and notifying pieces to draw themselves.
 */
public class Game {

    private final SpriteCollection sprites; // The collection of sprites in the game
    private final GameEnvironment environment; // The gameplay.Game's Environment for managing logic and collisions.
    private GUI gui;
    private Sleeper sleeper;
    private final Counter remainingBlocks;
    private final Counter availableBalls;
    private final Counter score;

    /**
     * Constructor.
     * Initializes the sprite collection and the game environment
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment(new ArrayList<Collidable>());
        this.remainingBlocks = new Counter();
        this.availableBalls = new Counter();
        this.score = new Counter();
    }

    /**
     * Adds a collidable to the gameplay.Game Environment's list of collidables.
     * @param c - A collidable to be added to the list
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a sprite to the gameplay.Game's list of sprites.
     * @param s - A sprite to be added to the list
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Removes a collidable object from the game.
     * @param c - The collidable object that should be removed.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes a sprite from the game.
     * @param s - The sprite that should be removed.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Accessor for GameEnvironment object.
     * @return The gameplay.Game's gameplay.Game Environment.
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

        // Create three balls
        Ball firstBall = new Ball(new Point(40, 400), 5, Color.BLACK);
        firstBall.addToGame(this);
        Ball secondBall = new Ball(new Point(760, 400), 5, Color.BLACK);
        secondBall.addToGame(this);
        Ball thirdBall = new Ball(new Point(400, 400), 5, Color.BLACK);
        thirdBall.addToGame(this);
        this.availableBalls.increase(3);

        // Create the frame
        this.createFrame();

        // Create the paddle
        Paddle paddle = new Paddle();
        paddle.addToGame(this);
        paddle.setGUI(this.gui);

        // Creating a BlockRemover listener and give it the game's counter.
        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);

        // Creating a ScoreTrackingListener to track the score when a block is destroyed.
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(score);

        // Creating additional in-game blocks
        int blockWidth = 52, blockHeight = 30, rows = 7;
        Color[] rowPalette = {Color.GRAY, Color.GREEN, Color.PINK, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.ORANGE};
        for (int i = 0; i < rows; i++) {
            for (int x = 210 + i * blockWidth; x < 790; x += blockWidth) {
                Point upperLeft = new Point(x - 45, 100 + i * blockHeight);
                Block block = new Block(upperLeft, blockWidth, blockHeight);
                block.setColor(rowPalette[i]);
                block.addToGame(this);
                remainingBlocks.increase(1);
                block.addHitListener(blockRemover);
                block.addHitListener(scoreTracker);
            }
        }
    }

    /**
     * Creates the Block objects at the edge of the GUI, to prevent the ball from moving out of the GUI's boundaries.
     */
    private void createFrame() {
        Block left = new Block(new Point(0, 30), 10, 600);
        Block right = new Block(new Point(790, 30), 10, 600);
        Block top = new Block(new Point(10, 30), 780, 10);
        Block bottom = new DeathRegion(new Point(10, 620), 780, 10);
        Block[] frame = {left, right, top, bottom};

        for (Block block : frame) {
            block.addToGame(this);
        }

        // Create the ScoreIndicator at the top of the GUI.
        Sprite scoreIndicator = new ScoreIndicator(new Point(350, 20), this.score);
        scoreIndicator.addToGame(this);

        // Create a BallRemover listener and add the bottom "death region" block to the listener.
        BallRemover ballRemover = new BallRemover(this, this.availableBalls);
        bottom.addHitListener(ballRemover);
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

            // Check if all blocks or balls have been removed. If so, stop the game.
            if (this.remainingBlocks.getValue() == 0 || this.availableBalls.getValue() == 0) {
                // Handle frame timing to make sure last block/ball disappearing and score update shown to the player.
                long usedTime = System.currentTimeMillis() - startTime;
                long millisecondsLeftToSleep = millisecondsPerFrame - usedTime;
                if (millisecondsLeftToSleep > 0) {
                    this.sleeper.sleepFor(millisecondsLeftToSleep);
                }
                gui.close();
                return;
            }

            // Notify all sprites that time has passed.
            this.sprites.notifyAllTimePassed();

            // If all blocks have now been destroyed, add 100 points to the score.
            if (this.remainingBlocks.getValue() == 0) {
                this.score.increase(100);
            }

            // Timing
            long usedTime = System.currentTimeMillis() - startTime;
            long millisecondsLeftToSleep = millisecondsPerFrame - usedTime;
            if (millisecondsLeftToSleep > 0) {
                this.sleeper.sleepFor(millisecondsLeftToSleep);
            }
        }
    }
}
