package gameplay;

import sprites.Block;
import sprites.Ball;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The ScoreTrackingListener. An event listener responsible for managing addition of points to the game
 * score when an in-game Block is destroyed.
 */
public class ScoreTrackingListener implements HitListener {
    private final Counter currentScore;

    /**
     * Constructor.
     * @param currentScore - The Counter object that is counting the score in the current game.
     */
    public ScoreTrackingListener(Counter currentScore) {
        this.currentScore = currentScore;
    }

    /**
     * Called whenever a Block is hit and destroyed. Adds five points to the game score.
     * @param beingHit - A block that is being hit by a ball.
     * @param hitter - A ball that is hitting a block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(5);
    }
}
