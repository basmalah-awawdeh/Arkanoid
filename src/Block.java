
import biuoop.DrawSurface;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Block.
 */
public class Block implements Collidable, Sprite {
    private Rectangle rect;
    private Color color;
    private int hitPoints;
    private List<HitListener> hitListeners;

    private static final double MIN_COLLISION_RATIO = 0.1;
    private static final double MAX_COLLISION_RATIO = 0.9;

    /**
     * Instantiates a new Block.
     *
     * @param rect  the rect
     * @param color the color
     */
    public Block(Rectangle rect, Color color) {
        this.rect = rect;
        this.color = color;
        this.hitPoints = 1;
        this.hitListeners = new ArrayList<>();
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return rect;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint,
                        Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        // Check where the collision occurred from the center of the block
        double relativeX = (collisionPoint.getX()
                - rect.getUpperLeft().getX()) / rect.getWidth();
        double relativeY = (collisionPoint.getY()
                - rect.getUpperLeft().getY()) / rect.getHeight();

        // Determine the new velocity based on the collision location
        if (relativeX <= MIN_COLLISION_RATIO
                || relativeX >= MAX_COLLISION_RATIO) {
            dx = -dx;
        }
        if (relativeY <= MIN_COLLISION_RATIO
                || relativeY >= MAX_COLLISION_RATIO) {
            dy = -dy;
        }
        // Decrease the hit points
        hitPoints--;

        // Notify hit listeners
        notifyHitListeners(hitter);

        return new Velocity(dx, dy);
    }

    /**
     * Notifies the hit listeners that a collision occurred with this block.
     *
     * @param hitter the ball that hit the block
     */
    private void notifyHitListeners(Ball hitter) {
        List<HitListener> listeners = new ArrayList<>(hitListeners);
        for (HitListener listener : listeners) {
            listener.hitEvent(this, hitter);
        }
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Add to game.
     *
     * @param game the game
     */
    public void addToGame(GameLevel game) {
        game.addCollidable(this);
        game.addSprite(this);
    }

    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillRectangle((int) rect.getUpperLeft().getX(),
                (int) rect.getUpperLeft().getY(), (int) rect.getWidth(),
                (int) rect.getHeight());
        surface.setColor(Color.BLACK);
        surface.drawRectangle((int) rect.getUpperLeft().getX(),
                (int) rect.getUpperLeft().getY(), (int) rect.getWidth(),
                (int) rect.getHeight());
    }

    @Override
    public void timePassed() {
        // Do nothing
    }

    /**
     * Remove from game.
     *
     * @param game the game
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Add hit listener.
     *
     * @param hl the hl
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * Remove hit listener.
     *
     * @param hl the hl
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * Gets hit points.
     *
     * @return the hit points
     */
    public int getHitPoints() {
        return hitPoints;
    }
}
