package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    /**
     * Test method for {@link Tube#Tube(double, Ray)}
     */
    @Test
    void testConstructor() {
        // =============== Boundary Values Tests ==================
        // TC01: Test that throws exception if radius is zero
        assertThrows(IllegalArgumentException.class,
                () -> new Tube(0,
                        new Ray(new Point(1, 1, 1), new Vector(1, 0, 0))));
    }

    /**
     * Test method for {@link Tube#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        Tube t = new Tube(1, new Ray(new Point(1,0,0), new Vector(1,0,0)));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that get normal from tube works properly
        assertEquals(new Vector(0,1,0), t.getNormal(new Point(3,1,0)),
                "ERROR: tube get normal does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC10: Test that get normal form tube works properly when the vector from the point to the
        // head of the ray of the tube is orthogonal to the ray
        assertEquals(new Vector(0,1,0), t.getNormal(new Point(1,1,0)),
                "ERROR: normal does not work correctly when the point is parallel to the head of the ray");
    }

    /**
     * Test method for {@link Tube#findIntersections(Ray)}
     */
    @Test
    void testFindIntersections() {
        Tube tube = new Tube(2, new Ray(new Point(0,-2,0.5), new Vector(0,4,1)));

        // ============ Equivalence Partitions Tests ==============
        // TC01: there are two intersection points
        List<Point> list = tube.findIntersections(new Ray(new Point(0,-4,3), new Vector(1,5,-2)));
        assertEquals(2,
                list.size(),
                "ERROR: doesn't return two intersection point");
        assertEquals(new Point(0.3, -2.52, 2.41),
                list.getFirst(),
                "ERROR: doesn't return the right first intersection point");
        assertEquals(new Point(1.38, 2.91, 0.24),
                list.getLast(),
                "ERROR: doesn't return the right second intersection point");
        // TC02: the ray starts inside the tube - there is one intersection point
        list = tube.findIntersections(new Ray(new Point(1,1,1), new Vector(1,5,-2)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(1.38, 2.91, 0.24),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC03: the ray doesn't intersect the tube at all
        list = tube.findIntersections(new Ray(new Point(0,-4,3), new Vector(10,4,-3)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC04: the line the ray is on intersects the tube, but the ray faces the other direction
        list = tube.findIntersections(new Ray(new Point(2,6,-1), new Vector(1,5,-2)));
        assertNull(list,
                "ERROR: return intersection point");

        // =============== Boundary Values Tests ==================
        // there are two intersection points: 3
        // TC05: the ray is orthogonal to the axis of the tube
        list = tube.findIntersections(new Ray(new Point(1,1,-3), new Vector(0,-1,4)));
        assertEquals(2,
                list.size(),
                "ERROR: doesn't return two intersection point");
        assertEquals(new Point(1,0.42,-0.68),
                list.getFirst(),
                "ERROR: doesn't return the right first intersection point");
        assertEquals(new Point(1, -0.42, 2.68),
                list.getLast(),
                "ERROR: doesn't return the right second intersection point");
        // TC06: the ray intersects the axis
        list = tube.findIntersections(new Ray(new Point(0,-4,3), new Vector(0,6,-3)));
        assertEquals(2,
                list.size(),
                "ERROR: doesn't return two intersection point");
        assertEquals(new Point(0,-2.75, 2.37),
                list.getFirst(),
                "ERROR: doesn't return the right first intersection point");
        assertEquals(new Point(0, 2.75, -0.37),
                list.getLast(),
                "ERROR: doesn't return the right second intersection point");
        // TC07: the ray intersects the axis and is orthogonal to it
        list = tube.findIntersections(new Ray(new Point(0,0,-2), new Vector(0,-1,2)));
        assertEquals(2,
                list.size(),
                "ERROR: doesn't return two intersection point");
        assertEquals(new Point(0,-0.22,-1.12),
                list.getFirst(),
                "ERROR: doesn't return the right first intersection point");
        assertEquals(new Point(0,-1.19,2.76),
                list.getLast(),
                "ERROR: doesn't return the right second intersection point");
        // TC08: the ray goes through the head of the axis
        list = tube.findIntersections(new Ray(new Point(0,-4,3), new Vector(0,4,-5)));
        assertEquals(2,
                list.size(),
                "ERROR: doesn't return two intersection point");
        assertEquals(new Point(0,-3.37,2.22),
                list.getFirst(),
                "ERROR: doesn't return the right first intersection point");
        assertEquals(new Point(0,-0.62,-1.22),
                list.getLast(),
                "ERROR: doesn't return the right second intersection point");
        // TC09: the ray goes through the head of the axis and is orthogonal to it
        list = tube.findIntersections(new Ray(new Point(0,-3,4.5), new Vector(0,1,-4)));
        assertEquals(2,
                list.size(),
                "ERROR: doesn't return two intersection point");
        assertEquals(new Point(0,-2.48,2.44),
                list.getFirst(),
                "ERROR: doesn't return the right first intersection point");
        assertEquals(new Point(0,-1.51,-1.44),
                list.getLast(),
                "ERROR: doesn't return the right second intersection point");

        // the ray starts inside the tube - there is one intersection point: 8
        // TC10: the ray is orthogonal to the axis of the tube
        list = tube.findIntersections(new Ray(new Point(1,1,1), new Vector(0,0,4)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(0.3,0.3,3.11),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC11: the ray starts on the edge of the tube
        list = tube.findIntersections(new Ray(new Point(0,4.25,0), new Vector(1,-1,1)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(1.96,2.29,1.96),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC12: the ray starts on the edge of the tube and is orthogonal to the axis
        list = tube.findIntersections(new Ray(new Point(0,4.25,0), new Vector(0,3.25,4)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(0,3.28,3.88),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC13: the ray intersects the axis
        list = tube.findIntersections(new Ray(new Point(1,1,1), new Vector(-1,-1,0)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(-1.94, -1.94, 1),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC14: the ray intersects the line that the axis of the tube is on but on the other direction
        list = tube.findIntersections(new Ray(new Point(-1,-1,1), new Vector(-1,-1,0)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(-1.94, -1.94, 1),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC15: the ray goes through the head of the axis
        list = tube.findIntersections(new Ray(new Point(1,-2,1), new Vector(-1,0,-0.5)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(-1.8,-1.99,-0.4),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC16: the ray starts on the axis of the tube
        list = tube.findIntersections(new Ray(new Point(0,0,1), new Vector(0,0,1)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(0,0,3.06),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC17: the ray starts on the line that the axis of the tube is on but on the other direction
        list = tube.findIntersections(new Ray(new Point(0,0,2), new Vector(0,0,1)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(0,0,3.06),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");

        // the ray doesn't intersect the tube at all: 3
        // TC18: the ray is inside the tube and doesn't intersect it
        list = tube.findIntersections(new Ray(new Point(0,-4,1.5), new Vector(0,4,1)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC19: the ray is orthogonal to the tube
        list = tube.findIntersections(new Ray(new Point(0,-4,3), new Vector(-1,0,0)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC20: the ray is parallel to the tube
        list = tube.findIntersections(new Ray(new Point(0,-4,3), new Vector(0,4,1)));
        assertNull(list,
                "ERROR: return intersection point");

        // the line the ray is on intersects the tube, but the ray faces the other direction: 2
        // TC21: the ray begins on the edge of the tube
        list = tube.findIntersections(new Ray(new Point(2,-4,0), new Vector(1,1,1)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC22: the ray is orthogonal to the axis of the tube
        list = tube.findIntersections(new Ray(new Point(0,-4,3), new Vector(0,-1,4)));
        assertNull(list,
                "ERROR: return intersection point");

        // the ray is inside the tube and doesn't intersect it: 5
        // TC23: the ray is on the edge of the tube
        list = tube.findIntersections(new Ray(new Point(2,-4,0), new Vector(0,4,1)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC24: the ray is on the same line and direction as the axis, and starts before the head of the axis
        list = tube.findIntersections(new Ray(new Point(0,-4,0), new Vector(0,4,1)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC25: the ray is on the same line and direction as the axis, and starts after the head of the axis
        list = tube.findIntersections(new Ray(new Point(0,4,2), new Vector(0,4,1)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC26: the ray is on the same line as the axis in the opposite direction, and starts before the head of the axis
        list = tube.findIntersections(new Ray(new Point(0,4,2), new Vector(0,-4,-1)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC27: the ray is on the same line as the axis in the opposite direction and starts after the head of the axis
        list = tube.findIntersections(new Ray(new Point(0,-4,0), new Vector(0,-4,-1)));
        assertNull(list,
                "ERROR: return intersection point");

        // the ray is tangent to the tube:
        // TC28: the ray is tangent to the tube
        list = tube.findIntersections(new Ray(new Point(2,-4,-1), new Vector(0,0,1)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC29: the ray is orthogonal to the tube
        list = tube.findIntersections(new Ray(new Point(1,0,3.06), new Vector(-1,0,0)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC30: the line the ray is on is tangent to the tube but the ray starts after the point of contact
        list = tube.findIntersections(new Ray(new Point(2,-4,3), new Vector(0,0,1)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC31: the ray is tangent to the tube and starts at the point of contact
        list = tube.findIntersections(new Ray(new Point(2,-4,0), new Vector(0,0,1)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC32: the ray starts on the head of the axis
        list = tube.findIntersections(new Ray(new Point(0,-2,0.5), new Vector(1,1,1)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(1.26,-0.38,2.12),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC33: the ray is the same as the axis
        list = tube.findIntersections(tube.axis);
        assertNull(list,
                "ERROR: return intersection point");
        // TC34: the ray start at the head of the axis but is in the opposite direction
        list = tube.findIntersections(new Ray(tube.axis.getHead(),tube.axis.getDirection().scale(-1)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC35: the ray is orthogonal to the axis
        list = tube.findIntersections(new Ray(tube.axis.getHead(), new Vector(1,0,0)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(2,-2,0.5),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");

        // the vector between the ray's head and the axis' head is orthogonal to the axis:
        // TC36: the ray starts on the edge of the tube
        list = tube.findIntersections(new Ray(new Point(2,-2,0.5),new Vector(1,1,1)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC37: the ray is orthogonal to the axis and goes through its head
        list = tube.findIntersections(new Ray(new Point(1,-2,0.5),new Vector(-1,0,0)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(-2,-2,0.5),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC38: the ray is orthogonal to the axis and doesn't go through its head
        list = tube.findIntersections(new Ray(new Point(1,-2,0.5),new Vector(1,0,0)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(2,-2,0.5),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC39: the ray is parallel to the axis
        list = tube.findIntersections(new Ray(new Point(1,-2,0.5),new Vector(0,4,1)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC40: there are 2 intersection points
        list = tube.findIntersections(new Ray(new Point(0,-3,4.5), new Vector(-1,3,-4.5)));
        assertEquals(2,
                list.size(),
                "ERROR: doesn't return two intersection point");
        assertEquals(new Point(-0.43,-1.72,2.58),
                list.getFirst(),
                "ERROR: doesn't return the right first intersection point");
        assertEquals(new Point(-1.13,0.4,-0.6),
                list.getLast(),
                "ERROR: doesn't return the right second intersection point");
        // TC41: there is one intersection point
        list = tube.findIntersections(new Ray(new Point(1,-2,0.5),new Vector(1,1,1)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(1.89, -1.11, 1.39),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC42: there is one intersection point and the ray starts on the edge of the tube
        list = tube.findIntersections(new Ray(new Point(2,-2,0.5),new Vector(-4, 2,-0.5)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(-1.78,-0.11,0.03),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
    }
}