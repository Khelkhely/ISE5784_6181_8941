package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Class AmbientLight represents an ambient light source which is a fixed-intensity and fixed-color light source
 * that affects all objects in the scene equally.
 * @author Rachel and Tehila
 */
public class AmbientLight extends Light {

    /** the default ambient light which is no light (black) */
    public static final AmbientLight NONE = new AmbientLight(new Color(java.awt.Color.BLACK), 0);

    /**
     * Constructor to initialize an ambient light with an intensity color and the attenuation coefficient.
     * @param intensity the intensity color.
     * @param kA the attenuation coefficient using Double3.
     */
    public AmbientLight(Color intensity, Double3 kA) {
        super(intensity.scale(kA));
    }

    /**
     * Constructor to initialize an ambient light with an intensity color and the attenuation coefficient.
     * @param intensity the intensity color.
     * @param kA the attenuation coefficient using double.
     */
    public AmbientLight(Color intensity, double kA) {
        super(intensity.scale(kA));
    }

}
