package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Vector class
 * @author Rachel and Tehila
 */
class VectorTest {

    /** Delta value for accuracy when comparing the numbers of type 'double' in assertEquals */
    private final double DELTA = 0.000001;

    /** Test method for zero vector */
    @Test
    void testZeroVector() {
        // =============== Boundary Values Tests ==================
        // TC01: Test that throws proper exception when creating a zero vector
        assertThrows(IllegalArgumentException.class, ()->new Vector(0, 0, 0),
                "ERROR: zero vector does not throw a fitting exception");
        // TC02: Test that throws proper exception when creating a zero vector
        assertThrows(IllegalArgumentException.class, ()->new Vector(Double3.ZERO),
                "ERROR: zero vector does not throw a fitting exception");
    }

    /** Test method for {@link Vector#add(Vector)} */
    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v1Opposite = new Vector(-1, -2, -3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that adding two vectors is proper
        assertEquals(v1Opposite, v1.add(v2),
                "ERROR: Vector + Vector does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC11: Test that throws proper exception when adding a vector to its opposite vector
        assertThrows(IllegalArgumentException.class, ()->v1.add(v1Opposite),
                "ERROR: Vector + -itself does not throw a fitting exception");
    }

    /** Test method for {@link Vector#subtract(Point)} */
    @Test
    void testSubtract() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that subtract two vectors is proper
        assertEquals(new Vector(3, 6, 9), v1.subtract(v2),
                "ERROR: Vector - Vector does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC11: Test that throws proper exception when subtracting a vector from itself
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
                "ERROR: Vector + -itself does not throw a fitting exception");
    }

    /** Test method for {@link Vector#scale(double)} */
    @Test
    void testScale() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that scaling a vector is proper
        assertEquals(new Vector(3,6,9), v1.scale(3),
                "ERROR: Vector scaling does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC11: Test that throws proper exception when scaling a vector with zero
        assertThrows(IllegalArgumentException.class, ()->v1.scale(0),
                "ERROR: Vector * 0 does not throw a fitting exception");
    }

    /** Test method for {@link Vector#dotProduct(Vector)} */
    @Test
    void testDotProduct() {
        Vector v1         = new Vector(1, 2, 3);
        Vector v1Opposite = new Vector(-1, -2, -3);
        Vector v2         = new Vector(-2, -4, -6);
        Vector v3         = new Vector(0, 3, -2);
        Vector v4         = new Vector(1, 2, 2);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that dot product of two vectors is proper
        assertEquals(-28, v1.dotProduct(v2), DELTA,
                "ERROR: dotProduct() wrong value");
        // =============== Boundary Values Tests ==================
        // TC11: Test that dot product of orthogonal vectors is zero
        assertEquals(0, v1.dotProduct(v3), DELTA,
                "ERROR: dotProduct() for orthogonal vectors is not zero");
    }
    /** Test method for {@link Vector#lengthSquared()} */
    @Test
    void testLengthSquared() {
        Vector v4 = new Vector(1, 2, 2);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that squared length is proper
        assertEquals(9, v4.lengthSquared(), DELTA,
                "ERROR: lengthSquared() wrong value");
    }

    /** Test method for {@link Vector#length()} */
    @Test
    void testLength() {
        Vector v4 = new Vector(1, 2, 2);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that length is proper
        assertEquals(3, v4.length(), DELTA,
                "ERROR: length() wrong value");
    }

    /** Test method for {@link Vector#normalize()} */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();
        // TC01: Test that normalized vector's length is 1
        assertEquals(1, u.length(), DELTA,
                "ERROR: the normalized vector is not a unit vector");
        // TC02: Test that the normalized vector is parallel to the original one
        assertThrows(IllegalArgumentException.class, ()->v.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");
        // TC03: Test that the normalized vector has the same direction of the original one
        assertTrue(v.dotProduct(u) > 0,
                "ERROR: the normalized vector is opposite to the original one");
    }

    /** Test method for {@link Vector#crossProduct(Vector)} */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        // ============ Equivalence Partitions Tests ==============
        Vector v = v1.crossProduct(v3);
        // TC01: Test that length of cross-product is proper
        assertEquals(v1.length() * v3.length(), v.length(), DELTA,
                "crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertEquals(0, v.dotProduct(v1), DELTA,
                "crossProduct() result is not orthogonal to 1st operand");
        assertEquals(0, v.dotProduct(v3), DELTA,
                "crossProduct() result is not orthogonal to 2nd operand");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of parallel vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                "crossProduct() for parallel vectors does not throw an exception");
    }
}