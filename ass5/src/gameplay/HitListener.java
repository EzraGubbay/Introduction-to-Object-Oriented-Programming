package gameplay;

import sprites.Block;
import sprites.Ball;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The HitListener interface. Represents event listeners that listen for subscribed objects being hit.
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit - A block that is being hit by a ball.
     * @param hitter - A ball that is hitting a block.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
