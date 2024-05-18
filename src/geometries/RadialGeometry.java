package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class RadialGeometry is the basic class representing a geometry that has a radius
 * in Cartesian 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public abstract class RadialGeometry implements Geometry {
    /** the radius of the radial geometry */
    protected final double radius;

    /**
     * Constructor to initialize the radial geometry with its radius
     * @param radius value for the radius
     */
    public RadialGeometry(double radius) {
        if (radius <= 0)
            throw new IllegalArgumentException("Radius has to be a positive number");
        this.radius = radius;
    }
}
