package geometries;

import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * Class Triangle is the basic class representing a triangle in Cartesian
 * 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public class Triangle extends Polygon {

    /**
     * Constructor to initialize a triangle based on the polygon constructor
     * @param vertices list of the three vertices of the triangle
     * @throws IllegalArgumentException if trying to create a triangle with more or less then 3 vertices
     */
    public Triangle(Point... vertices) {
        super(vertices);
        if(vertices.length != 3)
            throw new IllegalArgumentException("A Triangle has to have exactly 3 vertices");
    }

    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
        //return plane.getNormal(point);
    }
}
