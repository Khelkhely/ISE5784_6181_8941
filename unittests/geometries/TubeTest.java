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
}