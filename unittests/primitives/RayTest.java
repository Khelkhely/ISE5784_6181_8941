package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

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

    /**
     * Test method for {@link Ray#findClosestPoint(List)}
     */
    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(2,0,0), new Vector(-2,0,1));
        Point p001 = new Point(0,0,1);
        Point pm202 = new Point(-2,0,2);
        Point pm403 = new Point(-4,0,3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: point is in the middle of the list
        List<Point> list1 = List.of(pm202, p001, pm403);
        assertEquals(p001,
                ray.findClosestPoint(list1),
                "ERROR: doesn't work when point is in the middle of the list.");

        // =============== Boundary Values Tests ==================
        // TC11: the list is empty
        assertNull(ray.findClosestPoint(List.of()),
                "ERROR: doesn't return null when list is empty.");

        // TC12: the closest point is the first on the list
        List<Point> list2 = List.of(p001, pm403, pm202);
        assertEquals(p001,
                ray.findClosestPoint(list2),
                "ERROR: doesn't work when point is in the start of the list.");
        // TC13: the closest point is the last on the list
        List<Point> list3 = List.of(pm202, pm403, p001);
        assertEquals(p001,
                ray.findClosestPoint(list3),
                "ERROR: doesn't work when point is in the end of the list.");
    }
}