package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {
    /**
     * Test method for {@link Ray#Ray(Point,Vector}
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: check that vector is normalized
        Ray ray = new Ray(new Point(1,2,3), new Vector(1,8,5));
        assertEquals(1,
                ray.getDirection().length(),
                "ERROR: ray's vector isn't a normal");
    }

    /**
     * Test method for {@link Ray#getPoint(double)}
     */
    @Test
    void testGetPoint() {
        Point head = new Point(1,2,3);
        Ray ray = new Ray(head, new Vector(1,0,0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: positive distance
        assertEquals(new Point(5,2,3),
                ray.getPoint(4),
                "ERROR: doesn't work for positive distance");

        // TC02: negative distance
        assertEquals(new Point(-3,2,3),
                ray.getPoint(-4),
                "ERROR: doesn't work for negative distance");

        // =============== Boundary Values Tests ==================
        // TC11: distance is 0
        assertEquals(head,
                ray.getPoint(0),
                "ERROR: doesn't work if distance is zero");
    }
}