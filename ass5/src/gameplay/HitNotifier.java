package gameplay;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The HitNotifier interface. Represents objects that can subscribe to event listeners that listen
 * for the subscribing object being hit.
 */
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
