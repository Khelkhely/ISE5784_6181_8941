package renderer;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import primitives.Vector;
import scene.Scene;

import java.util.*;
import static java.lang.Math.floor;

public class AccelerationTests {
    Material ballMaterial = new Material().setKs(0.6).setKd(0.4).setShininess(10);
    Material wallMaterial = new Material().setKs(0.5).setKd(0.5).setShininess(10).setKr(0.3);
    Material tubeMaterial = new Material().setKt(0.75).setShininess(30).setKs(0.7).setKd(0.3);
    Color lightColor1 = new Color(110,110,70);
    Color lightColor2 = new Color(110,110,0);
    Color tubeColor = new Color(0,0,20);
    Color wallColor = new Color(20, 0, 45);
    Color yellow = new Color(100, 75, 0);
    Color red = new Color(175, 0, 0);
    Color blue = new Color(0, 0, 175);
    Color green = new Color(0, 100, 75);
    Color purple = new Color(75, 0, 100);
    Color[] colors = new Color[] {red, blue, green, purple, yellow, yellow, yellow, yellow};

    LightSource[] lights = new LightSource[] {
            new DirectionalLight(lightColor1,new Vector(-1,0,0)),
            new PointLight(lightColor1,new Point(5,7,10)),
            new PointLight(lightColor1,new Point(5,-7,10)),
            new SpotLight(lightColor2, new Point(8,0,30), new Vector(-1,0,-1)),
            new SpotLight(lightColor2, new Point(8,0,0), new Vector(-1,0,1))
    };

    int numRows = 9;
    int numSpheres = 12;
    double radius = 2;
    double shelfHeight = 0.4;
    double sphereShelf = radius * 1.1;
    double interval = sphereShelf * 2 + shelfHeight;
    Random random = new Random();

    BoundaryBox defaultBoundaryBox = null;

    Point camLocation = new Point(30,-14,23);

    Camera.Builder camBuild = new Camera.Builder()
            .setVpDistance(15)
            .setVpSize(20,20)
            .setDirection(new Point(0,-6,18), Vector.Z)
            .setLocation(camLocation)
            .setAntiAliasing(new AntiAliasingSuperSampler(300));
    /**
     * creates a tube with colored spheres inside of it
     * @param radius the radius of the spheres inside the tube
     * @param direction the direction of the tube
     * @param numOfSpheres the number of spheres to generate inside the tube
     * @param center the center of the middle sphere inside the tube
     * @return the geometries that the tube consists of
     */
    private List<Intersectable> buildTube(double radius, Point center, Vector direction, int numOfSpheres) {
        double distance = radius * 2.05; //the distance between the centers of neighboring spheres
        Point first = center.add(direction.scale(-floor(numOfSpheres/2)*distance));
        Ray axis = new Ray(first, direction);
        axis = new Ray(axis.getPoint(Math.random()*2-1), direction);
        Tube tube = new Tube(radius * 1.1, axis);

        List<Intersectable> geometries = new LinkedList<>();
        geometries.add(tube.setEmission(tubeColor).setMaterial(tubeMaterial)
                .setBoundaryBox(new BoundaryBox(defaultBoundaryBox)));

        for (int i = 0; i < numOfSpheres; i++) { //create all the spheres and add them to geometries
            Sphere sphere = new Sphere(radius, axis.getPoint(i * distance));
            sphere.setEmission(colors[random.nextInt(8)]);
            geometries.add(sphere.setMaterial(ballMaterial));
        }
        return geometries;
    }

    private List<Intersectable> buildShelf(Point center, double length, double width, double height, Vector up,
                            Vector forward) {
        Vector right = up.crossProduct(forward).normalize();
        Point topLeftA = center.add(right.scale(-length/2));
        Point topRightA = center.add(right.scale(length/2));
        Point bottomLeftA = topLeftA.add(forward.scale(width));
        Point bottomRightA = topRightA.add(forward.scale(width));
        Point topLeftB = topLeftA.add(up.scale(height));
        Point topRightB = topRightA.add(up.scale(height));
        Point bottomLeftB = bottomLeftA.add(up.scale(height));
        Point bottomRightB = bottomRightA.add(up.scale(height));

        Triangle top1 = new Triangle(topLeftA,topRightA,bottomLeftA);
        Triangle top2 = new Triangle(bottomRightA,topRightA,bottomLeftA);
        Triangle bottom1 = new Triangle(topLeftB,topRightB,bottomLeftB);
        Triangle bottom2 = new Triangle(bottomRightB,topRightB,bottomLeftB);
        Triangle front1 = new Triangle(bottomRightA,bottomLeftA,bottomRightB);
        Triangle front2 = new Triangle(bottomLeftB,bottomLeftA,bottomRightB);

        top1.setEmission(wallColor).setMaterial(wallMaterial);
        top2.setEmission(wallColor).setMaterial(wallMaterial);
        bottom1.setEmission(wallColor).setMaterial(wallMaterial);
        bottom2.setEmission(wallColor).setMaterial(wallMaterial);
        front1.setEmission(wallColor).setMaterial(wallMaterial);
        front2.setEmission(wallColor).setMaterial(wallMaterial);

        return List.of(top1,top2,bottom1,bottom2,front1,front2);
    }

    public Scene buildFlatScene() {
        Scene scene = new Scene("Mini Project 2");

        Point center1 = Point.ZERO;
        Ray ray = new Ray(center1, Vector.Z);
        Point center2 = ray.getPoint(sphereShelf).add(Vector.X.scale(-sphereShelf));
        Ray shelf = new Ray(center2, Vector.Z);

        defaultBoundaryBox = new BoundaryBox(
                center2.getX(),
                (numSpheres/2) * radius * -2.05,
                center1.getZ() - sphereShelf,
                camLocation.getX(),
                (numSpheres/2) * radius * 2.05,
                center1.getZ() + interval * numRows + sphereShelf
        );

        for (int i = 0; i < numRows; i++) {
            Point point = ray.getPoint(i * interval);
            scene.geometries.add(
                    buildTube(
                    radius,
                    point,
                    Vector.Y,
                    numSpheres
                    )
            );
            scene.geometries.add(
                    buildShelf(
                            shelf.getPoint(i * interval),
                            radius*1.05,
                            numSpheres*radius*2.2,
                            shelfHeight,
                            Vector.Z,
                            Vector.Y
                    )
            );
        }

        Plane wall = new Plane(center2, Vector.X);
        Plane floor = new Plane(ray.getPoint(-sphereShelf), Vector.Z);

        wall.setEmission(wallColor).setMaterial(wallMaterial).setBoundaryBox(new BoundaryBox(defaultBoundaryBox));
        floor.setEmission(wallColor).setMaterial(wallMaterial).setBoundaryBox(new BoundaryBox(defaultBoundaryBox));

        scene.geometries.add(wall,floor);
        scene.lights.addAll(Arrays.stream(lights).toList());
        scene.setAmbientLight(new AmbientLight(lightColor1,0.5));
        return scene;
    }

    public Scene buildHierarchicalScene() {
        Scene scene = new Scene("Mini Project 2");

        Point center1 = Point.ZERO;
        Ray ray = new Ray(center1, Vector.Z);
        Point center2 = ray.getPoint(sphereShelf).add(Vector.X.scale(-sphereShelf));
        Ray shelf = new Ray(center2, Vector.Z);

        defaultBoundaryBox = new BoundaryBox(
                center2.getX(),
                (-numSpheres/2) * radius * 2.05,
                center1.getZ() - sphereShelf,
                camLocation.getX(),
                (numSpheres/2) * radius * 2.05,
                center1.getZ() + interval * numRows + sphereShelf
        );

        Geometries bottomHalf = new Geometries();
        Geometries topHalf = new Geometries();
        int i = 0;
        for (; i < numRows / 2; i++) {
            Point point = ray.getPoint(i * interval);
            Geometries tubeG = new Geometries(
                    buildTube(
                            radius,
                            point,
                            Vector.Y,
                            numSpheres
                    )
            );
            Geometries shelfG = new Geometries(
                    buildShelf(
                            shelf.getPoint(i * interval),
                            radius*1.05,
                            numSpheres*radius*2.2,
                            shelfHeight,
                            Vector.Z,
                            Vector.Y
                    )
            );
            bottomHalf.add(tubeG, shelfG);
        }

        for (; i < numRows; i++) {
            Point point = ray.getPoint(i * interval);
            Geometries tubeG = new Geometries(
                    buildTube(
                            radius,
                            point,
                            Vector.Y,
                            numSpheres
                    )
            );
            Geometries shelfG = new Geometries(
                    buildShelf(
                            shelf.getPoint(i * interval),
                            radius*1.05,
                            numSpheres*radius*2.2,
                            shelfHeight,
                            Vector.Z,
                            Vector.Y
                    )
            );
            topHalf.add(tubeG, shelfG);
        }

        Plane wall = new Plane(center2, Vector.X);
        Plane floor = new Plane(ray.getPoint(-sphereShelf), Vector.Z);

        wall.setEmission(wallColor).setMaterial(wallMaterial).setBoundaryBox(new BoundaryBox(defaultBoundaryBox));
        floor.setEmission(wallColor).setMaterial(wallMaterial).setBoundaryBox(new BoundaryBox(defaultBoundaryBox));

        scene.geometries.add(new Geometries(topHalf, bottomHalf, wall),floor);
        scene.lights.addAll(Arrays.stream(lights).toList());
        scene.setAmbientLight(new AmbientLight(lightColor1,0.5));
        return scene;
    }

    @Test
    public void NoAccelerations() {
        camBuild.setImageWriter(new ImageWriter("NoAccelerations",500,500))
                .setRayTracer(new SimpleRayTracer(buildFlatScene()))
                .build().renderImage().writeToImage();

    }
    @Test
    public void FlatBoundaryVolume() {
        Scene scene = buildFlatScene();
        camBuild.setImageWriter(new ImageWriter("FlatBoundaryVolume",500,500))
                .setRayTracer(new SimpleRayTracer(scene))
                .setBoundaryVolumeOn(true)
                .build().renderImage().writeToImage();
    }

    @Test
    public void BoundaryVolumeHierarchy() {
        Scene scene = buildHierarchicalScene();
        //scene.geometries.buildBVH();
        camBuild.setImageWriter(new ImageWriter("BoundaryVolumeHierarchy",500,500))
                .setRayTracer(new SimpleRayTracer(scene))
                .setBoundaryVolumeOn(true)
                .build().renderImage().writeToImage();
    }

    @Test
    public void BoundaryVolumeAutomaticHierarchy() {
        Scene scene = buildFlatScene();
        scene.geometries.buildBVH();
        camBuild.setImageWriter(new ImageWriter("BoundaryVolumeAutomaticHierarchy",500,500))
                .setRayTracer(new SimpleRayTracer(scene))
                .setBoundaryVolumeOn(true)
                .build().renderImage().writeToImage();
    }

    int numOfThreads = 3;

    @Test
    public void MultiThreading() {
        Scene scene = buildFlatScene();
        camBuild.setImageWriter(new ImageWriter("MultiThreading",500,500))
                .setRayTracer(new SimpleRayTracer(scene))
                .setNumOfThreads(numOfThreads)
                .build().renderImage().writeToImage();
    }

    @Test
    public void BoundaryVolumeMultiThreading() {
        Scene scene = buildFlatScene();
        scene.geometries.buildBVH();
        camBuild.setImageWriter(new ImageWriter("BoundaryVolumeMultiThreading",500,500))
                .setRayTracer(new SimpleRayTracer(scene))
                .setBoundaryVolumeOn(true)
                .setNumOfThreads(numOfThreads)
                .build().renderImage().writeToImage();
    }
}
