package renderer;

import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * TargetAreaBase is a basic class representing an object that can generate for a target point
 * sample points from a rectangular target area around it to create sample rays for super sampling
 * @author Rachel and Tehila
 */
public abstract class TargetAreaBase {
    /** the number of sample points the target area generates for each point */
    protected int numberOfSamples = 100;
    /** the height of the target area */
    protected double height;
    /** the width of the target area */
    protected double width;

    /**
     * an empty constructor to create a TargetAreaBase
     */
    public TargetAreaBase() {}

    /**
     * a constructor to initialize a TargetAreaBase
     * @param numberOfSamples the number of sample points the target area generates
     * @param height the height of the target area
     * @param width the width of the target area
     */
    public TargetAreaBase(int numberOfSamples, double height, double width) {
        setNumberOfSamples(numberOfSamples).setHeight(height).setWidth(width);
    }

    /**
     * setter function for TargetAreaBase's numberOfSamples
     * @param numberOfSamples the number of sample points the target area generates
     * @return the TargetAreaBase object
     */
    public TargetAreaBase setNumberOfSamples(int numberOfSamples) {
        if (numberOfSamples <= 0) {
            throw new IllegalArgumentException("number of samples must be positive.");
        }
        this.numberOfSamples = numberOfSamples;
        return this;
    }

    /**
     * setter function for TargetAreaBase's height
     * @param height the height of the target area
     * @return the TargetAreaBase object
     */
    public TargetAreaBase setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("target area height must be positive.");
        }
        this.height = height;
        return this;
    }

    /**
     * setter function for TargetAreaBase's width
     * @param width the width of the target area
     * @return the TargetAreaBase object
     */
    public TargetAreaBase setWidth(double width) {
        if (width <= 0) {
            throw new IllegalArgumentException("target area width must be positive.");
        }
        this.width = width;
        return this;
    }

    /**
     * generates sample points in a rectangular target area
     * @param center the center of the target area
     * @param vUp a vector represents the up direction of the target area
     * @param vRight a vector represents the vRight direction of the target area
     * @return a list of all the sample points
     */
    public abstract List<Point> generateSamples(Point center, Vector vUp, Vector vRight);
}
