package renderer;

import primitives.*;

/**
 * class AntiAliasingSuperSampler represents a super sampler that implements the antialiasing improvement
 * @author Rachel and Tehila
 */
public class AntiAliasingSuperSampler extends SuperSampler {
    /** a ray tracer the super sampler uses to calculate the color received from each sample ray */
    RayTracerBase rayTracer;

    /**
     * a constructor to initialize an antialiasing super sampler with its target area
     * @param targetArea the target area the super sampler will use to get sample points
     */
    public AntiAliasingSuperSampler(RectangleTargetArea targetArea) {
        super(targetArea);
    }

    /**
     *
     * @param targetArea the target area of the super sampler
     * @return
     */
    public AntiAliasingSuperSampler setTargetArea(TargetAreaBase targetArea) {
        super.setTargetArea(targetArea);
        return this;
    }

    /**
     *
     * @param rayTracer
     * @return
     */
    public AntiAliasingSuperSampler setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    @Override
    public Ray constructSampleRay(Point convergence, Point sample) {
        return new Ray(convergence, sample.subtract(convergence));
    }

    @Override
    public Double3 traceRayValue(Ray ray) {
        return rayTracer.traceRay(ray).getRgb();
    }
}
