package primitives;

import static primitives.Util.isZero;

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
        this.direction = direction.normalize();
    }

    /**
     * getter function for ray's direction vector
     * @return the vector representing the ray's direction
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * getter function for ray's head
     * @return the point representing the ray's head
     */
    public Point getHead() {
        return head;
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

    public Point getPoint(double t) {
        if(isZero(t)) {
            return head;
        } else {
            return head.add(direction.scale(t));
        }
    }
}
