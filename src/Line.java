
import java.util.List;

/**
 * The type Line.
 */
public class Line {
    private Point start;
    private Point end;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final double DIVIDE = 2.0;


    /**
     * Instantiates a new Line.
     *
     * @param start the start
     * @param end   the end
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Instantiates a new Line.
     *
     * @param x1 the x 1
     * @param y1 the y 1
     * @param x2 the x 2
     * @param y2 the y 2
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Length double.
     *
     * @return the double
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Middle point.
     *
     * @return the point
     */
    public Point middle() {
        double x = (start.getX() + end.getX()) / DIVIDE;
        double y = (start.getY() + end.getY()) / DIVIDE;
        return new Point(x, y);
    }

    /**
     * Start point.
     *
     * @return the point
     */
    public Point start() {
        return start;
    }

    /**
     * End point.
     *
     * @return the point
     */
    public Point end() {
        return end;
    }

    /**
     * Is intersecting boolean.
     *
     * @param other the other
     * @return the boolean
     */
    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null;
    }

    /**
     * Intersection with point.
     *
     * @param other the other
     * @return the point
     */
    public Point intersectionWith(Line other) {
        double x1 = start.getX();
        double y1 = start.getY();
        double x2 = end.getX();
        double y2 = end.getY();
        double x3 = other.start.getX();
        double y3 = other.start.getY();
        double x4 = other.end.getX();
        double y4 = other.end.getY();

        double denominator = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
        // The lines are parallel or coincident
        if (denominator == ZERO) {
            return null;
        }

        double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3))
                / denominator;
        double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3))
                / denominator;
        // The intersection point is outside the line segments.
        if (ua < ZERO || ua > ONE || ub < ZERO || ub > ONE) {
            return null;
        }

        double x = x1 + ua * (x2 - x1);
        double y = y1 + ua * (y2 - y1);
        return new Point(x, y);
    }

    /**
     * Equals boolean.
     *
     * @param other the other
     * @return the boolean - return true is the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return start.equals(other.start) && end.equals(other.end);
    }

    /**
     * Closest intersection to start of line point.
     *
     * @param rect the rect
     * @return the point
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // the list of the points
        List<Point> intersectionPoints = rect.intersectionPoints(this);
        // no intersection
        if (intersectionPoints.isEmpty()) {
            return null;
        }

        Point closestPoint = intersectionPoints.get(ZERO);
        double closestDistance = this.start().distance(closestPoint);
        // find the closest point
        for (int i = ONE; i < intersectionPoints.size(); i++) {
            Point currentPoint = intersectionPoints.get(i);
            double currentDistance = this.start().distance(currentPoint);
            if (currentDistance < closestDistance) {
                closestPoint = currentPoint;
                closestDistance = currentDistance;
            }
        }

        return closestPoint;
    }
}
