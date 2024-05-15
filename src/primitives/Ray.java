package primitives;

/**
 * Class Ray is the basic class representing a ray of Euclidean geometry in Cartesian 3-Dimensional coordinate system.
 * All the points from the head of the ray in the line in the direction of the vector.
 * @author Rachel and Tehila
 */
public class Ray {
    /** a point that represents the head of the ray */
    private final Point head;
    /** a normalized vector that represents the direction of the ray */
    private final Vector direction;

    /**
     * a constructor to initialize a ray with a point and a vector.
     * normalizes the vector if it isn't already a normal.
     * @param head the point that is the head of the ray
     * @param direction a vector representing the direction of the ray. (doesn't need to be normalized)
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        if (direction.length() == 1)
            this.direction = direction;
        else
            this.direction = direction.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
}
