package renderer;

import geometries.*;
import lighting.DirectionalLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floor;

public class AccelerationTests {
    Material ballMaterial = new Material().setKs(0.5).setKd(0.5).setShininess(20);
    Material wallMaterial = new Material().setKs(0.5).setKd(0.5).setShininess(10).setKr(0.5);
    Material tubeMaterial = new Material().setKt(0.7).setShininess(30).setKs(0.7).setKd(0.3);
    Color lightColor = new Color(180,132,120);
    Color tubeColor = new Color(0,0,10);
    Color gold = new Color(100, 75, 0);
    Color red = new Color(175, 0, 0);
    Color blue = new Color(0, 0, 175);
    Color green = new Color(0, 100, 75);
    Color purple = new Color(20, 0, 45);

    Point camLocation = new Point(30,0,27);

    BoundaryBox defaultBoundaryBox = null;

    Scene scene = buildScene();
    Camera.Builder camBuild;
    {
        camBuild = new Camera.Builder()
                .setVpDistance(15)
                .setVpSize(10,10)
                .setDirection(new Vector(-1,0,0), Vector.Z)
                .setLocation(camLocation)
                .setRayTracer(new SimpleRayTracer(scene))//;
                .setAntiAliasing(new AntiAliasingSuperSampler());
    }

    private int numOfThreads = 3;

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

        Geometries geometries = new Geometries(tube.setEmission(tubeColor).setMaterial(tubeMaterial).setBoundaryBox(defaultBoundaryBox));

        for (int i = 0; i < numOfSpheres; i++) { //create all the spheres and add them to geometries
            Sphere sphere = new Sphere(radius, axis.getPoint(i * distance));
            geometries.add(sphere.setEmission(gold).setMaterial(ballMaterial));
        }

        return geometries;
    }

    private Geometries buildShelf(Point center, double length, double width, double height, Vector up, Vector forward) {
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

        top1.setEmission(purple).setMaterial(wallMaterial);
        top2.setEmission(purple).setMaterial(wallMaterial);
        bottom1.setEmission(purple).setMaterial(wallMaterial);
        bottom2.setEmission(purple).setMaterial(wallMaterial);
        front1.setEmission(purple).setMaterial(wallMaterial);
        front2.setEmission(purple).setMaterial(wallMaterial);

        return new Geometries(top1,top2,bottom1,bottom2,front1,front2);
    }

    public Scene buildScene() {
        Scene scene = new Scene("Mini Project 2");
        int numRows = 10;
        int numSpheres = 10;
        double radius = 2;
        double shelfHeight = 0.1;
        double sphereShelf = radius * 1.1;
        double interval = sphereShelf * 2 + shelfHeight;


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

        wall.setEmission(purple).setMaterial(wallMaterial).setBoundaryBox(defaultBoundaryBox);
        floor.setEmission(purple).setMaterial(wallMaterial).setBoundaryBox(defaultBoundaryBox);

        scene.geometries.add(wall,floor);
        scene.lights.add(new DirectionalLight(lightColor,new Vector(-1,0,0)));
        return scene;
    }


    @Test
    public void NoAccelerations() {
        camBuild.setImageWriter(new ImageWriter("NoAccelerations",200,200))
                .build().renderImage().writeToImage();

    }

    @Test
    public void MultiThreading() {
        camBuild.setImageWriter(new ImageWriter("MultiThreading",200,200))
                .setNumOfThreads(numOfThreads)
                .build().renderImage().writeToImage();
    }

    @Test
    public void FlatBoundaryVolume() {
        camBuild.setImageWriter(new ImageWriter("FlatBoundaryVolume",200,200))
                .setBoundaryVolumeOn(true)
                .build().renderImage().writeToImage();
    }

    @Test
    public void BoundaryVolumeHierarchy() {
        /*Geometries geos2 = new Geometries(A, B, C);
        Geometries geos3 = new Geometries(D, E);
        Geometries geos1 = new Geometries(geos2, F, geos3);
        scene.setGeometries(geos1);*/
        camBuild.setImageWriter(new ImageWriter("BoundaryVolumeHierarchy",200,200))
                .setBoundaryVolumeOn(true)
                .build().renderImage().writeToImage();
    }

    @Test
    public void BoundaryVolumeAutomaticHierarchy() {
        List<BoundaryBox> objects = new ArrayList<>();
        BVHNode root = BVHNode.buildBVH(objects);
    }

    @Test
    public void BoundaryVolumeMultiThreading() {
        camBuild.setImageWriter(new ImageWriter("BoundaryVolumeMultiThreading",200,200))
                .setBoundaryVolumeOn(true)
                .setNumOfThreads(numOfThreads)
                .build().renderImage().writeToImage();
    }
}
