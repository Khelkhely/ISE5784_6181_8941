package geometries;

import primitives.Double3;
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
        calcBoundaryBox();
    }

    @Override
    public Vector getNormal(Point p1) {
        return p1.subtract(center).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point head = ray.getHead();
        Vector direction = ray.getDirection();

        if(head.equals(center)) {
            GeoPoint p = new GeoPoint(this, ray.getPoint(radius));
            return List.of(p);
        }
        Vector u = center.subtract(head);
        double tm = alignZero(direction.dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
        if (d >= radius) {
            return null;
        }
        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if ((alignZero(t1 - maxDistance) > 0 || t1 <= 0) &&
                (alignZero(t2 - maxDistance) > 0 || t2 <= 0)) {
            return null;
        } else if (alignZero(t2 - maxDistance) > 0 || t2 <= 0) {
            GeoPoint p1 = new GeoPoint(this, ray.getPoint(t1));
            return List.of(p1);
        } else if (alignZero(t1 - maxDistance) > 0 || t1 <= 0) {
            GeoPoint p2 = new GeoPoint(this, ray.getPoint(t2));
            return List.of(p2);
        } else {
            GeoPoint p1 = new GeoPoint(this, ray.getPoint(t1));
            GeoPoint p2 = new GeoPoint(this, ray.getPoint(t2));
            return List.of(p1, p2);
        }
    }

    @Override
    public void calcBoundaryBox() {
        boundaryBoxFlag = true;
        if (boundaryBox == null) {
            boundaryBox = new BoundaryBox(
                    center.getX() - radius,
                    center.getY() - radius,
                    center.getZ() - radius,
                    center.getX() + radius,
                    center.getY() + radius,
                    center.getZ() + radius
            );
        }

    }
}
