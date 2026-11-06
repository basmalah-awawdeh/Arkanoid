
/**
 * The type Score tracking listener.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter scoreCounter;
    private static final int ADD = 5;

    /**
     * Instantiates a new Score tracking listener.
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Increase the score by 5 when the ball hits a block
        scoreCounter.increase(ADD);
    }
}
