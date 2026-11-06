
import biuoop.DrawSurface;

/**
 * The type End screen animation.
 */
public class EndScreenAnimation implements Animation {
    private String message;

    /**
     * Instantiates a new End screen animation.
     *
     * @param message the message
     */
    public EndScreenAnimation(String message) {
        this.message = message;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Display end screen message.
        d.drawText(100, d.getHeight() / 2, message, 32);
    }

    @Override
    public boolean shouldStop() {
        // Stop animation after one frame.
        return true;
    }
}
