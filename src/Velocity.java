
/**
 * class Velocity that contains methods of velocity.
 */
public class Velocity {
    private static final int ROUND = 360;
    private static final int ONE = 1;
    /**
     * The Dx.
     */
    public double dx;
    /**
     * The Dy.
     */
    public double dy;


    /**
     * Constructor for Velocity.
     *
     * @param dx The change in position on the x axis.
     * @param dy The change in position on the y axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * From angle and speed velocity.
     *
     * @param angle the angle
     * @param speed the speed
     * @return the velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // converts angle from degrees to radians
        angle = Math.random() * ROUND;
        speed = Math.random() + ONE;

        // calculates dx and dy using trigonometry
        double dx = speed * Math.cos(Math.toRadians(angle));
        double dy = speed * Math.sin(Math.toRadians(angle));

        return new Velocity(dx, dy);
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Returns a velocity with the given angle and speed.
     *
     * @param angle the angle of the velocity in degrees
     * @param speed the speed of the velocity
     * @return a velocity with the given angle and speed
     */
    public static Velocity angleToVelocity(double angle, double speed) {
        double angleRadians = Math.toRadians(angle);
        double dx = speed * Math.cos(angleRadians);
        // negative because y-coordinates increase downwards
        double dy = -speed * Math.sin(angleRadians);
        return new Velocity(dx, dy);
    }

    /**
     * Returns the change in position on x.
     *
     * @return The change in position on x.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Returns the change in position on y.
     *
     * @return The change in position on y.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Takes a point with position (x,y) and returns a new point
     * with position (x+dx, y+dy).
     *
     * @param p The point to apply the velocity to.
     * @return A new point with the velocity applied.
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }
}
