package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    /**
     * Test method for {@link Cylinder#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        Cylinder c = new Cylinder(2,
                new Ray(new Point(1,0,0), new Vector(0,1,0)),4);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test normal from point on the round surface of the cylinder
        assertEquals(new Vector(0,0,1), c.getNormal(new Point(1,1,2)),
                "ERROR: normal from point on the round surface is wrong");

        // TC02: Test normal from point on the first base of the cylinder
        assertEquals(new Vector(0,-1,0), c.getNormal(new Point(1,0,1)),
                "ERROR: normal from point on the first base is wrong");

        // TC03: Test normal from point on the second base of the cylinder
        assertEquals(new Vector(0,1,0), c.getNormal(new Point(1,4,1)),
                "ERROR: normal from point on the second base is wrong");


        // =============== Boundary Values Tests ==================
        // TC11: Test normal from the edge of the first base of the cylinder
        // (at the edges of the bases, will return a normal like a point on the base)
        assertEquals(new Vector(0,-1,0), c.getNormal(new Point(1,0,2)),
                "ERROR: normal from from the edge of the first base is wrong");

        // TC12: Test normal from the edge of the second base of the cylinder
        assertEquals(new Vector(0,1,0), c.getNormal(new Point(1,4,2)),
                "ERROR: normal from from the edge of the second base is wrong");

        // TC13: Test normal from the center of the first base of the cylinder
        assertEquals(new Vector(0,-1,0), c.getNormal(new Point(1,0,0)),
                "ERROR: normal from from the center of the first base is wrong");

        // TC14: Test normal from the center of the second base of the cylinder
        assertEquals(new Vector(0,-1,0), c.getNormal(new Point(1,4,0)),
                "ERROR: normal from from the center of the second base is wrong");
    }
}