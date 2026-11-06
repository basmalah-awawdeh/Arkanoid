
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * A decorator class for animations that stop when a key is pressed.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboardSensor;
    private String key;
    private Animation animation;
    private boolean isKeyPressed;
    private boolean isFinished;

    /**
     * Constructs a KeyPressStoppableAnimation.
     *
     * @param sensor    the keyboard sensor for detecting key presses
     * @param key       the key that stops the animation
     * @param animation the animation to decorate
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key,
                                      Animation animation) {
        this.keyboardSensor = sensor;
        this.key = key;
        this.animation = animation;
        this.isKeyPressed = false;
        this.isFinished = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        animation.doOneFrame(d);
        if (keyboardSensor.isPressed(key)) {
            isKeyPressed = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return isKeyPressed;
    }
}
