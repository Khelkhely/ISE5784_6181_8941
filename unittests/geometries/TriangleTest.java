package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    /** Delta value for accuracy when comparing the numbers of type 'double' in assertEquals */
    private final double DELTA = 0.000001;

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
        assertTrue(new Vector(0,0,1).equals(result)
                        || new Vector(0,0,-1).equals(result),
                "ERROR: it is not the desired normal");
    }
}