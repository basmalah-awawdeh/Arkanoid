
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The type Ball.
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private GameEnvironment environment;
    private GameLevel game;

    /**
     * Instantiates a new Ball.
     *
     * @param center      the center
     * @param radius      the radius
     * @param color       the color
     * @param environment the environment
     * @param game        the game
     */
    public Ball(Point center, int radius, Color color,
                GameEnvironment environment, GameLevel game) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.environment = environment;
        this.game = game;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Gets velocity.
     *
     * @return the velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Sets velocity.
     *
     * @param newVelocity the new velocity
     */
    public void setVelocity(Velocity newVelocity) {
        this.velocity = newVelocity;
    }

    /**
     * Sets velocity.
     *
     * @param dx the dx
     * @param dy the dy
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Move one step.
     */
    public void moveOneStep() {
        Point end = this.velocity.applyToPoint(this.center);
        Line traj = new Line(this.center, end);
        CollisionInfo c = environment.getClosestCollision(traj);
        if (c == null) {
            this.center = end;
        } else {
            Point collisionP = c.collisionPoint();
            Line l = new Line(center, collisionP);
            this.center = l.middle();
            this.velocity = c.collisionObject().hit(this, collisionP,
                    this.velocity);
        }
    }


    /**
     * Draw the ball on the given DrawSurface.
     *
     * @param surface the surface to draw on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.WHITE);
        surface.fillCircle(this.getX(), this.getY(), this.getSize());

        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.getSize());
    }


    /**
     * Notify the ball that a time unit has passed.
     */
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Add to game.
     *
     * @param g the g
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Remove from game.
     *
     * @param game the game
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }
}


