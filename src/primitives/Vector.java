package primitives;

import static primitives.Util.isZero;

/**
 * Class Vector is the basic class representing a vector of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public class Vector extends Point{

    /**
     * Constructor to initialize a vector with the three coordinates of its direction
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector cannot be zero vector");
    }

    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector cannot be zero vector");
    }

    public Vector add(Vector v1) {

    }

    public Vector scale(double num) {

    }

    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Vector other)
                && super.equals((Point) other);
    }
}
