
import java.util.ArrayList;
import java.util.List;

/**
 * The type Rectangle.
 */
public class Rectangle {
    private final Point upperLeft;
    private final double width;
    private final double height;

    /**
     * Instantiates a new Rectangle.
     *
     * @param upperLeft the upper left
     * @param width     the width
     * @param height    the height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Intersection points list.
     *
     * @param line the line
     * @return the list
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> points = new ArrayList<>();

        // Get the four sides of the rectangle
        Line top = new Line(upperLeft, new Point(upperLeft.getX() + width,
                upperLeft.getY()));
        Line right = new Line(new Point(upperLeft.getX() + width,
                upperLeft.getY()),
                new Point(upperLeft.getX() + width, upperLeft.getY()
                        + height));
        Line bottom = new Line(new Point(upperLeft.getX(), upperLeft.getY()
                + height),
                new Point(upperLeft.getX() + width, upperLeft.getY()
                        + height));
        Line left = new Line(upperLeft, new Point(upperLeft.getX(),
                upperLeft.getY() + height));

        // Check for intersection with each side of the four sides the rectangle
        if (line.isIntersecting(top)) {
            points.add(line.intersectionWith(top));
        }
        if (line.isIntersecting(right)) {
            points.add(line.intersectionWith(right));
        }
        if (line.isIntersecting(bottom)) {
            points.add(line.intersectionWith(bottom));
        }
        if (line.isIntersecting(left)) {
            points.add(line.intersectionWith(left));
        }

        return points;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return upperLeft.getX();
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return upperLeft.getY();
    }

    /**
     * Gets upper left.
     *
     * @return the upper left
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
}
