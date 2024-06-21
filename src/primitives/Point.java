package primitives;

/**
 * Class Point is the basic class representing a point of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public class Point {
    /** a point that represents the center of the 3-Dimensional coordinate system */
    public static final Point ZERO = new Point(Double3.ZERO);

    /** a double3 representing the x, y, and z coordinates */
    protected final Double3 xyz;

    /**
     * Constructor to initialize a point with its three coordinates
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructor to initialize a point with its three coordinates from a double3
     * @param xyz the three coordinates of the point
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * calculates the vector between the parameter point to the current point
     * @param p1 the point the vector starts from
     * @return a new vector between p1 and the current point
     */
    public Vector subtract(Point p1) {
        return new Vector(xyz.subtract(p1.xyz));
    }

    /**
     * calculates the new point received after adding the vector to the current point
     * @param v1 the vector that is added to the point
     * @return a new point
     */
    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz));
    }

    /**
     * a get method for the three coordinates of the point
     * @return the three coordinates of the point
     */
    public Double3 getXyz() {
        return xyz;
    }

    /**
     * calculates the squared distance between the current and parameter point
     * @param p1 the other point
     * @return the squared distance between the current point and parameter point
     */
    public double distanceSquared(Point p1) {
        return (xyz.d1 - p1.xyz.d1) * (xyz.d1 - p1.xyz.d1) +
                (xyz.d2 - p1.xyz.d2) * (xyz.d2 - p1.xyz.d2) +
                (xyz.d3 - p1.xyz.d3) * (xyz.d3 - p1.xyz.d3);
    }

    /**
     * calculates the squared distance between the current and parameter point
     * @param p1 the other point
     * @return the distance between the current point and parameter point
     */
    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }
}
