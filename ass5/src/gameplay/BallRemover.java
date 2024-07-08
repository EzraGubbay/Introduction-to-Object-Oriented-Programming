package gameplay;

import sprites.Block;
import sprites.Ball;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The BallRemover class. An event listener that listens for a ball hitting the game's "death region"
 * and removes the ball from the game, as well as decreases the tracked number of balls in the game.
 */
public class BallRemover implements HitListener {

    private final Game game;
    private final Counter availableBalls;

    /**
     * Constructor.
     * @param game - The current game that is being played.
     * @param availableBalls - The counter that is tracking the number of balls currently in the game.
     */
    public BallRemover(Game game, Counter availableBalls) {
        this.game = game;
        this.availableBalls = availableBalls;
    }

    /**
     * Called when a ball hits the "death region" block. Removes the ball from the game and decrements the number
     * of tracked balls from the counter.
     * @param beingHit - A block that is being hit by a ball.
     * @param hitter - A ball that is hitting a block.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.availableBalls.decrease(1);
    }
}
