package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;

/**
 * Class Plane is the basic class representing a plane in Cartesian
 * 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public class Plane extends Geometry {
    /** a point on the plane */
    private final Point q;
    /** a normal (vertical) vector to the plane */
    private final Vector normal;

    /**
     * Constructor to initialize a plane by calculating its normal by three points on the plane
     * @param p1 first point on the plane (the point the plane is defined by)
     * @param p2 second point on the plane
     * @param p3 third point on the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        normal = v1.crossProduct(v2).normalize();
        q = p1;
    }

    /**
     * Constructor to initialize a plane with point and normal vector
     * @param p a point on the plane
     * @param v a normal (vertical) vector to the plane
     */
    public Plane(Point p, Vector v){
        q = p;
        normal = v.normalize();
    }

    /**
     * calculates a normal (vertical) vector to the geometry
     * @return a normal (vertical) to the plane
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point p1) {
        return normal;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector direction = ray.getDirection();
        Point head = ray.getHead();
        double dotProduct = alignZero(direction.dotProduct(normal));
        if (dotProduct == 0) {
            return null;
        }
        if (q.equals(head)) {
            return null;
        }
        double t = alignZero(normal.dotProduct(q.subtract(head)) / dotProduct);
        if (alignZero(t - maxDistance) > 0 || t <= 0) {
            return null;
        } else {
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }
    }

    @Override
    public void calcBoundaryBox() {
        if (boundaryBox == null) {
            throw new MissingResourceException("default boundary box for plane not provided.",
                    "Plane",
                    "boundaryBox");
        }
        //reducing the default boundary box in the cases that the plane is orthogonal to one of the axes
        if (normal.equals(Vector.X)) {
            boundaryBox.setMinX(q.getX()).setMaxX(q.getX());
        } else {
            if (normal.equals(Vector.Y)) {
                boundaryBox.setMinY(q.getY()).setMaxY(q.getY());
            } else {
                if (normal.equals(Vector.Z)) {
                    boundaryBox.setMinZ(q.getZ()).setMaxZ(q.getZ());
                }
            }
        }
    }
}
