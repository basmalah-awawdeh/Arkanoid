
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.*;

/**
 * The type Paddle.
 */
public class Paddle implements Sprite, Collidable {
    private static final int NUM_REGIONS = 5;
    private static final int FIRST_ANGLE = 30;
    private static final int SECOND_ANGLE = 60;
    private static final int ROUND = 360;
    private static final int BARRIER = 10;
    private static final int WIDTH = 800;
    private biuoop.KeyboardSensor keyboard;
    private java.awt.Color color;
    private double speed;
    private Rectangle paddle;

    /**
     * Instantiates a new Paddle.
     *
     * @param paddle   the paddle
     * @param color    the color
     * @param keyboard the keyboard
     * @param speed    the speed
     */
    public Paddle(Rectangle paddle, Color color, KeyboardSensor keyboard,
                  int speed) {
        this.paddle = paddle;
        this.color = color;
        this.keyboard = keyboard;
        this.speed = speed;
    }

    /**
     * Move left.
     */
    public void moveLeft() {
        // Check that the paddle is not already at the left edge of the screen
        if (this.paddle.getUpperLeft().getX() > BARRIER) {
            Point newUpperLeft = new Point(this.paddle.getUpperLeft().getX()
                    - speed, this.paddle.getUpperLeft().getY());
            this.paddle = new Rectangle(newUpperLeft, this.paddle.getWidth(),
                    this.paddle.getHeight());
        }
    }

    /**
     * Move right.
     */
    public void moveRight() {
        // Check that the paddle is not already at the right edge of the screen
        if (this.paddle.getUpperLeft().getX() < WIDTH - BARRIER
                - this.paddle.getWidth()) {
            Point newUpperLeft = new Point(this.paddle.getUpperLeft().getX()
                    + speed, this.paddle.getUpperLeft().getY());
            this.paddle = new Rectangle(newUpperLeft, this.paddle.getWidth(),
                    this.paddle.getHeight());
        }
    }

    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.paddle.getUpperLeft().getX(),
                (int) this.paddle.getUpperLeft().getY(),
                (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
    }

    /**
     * Returns the collision rectangle of the paddle.
     *
     * @return the collision rectangle of the paddle.
     */
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }


    /**
     * Calculate and return new velocity of the ball after hitting the paddle
     * in a specific collision point.
     * The velocity will be changed according to the region of the paddle that
     * the ball hits.
     *
     * @param hitter          the ball that hit the paddle
     * @param collisionPoint  the point in which the ball hits the paddle
     * @param currentVelocity the current ball velocity before the collision
     * @return the new velocity of the ball after hitting the paddle
     */
    public Velocity hit(Ball hitter, Point collisionPoint,
                        Velocity currentVelocity) {
        // getting x coordinate.
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        double x = collisionPoint.getX();
        // dividing the paddle to 5 regions.
        double firstX = paddle.getUpperLeft().getX();
        double width = paddle.getWidth() / NUM_REGIONS;
        double region1 = firstX + width;
        double region2 = region1 + width;
        double region3 = region2 + width;
        double region4 = region3 + width;
        double region5 = region4 + width;

        // where did the ball hit the paddle, change movement as required.
        if (x >= firstX && x <= region1) {
            return Velocity.fromAngleAndSpeed(ROUND
                    - SECOND_ANGLE, this.speed);
        } else if (x >= region1 && x <= region2) {
            return Velocity.fromAngleAndSpeed(ROUND
                    - FIRST_ANGLE, this.speed);
        } else if (x >= region2 && x <= region3) {
            return new Velocity(dx, -dy);
        } else if (x >= region3 && x <= region4) {
            return Velocity.fromAngleAndSpeed(FIRST_ANGLE, this.speed);
        }
        if (x >= region4 && x <= region5) {
            return Velocity.fromAngleAndSpeed(SECOND_ANGLE, this.speed);
        }
        return new Velocity(dx, dy);
    }

    /**
     * Add to game.
     *
     * @param g the g
     */
// Add this paddle to the game.
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Sets keyboard.
     *
     * @param keyboard the keyboard
     */
    public void setKeyboard(biuoop.KeyboardSensor keyboard) {
        this.keyboard = keyboard;
    }
}