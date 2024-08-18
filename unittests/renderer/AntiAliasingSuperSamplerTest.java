package renderer;

import geometries.*;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;
import static java.lang.Math.floor;
import static org.junit.jupiter.api.Assertions.*;

class AntiAliasingSuperSamplerTest {

    /**
     * Test method for {@link AntiAliasingSuperSampler#calculateValue(Point, Point)}
     */
    @Test
    void testCalculateValue() {
        Scene scene = new Scene("different colors");
        Triangle blue = new Triangle(
          new Point(0,0,3),
          new Point(0,3,0),
          Point.ZERO
        );
        Triangle green = new Triangle(
                new Point(0,0,4),
                new Point(0,-3,1),
                new Point(0,0,1)
        );

        scene.geometries.add(
                blue.setEmission(new Color(0,0,100)),
                green.setEmission(new Color(0,100,0))
        );
        scene.background = new Color(100,0,0);

        AntiAliasingSuperSampler antiAliasing = new AntiAliasingSuperSampler()
                .setUp(Vector.Z)
                .setRight(Vector.Y)
                .setTargetArea(new RectangleJitteredGrid(4,1,1))
                .setRayTracer(new SimpleRayTracer(scene));

        assertEquals(
                new Double3(25,25,50),
                antiAliasing.calculateValue(new Point(100,0,0), new Point(1,0,1)),
                "ERROR: doesn't return the average of the colors"
        );
    }

    /*@Test
    public void tempTest() {
        Material material1 = new Material().setKs(0.5).setKd(0.5).setKt(1).setKr(0).setShininess(1);
        Material material2 = new Material().setKs(0.5).setKd(0.5).setKt(1).setKr(0.5).setShininess(20);

        Plane plane = new Plane(new Point(0,0,0), new Vector(1,0,0));
        plane.setEmission(new Color(BLUE).scale(0.5))
                .setMaterial(material1);

        Sphere sphere1 = new Sphere(2, new Point(2,0,0));
        sphere1.setEmission(new Color(WHITE))
                .setMaterial(material2);
        PointLight pointLight = new PointLight(new Color(YELLOW), new Point(0,2,2));
        DirectionalLight directionalLight = new DirectionalLight(new Color(YELLOW).scale(0.3), new Vector(-1,0,0));
        Scene scene = new Scene("plain");
        scene.setBackground(new Color(BLACK))
                .setAmbientLight(new AmbientLight(new Color(10,10,10), 0.1))
                .setGeometries(new Geometries(plane, sphere1));
        scene.lights.add(pointLight);
        scene.lights.add(directionalLight);

        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setImageWriter(new ImageWriter("tmp2", 800, 800))
                .setRayTracer(new SimpleRayTracer(scene))
                .setVpSize(5,5)
                .setVpDistance(5)
                .setLocation(new Point(10,0,0))
                .setDirection(new Vector(-1,0,0), new Vector(0,0,1));

        cameraBuilder
                .setImageWriter(new ImageWriter("without anti aliasing", 800, 800))
                .build().renderImage().writeToImage();
        cameraBuilder
                .setAntiAlising(new AntiAliasingSuperSampler(100))
                .setImageWriter(new ImageWriter("with anti aliasing", 800, 800))
                .build().renderImage().writeToImage();


    }*/

    @Test
    public void TriangleAntiAliasingTest() {
        Triangle triangle = new Triangle(
                Point.ZERO,
                new Point(0,20,0),
                new Point(0,0,20)
        );

        Scene scene = new Scene("TriangleAntiAliasingTest");
        scene.geometries.add(triangle.setEmission(new Color(0,0,100)));
        scene.background = new Color(0,100,0);

        Camera.Builder cameraBuilder = new Camera.Builder()
                .setLocation(new Point(40,10,10))
                .setDirection(new Vector(-1,0,0), Vector.Z)
                .setVpSize(5,5)
                .setVpDistance(10)
                .setRayTracer(new SimpleRayTracer(scene));

        cameraBuilder
                .setImageWriter(new ImageWriter("TriangleWithout",400,400))
                .build().renderImage().writeToImage();

        cameraBuilder
                .setAntiAlising(new AntiAliasingSuperSampler())
                .setImageWriter(new ImageWriter("TriangleWith",400,400))
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
    public void MiniProject1Final() {
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
        Geometry tube2 = new Tube(2.2, axis2).setMaterial(clearGlass).setEmission(tube);
        Geometry floor = new Plane(new Point(0,0,-5.5), Vector.Z)
                .setMaterial(wallMaterial).setEmission(purple);
        Geometry wall = new Plane(new Point(0,-2.5,0), Vector.Y)
                .setMaterial(ballMaterial).setEmission(purple);
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

        Camera.Builder camBuilder = Camera.getBuilder()
                .setImageWriter(new ImageWriter("miniProject1WithoutImprovement",800,800))
                .setRayTracer(new SimpleRayTracer(scene))
                .setVpSize(30,30)
                .setVpDistance(40)
                .setLocation(new Point(8,16,2))
                .setDirection(new Point(0,0,-1), new Vector(-1,-1,8));
        camBuilder.build().renderImage().writeToImage();
        camBuilder
                .setAntiAlising(new AntiAliasingSuperSampler(400))
                .setImageWriter(new ImageWriter("miniProject1WithImprovement",800,800))
                .build().renderImage().writeToImage();
    }

    /**
     * creates a tube with colored spheres inside of it
     * @param radius the radius of the spheres inside the tube
     * @param direction the direction of the tube
     * @param numOfSpheres the number of spheres to generate inside the tube
     * @param center the center of the middle sphere inside the tube
     * @return the geometries that the tube consists of
     */
    private Geometries buildTube(double radius, Point center, Vector direction, int numOfSpheres) {
        double distance = radius * 2.05; //the distance between the centers of neighboring spheres
        Point first = center.add(direction.scale(-floor(numOfSpheres/2)*distance));
        Ray axis = new Ray(first, direction);
        Tube tube = new Tube(radius * 1.1, axis);

        Geometries geometries = new Geometries(tube);

        for (int i = 0; i < numOfSpheres; i++) { //create all the spheres and add them to geometries
            Sphere sphere = new Sphere(radius, axis.getPoint(i * distance));
            geometries.add(sphere);
        }

        return geometries;
    }
}