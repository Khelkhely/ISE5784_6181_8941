package geometries;

import primitives.Point;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Class Plane is the basic class representing a plane in Cartesian
 * 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public class Plane implements Geometry {
    /** a point on the plane */
    private final Point q;
    /** a normal (vertical) vector to the plane */
    private final Vector normal;

    /**
     * Constructor to initialize a plane by calculating its normal by three points on the plane
     * @param p1 first point on the plane
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
}
