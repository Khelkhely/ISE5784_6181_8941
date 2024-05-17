package geometries;
import primitives.Vector;
import primitives.Point;

/**
 * Class Geometry is the basic class representing a geometric body or shape in Cartesian
 * 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public interface Geometry {
    /**
     * calculates a normal (vertical) vector to the geometry from a point on it
     * @param p1 a point on the geometry the normal is from
     * @return a normal (vertical) vector to the geometry
     */
    public abstract Vector getNormal(Point p1);
}