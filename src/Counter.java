
/**
 * The type Counter.
 */
public class Counter {
    private int count;

    /**
     * Increase.
     *
     * @param number the number
     */
    public void increase(int number) {
        count += number;
    }

    /**
     * Decrease.
     *
     * @param number the number
     */
    public void decrease(int number) {
        count -= number;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return count;
    }
    /**
     * Sets the value of the counter.
     *
     * @param value the new value to set
     */
    public void setValue(int value) {
        count = value;
    }
}
