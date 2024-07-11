package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    /** Delta value for accuracy when comparing the numbers of type 'double' in assertEquals */
    private final double DELTA = 0.000001;

    /** Test method for {@link Triangle#getNormal(Point)} */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that normal calculation of triangle is proper
        Triangle triangle = new Triangle(
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(-1, 0, 0)
        );

        // generate the test result
        Vector result = triangle.getNormal(new Point(-1, 0, 0));
        // Test that we got the desired result
        assertEquals(new Vector(0,0,1),
                result,
                "ERROR: it is not the desired normal");
    }

    /**
     * Test method for {@link Intersectable#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Point p100 = new Point(1,0,0);
        Point pm100 = new Point(-1,0,0);
        Point p020 = new Point(0,2,0);
        Point p111 = new Point(1,1,1);
        Point p011 = new Point(0,1,1);
        Point p010 = new Point(0,1,0);
        Point p031 = new Point(0,3,1);
        Point p101 = new Point(1,0,1);
        Point p0d511 = new Point(0.5,1,1);
        Point p1d501 = new Point(1.5,0,1);
        Vector v00m2 = new Vector(0,0,-2);
        Triangle triangle = new Triangle(p100, pm100, p020);
        List<Point> exp;
        List<Point> result;
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray is inside the triangle (1 point)
        result = triangle.findIntersections(new Ray(p011, v00m2));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(p010, result.getFirst(), "Ray inside the triangle");

        // TC02: Ray is after a vertex of the triangle (0 points)
        assertNull(triangle.findIntersections(new Ray(p031, v00m2)),
                "Ray after a vertex of the triangle");

        // TC03: Ray is after a side of the triangle (0 points)
        assertNull(triangle.findIntersections(new Ray(p111, v00m2)),
                "Ray after a side of the triangle");

        // =============== Boundary Values Tests ==================
        // TC11: Ray is on a vertex of the triangle (0 points)
        assertNull(triangle.findIntersections(new Ray(p101, v00m2)),
                "Ray on a vertex of the triangle");

        // TC12: Ray is on a side of the triangle (0 points)
        assertNull(triangle.findIntersections(new Ray(p0d511, v00m2)),
                "Ray on a side of the triangle");

        // TC13: Ray is on a side's continue (0 points)
        assertNull(triangle.findIntersections(new Ray(p1d501, v00m2)),
                "Ray on a side's continue");

    }

    /**
     * Test method for {@link Triangle#findGeoIntersections(Ray, double)}
     */
    @Test
    void testFindGeoIntersections() {
        Triangle triangle = new Triangle(
                new Point(1,-1,-1),
                new Point(1,1,-1),
                new Point(1,0,1)
        );
        Ray ray = new Ray(new Point(-1,0,0), new Vector(1,0,0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: intersection is further away than the max distance
        assertNull(triangle.findGeoIntersections(ray,1),
                "ERROR: doesn't return null if the point is further away than the max distance");

        // TC02: intersection is closer than the max distance
        assertEquals(1,
                triangle.findGeoIntersections(ray,3).size(),
                "ERROR: doesn't return the an intersection point if the point is closer than the max distance");

        // =============== Boundary Values Tests ==================
        // TC03: intersection is at the distance of the max distance
        assertEquals(1,
                triangle.findGeoIntersections(ray,2).size(),
                "ERROR: doesn't return the an intersection point if the distance is the max distance");
    }
}