
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * The type Sprite collection.
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Instantiates a new Sprite collection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * Add sprite.
     *
     * @param s the s
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Notify all time passed.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite sprite : spritesCopy) {
            sprite.timePassed();
            if (sprite instanceof Ball) {
                Ball ball = (Ball) sprite;
                ball.moveOneStep();
            }
        }
    }

    /**
     * Draw all on.
     *
     * @param d the d
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : sprites) {
            sprite.drawOn(d);
        }
    }

    /**
     * Remove sprite.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }

    /**
     * Get all sprites in the collection.
     *
     * @return the list of sprites
     */
    public List<Sprite> getSprites() {
        return sprites;
    }
}



