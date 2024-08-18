package renderer;

import primitives.Point;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public abstract class TargetAreaBase {
    protected int numberOfSamples = 100;
    protected double height;
    protected double width;

    public TargetAreaBase() {}

    public TargetAreaBase(int numberOfSamples, double height, double width) {
        setNumberOfSamples(numberOfSamples).setHeight(height).setWidth(width);
    }

    public TargetAreaBase setNumberOfSamples(int numberOfSamples) {
        if (numberOfSamples <= 0) {
            throw new IllegalArgumentException("number of samples must be positive.");
        }
        this.numberOfSamples = numberOfSamples;
        return this;
    }

    public TargetAreaBase setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("target area height must be positive.");
        }
        this.height = height;
        return this;
    }

    public TargetAreaBase setWidth(double width) {
        if (width <= 0) {
            throw new IllegalArgumentException("target area width must be positive.");
        }
        this.width = width;
        return this;
    }

    public abstract List<Point> generateSamples(Point center, Vector up, Vector right);
}
