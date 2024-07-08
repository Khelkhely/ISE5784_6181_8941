package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * DirectionalLight class represents a directional light source in the scene - when the light source is far away.
 * @author Rachel and Tehila
 */
public class DirectionalLight extends Light implements LightSource {
    /** a normalized vector of the direction of the light */
    private Vector direction;

    /**
     * constructor to initialize a directionalLight with its intensity and direction.
     * @param intensity the intensity of the light.
     * @param direction the direction of the light.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
