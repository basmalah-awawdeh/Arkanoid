
import java.util.ArrayList;

import java.util.List;

/**
 * The type Game environment.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * Instantiates a new Game environment.
     */
    public GameEnvironment() {
        collidables = new ArrayList<>();
    }

    /**
     * Add collidable.
     *
     * @param c the c
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Gets closest collision.
     *
     * @param trajectory the trajectory
     * @return the closest collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestCollision = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Collidable c : collidables) {
            Rectangle rect = c.getCollisionRectangle();
            Point intersectionPoint =
                    trajectory.closestIntersectionToStartOfLine(rect);
            // No intersection.
            if (intersectionPoint != null) {
                double distance =
                        trajectory.start().distance(intersectionPoint);
                // Find the closest.
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCollision = new CollisionInfo(intersectionPoint, c);
                }
            }
        }
        return closestCollision;
    }

    /**
     * Remove collidable.
     *
     * @param c the c
     */
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }
}
