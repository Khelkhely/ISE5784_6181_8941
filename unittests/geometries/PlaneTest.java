package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    /** Delta value for accuracy when comparing the numbers of type 'double' in assertEquals */
    private final double DELTA = 0.000001;

    /** Test method for {@link Plane#Plane(Point, Point, Point)}. */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test construct a correct plane
        assertDoesNotThrow(() -> new Plane(new Point(1,0,0),
                new Point(0,1,0),
                new Point(-1,0,0)),
                "ERROR: failed constructing a correct plane");

        // =============== Boundary Values Tests ==================
        // TC11: Test that throws proper exception when first and second points are not the same
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,0,0),
                        new Point(1,0,0),
                        new Point(-1,0,0)),
                "ERROR: same first and second points does not throw a fitting exception");
        // TC12: Test that throws proper exception when points are not on the same line
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,0,0),
                        new Point(0,0,0),
                        new Point(-1,0,0)),
                "ERROR: three points on the same line does not throw a fitting exception");

        /*
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,2,3),
                        new Point(2,4,6),
                        new Point(3,6,9)),
                "ERROR: three points on the same line does not throw a fitting exception");
        */
    }

    /** Test method for {@link Plane#getNormal()} */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane plane = new Plane(new Point(1,0,0),
                new Point(0,1,0),
                new Point(-1,0,0));
        // generate the test result
        Vector result = plane.getNormal();
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(),
                "ERROR: getNormal threw an exception");
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA,
                "ERROR: Plane's normal is not a unit vector");
        Vector v1 = new Vector(1,0,0).subtract(new Point(0,1,0));
        Vector v2 = new Vector(-1,0,0).subtract(new Point(0,1,0));
        // ensure the result is orthogonal to the plane
        assertEquals(0, result.dotProduct(v1), DELTA,
                   "ERROR: Polygon's normal is not orthogonal to the plane");
        assertEquals(0, result.dotProduct(v2), DELTA,
                "ERROR: Polygon's normal is not orthogonal to the plane");
        // Test that we got the desired result
        assertTrue(new Vector(0,0,1) == result
                        || new Vector(0,0,-1) == result,
                "ERROR: it is not the desired normal");

    }

    /** Test method for {@link Plane#getNormal(Point)} */
    @Test
    void testTestGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane plane = new Plane(new Point(1,0,0),
                new Point(0,1,0),
                new Point(-1,0,0));
        // generate the test result
        Vector result = plane.getNormal(new Point(1,0,0));
        // Test that we got the desired result
        assertTrue(new Vector(0,0,1) == result
                        || new Vector(0,0,-1) == result,
                "ERROR: it is not the desired normal");
    }
}