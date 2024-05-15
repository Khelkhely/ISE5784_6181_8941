package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class Cylinder is the basic class representing a cylinder in Cartesian
 * 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public class Cylinder extends Tube {
    /** the height of the cylinder */
    private final double height;

    /**
     * Constructor to initialize the cylinder with its radius, axis and height
     *
     * @param radius value for the radius
     * @param axis   value for the axis
     * @param height value for the height
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p1) {
        return super.getNormal(p1); //null
    }
}
