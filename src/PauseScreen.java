
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The type Pause screen.
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * Instantiates a new Pause screen.
     *
     * @param keyboard the keyboard
     */
    public PauseScreen(KeyboardSensor keyboard) {
        this.keyboard = keyboard;
        this.stop = false;
    }
@Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(10, d.getHeight() / 2,
                "Paused -- Press Space to Continue", 32);
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }
@Override
    public boolean shouldStop() {
        return this.stop;
    }
}
