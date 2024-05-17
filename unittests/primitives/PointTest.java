package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Point class
 * @author Rachel and Tehila
 */
class PointTest {

    /** Delta value for accuracy when comparing the numbers of type 'double' in assertEquals */
    private final double DELTA = 0.000001;

    /** * Test method for {@link Point#subtract(Point)}. */
    @Test
    void testSubtract() {
        Point  p1 = new Point(1, 2, 3);
        Point  p2 = new Point(2, 4, 6);
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that subtracting two points is proper
        assertEquals(v1, p2.subtract(p1),
                "ERROR: (point2 - point1) does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC11: Test that throws proper exception when subtracting a point from itself
        assertThrows(IllegalArgumentException.class, ()->p1.subtract(p1),
                "ERROR: (point - itself) does not throw a fitting exception");
    }

    /**  Test method for {@link Point#add(Vector)}. */
    @Test
    void testAdd() {
        Point  p1 = new Point(1, 2, 3);
        Point  p2 = new Point(2, 4, 6);
        Vector v1 = new Vector(1, 2, 3);
        Vector v1Opposite = new Vector(-1, -2, -3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that adding a vector to a point is proper
        assertEquals(p2, p1.add(v1),
                "ERROR: (point + vector) = other point does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC11: Test that adding the opposite vector to a point equals zero
        assertEquals(Point.ZERO, p1.add(v1Opposite),
                "ERROR: (point + vector) = center of coordinates does not work correctly");
    }

    /** Test method for {@link Point#distanceSquared(Point)}. */
    @Test
    void testDistanceSquared() {
        Point  p1 = new Point(1, 2, 3);
        Point  p3 = new Point(2, 4, 5);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that squared distance between points is proper
        assertEquals(9, p1.distanceSquared(p3) , DELTA,
                "ERROR: squared distance between points is wrong");
        // TC02: Test that squared distance between points is proper in the opposite order
        assertEquals(9, p3.distanceSquared(p1) , DELTA,
                "ERROR: squared distance between points is wrong");
        // =============== Boundary Values Tests ==================
        // TC11: Test that squared distance between a point and itself equals zero
        assertEquals(0, p1.distanceSquared(p1), DELTA,
                "ERROR: point squared distance to itself is not zero");
    }

    /** Test method for {@link Point#distance(Point)}. */
    @Test
    void testDistance() {
        Point  p1 = new Point(1, 2, 3);
        Point  p3 = new Point(2, 4, 5);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that distance between points is proper
        assertEquals(3, p1.distance(p3) , DELTA,
                "ERROR: distance between points is wrong");
        // TC02: Test that distance between points is proper in the opposite order
        assertEquals(3, p3.distance(p1) , DELTA,
                "ERROR: distance between points is wrong");
        // =============== Boundary Values Tests ==================
        // TC11: Test that distance between a point and itself equals zero
        assertEquals(0, p1.distance(p1), DELTA,
                "ERROR: point distance to itself is not zero");
    }
}