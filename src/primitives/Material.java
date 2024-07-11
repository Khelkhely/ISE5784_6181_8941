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
    /** the attenuation coefficient of the transparency of the material */
    public Double3 kT = Double3.ZERO;
    /** the attenuation coefficient of the reflection of the material */
    public Double3 kR = Double3.ZERO;

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
     * setter method for kD that receives a Double3
     * @param kT the attenuation coefficient of the transparency
     * @return the material object
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * setter method for kD that receives a double
     * @param kT the attenuation coefficient of the transparency
     * @return the material object
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * setter method for kD that receives a Double3
     * @param kR the attenuation coefficient of the reflection
     * @return the material object
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * setter method for kD that receives a double
     * @param kR the attenuation coefficient of the reflection
     * @return the material object
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
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
