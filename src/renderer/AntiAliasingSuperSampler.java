package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * class AntiAliasingSuperSampler represents a super sampler that implements the antialiasing improvement
 * @author Rachel and Tehila
 */
public class AntiAliasingSuperSampler extends SuperSampler {
    /** a ray tracer the super sampler uses to calculate the color received from each sample ray */
    private RayTracerBase rayTracer = null;
    /** a vector represents the up direction of the target area */
    private Vector up;
    /** a vector represents the right direction of the target area */
    private Vector right;

    /**
     * an empty constructor to initialize an antialiasing super sampler object
     */
    public AntiAliasingSuperSampler() {
        targetArea = new RectangleJitteredGrid();
    }

    /**
     * a constructor to initialize an antialiasing super sampler object with the number of samples it'll generate
     * @param numberOfSamples the number of samples it'll generate
     */
    public AntiAliasingSuperSampler(int numberOfSamples) {
        targetArea = new RectangleJitteredGrid().setNumberOfSamples(numberOfSamples);
    }

    /**
     * setter method for the target area of the super sampler
     * @param targetArea the target area of the super sampler
     * @return the AntiAliasingSuperSampler object
     */
    public AntiAliasingSuperSampler setTargetArea(TargetAreaBase targetArea) {
        super.setTargetArea(targetArea);
        return this;
    }

    /**
     * setter method for the ray tracer of the super sampler
     * @param rayTracer a ray tracer the super sampler uses
     * @return the AntiAliasingSuperSampler object
     */
    public AntiAliasingSuperSampler setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * setter method for the up vector of the super sampler
     * @param up a vector represents the up direction of the target area
     * @return the AntiAliasingSuperSampler object
     */
    public AntiAliasingSuperSampler setUp(Vector up) {
        this.up = up;
        return this;
    }

    /**
     * setter method for the right vector of the super sampler
     * @param right a vector represents the right direction of the target area
     * @return the AntiAliasingSuperSampler object
     */
    public AntiAliasingSuperSampler setRight(Vector right) {
        this.right = right;
        return this;
    }

    @Override
    public List<Ray> generateBeamOfRays(Point convergence, Point targetAreaCenter) {
        List<Ray> sampleRays = new LinkedList<>();
        for (Point samplePoint : targetArea.generateSamples(targetAreaCenter, up, right)) {
            sampleRays.add(new Ray(convergence, samplePoint.subtract(convergence)));
        }
        return sampleRays;
    }

    @Override
    public Double3 traceRayValue(Ray ray) {
        return rayTracer.traceRay(ray).getRgb();
    }
}
