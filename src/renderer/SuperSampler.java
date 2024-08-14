package renderer;

import primitives.*;

import java.util.List;

/**
 * Class SuperSampler is a base abstract class that manages super sampling for rays in the image for improvements
 * @author Rachel and Tehila
 */
public abstract class SuperSampler {
    /** the target area object the super sampler uses to get sample points to create sample rays */
    private TargetAreaBase targetArea;

    /**
     * a constructor to initialize a super sampler with its target area
     * @param targetArea the target area the super sampler will use to get sample points
     */
    public SuperSampler(TargetAreaBase targetArea) {
        this.targetArea = targetArea;
    }

    /**
     * getter method for target area
     * @return the target area of the super sampler
     */
    public TargetAreaBase getTargetArea() {
        return targetArea;
    }

    /**
     * setter method for target area
     * @param targetArea the target area of the super sampler
     * @return the super sampler object
     */
    public SuperSampler setTargetArea(TargetAreaBase targetArea) {
        this.targetArea = targetArea;
        return this;
    }

    /**
     * calculates the average of the colors traced with the super sampling
     * @param convergence the point where all the sample rays converge
     * @param targetAreaCenter the center of the target area from which the sample points are created
     * @return the average color received by super sampling
     */
    public Color traceColor (Point convergence, Point targetAreaCenter) {
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
        List<Point> samples = targetArea.generateSamples(targetAreaCenter);
        for (Point sample : samples) {
            Ray sampleRay = constructSampleRay(convergence, sample);
            sum = sum.add(traceRayValue(sampleRay));
        }
        return sum.reduce(samples.size());
    }

    /**
     * builds a sample ray between the sample point and the point of convergence
     * @param convergence the point where all the sample rays converge
     * @param sample a sample point generated on the sample area
     * @return a sample ray
     */
    public abstract Ray constructSampleRay(Point convergence, Point sample);

    /**
     * calculates the value of the ray that will be averaged between all the sample rays
     * @param ray a sample ray
     * @return the desired Double3 value of the ray
     */
    public abstract Double3 traceRayValue(Ray ray);
}
