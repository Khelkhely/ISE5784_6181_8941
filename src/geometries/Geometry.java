package geometries;
import primitives.Vector;
import primitives.Point;

/**
 * Class Geometry is the basic class representing a geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public interface Geometry {
    /**
     * calculates a normal (vertical) vector to the geometry
     * @param p1 a point on the geometry
     * @return a normal (vertical) vector to the geometry
     */
    public abstract Vector getNormal(Point p1);
}