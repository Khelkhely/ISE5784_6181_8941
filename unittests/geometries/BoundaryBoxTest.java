package geometries;

import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.AntiAliasingSuperSampler;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;

import static java.awt.Color.ORANGE;
import static java.lang.Math.floor;
import static org.junit.jupiter.api.Assertions.*;

class BoundaryBoxTest {

    /**
     * Test method for {@link BoundaryBox#doesIntersect(Ray)}
     */
    @Test
    void testDoesIntersect() {
        BoundaryBox boundaryBox = new BoundaryBox(-1,0,0,1,2,3);
        // ============ Equivalence Partitions Tests ==============
        //TC01: the ray intersects the boundary box
        Ray ray1 = new Ray(new Point(0,-1,0), new Vector(0,2,1));
        assertTrue(
                boundaryBox.doesIntersect(ray1),
                "ERROR: doesn't work if the ray intersects the boundary box"
        );
        //TC02: the ray doesn't intersect the boundary box
        Ray ray2 = new Ray(new Point(0,-1,0), new Vector(0,1,-1));
        assertFalse(
                boundaryBox.doesIntersect(ray2),
                "ERROR: doesn't work if the ray intersects the boundary box"
        );
        //TC03: the ray starts inside the boundary box
        Ray ray3 = new Ray(new Point(0,1,1), new Vector(0,2,1));
        assertTrue(
                boundaryBox.doesIntersect(ray3),
                "ERROR: doesn't work if the ray intersects the boundary box"
        );
        //TC04: the ray's line intersects the boundary box and starts after it
        Ray ray4 = new Ray(new Point(0,3,2), new Vector(0,2,1));
        assertFalse(
                boundaryBox.doesIntersect(ray4),
                "ERROR: doesn't work if the ray intersects the boundary box"
        );

        // =============== Boundary Values Tests ==================
        //TC11: ray starts on the edge of the boundary box and doesn't intersect it
        Ray ray5 = new Ray(new Point(0,0,1), new Vector(-1,-1,1));
        assertFalse(
                boundaryBox.doesIntersect(ray5),
                "ERROR: doesn't work if the ray intersects the boundary box"
        );
        //TC12: ray starts on the edge of the boundary box and intersects it
        Ray ray6 = new Ray(new Point(0,0,1), new Vector(1,1,1));
        assertTrue(
                boundaryBox.doesIntersect(ray6),
                "ERROR: doesn't work if the ray intersects the boundary box"
        );
        //TC13: ray intersects the boundary box on the edge of it
        Ray ray7 = new Ray(new Point(0,-1,0), new Vector(0,1,3));
        assertTrue(
                boundaryBox.doesIntersect(ray7),
                "ERROR: doesn't work if the ray intersects the boundary box"
        );
        //TC14: the ray is parallel to one of the axes and intersects the boundary box
        Ray ray8 = new Ray(new Point(0,1,-1), Vector.Z);
        assertTrue(
                boundaryBox.doesIntersect(ray8),
                "ERROR: doesn't work if the ray intersects the boundary box"
        );
        //TC15: the ray is parallel to one of the axes and doesn't intersect the boundary box
        Ray ray9 = new Ray(new Point(2,0,0), Vector.Z);
        assertFalse(
                boundaryBox.doesIntersect(ray9),
                "ERROR: doesn't work if the ray intersects the boundary box"
        );

        //TC16: the boundary box is flat and the ray intersects it
        BoundaryBox flat = new BoundaryBox(-1,0,0,1,2,0);
        Ray ray10 = new Ray(new Point(0,0,2), new Vector(0,1,-2));
        assertTrue(
                flat.doesIntersect(ray10),
                "ERROR: doesn't work if the ray intersects the boundary box"
        );
        //TC16: the boundary box is flat and the ray doesn't intersect it
        Ray ray11 = new Ray(new Point(2,0,0), new Vector(2,0,0));
        assertFalse(
                flat.doesIntersect(ray11),
                "ERROR: doesn't work if the ray intersects the boundary box"
        );
    }

    @Test
    public void MakePictureTest() {
        Scene scene = new Scene("memory balls");

        Material ballMaterial = new Material().setKs(0.5).setKd(0.5).setShininess(20);
        Material wallMaterial = new Material().setKs(0.5).setKd(0.5).setShininess(10).setKr(0.5);
        Material clearGlass = new Material().setKt(0.7).setShininess(30).setKs(0.7).setKd(0.3);
        Color lightColor = new Color(180,132,120);
        Color tube = new Color(0,0,10);
        Color gold = new Color(100, 75, 0);
        Color red = new Color(175, 0, 0);
        Color blue = new Color(0, 0, 175);
        Color green = new Color(0, 100, 75);
        Color purple = new Color(20, 0, 45);

        Point center1 = new Point(-8.2,0,2.5);
        Point center2 = new Point(-7,0,-2.5);
        Ray axis1 = new Ray(center1, Vector.X);
        Ray axis2 = new Ray(center2, Vector.X);
        Geometry tube1 = new Tube(2.2, axis1).setMaterial(clearGlass).setEmission(tube);
        tube1.boundaryBox = new BoundaryBox(-15,-2.2,0.3,13,2.2,4.7);
        Geometry tube2 = new Tube(2.2, axis2).setMaterial(clearGlass).setEmission(tube);
        tube2.boundaryBox = new BoundaryBox(-14,-2.2,-4.7,14,2.2,-0.3);
        Geometry floor = new Plane(new Point(0,0,-5.5), Vector.Z)
                .setMaterial(wallMaterial).setEmission(purple);
        floor.boundaryBox = new BoundaryBox(-15,-2.5,-5.5,14,16,-5.5);
        Geometry wall = new Plane(new Point(0,-2.5,0), Vector.Y)
                .setMaterial(ballMaterial).setEmission(purple);
        wall.boundaryBox = new BoundaryBox(-15,-2.5,5,14,-2.5,-5.5);
        scene.geometries.add(tube1, tube2, floor, wall);

        Point[] points = new Point[8];
        points[0] = new Point(-11,-2,0.1);
        points[1] = new Point(11,-2,0.1);
        points[2] = new Point(11,2,0.1);
        points[3] = new Point(-11,2,0.1);
        points[4] = new Point(-11,-2,-0.1);
        points[5] = new Point(11,-2,-0.1);
        points[6] = new Point(11,2,-0.1);
        points[7] = new Point(-11,2,-0.1);

        Geometry triangle1 = new Triangle(points[0],points[1],points[2]).setMaterial(wallMaterial).setEmission(purple);
        Geometry triangle2 = new Triangle(points[2],points[3],points[0]).setMaterial(wallMaterial).setEmission(purple);
        Geometry triangle3 = new Triangle(points[4],points[5],points[6]).setMaterial(wallMaterial).setEmission(purple);
        Geometry triangle4 = new Triangle(points[6],points[7],points[4]).setMaterial(wallMaterial).setEmission(purple);
        Geometry triangle5 = new Triangle(points[3],points[2],points[6]).setMaterial(wallMaterial).setEmission(purple);
        Geometry triangle6 = new Triangle(points[6],points[7],points[3]).setMaterial(wallMaterial).setEmission(purple);

        Vector add  = new Vector(0,0,5.2);
        for (int i = 0; i < 8; i++) {
            points[i] = points[i].add(add);
        }

        Geometry triangle1b = new Triangle(points[0],points[1],points[2]).setMaterial(wallMaterial).setEmission(purple);
        Geometry triangle2b = new Triangle(points[2],points[3],points[0]).setMaterial(wallMaterial).setEmission(purple);
        Geometry triangle3b = new Triangle(points[4],points[5],points[6]).setMaterial(wallMaterial).setEmission(purple);
        Geometry triangle4b = new Triangle(points[6],points[7],points[4]).setMaterial(wallMaterial).setEmission(purple);
        Geometry triangle5b = new Triangle(points[3],points[2],points[6]).setMaterial(wallMaterial).setEmission(purple);
        Geometry triangle6b = new Triangle(points[6],points[7],points[3]).setMaterial(wallMaterial).setEmission(purple);

        scene.geometries.add(triangle1,triangle2,triangle3,triangle4,triangle5,triangle6);
        scene.geometries.add(triangle1b,triangle2b,triangle3b,triangle4b,triangle5b,triangle6b);

        Geometry[] spheres1 = new Geometry[4];
        Geometry[] spheres2 = new Geometry[4];
        for (int i = 0; i < 4; i++) {
            spheres1[i] = new Sphere(2, axis1.getPoint(i * 4.1))
                    .setMaterial(ballMaterial).setEmission(gold);
            scene.geometries.add(spheres1[i]);
        }
        for (int i = 0; i < 4; i++) {
            spheres2[i] = new Sphere(2, axis2.getPoint(i * 4.1))
                    .setMaterial(ballMaterial);
            scene.geometries.add(spheres2[i]);
        }
        spheres1[0].setEmission(red);
        spheres1[1].setEmission(gold);
        spheres1[2].setEmission(gold);
        spheres1[3].setEmission(green);
        spheres2[0].setEmission(new Color(125,50,0));
        spheres2[1].setEmission(gold);
        spheres2[2].setEmission(blue);
        spheres2[3].setEmission(gold);

        PointLight pl1 = new PointLight(lightColor,new Point(4,8,3));
        PointLight pl2 = new PointLight(lightColor,new Point(-4,8,-3));
        DirectionalLight dl = new DirectionalLight(new Color(ORANGE).scale(0.8), new Vector(0,-1,0));

        scene.lights.add(pl1);
        scene.lights.add(pl2);
        scene.lights.add(dl);

        Camera cam = Camera.getBuilder()
                .setImageWriter(new ImageWriter("withBoundaryBox",800,800))
                .setRayTracer(new SimpleRayTracer(scene))
                .setVpSize(30,30)
                .setVpDistance(40)
                .setLocation(new Point(8,16,2))
                .setDirection(new Point(0,0,-1), new Vector(-1,-1,8))
                .setAntiAliasing(new AntiAliasingSuperSampler())
                .setBoundaryVolumeOn(false)
                .build();
        cam.renderImage().writeToImage();
    }
}