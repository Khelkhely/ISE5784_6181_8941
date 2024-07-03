package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * Class SimpleRayTracer is a class of simple ray tracers
 * @author Rachel and Tehila
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * a constructor for SimpleRayTracer
     * @param scene the scene the rays that the class is tracing go through
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) { //there are no intersections
            return scene.background;
        }
        return calcColor(ray.findClosestPoint(intersections));
    }

    /**
     * calculates the color of a point in the scene
     * @param point a point in the scene
     * @return the color of the point received
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
