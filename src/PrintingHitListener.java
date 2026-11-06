
/**
 * The type Printing hit listener.
 */
public class PrintingHitListener implements HitListener {
    /**
     * This method is called when the ball hits a block.
     * It prints a message that a block was hit.
     *
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block was hit.");
    }
}