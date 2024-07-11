package geometries;

import primitives.Point;
import primitives.Ray;
import org.junit.jupiter.api.Test;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class GeometriesTest {

    /**
     * Test method for {@link Intersectable#findIntersections(Ray)}
     */
    @Test
    void testFindIntersections() {
        Ray ray1 = new Ray(new Point(-5,0,0), new Vector(10,0,0)); //some
        Ray ray2 = new Ray(new Point(-3,0,0), new Vector(3,0,3)); //none
        Ray ray3 = new Ray(new Point(-7,0,0), new Vector(7,3,0)); //one
        Ray ray4 = new Ray(new Point(-5,-4,0), new Vector(9,5,0.5)); //all
        Sphere sphere = new Sphere(1, new Point(2,0,0));
        Plane plane = new Plane(
                new Point(-3,0,0),
                new Point(0,0,3),
                new Point(0,-3,0)
        );
        Triangle triangle = new Triangle(
                new Point(1,2,3),
                new Point(0,4,0),
                new Point(4,0,0)
        );
        Geometries geometries = new Geometries(sphere, plane, triangle);
        Geometries empty = new Geometries();
        // ============ Equivalence Partitions Tests ==============
        // TC01: some of the geometries intersect the ray
        assertEquals(3,
                geometries.findIntersections(ray1).size(),
                "ERROR: doesn't work when some of the geometries intersect the ray");


        // =============== Boundary Values Tests ==================
        // TC11: geometries is an empty list
        assertNull(empty.findIntersections(ray1),
                "ERROR: doesn't work when some of the geometries intersect the ray");

        // TC12: none of the geometries intersect the ray
        assertNull(geometries.findIntersections(ray2),
                "ERROR: doesn't work when none of the geometries intersect the ray");

        // TC12: only one of the geometries intersects the ray
        assertEquals(1,
                geometries.findIntersections(ray3).size(),
                "ERROR: doesn't work when only one of the geometries intersect the ray");

        // TC12: all the geometries intersect the ray
        assertEquals(4,
                geometries.findIntersections(ray4).size(),
                "ERROR: doesn't work when all the geometries intersect the ray");
    }

}