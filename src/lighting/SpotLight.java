package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * SpotLight class represents a spotlight source in the scene - the light comes from one point to a specific direction.
 * @author Rachel and Tehila
 */
public class SpotLight extends PointLight {
    /** the direction of the spotLight */
    private Vector direction;

    /**
     * constructor to initialize a spotLight with its intensity, position and direction.
     * @param intensity the intensity of the spotLight.
     * @param position the position of the spotLight.
     * @param direction the direction of the spotLight.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }

    @Override
    public SpotLight setKc(double kC) {
        super.setKc(kC);
        return this;
    }

    @Override
    public SpotLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    @Override
    public SpotLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double dotProduct = direction.normalize().dotProduct(getL(p).normalize());
        if (dotProduct < 0) {
            dotProduct = 0;
        }
        return super.getIntensity(p).scale(dotProduct);
    }
}
