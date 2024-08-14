package renderer;

import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.isZero;

/**
 * class RectangleJitteredTargetArea represents a rectangular target area that creates sample points
 * using the jittered super sampling pattern
 * @author Rachel and Tehila
 */
public class RectangleJitteredTargetArea extends RectangleTargetArea {

    public RectangleJitteredTargetArea(int numberOfSamples, double width, double height, Vector up, Vector right) {
        super(numberOfSamples, width, height, up, right);
    }

    //todo javadoc
    public RectangleJitteredTargetArea setWidth(double width) {
        this.width = width;
        return this;
    }

    public RectangleJitteredTargetArea setHeight(double height) {
        this.height = height;
        return this;
    }

    public RectangleJitteredTargetArea setUp(Vector up) {
        this.up = up;
        return this;
    }

    public RectangleJitteredTargetArea setRight(Vector right) {
        super.setRight(right);
        return this;
    }

    public List<Point> generateSamples(Point targetPoint) {
        Point corner = targetPoint.add(up.scale(-height/2)).add(right.scale(-width/2));
        LinkedList<Point> targets = new LinkedList<>();
        double scaleUp;
        double scaleRight;
        Point point;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                scaleUp = (i + random()) * rowHeight;
                scaleRight = (j + random()) * columnWidth;
                point = corner; //.add(up.scale(scaleUp)).add(right.scale(scaleRight));
                if (!isZero(scaleUp)) {
                    point = point.add(up.scale(scaleUp));
                }
                if (!isZero(scaleRight)) {
                    point = point.add(right.scale(scaleRight));
                }
                targets.add(point);
            }
        }
        return targets;
    }
}
