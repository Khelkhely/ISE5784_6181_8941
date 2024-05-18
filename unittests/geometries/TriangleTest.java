package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    /** Test method for {@link Triangle#getNormal(Point)} */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that normal calculation of triangle is proper
        Point[] pts =
                { new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(-1, 0, 0)};
        Triangle triangle = new Triangle(pts);

        // generate the test result
        Vector result = triangle.getNormal(new Point(-1, 0, 0));
        // Test that we got the desired result
        assertTrue(new Vector(0,0,1) == result
                        || new Vector(0,0,-1) == result,
                "ERROR: it is not the desired normal");
    }
}