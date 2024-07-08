package geometries;
import primitives.Color;
import primitives.Material;
import primitives.Vector;
import primitives.Point;

/**
 * Class Geometry is the basic class representing a geometric body or shape in Cartesian
 * 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public abstract class Geometry extends Intersectable {
    /** color of the geometry */
    protected Color emission = Color.BLACK;

    /** the material of the geometry */
    private Material material = new Material();

    /**
     * setter method for geometry's emission color
     * @param emission color of the geometry
     * @return the geometry object itself
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * getter method for geometry's emission color
     * @return the geometry's emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * setter method for geometry's material
     * @param material material of the geometry
     * @return the geometry object itself
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * getter method for geometry's material
     * @return the geometry's material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * calculates a normal (vertical) vector to the geometry from a point on it
     * @param p1 a point on the geometry the normal is from
     * @return a normal (vertical) vector to the geometry
     */
    public abstract Vector getNormal(Point p1);
}