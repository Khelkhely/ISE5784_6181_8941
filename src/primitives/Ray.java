package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

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
    /** a Delta value for moving rays forwards for constructing secondary rays */
    private static final double DELTA = 0.1;

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
     * a constructor to create a secondary ray in order to ensure that it does not intersect the geometry is started on.
     * @param head the point that was supposed to be the head of the ray.
     * @param direction a vector representing the direction of the ray. (doesn't need to be normalized)
     * @param normal the normal of the geometry in the point of intersection.
     */
    public Ray(Point head, Vector direction, Vector normal) {
        this.head = head.add(normal.scale(normal.dotProduct(direction) > 0 ? DELTA : -DELTA));
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

    /**
     * calculates the point on the ray that is a distance of t from the head of the ray in its direction
     * @param t the distance from the head of the ray to the desired point
     * @return a point on the ray that is a distance of t from the head of the ray
     */
    public Point getPoint(double t) {
        if(isZero(t)) {
            return head;
        } else {
            return head.add(direction.scale(t));
        }
    }

    /**
     * receives a list of points and returns the closest point to the head of the ray
     * @param points the list of points
     * @return the closest point to the head of the ray on the list
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * receives a list of GeoPoints and returns the closest GeoPoint to the head of the ray.
     * @param geoPoints the list of GeoPoints
     * @return the closest point to the head of the ray on the list
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        if (geoPoints == null || geoPoints.isEmpty()) {
            return null;
        }

        GeoPoint closest = geoPoints.getFirst(); // the closest point to the head
        double closestDistance = closest.point.distance(head); // the distance between the head and the closest point
        double distance; //a temporary variable of the distance between the point that is checked and the head

        for(GeoPoint geoPoint : geoPoints) { // go through all of the points and find the closest one
            distance = geoPoint.point.distance(head);
            if(distance < closestDistance) {
                closest = geoPoint;
                closestDistance = distance;
            }
        }

        return closest;
    }
}
