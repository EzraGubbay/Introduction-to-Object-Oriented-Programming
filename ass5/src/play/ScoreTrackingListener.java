package play;

import sprites.Block;
import sprites.Ball;

public class ScoreTrackingListener implements HitListener {
    private final Counter currentScore;

    public ScoreTrackingListener(Counter currentScore) {
        this.currentScore = currentScore;
    }

    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(5);
    }
}
