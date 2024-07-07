package gameplay;

public interface HitNotifier {

    /**
     * Subscribes a new listener.
     * @param hl - A HitListener that should be subscribed.
     */
    void addHitListener(HitListener hl);

    /**
     * Unsubscribes a new listener.
     * @param hl - A HitListener that should be unsubscribed.
     */
    void removeHitListener(HitListener hl);
}
