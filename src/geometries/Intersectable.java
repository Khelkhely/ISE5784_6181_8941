package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Class Intersectable is the basic class representing a geometric that is intersectable.
 */
public interface Intersectable {
    /**
     * A method that receives a ray and return a list of intersection points between the ray and the current geometry.
     * @param ray a ray that is thrown to the geometry.
     * @return a list of all the intersection points between 'ray' and the geometry.
     */
    List<Point> findIntersections(Ray ray);
}
