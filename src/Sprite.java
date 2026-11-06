
import biuoop.DrawSurface;

/**
 * The interface Sprite.
 */
public interface Sprite {
    /**
     * Draw on.
     *
     * @param d the d
     */
    void drawOn(DrawSurface d);

    /**
     * Time passed - notify the sprite that time has passed.
     */
    void timePassed();
}
