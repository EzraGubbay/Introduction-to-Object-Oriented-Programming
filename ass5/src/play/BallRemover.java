package play;

import sprites.Block;
import sprites.Ball;

public class BallRemover implements HitListener {

    Game game;
    Counter availableBalls;

    public BallRemover(Game game, Counter availableBalls) {
        this.game = game;
        this.availableBalls = availableBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.availableBalls.decrease(1);
    }
}
