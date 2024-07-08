package gameplay;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The Counter class. Represents a basic counter that keeps track of the quantity of something.
 */
public class Counter {

    private int count;

    /**
     * Constructor.
     */
    public Counter() {
        this.count = 0;
    }

    /**
     * Increases the value of this counter by the number provided.
     * @param number - The amount by which this counter's value should be increased.
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Decreases the value of this counter by the number provided.
     * @param number - The amount by which this counter's value should be decreased.
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * Accessor for the value of this counter.
     * @return The value of this counter.
     */
    public int getValue() {
        return this.count;
    }
}
