package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        return null;

    }
}
