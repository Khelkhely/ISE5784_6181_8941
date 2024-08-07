package renderer;

import primitives.Color;
import primitives.Double3;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

public class AntiAlisingRayTracer extends RayTracerBase{
    SuperSampler superSampler;
    Vector up;
    Vector right;

    /**
     * a constructor for RayTracerBase
     *
     * @param scene the scene the rays that the class is tracing go through
     */
    public AntiAlisingRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        Double3 sum = Double3.ZERO;
        for(ray : superSampler.createSampleRays(ray, up, right, ))
    }
}
