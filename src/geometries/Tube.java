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

        if (a.equals(n) || a.equals(n.scale(-1))) {
            return null;
        }

        Vector cross = n.crossProduct(a);
        double A = cross.lengthSquared();
        double discriminanta = A * radius * radius;
        double dot = b.dotProduct(cross);
        discriminanta -= a.lengthSquared() * dot * dot;
        if (alignZero(discriminanta) < 0) {
            return null;
        }
        //לבדוק אם הנקודה o נמצאת על הaxis
        double mB = cross.dotProduct(b.crossProduct(a));

        if (isZero(discriminanta)) {
            double d = mB / A;
            if (d <= 0) {
                return null;
            }
            return List.of(ray.getPoint(d));
        }
        double d1 = (mB + sqrt(discriminanta)) / A;
        double d2 = (mB - sqrt(discriminanta)) / A;
        if (alignZero(d2) < 0) {
            if (alignZero(d1) < 0) {
                return null;
            }
            return List.of(ray.getPoint(d1));
        }
        if(o.equals(ray.getPoint(d2)))
            return List.of(ray.getPoint(d1));
        //לבדוק אם הנקודה d2 נמצאת ממש על הtube
        return List.of(ray.getPoint(d2), ray.getPoint(d1));
    }

}
