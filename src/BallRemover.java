
/**
 * The type Ball remover.
 */
public class BallRemover implements HitListener {
    private static final int ONE = 1;
    private GameLevel game;
    private Counter ballCounter;

    /**
     * Instantiates a new Ball remover.
     *
     * @param game        the game
     * @param ballCounter the ball counter
     */
    public BallRemover(GameLevel game, Counter ballCounter) {
        this.game = game;
        this.ballCounter = ballCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Remove the ball from the game.
        hitter.removeFromGame(game);
        // Decrease the ball counter.
        ballCounter.decrease(ONE);
        // Decrease the remaining balls counter.
        int remainingBalls = game.getRemainingBalls();
        game.setRemainingBalls(remainingBalls - ONE);
    }
}


