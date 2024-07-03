package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Class RayTracerBase is a base class for ray tracers which calculate the color of a pixel
 * according to the ray that goes through it and its intersection points with the scene
 * @author Rachel and Tehila
 */
public abstract class RayTracerBase {
    /** the scene that the rays that the class is tracing go through */
    protected Scene scene;

    /**
     * a constructor for RayTracerBase
     * @param scene the scene the rays that the class is tracing go through
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * calculates the color of the pixel that the ray received according to the ray's intersections with the scene
     * @param ray the ray that is sent through the scene
     * @return the appropriate color according to the ray and the scene
     */
    public abstract Color traceRay(Ray ray);
}
