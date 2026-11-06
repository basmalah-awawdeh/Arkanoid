
/**
 * class Point that contains the methods of the point.
 */
public class Point {
    private double x;
    private double y;

    /**
     * constructor - Instantiates a new Point.
     *
     * @param x the x
     * @param y the y
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;

    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Distance double.
     *
     * @param other the other
     * @return the double return the distance of this point to the other point
     */
    public double distance(Point other) {
        return Math.sqrt((this.getX() - other.getX()) * (this.getX()
                - other.getX()) + (this.getY() - other.getY()) * (this.getY()
                - other.getY()));
    }

    /**
     * Equals boolean.
     *
     * @param other the other
     * @return the boolean - return true is the points are equal, else false
     */
    public boolean equals(Point other) {
        if (this.getX() == other.getX() && this.getY() == other.getY()) {
            return true;
        }
        return false;
    }

    /**
     * Gets x.
     *
     * @return return the x value of this point
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets y.
     *
     * @return return the y value of this point
     */
    public double getY() {
        return this.y;
    }
}
