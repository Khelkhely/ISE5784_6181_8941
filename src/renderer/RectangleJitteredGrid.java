package renderer;

import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;
import static java.lang.Math.random;

/**
 * class RectangleJitteredTargetArea represents a rectangular target area that creates sample points
 * using the jittered super sampling pattern
 * @author Rachel and Tehila
 */
public class RectangleJitteredGrid extends TargetAreaBase{
    /** The number of rows or columns of the sample points */
    private int n = 9;

    /**
     * an empty constructor to create a RectangleJitteredGrid
     */
    public RectangleJitteredGrid() {}

    /**
     * a constructor to initialize a RectangleJitteredGrid
     * @param numberOfSamples the number of sample points the target area generates
     * @param height the height of the target area
     * @param width the width of the target area
     */
    public RectangleJitteredGrid(int numberOfSamples, double height, double width) {
        super(numberOfSamples, height, width);
        n = (int) round(sqrt(numberOfSamples));
    }

    @Override
    public TargetAreaBase setNumberOfSamples(int numberOfSamples) {
        super.setNumberOfSamples(numberOfSamples);
        n = (int) round(sqrt(numberOfSamples));
        return this;
    }

    @Override
    public TargetAreaBase setHeight(double height) {
        super.setHeight(height);
        return this;
    }

    @Override
    public TargetAreaBase setWidth(double width) {
        super.setWidth(width);
        return this;
    }

    @Override
    public List<Point> generateSamples(Point center, Vector vUp, Vector vRight) {
        double columnWidth = width / n;
        double rowHeight = height / n;
        Point corner = center.add(vUp.scale(-height/2)).add(vRight.scale(-width/2));
        LinkedList<Point> targets = new LinkedList<>();
        double scaleUp;
        double scaleRight;
        Point point;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //generate a random point in a rectangle around the center of the target area
                //the rectangle is half the height and width of the target area
                scaleUp = (i + random()/2 + 0.25) * rowHeight;
                scaleRight = (j + random()/2 + 0.25) * columnWidth;
                point = corner.add(vUp.scale(scaleUp)).add(vRight.scale(scaleRight));
                targets.add(point);
            }
        }
        return targets;
    }
}
