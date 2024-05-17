package geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
        Sphere s1 = new Sphere(2, new Point(1,1,1));
        assertEquals(new Vector(1,0,0), s1.getNormal(new Point(3,1,1)),
                "ERROR: Sphere get normal does not work correctly");
    }
}