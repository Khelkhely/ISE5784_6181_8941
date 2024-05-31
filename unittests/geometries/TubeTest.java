package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
        // ============ Equivalence Partitions Tests ==============
        // TC01: there are two intersection points
        // TC02: the ray starts inside the tube - there is one intersection point
        // TC03: the ray doesn't intersect the tube at all
        // TC04: the line the ray is on intersects the tube, but the ray faces the other direction
        // TC05: the ray is inside the tube and doesn't intersect it
        // =============== Boundary Values Tests ==================
        // there are two intersection points:
        // TC11: the ray is orthogonal to the axis of the tube
        // TC12: the ray intersects the axis
        // TC13: the ray intersects the axis and is orthogonal to it
        // TC14: the ray goes through the head of the axis
        // TC14: the ray goes through the head of the axis and is orthogonal to it
        // TC15: the vector between the ray's head and the axis' head is orthogonal to the axis

        // the ray starts inside the tube - there is one intersection point:
        // TC20: the ray starts on the edge of the tube
        // TC11: the ray is orthogonal to the axis of the tube
        // TC12: the ray intersects the axis
        // TC12: the ray starts on the axis of the tube
        // TC12: the ray starts on the line that the axis of the tube is on but the axis is in the other direction
        // TC13: the ray intersects the axis and is orthogonal to it
        // TC14: the ray goes through the head of the axis
        // TC14: the ray starts at the head of the axis
        // TC14: the line the ray is on goes through the head of the axis, but the ray is on the other direction
        // TC14: the ray goes through the head of the axis and is orthogonal to it
        // TC15: the vector between the ray's head and the axis' head is orthogonal to the axis


        // the ray doesn't intersect the tube at all:
        // TC: the ray is orthogonal to the tube
        // TC: the ray is parallel to the tube
        // TC: the vector between the ray's head and the axis' head is orthogonal to the axis

        // the line the ray is on intersects the tube, but the ray faces the other direction:
        // TC: the ray begins on the edge of the tube
        // TC: the vector between the ray's head and the axis' head is orthogonal to the axis

        // the ray is inside the tube and doesn't intersect it:
        // TC: the ray is on the edge of the tube
        // TC: the ray is on the same line and direction as the axis, and starts before the head of the axis
        // TC: the ray is on the same line and direction as the axis, and starts after the head of the axis
        // TC: the ray starts at the head of the axis and has the same direction
        // TC: the ray is on the same line as the axis in the opposite direction, and starts before the head of the axis
        // TC: the ray is on the same line as the axis in the opposite direction and starts after the head of the axis
        // TC: the ray starts at the head of the axis but has the opposite direction
        // TC: the vector between the ray's head and the axis' head is orthogonal to the axis

        // the ray is tangent to the tube:
        // TC: the ray is orthogonal to the tube
        // TC: the line the ray is on is tangent to the tube but the ray starts after the point of contact
        // TC: the ray is tangent to the tube and starts at the point of contact
    }
}