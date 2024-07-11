package geometries;

import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class Geometry is the class representing a list of geometric bodies or shapes in Cartesian
 * 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public class Geometries extends Intersectable {
    /** a list of geometries */
    final private LinkedList<Intersectable> geometries = new LinkedList<>();

    /**
     * an empty constructor to initialize an empty list of geometries
     */
    public Geometries() { }

    /**
     * a constructor to initialize a list of geometries with the geometries sent as parameters
     * @param geometries the geometries to initialize the list with
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * adds the geometries sent as parameters to the geometries list
     * @param geometries the geometries to add to the list
     */
    public void add (Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (Intersectable geometry : geometries) {
            List<GeoPoint> geometryIntersections = geometry.findGeoIntersectionsHelper(ray,maxDistance);
            if (geometryIntersections != null) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(geometryIntersections);
            }
        }
        return intersections;
    }
}
