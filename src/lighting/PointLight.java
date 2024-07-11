package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static java.lang.Math.sqrt;

/**
 * PointLight class represents a point light source in the scene - the light comes from one point to all directions.
 * @author Rachel and Tehila
 */
public class PointLight extends Light implements LightSource {
    /** the position of the pointLight */
    protected Point position;
    /** factor kC for attenuation with distance */
    private double kC = 1;
    /** factor kL for attenuation with distance */
    private double kL = 0;
    /** factor kQ for attenuation with distance */
    private double kQ = 0;

    /**
     * constructor to initialize a PointLight with its intensity and position.
     * @param intensity the intensity of the pointLight.
     * @param position the position of the pointLight.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * setter method for kC attenuation factor
     * @param kC factor kC for attenuation with distance
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * setter method for kL attenuation factor
     * @param kL factor kL for attenuation with distance
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * setter method for kQ attenuation factor
     * @param kQ factor kQ for attenuation with distance
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double dSquared = p.distanceSquared(position);
        return intensity.reduce(kC + kL * sqrt(dSquared) + kQ * dSquared);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return point.distance(position);
    }
}
