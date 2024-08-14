package renderer;

import primitives.Point;

import java.util.List;

/**
 * TargetAreaBase is a basic class representing an object that can generate for a target point
 * sample points from a target area around it to create sample rays for super sampling
 * @author Rachel and Tehila
 */
public abstract class TargetAreaBase {
    /** the number of sample points the target area generates for each point */
    private int numberOfSamples = 81;

    /**
     *
     * @return
     */
    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    /**
     *
     * @param numberOfSamples
     */
    public void setNumberOfSamples(int numberOfSamples) {
        if (numberOfSamples <= 0) {
            throw new IllegalArgumentException("number of samples needs to be positive");
        }
        this.numberOfSamples = numberOfSamples;
    }

    /**
     * a constructor to initialize a TargetAreaBase with the number of samples
     * @param numberOfSamples the number of sample points the target area generates
     */
    public TargetAreaBase(int numberOfSamples) {
        setNumberOfSamples(numberOfSamples);
    }

    /**
     * generates sample points in an area around the received targetPoint
     * @param targetPoint the center of the target area
     * @return a list of all the sample points
     */
    public abstract List<Point> generateSamples(Point targetPoint);

}
