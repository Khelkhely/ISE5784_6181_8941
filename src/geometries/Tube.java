package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
        if (!isZero(dotProduct))
            head = axis.getPoint(dotProduct);
        return p1.subtract(head).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector n = ray.getDirection();
        Point o = ray.getHead();
        Vector a = axis.getDirection();
        double dot, A, B;
        if (a.isParallel(n)) { //if the ray is parallel to the tube, there are no intersections
            return null;
        }
        Vector cross = n.crossProduct(a);
        A = alignZero(cross.lengthSquared());
        if (o.equals(axis.getHead())) {
            dot = 0;
            B = 0;
        } else {
            Vector b = axis.getHead().subtract(o);
            dot = alignZero(b.dotProduct(cross));
            if (a.isParallel(b)) {
                B = 0;
            } else {
                B = alignZero(cross.dotProduct(b.crossProduct(a)));
            }
        }
        double discriminant = alignZero(A * radius * radius - a.lengthSquared() * dot * dot);
        if (discriminant <= 0) {
            //either there are no intersections, or there is only one which means the ray is tangent to the tube
            return null;
        }
        double sqrt = sqrt(discriminant);
        double d1 = (B + sqrt) / A;
        double d2 = (B - sqrt) / A;
        if (alignZero(d2) <= 0 ) { //if the ray starts on the tube
            if (alignZero(d1) <= 0) {
                return null;
            }
            return List.of(ray.getPoint(d1));
        }
        return List.of(ray.getPoint(d2), ray.getPoint(d1));
    }

}
