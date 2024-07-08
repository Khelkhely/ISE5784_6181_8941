package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * An abstract class Light represents an external light source in the scene.
 * @author Rachel and Tehila
 */
public interface LightSource {
    /**
     * Get light intensity from the light source at a point
     * @param p the point
     * @return color of the intensity from the light source at a point
     */
    public Color getIntensity(Point p);

    /**
     * calculates the direction the light source reaches the point from
     * @param p the point
     * @return the normalized vector of the direction the light source reaches the point from
     */
    public Vector getL(Point p);
}
