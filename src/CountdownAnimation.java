
import biuoop.DrawSurface;


import java.awt.Color;

/**
 * The type Countdown animation.
 */
public class CountdownAnimation implements Animation {
    private double countdownFrom;
    private int countFrom;
    private SpriteCollection gameScreen;
    private boolean stop;
    private long startTime;
    private long prevTime;
    private long millisecondsPerCount;
    private static final int ZERO = 0;
    private static final int TWO = 2;

    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(double numOfSeconds, int countFrom,
                              SpriteCollection gameScreen) {
        this.countdownFrom = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
        this.startTime = System.currentTimeMillis();
        this.prevTime = this.startTime;
        this.millisecondsPerCount = (long) (1000 * numOfSeconds / countFrom);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Draw the game screen.
        gameScreen.drawAllOn(d);

        // Calculate elapsed time.
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - prevTime;

        // Calculate remaining time in the countdown.
        double remainingTime = countdownFrom - (currentTime - startTime)
                / 1000.0;

        // Check if it's time to display next count.
        if (elapsedTime >= millisecondsPerCount && countFrom > ZERO) {
            countFrom--;
            prevTime = currentTime;
        }

        // Draw countdown number.
        d.setColor(Color.RED);
        if (countFrom > 0) {
            d.drawText(d.getWidth() / 2, d.getHeight() / TWO,
                    Integer.toString(countFrom), 72);
        } else {
            d.drawText(d.getWidth() / 2, d.getHeight() / TWO, "Go!",
                    72);
        }

        // Check if countdown is finished.
        if (remainingTime <= ZERO) {
            stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return stop;
    }
}
