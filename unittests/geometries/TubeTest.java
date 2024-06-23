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
        assertEquals(new Point(0.2957255409482756,-2.5213722952586224,2.408548918103449),
                list.getFirst(),
                "ERROR: doesn't return the right first intersection point");
        assertEquals(new Point(1.3816938138904347,2.908469069452173,0.23661237221913067),
                list.getLast(),
                "ERROR: doesn't return the right second intersection point");

        // TC02: the ray starts inside the tube - there is one intersection point
        list = tube.findIntersections(new Ray(new Point(1,1,1), new Vector(1,5,-2)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point 1");
        assertEquals(new Point(1.3816938138904342,2.9084690694521713,0.23661237221913145),
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

        // there are two intersection points: 5
        // TC05: the ray is orthogonal to the axis of the tube
        list = tube.findIntersections(new Ray(new Point(1,1,-3), new Vector(0,-1,4)));
        assertEquals(2,
                list.size(),
                "ERROR: doesn't return two intersection point");
        assertEquals(new Point(1,0.42008402520840293,-0.6803361008336117),
                list.getFirst(),
                "ERROR: doesn't return the right first intersection point");
        assertEquals(new Point(1, -0.42008402520840304,2.680336100833612),
                list.getLast(),
                "ERROR: doesn't return the right second intersection point");
        // TC06: the ray intersects the axis
        list = tube.findIntersections(new Ray(new Point(0,-4,3), new Vector(0,6,-3)));
        assertEquals(2,
                list.size(),
                "ERROR: doesn't return two intersection point");
        assertEquals(new Point(0,-2.7487370837451066,2.3743685418725535),
                list.getFirst(),
                "ERROR: doesn't return the right first intersection point");
        assertEquals(new Point(0, 2.7487370837451066, -0.3743685418725535),
                list.getLast(),
                "ERROR: doesn't return the right second intersection point");
        // TC07: the ray intersects the axis and is orthogonal to it
        list = tube.findIntersections(new Ray(new Point(0,0,-2), new Vector(0,-1,2)));
        assertEquals(2,
                list.size(),
                "ERROR: doesn't return two intersection point");
        assertEquals(new Point(0,-0.4170876387516312,-1.1658247224967377),
                list.getFirst(),
                "ERROR: doesn't return the right first intersection point");
        assertEquals(new Point(0,-2.2495790279150363,2.4991580558300726),
                list.getLast(),
                "ERROR: doesn't return the right second intersection point");
        // TC08: the ray goes through the head of the axis
        list = tube.findIntersections(new Ray(new Point(0,-4,3), new Vector(0,4,-5)));
        assertEquals(2,
                list.size(),
                "ERROR: doesn't return two intersection point");
        assertEquals(new Point(0,-3.3743685418725535,2.2179606773406917),
                list.getFirst(),
                "ERROR: doesn't return the right first intersection point");
        assertEquals(new Point(0,-0.625631458127446,-1.2179606773406926),
                list.getLast(),
                "ERROR: doesn't return the right second intersection point");
        // TC09: the ray goes through the head of the axis and is orthogonal to it
        list = tube.findIntersections(new Ray(new Point(0,-3,4.5), new Vector(0,1,-4)));
        assertEquals(2,
                list.size(),
                "ERROR: doesn't return two intersection point");
        assertEquals(new Point(0,-2.485071250072666,2.4402850002906638),
                list.getFirst(),
                "ERROR: doesn't return the right first intersection point");
        assertEquals(new Point(0,-1.514928749927334,-1.4402850002906638),
                list.getLast(),
                "ERROR: doesn't return the right second intersection point");

        // the ray starts inside the tube - there is one intersection point: 8
        // TC10: the ray is orthogonal to the axis of the tube
        list = tube.findIntersections(new Ray(new Point(1,1,1), new Vector(0,0,4)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point 2");
        assertEquals(new Point(1,1,3.0353571071357126),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC11: the ray starts on the edge of the tube
        list = tube.findIntersections(new Ray(new Point(2,-4,0), new Vector(-1,1,1)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point 3");
        assertEquals(new Point(-0.6153846153846159,-1.3846153846153841,2.615384615384616),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point 3");
        // TC12: the ray starts on the edge of the tube and is orthogonal to the axis
        list = tube.findIntersections(new Ray(new Point(0.2957255409482756,-2.5213722952586224,2.408548918103449), new Vector(-1,0,0)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point 4");
        assertEquals(new Point(-0.29572554094827835,-2.5213722952586224,2.408548918103449),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point 4");
        // TC13: the ray intersects the axis
        list = tube.findIntersections(new Ray(new Point(1,1,1), new Vector(-1,-1,0)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point 5");
        assertEquals(new Point(-1.9436506316150997,-1.9436506316150997,1),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC14: the ray intersects the line that the axis of the tube is on but on the other direction
        list = tube.findIntersections(new Ray(new Point(-1,-1,1), new Vector(-1,-1,0)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point 6");
        assertEquals(new Point(-1.9436506316151,-1.9436506316151, 1),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC15: the ray goes through the head of the axis
        list = tube.findIntersections(new Ray(new Point(1,-2,1), new Vector(-1,0,-0.5)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point 7");
        assertEquals(new Point(-1.7994708216848743,-2.0,-0.39973541084243713),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC16: the ray starts on the axis of the tube
        list = tube.findIntersections(new Ray(new Point(0,0,1), new Vector(0,0,3)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point 8");
        assertEquals(new Point(0,0,3.0615528128088303),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC17: the ray starts on the line that the axis of the tube is on but on the other direction
        list = tube.findIntersections(new Ray(new Point(0,0,2), new Vector(0,0,1)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point 9");
        assertEquals(new Point(0,0,3.0615528128088303),
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

        // the ray is tangent to the tube: 8
        // TC28: the ray is tangent to the tube
        list = tube.findIntersections(new Ray(new Point(2,-4,-1), new Vector(0,0,1)));
        assertNull(list,
                "ERROR: return intersection point");
        // TC29: the ray is orthogonal to the tube
        list = tube.findIntersections(new Ray(new Point(1,0,3.0615528128088303), new Vector(-1,0,0)));
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
        assertEquals(new Point(1.6172150801252798,-0.3827849198747202,2.1172150801252796),
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

        // the vector between the ray's head and the axis' head is orthogonal to the axis: 7
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
        assertEquals(new Point(-0.4258518087921898,-1.7224445736234306,2.5836668604351454),
                list.getFirst(),
                "ERROR: doesn't return the right first intersection point");
        assertEquals(new Point(-1.1331001562733125,0.39930046881993686,-0.5989507032299057),
                list.getLast(),
                "ERROR: doesn't return the right second intersection point");
        // TC41: there is one intersection point
        list = tube.findIntersections(new Ray(new Point(1,-2,0.5),new Vector(1,1,1)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(1.8918100998007785,-1.1081899001992215,1.3918100998007785),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
        // TC42: there is one intersection point and the ray starts on the edge of the tube
        list = tube.findIntersections(new Ray(new Point(2,-2,0.5),new Vector(-4, 2,-0.5)));
        assertEquals(1,
                list.size(),
                "ERROR: doesn't return one intersection point");
        assertEquals(new Point(-1.7777777777777777,-0.11111111111111116,0.02777777777777779),
                list.getFirst(),
                "ERROR: doesn't return the right intersection point");
    }
}