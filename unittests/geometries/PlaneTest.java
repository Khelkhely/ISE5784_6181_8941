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
    }

    /** Test method for {@link Plane#Plane(Point, Vector)}. */
    @Test
    public void testConstructor() {
    }

    /** Test method for {@link Plane#getNormal()} */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01:
        Plane p = new Plane(new Point(1,0,0),
                            new Point(0,1,0),
                            new Point(-1,0,0));
        assertEquals(new Vector(0,0,1), p.getNormal(),
                "ERROR: it is not the right normal");
        // generate the test result
        Vector result = p.getNormal();
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA,
                "Plane's normal is not a unit vector");

        //???|v
        // ensure there are no exceptions
        assertDoesNotThrow(() -> p.getNormal(), "");

        // ensure the result is orthogonal to the plane (to at list two of the vectors of the plane)
        for (int i = 0; i < 3; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                    "Polygon's normal is not orthogonal to one of the edges");

    }

    /** Test method for {@link Plane#getNormal(Point)} */
    @Test
    void testTestGetNormal() {
        fail("Not yet implemented");
    }
}