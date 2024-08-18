package renderer;

import primitives.*;

import java.util.List;

/**
 * Class SuperSampler is a base abstract class that manages super sampling for rays in the image for improvements
 * Calculates the average of the value from multiple samples rays instead of a simple ray to improve the image.
 * @author Rachel and Tehila
 */
public abstract class SuperSampler {
    /** the target area object the super sampler uses to get sample points to create sample rays */
    protected TargetAreaBase targetArea;

    /**
     * a constructor to initialize a super sampler with its target area
     * @param numberOfSamples the number of sample rays the super sampler will generate
     */
    public SuperSampler(int numberOfSamples) {
        this.targetArea.setNumberOfSamples(numberOfSamples);
    }

    /**
     * an empty constructor to initialize a super sampler object
     */
    public SuperSampler() {}

    /**
     * getter method for target area
     * @return the target area of the super sampler
     */
    public TargetAreaBase getTargetArea() {
        return targetArea;
    }

    /**
     * setter method for the target area of the super sampler
     * @param targetArea the target area
     * @return the super sampler object
     */
    public SuperSampler setTargetArea(TargetAreaBase targetArea) {
        this.targetArea = targetArea;
        return this;
    }

    /**
     * calculates the average of the colors that are calculated for all the sample rays
     * @param convergence the point where all the sample rays converge
     * @param targetAreaCenter the center of the target area from which the sample points are created
     * @return the average color received by super sampling
     */
    public Color calculateColor(Point convergence, Point targetAreaCenter) {
        return new Color(calculateValue(convergence, targetAreaCenter));
    }

    /**
     * calculates the average of the Double3 values that are calculated for all the sample rays
     * @param convergence the point where all the sample rays converge
     * @param targetAreaCenter the center of the target area from which the sample points are created
     * @return the average of the Double3 values of all the sample rays
     */
    public Double3 calculateValue(Point convergence, Point targetAreaCenter) {
        Double3 sum = Double3.ZERO;
        List<Ray> sampleRays = generateBeamOfRays(convergence, targetAreaCenter);
        for (Ray sampleRay : sampleRays) {
            sum = sum.add(traceRayValue(sampleRay));
        }
        return sum.reduce(sampleRays.size());
    }

    /* public abstract Ray constructSampleRay(Point convergence, Point sample);*/

    /**
     * creates a beam of sample rays between the point of convergence and a target area
     * @param convergence the point where all the sample rays converge
     * @param targetAreaCenter the center of the target area from which the sample points are created
     * @return a list of rays between the point of convergence and a target area
     */
    public abstract List<Ray> generateBeamOfRays(Point convergence, Point targetAreaCenter);

    /**
     * calculates the value of the ray that will be averaged between all the sample rays
     * @param ray a sample ray
     * @return the desired Double3 value of the ray
     */
    public abstract Double3 traceRayValue(Ray ray);
}
