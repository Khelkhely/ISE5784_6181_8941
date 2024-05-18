package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
        Point p0 = axis.getHead();
        double dotProduct = axis.getDirection().dotProduct(p1.subtract(p0));
        if (dotProduct != 0)
            p0 = p0.add(axis.getDirection().scale(dotProduct));
        return p1.subtract(p0).normalize();
    }
}
