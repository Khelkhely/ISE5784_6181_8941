package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
