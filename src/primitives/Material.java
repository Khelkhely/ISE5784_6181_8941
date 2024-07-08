package primitives;

/**
 * Material class represents a material of a geometric body or shape in Cartesian 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public class Material {
    /** the attenuation coefficient of the diffusion of the light on the material */
    public Double3 kD = Double3.ZERO;
    /** the attenuation coefficient of the specularity of the light on the material */
    public Double3 kS = Double3.ZERO;
    /** the material's shininess */
    public int nShininess = 0;

    /**
     * setter method for kD that receives a Double3
     * @param kD the attenuation coefficient of the diffusion
     * @return the material object
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * setter method for kD that receives a double
     * @param kD the attenuation coefficient of the diffusion
     * @return the material object
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * setter method for kS that receives a Double3
     * @param kS the attenuation coefficient of the specularity
     * @return the material object
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * setter method for kS that receives a double
     * @param kS the attenuation coefficient of the specularity
     * @return the material object
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * setter method for nShininess
     * @param nShininess the material's shininess
     * @return the material object
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
