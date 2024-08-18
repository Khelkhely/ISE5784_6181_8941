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
    private Vector up;
    private Vector right;

    /**
     * a constructor to initialize an antialiasing super sampler object with the number of samples it'll generate
     * @param numberOfSamples the number of samples it'll generate
     */
    public AntiAliasingSuperSampler(int numberOfSamples) {
        targetArea = new RectangleJitteredGrid().setNumberOfSamples(numberOfSamples);
    }

    /**
     * an empty constructor to initialize an antialiasing super sampler object
     */
    public AntiAliasingSuperSampler() {
        targetArea = new RectangleJitteredGrid();
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

    //todo javadoc
    /**
     *
     * @param up
     * @return
     */
    public AntiAliasingSuperSampler setUp(Vector up) {
        this.up = up;
        return this;
    }

    /**
     *
     * @param right
     * @return
     */
    public AntiAliasingSuperSampler setRight(Vector right) {
        this.right = right;
        return this;
    }

    public AntiAliasingSuperSampler setNumberOfSamples(int numberOfSamples) {
        this.targetArea.setNumberOfSamples(numberOfSamples);
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
