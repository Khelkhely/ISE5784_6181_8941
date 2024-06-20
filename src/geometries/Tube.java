package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.sqrt;

/**
 * Class Tube is the basic class representing a tube in Cartesian
 * 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public class Tube extends RadialGeometry {
    /** ray of the main axis of the tube */
    protected final Ray axis;

    /**
     * Constructor to initialize the tube with its radius and axis
     * @param radius value for the radius
     * @param axis value for the axis
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point p1) {
        Point head = axis.getHead();
        Vector direction = axis.getDirection();
        double dotProduct = direction.dotProduct(p1.subtract(head));
        if (dotProduct != 0)
            head = axis.getPoint(dotProduct);
        return p1.subtract(head).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector n = ray.getDirection();
        Point o = ray.getHead();
        Vector a = axis.getDirection();
        Vector b = axis.getHead().subtract(o);
        if (a.equals(n)) {
            return null;
        }
        Vector cross = n.crossProduct(a);
        double discriminanta = cross.lengthSquared() * radius * radius;
        double dot = b.dotProduct(cross);
        discriminanta -= a.lengthSquared() * dot * dot;
        if (discriminanta < 0) {
            return null;
        }
        double B = cross.dotProduct(b.crossProduct(a));

        if (discriminanta == 0) {
            double d = B / cross.lengthSquared();
            if (d <= 0) {
                return null;
            }
            return List.of(ray.getPoint(d));
        }
        double d1 = (B + sqrt(discriminanta)) / cross.lengthSquared();
        double d2 = (B - sqrt(discriminanta)) / cross.lengthSquared();
        if (d1 < 0) {
            if (d2 < 0) {
                return null;
            }
            return List.of(ray.getPoint(d2));
        }
        return List.of(ray.getPoint(d2), ray.getPoint(d1));
    }

}
