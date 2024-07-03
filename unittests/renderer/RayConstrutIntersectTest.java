package renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;

import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

/**
 * Testing integration between the ray construction and ray intersections functions
 * @author Rachel and Tehila
 */
public class RayConstrutIntersectTest {

    /**
     * counts the number of intersection points between a geometry and the rays that are cast from
     * a camera to a 3x3 view plane
     * @param geometry the geometry that the rays intersect
     * @param camera the camera the rays are cast from
     * @return the number of intersections
     */
    int countIntersections(Intersectable geometry, Camera camera) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRay(3,3,j, i);
                List<Point> list = geometry.findIntersections(ray);
                if(list != null) {
                    count += list.size();
                }
            }
        }
        return count;
    }

    @Test
    void testSphereIntegration() {
        Camera camera1 = Camera.getBuilder()
                .setVpSize(3,3)
                .setVpDistance(1)
                .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
                .setLocation(new Point(0,0,0))
                .setImageWriter(new ImageWriter("test integration",100,100))
                .setRayTracer(new SimpleRayTracer(new Scene("test integration")))
                .build();

        // TC01
        Sphere sphere1 = new Sphere(1, new Point(0,0,-3));

        assertEquals(2,
                countIntersections(sphere1, camera1),
                "Number of intersection points is incorrect.");

        // TC02
        Camera camera2 = Camera.getBuilder()
                .setVpSize(3,3)
                .setVpDistance(1)
                .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
                .setLocation(new Point(0,0,0.5))
                .setImageWriter(new ImageWriter("test integration",100,100))
                .setRayTracer(new SimpleRayTracer(new Scene("test integration")))
                .build();
        Sphere sphere2 = new Sphere(2.5, new Point(0,0,-2.5));
        assertEquals(18,
                countIntersections(sphere2, camera2),
                "Number of intersection points is incorrect.");

        // TC03
        Sphere sphere3 = new Sphere(2, new Point(0,0,-2));
        assertEquals(10,
                countIntersections(sphere3, camera2),
                "Number of intersection points is incorrect.");

        // TC04
        Sphere sphere4 = new Sphere(4, new Point(0,0,-2));
        assertEquals(9,
                countIntersections(sphere4, camera2),
                "Number of intersection points is incorrect.");

        // TC05
        Sphere sphere5 = new Sphere(0.5, new Point(0,0,1));
        assertEquals(0,
                countIntersections(sphere5, camera1),
                "Number of intersection points is incorrect.");
    }

    @Test
    void testTriangleIntegration() {
        Camera camera = Camera.getBuilder()
                .setVpSize(3,3)
                .setVpDistance(1)
                .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
                .setLocation(new Point(0,0,0))
                .setImageWriter(new ImageWriter("test integration",100,100))
                .setRayTracer(new SimpleRayTracer(new Scene("test integration")))
                .build();

        // TC01
        Triangle triangle1 = new Triangle(
                new Point(0,1,-2),
                new Point(1,-1,-2),
                new Point(-1,-1,-2)
        );
        assertEquals(1,
                countIntersections(triangle1, camera),
                "Number of intersection points is incorrect.");

        // TC02
        Triangle triangle2 = new Triangle(
                new Point(0,20,-2),
                new Point(1,-1,-2),
                new Point(-1,-1,-2)
        );
        assertEquals(2,
                countIntersections(triangle2, camera),
                "Number of intersection points is incorrect.");
    }

    @Test
    void testPlaneIntegration() {
        Camera camera = Camera.getBuilder()
                .setVpSize(3,3)
                .setVpDistance(1)
                .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
                .setLocation(new Point(0,0,0))
                .setImageWriter(new ImageWriter("test integration",100,100))
                .setRayTracer(new SimpleRayTracer(new Scene("test integration")))
                .build();

        // TC01
        Plane plane1 = new Plane(
          new Point(0,0,-3),
          new Vector(0,0,1)
        );
        assertEquals(9,
                countIntersections(plane1, camera),
                "Number of intersection points is incorrect.");

        // TC02
        Plane plane2 = new Plane(
                new Point(0,0,-3),
                new Vector(0,-0.5,1)
        );
        assertEquals(9,
                countIntersections(plane2, camera),
                "Number of intersection points is incorrect.");

        // TC03
        Plane plane3 = new Plane(
                new Point(0,0,-3),
                new Vector(0,-9,1)
        );
        assertEquals(6,
                countIntersections(plane3, camera),
                "Number of intersection points is incorrect.");

    }
}
