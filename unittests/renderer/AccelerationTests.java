package renderer;

import geometries.*;
import lighting.DirectionalLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.lang.Math.floor;

public class AccelerationTests {
    Material ballMaterial = new Material().setKs(0.5).setKd(0.5).setShininess(20);
    Material wallMaterial = new Material().setKs(0.5).setKd(0.5).setShininess(10).setKr(0.3);
    Material tubeMaterial = new Material().setKt(0.7).setShininess(30).setKs(0.7).setKd(0.3);
    Color lightColor = new Color(180,132,120);
    Color tubeColor = new Color(0,0,20);
    Color gold = new Color(100, 75, 0);
    Color red = new Color(175, 0, 0);
    Color blue = new Color(0, 0, 175);
    Color green = new Color(0, 100, 75);
    Color purple = new Color(20, 0, 45);

    Point camLocation = new Point(30,-3,22);

    BoundaryBox defaultBoundaryBox = null;

    Camera.Builder camBuild = new Camera.Builder()
            .setVpDistance(15)
            .setVpSize(21,21)
            .setDirection(new Point(0,0,18), Vector.Z)
            .setLocation(camLocation);
            //.setAntiAliasing(new AntiAliasingSuperSampler());
    /**
     * creates a tube with colored spheres inside of it
     * @param radius the radius of the spheres inside the tube
     * @param direction the direction of the tube
     * @param numOfSpheres the number of spheres to generate inside the tube
     * @param center the center of the middle sphere inside the tube
     * @return the geometries that the tube consists of
     */
    private void buildTube(double radius, Point center, Vector direction, int numOfSpheres, Geometries geometries) {
        double distance = radius * 2.05; //the distance between the centers of neighboring spheres
        Point first = center.add(direction.scale(-floor(numOfSpheres/2)*distance));
        Ray axis = new Ray(first, direction);
        Tube tube = new Tube(radius * 1.1, axis);

        geometries = new Geometries(tube.setEmission(tubeColor).setMaterial(tubeMaterial)
                .setBoundaryBox(new BoundaryBox(defaultBoundaryBox)));

        for (int i = 0; i < numOfSpheres; i++) { //create all the spheres and add them to geometries
            Sphere sphere = new Sphere(radius, axis.getPoint(i * distance));
            geometries.add(sphere.setEmission(gold).setMaterial(ballMaterial));
        }
    }

    private void buildShelf(Point center, double length, double width, double height, Vector up,
                            Vector forward, Geometries geometries) {
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

        geometries.add(top1,top2,bottom1,bottom2,front1,front2);
    }

    public Scene buildFlatScene() {
        Scene scene = new Scene("Mini Project 2");
        int numRows = 10;
        int numSpheres = 11;
        double radius = 2;
        double shelfHeight = 0.2;
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
            buildTube(
                    radius,
                    point,
                    Vector.Y,
                    numSpheres,
                    scene.geometries
                    );
            buildShelf(
                    shelf.getPoint(i * interval),
                    radius*1.05,
                    numSpheres*radius*2.2,
                    shelfHeight,
                    Vector.Z,
                    Vector.Y,
                    scene.geometries
            );
        }

        Plane wall = new Plane(center2, Vector.X);
        Plane floor = new Plane(ray.getPoint(-sphereShelf), Vector.Z);

        wall.setEmission(purple).setMaterial(wallMaterial).setBoundaryBox(new BoundaryBox(defaultBoundaryBox));
        floor.setEmission(purple).setMaterial(wallMaterial).setBoundaryBox(new BoundaryBox(defaultBoundaryBox));

        scene.geometries.add(wall,floor);
        scene.lights.add(new DirectionalLight(lightColor,new Vector(-1,0,0)));
        return scene;
    }

    public Scene buildHierarchicalScene() {
        Scene scene = new Scene("Mini Project 2");
        int numRows = 10;
        int numSpheres = 11;
        double radius = 2;
        double shelfHeight = 0.2;
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

        Geometries bottomHalf = new Geometries();
        Geometries topHalf = new Geometries();
        int i = 0;
        for (; i < numRows / 2; i++) {
            Point point = ray.getPoint(i * interval);
            Geometries tubeG = new Geometries();
            Geometries shelfG = new Geometries();
            buildTube(
                    radius,
                    point,
                    Vector.Y,
                    numSpheres,
                    tubeG
            );
            buildShelf(
                    shelf.getPoint(i * interval),
                    radius*1.05,
                    numSpheres*radius*2.2,
                    shelfHeight,
                    Vector.Z,
                    Vector.Y,
                    shelfG
            );
            bottomHalf.add(tubeG, shelfG);
        }

        for (; i < numRows; i++) {
            Point point = ray.getPoint(i * interval);
            Geometries tubeG = new Geometries();
            Geometries shelfG = new Geometries();
            buildTube(
                    radius,
                    point,
                    Vector.Y,
                    numSpheres,
                    tubeG
            );
            buildShelf(
                    shelf.getPoint(i * interval),
                    radius*1.05,
                    numSpheres*radius*2.2,
                    shelfHeight,
                    Vector.Z,
                    Vector.Y,
                    shelfG
            );
            topHalf.add(tubeG, shelfG);
        }

        Plane wall = new Plane(center2, Vector.X);
        Plane floor = new Plane(ray.getPoint(-sphereShelf), Vector.Z);

        wall.setEmission(purple).setMaterial(wallMaterial).setBoundaryBox(new BoundaryBox(defaultBoundaryBox));
        floor.setEmission(purple).setMaterial(wallMaterial).setBoundaryBox(new BoundaryBox(defaultBoundaryBox));

        scene.geometries.add(new Geometries(topHalf, bottomHalf, wall),floor);
        scene.lights.add(new DirectionalLight(lightColor,new Vector(-1,0,0)));
        return scene;
    }

    @Test
    public void NoAccelerations() {
        camBuild.setImageWriter(new ImageWriter("testing",200,200))
                .setRayTracer(new SimpleRayTracer(buildFlatScene()))
                //.setBoundaryVolumeOn(true)
                .build().renderImage().writeToImage();

    }
    @Test
    public void FlatBoundaryVolume() {

    }

    @Test
    public void BoundaryVolumeHierarchy() {

    }

    @Test
    public void BoundaryVolumeAutomaticHierarchy() {

    }

    @Test
    public void MultiThreading() {

    }

    @Test
    public void BoundaryVolumeMultiThreading() {

    }
}
