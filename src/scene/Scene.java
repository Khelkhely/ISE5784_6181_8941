package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Class Scene is the basic class representing a scene in a Cartesian 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public class Scene {
    /** the name of the scene */
    public String name;
    /** the background color of the scene */
    public Color background = new Color(java.awt.Color.BLACK);
    /** the ambient light of the scene */
    public AmbientLight ambientLight = AmbientLight.NONE;
    /** a collection of the geometries in the scene */
    public Geometries geometries = new Geometries();
    /** a list of the lights sources of the scene */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * a constructor for Scene
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * a setter function for the background of the scene
     * @param background the color of the background of the scene
     * @return the Scene object
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * a setter function for the ambient light of the scene
     * @param ambientLight the ambient light of the scene
     * @return the Scene object
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * a setter function for the geometries of the scene
     * @param geometries a collection of the geometries in the scene
     * @return the Scene object
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * a setter function for the light sources of the scene
     * @param lights a list of the lights sources of the scene
     * @return the Scene object
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
