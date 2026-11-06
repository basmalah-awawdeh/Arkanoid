
/**
 * The type Block remover.
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;
    private Counter scoreCounter;
    private static final int ONE = 1;
    private static final int FINISH = 100;
    private static final int ZERO = 0;

    /**
     * Instantiates a new Block remover.
     *
     * @param gameLevel       the game level
     * @param remainingBlocks the remaining blocks
     * @param scoreCounter    the score counter
     */
    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks,
                        Counter scoreCounter) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
        this.scoreCounter = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        int remaining = gameLevel.getRemainingBlocks();
        beingHit.removeFromGame(gameLevel);
        remainingBlocks.decrease(ONE);

        // Remove the block from the game environment.
        gameLevel.removeCollidable(beingHit);
        // Remove it from the sprites collection
        gameLevel.removeSprite(beingHit);

        // Increase the score by 5 for each block hit.
        scoreCounter.increase(5);

        // If all the blocks have been removed.
        if (remainingBlocks.getValue() == ZERO) {
            // Increase the score by 100 for finishing the level.
            scoreCounter.increase(FINISH);
        }

        // Decrease the remaining blocks counter.
        gameLevel.setRemainingBlocks(remaining - ONE);
    }
}

