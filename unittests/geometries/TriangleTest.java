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
        Triangle triangle = new Triangle(new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(-1, 0, 0));

        // generate the test result
        Vector result = triangle.getNormal(new Point(-1, 0, 0));
        // Test that we got the desired result
        assertEquals(new Vector(0,0,1), result,
                "ERROR: it is not the desired normal");
    }
}