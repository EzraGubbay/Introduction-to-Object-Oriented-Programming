package gameplay;

import sprites.Block;
import sprites.Ball;

public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit - A block that is being hit by a ball.
     * @param hitter - A ball that is hitting a block.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
