package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class Sphere is the basic class representing a sphere in Cartesian
 * 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public class Sphere extends RadialGeometry {
    /** the center of the sphere */
    private final Point center;

    /**
     * Constructor to initialize the sphere with its radius and center
     * @param radius value for the radius
     * @param center value for the center of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point p1) {
        return p1.subtract(center).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        if(p0 == center) {
            Point p = p0.add(v.scale(radius));//???? vector?
            return List.of(p);
        }

        Vector u = center.subtract(p0);
        double tm = alignZero(v.dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
        if (d >= radius) {
            return null;
        }
        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0) {
            return null;
        } else if (t2 <= 0) {
            Point p1 = p0.add(v.scale(t1));
            return List.of(p1);
        } else if (t1 <= 0) {
            Point p2 = p0.add(v.scale(t2));
            return List.of(p2);
        } else {
            Point p1 = p0.add(v.scale(t1));
            Point p2 = p0.add(v.scale(t2));
            return List.of(p1, p2);
        }
    }
}
