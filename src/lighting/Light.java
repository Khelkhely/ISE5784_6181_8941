package lighting;

import primitives.Color;

/**
 * An abstract class Light represents a light in the scene.
 * @author Rachel and Tehila
 */
abstract class Light {
    /** the intensity color of the light */
    protected Color intensity;

    /**
     * Constructor to initialize a light with an intensity color
     * @param intensity the intensity color of the light
     */
    public Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * getter method for light's original intensity
     * @return light's intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
