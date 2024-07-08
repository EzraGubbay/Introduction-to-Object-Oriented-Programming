package gameplay;

import sprites.Block;
import sprites.Ball;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The BlockRemover class. An event listener responsible for removing in-game Blocks that are destroyed
 * by the balls.
 */
public class BlockRemover implements HitListener {

    private final Game game;
    private final Counter remainingBlocks;

    /**
     * Constructor.
     * @param game - Game object of the current game that is being played.
     * @param remainingBlocks - The Counter object of the number of destroyable Blocks in the current game.
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Called when a Block is hit and destroyed by a ball.
     * Removes the Block from the Game and this listener from the Block.
     * Also decrements the number of Blocks in the game.
     * @param beingHit - A block that is being hit by a ball.
     * @param hitter - A ball that is hitting a block.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeFromGame(this.game);
        beingHit.removeHitListener(this);
        this.remainingBlocks.decrease(1);
    }
}
