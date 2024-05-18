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
        if (height < 0)
            throw new IllegalArgumentException("The height of the cylinder has to be positive number.");
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p1)
    {
        Vector direction = axis.getDirection();
        Point head = axis.getHead();
        // the point on 'axis' and on the second base of the cylinder:
        Point head2 = head.add(direction.scale(height));

        if (p1.subtract(head).dotProduct(direction) == 0) // if p1 is on the first base of the cylinder
                // if the vector between p1 and the head of 'axis' is vertical to the direction of 'axis'
            return direction.scale(-1);

        if (p1.subtract(head2).dotProduct(direction) == 0) // if p1 is on the second base of the cylinder
                // if the vector between p1 and head2 is vertical to the direction of 'axis'
            return direction;

        // if p1 is on the round surface of the cylinder
        return super.getNormal(p1);
    }
}
