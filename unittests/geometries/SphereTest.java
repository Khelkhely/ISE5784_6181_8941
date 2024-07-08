package geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

class SphereTest {
    /**
     * Test method for {@link Sphere#Sphere(double, Point)}
     */
    @Test
    void testConstructor(){
        // =============== Boundary Values Tests ==================
        // TC01: Test that throws exception if Radius is zero
        assertThrows(IllegalArgumentException.class,
                ()-> new Sphere(0, new Point(1,1,1)));
    }

    /**
     * Test method for {@link Sphere#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that normal calculation of sphere is proper
        Sphere sphere = new Sphere(2, new Point(1,1,1));
        assertEquals(new Vector(1,0,0),
                sphere.getNormal(new Point(3,1,1)),
                "ERROR: Sphere get normal does not work correctly");
    }

    /**
     * Test method for {@link Intersectable#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        final Point p100 = new Point(1, 0, 0);
        final Point p200 = new Point(2, 0, 0);
        final Point p300 = new Point(3, 0, 0);
        final Point p400 = new Point(4, 0, 0);
        final Point p101 = new Point(1, 0, 1);
        final Point p201 = new Point(2, 0, 1);
        final Point p301 = new Point(3, 0, 1);
        final Point p2d500 = new Point(2.5, 0, 0);
        final Point p0d500 = new Point(0.5, 0, 0);
        final Point p00m1 = new Point(0, 0, -1);
        final Point pm100 = new Point(-1, 0, 0);
        final Point p1d500 = new Point(1.5, 0, 0);
        final Point p1d500d86 = new Point(1.5, 0, 0.8660254037844);
        final Vector v100 = new Vector(1, 0, 0);
        final Vector v200 = new Vector(2, 0, 0);
        final Vector v102 = new Vector(1, 0, 2);
        final Vector v400 = new Vector(4, 0, 0);
        final Vector v202 = new Vector(2, 0, 2);
        final Vector v303 = new Vector(3, 0, 3);
        final Vector v110 = new Vector(1, 1, 0);
        final Vector v001 = new Vector(0, 0, 1);
        Sphere sphere = new Sphere(1d, p200);
        List<Point> exp;
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p0d500, v110)),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        exp = List.of(p100, p201);
        result = sphere.findIntersections(new Ray(p00m1, v303));
        assertEquals(2,
                result.size(),
                "Wrong number of points");
        assertEquals(exp,
                result,
                "Ray starts before and crosses the sphere");

        // TC03: Ray starts inside the sphere (1 point)
        exp = List.of(p201);
        result = sphere.findIntersections(new Ray(p1d500, v102));
        assertEquals(1,
                result.size(),
                "Wrong number of points");
        assertEquals(exp,
                result,
                "Ray starts inside the sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p400, v100)),
                "Ray's line after the sphere");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        exp = List.of(p201);
        result = sphere.findIntersections(new Ray(p100, v202));
        assertEquals(1,
                result.size(),
                "Wrong number of points");
        assertEquals(exp,
                result,
                "Ray starts at sphere and goes inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p100, v100.scale(-1))),
                "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        exp = List.of(p100, p300);
        result = sphere.findIntersections(new Ray(pm100, v400));
        assertEquals(2,
                result.size(),
                "Wrong number of points");
        assertEquals(exp,
                result,
                "Ray starts before the sphere");

        // TC14: Ray starts at sphere and goes inside (1 point)
        exp = List.of(p300);
        result = sphere.findIntersections(new Ray(p100, v400));
        assertEquals(1,
                result.size(),
                "Wrong number of points");
        assertEquals(exp,
                result,
                "Ray starts at sphere and goes inside");

        // TC15: Ray starts inside (1 point)
        exp = List.of(p300);
        result = sphere.findIntersections(new Ray(p2d500, v100));
        assertEquals(1,
                result.size(),
                "Wrong number of points");
        assertEquals(exp,
                result,
                "Ray starts inside");

        // TC16: Ray starts at the center (1 point)
        exp = List.of(p300);
        result = sphere.findIntersections(new Ray(p200, v200));
        assertEquals(1,
                result.size(),
                "Wrong number of points");
        assertEquals(exp,
                result,
                "Ray starts at the center");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p300, v100)),
                "Ray starts at sphere and goes outside");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p400, v100)),
                "Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(p101, v400)),
                "Ray starts before the tangent point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(p201, v100)),
                "Ray starts at the tangent point");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(p301, v100)),
                "Ray starts after the tangent point");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line (0 points)
        assertNull(sphere.findIntersections(new Ray(p0d500, v001)),
                "Ray's line is outside and orthogonal");

        // TC23: Ray's line is inside the sphere, ray is orthogonal to sphere's center line (1 point)
        exp = List.of(p1d500d86);
        result = sphere.findIntersections(new Ray(p1d500, v001));
        assertEquals(1,
                result.size(),
                "Wrong number of points");
        assertEquals(exp,
                result,
                "Ray's line is inside and orthogonal");
    }
}