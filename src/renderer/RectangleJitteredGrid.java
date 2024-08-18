package renderer;


import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;
import static java.lang.Math.random;

/** todo
 * TargetAreaBase is a basic class representing an object that can generate for a target point
 * sample points from a target area around it to create sample rays for super sampling
 * @author Rachel and Tehila
 */
/**
 * class RectangleJitteredTargetArea represents a rectangular target area that creates sample points
 * using the jittered super sampling pattern
 * @author Rachel and Tehila
 */
public class RectangleJitteredGrid extends TargetAreaBase{
    private int n = 9;

    public RectangleJitteredGrid() {}

    public RectangleJitteredGrid(int numberOfSamples, double height, double width) {
        super(numberOfSamples, height, width);
        n = (int) round(sqrt(numberOfSamples));
    }

    public RectangleJitteredGrid setNumberOfSamples(int numberOfSamples) {
        super.setNumberOfSamples(numberOfSamples);
        n = (int) round(sqrt(numberOfSamples));
        return this;
    }

    public RectangleJitteredGrid setHeight(double height) {
        super.setHeight(height);
        return this;
    }

    public RectangleJitteredGrid setWidth(double width) {
        super.setWidth(width);
        return this;
    }

    public List<Point> generateSamples(Point center, Vector up, Vector right) {
        double columnWidth = width / n;
        double rowHeight = height / n;
        Point corner = center.add(up.scale(-height/2)).add(right.scale(-width/2));
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
                point = corner.add(up.scale(scaleUp)).add(right.scale(scaleRight));
                targets.add(point);
            }
        }
        return targets;
    }
}
