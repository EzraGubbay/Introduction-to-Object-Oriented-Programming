package play;

import sprites.*;

public class PrintingHitListener implements HitListener {

    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A block was hit.");
    }
}
