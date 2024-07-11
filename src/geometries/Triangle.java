package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Class Triangle is the basic class representing a triangle in Cartesian
 * 3-Dimensional coordinate system.
 *
 * @author Rachel and Tehila
 */
public class Triangle extends Polygon {

    /**
     * Constructor to initialize a triangle based on the polygon constructor
     *
     * @param p1 first vertices of the triangle
     * @param p2 second vertices of the triangle
     * @param p3 third vertices of the triangle
     * @throws IllegalArgumentException if trying to create a triangle with more or less then 3 vertices
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = plane.findGeoIntersections(ray, maxDistance);
        if (intersections == null) {
            return null;
        }
        Point head = ray.getHead();
        Vector direction = ray.getDirection();

        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);

        Vector v1 = p1.subtract(head);
        Vector v2 = p2.subtract(head);
        Vector v3 = p3.subtract(head);

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        double dotProduct1 = direction.dotProduct(n1);
        double dotProduct2 = direction.dotProduct(n2);
        double dotProduct3 = direction.dotProduct(n3);
        if(isZero(dotProduct1) || isZero(dotProduct2) || isZero(dotProduct3)) {
            return null;
        }
        if ((dotProduct1 > 0 && dotProduct2 > 0 && dotProduct3 > 0)
                || (dotProduct1 < 0 && dotProduct2 < 0 && dotProduct3 < 0)) {
            return List.of(new GeoPoint(this, intersections.getFirst().point));
        } else {
            return null;
        }


    }
}
