package renderer;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.*;

class AntiAliasingSuperSamplerTest {

    /**
     * Test method for {@link AntiAliasingSuperSampler#traceColor(Point, Point)}
     */
    @Test
    void testTraceColor() {


    }

    @Test
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
        AntiAliasingSuperSampler antiAliasing = new AntiAliasingSuperSampler()
                .setNumberOfRays(300)
                .setRayTracer(new SimpleRayTracer(scene));
        cameraBuilder
                .setAntiAlising(antiAliasing)
                .setImageWriter(new ImageWriter("with anti aliasing", 800, 800))
                .build().renderImage().writeToImage();


    }
}