package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        // TC01: Test that the normal we got is proper
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
                "ERROR: plane's normal is not a unit vector");
        Vector v1 = new Vector(1,0,0).subtract(new Point(0,1,0));
        Vector v2 = new Vector(-1,0,0).subtract(new Point(0,1,0));
        // ensure the result is orthogonal to the plane
        assertEquals(0, result.dotProduct(v1), DELTA,
                   "ERROR: plane's normal is not orthogonal to the plane");
        assertEquals(0, result.dotProduct(v2), DELTA,
                "ERROR: plane's normal is not orthogonal to the plane");
    }

    /** Test method for {@link Plane#getNormal(Point)} */
    @Test
    void testTestGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the normal we got is proper
        Plane plane = new Plane(new Point(1,0,0),
                new Point(0,1,0),
                new Point(-1,0,0));
        // generate the test result
        Vector result = plane.getNormal(new Point(0, 0, 0));
        // Test that we got the desired result
        assertEquals(new Vector(0,0,1), result,
                "ERROR: it is not the desired normal");

    }

    /**
     * Test method for {@link Plane#findIntersections(Ray)}
     */
    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point(1,0,0), new Point(0,1,0), new Point(0,0,1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane
        List<Point> list = plane.findIntersections(new Ray(new Point(-5,0,0), new Vector(3,1,0)));
        assertEquals(1, list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(-0.5,1.5,0), list.getFirst(),
                "ERROR: doesn't return the right intersection point");

        // TC02: Ray doesn't intersect the plane
        assertNull(plane.findIntersections(new Ray(new Point(0,2,0), new Vector(0,1,0))),
                "ERROR: doesn't return null if there are no intersections");


        // =============== Boundary Values Tests ==================
        // TC11: Ray is parallel to the plane
        assertNull(plane.findIntersections(new Ray(new Point(0,2,0), new Vector(1,-1,0))),
                "ERROR: doesn't return null if ray is parallel to the plane");
        // TC12: Ray is on the plane
        assertNull(plane.findIntersections(new Ray(new Point(-1,2,0), new Vector(0,1,1))),
                "ERROR: doesn't return null if ray is on the plane");
        // TC13: Ray is orthogonal to the plane and starts before it
        assertEquals(new Point(0.666667, -0.333333, 0.666667),
                plane.findIntersections(new Ray(new Point(0,-1,0), new Vector(1,1,1))).getFirst(),
                "ERROR: doesn't work if the ray is orthogonal to the plane and has an intersection");
        // TC14: Ray is orthogonal to the plane and starts after it
        assertNull(plane.findIntersections(new Ray(new Point(1,0,1), new Vector(1,1,1))).getFirst(),
                "ERROR: doesn't work if the ray is orthogonal to the plane and doesn't have an intersection");
        // TC15: Ray is orthogonal to the plane and starts on it
        assertNull(plane.findIntersections(
                new Ray(new Point(0.666667, -0.333333, 0.666667), new Vector(1,1,1))).getFirst(),
                "ERROR: doesn't work if the ray is orthogonal to the plane and starts at the intersection");
        // TC11: Ray begins on the plane
        assertNull(plane.findIntersections(
                new Ray (new Point (1,2,-2), new Vector(-1,-2,8))).getFirst(),
                "ERROR: doesn't return null if the ray starts on the plane");
        // TC11: Ray begins on the point that the plane is defined by
        assertNull(plane.findIntersections(
                        new Ray (new Point (1,0,0), new Vector(-1,-2,8))).getFirst(),
                "ERROR: doesn't return null if the ray starts on the point the plane is defined by");
    }
}