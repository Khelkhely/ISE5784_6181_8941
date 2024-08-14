package renderer;

import primitives.Point;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.ceil;
import static java.lang.Math.sqrt;

/**
 * RectangleTargetArea is a basic class representing a rectangular target area
 * @author Rachel and Tehila
 */
public abstract class RectangleTargetArea extends TargetAreaBase{
    protected double width;
    protected double height;
    protected Vector up;
    protected Vector right;

    protected int n; //number of rows / columns the rectangle is divided into
    protected double columnWidth; //width of each square on the grid
    protected double rowHeight; //height of each square on the grid

    public double getWidth() {
        return width;
    }

    public RectangleTargetArea setWidth(double width) {
        this.width = width;
        return this;
    }

    public double getHeight() {
        return height;
    }

    public RectangleTargetArea setHeight(double height) {
        this.height = height;
        return this;
    }

    public Vector getUp() {
        return up;
    }

    public RectangleTargetArea setUp(Vector up) {
        this.up = up;
        return this;
    }

    public Vector getRight() {
        return right;
    }

    public RectangleTargetArea setRight(Vector right) {
        this.right = right;
        return this;
    }

    public RectangleTargetArea(int numberOfSamples, double width, double height, Vector up, Vector right) {
        super(numberOfSamples);
        this.width = width;
        this.height = height;
        this.up = up;
        this.right = right;
        n = (int) ceil(sqrt(numberOfSamples));
        columnWidth = width / n;
        rowHeight = height / n;
    }

    /**
     * a constructor to initialize a TargetAreaBase with the number of samples
     *
     * @param numberOfSamples the number of sample points the target area generates
     */
    public RectangleTargetArea(int numberOfSamples) {
        super(numberOfSamples);
    }
}
