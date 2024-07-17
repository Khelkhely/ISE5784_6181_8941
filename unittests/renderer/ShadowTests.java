package renderer;

import static java.awt.Color.*;

import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;



/** Testing basic shadows
 * @author Dan */
public class ShadowTests {
   /** Scene of the tests */
   private final Scene          scene      = new Scene("Test scene");
   /** Camera builder of the tests */
   private final Camera.Builder camera     = Camera.getBuilder()
      .setDirection(Point.ZERO, Vector.Y)
      .setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
      .setVpSize(200, 200)
      .setRayTracer(new SimpleRayTracer(scene));

   /** The sphere in the tests */
   private final Intersectable  sphere     = new Sphere(60d, new Point(0, 0, -200))
      .setEmission(new Color(BLUE))
      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
   /** The material of the triangles in the tests */
   private final Material       trMaterial = new Material().setKd(0.5).setKs(0.5).setShininess(30);

   /** Helper function for the tests in this module
    * @param pictName     the name of the picture generated by a test
    * @param triangle     the triangle in the test
    * @param spotLocation the spotlight location in the test */
   private void sphereTriangleHelper(String pictName, Triangle triangle, Point spotLocation) {
      scene.geometries.add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
      scene.lights.add( //
                       new SpotLight(new Color(400, 240, 0), spotLocation, new Vector(1, 1, -3)) //
                          .setKl(1E-5).setKq(1.5E-7));
      camera.setImageWriter(new ImageWriter(pictName, 400, 400))
         .build()
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a sphere and triangle with point light and shade */
   @Test
   public void sphereTriangleInitial() {
      sphereTriangleHelper("shadowSphereTriangleInitial", //
                           new Triangle(
                                   new Point(-70, -40, 0),
                                   new Point(-40, -70, 0),
                                   new Point(-68, -68, -4)
                           ),
                           new Point(-100, -100, 200));
   }
   /** Sphere-Triangle shading - move triangle up-right */
   @Test
   public void sphereTriangleMove1() {
      sphereTriangleHelper("shadowSphereTriangleMove1",
              new Triangle(
                      new Point(-57, -27, 4),
                      new Point(-27, -57, 4),
                      new Point(-55, -55, 0)
              ),
              new Point(-100, -100, 200));
   }

   /** Sphere-Triangle shading - move triangle upper-righter */
   @Test
   public void sphereTriangleMove2() {
      sphereTriangleHelper("shadowSphereTriangleMove2", //
              new Triangle(
                      new Point(-44, -14, 8),
                      new Point(-14, -44, 8),
                      new Point(-42, -42, 4)
              ),
                           new Point(-100, -100, 200));
   }

   /** Sphere-Triangle shading - move spot closer */
   @Test
   public void sphereTriangleSpot1() {
      sphereTriangleHelper("shadowSphereTriangleSpot1", //
                           new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                           new Point(-81, -81, 99));
   }

   /** Sphere-Triangle shading - move spot even more close */
   @Test
   public void sphereTriangleSpot2() {
      sphereTriangleHelper("shadowSphereTriangleSpot2", //
                           new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                           new Point(-72, -72, 50));
   }

   /** Produce a picture of a two triangles lighted by a spot light with a Sphere
    * producing a shading */
   @Test
   public void trianglesSphere() {
      scene.geometries.add(
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                           new Sphere(30d, new Point(0, 0, -11)) //
                              .setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
      );
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
      scene.lights.add(
                       new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                          .setKl(4E-4).setKq(2E-5));

      camera.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600))
         .build()
         .renderImage()
         .writeToImage();
   }

   /** experimenting with making an image */
   @Test
   public void tempTest() {
      Scene scene = new Scene("glass pyramid");
      Camera.Builder camBuild = Camera.getBuilder()
              .setImageWriter(new ImageWriter("tmp",800,800))
              .setRayTracer(new SimpleRayTracer(scene))
              .setVpSize(5,5)
              .setVpDistance(10);

      Point camLocation = new Point(15, 15, 10);

      Material mirror = new Material().setKs(0.5).setKd(0.5).setShininess(50).setKr(1);
      Material mirrory = new Material().setKs(0.5).setKd(0.5).setShininess(40).setKr(0.5);
      Material simple = new Material().setKs(0.5).setKd(0.5).setShininess(70).setKr(0.2);
      Material wally = new Material().setKs(0.5).setKd(0.5).setShininess(30);
      Material glass = new Material().setKs(0.5).setKd(0.5).setShininess(15).setKt(0.4).setKr(0.1);
      Material clearGlass = new Material().setKt(0.95).setShininess(30).setKs(0.5).setKd(0.5);

      Plane plane = new Plane(Point.ZERO, Vector.Z);
      /*Point p1 = new Point(0,0,0.2);
      Point p2 = new Point(-6,0,0.2);
      Point p3 = new Point(-3,-5.2,0.2);
      Point p4 = new Point(-3,-1.73,4);
      Triangle t1 = new Triangle(p1,p2,p3);
      Triangle t2 = new Triangle(p1,p2,p4);
      Triangle t3 = new Triangle(p4,p2,p3);
      Triangle t4 = new Triangle(p1,p4,p3);*/
      Point center = new Point(-6, -6, 3);
      Sphere s1 = new Sphere(2, center);
      /*
      Point pt1 = new Point(0,-12,1);
      Point pt2 = new Point(-20,-10,1);
      Point pt3 = new Point(-9,-10,10);
      Triangle tri = new Triangle(pt1,pt2,pt3);
      */
       Ray axis = new Ray(center, new Vector(-4, 1, 0));
       Sphere s2 = new Sphere(2, axis.getPoint(5));
       Sphere s3 = new Sphere(2, axis.getPoint(-5));
       Sphere s4 = new Sphere(2, axis.getPoint(10));
      Tube tube = new Tube(2.2, axis);
      Vector normal = new Vector(1,4,0);
      Plane wall = new Plane(center.add(normal.scale(-1)),normal);

      scene.geometries = new Geometries(plane.setMaterial(mirror).setEmission(new Color(20,20,20)));
      /*scene.geometries.add(t1.setMaterial(glass).setEmission(new Color(BLUE)));
      scene.geometries.add(t2.setMaterial(glass).setEmission(new Color(100,0,0)));
      scene.geometries.add(t3.setMaterial(glass).setEmission(new Color(0,100,0)));
      scene.geometries.add(t4.setMaterial(glass).setEmission(new Color(0,0,100)));*/
      //scene.geometries.add(tri.setMaterial(mirrory).setEmission(new Color(100,0,100)));
      scene.geometries.add(tube.setMaterial(clearGlass).setEmission(new Color(30,30,30)));
      Color gold = new Color(60, 50, 0);
      scene.geometries.add(s1.setMaterial(simple).setEmission(gold));
      scene.geometries.add(s2.setMaterial(simple).setEmission(gold));
      scene.geometries.add(s3.setMaterial(simple).setEmission(gold));
      scene.geometries.add(s4.setMaterial(simple).setEmission(gold));
      scene.geometries.add(wall.setMaterial(wally).setEmission(new Color(50,0,50)));


      DirectionalLight dl = new DirectionalLight(new Color(60,60,60),new Vector(0,1,-1));
      Color lightColor = new Color(120, 120, 120);
      PointLight pl = new PointLight(lightColor, new Point(-3,0,1));
      PointLight pl1 = new PointLight(gold.scale(2), center.add(camLocation.subtract(center).scale(0.5)));
      Point p0 = center.add(Vector.Z.scale(10));
      SpotLight sl = new SpotLight(lightColor.scale(2),p0,center.subtract(p0));
      //scene.lights.add(pl);
      scene.lights.add(pl1);
      //scene.lights.add(sl);
      scene.ambientLight = new AmbientLight(new Color(10,10,10), 0.1);
      scene.setBackground(Color.BLACK);

      Camera cam1 = camBuild
              .setLocation(camLocation)
              .setDirection(center, new Vector(-1,-1,6))
              .build();
      cam1.renderImage().writeToImage();
   }
}
