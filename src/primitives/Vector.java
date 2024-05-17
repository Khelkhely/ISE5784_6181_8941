package primitives;

/**
 * Class Vector is the basic class representing a vector of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public class Vector extends Point {

    /**
     * Constructor to initialize a vector with the three coordinates of its direction
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     * @throws IllegalArgumentException if the vector is zero vector (x = y = z = 0)
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector cannot be zero vector.");
    }

    /**
     * Constructor to initialize a vector with the three coordinates of its direction as a double3
     * @param xyz the three coordinates of the vector
     * @throws IllegalArgumentException if the vector is zero vector (xyz = (0, 0, 0))
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector cannot be zero vector.");
    }

    /**
     * calculates the sum of the two vectors
     * @param v1 the vector that is added to the current vector
     * @return a vector that is the sum of the current vector and parameter vector
     */
    public Vector add(Vector v1) {
        return new Vector(xyz.add(v1.xyz));
    }

    /**
     * multiplies each of the coordinates of the vector by the number given
     * @param num the number the vector is multiplied by
     * @return a new vector that is the multiplication of the vector and the number
     */
    public Vector scale(double num) {
        return new Vector(xyz.scale(num));
    }

    /**
     * calculates the dot-product of the two vectors
     * @param v1 the second vector
     * @return the sum of each of the three coordinates of the vector multiplied by the
     * corresponding coordinate in v1
     */
    public double dotProduct(Vector v1) {
        return xyz.d1 * v1.xyz.d1 + xyz.d2 * v1.xyz.d2 + xyz.d3 * v1.xyz.d3;
    }

    /**
     * calculates the squared length of the vector - the sum of each of the three
     * coordinates multiplied by themselves
     * @return the squared length of the vector
     */
    public double lengthSquared() {
        return dotProduct(this);
    }

    /**
     * calculates the squared length of the vector - the square root of the sum of each of the three
     * coordinates multiplied by themselves
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * creates a new vector that is the same direction as the original vector but has a length of one
     * @return a normalized vector of the original
     */
    public Vector normalize() {
        return scale(1 / length());
    }

    /**
     * calculates a vector that is orthogonal to both the current and the parameter vector
     * (the vector points upward in the perspective in which the current vector is to the right of the second vector)
     * @param v1 the parameter vector
     * @return a vector that is orthogonal to both vectors
     */
    public Vector crossProduct(Vector v1) {
        return new Vector(xyz.d2 * v1.xyz.d3 - xyz.d3 * v1.xyz.d2,
                          xyz.d3 * v1.xyz.d1 - xyz.d1 * v1.xyz.d3,
                          xyz.d1 * v1.xyz.d2 - xyz.d2 * v1.xyz.d1);
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
                && super.equals(other);
    }
}
