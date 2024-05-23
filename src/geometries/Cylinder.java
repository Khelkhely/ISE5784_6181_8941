package geometries;

import primitives.Point;
import primitives.Ray;
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
        if (height <= 0)
            throw new IllegalArgumentException("The height of the cylinder has to be positive number.");
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p1) {
        Point center1 = axis.getHead();
        Vector direction = axis.getDirection();
        if (p1.equals(center1) || //the point is the center of the first base
                direction.dotProduct(p1.subtract(center1)) == 0) //the point is on the first base
            return direction.scale(-1);
        Point center2 = center1.add(direction.scale(height)); //center of second base
        if (p1.equals(center2) || //the point is the center of the second base
                direction.dotProduct(p1.subtract(center2)) == 0) //point is on the second base
            return direction;
        //point is on the round surface
        return super.getNormal(p1);
    }
}
